package sample;

import ClinicSoftware.Appointment;
import ClinicSoftware.Schedule;
import ClinicSoftware.SingleScheduleEntry;
import ClinicSoftware.Slot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AddScheduleWindowController implements Initializable {


    Slot slot;
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
   public void save(ActionEvent event)
    {
//    Appointment appointment=new Appointment()
//        SingleScheduleEntry scheduleEntry=new SingleScheduleEntry(slot,appointment);
    }

    @FXML
    public void slotChooserStart(ActionEvent event)
    {
        ComboBox newSlot=(ComboBox) event.getSource();
        String slotStart=newSlot.getValue().toString();
        slotChooserEnd.getItems().clear();
        slotChooserEnd.setItems(getValidSlots(slotStart));
    }


    @FXML
    public void cancel()
    {}

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
        for(int i=0;i<data.size();i++)
        {
            if(slot.equals(data.get(0)))
            {
                data.remove(0);
                break;
            }
            data.remove(0);
        }
        return data;
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
}
