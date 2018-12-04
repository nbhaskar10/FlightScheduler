
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Flights {
    
    private static Connection connection = Database.getConnection();
    private  static PreparedStatement SQLStatement;
    private static ResultSet resultSet=null;
    public  static ArrayList<String> getFlightNames(){
        ArrayList<String> flightNames=new ArrayList<String>();
        try{
            SQLStatement=connection.prepareStatement("SELECT FLIGHT FROM Flight ");
            resultSet=SQLStatement.executeQuery();
            while (resultSet.next()){
                flightNames.add(resultSet.getString("FLIGHT"));
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        return flightNames;
    }
    
    public static int getSeatCount(String flight){
        int seatCountRet=0;
        try{
            SQLStatement=connection.prepareStatement("SELECT SEATS FROM Flight WHERE FLIGHT=?");
            SQLStatement.setString(1, flight);
            resultSet=SQLStatement.executeQuery();
            resultSet.next(); 
           
            seatCountRet = resultSet.getInt("SEATS");
            
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        return seatCountRet;
    }
    
    public static void addNewFlight(String flight, int seatCount){
    try{
            SQLStatement=connection.prepareStatement("INSERT INTO FLIGHT (FLIGHT, SEATS) VALUES (?, ?)");
            SQLStatement.setString(1, flight);
            SQLStatement.setInt(2, seatCount);
            SQLStatement.executeUpdate();           
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }    
    }
    
    public static void deleteFlight(String flight){
        try{
            SQLStatement=connection.prepareStatement("DELETE FROM FLIGHT WHERE FLIGHT=?");
            SQLStatement.setString(1, flight);
            SQLStatement.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
}
