import java.util.ArrayList;

public interface FileEditor {

    public ArrayList<Media> readMovieData();
    public ArrayList<Media> readSeriesData();

    ArrayList<User> readUserData();

    void writeUserData(User user);

    void loadUserLists(ArrayList<User> users, ArrayList<Media> allMedia);

    void saveUserLists(ArrayList<User> users);



}
