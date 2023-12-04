import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DBConnector  {

    // database URL
    //jdbc = java data base connectivity
    static final String DB_URL = "jdbc:mysql://localhost/my_streaming";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "fred1";





    public ArrayList<User> readUserData() {

        ArrayList<User> users = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");


            String sql = "SELECT userID, username, password, age FROM my_streaming.userdata";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()){
                //Retrieve by column name

                int userID = rs.getInt("userID");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                users.add(new User(userID,username,password,false,age));
            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        return users;
    }



    public void writeUserData(User user) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try{
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");

            String sql = "INSERT INTO my_streaming.userdata (userID, username, password, age) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);


            stmt.setInt(1, user.getUserID());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getAge());

            stmt.executeUpdate();


            //STEP 5: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

    }



    public void loadUserLists(ArrayList<User> users, ArrayList<Media> allMedia) {

        Boolean mediaFound;

        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");


            String sql = "SELECT userID, toWatch, watched FROM my_streaming.userlists";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()){
                //Retrieve by column name

                int userID = rs.getInt("userID");
                String toWatch = rs.getString("toWatch");
                String watched = rs.getString("watched");

                String [] arrToWatch = toWatch.split("; ");
                String [] arrWatched = toWatch.split("; ");

                for (String str: arrToWatch) {

                    mediaFound=false;
                    for(Media m : allMedia) {
                        if (str.equals(m.getTitel())){
                            mediaFound = true;
                            (users.get(userID-1)).addToWatchList(m);
                        }
                    }
                    if (!mediaFound){
                        System.out.println("Media not found :(");
                    }
                }

                for (String str: arrWatched) {

                    mediaFound = false;
                    for (Media m : allMedia) {
                        if (watched.equals(m.getTitel())) {
                            mediaFound = true;
                            (users.get(userID - 1)).addWatchedList(m);
                        }
                    }
                    if (!mediaFound) {
                        System.out.println("Media not found :(");
                    }
                }



            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

    }

    public void saveUserLists(ArrayList<User> users) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try{
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");

            //Empty table
            String sql = "TRUNCATE TABLE my_streaming.userlists";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();


            for(User u: users) {
                String userToWatchList = "";
                String userWatchedList = "";
                for (Media m : u.getToWatchList()) {
                    userToWatchList += m.getTitel() + "; ";
                }
                for (Media m : u.getWatchedList()) {
                    userWatchedList += m.getTitel() + "; ";
                }


                String sql2 = "INSERT INTO my_streaming.userlists (userID, toWatch, watched) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sql2);


                stmt.setInt(1, u.getUserID());
                stmt.setString(2, userToWatchList);
                stmt.setString(3, userWatchedList);

                stmt.executeUpdate();
            }

            //STEP 5: Clean-up environment
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try



    }





}