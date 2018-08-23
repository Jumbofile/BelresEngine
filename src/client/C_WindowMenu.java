package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class C_WindowMenu extends BasicGameState implements ActionListener, KeyListener{
	// References
	C_Vars vars = new C_Vars();

	// Vars
	public boolean connected;
	private String username = new String();
	private String password = new String();
	private String temp = new String();
	private StringBuffer buf2 = new StringBuffer();
	private boolean loginPressed = false;
	private double LastMoveTime;
	private boolean loginValid = false;
	
	//Classes
	C_Network network;
	// Resources
	private TrueTypeFont roboto14, roboto18;
	private Image menuBack, menuLogo, menuButtonUp, menuButtonDown, menuButtonHover, menuLogin;
	private Animation fire;
	
	// Elements
	private TextField usernameBox, passwordBox;
	// ID we return to class 'Application'
	public static final int ID = 0;
	
	//defines the network used
	public C_WindowMenu(C_Network net) {
		this.network = net;
		//addKeyListener(this);

	}
	
	// init-method for initializing all resources
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		/*
		 * MENU
		 */
		roboto14 = new TrueTypeFont(new Font("Roboto", Font.PLAIN, 18), true);
		roboto18 = new TrueTypeFont(new Font("Roboto", Font.PLAIN, 18), false);
		// Login fields
		usernameBox = new TextField(gc, roboto14, (vars.screenX / 2) - 75, (vars.screenY / 2) - 20, 150, 24,
				new ComponentListener() {
					public void componentActivated(AbstractComponent source) {
						passwordBox.setFocus(true);
						username = usernameBox.getText();
					}
				});
		
			
		//set focus to the username box first
		usernameBox.setFocus(true);
		
		// PasswordField
		passwordBox = new TextField(gc, roboto14, (vars.screenX / 2) - 75, (vars.screenY / 2) + 20, 150, 24,
				new ComponentListener() {
					public void componentActivated(AbstractComponent source) {
						usernameBox.setFocus(true);
					}
				});
		/*//menu animations
		Image fireSheetImg = new Image("data/graphics/menu/snow.png");
		SpriteSheet fireSheet = new SpriteSheet(fireSheetImg, 800, 600);
		fire = new Animation(fireSheet, 80);*/
		
		// Load in Images 
		menuBack 		= new Image("data/graphics/menu/back2.png");
		menuLogo 		= new Image("data/graphics/menu/logo.png");
		menuLogin 		= new Image("data/graphics/menu/login.png");
		menuButtonUp 	= new Image("data/graphics/menu/bup.png");
		menuButtonDown 	= new Image("data/graphics/menu/bdown.png");
		menuButtonHover = new Image("data/graphics/menu/bhover.png");
	}

	// render-method for all the things happening on-screen
	@Override
	public void render(GameContainer gmc, StateBasedGame sbg, Graphics gr) throws SlickException {
		//mouse
		Input input = gmc.getInput();
		int xpos = input.getMouseX();
		int ypos = input.getMouseY();
		//MouseListener click = new MouseListener();
				
		// Render menu
		menuBack.draw(0, 0);
		
		//animation
		//fire.draw(0, 0);
		
		//logo
		menuLogo.draw((vars.screenX / 2) - 330, 10);
		
		//login
		menuLogin.draw((vars.screenX / 2) - 75, (vars.screenY / 2) - 35);
		usernameBox.render(gmc, gr);
		passwordBox.render(gmc, gr);

		//login button
		menuButtonUp.draw((vars.screenX / 2) - (120 / 2), (vars.screenY / 2) + 45, 120, 49);
		if(!loginPressed) {
			if(xpos > (vars.screenX / 2) - (120 / 2) && xpos < (vars.screenX / 2) + (120 / 2) &&
					ypos > (vars.screenY / 2) + 45 && ypos < (vars.screenY / 2) + 94) {
				menuButtonHover.draw((vars.screenX / 2) - (120 / 2), (vars.screenY / 2) + 45, 120, 49);
			}
		}else if(loginPressed) {
			menuButtonDown.draw((vars.screenX / 2) - (120 / 2), (vars.screenY / 2) + 45, 120, 49);
		}
		
		// Connection label
		//if (network.getConnected()) {
		//	roboto14.drawString(10, 30, "Online", Color.green);
		//} else {
		//	roboto14.drawString(10, 30, "Offline", Color.red);
		//}
	}
	@Override
	public void mousePressed(int button, int x, int y)
	{
	    if( button == 0 )
	    {
	    	if(x > (vars.screenX / 2) - (120 / 2) && x < (vars.screenX / 2) + (120 / 2) &&
					y > (vars.screenY / 2) + 45 && y < (vars.screenY / 2) + 94) {
	    		if(usernameBox.getText().equals("") || usernameBox.getText().equals(null) ||
	    				passwordBox.getText().equals("") || passwordBox.getText().equals(null)) {
	    			//invalid entry
	    		}else {
		    		loginPressed = true;
	    			menuButtonDown.draw((vars.screenX / 2) - (120 / 2), (vars.screenY / 2) + 45, 120, 49);
	    			//System.out.println("CLONK");
	    			//System.out.println(temp);
	    			//String pw_hash = BCrypt.hashpw(temp, BCrypt.gensalt()); 
	    			network.sendLogin(usernameBox.getText(), temp);
	    			LastMoveTime = System.currentTimeMillis();
	    			
	    			//Are you online?
	    			if(network.connected) {
	    			//was the login attempt valid?
		    			
	    			}else {
	    				JOptionPane.showMessageDialog(new JFrame(), "Server Offline");
	    			}
	    		}
	    	}
	    }else {
	    	loginPressed = false;
	    }
	}
	
	// update-method with all the magic happening in it
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		//mouse
		Input input = gc.getInput();
		int xpos = input.getMouseX();
		int ypos = input.getMouseY();
		/*
		 * This stars out the password in the textfield
		 */

		// If backspace was pressed chop a char off of temp
		if (gc.getInput().isKeyPressed(Input.KEY_BACK)) {
			// No OutOfBounds here
			if (buf2.length() > 0) {
				buf2.deleteCharAt(buf2.length() - 1);
				temp = buf2.toString();
			}
		}

		// Get the string from the textfield
		password = passwordBox.getText();

		// Add characters to a buffer before they are changed to a '*', ignores '*'
		if (password.length() > 0) {
			if (!password.substring(password.length() - 1, password.length()).equals("*")) {
				buf2.append(password.substring(password.length() - 1, password.length()));
				temp = buf2.toString();
			}
		} else {
			// if the length is 0 the string is empty
			temp = "";
		}

		// Buffer for masking the characters
		StringBuffer maskValue = new StringBuffer();

		// Go through the password and change each of the chars to '*'
		if (password != null && password.length() > 0) {
			for (int i = 0; i < password.length(); i++) {
				maskValue.append("*");
			}
		}

		// Set the text in the password box to '*'
		passwordBox.setText(maskValue.toString());
		
		// DEBUG System.out.println(temp);
		if(loginPressed) {
			//System.out.println(loginValid);
			if(network.loginValid() == true) {
				sbg.enterState(1);
			}
			//need to show something to let user know login was invalid
			if(System.currentTimeMillis() - LastMoveTime >= 150){ 
				loginPressed = false;
			}
			
		}
	}
	// Returning 'ID' from class 'MainMenu'
	public int getID() {
		return C_WindowMenu.ID;
	}

	// Sets connection on GUI
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == Input.KEY_TAB) {
            System.out.println("HI");
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
	
	