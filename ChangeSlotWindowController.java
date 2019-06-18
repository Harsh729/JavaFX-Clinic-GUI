package sample;

import ClinicSoftware.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

//TODO: implement passed values in changeSlotWindowController
//TODO: combobox not working properly, probably have to update it from FXUtilities
public class ChangeSlotWindowController implements Initializable {

    public static Schedule schedule=new Schedule();
    Slot slot;
    static MainWindowController obj=new MainWindowController();
    public static Appointment app=new Appointment();
    public static SingleScheduleEntry row=new SingleScheduleEntry(app);
    Appointment appointment=row.getAppointment();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        FXUtilities util=new FXUtilities(slotChooserStart,slotChooserEnd,schedule,data,slotStart,slotEnd);
        util.createComboBoxItems();
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
    private ComboBox slotChooserStart;

    @FXML
    private ComboBox slotChooserEnd;

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

    ObservableList<String> data= FXCollections.observableArrayList();

    String slotStart="";
    String slotEnd="";

    @FXML
    void cancel() {
        Stage stage=(Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        FXUtilities util=new FXUtilities(slotChooserStart,slotChooserEnd,schedule,data,slotStart,slotEnd);
        try {
            LinkedList<String> timeSlotString=util.getTimeSlot();
            LinkedList<Slot> timeSlotSlot=new LinkedList<>();
            ScheduleFile newScheduleFile=new ScheduleFile(schedule);
            for(int i=0;i<timeSlotString.size();i++)
            {
                timeSlotSlot.add(slot.toSlot(timeSlotString.get(i)));
                if(newScheduleFile.editEntry(appointment,timeSlotSlot.get(i))==null)
                {
                    System.out.println("Edited successfully");
                }
            }
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
    void setSlotEnd(ActionEvent event) {
        slotEnd=slotChooserEnd.getValue().toString();
    }

    @FXML
    void setSlotStart(ActionEvent event) {
        FXUtilities util=new FXUtilities(slotChooserStart,slotChooserEnd,schedule,data,slotStart,slotEnd);
        slotStart=slotChooserStart.getValue().toString();
        System.out.println(slotStart);
//        slotChooserEnd.getItems().clear();
        slotChooserStart.setItems(util.getValidSlots(slotStart));
    }

}