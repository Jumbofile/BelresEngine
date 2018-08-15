package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import server.S_DBUtil;

public class S_DerbyDatabase implements S_IDatabase { /// most of the gamePersist package taken from Lab06 ----CITING
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
///////////////////////////////////////////////////////////////////////////////////
///////////////////// REGISTER ACCOUNT////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	public boolean registerAccount(String userName, String pass, String email) throws SQLException {

		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet resultSet = null;
		

		conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");

		try {
			// retreive username attribute from login
			stmt = conn.prepareStatement("select userName " // user attribute
					+ "  from account " // from account table
					+ "  where userName = ?"

			);

			// substitute the title entered by the user for the placeholder in
			// the query
			stmt.setString(1, userName);

			// execute the query
			resultSet = stmt.executeQuery();

			if (!resultSet.next()) { /// if username doesnt exist

				stmt2 = conn.prepareStatement( // enter username
						"insert into account(userName, password, email, type)" + "values(?, ?, ?, ?)");

				stmt2.setString(1, userName);
				stmt2.setString(2, pass);
				stmt2.setString(3, email);
				stmt2.setString(4, "1");

				stmt2.execute();

				return true;
			} else {
				return false; // username already exists
			}

		} finally {
			S_DBUtil.closeQuietly(resultSet);
			S_DBUtil.closeQuietly(stmt);
			S_DBUtil.closeQuietly(stmt2);	
			S_DBUtil.closeQuietly(conn);
		}
	}

	public boolean accountExist(String username, String password){ ///checks if account exists
		//Checks if the user exist and if the password matches
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String user = null;
		String pass = null;
		boolean exist = false;
		int count = 0;
		
		try {
			
			conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
			// retreive username attribute from login
			stmt = conn.prepareStatement(
					"select * from account"
			);		

			// execute the query
			resultSet = stmt.executeQuery();
			
			//harry = resultSet.getString("username");/// this might not work 
			while(resultSet.next()) {
				user = resultSet.getString("userName");
				pass = resultSet.getString("password");
				if(username.equals(user) && password.equals(pass)) {
					exist = true;
				}
				
			}
			
			//System.out.println(exist);
			if(exist == true) { 
				return true;//account exists				
			}
			else{
				return false;//account doesnt exists		
			}
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		finally {
			S_DBUtil.closeQuietly(resultSet);
			S_DBUtil.closeQuietly(stmt);
			S_DBUtil.closeQuietly(conn);
		}
		return false;
		
	}
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new S_PersistenceException("Transaction failed", e);
		}
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			S_DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	public void loadInitialData() { ///taken from lab06
		/*executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Item> houseItems;
				List<Area> areaList;
				List<LinearArea> linearAreaList;
				List<Health> healthList;
				
				try {
					houseItems = InitialData.getHouseItems();
					areaList = InitialData.getArea();
					linearAreaList = InitialData.getLinearArea();
					healthList = InitialData.getHealth();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertHouseItem = null;
				PreparedStatement insertArea = null;
				PreparedStatement insertLinearArea = null;
				PreparedStatement insertHealth = null;

				
				try {
					// populate houseItems table 
					insertHouseItem = conn.prepareStatement("insert into houseItems (itemName, itemType, size) values (?, ?, ?)");
					for (Item item : houseItems) {
//						insertAuthor.setInt(1, author.getAuthorId());	// auto-generated primary key, don't insert this
						insertHouseItem.setString(1, item.getName());
						insertHouseItem.setString(2, item.getItemType());
						insertHouseItem.setInt(3, item.getSize());
						insertHouseItem.addBatch();
					}
					insertHouseItem.executeBatch();
					
					insertArea = conn.prepareStatement("insert into area(areaName, para, Opt1, Opt2, Opt3, Opt4, Opt5, Opt6, areaLink1, areaLink2, areaLink3, areaLink4, areaLink5, areaLink6, areaPicture) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for (Area area : areaList) {
						insertArea.setString(1, area.getName());
						insertArea.setString(2, area.getPara());
						insertArea.setString(3, area.getOpt1());
						insertArea.setString(4, area.getOpt2());
						insertArea.setString(5, area.getOpt3());
						insertArea.setString(6, area.getOpt4());
						insertArea.setString(7, area.getOpt5());
						insertArea.setString(8, area.getOpt6());
						insertArea.setString(9, area.getLnk1());
						insertArea.setString(10, area.getLnk2());
						insertArea.setString(11, area.getLnk3());
						insertArea.setString(12, area.getLnk4());
						insertArea.setString(13, area.getLnk5());
						insertArea.setString(14, area.getLnk6());
						insertArea.setString(15, area.getPicture());
						insertArea.addBatch();
						
					}
					
					insertArea.executeBatch();
					
					insertLinearArea = conn.prepareStatement("insert into linearArea (areaName, para) values (?, ?)");
					for (LinearArea linearArea : linearAreaList) {
						insertLinearArea.setString(1, linearArea.getName());
						insertLinearArea.setString(2, linearArea.getPara());
						insertLinearArea.addBatch();
					}
					
					insertLinearArea.executeBatch();
					
					insertHealth = conn.prepareStatement("insert into health (health) values (?)");
					for (Health health : healthList) {
						insertHealth.setString(1, health.getHealth());
						insertHealth.addBatch();
					}
					
					insertHealth.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertHouseItem);
					DBUtil.closeQuietly(insertArea);
					DBUtil.closeQuietly(insertLinearArea);
					DBUtil.closeQuietly(insertHealth);
				}
			}
		});*/
	}
	
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
								
				try {
					stmt1 = conn.prepareStatement( //creates account table
						"create table account (" +
						"	login_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	userName varchar(40)," +
						"	password varchar(100),"+
						"   email varchar(40),"    +
						"   type varchar(40)"      +
						")"
					);	
					stmt1.executeUpdate();
						
					return true;
				} finally {
					S_DBUtil.closeQuietly(stmt1);
				}
			}
		});
	}
	

	
	
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		S_DerbyDatabase db = new S_DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Success!");
	}
}