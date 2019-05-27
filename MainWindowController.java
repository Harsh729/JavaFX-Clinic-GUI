package sample;

import ClinicSoftware.*;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initializeLabWorkTable();
        initializePrescriptionTable();
        MyDate date=new MyDate();
        String d=date.toString();
        initializeScheduleTable("27-5-2019");
    }

    public void initializeLabWorkTable()
    {
        LabWork lab=new LabWork();
        try {
            LabWorkFile file2 = new LabWorkFile("Name 12-1-2019");
            lab = file2.readFile();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        ObservableList<LabWork> data=FXCollections.observableArrayList(lab);

        TableColumn LabWorkSentDate=new TableColumn("Sent Date");
        TableColumn LabWorkPatientName=new TableColumn("Patient Name");
        TableColumn LabWorkLabName=new TableColumn("Lab Name");
        TableColumn LabWorkWork=new TableColumn("Work");

        LabWorkTable.getColumns().addAll(LabWorkSentDate,LabWorkPatientName,LabWorkLabName,LabWorkWork);



        LabWorkSentDate.setCellValueFactory(new PropertyValueFactory<LabWork,String>("sentDate"));
        LabWorkPatientName.setCellValueFactory(new PropertyValueFactory<LabWork,String>("patientName"));
        LabWorkLabName.setCellValueFactory(new PropertyValueFactory<LabWork,String>("labName"));
        LabWorkWork.setCellValueFactory(new PropertyValueFactory<LabWork,String>("work"));

        LabWorkTable.setItems(data);
    }

    public void initializePrescriptionTable()
    {
        Prescription pre=new Prescription();
        try {
            PrescriptionFile file = new PrescriptionFile("Name2 14-01-2019");
            pre = file.readFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        ObservableList<Prescription> data=FXCollections.observableArrayList(pre);

        TableColumn PrescriptionDate=new TableColumn("Date");
        TableColumn PrescriptionPatientName=new TableColumn("Patient Name");

        PrescriptionsTable.getColumns().addAll(PrescriptionDate,PrescriptionPatientName);

        PrescriptionDate.setCellValueFactory(new PropertyValueFactory<Prescription,String>("date"));
        PrescriptionPatientName.setCellValueFactory(new PropertyValueFactory<Prescription,String>("patientName"));

        PrescriptionsTable.setItems(data);
    }

    public void initializeScheduleTable(String date)
    {
        Schedule schedule=new Schedule();
        ScheduleFile file=new ScheduleFile(date);
        try {
            schedule = file.readFile();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        LinkedList<Slot> slots=schedule.getSlots();
        LinkedList<Appointment> appointments=schedule.getAppointments();

        Iterator i=slots.iterator();
        Iterator j=appointments.iterator();

        ObservableList<SingleScheduleEntry> data=FXCollections.observableArrayList();

        while(i.hasNext()&&j.hasNext())
        {
            data.add(new SingleScheduleEntry((Slot)i.next(),(Appointment)j.next()));
        }

        TableColumn slotColumn=new TableColumn("Time");
        TableColumn appointmentColumnPatientName=new TableColumn("Appointment");

        ScheduleTable.getColumns().addAll(slotColumn,appointmentColumnPatientName);

        slotColumn.setCellValueFactory(new PropertyValueFactory<SingleScheduleEntry, String>("time"));
        appointmentColumnPatientName.setCellValueFactory(new PropertyValueFactory<SingleScheduleEntry,String>("patientName"));

        ScheduleTable.setItems(data);
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
    private TableView LabWorkTable;

    @FXML
    private AnchorPane ScheduleAnchorPane;

    @FXML
    private TableView ScheduleTable;

    @FXML
    private DatePicker ScheduleDatePicker;

    @FXML
    private Button ScheduleTableEditButton;

    @FXML
    private AnchorPane PrescriptionsTabAnchorPane;

    @FXML
    private TableView PrescriptionsTable;

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