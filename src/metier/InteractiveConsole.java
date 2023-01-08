package metier;
import java.util.Scanner;

public interface InteractiveConsole {

    Scanner clavier = new Scanner(System.in);

    static void fermerClavier(){

            clavier.close();

    }


}
