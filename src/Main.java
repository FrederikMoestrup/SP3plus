public class Main {
    public static void main(String[] args){

        DBConnector db = new DBConnector();

        User user = new User(2,"fred","123",false, 25);

        db.writeUserData(user);

       // StreamingService stream = new StreamingService();
        //stream.startMenu();
    }
}