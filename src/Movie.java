import java.util.ArrayList;

public class Movie extends AMedia{


    public Movie(String titel, ArrayList<String> genre, String releaseYear, float rating) {
        super(titel, genre, releaseYear, rating);
    }

    public void play(){
        System.out.println(titel + " is now playing");

    }
    public String getTitel(){
        return titel;
}
    public ArrayList<String> getGenre(){
        return genre;
    }
    public String getReleaseYear(){
        return releaseYear;
    }
    public float getRating(){
        return rating;
    }
    @Override
    public String toString(){
        return "\n Title:  " + titel
                + "\n Genre:  "+ genre
                + "\n Release Year:  " + releaseYear
                + "\n Rating:  " + rating+"\n";

    }

}
