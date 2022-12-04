package metier.authentification;


import metier.admin.ServiceAdmin;
import metier.clients.ServiceClient;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;

import static metier.InteractiveConsole.clavier;

public class ServiceAuth implements IAuth, IServiceIHM {
    private Banque banque;
    private IServiceIHM service;
    private Admin admin;
    public ServiceAuth(Banque banque) {
        this.banque=banque;
        admin=Admin.getInstance();
    }

    @Override
    public void seConnecter() {
        int choix=menuGlobal();
        String login;
        String mdp;
        System.out.println("------------------------------------------------------");
        System.out.println("| Login :");
        login=clavier.nextLine();
        System.out.println("| Mot de passe :");
        mdp=clavier.nextLine();
        System.out.println("------------------------------------------------------");
        if(choix==1){
            if(admin.getLogin().equals(login)&&admin.getMotDePasse().equals(mdp))
            service=new ServiceAdmin(banque);
            else System.out.println("| Login ou mot de passe incorrect");
        }
        else {
            Client client=chercherClient(login,mdp);
            if(client!=null)
            service=new ServiceClient(client);
            else System.out.println("| Login ou mot de passe incorrect");
        }
        if(service!=null)
            service.menuGlobal();
        SeDéconnecter();
    }

    @Override
    public void SeDéconnecter() {
        service=null;
        seConnecter();
    }
    @Override
    public int menuGlobal() {
        int choix;
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Admin");
        System.out.println("| 2. Client");
        System.out.println("------------------------------------------------------");
        do {
            choix=clavier.nextInt();clavier.nextLine();
            if(choix==1 || choix ==2)
                break;

            else    System.out.println("choix invalide");
        }while (true);
        return choix;
    }
    private Client chercherClient(String login, String mdp){
       return banque.getClientsDeBanque()
                .stream().filter(client -> client.getLogin().equals(login)&&client.getMotDePasse().equals(mdp))
                .findAny().orElse(null);
    }
}
