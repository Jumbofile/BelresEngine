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
	private JTextArea console;
	private S_DerbyDatabase db;
	private S_Vars vars;
	private Server server;
	
	public S_Network(JTextArea console) {
		this.console = console;
		db = new S_DerbyDatabase();
		vars = new S_Vars();
	}

	private static HashMap<String, Connection> clients = new HashMap<String, Connection>();
	
	public void run() throws IOException {
			server = new Server();
		    server.start();
		    server.bind(vars.portTCP, vars.portUDP);
		    console.append("Server started on TCP port " + vars.portTCP + " and UDP port " + vars.portUDP + ".\n");
		    
		    server.addListener(new Listener() {
		    	public void received (Connection connection, Object object) {
		    		//System.out.println("WHAT");
		    		if(object instanceof Packet) {
		    			console.append("Got a packet\n");
			    		if (object instanceof PacketLogin) {
			    			PacketLogin p1 = (PacketLogin) object;
			    			
			    			//does account exist and has valid information
			    			boolean valid = db.accountExist(p1.username, p1.password);
			    			try {
								sendLoginStatus(valid, connection);
								//if its valid add to the map
				    			if(valid) {
				    				console.append("Client " + connection.getID() + " has connected.");
					    			clients.put(p1.username, connection);
					    			server.sendToAllExceptTCP(connection.getID(), p1);
				    			}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    		}else if(object instanceof PacketDisconnect) {
			    			PacketDisconnect p2 = (PacketDisconnect) object;
			    			clients.remove(p2.clientName);
			    			server.sendToAllExceptTCP(clients.get(p2.clientName).getID(), p2);
			    		}else if(object instanceof PacketChat) {
			    			PacketChat p3 = (PacketChat) object;
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
	private void sendLoginStatus(Boolean b, Connection con) throws IOException {	
		//send validity to the client
		PacketConnected p1 = new PacketConnected();
		p1.status = b;
		server.sendToTCP(con.getID(), p1);
			//server.sed
	}
}
