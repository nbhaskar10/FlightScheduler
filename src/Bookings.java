
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Bookings {
    private static int bookedSeatsCount=0;
    private static Connection connection = Database.getConnection();
    private  static PreparedStatement SQLStatement;
    private static ResultSet resultSet=null;
    
    private  static PreparedStatement SQLStatement2;
    private static ResultSet resultSet2=null;
    public static int getBookedSeatsCount(String flight, Date date){
        bookedSeatsCount=0;
         try{
            SQLStatement=connection.prepareStatement("SELECT FLIGHT FROM BOOKINGS WHERE FLIGHT=? AND DAY=?");
            SQLStatement.setString(1, flight);
            SQLStatement.setDate(2, date);
            resultSet=SQLStatement.executeQuery();
            while (resultSet.next()){
                bookedSeatsCount++;
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
         
        return bookedSeatsCount;
        
    }
    public static void insertBookings(String customer, String flight, Date date, Timestamp timestamp){
        try{
            SQLStatement=connection.prepareStatement("INSERT INTO BOOKINGS (CUSTOMER, FLIGHT, DAY, TIMESTAMP) VALUES (?,?,?,?) ");
            SQLStatement.setString(1, customer);
            SQLStatement.setString(2, flight);
            SQLStatement.setDate(3, date);
            SQLStatement.setTimestamp(4, timestamp);
            SQLStatement.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public static ArrayList<ScheduleEntry> retrieveBookings(String flight, Date date){
        ArrayList<ScheduleEntry> ret=new ArrayList<ScheduleEntry>();
        try{ 
            SQLStatement=connection.prepareStatement("SELECT * FROM BOOKINGS WHERE FLIGHT=? AND DAY=?");
            SQLStatement.setString(1, flight);
            SQLStatement.setDate(2, date);
            resultSet=SQLStatement.executeQuery();
            while (resultSet.next()){
                    ret.add(new ScheduleEntry (resultSet.getString("CUSTOMER"), 
                    resultSet.getString("FLIGHT"), resultSet.getDate("DAY")));
                }
            }   
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            }
        return ret;
    }
    
    public static ArrayList<ScheduleEntry> retrieveBookingsByCustomer(String customer){
        ArrayList<ScheduleEntry> ret=new ArrayList<ScheduleEntry>();
        try{ 
            SQLStatement=connection.prepareStatement("SELECT * FROM BOOKINGS WHERE CUSTOMER=?");
            SQLStatement.setString(1, customer);
            resultSet=SQLStatement.executeQuery();
            while (resultSet.next()){
                    ret.add(new ScheduleEntry (resultSet.getString("CUSTOMER"), 
                    resultSet.getString("FLIGHT"), resultSet.getDate("DAY")));
                }
            }   
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            }
        return ret;
    }
    public static ArrayList<String> retrieveBookingNames(){
        ArrayList<String> ret=new ArrayList<String>();
        try{ 
            SQLStatement=connection.prepareStatement("SELECT CUSTOMER FROM BOOKINGS");
            resultSet=SQLStatement.executeQuery();
            while (resultSet.next()){
                    ret.add(resultSet.getString("CUSTOMER"));
                }
            }   
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            }
        return ret;
    }
    
    public static SortedSet<String> getallNames(){
        SortedSet<String> ts=new TreeSet<>();
        ArrayList<String> bNames=new ArrayList<>();
        bNames=Bookings.retrieveBookingNames();
        ArrayList<String> wNames=new ArrayList<>();
        wNames=Waitlist.retrieveWaitlistNames();
        for (int i=0; i<bNames.size(); i++)
            ts.add(bNames.get(i));
        for (int i=0; i<wNames.size(); i++)
            ts.add(wNames.get(i));
        
        return ts;
    }
    
    public static ScheduleEntry isBooked(String customer, Date date){
        ScheduleEntry ret=null;
        try{ 
            SQLStatement=connection.prepareStatement("SELECT * FROM BOOKINGS WHERE CUSTOMER=? AND DAY=?");
            SQLStatement.setString(1, customer);
            SQLStatement.setDate(2, date);
            resultSet=SQLStatement.executeQuery();
            
            if (!resultSet.next())
                ret=null;
            else{
                ret=new ScheduleEntry(resultSet.getString("CUSTOMER"), 
                        resultSet.getString("FLIGHT"), resultSet.getDate("DAY"));
                
            }
        }
            
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            }
        return ret;
    }
    
    public static void deleteBooking(String customer, Date date){
        try{ 
            SQLStatement=connection.prepareStatement("DELETE FROM BOOKINGS WHERE CUSTOMER=? AND DAY=?");
            SQLStatement.setString(1, customer);
            SQLStatement.setDate(2, date);
            SQLStatement.executeUpdate();
        }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            }
     
    }
    
    public static ScheduleEntry bookFromWaitlist(String flight, Date date){
        ScheduleEntry ret=null;
         try{ 
            //retrieve from waitlist
            SQLStatement=connection.prepareStatement("SELECT * FROM WAITLIST WHERE FLIGHT=? AND DAY=?");
            SQLStatement.setString(1, flight);
            SQLStatement.setDate(2, date);
            resultSet=SQLStatement.executeQuery();
            //enter into the bookings
            Timestamp timestamp=new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            if (resultSet.next()){
                String customer=resultSet.getString("CUSTOMER");
                SQLStatement=connection.prepareStatement("INSERT INTO BOOKINGS (CUSTOMER, FLIGHT, DAY, TIMESTAMP) VALUES (?,?,?,?) ");
                SQLStatement.setString(1, customer);
                SQLStatement.setString(2, flight);
                SQLStatement.setDate(3, date);
                SQLStatement.setTimestamp(4, timestamp);
                SQLStatement.executeUpdate();
                Waitlist.deleteWaitlistEntry(customer, date);
                ret=new ScheduleEntry(customer, flight, date);
            }
            
            
        }
            
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            }
        return ret;
        
    }
    
    public static ArrayList<String> reBook(String flight){
        ArrayList<String> flights=Flights.getFlightNames();
        ArrayList<String> ret=new ArrayList<>();
        Timestamp timestamp=new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        try{ 
            SQLStatement2=connection.prepareStatement("SELECT CUSTOMER, FLIGHT, DAY, TIMESTAMP FROM BOOKINGS WHERE FLIGHT=?");
            SQLStatement2.setString(1, flight);
            resultSet2=SQLStatement2.executeQuery();
            while (resultSet2.next()){
                
                for (int i=0; i<flights.size(); i++){
                    
                    if (Bookings.getBookedSeatsCount(flights.get(i), resultSet2.getDate("DAY"))<Flights.getSeatCount(flights.get(i))){
                        
                        timestamp=new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
 
                        Bookings.insertBookings(resultSet2.getString("CUSTOMER"), flights.get(i), resultSet2.getDate("DAY"), timestamp);
                        ret.add(resultSet2.getString("CUSTOMER")+" was rebooked to "+flights.get(i)+" on the same day, "+resultSet2.getDate("DAY")+"\n");
                        break;
                    }
                    else if(i==flights.size()-1){
                        ret.add(resultSet2.getString("CUSTOMER")+" was unable to rebooked to another flight on the same day \n");
                    }
                }
            }
            SQLStatement2=connection.prepareStatement("DELETE FROM BOOKINGS WHERE FLIGHT=?");
            SQLStatement2.setString(1, flight);
            SQLStatement2.executeUpdate();
        }   
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            }
        return ret;
    }
}
    
   
    
    
    
            
    
    
    

