package hr.parkingsystem.main;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.video.ImageListener;

public class VideoStreamer {
	
	private static BufferedImage bufferedImage;
	private static CanvasFrame canvas = new CanvasFrame("DroneParkingSystem",1.0);
	private static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	private static hr.parkingsystem.main.Detector detector = new hr.parkingsystem.main.Detector();
	private static IplImage iplImage = null;
	private static Frame frameImage = null;
	  
	private static void getImageFromURL(URL url){
		
		while(true){
							
			try {
				bufferedImage = ImageIO.read(url);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.err.println("Unknown host");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error while getting image from URL");
			}
			
			 
	        iplImage = Helper.toIplImage(bufferedImage);
			
			frameImage = converter.convert(detector.findMarker(iplImage));
			
			canvas.showImage(frameImage);
		}
	}

	
		
		public VideoStreamer() throws IOException
		{	  
			IARDrone drone = null;
		    try
		    {
		        drone = new ARDrone();
		        drone.start();
		    }
		    catch (java.lang.Exception exc)
			{
				exc.printStackTrace();
				System.err.println("Error while initiating drone");
			}
	   
		    InitCanvas();
	    
		    drone.getCommandManager().setVideoChannel(VideoChannel.VERT);
	    
		    drone.getVideoManager().addImageListener(new ImageListener() {			
		    	public void imageUpdated(BufferedImage image)
		        {
		            iplImage = Helper.toIplImage(image);
		    				    			
					frameImage = converter.convert(detector.findMarker(iplImage)); 
	    			
	    			canvas.showImage(frameImage);
	        			    			
	            }
			});		
	  }
	  
	  private void InitCanvas(){
		  
		    canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		    canvas.setLayout(null);
		    
		    
		    JButton btnStart = new JButton("Start");
		    btnStart.setSize(140, 50);		    
		    btnStart.setLocation(650, 30);
		    
		    JButton btnLand = new JButton("Land");
		    btnLand.setSize(140, 50);		    
		    btnLand.setLocation(650, 90);
		    
		    canvas.add(btnStart);
		    canvas.add(btnLand);
		    
		    canvas.setSize(810, 480);
		    
		    btnStart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						SocketMessage.getInstance().sendMessage("drone_start");
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
				}
			});
		    
		    btnLand.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						SocketMessage.getInstance().sendMessage("drone_land");
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
	  }
}
