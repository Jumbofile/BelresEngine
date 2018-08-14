package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class S_Main {

	public static void main(String[] args) {
		//Initialize the server
		S_Vars vars = new S_Vars();
		
		ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(vars.PORT);
            System.out.println("Server started on port " + vars.PORT + ".");
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new S_Network(socket).run();
        }
    }
	
	
}

