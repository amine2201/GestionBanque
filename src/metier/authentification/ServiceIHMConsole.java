package metier.authentification;

import metier.Verifiable;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.util.ConsoleColors;

import static metier.InteractiveConsole.clavier;

public class ServiceIHMConsole implements IServiceIHM{
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    private Banque banque;
    private  IAuth auth;

    public ServiceIHMConsole(Banque banque) {
        this.banque = banque;
        auth=new ServiceAuthConsole(banque);
    }

    @Override
    public int menuGlobal() {
        int choix;
        String s;
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Se connecter");
        System.out.println("| 2. Quitter");
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            String line =clavier.nextLine();
            if(Verifiable.isNumeric(line)) {
                choix=Integer.parseInt(line);
                if (choix == 1 || choix == 2)
                    break;
            }
            System.out.println("|"+RED +" Choix invalide"+RESET);
        }while (true);
        if(choix==1){
            auth.seConnecter();
        }
        return choix;
    }
}
