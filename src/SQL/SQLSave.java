package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLSave {
    private static String url = "jdbc:mysql://localhost:3306/?user=root";
    private static String username= "root";
    private static String password = "*********";

    public static void main(String[] args){

        try{
            Connection conn = DriverManager.getConnection(url,username,password);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
