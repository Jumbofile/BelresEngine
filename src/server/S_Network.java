package server;

import java.io.*;
import java.net.*;

public class S_Network {
	private ServerSocket gameServer;
	public boolean connected = false;
	
	public S_Network(int port) {
		try {
			gameServer = new ServerSocket(port);
			
			System.out.println("Server started on port " + port);
			
			Socket activeSocket = gameServer.accept();
			
			if(activeSocket.isConnected()) {
				connected = true;
				System.out.println("New connection: " + activeSocket.getInetAddress() );
			}else {
				connected = false;
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
