import java.util.ArrayList;

public interface FileEditor {

    public ArrayList<Media> readMovieData(String path);
    public ArrayList<Media> readSeriesData(String path);

    public void writeUserData(String userPath, ArrayList<User> users);
}
