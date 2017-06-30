package hr.parkingsystem.main;

import java.awt.image.BufferedImage;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

public class Helper {

   final static ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
   final static Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
   
   static IplImage toIplImage(BufferedImage bufImage) {
	    return iplConverter.convert(java2dConverter.convert(bufImage));
	}
	
   final static int centerX = 320;
   final static int centerY = 240;
   
   public  static Direction getDirection(int x, int y){
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