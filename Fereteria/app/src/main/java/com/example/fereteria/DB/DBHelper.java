package com.example.fereteria.DB;

import static java.sql.DriverManager.getConnection;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    @SuppressLint("NewApi")

    public static Connection CONN() {

        String _user = "andy";
        String _pass = "1234";
        String _DB = "ferreteria";
        String _server = "192.168.88.25:4123";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String connectionString = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionString = "jdbc:jtds:sqlserver://" + _server + ";"
                    + "databaseName=" + _DB + ";user=" + _user + ";password="
                    + _pass + ";";
            conn = DriverManager.getConnection(connectionString);
        }
        catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        }
        catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return conn;
    }

}
