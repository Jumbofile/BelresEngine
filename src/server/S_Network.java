package server;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import javax.swing.JTextArea;

import org.mindrot.jbcrypt.BCrypt;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import packets.Packet;
import packets.PacketChat;
import packets.PacketConnected;
import packets.PacketDisconnect;
import packets.PacketLogin;


public class S_Network {
	protected Socket socket;
	private static PrintWriter out;
	private BufferedReader brinp = null;
	private JTextArea console;
	private S_DerbyDatabase db;
	private S_Vars vars;
	
	public S_Network(JTextArea console) {
		this.console = console;
		db = new S_DerbyDatabase();
		vars = new S_Vars();
	}

	private static HashMap<String, Connection> clients = new HashMap<String, Connection>();
	
	public void run() throws IOException {
			Server server = new Server();
		    server.start();
		    server.bind(vars.portTCP, vars.portUDP);
		    console.append("Server started on TCP port " + vars.portTCP + " and UDP port " + vars.portUDP + ".\n");
		    
		    server.addListener(new Listener() {
		    	public void recieved(Connection con, Object obj) {
		    		if(obj instanceof Packet) {
			    		if (obj instanceof PacketLogin) {
			    			PacketLogin p1 = (PacketLogin) obj;
			    			clients.put(p1.username, con);
			    			server.sendToAllExceptTCP(con.getID(), p1);
			    		}else if(obj instanceof PacketDisconnect) {
			    			PacketDisconnect p2 = (PacketDisconnect) obj;
			    			clients.remove(p2.clientName);
			    			server.sendToAllExceptTCP(clients.get(p2.clientName).getID(), p2);
			    		}else if(obj instanceof PacketChat) {
			    			PacketChat p3 = (PacketChat) obj;
			    			server.sendToAllTCP(p3);
			    		}
		    		}
		    	}
		    });
		    
		    //Register classes
		    server.getKryo().register(Packet.class);
		    server.getKryo().register(PacketChat.class);
		    server.getKryo().register(PacketConnected.class);
		    server.getKryo().register(PacketDisconnect.class);
		    server.getKryo().register(PacketLogin.class);

		    
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
