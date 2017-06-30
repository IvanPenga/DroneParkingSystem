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
		  	canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		  	//na ovom portu ce Node updateati slike  
			final URL url = new URL("http://localhost:8080/");
			new Runnable() {
				
				@Override
				public void run() {
					
					getImageFromURL(url);
					
				}
			}.run();	
	  }
}