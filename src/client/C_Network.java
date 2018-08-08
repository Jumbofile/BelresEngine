package client;

import java.io.*;
import java.net.*;

public class C_Network {
	public boolean connected = false;
	private static Socket gameClient;
	private static BufferedReader in;
	private static PrintWriter out;
	
	public C_Network() {
		//Start the client		
		 

	}
	
	public void start_connection(int port, String ip) {
		//start network
		 try {
				gameClient = new Socket(ip, port);
				in = new BufferedReader(new InputStreamReader(gameClient.getInputStream()));
				out = new PrintWriter(gameClient.getOutputStream(), true);
				//Are we connected
				if(gameClient.isConnected()) {
					connected = true;
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
