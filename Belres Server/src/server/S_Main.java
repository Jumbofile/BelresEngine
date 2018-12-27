package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.mindrot.jbcrypt.BCrypt;
import gameSqldemo.SQLDemo;

public class S_Main {
	
	public static JTextArea consoleWin = new JTextArea();
	public static boolean sqlOn = false;
	
	public static void main(String[] args) {
		//Initialize the server
		S_Vars vars = new S_Vars();
		S_Database db = new S_Database();
		    
		//Start server gui
		JFrame frame = new JFrame("Belres Server");
		JTextField consoleBox = new JTextField();
		JPanel panel = new JPanel();
		//panel.setBackground(Color.GRAY);
		JScrollPane scroll = new JScrollPane (consoleWin);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(810, 505);
		frame.setResizable(false);
		//consoleWin.setBackground(Color.DARK_GRAY);
		consoleWin.setEditable(false);
		consoleWin.setFont(new Font("Consolas", Font.PLAIN, 12));  // make a new font object);
		scroll.setBounds(10,10,775,420);
		consoleWin.setLineWrap(true);
		
		//consoleBox.setBackground(Color.BLUE);
		consoleBox.setBounds(10,432,775,24);
		frame.getContentPane().add(scroll);
		
		//is enter hit
        Action action = new AbstractAction()
        {
            /**
			 * eclipse made me do this
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void actionPerformed(ActionEvent e)
            {
                consoleWin.append(consoleBox.getText() + "\n");
                
                /*S
                 * CONSOLE COMMANDS
                 */
                
                //Help command "/help"
                if(consoleBox.getText().toLowerCase().startsWith("/help")) {
                	consoleWin.append("Available Commands: \n");
                	consoleWin.append("/addnewuser \n");
                	consoleWin.append("/sql \n");
                }
                
                //register a new user with format /addnewuser username,password,email
                if(consoleBox.getText().toLowerCase().startsWith("/addnewuser")) {
                	try {
	                	String user = consoleBox.getText().substring(
	                		consoleBox.getText().toLowerCase().indexOf(' ') + 1, consoleBox.getText().indexOf(','));
	                	String pass  = consoleBox.getText().substring(
	                			consoleBox.getText().indexOf(",") + 1, consoleBox.getText().indexOf(',', 
	                					consoleBox.getText().indexOf(',') + 1));
	                	String email = consoleBox.getText().substring(consoleBox.getText().indexOf(',', 
	                					consoleBox.getText().indexOf(',') + 1) + 1);
	                	//hash that pass!
	                	//consoleWin.append(pass + "\n");
	                	String enPass = BCrypt.hashpw(pass, BCrypt.gensalt());
	                	//consoleWin.append(user+ " " + pass + " " + email + "\n");
	                	//send to DB
						boolean accountMade = db.registerAccount(user, enPass, email);
	                	
						//was the account made?
						if(accountMade) {
							consoleWin.append("Account created successfully.\n");
							pass = "";
						}else {
							consoleWin.append("Account creation failed.\n");
							pass = "";
						}
                	}catch(Exception x){
                		consoleWin.append(x.toString() + "\n");
                		consoleWin.append("INVALID STATEMENT: use format; /addnewuser username,password,email\n");
                	}   	
                }
                
                //sql commands: /sql getdb:dbname
                if(consoleBox.getText().toLowerCase().startsWith("/sql")) {
                	//while(!consoleBox.getText().equals("quit")){
                		//SQLDemo demo = new SQLDemo();
					String statement = consoleBox.getText().substring(consoleBox.getText().indexOf(' '));
						try {
							gameSqldemo.SQLDemo.accessDemo(statement);
						}catch(Exception e1){
							consoleWin.append("Invalid statement \n");
						}
					//}
                	/*try {
                		String command = consoleBox.getText().substring(
                				consoleBox.getText().toLowerCase().indexOf(' ') + 1, consoleBox.getText().indexOf(':'));
                		if(command.equals("getdb")) {
                			String dbName = consoleBox.getText().substring(consoleBox.getText().indexOf(':') + 1);
                			
                			db.printDB(dbName);
                			//for(int i = 0; i < results.size(); i++) {
                			//	consoleWin.append(results.get(i));
                			//}
                		}
                	}catch(Exception x){
                		consoleWin.append(x.toString() + "\n");
                		consoleWin.append("INVALID STATEMENT: use format; /sql COMMAND:dbname\n");
                	}   	*/
                }
                consoleBox.setText("");
                
            }
        };
        
        //jframe stuff
        consoleBox.addActionListener( action );
		frame.getContentPane().add(consoleBox);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		
		
		//Start server networking
		S_Network network = new S_Network(consoleWin);
		try {
			network.run();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
    }
	
	
}

