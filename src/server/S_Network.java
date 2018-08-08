package server;

import java.io.*;
import java.net.*;

public class S_Network {
	private ServerSocket gameServer;
	public boolean connected = false;
	private boolean start = false;
	
	public S_Network(int port) {
		try {
			gameServer = new ServerSocket(port);
			
			System.out.println("Server started on port " + port);
			
			start = true;
			while(start) {
				//Server main loop
				Socket activeSocket = gameServer.accept();
				
				if(activeSocket.isConnected()) {
					connected = true;
					System.out.println("New connection: " + activeSocket.getInetAddress() );
				}else {
					connected = false;
				}
				
				if(connected = true && activeSocket.isConnected()) {
					System.out.println(activeSocket.getInetAddress() + " disconnected" );
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
