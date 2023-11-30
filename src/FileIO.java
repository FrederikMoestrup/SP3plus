import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileIO implements FileEditor{


   public ArrayList<Media> readMovieData(String moviePath){
       ArrayList<Media> data = new ArrayList<>();
       File file = new File(moviePath);
       try {
           Scanner scan = new Scanner(file);
           while (scan.hasNextLine()) {
               String s = scan.nextLine();// Hele linjen vil stå i én string
               String [] row = s.split(";");

               String titel = row[0];

               String releaseYear = row[1].trim();

               String genre = row[2];

               String [] genreList = genre.split(",");

               ArrayList<String> genreArrList = new ArrayList<>();

               for (String g: genreList) {
                   genreArrList.add(g.trim());
               }

               row[3] = row[3].replace(',', '.').trim();

               float rating = Float.parseFloat(row[3]);

               Movie movie = new Movie(titel,genreArrList,releaseYear,rating);
               data.add(movie);

           }
       }catch(FileNotFoundException e){
           System.out.println("file not found");
       }
       return data;
   }

    public ArrayList<Media> readSeriesData(String seriesPath){
        ArrayList<Media> data = new ArrayList<>();
        File file = new File(seriesPath);
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String s = scan.nextLine();// Hele linjen vil stå i én string

                String [] row = s.split(";");

                String titel = row[0];

                String releaseYear = row[1].trim();

                String genre = row[2];

                String [] genreList = genre.split(", ");

                ArrayList<String> genreArrList = new ArrayList<>();

                for (String g: genreList) {
                    genreArrList.add(g.trim());
                }

                row[3] = row[3].replace(',', '.').trim();

                float rating = Float.parseFloat(row[3]);

                String seasons = row[4];

                String [] seasonsList = seasons.split(",");

                int seasonAmount = 0;

                HashMap<Integer, Integer> seasonEpisodeAmount = new HashMap<>();;

                for (String se: seasonsList) {

                    String [] seasonEpisode = se.split("-");

                    seasonEpisodeAmount.put(Integer.parseInt(seasonEpisode[0].trim()),Integer.parseInt(seasonEpisode[1].trim()));

                    seasonAmount++;

                }

                Series series = new Series(titel,genreArrList,releaseYear,rating, seasonAmount,seasonEpisodeAmount);
                data.add(series);

            }
        }catch(FileNotFoundException e){
            System.out.println("file not found");
        }
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
                User user = new User(1, username, password, false,number);
                users.add(user);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        return users;
    }

    public void writeUserData(String userPath, ArrayList<User> users) {
        try{

            FileWriter writer = new FileWriter(userPath);
            for(User s: users){
                String userSave = s.getUsername() + "," + s.getPassword() + "," + s.getAge()+"\n";
                writer.write(userSave);

            }
            writer.close();


        } catch(IOException e){
            System.out.println("File not found");

            }

}


    public void saveUserLists(String userListPath, ArrayList<User> users) {
        try{

            FileWriter writer = new FileWriter(userListPath);
            for(User u: users){
                String userToWatchList = u.getUsername()+" toWatch; ";
                String userWatchedList = u.getUsername()+" watched; ";
                for (Media m : u.getToWatchList()) {
                    userToWatchList +=m.getTitel()+"; ";
                }
                for (Media m : u.getWatchedList()) {
                    userWatchedList +=m.getTitel()+"; ";
                }
                userToWatchList+="\n";
                userWatchedList+="\n";
                writer.write(userToWatchList);
                writer.write(userWatchedList);
            }
            writer.close();

        } catch(IOException e){
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
                    mediaFound=false;
                    for(Media m : allMedia) {
                        if (userTowatch[i].equals(m.getTitel())){
                            mediaFound = true;
                            (users.get(count)).addToWatchList(m);
                        }
                    }
                    if (!mediaFound){
                        System.out.println("Media not found :(");
                    }
                }
                for (i = 1; i < userWatched.length; i++) {
                    mediaFound=false;
                    for(Media m : allMedia) {
                        if (userWatched[i].equals(m.getTitel())){
                            mediaFound = true;
                            (users.get(count)).addWatchedList(m);
                        }
                    }
                    if (!mediaFound){
                        System.out.println("Media not found :(");
                    }
                }
               count++;
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }

    }


}
