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
        createComboBoxItems();
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
            LinkedList<String> timeSlotsString=getTimeSlot();
            LinkedList<Slot>  timeSlotsSlot=new LinkedList<>();
            ScheduleFile newScheduleFile=new ScheduleFile(schedule);
            for(int i=0;i<timeSlotsString.size();i++)
            {
                timeSlotsSlot.add(slot.toSlot(timeSlotsString.get(i)));
                Appointment newAppointment=new Appointment(newPatient,schedule.getDate(),timeSlotsSlot.get(i));
                AppointmentFile appointmentFile=new AppointmentFile(newAppointment);
                newScheduleFile.addEntry(newAppointment);
            }
            //String args[]={};
        }
        catch(IOException e)
        {
            System.err.println("IOException caught.");
        }
        cancel();
        Main.open();
    }

    @FXML
    public void setSlotStart(ActionEvent event)
    {

        slotStart=slotChooserStart.getValue().toString();
        System.out.println(slotStart);
//        slotChooserEnd.getItems().clear();
        slotChooserEnd.setItems(getValidSlots(slotStart));
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

    public LinkedList<String> generateAllSlots()
    {
        LinkedList<String> availableSlots=new LinkedList<>();
        double ctr=15;
        while(ctr<21)
        {
            double minutes[]={0.00,0.25,0.50,0.75};
            for(int i=0;i<3;i++)
            {

                availableSlots.add((ctr+minutes[i])+" - "+(ctr+minutes[i+1]));
            }
            availableSlots.add((ctr+minutes[3])+" - "+(++ctr+minutes[0]));
        }
        return availableSlots;
    }

    public LinkedList<String> generateOccupiedSlots()
    {
        LinkedList<Slot> slots=new LinkedList<>();
        try
        {
            slots=schedule.getSlots();
        }
        catch(NullPointerException e)
        {
            System.err.println("Null pointer exception, probably because File was not found");
        }
        LinkedList<String> occupiedSlots=new LinkedList<>();
        Iterator itr=slots.iterator();
        while(itr.hasNext())
        {
            occupiedSlots.add(((Slot)itr.next()).displaySlot());
        }
        return occupiedSlots;
    }

    public LinkedList<String> generateAvailableSlots(LinkedList<String> availableSlots,LinkedList<String> occupiedSlots)
    {
        for(int i=0;i<occupiedSlots.size();i++)
        {
            for(int j=0;j<availableSlots.size();j++)
            {
                if(occupiedSlots.get(i).equals(availableSlots.get(j)))
                {
                    availableSlots.remove(j);
                    break;
                }
            }
        }
        return availableSlots;
    }

    public ObservableList<String> getValidSlots(String slot)
    {
        ObservableList<String> dataCopy=FXCollections.observableArrayList(data);
        int n=dataCopy.size();
        for(int i=0;i<n;i++)
        {
            if(slot.equals(dataCopy.get(0)))
            {
                break;
            }
            dataCopy.remove(0);
        }
        return dataCopy;
    }

    public void createComboBoxItems()
    {
         LinkedList<String> occupiedSlots=generateOccupiedSlots();

         LinkedList<String> availableSlots=generateAllSlots();

         availableSlots=generateAvailableSlots(availableSlots,occupiedSlots);

         data=FXCollections.observableList(availableSlots);

         try {
             slotChooserStart.setPromptText("Start Time");
             slotChooserEnd.setPromptText("End Time");
             slotChooserStart.setItems(data);
             slotChooserEnd.setItems(data);
             }

         catch(NullPointerException e)
         {
             System.err.println("Null pointer exception, probably because File was not found");
         }
    }

    public LinkedList<String> getTimeSlot()
    {
        LinkedList<String> allSlots=generateAllSlots();
        LinkedList<String> selectedSlots=new LinkedList<>();
        boolean slotInRange=false;

        for(int i=0;i<allSlots.size();i++)
        {
            if(allSlots.get(i).equals(slotStart)) {
                selectedSlots.add(slotStart);
                slotInRange = true;
            }
            if(slotInRange)
                selectedSlots.add(allSlots.get(i));
            if(allSlots.get(i).equals(slotEnd))
            {
                selectedSlots.add(slotEnd);
                slotInRange=false;
            }
        }
        return selectedSlots;
    }
}