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
/////////////////////ADD AREA////////////////////////////////////
/////////////////////////////////////////////////////////////////////////	
public int createArea(String name, String para, ArrayList<String> options) throws SQLException {
		
		int area_id = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		PreparedStatement stmt5 = null;
		PreparedStatement stmt6 = null;
		ResultSet resultSet = null;
		ResultSet resultSet2 = null;
		ResultSet resultSet3 = null;
		ResultSet resultSet4 = null;
		ResultSet resultSet5 = null;
		
		//Connects to database
		conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
				
		//Takes information from the editor jsp and inserts it to the database for area
				try {
						stmt2 = conn.prepareStatement( // enter username
								"insert into area(areaName, para, Opt1, Opt2, Opt3, Opt4, Opt5, Opt6, areaLink1, areaLink2, areaLink3, areaLink4, areaLink5, areaLink6, areaPicture)"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
						);				
								
						stmt2.setString(1, name);
						stmt2.setString(2, para);
						for(int i = 0; i < 13; i++){
							stmt2.setString(i + 3, options.get(i));
						}
						
								
						stmt2.execute();
						
						stmt3 = conn.prepareStatement(
								"select area.area_id from area where areaName = (?) and para = (?)"
								
						);
					
						stmt3.setString(1, name);
						stmt3.setString(2, para);
						
						
						resultSet = stmt3.executeQuery();
						if(resultSet.next()) {
							area_id = resultSet.getInt(1);
						}
						
						
					
					
				
					
					
				} finally {
					S_DBUtil.closeQuietly(resultSet);
					S_DBUtil.closeQuietly(resultSet2);
					S_DBUtil.closeQuietly(resultSet3);
					S_DBUtil.closeQuietly(resultSet4);
					S_DBUtil.closeQuietly(resultSet5);
					S_DBUtil.closeQuietly(stmt);
					S_DBUtil.closeQuietly(stmt2);
					S_DBUtil.closeQuietly(stmt3);
					S_DBUtil.closeQuietly(stmt4);
					S_DBUtil.closeQuietly(stmt5);
					S_DBUtil.closeQuietly(stmt6);
					S_DBUtil.closeQuietly(conn);
				}
			return area_id;
	}	
///////////////////////////////////////////////////////////////////////////////////	
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
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				PreparedStatement stmt6 = null;
				PreparedStatement stmt7 = null;
				
				
				try {
					stmt1 = conn.prepareStatement( //creates login table
						"create table login (" +
						"	login_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	userName varchar(40)," +
						"	password varchar(100),"+
						"   email varchar(40),"    +
						"   type varchar(40),"     +
						"   area varchar(40)"      +
						")"
					);	
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement( // creates user inventory table
							"create table userInventory (" +
							"	userInventory_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	itemName varchar(40)," +
							"	itemType varchar(40)," +
							"   size integer"     +
							")"
						);	
						stmt2.executeUpdate();
						
					stmt3 = conn.prepareStatement( //creates house inventory 
							"create table houseItems (" +
							"	houseInventory_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	itemName varchar(40)," +
							"	itemType varchar(40)," +
							"   size integer"     +
							")"
						);	
						stmt3.executeUpdate();	
						
					stmt4 = conn.prepareStatement( //creates house inventory 
							"create table area (" +
							"	area_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	areaName varchar(40)," +
							"	para varchar(1000)," +
							"   Opt1 varchar(40)," +
							"   Opt2 varchar(40)," +
							"   Opt3 varchar(40)," +
							"   Opt4 varchar(40)," +
							"   Opt5 varchar(40)," +
							"   Opt6 varchar(40)," +
							"	areaLink1 varchar(40)," +
							"   areaLink2 varchar(40)," +
							"   areaLink3 varchar(40)," +
							"   areaLink4 varchar(40)," +
							"   areaLink5 varchar(40)," +
							"   areaLink6 varchar(40)," +
							"   areaPicture varchar(40)" +
							")"
						);	
					stmt4.executeUpdate();
					
					stmt5 = conn.prepareStatement( //creates house inventory 
							"create table linearArea (" +
							"	area_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	areaName varchar(40),"
							+ " para varchar(1000)" +
							
							")"
						);	
					stmt5.executeUpdate();
					
					stmt6 = conn.prepareStatement( //creates playerLocation table
							"create table playerLocation (" +
							"	location_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	area varchar(40)" +
							")"
						);	
					stmt6.executeUpdate();
					
					stmt7 = conn.prepareStatement( //creates house inventory 
							"create table health (" +
							"	health_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +									
							"	health varchar(40)" +
							")"
						);	
					stmt7.executeUpdate();
					
					
					
					return true;
				} finally {
					S_DBUtil.closeQuietly(stmt1);
					S_DBUtil.closeQuietly(stmt2);
					S_DBUtil.closeQuietly(stmt3);
					S_DBUtil.closeQuietly(stmt4);
					S_DBUtil.closeQuietly(stmt5);
					S_DBUtil.closeQuietly(stmt6);
					S_DBUtil.closeQuietly(stmt7);
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