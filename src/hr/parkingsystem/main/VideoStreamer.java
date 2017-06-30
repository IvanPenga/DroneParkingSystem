package hr.parkingsystem.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.WindowConstants;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.video.ImageListener;

public class VideoStreamer {
	
	private static BufferedImage bufferedImage;
	private static CanvasFrame canvas = new CanvasFrame("DroneParkingSystem",1.0);
	private static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	private static Detector detector = new Detector();
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
	

	  
	  public VideoStreamer() throws MalformedURLException
	  {
		    IARDrone drone = null;
		    try
		    {
		        drone = new ARDrone();
		        //drone.start();
		    }
		    catch (Exception exc)
			{
				exc.printStackTrace();
				System.err.println("Error while initiating drone");
			}
		    
		    canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
}
