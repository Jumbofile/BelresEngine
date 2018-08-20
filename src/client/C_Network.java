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
	//private static Socket gameClient;
	private static BufferedReader in;
	private static PrintWriter out;
	private Client gameClient = new Client();
	
	public C_Network() {
		//System.out.println("HM");
		//set up variables
		C_Vars vars = new C_Vars();
		
		//Start the client		
		gameClient = new Client();
		gameClient.start();
		//System.out.println("HM");
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
	    	public void recieved(Connection con, Object obj) {
	    		if(obj instanceof Packet){
	    			if(obj instanceof PacketConnected) {
	    				PacketConnected p1 = (PacketConnected) obj;
	    				connected = true;
	    			}else if(obj instanceof PacketDisconnect) {
	    				PacketDisconnect p2 = (PacketDisconnect) obj;
	    				connected = false;
	    			}
	    		}
	    	}
	    });
	}
	
	//send login to the server
	public void sendLogin(String username, String password) {
		//send login information
		PacketLogin p1 = new PacketLogin();
		p1.username = username;
		p1.password = password;
		System.out.println("Sent");
		gameClient.sendTCP(p1);
	}

	public boolean loginValid() {
		
		return connected;
	}
}
