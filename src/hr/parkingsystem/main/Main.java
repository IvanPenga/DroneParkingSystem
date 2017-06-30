package hr.parkingsystem.main;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.WindowConstants;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class Main {
	 
	
	  
		
	public static void main(String[] args) throws Exception{
		System.out.println("Starting...");
		Loader.load(opencv_core.class);
		System.out.println("Ucitao...");
		new VideoStreamer();
		
		/*
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("http://192.168.1.1:5555");
        // Open video video file
        grabber.start();

        // Prepare window to display frames
        CanvasFrame canvasFrame = new CanvasFrame("Extracted Frame", 1);
        canvasFrame.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());
        // Exit the example when the canvas frame is closed
        canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // Read frame by frame, stop early if the display window is closed
        Frame frame;
        while (true) {
            // Capture and show the frame
        	frame = grabber.grab();
        	if (frame != null){
        		canvasFrame.showImage(frame);
            	// Delay
            	Thread.sleep(33);
        	}
        }

        // Close the video file
        //grabber.release();
		*/

    		
    		

		/*
		
	    @SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(5000);
		    System.out.println("Server started");

		    while (true) {
		      System.out.println("Waiting for a  connection...");

		      final Socket activeSocket = serverSocket.accept();

		      Runnable runnable = new Runnable() {
				@Override
				public void run() {
					handleClientRequest(activeSocket);
				}
			};
		      new Thread(runnable).start(); // start a new thread
		    }
		*/
	}
	


	  public static void handleClientRequest(Socket socket) {
	    try{
	      BufferedReader socketReader = null;

	      socketReader = new BufferedReader(new InputStreamReader(
	          socket.getInputStream()));

	      String inMsg = null;
	      while ((inMsg = socketReader.readLine()) != null) {
	        System.out.println("Received from  client: " + inMsg);
	        if ("initiateDrone".equals(inMsg)){
	        
	        	System.out.println("Drone initiated...");
	        }
	        if ("landCommand".equals(inMsg)){
	     
	        	System.out.println("Landing...");
	        }
	        if ("emergencyCommand".equals(inMsg)){
	       
	        	System.out.println("EMERGENCY!");
	        }
	      }
	      socket.close();
	    }catch(Exception e){
	      e.printStackTrace();
	    }

	  }
	

}
