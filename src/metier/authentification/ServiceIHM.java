package metier.authentification;

import metier.admin.ServiceAdmin;
import presentation.modele.util.ConsoleColors;

import static metier.InteractiveConsole.clavier;

public class ServiceIHM implements IServiceIHM{
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    @Override
    public int menuGlobal() {
        int choix;
        String s;
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Admin");
        System.out.println("| 2. Client");
        System.out.println("| 3. Quitter");
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            choix=clavier.nextInt();clavier.nextLine();
            if(choix==1 || choix ==2 || choix==3)
                break;

            System.out.println("|"+RED +" Choix invalide"+RESET);
        }while (true);
        return choix;
    }
}
