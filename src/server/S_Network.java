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
		String line;
		while (true) {
			try {
				//Recieve line from client
				line = brinp.readLine();
				
				//LOGOUT WILL GO HERE
				if ((line == null) || line.equalsIgnoreCase("QUIT")) {
					socket.close();
					return;
				} else {
					out.writeBytes(line + "\n\r");
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
