package client;

import java.io.*;
import java.net.*;

public class C_Network {
	public boolean connected = false;
	private static Socket gameClient;
	private static BufferedReader in;
	private static PrintWriter out;
	
	public C_Network(int port, String ip) {
		//Start the client		
		 try {
				gameClient = new Socket(ip, port);
				in = new BufferedReader(new InputStreamReader(gameClient.getInputStream()));
				out = new PrintWriter(gameClient.getOutputStream(), true);
				//Are we connected
				if(gameClient.isConnected()) {
					connected = true;
				}	
				
			} catch (Exception e) {
				connected = false;
			}

	}
	
	//Check if client is online
	public boolean getConnected() {
		return connected;
	}
	
	//send login to the server
	public void sendLogin(String username, String password) {
		//send login information
		//System.out.print(username + ", " + password);
		String packet = new String("login<" + username + "," + password);
		out.println(packet);
		//out.close();
	}

	public boolean loginValid() {
		//if(in.)
		
		try {
			String line = in.readLine();
			//System.out.println(line);
			if(line.equals("true")) {
				out.close();
				return true;
			}else {
				return false;
			}
		} catch (IOException e) {
			System.out.println("HI");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
		}
	}
}
