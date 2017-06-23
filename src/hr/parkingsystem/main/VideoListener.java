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

    private IplImage iplImage = null;
	private Frame frameImage = null;
	
    private CanvasFrame canvas = new CanvasFrame("DroneParkingSystem",1.0);
	private OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	private Detector detector = new Detector();
	
    public VideoListener(final IARDrone drone)
    {
    	canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	drone.getCommandManager().setVideoChannel(VideoChannel.VERT);
    	 
        drone.getVideoManager().addImageListener(new ImageListener() {
            public void imageUpdated(BufferedImage image)
            {
                iplImage = Helper.toIplImage(image);
        		
        		frameImage = converter.convert(detector.findMarker(iplImage));
        		canvas.showImage(frameImage);

            }
        });       
    }
    
 
	

    

}
