package client;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class C_Graphics extends StateBasedGame{
	
	//Classes
	C_Network network;
	
	// Game state identifiers
    public static final int MAINMENU     = 0;
    public static final int GAME         = 1;

    // Application Properties
    public static final int WIDTH   = 640;
    public static final int HEIGHT  = 480;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;
    
    public C_Graphics(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
    
    public void setNet(C_Network net) {
    	this.network = net;
    }
    
    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) throws SlickException {
        // The first state added will be the one that is loaded first, when the application is launched
        this.addState(new C_WindowMenu(network));
        this.addState(new C_WindowGame());
    }
    

}