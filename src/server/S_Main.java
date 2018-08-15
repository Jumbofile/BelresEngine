package server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

public class S_Main {
	
	public static JTextArea consoleWin = new JTextArea();

	public static void main(String[] args) {
		//Initialize the server
		S_Vars vars = new S_Vars();
		S_DerbyDatabase db = new S_DerbyDatabase();
		       
		//Start server gui
		JFrame frame = new JFrame("Belres Server");
		JTextField consoleBox = new JTextField();
		JPanel panel = new JPanel();
		JScrollPane scroll = new JScrollPane (consoleWin);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 295);
		frame.setResizable(false);;
		consoleWin.setEditable(false);
		scroll.setBounds(10,10,475,220);
		consoleWin.setLineWrap(true);
		
		//consoleBox.setBackground(Color.blue);
		consoleBox.setBounds(10,232,475,24);
		frame.getContentPane().add(scroll);
		
		//is enter hit
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                consoleWin.append(consoleBox.getText() + "\n");
                
                //register a new user with format /addnewuser username,password,email
                if(consoleBox.getText().toLowerCase().startsWith("/addnewuser")) {
                	try {
	                	String user = consoleBox.getText().substring(
	                		consoleBox.getText().toLowerCase().indexOf(' ') + 1, consoleBox.getText().indexOf(','));
	                	String pass  = consoleBox.getText().substring(
	                			consoleBox.getText().indexOf(",") + 1, consoleBox.getText().indexOf(',', 
	                					consoleBox.getText().indexOf(',') + 1));
	                	String email = consoleBox.getText().substring(consoleBox.getText().indexOf(',', 
	                					consoleBox.getText().indexOf(',') + 1));
	                	//hash that pass!
	                	String enPass = BCrypt.hashpw(pass, BCrypt.gensalt());
	                	
	                	//send to DB
						boolean accountMade = db.registerAccount(user, enPass, email);
	                	
						//was the account made?
						if(accountMade) {
							consoleWin.append("Account created successfully\n");
						}else {
							consoleWin.append("Account creation failed.\n");
						}
                	}catch(Exception x){
                		consoleWin.append(x.toString() + "\n");
                		consoleWin.append("INVALID STATEMENT: use format; /addnewuser username,password,email\n");
                	}
                	
                }
                consoleBox.setText("");
                
            }
        };
        
        consoleBox.addActionListener( action );
		frame.getContentPane().add(consoleBox);
		frame.getContentPane().add(panel);
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

