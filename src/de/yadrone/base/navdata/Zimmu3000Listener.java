package de.yadrone.base.navdata;

import java.util.EventListener;

public interface Zimmu3000Listener extends EventListener {

	public void location(double latitude, double longitude, double elevation);

}
