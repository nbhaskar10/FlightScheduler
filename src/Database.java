
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Database {
    private static final String URL = "jdbc:derby://localhost:1527/FlightScheduler_Bhaskar_Nyshadham_bfn5035";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";
    
    private static Connection connection=null;
    
    public Database(){
    }
    
    public static Connection getConnection(){
       try{
            connection=DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            System.exit(1);
        }
        return connection;
    }


}
