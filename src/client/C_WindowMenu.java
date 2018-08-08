package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class C_WindowMenu extends BasicGameState implements ActionListener  {
	//References
	C_Vars vars = new C_Vars();
	
	//Vars
	public boolean connected;
	String username = new String();
	String password;
	//Resources
	private TrueTypeFont font;
	private Image menuBack;
	private Image menuLogo;
	
	//Elements
	TextField usernameBox, passwordBox;
	// ID we return to class 'Application'
		public static final int ID = 0;

		// init-method for initializing all resources
		@Override
		public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
			/*
			 * MENU
			 */ 
			//fonts
			Font awtFont = new Font("Roboto", Font.BOLD, 14);
			font = new TrueTypeFont(awtFont, true);
			
			//Login fields
			usernameBox = new TextField(gc, font, (vars.screenX / 2) - 75, (vars.screenY / 2) - 20, 150, 25, new ComponentListener() {
				public void componentActivated(AbstractComponent source) {
					username = usernameBox.getText();
					passwordBox.setFocus(true);
				}
			});
			StringBuffer buf = new StringBuffer();
	        char ch = '*';
	        if(username.length() > 0) {
		        for (int i=0; i < username.length(); i++) {
		            buf.append(ch);
		        }
	        }
	        usernameBox.setText(buf.toString());
	        
			//PasswordField 
			passwordBox = new TextField(gc, font, (vars.screenX / 2) - 75, (vars.screenY / 2) + 20, 150, 25,new ComponentListener() {
				public void componentActivated(AbstractComponent source) {
					password = passwordBox.getText();
					usernameBox.setFocus(true);
				}
			});
			
			
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
			
			font.drawString((vars.screenX / 2)  - 343, (vars.screenY / 2) - 40, "Username", Color.white);
			usernameBox.render(gmc, gr);
			passwordBox.render(gmc, gr);
			
			
			
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
		
		//Sets connection on GUI
		public void setConnected(boolean connected) {
			this.connected = connected;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

}
