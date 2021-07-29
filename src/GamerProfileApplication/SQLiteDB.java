package GamerProfileApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class SQLiteDB {
   private static String jbdcURL = "jdbc:sqlite:/C:\\Users\\kenpf\\IdeaProjects\\KenFowler_COMP228Lab5\\gamerprofile.db";



   //allows user to update a players profile
   public  static void UpdatePlayerInformation(int userId, String firstName, String lastName, String address, String postalCode, String province, String phoneNumber)
   {
       Connection connection = null;
       try
       {
           connection = DriverManager.getConnection(jbdcURL);
           Statement statement  = connection.createStatement();
           String insertStatement = String.format("UPDATE player SET first_name = '%s', last_name = '%s', address = '%s', postal_code = '%s', province = '%s', phone_number = '%s' WHERE player_id = %d",firstName, lastName, address, postalCode, province, phoneNumber, userId);
           statement.executeUpdate(insertStatement);
           System.out.println("Player information updated!");
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
    //allow user to insert a game title into the database
    public static void InsertGameInformation(String title)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(jbdcURL);
            Statement statement  = connection.createStatement();
            String insertStatement = String.format("INSERT INTO game (game_title) VALUES('%s')", title);
            statement.executeUpdate(insertStatement);
            System.out.println("Title included in database!");
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
    public static void InsertPlayerInformation(String firstName, String lastName, String address, String postalCode, String province, String phoneNumber)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(jbdcURL);
            String[] userInput = {firstName, lastName, address, postalCode, province, phoneNumber};
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

    //READ PLAYERS FORM DATABASE
    public static String GetPlayersFromDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jbdcURL);
            String sql = "SELECT * FROM player";
            Statement statement  = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            String contents = "";
            while (result.next())
            {
                int id = result.getInt("player_id");
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                String address = result.getString("address");
                String postal_code = result.getString("postal_code");
                String province = result.getString("province");
                String phone_number = result.getString("phone_number");

                contents += id + " | " + first_name+ " | " + last_name+ " | " + address + " | " + postal_code + " | " + province + " | " + phone_number + "\n";
            }
            return contents;
        }
        catch (SQLException throwable) {
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

  //READ games from database
    public static String GetGamesFromDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jbdcURL);
            String sql = "SELECT * FROM game";
            Statement statement  = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            String contents = "";
            while (result.next())
            {
                //TODO: need to be able to get for either player or game queries
                int id = result.getInt("game_id");
                String title = result.getString("game_title");

                contents += id + " | " + title + "\n";
            }
            return contents;
        }
        catch (SQLException throwable) {
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

  //READ player information from database based on userid selected
  public static ObservableList<String> GetUserInformationById(int userId)
  {
      ObservableList<String> userInformation = FXCollections.observableArrayList();
      Connection connection = null;
      try
      {
          connection = DriverManager.getConnection(jbdcURL);
          String sql = String.format("SELECT first_name, last_name, address, postal_code, province, phone_number FROM player WHERE player_id = %d", userId);
          Statement statement  = connection.createStatement();
          ResultSet result = statement.executeQuery(sql);
          String first_name = result.getString("first_name");
          String last_name = result.getString("last_name");
          String address = result.getString("address");
          String postal_code = result.getString("postal_code");
          String province = result.getString("province");
          String phone_number = result.getString("phone_number");
          userInformation.addAll(first_name, last_name, address, postal_code, province, phone_number);
          return userInformation;
      } catch (SQLException throwable) {
          System.out.println("Error: Cannot connect to SQLite Database");
          throwable.printStackTrace();
          return userInformation;
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

    //READ all player ids from database
    public static ObservableList<Integer> GetUserIds()
    {
        ObservableList<Integer> userIds = FXCollections.observableArrayList();
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(jbdcURL);
            String sql = "SELECT * FROM player";
            Statement statement  = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next())
            {
                int id = result.getInt("player_id");
                userIds.add(id);
            }
            return userIds;
        } catch (SQLException throwable) {
            System.out.println("Error: Cannot connect to SQLite Database");
            throwable.printStackTrace();
            return userIds;
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

    public static class PlayerAndGame
    {
        int player_game_id;
        int game_id;
        int player_id;
        Date playing_date;
        int score;

        public PlayerAndGame(int player_game_id, int game_id, int player_id, Date playing_date, int score)
        {
            this.player_game_id = player_game_id;
            this.game_id = game_id;
            this.player_id = player_id;
            this.playing_date = playing_date;
            this.score = score;
        }
    }

    //READ player and game information
    public static ObservableList<PlayerAndGame> GetPlayerGameReport() {

    ObservableList<PlayerAndGame> userInformation = FXCollections.observableArrayList();
    Connection connection = null;


        try
    {
        connection = DriverManager.getConnection(jbdcURL);
        String sql = String.format("SELECT player_game_id, game_id, player_id, datetime(playing_date, 'unixepoch') as playing_date, score FROM playerandgame");
        Statement statement  = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next())
        {

            int player_game_id = result.getInt("player_game_id");
            int game_id = result.getInt("game_id");
            int player_id = result.getInt("player_id");
            Date playing_date = result.getDate("playing_date");
            int score = result.getInt("score");

            userInformation.addAll(new PlayerAndGame(player_game_id, game_id, player_id, playing_date, score));
        }
        return userInformation;
    } catch (SQLException throwable) {
        System.out.println("Error: Cannot connect to SQLite Database");
        throwable.printStackTrace();
        return userInformation;
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

    //READ player and game information for a specific player
    public static ObservableList<PlayerAndGame> GetSpecificPlayerGameReport(int id) {

        ObservableList<PlayerAndGame> userInformation = FXCollections.observableArrayList();
        Connection connection = null;


        try
        {
            connection = DriverManager.getConnection(jbdcURL);
            String sql = String.format("SELECT player_game_id, game_id, player_id, datetime(playing_date, 'unixepoch') as playing_date, score FROM playerandgame where player_id = %d", id);
            Statement statement  = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next())
            {

                int player_game_id = result.getInt("player_game_id");
                int game_id = result.getInt("game_id");
                int player_id = result.getInt("player_id");
                Date playing_date = result.getDate("playing_date");
                int score = result.getInt("score");
                userInformation.addAll(new PlayerAndGame(player_game_id, game_id, player_id, playing_date, score));
            }
            return userInformation;
        } catch (SQLException throwable) {
            System.out.println("Error: Cannot connect to SQLite Database");
            throwable.printStackTrace();
            return userInformation;
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
