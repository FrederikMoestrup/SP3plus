import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class StreamingService {
    private ArrayList<User> users = new ArrayList<>();

    private ArrayList<Media> media = new ArrayList<>();
    private ArrayList<Media> movies = new ArrayList<>();
    private ArrayList<Media> series = new ArrayList<>();


    private User currentUser;
    private ArrayList<Media> currentList;
    private int mediaType;


    private TextUI ui = new TextUI();
    private  DBConnector db = new DBConnector();
    private MediaLibrary library = new MediaLibrary();

    public void startMenu(){

        ui.displayMessage("\n" + "Hello and welcome to StreamStream! \n" +
                "\n" +
                "Please choose one of the following options:" + "\n" +
                "1. Sign in to an existing user \n" +
                "2. Create a new user" + "\n" +
                "\n" + "0. Exit" + "\n");
        initializeLibrary();
        users = db.readUserData();
        db.loadUserLists(users, media);
        String input = ui.getInput();
       switch(input){
           case "1":
               logIn();
               break;
           case "2":
               signUp();
               break;
           case "0":
               System.exit(0);
               break;
           default:
               invalidInput();
               break;
       }

    }
    private void mainMenu(){

        if(currentUser.getAge()<15){
            kidsMenu();
        }
        else {

            ui.displayMessage("\n"+"Please select your desired option from the menu below\n" + "\n" +
                    "1. Search for media" + "\n" +
                    "2. Find media sorted by genre" + "\n" +
                    "3. Find media sorted by rating" + "\n" +
                    "4. Show plan to watch list" + "\n" +
                    "5. Show media history" + "\n" +
                    "\n"+"9. Log out" + "\n" +
                    "0. Exit" + "\n");

            String input = ui.getInput();
            switch (input) {
                case "1":
                    pickMediaType();
                    searchMedia();
                    listEmpty();
                    mediaChoice(pickMedia(currentList));

                    break;
                case "2":
                    pickMediaType();
                    searchGenre();
                    listEmpty();
                    mediaChoice(pickMedia(currentList));
                    break;
                case "3":
                    pickMediaType();
                    searchRating();
                    listEmpty();
                    mediaChoice(pickMedia(currentList));
                    break;
                case "4":
                    showToWatchlist();
                    pickFromListOrMainMenu();
                    mediaChoiceInPTW(pickMedia(currentList));
                    break;
                case "5":
                    showHistory();
                    pickFromListOrMainMenu();
                    mediaChoice(pickMedia(currentList));
                    break;

                case "9":
                    db.saveUserLists(users);
                    ui.displayMessage("\n" + "You have been logged out.");
                    startMenu();
                    break;
                case "0":
                    db.saveUserLists(users);
                    ui.displayMessage("\n" + "Thank you for using StreamStream.");
                    System.exit(0);
                    break;
                default:
                    invalidInputMainMenu();
                    break;

            }
        }

        }



    private void kidsMenu(){

        ui.displayMessage("\n"+"Please select your desired option from the menu below\n" + "\n" +
                "1. Search for media" + "\n" +
                "2. Find media sorted by genre" + "\n" +
                "3. Find media sorted by rating" + "\n" +
                "4. Show plan to watch list" + "\n" +
                "5. Show media history" + "\n" +
                "\n"+"9. Log out" + "\n" +
                "0. Exit" + "\n");

            String input = ui.getInput();
            switch (input) {
                case "1":
                    pickMediaType();
                    searchMedia();
                    listEmpty();
                    mediaChoice(pickMedia(currentList));
                    break;
                case "2":
                    pickMediaType();
                    searchGenre();
                    listEmpty();
                    mediaChoice(pickMedia(currentList));
                    break;
                case "3":
                    pickMediaType();
                    searchRating();
                    listEmpty();
                    mediaChoice(pickMedia(currentList));
                    break;
                case "4":
                    showToWatchlist();
                    pickFromListOrMainMenu();
                    mediaChoiceInPTW(pickMedia(currentList));
                    break;
                case "5":
                    showHistory();
                    pickFromListOrMainMenu();
                    mediaChoice(pickMedia(currentList));
                    break;
                case "9":
                    db.saveUserLists(users);
                    ui.displayMessage("\n" + "You have been logged out.");
                    startMenu();
                    break;
                case "0":
                    db.saveUserLists(users);
                    ui.displayMessage("\n" + "Thank you for using StreamStream.");
                    System.exit(0);
                    break;
                default:
                    invalidInputMainMenu();
                    break;

            }

        }

    private void listEmpty() {
        if(currentList.isEmpty()){
            ui.displayMessage("Your list is empty please try again"+"\n");
            mainMenu();
        }
    }

    public void searchMedia() {

        ui.displayMessage("Search for the title you want to watch" + "\n" + "0. Go to main menu" + "\n");

        String input = ui.getInput();
        if (!input.equals("0")) {
            ArrayList<Media> results = new ArrayList<>();
            HashSet<String> uniqueList = new HashSet<>(); //Der kan kun være en af hver
            for (Media m : currentList) {
                String title = m.getTitel();
                if (title.toLowerCase().contains(input.toLowerCase()) && uniqueList.add(title.toLowerCase())) {
                    results.add(m);
                }
            }
            currentList = results;
            ui.displayArrayList(currentList);
            sortFurther();
        } else if (input.equals("0")) {
            mainMenu();
        }
    }
    public void searchGenre() {
        ui.displayMessage("Type in your genre you want to find" + "\n" + "0. Go to main menu" + "\n");

        if(mediaType==1) {
            System.out.println(library.getMovieGenres());
        }
        else if (mediaType==2){
            System.out.println(library.getSeriesGenres());
        }
        else if (mediaType==3){
            System.out.println(library.getMediaGenres());
        }
        else{
            ui.displayMessage("Something went wrong with your media type. Try again.");
            mainMenu();
        }


        String input = ui.getInput();
        if (!input.equals("0")) {
        currentList = library.makeGenreList(currentList,input);
        ui.displayArrayList(currentList);
        sortFurther();

        } else if (input.equals("0")) {
            mainMenu();
        } else{
            ui.displayMessage("please enter a correct genre");
            searchGenre();
        }
    }

    public void searchRating() {
       String input = String.valueOf(ui.getNumericInput("Sort by rating. What is the minimum rating media should have?" + "\n" + "0. Go to main menu" + "\n"));


        if (!input.equals("0")) {
            //Dette kan gå galt hvis bruger ikke skriver en float
            float rating = Float.parseFloat(input);
            currentList = library.makeMinimumRatingList(currentList, rating);
            ui.displayArrayList(currentList);
            sortFurther();

        } else if (input.equals("0")) {
            mainMenu();
        }
    }

    private void pickFromListOrMainMenu(){
        ui.displayMessage("1. Pick media from list\n"+
                "0. Go back to main menu");
        String input = ui.getInput();
        switch (input){
            case"1":
                break;
            case"0":
                mainMenu();
                break;
            default:
                invalidInputMainMenu();
                break;
        }

    }

    private void showHistory() {
        if(currentUser.getWatchedList().isEmpty()){
            ui.displayMessage("Your media history list is empty. Go watch some media :)"+"\n");
            mainMenu();
        }else {
            currentList=currentUser.getWatchedList();
            ui.displayArrayList(currentList);

        }
    }

    private void showToWatchlist() {
        if(currentUser.getToWatchList().isEmpty()){
            ui.displayMessage("Your plan to watch list is empty. When you find media you can add it to your plan to watch list."+"\n");
            mainMenu();
        }else {
            currentList=currentUser.getToWatchList();
            ui.displayArrayList(currentList);
        }
    }


    private void signUp(){

        ui.displayMessage("Please enter a username.");
        String userInput = ui.scan.nextLine();
        ui.displayMessage("Please enter a password.");
        String passwordInput = ui.scan.nextLine();
       int age = ui.getUserAge("Please enter your age");
        User user = new User(users.size()+1,userInput, passwordInput, false,age);

        users.add(user);
        ui.displayMessage("Thank you for signing up , " + userInput + ".");

        db.writeUserData(user);

        startMenu();

        }


    private void logIn(){

        ui.displayMessage("Please enter your username.");
        String userInput = ui.getInput();
        ui.displayMessage("Please enter your password.");
        String passwordInput = ui.getInput();
        boolean loggingin = false;
        for(User c: users) {

            if (userInput.equals(c.getUsername()) && passwordInput.equals(c.getPassword())) {
                ui.displayMessage("Logging in. Stand by.");
                currentUser = c;
                loggingin = true;
                mainMenu();
            }
        }


            if(!loggingin){
                invalidUserPass();
            }
        }



    private void initializeLibrary(){
        movies = library.getAllMovies();
        series = library.getAllSeries();
        media = library.getAllMedia();

    }

    private void invalidInput(){

        ui.displayMessage("Your input was invalid. Press 1 to be redirected to the start menu.");
        if(ui.getInput().equals("1")) {

            startMenu();

        } else{
            invalidInput();
        }
    }
    private void invalidInputMainMenu(){
        ui.displayMessage("Your input was invalid. Press 1 to be redirected to the main menu.");
        if(ui.getInput().equals("1")){
            mainMenu();
        } else {
            invalidInputMainMenu();
        }
    }

    private void invalidUserPass(){

        ui.displayMessage("Your username or password is invalid. Press 1 to be redirected to the start menu.");
        if(ui.getInput().equals("1")) {

            startMenu();

        } else{
            invalidInput();
        }
    }


    public void playMedia(Media m){
        m.play();
        currentUser.addWatchedList(m);
        try{

            Thread.sleep(5000);
            ui.displayMessage(m.getTitel() + " is now finished playing." + "\n" +
                    "\n"+
                    "Would you like to submit a rating for the content you just watched?" + "\n"
                    + "\n" +
                    "1. Yes" + "\n" +
                    "2. No");
            String input = ui.getInput();
            switch(input){
                case "1":
                    submitRating();
                    break;
                case "2":
                    break;
                default:
                    invalidInputMainMenu();
                    break;
            }
        } catch(InterruptedException e){
            System.out.println("An error occurred while playing.");
        }
        mainMenu();
    }

    private void mediaChoice(Media media){
        ui.displayMessage("1. Play "+media.getTitel()+"\n"+
                "2. Add to plan to watch list"+"\n"+
                "0. Go back to main menu");
        String input = ui.getInput();
      switch (input){
          case"1":
            playMedia(media);
            break;
          case"2":
              currentUser.addToWatchList(media);
              mediaChoice(media);
              break;
          case"0":
              mainMenu();
              break;
          default:
              invalidInputMainMenu();
      }
    }


    private void mediaChoiceInPTW(Media media){
        ui.displayMessage("1. Play "+media.getTitel()+"\n"+
                "2. Remove from plan to watch list"+"\n"+
                "0. Go back to main menu");
        String input = ui.getInput();
        switch (input){
            case"1":
                playMedia(media);
                break;
            case"2":
                currentUser.removeFromWatchList(media);

                ui.displayMessage("1. Show plan to watch list"+"\n"+
                        "0. Go back to main menu");
                String inputM = ui.getInput();
                switch (inputM) {
                    case "1":
                    showToWatchlist();
                    mediaChoiceInPTW(pickMedia(currentList));
                    break;
                    case "0":
                        mainMenu();
                        break;
                    default:
                        invalidInput();
                        break;

                }
                break;
            case"0":
                mainMenu();
                break;
            default:
                invalidInputMainMenu();
                break;
        }
    }



    private Media pickMedia(ArrayList<Media> list){
        ui.displayMessage("Pick media from the list above by writing a titel.");
        String input = ui.getInput();
        Media pickedMedia;
        for (Media m : list) {
            if(input.equalsIgnoreCase(m.getTitel())){
                pickedMedia= m;
                return pickedMedia;
            }
        }

        ui.displayMessage("The titel you wrote doesn't match your list. Try again.");
        return pickMedia(list);
    }
    private void pickMediaType(){
        ui.displayMessage("Please select what type of media you want to search for\n"+"\n"+
                "1. Movies"+"\n"+
                "2. Series"+ "\n"+
                "3. Both"+ "\n"+
                "\n"+"0. Back to main menu");
        String inputM = ui.getInput();

        if(currentUser.getAge()<15){
            switch (inputM) {
                case "1":
                    mediaType = 1;
                    currentList = library.makeGenreList(movies,"Family");
                    break;
                case "2":
                    mediaType = 2;
                    currentList = library.makeGenreList(series,"Family");
                    break;
                case "3":
                    mediaType = 3;
                    currentList = library.makeGenreList(media,"Family");
                    break;
                case "0":
                    mainMenu();
                    break;
                default:
                    invalidInputMainMenu();
            }

        }else {

            switch (inputM) {
                case "1":
                    mediaType = 1;
                    currentList = movies;
                    break;
                case "2":
                    mediaType = 2;
                    currentList = series;
                    break;
                case "3":
                    mediaType = 3;
                    currentList = media;
                    break;
                case "0":
                    mainMenu();
                    break;
                default:
                    invalidInputMainMenu();
            }
        }

    }

    private void submitRating(){


         int input =ui.getNumericInputInt("Please enter a rating between 1 and 10 that most accurately represents your opinion of the watched content, with 1 being the lowest and 10 being the highest.");
        if(input > 0 && input <= 10){
            ui.displayMessage("Thank you for submitting your rating!");
        }
        else{
            ui.displayMessage("Your input was invalid.");
            submitRating();
        }
    }

    private void sortFurther(){
        listEmpty();
        ui.displayMessage("Do you want to sort further?\n"+"\n"+
                "1. No"+"\n"+
                "2. Search further"+ "\n"+
                "3. Sort by genre"+ "\n"+
                "4. Sort by rating"+ "\n"+
                "\n"+"0. Back to main menu");
        String input = ui.getInput();
        switch (input) {
            case "1":
                break;
            case "2":
                searchMedia();
                break;
            case "3":
                searchGenre();
                break;
            case "4":
                searchRating();
                break;
            case "0":
                mainMenu();
                break;
            default:
                invalidInputMainMenu();
        }

    }


}
