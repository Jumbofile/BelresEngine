package client;

import java.awt.Font;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.*;
/**
 * 
 * @Arsalan Taseer
 * This is a very basic example which
 * initializes a container
 *
 */
public class C_Graphics extends BasicGame{
	//Vars
	public boolean connected;
	
	//RESOURCES
	private TextField username;
	private TrueTypeFont font;
	
		
	public C_Graphics() {
		super("Belres Engine");
	}
	
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	/**
	 * This initializes the container
	 */
	public void init(GameContainer gc) throws SlickException
	{
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, true);
		 
		username = new TextField(gc, font, 100, 540, 1720 , 140);
	    username.setBackgroundColor(Color.white);
	    username.setBorderColor(Color.black);
	}
	
	public void update(GameContainer gc, int delta) throws SlickException
	{
		
	}
	
	public void render(GameContainer gmc, Graphics gr) throws SlickException
	{
		username.render(gmc, gr);
		
		//Connection label
		if(connected) {
			font.drawString(5, 50, "Online", Color.green);
		}else {
			font.drawString(5, 50, "Offline", Color.red);
		}
	}

}