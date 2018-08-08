package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
	public boolean playing = true;
	/*
	 * THIS IS WHERE ARE THE MAGIC HAPPENS
	 */
	public static void main(String[] args) {
		//init the isntances
		vars = new C_Vars(); //NEED FOR VARIABLES
		network = new C_Network();
		//starts connection
		network.start_connection(vars.PORT, vars.IP);
		
		//Starts graphics
		 try {
            AppGameContainer app = new AppGameContainer(new C_Graphics("Belres Engine v" + vars.verison));
            app.setDisplayMode(vars.screenX, vars.screenY, false);
            app.setTargetFrameRate(vars.maxFPS);
            app.setShowFPS(true);
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
		
		 
		 
	}
}
