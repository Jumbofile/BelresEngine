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
 * Derby
 * By: 
 * Gregory Plachno (Jumbofile)
 */
public class C_Main {
	public static C_Vars vars;
	public static C_Network network;
	public static C_Graphics graph;
	public boolean playing = true;
	/*
	 * THIS IS WHERE ARE THE MAGIC HAPPENS
	 */
	public static void main(String[] args) {
		//init the isntances
		vars = new C_Vars(); //NEED FOR VARIABLES
		
		//starts connection
		network = new C_Network();
		
		System.out.println("HM");
		//Starts graphics
		 try {
            AppGameContainer app = new AppGameContainer(graph = new C_Graphics("Belres Engine v" + vars.verison));
            app.setDisplayMode(vars.screenX, vars.screenY, false);
            app.setTargetFrameRate(vars.maxFPS);
            app.setShowFPS(true);
            graph.setNet(network);
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
		
		 
		 
	}
}
