import java.util.ArrayList;
import java.util.Scanner;

public class TextUI{
    protected Scanner scan = new Scanner(System.in);

    //shows a message and returns the user's input as a String
    public String getInput(){
        return scan.nextLine();
    }


    public void displayMessage(String msg){

        System.out.println(msg);

    }

    public void displayArrayList(ArrayList<Media> media){
        for (Media m : media){

            System.out.println(m+"\n"+"______________________________________________"+"");

        }

    }
    public int getUserAge(String msg){
        System.out.println(msg);

        String input = scan.nextLine();          //Give brugere et sted at placere sit svar og vente på svaret
        int num = 0;
        try {
            num = Integer.parseInt(input);       //Konvertere svaret til et tal

        }catch (NumberFormatException e){ //den sørger for hvis brugeren skriver noget som ikke er et tal så fanger den det med et formatException
            System.out.println("This was not a number. Please try again.");
            num = getUserAge(msg);

        }
        return num;

    }

    public float getNumericInput(String msg) {
        System.out.println(msg);
       String input = getInput();
       float num;
       try {
           num = Float.parseFloat(input);
       }catch (NumberFormatException var5){
           displayMessage("Please enter a number and use '.' instead of ','");
           num= this.getNumericInput(msg);
       }
       return num;
       }

    public int getNumericInputInt(String msg) {
        System.out.println(msg);
        String input = getInput();
        int num;
        try {
            num = Integer.parseInt(input);
        }catch (NumberFormatException var5){
            displayMessage("Please enter a number");
            num= this.getNumericInputInt(msg);
        }
        return num;
    }

}