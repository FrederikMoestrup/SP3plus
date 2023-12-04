import java.util.ArrayList;
import java.util.Collections;

public class MediaLibrary {

    DBConnector db = new DBConnector();

    ArrayList<String> movieGenres = new ArrayList<>();
    ArrayList<String> seriesGenres = new ArrayList<>();
    ArrayList<String> mediaGenres = new ArrayList<>();

    ArrayList<Media> allMedia = new ArrayList<>();
    ArrayList<Media> allMovies = new ArrayList<>();
    ArrayList<Media> allSeries = new ArrayList<>();

    ArrayList<Media> allKidsMedia= new ArrayList<>();

    public ArrayList<String> getMovieGenres() {
        movieGenres.add("Crime");
        movieGenres.add("Drama");
        movieGenres.add("Biography");
        movieGenres.add("Sport");
        movieGenres.add("History");
        movieGenres.add("Romance");
        movieGenres.add("War");
        movieGenres.add("Mystery");
        movieGenres.add("Adventure");
        movieGenres.add("Family");
        movieGenres.add("Fantasy");
        movieGenres.add("Thriller");
        movieGenres.add("Horror");
        movieGenres.add("Film-Noir");
        movieGenres.add("Action");
        movieGenres.add("Sci-fi");
        movieGenres.add("Comedy");
        movieGenres.add("Musical");
        movieGenres.add("Western");
        movieGenres.add("Music");
        Collections.sort(movieGenres);

        return movieGenres;
    }
    public ArrayList<String> getSeriesGenres() {
        seriesGenres.add("Talk-show");
        seriesGenres.add("Documentary");
        seriesGenres.add("Crime");
        seriesGenres.add("Drama");
        seriesGenres.add("Action");
        seriesGenres.add("Adventure");
        seriesGenres.add("Comedy");
        seriesGenres.add("Fantasy");
        seriesGenres.add("Animation");
        seriesGenres.add("Horror");
        seriesGenres.add("Sci-fi");
        seriesGenres.add("War");
        seriesGenres.add("Thriller");
        seriesGenres.add("Mystery");
        seriesGenres.add("Biography");
        seriesGenres.add("History");
        seriesGenres.add("Family");
        seriesGenres.add("Western");
        seriesGenres.add("Romance");
        seriesGenres.add("Sport");
        Collections.sort(seriesGenres);

        return seriesGenres;
    }

    public ArrayList<String> getMediaGenres() {
        mediaGenres.add("Talk-show");
        mediaGenres.add("Documentary");
        mediaGenres.add("Crime");
        mediaGenres.add("Drama");
        mediaGenres.add("Action");
        mediaGenres.add("Adventure");
        mediaGenres.add("Comedy");
        mediaGenres.add("Fantasy");
        mediaGenres.add("Animation");
        mediaGenres.add("Horror");
        mediaGenres.add("Sci-fi");
        mediaGenres.add("War");
        mediaGenres.add("Thriller");
        mediaGenres.add("Mystery");
        mediaGenres.add("Biography");
        mediaGenres.add("History");
        mediaGenres.add("Family");
        mediaGenres.add("Western");
        mediaGenres.add("Romance");
        mediaGenres.add("Sport");
        mediaGenres.add("Film-Noir");
        mediaGenres.add("Musical");
        mediaGenres.add("Music");
        Collections.sort(mediaGenres);
        return mediaGenres;

    }
    public ArrayList<Media> getKidsMedia(){
        return makeGenreList(allMedia,"Family");
    }



    public ArrayList<Media> getAllMovies() {
        allMovies = db.readMovieData();
        return allMovies;
    }

    public ArrayList<Media> getAllSeries() {
        allSeries = db.readSeriesData();
        return allSeries;
    }

    public ArrayList<Media> getAllMedia(){

        allMedia = getAllMovies();

        for (Media s: getAllSeries()) {
            allMedia.add(s);
        }

        return allMedia;
    }


    public ArrayList<Media> makeGenreList(ArrayList<Media> mediaList, String genre){
        ArrayList<Media> genreList = new ArrayList<>();

        for (Media m : mediaList) {

            ArrayList<String> genres = new ArrayList<>();
            genres = m.getGenre();

            for (String g:genres) {
                if((g.toLowerCase()).equals(genre.toLowerCase())){
                    genreList.add(m);
            }


            }
        }

        return genreList;
    }
    public ArrayList<Media> makeMinimumRatingList(ArrayList<Media> mediaList, Float minRating){
        ArrayList<Media> ratingList = new ArrayList<>();

        for (Media m : mediaList) {
            if(m.getRating()>=minRating){
                ratingList.add(m);
            }
        }

        return ratingList;
    }
}
