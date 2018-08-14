package server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.*;

public class S_Network {
	protected Socket socket;
	private InputStream inp = null;
	private BufferedInputStream bin = null;
	private DataOutputStream out = null;
	
	/*
	 * PACKET IDENTIFICATION
	 * 
	 * 0 : Login
	 */
	public S_Network(Socket clientSocket) {
		this.socket = clientSocket;
	}

	public void run() {
		try {
			inp = socket.getInputStream();
			bin = new BufferedInputStream(inp);
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			return;
		}
		System.out.println("Client " + socket.getInetAddress() + " connected." );
		String line = null;
		int packetID;
		while (true) {
			try {
				packetID = bin.read();
				
				if(packetID == 0) {
					recieveLogin(line);
				}
				//LOGOUT WILL GO HERE
				if ((line == null) || line.equalsIgnoreCase("QUIT")) {
					socket.close();
					return;
				} else {
					out.writeBytes(line + "\n\r");
					out.flush();
				}
			} catch (IOException e) {
				try {
					System.out.println("Client " + socket.getInetAddress() + " disconnected.");
					socket.close();
				}catch (Exception x) {
					e.printStackTrace();
				}
				
				return;
			}
		}
	}

	private void recieveLogin(String message) {
		BufferedReader r = new BufferedReader(
		        new InputStreamReader(bin, StandardCharsets.UTF_8));
		
		//int lineBreak = message.indexOf(",");
		String username = null;
		String password = null;
		try {
			System.out.println(bin.available());
			username = r.readLine();
			password = r.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.print(username + ", " + password);
		
	}
}
