import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DBConnector implements FileEditor {
    static final String DB_URL = "jdbc:mysql://localhost/my_streaming";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "jegloggerindpåminserver4";

    public ArrayList<Media> readMovieData() {
        ArrayList<Media> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT genre, titel, year, rating FROM movie WHERE movieID BETWEEN 1 and 100";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name


                String titel = rs.getString("titel");
                String genre = rs.getString("genre");
                String year = rs.getString("year");
                float rating = rs.getFloat("rating");

                ArrayList<String> genreArrList = new ArrayList<>();
                String[] genreList = genre.split(", ");

                for (String g : genreList) {
                    genreArrList.add(g.trim());
                }
                Movie movie = new Movie(titel, genreArrList, year, rating);
                data.add(movie);

            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
return data;
    }

    public ArrayList<Media> readSeriesData() {
        ArrayList<Media> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT genre, titel, releaseYear, rating, seasons FROM series WHERE seriesID BETWEEN 1 and 100";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name


                String titel = rs.getString("titel");
                String genre = rs.getString("genre");
                String releaseYear = rs.getString("releaseYear");
                float rating = rs.getFloat("rating");
                String seasons = rs.getString("seasons");

                ArrayList<String> genreArrList = new ArrayList<>();
                String[] genreList = genre.split(". ");

                for (String g : genreList) {
                    genreArrList.add(g.trim());
                }
                String [] seasonsList =seasons.split("\\.");
                int seasonAmount = 0;
                HashMap<Integer, Integer> seasonEpisodeAmount = new HashMap<>();
                for (String se: seasonsList){
                    String [] seasonsEpisode = se.trim().split("-");

                    seasonEpisodeAmount.put(Integer.parseInt(seasonsEpisode[0].trim()),Integer.parseInt(seasonsEpisode[1].trim()));
                    seasonAmount++;
                }
                Series series = new Series(titel, genreArrList, releaseYear, rating,seasonAmount,seasonEpisodeAmount);
                data.add(series);

            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        return data;
    }


    public ArrayList<User> readUserData(String userPath) {
        ArrayList<User> users = new ArrayList<>();
        File userFile = new File(userPath);
        try {
            Scanner scan = new Scanner(userFile);
            for (int i = 0; scan.hasNextLine(); i++) {
                String split = scan.nextLine();
                String[] usersAndPasswordsAndAge = split.split(",");
                String username = usersAndPasswordsAndAge[0];
                String password = usersAndPasswordsAndAge[1];
                String age = usersAndPasswordsAndAge[2];
                int number = Integer.parseInt(age);
                User user = new User(username, password, false, number);
                users.add(user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return users;
    }

    public void writeUserData(String userPath, ArrayList<User> users) {
        try {

            FileWriter writer = new FileWriter(userPath);
            for (User s : users) {
                String userSave = s.getUsername() + "," + s.getPassword() + "," + s.getAge() + "\n";
                writer.write(userSave);

            }
            writer.close();


        } catch (IOException e) {
            System.out.println("File not found");

        }

    }


    public void saveUserLists(String userListPath, ArrayList<User> users) {
        try {

            FileWriter writer = new FileWriter(userListPath);
            for (User u : users) {
                String userToWatchList = u.getUsername() + " toWatch; ";
                String userWatchedList = u.getUsername() + " watched; ";
                for (Media m : u.getToWatchList()) {
                    userToWatchList += m.getTitel() + "; ";
                }
                for (Media m : u.getWatchedList()) {
                    userWatchedList += m.getTitel() + "; ";
                }
                userToWatchList += "\n";
                userWatchedList += "\n";
                writer.write(userToWatchList);
                writer.write(userWatchedList);
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("File not found");

        }

    }

    public void loadUserLists(String userListPath, ArrayList<User> users, ArrayList<Media> allMedia) {
        File userFile = new File(userListPath);
        try {
            Scanner scan = new Scanner(userFile);
            int count = 0;
            Boolean mediaFound;
            for (int i = 0; scan.hasNextLine(); i++) {
                String split1 = scan.nextLine();
                String[] userTowatch = split1.split("; ");
                String split2 = scan.nextLine();
                String[] userWatched = split2.split("; ");

                for (i = 1; i < userTowatch.length; i++) {
                    mediaFound = false;
                    for (Media m : allMedia) {
                        if (userTowatch[i].equals(m.getTitel())) {
                            mediaFound = true;
                            (users.get(count)).addToWatchList(m);
                        }
                    }
                    if (!mediaFound) {
                        System.out.println("Media not found :(");
                    }
                }
                for (i = 1; i < userWatched.length; i++) {
                    mediaFound = false;
                    for (Media m : allMedia) {
                        if (userWatched[i].equals(m.getTitel())) {
                            mediaFound = true;
                            (users.get(count)).addWatchedList(m);
                        }
                    }
                    if (!mediaFound) {
                        System.out.println("Media not found :(");
                    }
                }
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

    }
}
