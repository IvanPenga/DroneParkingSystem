package hr.parkingsystem.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketMessage {

	 private Socket socket;
	 private PrintWriter out;
	 private static SocketMessage socketInstance = null;
	 
	 private SocketMessage() throws UnknownHostException, IOException{
		
		String address = "192.168.1.200"; //raspberry pi address
		socket = new Socket (address, 5000);
		socket.setKeepAlive(true);
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
		    try {
		        closeConnection();
		        System.out.println("Socket closed");
		    } 
		    catch (IOException e)
		    {
		    	e.printStackTrace();
		    	System.out.println("Error while closing socket");
		    }
		}});
		
	}
	
	public static SocketMessage getInstance() throws UnknownHostException, IOException{
		if (socketInstance == null){
			socketInstance= new SocketMessage();
			return socketInstance;
		}
		return socketInstance;
	}
	
	public void sendMessage(String message) throws IOException,UnknownHostException{
		 
		if (socket.isConnected()){			
			out = new PrintWriter (socket.getOutputStream(), true);	    
			out.println (message);			
		}
		
	}
	
	public void closeConnection() throws IOException{
		out.close();
		socket.close();
	}
	
}
