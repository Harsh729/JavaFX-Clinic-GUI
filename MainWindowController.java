package sample;

import ClinicSoftware.LabWork;
import ClinicSoftware.LabWorkFile;
import ClinicSoftware.Schedule;
import javafx.collections.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;


public class MainWindowController {

    void onStart()throws IOException
    {
        //ObservableList<>
//        PrescriptionsTable=new TableView<Schedule>();
//        PrescriptionsTable.setEditable(true);
//        PrescriptionsTablePatientNameColumn=new TableColumn<>("Patient Name");
//        PrescriptionsTableDateColumn=new TableColumn<>("Date");
//        PrescriptionsTableMedicinesColumn=new TableColumn<>("Medicines");
//        PrescriptionsTable.getColumns().add(PrescriptionsTableDateColumn);
//        PrescriptionsTable.getColumns().add(PrescriptionsTablePatientNameColumn);
//        PrescriptionsTable.getColumns().add(PrescriptionsTableMedicinesColumn);
//        System.out.println(PrescriptionsTable.getItems().add("Harsh"));
//        ScheduleTabButton.getStyleClass().add("tab-button-selected");

        LabWorkFile file2=new LabWorkFile("Name 12-1-2019");
        LabWork lab=file2.readFile("Name 12-1-2019");

        ObservableList<LabWork> data=FXCollections.observableArrayList(lab);

        LabWorkTable.setEditable(true);
        LabWorkSentDate.setCellValueFactory(new PropertyValueFactory<>("sentDate"));
        LabWorkPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        LabWorkLabName.setCellValueFactory(new PropertyValueFactory<>("labName"));
        LabWorkWork.setCellValueFactory(new PropertyValueFactory<>("work"));

        LabWorkTable.setItems(data);

        System.out.println(LabWorkTable.getColumns().addAll(LabWorkSentDate,LabWorkPatientName,LabWorkLabName,LabWorkWork));

        LabWorkWork.setVisible(false);


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
    private TableView LabWorkTable=new TableView();

    @FXML
    private TableColumn<String, LabWork> LabWorkSentDate=new TableColumn<>("Sent Date");

    @FXML
    private TableColumn<String, LabWork> LabWorkPatientName=new TableColumn<>("Patient Name");

    @FXML
    private TableColumn<String, LabWork> LabWorkLabName=new TableColumn<>("Lab Name");

    @FXML
    private TableColumn<String, LabWork> LabWorkWork=new TableColumn<>("Work");

    @FXML
    private AnchorPane ScheduleAnchorPane;

    @FXML
    private TableView<Schedule> ScheduleTable=new TableView<Schedule>();

    @FXML
    private TableColumn ScheduleTableTimeColumn=new TableColumn<>();


    @FXML
    private TableColumn ScheduleTableNameColumn;

    @FXML
    private TableColumn ScheduleTableProcedureColumn;

    @FXML
    private DatePicker ScheduleDatePicker;

    @FXML
    private Button ScheduleTableEditButton;

    @FXML
    private AnchorPane PrescriptionsTabAnchorPane;

    @FXML
    private TableView<Schedule> PrescriptionsTable;

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
    void setScheduleDate(){
        System.out.println(ScheduleDatePicker.getValue().toString());
    }

    @FXML
    void testResponse(){
        System.out.println("Test successful");
    }

}