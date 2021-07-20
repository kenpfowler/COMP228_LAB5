package GamerProfileApplication;

import java.sql.*;

public class SQLiteDB {
    public static void InsertGameInformation(String title)
    {
    }
    //INSERT: user need to be able to insert player and game information into DB
    //player table: player_id, first_name, last_name, address, postal_code, province, phone_number
    //game table: game_id, game_title
    //You should populate the table Game with titles of games that you have "played" during this semester.

    //UPDATE: user must be able to update the player table

    //DISPLAY reports with player and played games information.
    //You may use a JTable or other components to display the reports.
    // Allow the user to select player_id.

    public static String GetFromDB() {
        Connection connection = null;
        try {
            String jbdcURL = "jdbc:sqlite:/C:\\Users\\kenpf\\IdeaProjects\\KenFowler_COMP228Lab5\\gamerprofile.db";
            connection = DriverManager.getConnection(jbdcURL);
            String sql = "SELECT * FROM game";
            Statement statement  = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            String contents = "";
            while (result.next())
            {
                int id = result.getInt("game_id");
                String name = result.getString("game_title");
                contents += id + " | " + name;
            }
            return contents;
        } catch (SQLException throwable) {
            System.out.println("Error: Cannot connect to SQLite Database");
            throwable.printStackTrace();
            return "";
        }
        finally {
            // ensure database connection is closed
            try {
                if(connection != null) connection.close();
                System.out.println("Connection Closed!");
            }
            catch(SQLException e) {
                // connection close failed
                System.err.println( e.getMessage() );
            }
    }
}
}
