package GamerProfileApplication;

import java.sql.*;

public class SQLiteDB {
   private static String jbdcURL = "jdbc:sqlite:/C:\\Users\\kenpf\\IdeaProjects\\KenFowler_COMP228Lab5\\gamerprofile.db";

   //allows user to update a players profile
   public  static void UpdatePlayerInformation()
   {

   }
    //allow user to insert a game title into the database
    public static void InsertGameInformation()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(jbdcURL);
            String userInput = "Tetris";
            Statement statement  = connection.createStatement();
            String insertStatement = String.format("INSERT INTO game (game_title) VALUES('%s')", userInput);
            statement.executeUpdate(insertStatement);
        }
        catch (SQLException throwable)
        {
            System.out.println("Error: Cannot connect to SQLite Database");
            throwable.printStackTrace();
        }
        finally
        {
            // ensure database connection is closed
            try
            {
                if(connection != null) connection.close();
                System.out.println("Connection Closed!");
            }
            catch(SQLException e) {
                // connection close failed
                System.err.println( e.getMessage() );
            }
        }
    }

    //allow user to create a new player record
    public static void InsertPlayerInformation()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(jbdcURL);
            String[] userInput = {"Ken", "Fowler", "222 Wellesley Street East", "M1L3Y8", "ON", "647-608-9359"};
            Statement statement  = connection.createStatement();
            String insertStatement = String.format("INSERT INTO player (first_name, last_name, address, postal_code, province, phone_number) VALUES('%s','%s','%s','%s','%s','%s')", userInput[0], userInput[1],userInput[2],userInput[3],userInput[4],userInput[5]);
            statement.executeUpdate(insertStatement);
        }
        catch (SQLException throwable)
        {
            System.out.println("Error: Cannot connect to SQLite Database");
            throwable.printStackTrace();
        }
        finally
        {
            // ensure database connection is closed
            try
            {
                if(connection != null) connection.close();
                System.out.println("Connection Closed!");
            }
            catch(SQLException e) {
                // connection close failed
                System.err.println( e.getMessage() );
            }
        }
    }
    //INSERT: user needs to be able to insert player and game information into DB
    //player table: player_id, first_name, last_name, address, postal_code, province, phone_number
    //game table: game_id, game_title
    //You should populate the table Game with titles of games that you have "played" during this semester.

    //UPDATE: user must be able to update the player table

    //DISPLAY reports with player and played games information.
    //You may use a JTable or other components to display the reports.
    // Allow the user to select player_id.

    //print out a player record to the GUI
    public static String GetFromDB() {
        Connection connection = null;
        try {
            String jbdcURL = "jdbc:sqlite:/C:\\Users\\kenpf\\IdeaProjects\\KenFowler_COMP228Lab5\\gamerprofile.db";
            connection = DriverManager.getConnection(jbdcURL);
            String sql = "SELECT * FROM player";
            Statement statement  = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            String contents = "";
            while (result.next())
            {
                //TODO: need to be able to get for either player or game queries
                int id = result.getInt("player_id");
                String name = result.getString("first_name");
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
