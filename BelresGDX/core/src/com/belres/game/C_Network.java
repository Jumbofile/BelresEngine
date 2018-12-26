package com.belres.game;

import java.io.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.belres.packets.Packet;
import com.belres.packets.PacketChat;
import com.belres.packets.PacketConnected;
import com.belres.packets.PacketDisconnect;
import com.belres.packets.PacketLogin;

public class C_Network {
	private boolean connected = false;
	public String usernameClient = null;
	private boolean loginValid = false;
	private Client gameClient = new Client();
	private C_Player player;

	public C_Network() {
		//set up variables
		C_Vars vars = new C_Vars();
		
		//Start the client		
		gameClient = new Client();
		gameClient.start();
		
		try {
			gameClient.connect(5000, vars.IP, vars.portTCP, vars.portUDP);
			connected = true;
		} catch (IOException e) {
			connected = false;
			
		}
		
		//Register classes
	    gameClient.getKryo().register(Packet.class);
	    gameClient.getKryo().register(PacketChat.class);
	    gameClient.getKryo().register(PacketConnected.class);
	    gameClient.getKryo().register(PacketDisconnect.class);
	    gameClient.getKryo().register(PacketLogin.class);

	    //Packet listener
	    gameClient.addListener(new Listener(){
	    	public void received (Connection connection, Object object) { 
	    		if(object instanceof Packet){

	    		    //If the packet received was a Connected Packet
	    			if(object instanceof PacketConnected) {
	    				PacketConnected p1 = (PacketConnected) object;
	    				loginValid = p1.status;
                        player = new C_Player(usernameClient);

	    			}
                    //If the packet received was a Disconnected Packet
	    			else if(object instanceof PacketDisconnect) {
	    				PacketDisconnect p2 = (PacketDisconnect) object;
	    				loginValid = false;
	    			}
	    		}
	    	}
	    });
	}
	
	//send login to the server
	public void sendLogin(String username, String password) {
		//send login information
		PacketLogin p1 = new PacketLogin();
		usernameClient = username;
		p1.username = username;
		p1.password = password;
		//System.out.println("Sent");
		gameClient.sendTCP(p1);
	}
	
	//send login to the server
		public void sendDisconnect() {
			//send login information
			if(usernameClient.equals(null)) {
				
			}else {
				PacketDisconnect p1 = new PacketDisconnect();
				p1.clientName = usernameClient;
				gameClient.sendTCP(p1);
			}
		}

	public boolean loginValid() {
		return loginValid;
	}

	public boolean getConnected(){
		return connected;
	}
}
