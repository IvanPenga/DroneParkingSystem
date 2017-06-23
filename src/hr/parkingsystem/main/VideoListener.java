package hr.parkingsystem.main;

import java.awt.image.BufferedImage;

import javax.swing.WindowConstants;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.Marker;
import org.bytedeco.javacv.MarkerDetector;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

import de.yadrone.base.IARDrone;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.video.ImageListener;


public class VideoListener{


	private BufferedImage image = null;
    private IplImage iplImage = null;
    private CanvasFrame canvas = new CanvasFrame("DroneParkingSystem",1.0);
	private OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	
	Marker[] polje;
	MarkerDetector markerDetector = new MarkerDetector();
	
    public VideoListener(final IARDrone drone)
    {
    	canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	drone.getCommandManager().setVideoChannel(VideoChannel.NEXT);
    	 
        drone.getVideoManager().addImageListener(new ImageListener() {
            public void imageUpdated(BufferedImage newImage)
            {
                image = newImage;
                iplImage = toIplImage(image);
                
        		polje = markerDetector.detect(iplImage, false);
        		markerDetector.draw(iplImage, polje);
        		
        		Frame frameImage = converter.convert(iplImage);
        		canvas.showImage(frameImage);

            }
        });       
    }
    
	IplImage toIplImage(BufferedImage bufImage) {

	    ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
	    Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
	    IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
	    return iplImage;
	}
	

    

}
