public class Main {
    public static void main(String[] args) {
/*
        StreamingService stream = new StreamingService();
        stream.startMenu();


 */
        DBConnector dataBase = new DBConnector();
        dataBase.readMovieData();

    }
}