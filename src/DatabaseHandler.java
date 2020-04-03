import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler {

    private Connection connection = null;
    private Statement statement = null;


    /**
     * Method to connect to database, sets up a connection usable by other methods, to send SQL statements to
     */
    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(System.getenv("host"));
            System.out.println("Creating connection " + connection);
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method creates a new row in the database with the parameters sent to it
     * @param name The name that will be sent to database
     * @param email The email that will be sent to database
     * @param homepage The homepage that will be sent to databse
     * @param comment The comment that will be sent to database
     * @throws SQLException
     */
    public void createPost(String name, String email, String homepage, String comment) throws SQLException {
        String sqlQuery = "INSERT INTO Guest_Book (Name, Email, Homepage, Comment) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, homepage);
        preparedStatement.setString(4, comment);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println(result);
    }

    /**
     * This method gets all entries from the database table
     * @return A Result set from which we parse the results and print to screen
     * @throws SQLException
     */
    public ArrayList<HashMap<String, String>> getAllFromGuestBook() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Guest_Book");
        ArrayList<HashMap<String,String>> results = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        while (rs.next()) {
            HashMap<String, String> contents = new HashMap<>();

            contents.put("name", rs.getString(1));
            contents.put("email", rs.getString(2));
            contents.put("homepage", rs.getString(3));
            contents.put("comment", rs.getString(4));
            System.out.println(contents);
            results.add(contents);

        }
        System.out.println(results);
        connection.close();
        return results;
    }

}
