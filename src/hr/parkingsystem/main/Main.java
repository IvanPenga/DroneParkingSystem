package hr.parkingsystem.main;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class Main {
	 
	
	  
		
	public static void main(String[] args) throws Exception{
		System.out.println("Starting...");
		Loader.load(opencv_core.class);
		System.out.println("Ucitao...");
		new VideoStreamer();
		
		//Drone.initDrone();
		

    		
    		

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
