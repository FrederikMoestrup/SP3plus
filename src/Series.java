import java.util.ArrayList;
import java.util.HashMap;

public class Series extends AMedia {
    private int seasonAmount;
    private HashMap<Integer, Integer> seasonEpisodeAmount = new HashMap<>();

    public Series(String titel, ArrayList<String> genre, String releaseYear, float rating, int seasonAmount, HashMap<Integer, Integer> seasonEpisodeAmount) {
        super(titel, genre, releaseYear, rating);
        this.seasonAmount = seasonAmount;
        this.seasonEpisodeAmount = seasonEpisodeAmount;
    }


    public void play() {
        System.out.println(titel + " is now playing");
    }

    public String getTitel() {
        return titel;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public float getRating() {
        return rating;
    }

    public int getSeasonAmount() {
        return seasonAmount;
    }

    public int getSeasonEpisodeAmount(int seasonNum) {
        int episodeAmount = seasonEpisodeAmount.get(seasonNum);
        return episodeAmount;
    }

    public int getTotalepisodeAmount(){
        int sum = 0;

        for(int i = 1; i<=seasonAmount; i++){
            sum+=getSeasonEpisodeAmount(i);
        }

        return sum;
    }


    @Override
    public String toString() {
        return "\n Title:  " +titel
                + "\n Genre:  "+genre
                + "\n Release Year:  "+releaseYear
                + "\n Rating:  "+rating
                + "\n Season amount:  "+seasonAmount
                + "\n Total episode amount:  "+this.getTotalepisodeAmount()+"\n";
    }
}

