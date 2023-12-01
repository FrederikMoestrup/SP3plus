import java.util.ArrayList;

public interface FileEditor {

  public ArrayList<Media> readMovieData();
  public ArrayList<Media> readSeriesData();

    public void writeUserData(String userPath, ArrayList<User> users);
}
