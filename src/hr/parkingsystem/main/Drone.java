package hr.parkingsystem.main;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.navdata.AttitudeListener;
import de.yadrone.base.navdata.NavDataManager;
import de.yadrone.base.navdata.Zimmu3000Listener;

public class Drone {
	
	static CommandManager cmd = null;
	static IARDrone drone = null;
	public static int speed = 0;
	
	public void takeOff(){
		if (cmd!=null){
			cmd.takeOff();
		}		
	}
	
	public void landing(){
		if (cmd!=null){
			cmd.landing();
			cmd.stop();
		}
	}
	public void foward(){
		if (cmd!=null){
			cmd.forward(speed);
		}
	}
	public void backward(){
		if (cmd!=null){
			cmd.backward(speed);
		}
	}
	public void right(){
		if (cmd!=null){
			cmd.goRight(speed);
		}
	}
	public void left(){
		if (cmd!=null){
			cmd.goLeft(speed);
		}
	}
	public void spinRight(){
		if (cmd!=null){
			cmd.spinRight(speed);
		}
	}
	
	public void spinLeft(){
		if (cmd!=null){
			cmd.spinRight(speed);
		}
	}
	


	public Drone(){
		
	   try
	    {
	        drone = new ARDrone();
	        

	        cmd = drone.getCommandManager();
	        drone.start();
	        //ako letimo po zatvorenom
	        cmd.setOutdoor(false, true);
	        
	        
	        final NavDataManager data = drone.getNavDataManager();
	       
			data.addZimmu3000Listener(new Zimmu3000Listener() {
				
				@Override
				public void location(double latitude, double longitude, double elevation) {
					System.out.println(latitude + " - " + longitude + " - " + elevation);
					
				}
			});
			
			data.addAttitudeListener(new AttitudeListener() {
				
				@Override
				public void windCompensation(float arg0, float arg1) {
					
				}
				
				@Override
				public void attitudeUpdated(float arg0, float arg1, float arg2) {
				
				}
				
				@Override
				public void attitudeUpdated(float arg0, float arg1) {
					
					
				}
			});	
	        
	    }
	    catch (Exception exc)
		{
	    	System.out.println("Ne mogu se povezati sa dronom");
			exc.printStackTrace();
		}
	   	
		new VideoListener(drone);
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
