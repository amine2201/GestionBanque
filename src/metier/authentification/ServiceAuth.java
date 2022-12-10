package metier.authentification;


import metier.admin.ServiceAdmin;
import metier.clients.ServiceClient;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.util.ConsoleColors;

import static metier.InteractiveConsole.clavier;

public class ServiceAuth implements IAuth, IServiceIHM {
    private Banque banque;
    private IServiceIHM service;
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    private Admin admin;
    public ServiceAuth(Banque banque) {
        this.banque=banque;
        admin=Admin.getInstance();
    }

    @Override
    public void seConnecter() {
        int choix=menuGlobal();
        if(choix!=3){
        String login;
        String mdp;
        System.out.println("------------------------------------------------------");
        System.out.print("| Login: ");
        login=clavier.nextLine();
        System.out.print("| Mot de passe: ");
        mdp=clavier.nextLine();
        System.out.println("------------------------------------------------------");
        if(choix==1){
            if(admin.getLogin().equals(login)&&admin.getMotDePasse().equals(mdp))
            service=new ServiceAdmin(banque);
            else System.out.println("|"+RED +" Login ou mot de passe incorrect"+RESET);
        }
        else {
            Client client=chercherClient(login,mdp);
            if(client!=null)
            service=new ServiceClient(client,banque);
            else System.out.println("|"+RED +" Login ou mot de passe incorrect"+RESET);
        }
        if(service!=null)
            service.menuGlobal();
        SeDéconnecter();}
    }

    @Override
    public void SeDéconnecter() {
        service=null;
        seConnecter();
    }
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
    private Client chercherClient(String login, String mdp){
       return banque.getClientsDeBanque()
                .stream().filter(client -> client.getLogin().equals(login)&&client.getMotDePasse().equals(mdp))
                .findAny().orElse(null);
    }
}
