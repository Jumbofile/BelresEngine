package client;

import java.io.*;
import java.net.*;

public class C_Network {
	private Socket gameClient;
	public boolean connected = false;
	private BufferedReader recieveTCP;
	private DataOutputStream toServer;
	
	public C_Network(int port, String ip) {
		//Start the client		
		try {
			gameClient = new Socket(ip, port);
			toServer = new DataOutputStream(gameClient.getOutputStream());
			
			//Are we connected
			if(gameClient.isConnected()) {
				connected = true;
				C_WindowMenu game = new C_WindowMenu();
				game.setConnected(connected);
			}else {
				connected = false;
				C_WindowMenu game = new C_WindowMenu();
				game.setConnected(connected);
			}
		} catch (Exception e) {
			connected = false;
			C_WindowMenu game = new C_WindowMenu();
			game.setConnected(connected);
		}

	}
	

}
