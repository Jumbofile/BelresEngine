package client;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/*
 * Belres Engine
 * 2D Sidescroller ORPG Engine
 * Using:
 * Slick2d
 * KryoNet
 */
public class C_Main {
	public static C_Vars vars;
	public static C_Network network;
	public static void main(String[] args) {
		//init the isntances
		vars = new C_Vars(); //NEED FOR VARIABLES
		network = new C_Network(vars.PORT, vars.IP); //Start network connection
		
		//Starts graphics
		// initially the container is set to null.We will add properties later
		AppGameContainer screen = null;
		try {
			screen = new AppGameContainer(new C_Graphics());
		} catch (SlickException e1) {
		
			e1.printStackTrace();
		}
		try {
			screen.setDisplayMode(vars.screenX, vars.screenY, false);
		} catch (SlickException e) {
			
			e.printStackTrace();
		}
		
		try {
			screen.start();
		} catch (SlickException e) {
		
			e.printStackTrace();
		}
	}
}
