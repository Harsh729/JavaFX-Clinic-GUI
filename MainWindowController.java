package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainWindowController {

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private Menu MenuBar_Menu_File;

    @FXML
    private MenuItem Menu_File_Close;

    @FXML
    private MenuItem Menu_File_OpenPatient;

    @FXML
    private MenuItem Menu_File_OpenLabRecords;

    @FXML
    private MenuItem Menu_File_OpenPrescriptions;

    @FXML
    private Menu MenuBar_Menu_Edit;

    @FXML
    private Button ScheduleTabButton;

    @FXML
    private Button LabWorkTabButton;

    @FXML
    private Button PrescriptionsTabButton;

    @FXML
    private AnchorPane LabWorkAnchorPane;

    @FXML
    private TableView<?> LabWorkTable;

    @FXML
    private TableColumn<?, ?> LabWorkSentDate;

    @FXML
    private TableColumn<?, ?> LabWorkPatientName;

    @FXML
    private TableColumn<?, ?> LabWorkLabName;

    @FXML
    private TableColumn<?, ?> LabWorkWork;

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
    private DatePicker ScheduleDatePicker;

    @FXML
    private Button ScheduleTableEditButton;

    @FXML
    private AnchorPane PrescriptionsTabAnchorPane;

    @FXML
    void openLabWorkTab(ActionEvent event) {
        LabWorkAnchorPane.toFront();
    }

    @FXML
    void openScheduleTab(ActionEvent event) {
        ScheduleAnchorPane.toFront();
    }

    @FXML
    void openPrescriptionsTab(){
        PrescriptionsTabAnchorPane.toFront();
    }

    @FXML
    void openScheduleEditWindow(ActionEvent event) {

    }


    @FXML
    void closeWindow(){}

    @FXML
    void openPatientRecord(){}

    @FXML
    void openLabRecords(){}

    @FXML
    void openPrescriptions(){}

    @FXML
    void setScheduleDate(){}

}
