package metier.authentification;


import metier.admin.ServiceAdmin;
import metier.admin.ServiceIHMAdmin;
import metier.clients.ServiceClient;
import metier.clients.ServiceIHMClient;
import metier.forms.LoginFormValidator;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Utilisateur;
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
        service=null;
    }

    @Override
    public void seConnecter() {
        String login;
        String mdp;
        System.out.println("------------------------------------------------------");
        System.out.print("| Login: ");
        login=clavier.nextLine();
        System.out.print("| Mot de passe: ");
        mdp=clavier.nextLine();
        System.out.println("------------------------------------------------------");
        LoginFormValidator loginFormValidator=new LoginFormValidator(banque);
        Utilisateur utilisateur=loginFormValidator.validerUtilisateur(login,mdp);
        if(utilisateur==null) System.out.println("|"+RED +" Login ou mot de passe incorrect"+RESET);
        else if(utilisateur.getRole().equals("Admin"))
            service=new ServiceIHMAdmin(banque);
        else if(utilisateur.getRole().equals("Client"))
            service=new ServiceIHMClient((Client) utilisateur,banque);
        if(service!=null)
            service.menuGlobal();
        SeDéconnecter();
    }

    @Override
    public void SeDéconnecter() {
        service=null;
    }
}
