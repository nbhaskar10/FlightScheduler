
import java.sql.Date;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 *
 */
public class ScheduleEntry {
    private String customer;
    private String flight;
    private Date date;
    
    public ScheduleEntry(String customer, String flight, Date date){
        this.customer=customer;
        this.flight=flight;
        this.date=date;
    }

    /**
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * @return the flight
     */
    public String getFlight() {
        return flight;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(String flight) {
        this.flight = flight;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString(){
        return String.format("%s\t%s\t%s%n", customer, flight, date.toString());
    }
    
    
}
