package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
//import ClinicSoftware;

public class MainWindowController {

    void onStart(){
        //ObservableList<>
        PrescriptionsTable=new TableView<>();
        PrescriptionsTable.setEditable(true);
        PrescriptionsTablePatientNameColumn=new TableColumn<>("Patient Name");
        PrescriptionsTableDateColumn=new TableColumn<>("Date");
        PrescriptionsTableMedicinesColumn=new TableColumn<>("Medicines");
        PrescriptionsTable.getColumns().add(PrescriptionsTableDateColumn);
        PrescriptionsTable.getColumns().add(PrescriptionsTablePatientNameColumn);
        PrescriptionsTable.getColumns().add(PrescriptionsTableMedicinesColumn);
        System.out.println(PrescriptionsTable.getItems().add("Harsh"));
    }

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
    private TableView<String> LabWorkTable;

    @FXML
    private TableColumn<String, String> LabWorkSentDate;

    @FXML
    private TableColumn<String, String> LabWorkPatientName;

    @FXML
    private TableColumn<String, String> LabWorkLabName;

    @FXML
    private TableColumn<String, String> LabWorkWork;

    @FXML
    private AnchorPane ScheduleAnchorPane;

    @FXML
    private TableView<String> ScheduleTable;

    @FXML
    private TableColumn<String, String> ScehduleTableTimeColumn;

    @FXML
    private TableColumn<String, String> ScheduleTableNameColumn;

    @FXML
    private TableColumn<String, String> ScheduleTableProcedureColumn;

    @FXML
    private DatePicker ScheduleDatePicker;

    @FXML
    private Button ScheduleTableEditButton;

    @FXML
    private AnchorPane PrescriptionsTabAnchorPane;

    @FXML
    private TableView<String> PrescriptionsTable;

    @FXML
    private TableColumn<String, String> PrescriptionsTablePatientNameColumn;



    @FXML
    private TableColumn<String, String> PrescriptionsTableDateColumn;

    @FXML
    private TableColumn<String, String> PrescriptionsTableMedicinesColumn;

    @FXML
    void openLabWorkTab(ActionEvent event) {
        if(LabWorkTabButton.getStyleClass().size()<2)
        {
            LabWorkAnchorPane.toFront();
            LabWorkTabButton.getStyleClass().add("tab-button-selected");
            ScheduleTabButton.getStyleClass().remove("tab-button-selected");
            PrescriptionsTabButton.getStyleClass().remove("tab-button-selected");
        }
    }

    @FXML
    void openScheduleTab(ActionEvent event) {
        if(ScheduleTabButton.getStyleClass().size()<2) {
            ScheduleAnchorPane.toFront();
            LabWorkTabButton.getStyleClass().remove("tab-button-selected");
            ScheduleTabButton.getStyleClass().add("tab-button-selected");
            PrescriptionsTabButton.getStyleClass().remove("tab-button-selected");
        }
    }

    @FXML
    void openPrescriptionsTab(){
        if(PrescriptionsTabButton.getStyleClass().size()<2) {
            PrescriptionsTabAnchorPane.toFront();
            LabWorkTabButton.getStyleClass().remove("tab-button-selected");
            ScheduleTabButton.getStyleClass().remove("tab-button-selected");
            PrescriptionsTabButton.getStyleClass().add("tab-button-selected");
        }
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

    @FXML
    void testResponse(){
        System.out.println("Test successful");
    }

}
