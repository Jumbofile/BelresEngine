package client;

import java.io.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import packets.Packet;
import packets.PacketChat;
import packets.PacketConnected;
import packets.PacketDisconnect;
import packets.PacketLogin;

public class C_Network {
	public boolean connected = false;
	public String usernameClient = null;
	private boolean loginValid = false;
	private Client gameClient = new Client();
	
	public C_Network() {
		//System.out.println("HM");
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
	    
	    gameClient.addListener(new Listener(){
	    	public void received (Connection connection, Object object) { 
	    		if(object instanceof Packet){
	    			if(object instanceof PacketConnected) {
	    				PacketConnected p1 = (PacketConnected) object;
	    				loginValid = p1.status;
	    				
	    			}else if(object instanceof PacketDisconnect) {
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
}
