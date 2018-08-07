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
		
	//Resources
	private TrueTypeFont font;
	private Image menuBack;
	private Image menuLogo;
	
	//Elements
	JTextField usernameBox, passwordBox;
	JFrame frame;
	// ID we return to class 'Application'
		public static final int ID = 0;

		// init-method for initializing all resources
		@Override
		public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
			/*
			 * MENU
			 */
			frame = new JFrame();  
			 
			//fonts
			Font awtFont = new Font("Times New Roman", Font.BOLD, 12);
			font = new TrueTypeFont(awtFont, true);
			
			//Login fields
			usernameBox = new JTextField("Username"); 
			usernameBox.setBounds((vars.screenX / 2) - 75, (vars.screenY / 2) - 20, 150, 25);  
			
			passwordBox = new JTextField("Username"); 
			passwordBox.setBounds((vars.screenX / 2) - 75, (vars.screenY / 2) + 20, 150 , 25);  

			usernameBox.addActionListener(this);
			passwordBox.addActionListener(this);
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
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			    	frame.add(usernameBox);
					frame.add(passwordBox);
			    }
			  });
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
