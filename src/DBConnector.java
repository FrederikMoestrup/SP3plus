import java.sql.*;
import java.util.ArrayList;

public class DBConnector  {

    // database URL
    static final String DB_URL = "jdbc:mysql://localhost/world";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "NEWPASS";



    public void readData() {

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
            String sql = "SELECT name, population FROM world.city WHERE id BETWEEN 1 and 10";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next()){
                //Retrieve by column name


                String name = rs.getString("Name");
                int population = rs.getInt("Population");
                System.out.println(name + ": " + population);

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

    public ArrayList<Media> readMovieData(){

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
            for (int i = 1; i < 101; i++) {



            //Trækker ID
                String sql = "SELECT movieID FROM StreamStream.movie WHERE movieID = ?";
                stmt = conn.prepareStatement(sql);


                stmt.setInt(1,i );

                ResultSet rs = stmt.executeQuery();
                int printMovieID= rs.getInt("movieID");
            //Trækker Genre

                sql = "SELECT genre FROM StreamStream.movie WHERE movieID = ?";
                stmt = conn.prepareStatement(sql);


                stmt.setInt(1,i );

                 rs = stmt.executeQuery();
                String printGenre= rs.getString("genre");

                String [] genreList = printGenre.split(",");
                ArrayList<String> genreArrList = new ArrayList<>();

                for (String g: genreList) {
                    genreArrList.add(g.trim());
                }
                //Trækker name ud

                sql = "SELECT name FROM StreamStream.movie WHERE movieID = ?";
                stmt = conn.prepareStatement(sql);


                stmt.setInt(1,i );

                rs = stmt.executeQuery();
                String printName= rs.getString("name");

            //Trækker Year

                sql = "SELECT year FROM StreamStream.movie WHERE movieID = ?";
                stmt = conn.prepareStatement(sql);


                stmt.setInt(1,i );

                rs = stmt.executeQuery();
                int printYear= rs.getInt("year");
                String yearString= Integer.toString(printYear);

            //Trækker Rating
                sql = "SELECT rating FROM StreamStream.movie WHERE movieID = ?";
                stmt = conn.prepareStatement(sql);


                stmt.setInt(1,i );

                rs = stmt.executeQuery();
                float printRating= rs.getFloat("rating");

                Movie movie = new Movie(printName,genreArrList,yearString,printRating);
            }

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
        return readMovieData();


    }
}
