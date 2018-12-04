
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class Waitlist {
    private static int bookedSeatsCount=0;
    private static Connection connection = Database.getConnection();
    private  static PreparedStatement SQLStatement;
    private static ResultSet resultSet=null;
    public static void insertWaitlist(String customer, String flight, Date date, Timestamp timestamp){
        try{
            SQLStatement=connection.prepareStatement("INSERT INTO WAITLIST (CUSTOMER, FLIGHT, DAY, TIMESTAMP) VALUES (?,?,?,?) ");
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
    
    public static ArrayList<ScheduleEntry> retrieveWaitlist(String flight, Date date) {
        ArrayList <ScheduleEntry> ret=new ArrayList<ScheduleEntry>();
        try{ 
            SQLStatement=connection.prepareStatement("SELECT * FROM WAITLIST WHERE FLIGHT=? AND DAY=?");
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
    public static ArrayList<ScheduleEntry> retrieveWaitlistByCustomer(String customer){
        ArrayList<ScheduleEntry> ret=new ArrayList<ScheduleEntry>();
        try{ 
            SQLStatement=connection.prepareStatement("SELECT * FROM WAITLIST WHERE CUSTOMER=?");
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
     public static ArrayList<String> retrieveWaitlistNames(){
        ArrayList<String> ret=new ArrayList<String>();
        try{ 
            SQLStatement=connection.prepareStatement("SELECT CUSTOMER FROM WAITLIST");
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
     
    public static void deleteWaitlistEntry(String customer, Date date){
        
        try{ 
            SQLStatement=connection.prepareStatement("DELETE FROM WAITLIST WHERE CUSTOMER=? AND DAY=?");
            SQLStatement.setString(1, customer);
            SQLStatement.setDate(2, date);
            SQLStatement.executeUpdate();
            
        }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            }
       
    }
    
    public static ArrayList<String> deleteWaitlistForFlight(String flight){
       ArrayList<String> ret=new ArrayList<>();
        try{ 
            SQLStatement=connection.prepareStatement("SELECT * FROM WAITLIST WHERE FLIGHT=?");
            SQLStatement.setString(1, flight);
            resultSet=SQLStatement.executeQuery();
            while(resultSet.next()){
                ret.add(resultSet.getString("CUSTOMER")+" was deleted from the waitlist \n");
            }
            
            SQLStatement=connection.prepareStatement("DELETE FROM WAITLIST WHERE FLIGHT=?");
            SQLStatement.setString(1, flight);
            SQLStatement.executeUpdate();
            
        }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
                System.exit(1);
                
            } 
        return ret;
    }
}
