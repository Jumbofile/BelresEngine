package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

//import gameSqldemo.SQLDemo.RowList;
import server.S_DBUtil;

public class S_Database implements S_IDatabase {
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Database driver");
		}
	}
	
	//decleration
	S_Main main = new S_Main();
	
	static class RowList extends ArrayList<List<String>> {
		private static final long serialVersionUID = 1L;
	}
	
	private static final String PAD =
			"                                                    " +
			"                                                    " +
			"                                                    " +
			"                                                    ";
		private static final String SEP =
			"----------------------------------------------------" +
			"----------------------------------------------------" +
			"----------------------------------------------------" +
			"----------------------------------------------------";
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
///////////////////////////////////////////////////////////////////////////////////
///////////////////// REGISTER ACCOUNT////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	public boolean registerAccount(String userName, String pass, String email) throws SQLException {

		return executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;
                PreparedStatement stmt2 = null;
                ResultSet resultSet = null;


                try {
                    // retreive username attribute from login
                    stmt = conn.prepareStatement("select username from account where username=?" // user attribute

                    );

                    // substitute the title entered by the user for the placeholder in
                    // the query
                    stmt.setString(1, userName);

                    // execute the query
                    resultSet = stmt.executeQuery();

                    if (!resultSet.next()) { /// if username doesnt exist

                        stmt2 = conn.prepareStatement( // enter username
                                "insert into account(userName, password, email, typeOf)" + "values(?, ?, ?, ?)");

                        stmt2.setString(1, userName);
                        stmt2.setString(2, pass);
                        stmt2.setString(3, email);
                        stmt2.setString(4, "1");

                        stmt2.execute();

                        int accountID = -1;
						PreparedStatement stmt3 = null;
						ResultSet resultSet2 = null;

						// retreive username attribute from login
						stmt3 = conn.prepareStatement("select login_id" // user attribute
								+ "  from account " // from account table
								+ "  where userName = ?"

						);

						stmt3.setString(1, userName);

						resultSet2 = stmt3.executeQuery();

						while(resultSet2.next()){
							accountID = resultSet2.getInt(1);;
						}

						System.out.println(accountID);

                        if (accountID != -1) {
                            //make the character
                            stmt2 = conn.prepareStatement( // enter username
                                    "insert into toons(account_id, map_id, x, y, typeOf)" + "values(?, ?, ?, ?, ?)");

                            stmt2.setInt(1, accountID);
                            stmt2.setInt(2, 1);
                            stmt2.setInt(3, -1);
                            stmt2.setInt(4, -1);
                            stmt2.setString(5, "normal");

                            stmt2.execute();

                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false; // username already exists
                    }


                }catch(Exception e){
                	e.printStackTrace();
				}
				S_DBUtil.closeQuietly(resultSet);
				S_DBUtil.closeQuietly(stmt);
				S_DBUtil.closeQuietly(stmt2);
				S_DBUtil.closeQuietly(conn);
				return false;
            }

        });
	}

	public int getAccountID(String username) throws SQLException{
        return executeTransaction(new Transaction<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                int id = -1;
                PreparedStatement stmt = null;
                ResultSet resultSet = null;

                // retreive username attribute from login
                stmt = conn.prepareStatement("select login_id" // user attribute
                        + "  from account " // from account table
                        + "  where userName = ?"

                );

				stmt.setString(1, username);

                resultSet = stmt.executeQuery();

                if (!resultSet.next()) {
                    id = resultSet.getRow();
                }

                return id;
            }
        });
	}
    public boolean accountExist (String username, String password){ ///checks if account exists
        //Checks if the user exist and if the password matches

        /*return executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet resultSetacc = null;
                String user = null;
                String pass = null;
                boolean exist = false;
                int count = 0;

                try {

                    // retreive username attribute from login
                    stmt = conn.prepareStatement(
                            "select * from account"
                    );

                    // execute the query
                    resultSetacc = stmt.executeQuery();

                    //harry = resultSet.getString("username");/// this might not work
                    while (resultSetacc.next()) {
                        user = resultSetacc.getString("userName");
                        //System.out.println("9" + username + "9");
                        //System.out.println("9" + user + "9");
                        if (username.equals(user)) {

                            pass = resultSetacc.getString("password");
                            //System.out.println(password);
                            //System.out.println(pass);
                            if (BCrypt.checkpw(password, pass)) {
                                exist = true;
                            }
                        }

                    }

                    //System.out.println(exist);
                    if (exist == true) {
                        return true;//account exists
                    } else {
                        return false;//account doesnt exists
                    }


                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    S_DBUtil.closeQuietly(resultSetacc);
                    S_DBUtil.closeQuietly(stmt);
                    S_DBUtil.closeQuietly(conn);
                }

                return false;
            }
        });*/
        return true;
    }
	
	//return a db
	public void printDB(String dbName) {
        executeTransaction(new Transaction<Boolean>() {
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                ArrayList<String> returnStmt = new ArrayList<String>();
                String database = dbName;
                PreparedStatement stmt = null;
                ResultSet resultSet = null;
                int rowCount = 0;

                try {

                    //conn = DriverManager.getConnection("org.h2.Driver");
                    if (dbName.toLowerCase().equals("account")) {
                        // retreive username attribute from login
                        stmt = conn.prepareStatement(
                                "select * from account"
                        );
                    } else {
                        S_Main.consoleWin.append("Invalid database name.\n");
                    }
                    //stmt.setString(1, database);
                    // execute the query

                    resultSet = stmt.executeQuery();

                    //harry = resultSet.getString("username");/// this might not work
                    //int i = 1;
                    ResultSetMetaData schema = resultSet.getMetaData();

                    List<String> colNames = new ArrayList<String>();
                    for (int i = 1; i <= schema.getColumnCount(); i++) {
                        colNames.add(schema.getColumnName(i));
                    }

                    RowList rowList = getRows(resultSet, schema.getColumnCount());
                    rowCount = rowList.size();

                    List<Integer> colWidths = getColumnWidths(colNames, rowList);

                    printRow(colNames, colWidths);
                    printSeparator(colWidths);
                    for (List<String> row : rowList) {
                        printRow(row, colWidths);
                    }

                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    S_DBUtil.closeQuietly(resultSet);
                    S_DBUtil.closeQuietly(stmt);
                    S_DBUtil.closeQuietly(conn);
                }
                return true;
            }
        });
	}
	
	//used for printing sql statments
	private static void printRow(List<String> row, List<Integer> colWidths) {
		for (int i = 0; i < row.size(); i++) {
			if (i > 0) {
				S_Main.consoleWin.append(" ");
			}
			String item = row.get(i);
			S_Main.consoleWin.append(PAD.substring(0, colWidths.get(i) - item.length()));
			S_Main.consoleWin.append(item);
		}
		S_Main.consoleWin.append("\n");
	}

	private static void printSeparator(List<Integer> colWidths) {
		List<String> sepRow = new ArrayList<String>();
		for (Integer w : colWidths) {
			sepRow.add(SEP.substring(0, w));
		}
		printRow(sepRow, colWidths);
	}

	private static RowList getRows(ResultSet resultSet, int numColumns) throws SQLException {
		RowList rowList = new RowList();
		while (resultSet.next()) {
			List<String> row = new ArrayList<String>();
			for (int i = 1; i <= numColumns; i++) {
				row.add(resultSet.getObject(i).toString());
			}
			rowList.add(row);
		}
		return rowList;
	}

	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new S_PersistenceException("Transaction failed", e);
		}
	}
	
	private static List<Integer> getColumnWidths(List<String> colNames, RowList rowList) {
		List<Integer> colWidths = new ArrayList<Integer>();
		for (String colName : colNames) {
			colWidths.add(colName.length());
		}
		for (List<String> row: rowList) {
			for (int i = 0; i < row.size(); i++) {
				colWidths.set(i, Math.max(colWidths.get(i), row.get(i).length()));
			}
		}
		return colWidths;
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
		Connection conn = DriverManager.getConnection("jdbc:h2:./belres.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	public void loadInitialData() { ///taken from lab06

	}
	
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;

				System.out.println("Creating account table...");
				try {
					stmt1 = conn.prepareStatement( //creates account table
						"create table account(" +
						"login_id bigint auto_increment, " +
						"userName varchar(40)," +
						"password varchar(100),"+
						"email varchar(40),"    +
						"typeOf varchar(40)"      +
						")"
					);	
					stmt1.executeUpdate();
					System.out.println("Success!");
					System.out.println("Creating toon table...");
					stmt2 = conn.prepareStatement( //creates character table
						"create table toons(" +
						"char_id bigint auto_increment, " +
						"account_id int," 		+
						"map_id int,"			+
						"x int," 				+
						"y int," 				+
						"typeOf varchar(40)"      	+
						")"
					);	
					stmt2.executeUpdate();
					System.out.println("Success!");
					System.out.println("Creating inventory table...");
					stmt3 = conn.prepareStatement( //creates inventory table
						"create table inventory (" +									
						"	account_id int," +
						"	item_id varchar(500),"+
						"   item_quantity varchar(500)" +     
						")"
					);	
					stmt3.executeUpdate();
					System.out.println("Success!");
					System.out.println("Creating item table...");
					stmt4 = conn.prepareStatement( //creates Item table
						"create table items(" +									
						"	item_id int," +
						"	name varchar(50),"+
						"	typeOf varchar(40),"+
						"	level varchar(40),"+
						"	stats varchar(500),"+
						"   item_quantity varchar(500)" +     
						")"
					);
					stmt4.executeUpdate();
					System.out.println("Success!");
					return true;
				} finally {
					S_DBUtil.closeQuietly(stmt1);
					S_DBUtil.closeQuietly(stmt2);
					S_DBUtil.closeQuietly(stmt3);
					S_DBUtil.closeQuietly(stmt4);
				}
			}
		});
	}

	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		S_Database db = new S_Database();
		db.createTables();
		

		db.loadInitialData();
		
		System.out.println("Success!");
	}
}