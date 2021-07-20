package GamerProfileApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Controller {
    @FXML
    TextArea txtAreaSummary;

    public void PrintDB()
    {
        txtAreaSummary.setText(SQLiteDB.GetFromDB());
    }

}
