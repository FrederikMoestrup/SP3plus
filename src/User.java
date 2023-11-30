import java.util.ArrayList;

public class User {
    private int age;
    private Boolean administrator;
    private String username;
    private String password;
    private ArrayList<Media> toWatchList = new ArrayList<>();
    private  ArrayList<Media> watchedList = new ArrayList<>();

    User(String username, String password, boolean administrator,int age){
        this.age = age;
        this.username = username;
        this.password = password;
        this.administrator = administrator;

    }

    public void addToWatchList(Media media){
        toWatchList.add(media);

    }
    public void removeFromWatchList(Media media){
   toWatchList.remove(media);
    }

    public void addWatchedList(Media media){

        watchedList.add(media);

    }

    public  ArrayList<Media> getWatchedList(){


        return watchedList;

    }
    public ArrayList<Media> getToWatchList(){

            return toWatchList;

    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getAge(){
        return age;
    }

}