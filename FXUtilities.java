package sample;

import ClinicSoftware.Schedule;
import ClinicSoftware.Slot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.Iterator;
import java.util.LinkedList;

public class FXUtilities {
    ComboBox slotChooserStart;
    ComboBox slotChooserEnd;
    Schedule schedule;
    ObservableList<String> data;
    String slotStart;
    String slotEnd;

    public FXUtilities(ComboBox slotChooserStart,ComboBox slotChooserEnd,Schedule schedule,ObservableList<String> data,String slotStart,String slotEnd)
    {
        this.slotChooserStart=slotChooserStart;
        this.slotChooserEnd=slotChooserEnd;
        this.schedule=schedule;
        this.data=data;
        this.slotStart=slotStart;
        this.slotEnd=slotEnd;
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
        ObservableList<String> dataCopy= FXCollections.observableArrayList(data);
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

        int start=allSlots.indexOf(slotStart);
        int end=allSlots.indexOf(slotEnd);

        for(int i=start;i<=end;i++)
        {
            selectedSlots.add(allSlots.get(i));
        }
        return selectedSlots;
    }

    public void setSlots(String slotStart,String slotEnd)
    {
        this.slotStart=slotStart;
        this.slotEnd=slotEnd;
    }
}
