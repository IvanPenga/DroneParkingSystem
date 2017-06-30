package hr.parkingsystem.main;

import org.bytedeco.javacpp.opencv_imgproc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgproc.CvFont;
import org.bytedeco.javacv.Marker;
import org.bytedeco.javacv.MarkerDetector;

public class Detector {
	
	private Marker[] polje;
	private MarkerDetector markerDetector = new MarkerDetector();
	
	private CvFont font = opencv_imgproc.cvFont(3);
	private CvScalar color = new CvScalar(0, 0,255, 1);
	private CvPoint markerCenter = new CvPoint();
	private boolean markerFound = false;
	private Marker marker;
	
	public IplImage findMarker(IplImage image){
				
		if (image == null){
			return null;
		} 
		
		polje = markerDetector.detect(image, false);
		markerDetector.draw(image, polje);

		markerFound = false;

		for(int i = 0; i<polje.length;i++){
			if(polje[i].id == 1){
				marker = polje[i];
				markerCenter.put((int)marker.getCenter()[0], (int)marker.getCenter()[1]);
				
				opencv_imgproc.cvDrawCircle(image,
						markerCenter, 
						5,
						color,
						0,0,0);
				
				opencv_imgproc.cvPutText(image, "ID: " + marker.id,
						markerCenter,
						font,
						color);
				
				markerFound=true;
				break;
			}
		}
		
		if (markerFound){
			ValidFrame.valid(true);
		}
		else{
			ValidFrame.valid(false);
		}
		
		if (ValidFrame.getValidator()){
			//getSpeedRatio(markerCenter.x(),markerCenter.y())
			//Drone.getInCenter(Helper.getDirection(markerCenter.x(),markerCenter.y()));
			/*
			opencv_imgproc.cvPutText(image, "Found!",
					new CvPoint(300,50),
					font,
					color);
			*/
			
			
			try {
				SocketMessage.getInstance().sendMessage("Hello");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.err.println("Error on creating socket or sending message");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error on creating socket or sending message");
			}
		     		
		}
		
		return image;
	}
}
