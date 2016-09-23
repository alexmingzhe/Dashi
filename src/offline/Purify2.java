
package offline;

import java.io.BufferedReader;
import java.io.FileReader;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import db.DBUtil;

public class Purify2 {
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(DBUtil.DB_NAME);
		// The name of the file to open.
		// address starts from /Users
		String fileName = "/Users/Alex/Desktop/group.csv";

		String line = null;

		try {
			// java IO type
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(",");
				// split the line text into several strings, and append them
				// into db orderly
				db.getCollection("counting")
						.insertOne(new Document().append("Email", values[0]).append("LastName", values[1])
								.append("FirstName", values[2]).append("Username", values[3])
								.append("Password", values[4]));
			}
			System.out.println("Import Done!");
			bufferedReader.close();
			mongoClient.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
