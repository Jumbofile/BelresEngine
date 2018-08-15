package server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import entity.Item;

public interface S_IDatabase {
	//boolean registerAccount(String username, String password, String email) throws SQLException;
	
	//int createArea(String name, String para, ArrayList<String> options) throws SQLException;
	boolean accountExist(String username, String password);
	boolean registerAccount(String userName, String pass, String email) throws SQLException;
	//ArrayList<String> getArea(String id) throws SQLException;
	
	//void insertPlayerLocation(String area);
	
	//String checkAccess(String username);

}