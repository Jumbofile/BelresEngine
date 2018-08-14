package server;

import java.io.*;
import java.net.*;


public class S_Network {
	protected Socket socket;
	
	public S_Network(Socket clientSocket) {
		this.socket = clientSocket;
	}

	public void run() {
		InputStream inp = null;
		BufferedReader brinp = null;
		DataOutputStream out = null;
		try {
			inp = socket.getInputStream();
			brinp = new BufferedReader(new InputStreamReader(inp));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			return;
		}
		System.out.println("Client " + socket.getInetAddress() + " connected." );
		String line;
		while (true) {
			try {
				int packetID = 0;
				//Recieve line from client
				line = brinp.readLine();
				try {
					packetID = line.indexOf("<");
				}catch(Exception e) {
					System.out.println("Invalid packet.");
				}
				
				if(line.substring(0, packetID).equals("login")) {
					recieveLogin(line, packetID);
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

	private void recieveLogin(String message, int id) {
		int lineBreak = message.indexOf(",");
		String username = message.substring(id + 1, lineBreak);
		String password = message.substring(lineBreak + 1);
		
		System.out.print(username + ", " + password);
		
	}
}
