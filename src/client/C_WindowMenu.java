package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class C_WindowMenu extends BasicGameState implements ActionListener {
	// References
	C_Vars vars = new C_Vars();

	// Vars
	public boolean connected;
	private String username = new String();
	private String password = new String();
	private String temp = new String();
	private StringBuffer buf2 = new StringBuffer();

	// Resources
	private TrueTypeFont font;
	private Image menuBack;
	private Image menuLogo;

	// Elements
	TextField usernameBox, passwordBox;
	// ID we return to class 'Application'
	public static final int ID = 0;

	// init-method for initializing all resources
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		/*
		 * MENU
		 */
		// fonts
		Font awtFont = new Font("Roboto", Font.BOLD, 14);
		font = new TrueTypeFont(awtFont, true);

		// Login fields
		usernameBox = new TextField(gc, font, (vars.screenX / 2) - 75, (vars.screenY / 2) - 20, 150, 25,
				new ComponentListener() {
					public void componentActivated(AbstractComponent source) {
						username = usernameBox.getText();
						passwordBox.setFocus(true);
					}
				});

		// PasswordField
		passwordBox = new TextField(gc, font, (vars.screenX / 2) - 75, (vars.screenY / 2) + 20, 150, 25,
				new ComponentListener() {
					public void componentActivated(AbstractComponent source) {
						usernameBox.setFocus(true);
					}
				});

		// back image
		menuBack = new Image("data/graphics/menu/back.png");
		menuLogo = new Image("data/graphics/menu/logo.png");
	}

	// render-method for all the things happening on-screen
	@Override
	public void render(GameContainer gmc, StateBasedGame sbg, Graphics gr) throws SlickException {
		// Render menu
		menuBack.draw(0, 0);
		menuLogo.draw((vars.screenX / 2) - 330, 10);

		font.drawString((vars.screenX / 2) - 34, (vars.screenY / 2) - 40, "Username", Color.white);
		usernameBox.render(gmc, gr);
		passwordBox.render(gmc, gr);

		// Connection label
		if (connected) {
			font.drawString(5, 50, "Online", Color.green);
		} else {
			font.drawString(5, 50, "Offline", Color.red);
		}
	}

	// update-method with all the magic happening in it
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {

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

}
