package hr.parkingsystem.main;

import java.awt.image.BufferedImage;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

public class Helper {

   static ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
   static Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
   
   static IplImage toIplImage(BufferedImage bufImage) {
	    return iplConverter.convert(java2dConverter.convert(bufImage));
	}
   
}