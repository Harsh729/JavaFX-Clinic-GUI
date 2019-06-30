package sample;

import ClinicSoftware.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
//TODO: implement name setting in Lab and Pre (maybe I don't want to do that)

public class AddScheduleWindowController implements Initializable {


    String slotStart;
    String slotEnd;

    LabWork lab=new LabWork();
    Prescription pre=new Prescription();
    Record record=new Record();


    Slot slot=new Slot();

    static Schedule schedule=new Schedule();

    public void setSchedule(Schedule schedule)
    {
        this.schedule=schedule;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        util=new FXUtilities(slotChooserStart,slotChooserEnd,schedule,data,slotStart,slotEnd);
        util.createComboBoxItems();
        updateData();
        //System.out.println(slotStart);
    }

    public void updateData()
    {
        slotChooserStart=util.slotChooserStart;
        slotChooserEnd=util.slotChooserEnd;
        data=util.data;
        schedule=util.schedule;
        slotStart=util.slotStart;
        slotEnd=util.slotEnd;
    }

    @FXML
    private AnchorPane baseAnchorPane;

    @FXML
    private AnchorPane detailsPane;

    @FXML
    private Label headingLabel;

    @FXML
    private Label slotLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ComboBox slotChooserStart;

    @FXML
    private ComboBox slotChooserEnd;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField priceTextField;

    @FXML
    private AnchorPane buttonPane;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private HBox buttonBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button acceptButton;

    @FXML
    private Button CreateRecordButton;

    @FXML
    private Button CreateLabWorkButton;

    @FXML
    private Button CreatePrescriptionButton;

    private ObservableList<String> data=FXCollections.observableArrayList();

    public static MainWindowController obj;//for closing

    FXUtilities util;

    public static int userSignature=0;

    public void setUserSignature(int userSignature)
    {
        this.userSignature=userSignature;
    }

   @FXML
   public void save()
    {
        try {
            Record newPatient = new Record(nameTextField.getText(), phoneTextField.getText());
            RecordFile patientFile = new RecordFile(newPatient.getFileName());
            Record existingRecord=patientFile.readFile();
            if(existingRecord!=null)
            {
                newPatient=existingRecord;
            }
            else
            {
                RecordFile rf=new RecordFile(newPatient);
            }
            if(record.getName()!="")
            {
                newPatient=record;
            }
            util.setSlots(slotStart,slotEnd);
            LinkedList<String> timeSlotsString=util.getTimeSlot();
            LinkedList<Slot>  timeSlotsSlot=new LinkedList<>();
            ScheduleFile newScheduleFile=new ScheduleFile(schedule);
            boolean isPriceAdded=false;
            for(int i=0;i<timeSlotsString.size();i++)
            {
                timeSlotsSlot.add(slot.toSlot(timeSlotsString.get(i)));
                Appointment newAppointment=new Appointment(newPatient,schedule.getDate(),timeSlotsSlot.get(i));
                newAppointment.setProcedure(descriptionTextArea.getText());
                newAppointment.setUserSignature(userSignature);
                if(!isPriceAdded) {
                    String check=priceTextField.getText();
                    if(!priceTextField.getText().equals("")) {
                        newAppointment.setPrice(Double.valueOf(priceTextField.getText()));
                        newAppointment.reflect();
                    }
                    isPriceAdded=true;
                }
                if(lab.getSentDate()!="")
                    newAppointment.setLab(lab);
                if(pre.getPatientName()!="")
                    newAppointment.setPrescription(pre);
                AppointmentFile appointmentFile=new AppointmentFile(newAppointment);
                if(newScheduleFile.addEntry(newAppointment)==null)
                {
                    System.out.println("Entry added successfully");
                }
            }
            //String args[]={};
        }
        catch(IOException e)
        {
            System.err.println("IOException caught.");
        }
        cancel();
        obj.init(schedule.getDate());
    }

    @FXML
    public void setSlotStart(ActionEvent event)
    {
        slotStart=slotChooserStart.getValue().toString();
        //System.out.println(slotStart);
        slotChooserEnd.setItems(util.getValidSlots(slotStart));
        //System.out.println(slotEnd);
    }

    @FXML
    public void setSlotEnd(ActionEvent event)
    {
        slotEnd=slotChooserEnd.getValue().toString();
    }

    @FXML
    public void cancel()
    {
        Stage stage=(Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void createRecord()
    {
        try {
            CreateRecordMain obj = new CreateRecordMain();
            Stage stage = new Stage();
            obj.start(stage);
            obj.setObj(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setRecord(Record record)
    {
        this.record=record;
    }

    @FXML
    public void createLabWork()
    {
        try
        {
            CreateLabWorkMain obj=new CreateLabWorkMain();
            Stage stage=new Stage();
            obj.start(stage);
            obj.setPatientName(nameTextField.getText());
            obj.setObj(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setLab(LabWork lab)
    {
        this.lab=lab;
    }

    @FXML
    public void createPrescription()
    {
        try
        {
            CreatePrescriptionMain obj=new CreatePrescriptionMain();
            Stage stage=new Stage();
            obj.start(stage);
            obj.setObj(this);
            obj.setPatientName(nameTextField.getText());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setPrescription(Prescription pre)
    {
        this.pre=pre;
    }
}