package com.belres.game;

public class C_Vars {
	/*
	 * These are where all of the variables are stored
	 */
	
	//STATIC
	//Game Generics
	public final double verison 	= 0.1;
	public final int maxFPS  		= 60;
	
	//network
	public final int portTCP    	= 7002;
	public final int portUDP    	= 7003;
	public final String IP   		= new String("127.0.0.1");
	
	//screen
	public final int screenX 		= 1280;
	public final int screenY 		= 720;
	
	
	//DYNAMIC
	//Are we switching scenes or ending the game?
	public boolean stillPlaying = false;
}
