package hr.parkingsystem.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.WindowConstants;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

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
    	
    	
        drone.getVideoManager().addImageListener(new ImageListener() {
            public void imageUpdated(BufferedImage image)
            {
                iplImage = Helper.toIplImage(image);
        		
        		try {
					frameImage = converter.convert(detector.findMarker(iplImage));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		canvas.showImage(frameImage);

            }
        }); 
    	
    }
    /*

	*/

    

}
