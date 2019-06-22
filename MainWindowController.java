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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
//TODO: add functionality to MenuBar
//TODO: weird filewriting bugs after changing appointment and adding
//TODO: change naming system for appointments
public class MainWindowController implements Initializable {

    private String dir="C:/Anand/Code Projects!/Directories/";

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initializeLabWorkTable();
        initializePrescriptionTable();
        MyDate date=new MyDate();
        String d=date.toString();
        //System.out.println(d);
        initializeScheduleTable(d);
        initializePatientTable();
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
                LabWorkFile labFile = new LabWorkFile(LabWorkFiles[i].getName().split("\\.")[0]);
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
        TableColumn PrescriptionDate=new TableColumn("Date");
        TableColumn PrescriptionPatientName=new TableColumn("Patient Name");

        PrescriptionsTable.getColumns().addAll(PrescriptionDate,PrescriptionPatientName);

        PrescriptionDate.setCellValueFactory(new PropertyValueFactory<Prescription,String>("date"));
        PrescriptionPatientName.setCellValueFactory(new PropertyValueFactory<Prescription,String>("patientName"));

        Prescription pre=new Prescription();
        try {
            //LabWorkFile file2 = new LabWorkFile("Name 12-1-2019");
            File folder=new File(dir+"Prescriptions\\");
            File[] PrescriptionFiles=folder.listFiles();
            ObservableList<Prescription> data = FXCollections.observableArrayList();
            for(int i=0;i<PrescriptionFiles.length;i++) {
                PrescriptionFile prescriptionFile = new PrescriptionFile(PrescriptionFiles[i].getName().split("\\.")[0]);
                pre=prescriptionFile.readFile();
                data.add(pre);

            }
            PrescriptionsTable.setItems(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void initializeScheduleTable(String date)
    {
        try {
            ScheduleTable.getColumns().clear();
        }
        catch(NullPointerException e)
        {
            System.err.println("Null pointer exception");
        }
        schedule=new Schedule();
        ScheduleFile file=new ScheduleFile(date);
        try {
            schedule = file.readFile();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        LinkedList<Appointment> appointments=new LinkedList<>();

        try {

            appointments = schedule.getAppointments();
        }
        catch(NullPointerException e)
        {
            System.err.println("Null pointer exception, probably because File was not found");
        }


        Iterator j=appointments.iterator();

        ObservableList<SingleScheduleEntry> data=FXCollections.observableArrayList();

        while(j.hasNext())
        {
            data.add(new SingleScheduleEntry((Appointment)j.next()));
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
        TableColumn patientName=new TableColumn("Patient Name");
        TableColumn phone=new TableColumn("Phone No.");
        TableColumn age=new TableColumn("Age");
        TableColumn money=new TableColumn("Pending amount");

        PatientTable.getColumns().addAll(patientName,phone,age,money);

        patientName.setCellValueFactory(new PropertyValueFactory<Record,String>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<Record,String>("phone"));
        age.setCellValueFactory(new PropertyValueFactory<Record,Integer>("age"));
        money.setCellValueFactory(new PropertyValueFactory<Record,Double>("paid"));

        try{
            File folder=new File(dir+"Records\\");
            File[] RecordFiles=folder.listFiles();
            ObservableList<Record> data=FXCollections.observableArrayList();
            for(File file: RecordFiles)
            {
                RecordFile recordFile=new RecordFile(file.getName().split("\\.")[0]);
                Record rec=recordFile.readFile();
                data.add(rec);
            }
            PatientTable.setItems(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public Schedule schedule;

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
    private Button ChangeAppointmentButton;

    @FXML
    private Button ChangeSlotButton;

    @FXML
    private AnchorPane PrescriptionsTabAnchorPane;

    @FXML
    private TableView PrescriptionsTable;

    @FXML
    private AnchorPane PatientAnchorPane;

    @FXML
    private Button PatientTabButton;

    @FXML
    private TableView PatientTable;

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
    void openchangeAppointmentWindow(ActionEvent event) {

    }

    @FXML
    void openScheduleAddWindow(ActionEvent event)
    {
        AddScheduleWindowController obj=new AddScheduleWindowController();
        obj.setSchedule(schedule);
        AddScheduleWindowMain obj2=new AddScheduleWindowMain();
        Stage stage=new Stage();
        try {
            obj2.setMainWindowController(this);
            obj2.start(stage);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println("Reach");
    }

    @FXML
    public void openChangeSlotWindow()
    {
        SingleScheduleEntry selected=(SingleScheduleEntry)ScheduleTable.getSelectionModel().getSelectedItem();
        ChangeSlotWindowMain obj=new ChangeSlotWindowMain();
        obj.setSelectedRow(selected);
        obj.setMainWindowController(this);
        obj.setSchedule(schedule);
        Stage stage=new Stage();
        try
        {
            obj.start(stage);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void openChangeAppointmentWindow()
    {
        SingleScheduleEntry row=(SingleScheduleEntry)ScheduleTable.getSelectionModel().getSelectedItem();
        ChangeAppointmentWindowMain obj=new ChangeAppointmentWindowMain();
        obj.setSelectedRow(row);
        obj.setMainWindowController(this);
        obj.setSchedule(schedule);
        Stage stage=new Stage();
        try
        {
            obj.start(stage);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteScheduleEntry()
    {
        SingleScheduleEntry row=(SingleScheduleEntry)ScheduleTable.getSelectionModel().getSelectedItem();
        try
        {
            ScheduleFile file=new ScheduleFile(schedule);
            if(file.deleteEntry(row.getSlot())==null)
            {
                System.out.println("Deleted successfully.");
            }
            initializeScheduleTable(schedule.getDate());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    @FXML
    public void closeWindow(){
        Stage stage=(Stage) ScheduleTabButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openPatientRecord(){}

    @FXML
    void openLabRecords(){}

    @FXML
    void openPrescriptions(){}

    @FXML
    void setScheduleDate(){
        String mydate=dateExtract();
        System.out.println(mydate);
        //ScheduleTable.getColumns().clear();
        initializeScheduleTable(mydate);
    }

    String dateExtract()
    {
        String date[]=ScheduleDatePicker.getValue().toString().split("-");
        return date[2]+"-"+date[1]+"-"+date[0];
    }

    @FXML
    void testResponse(){
        System.out.println("Test successful");
    }

}