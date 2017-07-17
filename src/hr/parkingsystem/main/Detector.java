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
	private Marker carMarker;
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
				
			}
			if (markerFound){
				if (polje[i].id == 2){
					//pronaden je i drugi marker i sada mozes sletiti					
					try {
						SocketMessage.getInstance().sendMessage("drone_carFound");
					} catch (UnknownHostException e) {
						System.err.println("Unknown host");
						e.printStackTrace();
					} catch (IOException e) {
						System.err.println("Error while sending message: drone_carFound");
						e.printStackTrace();
					}				
				}
			}
		}
		
		if (markerFound){
			ValidFrame.valid(true);
		}
		else{
			ValidFrame.valid(false);
		}
		
		if (ValidFrame.getValidator()){
			System.out.println(Helper.getDirection((int)marker.getCenter()[0], (int)marker.getCenter()[1]));
						
			String direction = Helper.getDirection((int)marker.getCenter()[0], (int)marker.getCenter()[1]).toString();
			
			try {
				SocketMessage.getInstance().sendMessage("drone_center"); //"drone_"+direction
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
	
	public void drawGrid(IplImage image){
		int width = image.width();
		int height = image.height();
		
		for (int i = width/4; i < width; i+=width/4){
			opencv_imgproc.cvDrawLine(
					image,
					new CvPoint(i, 0), 
					new CvPoint(i,height),
					color, 1, 0, 0);	
		}
		for (int i = height/4; i < height; i+=height/4){
			opencv_imgproc.cvDrawLine(
					image,
					new CvPoint(0,i), 
					new CvPoint(width,i),
					color, 1, 0, 0);	
		}
	}
}
