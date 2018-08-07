package client;

import java.awt.Font;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
	
	//References
	C_Vars vars = new C_Vars();
	
	//RESOURCES
	private TextField username;
	private TextField password;
	private TrueTypeFont font;
	private Image menuBack;
	private Image menuLogo;
	
		
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
		/*
		 * MENU
		 */
		
		//fonts
		Font awtFont = new Font("Times New Roman", Font.BOLD, 12);
		font = new TrueTypeFont(awtFont, true);
		
		//Login fields
		username = new TextField(gc, font, (vars.screenX / 2) - 75, (vars.screenY / 2) - 20, 150 , 25);
	    username.setBackgroundColor(Color.white);
	    username.setBorderColor(Color.darkGray);
	    
	    password = new TextField(gc, font, (vars.screenX / 2) - 75, (vars.screenY / 2) + 20, 150 , 25);
	    password.setBackgroundColor(Color.white);
	    password.setBorderColor(Color.darkGray);
	    
	    //back image
	    menuBack = new Image("data/graphics/menu/back.png");
	    menuLogo = new Image("data/graphics/menu/logo.png");
	}
	
	public void update(GameContainer gc, int delta) throws SlickException
	{
		
	}
	
	public void render(GameContainer gmc, Graphics gr) throws SlickException
	{
		//Render menu
		menuBack.draw(0,0);
		menuLogo.draw((vars.screenX / 2) - 330, 10);
		username.render(gmc, gr);
		password.render(gmc, gr);
		
		
		//Connection label
		if(connected) {
			font.drawString(5, 50, "Online", Color.green);
		}else {
			font.drawString(5, 50, "Offline", Color.red);
		}
	}

}