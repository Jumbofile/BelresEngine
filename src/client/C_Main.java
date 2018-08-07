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
		 try {
	            AppGameContainer app = new AppGameContainer(new C_Graphics("My Game v" + vars.verison));
	            app.setDisplayMode(vars.screenX, vars.screenY, false);
	            app.setTargetFrameRate(vars.maxFPS);
	            app.setShowFPS(true);
	            app.start();
	        } catch(SlickException e) {
	            e.printStackTrace();
	        }
	}
}
