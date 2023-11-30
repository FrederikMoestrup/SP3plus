import java.util.ArrayList;

public abstract class AMedia implements Media {
    ArrayList<String> genre = new ArrayList<>();
    String releaseYear;
    String titel;
    float rating;

    public AMedia(String titel, ArrayList<String> genre, String releaseYear, float rating){
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.titel = titel;
        this.rating = rating;

    }

    public void play(){
        System.out.println(titel + " is now playing");
    }

    public ArrayList<String> getGenre(){
        return genre;
    }

    public String getTitel() {
        return titel;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public float getRating() {
        return rating;
    }
}
