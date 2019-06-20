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

public class AddScheduleWindowController implements Initializable {


    String slotStart;
    String slotEnd;

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
        System.out.println(slotStart);
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
    private AnchorPane buttonPane;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private HBox buttonBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button acceptButton;

    private ObservableList<String> data=FXCollections.observableArrayList();

    public static MainWindowController obj;//for closing

    FXUtilities util;

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
            util.setSlots(slotStart,slotEnd);
            LinkedList<String> timeSlotsString=util.getTimeSlot();
            LinkedList<Slot>  timeSlotsSlot=new LinkedList<>();
            ScheduleFile newScheduleFile=new ScheduleFile(schedule);
            for(int i=0;i<timeSlotsString.size();i++)
            {
                timeSlotsSlot.add(slot.toSlot(timeSlotsString.get(i)));
                Appointment newAppointment=new Appointment(newPatient,schedule.getDate(),timeSlotsSlot.get(i));
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
        Main.open();
        obj.closeWindow();
    }

    @FXML
    public void setSlotStart(ActionEvent event)
    {
        slotStart=slotChooserStart.getValue().toString();
        System.out.println(slotStart);
        slotChooserEnd.setItems(util.getValidSlots(slotStart));
        System.out.println(slotEnd);
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
}