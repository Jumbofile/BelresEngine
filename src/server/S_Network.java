package server;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

import org.mindrot.jbcrypt.BCrypt;


public class S_Network {
	protected Socket socket;
	private static PrintWriter out;
	private BufferedReader brinp = null;
	private JTextArea console;
	private S_DerbyDatabase db;
	
	public S_Network(Socket clientSocket, JTextArea console) {
		this.socket = clientSocket;
		this.console = console;
		db = new S_DerbyDatabase();
	}

	public void run() {
		InputStream inp = null;
		
		//DataOutputStream out = null;
		try {
			inp = socket.getInputStream();
			brinp = new BufferedReader(new InputStreamReader(inp));
			//out = new DataOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			return;
		}
		console.append("Client " + socket.getInetAddress() + " connected.\n" );
		//System.out.println("Client " + socket.getInetAddress() + " connected." );
		String line;
		while (true) {
			try {
				int packetID = 0;
				
				/*
				 * GARBAGE TCP LOGIN START
				 */
				//Recieve line from client
				line = brinp.readLine();
				//System.out.println(line);
				try {
					packetID = line.indexOf("<");
					
					if(line.substring(0, packetID).equals("login")) {
						recieveLogin(line, packetID);
					}
					
				}catch(Exception e) {
					//ignore it
				}
				/*
				 * GARBAGE TCP END
				 */
				
				//LOGOUT WILL GO HERE
				if ((line == null) || line.equalsIgnoreCase("QUIT")) {
					socket.close();
					return;
				} else {
					
				}
			} catch (IOException e) {
				try {
					console.append("Client " + socket.getInetAddress() + " disconnected.\n");
					socket.close();
				}catch (Exception x) {
					e.printStackTrace();
				}
				
				return;
			}
		}
	}
	
	//Process login, very slow but its tcp so who really cares
	private void recieveLogin(String message, int id) {
		int lineBreak = message.indexOf(",");
		String username = message.substring(id + 1, lineBreak);
		String password = message.substring(lineBreak + 1);
		try {
			//validate account TEMP
			/*if(username.toLowerCase().equals("greg")) { 
				if(BCrypt.checkpw("pass", password)){
					sendLoginStatus(true);
				}else {
					sendLoginStatus(false);
				}
			}else {
				sendLoginStatus(false);
			}*/
			
			//Sends the user and pass to the db and returns if it is valid or not
			sendLoginStatus(db.accountExist(username, password)); 
		}catch(Exception e) {
			
		}
		
	}
	
	private void sendLoginStatus(Boolean b) throws IOException {
		out = new PrintWriter(socket.getOutputStream(), true);
		//console.append(b.toString() + "\n");
		String packet = b.toString();
		//out.
		out.println(packet);
	}
}
