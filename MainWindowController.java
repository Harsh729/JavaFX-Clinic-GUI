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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
//TODO: add functionality to "Add" and "Edit" buttons
//TODO: add initializePatientTable()

public class MainWindowController implements Initializable {

    private String dir="C:/Anand/Code Projects!/Directories/";

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initializeLabWorkTable();
        initializePrescriptionTable();
        MyDate date=new MyDate();
        String d=date.toString();
        initializeScheduleTable(d);
    }

    public void initializeLabWorkTable()
    {

        TableColumn LabWorkSentDate=new TableColumn("Sent Date");
        TableColumn LabWorkPatientName=new TableColumn("Patient Name");
        TableColumn LabWorkLabName=new TableColumn("Lab Name");
        TableColumn LabWorkWork=new TableColumn("Work");

        LabWorkTable.getColumns().addAll(LabWorkSentDate,LabWorkPatientName,LabWorkLabName,LabWorkWork);



        LabWorkSentDate.setCellValueFactory(new PropertyValueFactory<LabWork,String>("sentDate"));
        LabWorkPatientName.setCellValueFactory(new PropertyValueFactory<LabWork,String>("patientName"));
        LabWorkLabName.setCellValueFactory(new PropertyValueFactory<LabWork,String>("labName"));
        LabWorkWork.setCellValueFactory(new PropertyValueFactory<LabWork,String>("work"));

        LabWork lab=new LabWork();
        try {
            //LabWorkFile file2 = new LabWorkFile("Name 12-1-2019");
            File folder=new File(dir+"Lab Work\\");
            File[] LabWorkFiles=folder.listFiles();
            ObservableList<LabWork> data = FXCollections.observableArrayList();
            for(int i=0;i<LabWorkFiles.length;i++) {
                LabWorkFile labFile = new LabWorkFile(LabWorkFiles[i].getName());
                lab= labFile.readFile();
                data.add(lab);

            }
            LabWorkTable.setItems(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


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
        ScheduleTable.getColumns().clear();
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
        TableColumn description=new TableColumn("Description");

        ScheduleTable.getColumns().addAll(slotColumn,appointmentColumnPatientName,description);

        slotColumn.setCellValueFactory(new PropertyValueFactory<SingleScheduleEntry, String>("time"));
        appointmentColumnPatientName.setCellValueFactory(new PropertyValueFactory<SingleScheduleEntry,String>("patientName"));
        description.setCellValueFactory(new PropertyValueFactory<SingleScheduleEntry,String>("description"));

        ScheduleTable.setItems(data);
    }

    public void initializePatientTable()
    {

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
    private Button ScheduleTableAddButton;

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
    private AnchorPane PatientAnchorPane;

    @FXML
    private Button PatientTabButton;

    @FXML
    void openLabWorkTab(ActionEvent event) {
        if(LabWorkTabButton.getStyleClass().size()<2)
        {
            LabWorkAnchorPane.toFront();
            LabWorkTabButton.getStyleClass().add("tab-button-selected");
            ScheduleTabButton.getStyleClass().remove("tab-button-selected");
            PatientTabButton.getStyleClass().remove("tab-button-selected");
            PrescriptionsTabButton.getStyleClass().remove("tab-button-selected");
        }
    }

    @FXML
    void openScheduleTab(ActionEvent event) {
        if(ScheduleTabButton.getStyleClass().size()<2) {
            ScheduleAnchorPane.toFront();
            LabWorkTabButton.getStyleClass().remove("tab-button-selected");
            ScheduleTabButton.getStyleClass().add("tab-button-selected");
            PatientTabButton.getStyleClass().remove("tab-button-selected");
            PrescriptionsTabButton.getStyleClass().remove("tab-button-selected");
        }
    }

    @FXML
    void openPrescriptionsTab(){
        if(PrescriptionsTabButton.getStyleClass().size()<2) {
            PrescriptionsTabAnchorPane.toFront();
            LabWorkTabButton.getStyleClass().remove("tab-button-selected");
            ScheduleTabButton.getStyleClass().remove("tab-button-selected");
            PatientTabButton.getStyleClass().remove("tab-button-selected");
            PrescriptionsTabButton.getStyleClass().add("tab-button-selected");
        }
    }

    @FXML
    void openPatientTab()
    {
        if(PatientTabButton.getStyleClass().size()<2) {
            PatientAnchorPane.toFront();
            LabWorkTabButton.getStyleClass().remove("tab-button-selected");
            ScheduleTabButton.getStyleClass().remove("tab-button-selected");
            PrescriptionsTabButton.getStyleClass().remove("tab-button-selected");
            PatientTabButton.getStyleClass().add("tab-button-selected");
        }
    }

    @FXML
    void openScheduleEditWindow(ActionEvent event) {

    }

    @FXML
    void openScheduleAddWindow(ActionEvent event)
    {}


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
        String date[]=ScheduleDatePicker.getValue().toString().split("-");
        String mydate=date[2]+"-"+date[1]+"-"+date[0];
        System.out.println(mydate);
        //ScheduleTable.getColumns().clear();
        initializeScheduleTable(mydate);
    }

    @FXML
    void testResponse(){
        System.out.println("Test successful");
    }

}