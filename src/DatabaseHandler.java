import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DatabaseHandler {

    Connection connection = null;
    Statement statement = null;


    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + "atlas.dsv.su.se" + "/" + "db_20204492", "usr_20204492", "204492");
            System.out.println("Creating connection " + connection);
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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

    public String getAllFromGuestBook() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Guest_Book");
        StringBuilder builder = new StringBuilder();
        while (rs.next()) {
            builder.append(" ");
            builder.append(rs.getString(1));
            builder.append(",");
            builder.append(rs.getString(2));
            builder.append(",");
            builder.append(rs.getString(3));
            builder.append(",");
            builder.append(rs.getString(4));

            System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "  " + rs.getString(4));
        }
        connection.close();
        return builder.toString();
    }

}
