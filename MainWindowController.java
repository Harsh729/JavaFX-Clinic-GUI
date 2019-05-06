package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainWindowController {

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private Menu MenuBar_Menu_File;

    @FXML
    private Menu MenuBar_Menu_Edit;

    @FXML
    private Button ScheduleTabButton;

    @FXML
    private Button LabWorkTabButton;

    @FXML
    private Pane LabWorkPane;

    @FXML
    private AnchorPane ScheduleAnchorPane;

    @FXML
    private TableView<?> ScheduleTable;

    @FXML
    private TableColumn<?, ?> ScehduleTableTimeColumn;

    @FXML
    private TableColumn<?, ?> ScheduleTableNameColumn;

    @FXML
    private TableColumn<?, ?> ScheduleTableProcedureColumn;

    @FXML
    private Label ScheduleDateLabel;

    @FXML
    private Button ScheduleTableEditButton;

    @FXML
    void getLabWorkTab(ActionEvent event) {

    }

    @FXML
    void getScheduleTab(ActionEvent event) {

    }

    @FXML
    void openScheduleEditWindow(ActionEvent event) {

    }

}
