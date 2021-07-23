package GamerProfileApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicReference;

public class Controller {
    @FXML
    ComboBox cbxPlayerIdSummary;
    @FXML
    TextArea txtAreaSummary;
    @FXML
    TextField txtFirstName;
    @FXML
    TextField txtLastName;
    @FXML
    TextField txtAddress;
    @FXML
    TextField txtPostalCode;
    @FXML
    TextField txtProvince;
    @FXML
    TextField txtPhoneNumber;
    @FXML
    TextField txtGameTitle;

    @FXML
    ComboBox cbxPlayerId;

    //initialize combo box with with player ids from database
    @FXML
    private void initialize()
    {
        ObservableList<Integer> ids = SQLiteDB.GetUserIds();
        cbxPlayerId.setItems(ids);
        cbxPlayerIdSummary.setItems(ids);
    }

    //monitor if a player has been selected
    boolean playerSelected = false;

    //when player id is selected from combo box, fill form fields with that players data
    public void PopulateFieldsToUpdate()
    {
        if (cbxPlayerId.getValue() != null) {
            ObservableList<String> userInformation = SQLiteDB.GetUserInformationById(Integer.parseInt(String.valueOf(cbxPlayerId.getValue())));
            System.out.println(userInformation);
            txtFirstName.setText(userInformation.get(0));
            txtLastName.setText(userInformation.get(1));
            txtAddress.setText(userInformation.get(2));
            txtPostalCode.setText(userInformation.get(3));
            txtProvince.setText(userInformation.get(4));
            txtPhoneNumber.setText(userInformation.get(5));
            playerSelected = true;
        }
    }

    //UPDATE player information in database if any field is changed
    public void UpdatePlayerInformation()
    {
        if (!playerSelected)
        {
            return;
        }
        SQLiteDB.UpdatePlayerInformation(Integer.parseInt(String.valueOf(cbxPlayerId.getValue())), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtPostalCode.getText(), txtProvince.getText(), txtPhoneNumber.getText());
        if (cbxPlayerId.getValue() != null) {
            cbxPlayerId.getSelectionModel().clearSelection();
        }
    }


    //INSERT new player information into database
    public void InsertPlayerInformation()
    {
        SQLiteDB.InsertPlayerInformation(txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtPostalCode.getText(), txtProvince.getText(), txtPhoneNumber.getText());
        cbxPlayerId.setItems(SQLiteDB.GetUserIds());
        txtAreaSummary.setText(SQLiteDB.GetPlayersFromDB());

    }

    //INSERT new game title into database
    public void InsertGameTitle()
    {
        SQLiteDB.InsertGameInformation(txtGameTitle.getText());
        txtGameTitle.setText(" ");
    }

    //READ the contents of the database
    public void PrintGames()
    {
        txtAreaSummary.setText("");
        txtAreaSummary.setText(SQLiteDB.GetGamesFromDB());
    }

    public void PrintPlayers()
    {
        txtAreaSummary.setText("");
        txtAreaSummary.setText(SQLiteDB.GetPlayersFromDB());
    }


    public void PrintPlayerAndGameReport()
    {
        if (cbxPlayerIdSummary.getValue() != null) {
            txtAreaSummary.setText("");
            AtomicReference<String> contents = new AtomicReference<>("");

            SQLiteDB.GetSpecificPlayerGameReport(Integer.parseInt(String.valueOf(cbxPlayerIdSummary.getValue()))).forEach(item -> {
                contents.updateAndGet(v -> v + item.player_game_id + " | " + item.game_id + " | " + item.player_id + " | " + item.playing_date + " | " + item.score + "\n");
            });
            if (contents.toString() == "")
            {
                contents.set("Player with this ID has no played games");
            }
            txtAreaSummary.setText(contents.toString());
        }
        else
        {
            txtAreaSummary.setText("");
            AtomicReference<String> contents = new AtomicReference<>("");

            SQLiteDB.GetPlayerGameReport().forEach(item -> {
                contents.updateAndGet(v -> v + item.player_game_id + " | " + item.game_id + " | " + item.player_id + " | " + item.playing_date + " | " + item.score + "\n");
            });
            txtAreaSummary.setText(contents.toString());
        }

    }

    public void ClearFields()
    {
        txtAreaSummary.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAddress.setText("");
        txtPostalCode.setText("");
        txtProvince.setText("");
        txtPhoneNumber.setText("");
        cbxPlayerId.getSelectionModel().clearSelection();
        cbxPlayerIdSummary.getSelectionModel().clearSelection();
    }
}
