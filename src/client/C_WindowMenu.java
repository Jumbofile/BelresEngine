package client;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class C_WindowMenu extends BasicGameState  {
	//References
	C_Vars vars = new C_Vars();
	
	//Vars
	public boolean connected;
		
	//Resources
	private TextField username;
	private TextField password;
	private TrueTypeFont font;
	private Image menuBack;
	private Image menuLogo;
	
	// ID we return to class 'Application'
		public static final int ID = 0;

		// init-method for initializing all resources
		@Override
		public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
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

		// render-method for all the things happening on-screen
		@Override
		public void render(GameContainer gmc, StateBasedGame sbg, Graphics gr) throws SlickException {
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

		// update-method with all the magic happening in it
		@Override
		public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
			
		}

		// Returning 'ID' from class 'MainMenu'
		public int getID() {
			return C_WindowMenu.ID;
		}
		
		public void setConnected(boolean connected) {
			this.connected = connected;
		}

}
