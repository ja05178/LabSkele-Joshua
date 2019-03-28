package com.example.labskeletest;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAccess extends AsyncTask<Void , Void , Void> {

    ResultSet rs;
    private Connection conn;

    DBConfiguration dbc = new DBConfiguration();
    @SuppressLint("NewApi")
    public Connection getConnection(String user , String password , String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        DBConfiguration dbc = new DBConfiguration();
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionUrl = "jdbc:jtds:sqlserver://" + server + ";database=" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionUrl);

            //Statement stmt = connection.createStatement();
           // ResultSet test = stmt.executeQuery("select * from machine_info where host like '%1202%';");
        }catch (Exception e){
            e.printStackTrace();
          //  Toast.makeText(this , "Connection Failed" , Toast.LENGTH_LONG).show();
        }

        return connection;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            //DBConfiguration dbc = new DBConfiguration();
            conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
            if(conn == null){
                Log.i("A" , "Connection unsuccessful");
            }else{
                Log.i("A" , "Connection successful");
            }
        }catch (Exception e){
            Log.d("Error" , e.getMessage());
        }
        return null;
    }

    public ResultSet getComputers(String lab){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());

        String query = "Use lablocator; select * from machine_info where host like '%." + lab + "X0%' order by host ASC;";
        //String query = "select * from INFORMATION_SCHEMA.COLUMNS";
        try{

            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getOccupancy(String lab){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; select COUNT(*) AS InUse from machine_info where host LIKE '%" + lab + "%' AND occupied='1';";


        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

        }catch(SQLException e){

            e.printStackTrace();
        }
        return rs;
    }
    public ResultSet getTotalComputers(String lab){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; select COUNT(*) AS Total from machine_info where host LIKE '%" + lab + "%';";

        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
    public ResultSet getPreSetTotalComputers(String lab){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; select Total from labs where LabID LIKE '%" + lab + "%';";

        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public void saveFavorite(String UUID, String lab){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; Insert into favorites (UUID, LabID) values ('" + UUID + "','" + lab + "');";


        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //CEIT255 will need to be changed when the COBA building is added
    public void deleteFavorite(String UUID, String lab){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; Delete from favorites where UUID = '" + UUID + "' and LabID = '" + lab + "';";


        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String UUID){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; Insert into users (UUID) values ('" + UUID + "');";


        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet getLabs(String building){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; select LabID from labs where LabID like '%" + building + "%';";


        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getFavorites(String UUID){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; Select LabID from favorites where UUID = '" + UUID + "'";


        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getSoftware(String lab){
        conn = getConnection(dbc.getUserName() , dbc.getPassword() , dbc.getDb() , dbc.getServerName());
        String query = "Use lablocator; Select * from available_software where LabID = '" + lab + "'";


        try{
            Log.i("Conn status", String.valueOf(conn.isClosed()));
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
}