package hr.parkingsystem.main;

import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.bytedeco.javacv.CameraDevice; 
import org.bytedeco.javacv.CameraSettings;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import javax.swing.WindowConstants;

import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_imgproc.CvFont;
import org.bytedeco.javacpp.opencv_videoio.CvCapture;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.Marker;
import org.bytedeco.javacv.MarkerDetector;
import org.bytedeco.javacv.MarkerDetector.Settings;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

public class Capture {

	private VideoCapture videoCapture = new VideoCapture();	
	
	private CanvasFrame canvas = new CanvasFrame("Window",1.0);

	OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	
	private ScheduledExecutorService timer;
	
	
	Runnable timerTask = new Runnable() {	
		//@Override
		public void run() {
			getFrame();
		}
	};

	
	public Drone drone;
	public void start(){

		//canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		for(int i = 0; i<2;i++){
			videoCapture.open(i);
			System.out.println("Index: " + i + " - " + videoCapture.isOpened());
			if (videoCapture.isOpened() == true){
				System.out.println("Kamera je otvorena");
				//pokreni timer za pregled slika
				timer = Executors.newSingleThreadScheduledExecutor();
				timer.scheduleAtFixedRate(timerTask, 1, 33,TimeUnit.MILLISECONDS);
				//postavi brzinu
				//Drone.speed = 5;
				drone = new Drone();
				break;								
			}
		}
		if (drone!=null){
			//drone.takeOff();
			
			//drone.sqareMovement();
		}
		 
		 
	}
	
	

	
	
	Mat matFrame = new Mat();
	IplImage iplFrame = new IplImage();
	Frame fFrame = new Frame();
	
	Mat originalFrame = new Mat();
	Mat subMat = new Mat();
	
	private void getFrame(){
		/*
	    videoCapture.read(matFrame);
		fFrame = converter.convert(matFrame);
		iplFrame = converter.convert(fFrame);
		IplImage slika = findMarker(iplFrame);
		Frame noviframe = converter.convert(slika);
		canvas.showImage(noviframe);
		*/
	}

	
	Marker[] polje;
	MarkerDetector markerDetector = new MarkerDetector();
	
	
	
	CvFont font = opencv_imgproc.cvFont(3);
	CvScalar color = new CvScalar(0, 0,255, 1);
	CvPoint markerCenter = new CvPoint();
	
	private IplImage findMarker(IplImage slika){
		
		Marker marker;
		polje = markerDetector.detect(slika, false);
		markerDetector.draw(slika, polje);

		boolean markerFound = false;

		for(int i = 0; i<polje.length;i++){
			if(polje[i].id == 1){
				marker = polje[i];
				markerCenter.put((int)marker.getCenter()[0], (int)marker.getCenter()[1]);
				
				opencv_imgproc.cvDrawCircle(slika,
						markerCenter, 
						5,
						color,
						0,0,0);
				
				opencv_imgproc.cvPutText(slika, "ID: " + marker.id,
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
			
			//drone.getInCenter(getSpeedRatio(markerCenter.x(),markerCenter.y()),getDirection(markerCenter.x(),markerCenter.y()));
			
			opencv_imgproc.cvPutText(slika, "Found!",
					new CvPoint(300,50),
					font,
					color);
			
			//drone.landing();
			
		}
		
		return slika;
	}
	
	final int centerX = 320;
	final int centerY = 240;
	//omjer lijevo desno naspram gore dole
	private double getSpeedRatio(double x, double y){	
		return (Math.abs(centerX-x)) / (Math.abs(centerY-y));
	}
	
	private Direction getDirection(int x, int y){
		//gore desno
		if (centerX < x && centerY > y) 
			return Direction.upright;
		//dolje desno
		if (centerX < x && centerY < y) 
			return Direction.downright;
		//gore lijevo
		if (centerX > x && centerY > y) 
			return Direction.upleft;
		//dolje lijevo 
		if (centerX > x && centerY < y) 
			return Direction.downleft;
		return null;
	}
	
}

enum Direction{
	downright,
	downleft,
	upleft,
	upright
}



