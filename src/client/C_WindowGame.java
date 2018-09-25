package client;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class C_WindowGame extends BasicGameState {
	/**
	 * MAIN GAME WINDOW
	 */
	// ID we return to class 'Application'
	public static final int ID = 1;
	private C_Player mainPlayer;
	private C_Network network;

	public C_WindowGame(C_Network network){
		this.network = network;
	}
	// init-method for initializing all resources
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//C_Player mainPlayer = new C_Player(name);
	}

	// render-method for all the things happening on-screen
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
	}

	// update-method with all the magic happening in it
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		
	}

	// Returning 'ID' from class 'MainMenu'
	@Override
	public int getID() {
		return C_WindowGame.ID;
	}
}