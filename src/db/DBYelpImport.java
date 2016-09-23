package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Create DB tables in MySQL.
 *
 */
public class DBYelpImport {

	// write MySQL code into database, run as java application
	
	public static void main(String[] args) {
		try {
			// Ensure the driver is imported.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = null;

			try {
				System.out.println("Connecting to \n" + DBUtil.URL);
				conn = DriverManager.getConnection(DBUtil.URL);
			} catch (SQLException e) {
				System.out.println("SQLException " + e.getMessage());
				System.out.println("SQLState " + e.getSQLState());
				System.out.println("VendorError " + e.getErrorCode());
			}
			if (conn == null) {
				return;
			}
			// Step 1 Drop tables in case they exist.
			Statement stmt = conn.createStatement();

			String sql = "DROP TABLE IF EXISTS history";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS restaurants";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS users";
			stmt.executeUpdate(sql);

			System.out.println("DBYelpImport: import is done successfully.");

			/*
			 * step 2: create 3 tables If write MySQL code in Eclipse, we have
			 * to set up a String sql and write code with "String1" + "String2"
			 * type.
			 */
			sql = "CREATE TABLE restaurants " + "(business_id VARCHAR(255) NOT NULL, " + " name VARCHAR(255), "
					+ "categories VARCHAR(255), " + "city VARCHAR(255), " + "state VARCHAR(255), " + "stars FLOAT,"
					+ "full_address VARCHAR(255), " + "latitude FLOAT, " + " longitude FLOAT, "
					+ "image_url VARCHAR(255)," + "url VARCHAR(255)," + " PRIMARY KEY ( business_id ))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE users " + "(user_id VARCHAR(255) NOT NULL, " + " password VARCHAR(255) NOT NULL, "
					+ " first_name VARCHAR(255), last_name VARCHAR(255), "  + " PRIMARY KEY ( user_id ))" ;
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE history " + "(visit_history_id bigint(20) unsigned NOT NULL AUTO_INCREMENT, "
					+ " user_id VARCHAR(255) NOT NULL , " + " business_id VARCHAR(255) NOT NULL, "
					+ " last_visited_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
					+ " PRIMARY KEY (visit_history_id),"
					+ "FOREIGN KEY (business_id) REFERENCES restaurants(business_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id))";
			stmt.executeUpdate(sql);

			// HW_Q2, insert four restaurants:
			sql = "INSERT INTO restaurants (business_id, name, categories) "
					+ "VALUES (\"1234\", \"xiangweilou\", \"Japanese, Korean, Chinese\")";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO restaurants (business_id, name, categories) "
					+ "VALUES (\"2234\", \"Beijingyuan\", \"Chinese, Japanese\")";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO restaurants (business_id, name, categories) "
					+ "VALUES (\"3234\", \"Ludingji\", \"Chinese\")";
			stmt.executeUpdate(sql);

			sql = "INSERT INTO restaurants (business_id, name, categories) "
					+ "VALUES (\"4234\", \"Pizzahot\", \"Korean\")";
			stmt.executeUpdate(sql);

			// then filter the "Chinese" restaurants
			sql = "DELETE FROM restaurants WHERE categories REGEXP 'Chinese'";
			stmt.executeUpdate(sql);

			// HW_Q3: return the unique first_name; 不区分大小写！
			// 每句话后面都要stmt.executeUpdate(sql) 一次！！！
			
			sql = "INSERT INTO users " + "VALUES (\"1111\", \"3229c1097c00d497a0fd282d586be050\", \"John\", \"Smith\")";
			stmt.executeUpdate(sql);
			System.out.println("\nDBYelpImport executing query:\n" + sql);

			sql = "INSERT INTO users " + "VALUES (\"2222\", \"3229d1097c00d497a0fd282d586be050\", \"John\", \"King\")";
			stmt.executeUpdate(sql);
			System.out.println("\nDBYelpImport executing query:\n" + sql);

			/*  these code need to be written in PHPmyadmin_SQL, not eclipse since no return value
			sql = "SELECT DISTINCT first_name FROM users LIMIT 2";
				Though limit == 2, we can only get one result;
			*/
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}