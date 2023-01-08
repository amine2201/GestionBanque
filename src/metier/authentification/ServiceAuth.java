package metier.authentification;


import metier.admin.ServiceAdmin;
import metier.admin.ServiceIHMAdmin;
import metier.clients.ServiceClient;
import metier.clients.ServiceIHMClient;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.util.ConsoleColors;

import static metier.InteractiveConsole.clavier;

public class ServiceAuth implements IAuth {
    private Banque banque;
    private IServiceIHM service;
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    private Admin admin;
    public ServiceAuth(Banque banque) {
        this.banque=banque;
        admin=Admin.getInstance();
        service=new ServiceIHM();
    }

    @Override
    public void seConnecter() {
        int choix= service.menuGlobal();
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
            service=new ServiceIHMAdmin(banque);
            else System.out.println("|"+RED +" Login ou mot de passe incorrect"+RESET);
        }
        else {
            Client client=chercherClient(login,mdp);
            if(client!=null)
            service=new ServiceIHMClient(client,banque);
            else System.out.println("|"+RED +" Login ou mot de passe incorrect"+RESET);
        }
        if(service!=null)
            service.menuGlobal();
        SeDéconnecter();}
    }

    @Override
    public void SeDéconnecter() {
        service=new ServiceIHM();
        seConnecter();
    }

    private Client chercherClient(String login, String mdp){
       return banque.getClientsDeBanque()
                .stream().filter(client -> client.getLogin().equals(login)&&client.getMotDePasse().equals(mdp))
                .findAny().orElse(null);
    }
}
