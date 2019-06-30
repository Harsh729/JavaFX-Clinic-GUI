package sample;

import ClinicSoftware.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PayWindowController {

    public static Appointment app=new Appointment();
    public static SingleScheduleEntry selected=new SingleScheduleEntry(app);

    public static MainWindowController obj=new MainWindowController();

    @FXML
    private TextField AmountTextField;

    @FXML
    private Button CancelButton;

    @FXML
    private Button OKButton;

    @FXML
    void closeWindow()
    {
        Stage stage=(Stage)CancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save()
    {
        try {
            selected.getAppointment().setPaid(Double.valueOf(AmountTextField.getText()));
            selected.getAppointment().pay();
            RecordFile rf=new RecordFile(selected.getAppointment().getRecord());
            AppointmentFile file = new AppointmentFile(selected.getAppointment());
            closeWindow();
            obj.initializePatientTable();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
