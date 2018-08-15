package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class S_Main {
	
	public static JTextArea consoleWin = new JTextArea();

	public static void main(String[] args) {
		//Initialize the server
		S_Vars vars = new S_Vars();
		
		//Start server gui
		JFrame frame = new JFrame("Belres Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		frame.setResizable(false);;
		consoleWin.setEditable(false);
		consoleWin.setBounds(10,10,200,60);
		consoleWin.setLineWrap(true);
		frame.add(consoleWin);
		frame.setVisible(true);
		//Start server networking
		ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(vars.PORT);
            consoleWin.append("Server started on port " + vars.PORT + ".\n");
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
            new S_Network(socket, consoleWin).run();
        }
    }
	
	
}

