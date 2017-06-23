package hr.parkingsystem.main;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;

public class Main {

	public static void main(String[] args) {
		System.out.println("Starting...");
		Loader.load(opencv_core.class); 
		new Drone();

	}

}
