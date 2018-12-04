
import java.sql.Connection;
import java.sql.Date;
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
public class Dates {
    private static Connection connection = Database.getConnection();
    private  static PreparedStatement SQLStatement;
    private static ResultSet resultSet=null;
    public Dates(){
    }
    
    public static ArrayList<String> getDates(){
        ArrayList<String> datesOut = new ArrayList<String>();
        try{
            SQLStatement=connection.prepareStatement("SELECT DAY FROM Dates ");
            resultSet=SQLStatement.executeQuery();
            while (resultSet.next()){
                datesOut.add((resultSet.getDate("DAY")).toString());
            }
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        return datesOut;
    }
    
    public static void addNewDate(Date date){
        try{
            SQLStatement=connection.prepareStatement("INSERT INTO DATES (DAY) VALUES (?)");
            SQLStatement.setDate(1, date);
            SQLStatement.executeUpdate();           
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
}
