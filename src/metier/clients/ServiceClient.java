package metier.clients;

import metier.authentification.IServiceIHM;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.Log;
import presentation.modele.util.TypeLog;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static metier.InteractiveConsole.clavier;

public class ServiceClient implements IServiceClient, IServiceIHMClient{
    private Client client;
    private Compte compte;
    public ServiceClient(Client client){
        this.client=client;
        compte=client.getComptesClient().get(0);
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client){
        this.client=client;
    }

    @Override
    public boolean versement() {
        int indexcompte=0;
        System.out.println("Entrer le montant:");
        Double solde=clavier.nextDouble();clavier.nextLine();
        if(solde>0){
            compte.setSolde(compte.getSolde()+solde);
            compte.setLog(TypeLog.VERSEMENT,"de "+solde);
        }
        System.out.println("montant invalide");
        return false;
    }

    @Override
    public boolean retrait() {
        int indexcompte=0;
        System.out.println("Entrer le montant:");
        Double solde=clavier.nextDouble();clavier.nextLine();
        if(solde<=compte.getSolde()){
            compte.setSolde(compte.getSolde()-solde);
            compte.setLog(TypeLog.RETRAIT,"de "+solde);
        }
        System.out.println("solde insuffisant");
        return false;
    }

    @Override
    public boolean retrait(int choixRetrait) {
        Double solde=0.0;
        switch (choixRetrait){
            case 1:solde=50.0;break;
            case 2:solde=100.0;break;
            case 3:solde=200.0;break;
            case 4:solde=500.0;break;
        }
        if(solde<=compte.getSolde()){
            compte.setSolde(compte.getSolde()-solde);
            compte.setLog(TypeLog.RETRAIT,"de "+solde);
        }
        System.out.println("solde insuffisant");
        return false;
    }

    @Override
    public boolean virement() {
        System.out.println("Entrer le montant:");
        Double solde=clavier.nextDouble();clavier.nextLine();
        if(solde>0){
            compte.setSolde(compte.getSolde()+solde);
            compte.setLog(TypeLog.VIREMENT,"de "+solde);
        }
        System.out.println("solde insuffisant");
        return false;
    }

    @Override
    public boolean modifierProfile(int choixModification) {
        switch (choixModification){
            case 1:
                String mdp;
                do {
                    System.out.println("Entrer le nv mdp");
                    mdp=clavier.nextLine();
                    System.out.println("Confirmation:");
                    if(clavier.nextLine().equals(mdp)){
                        client.setMotDePasse(mdp);
                        break;}
                    else System.out.println("confirmation incorrecte: ");
                }while (true);
                return true;
            case 2:
                String email;
                do {
                    System.out.println("Entrer le nv email: ");
                    email = clavier.nextLine();
                    if (Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)){
                        client.setEmail(email);
                        break;}
                    else System.out.println("Email invalide");
                }while (true);
                return true;
            case 3:
                String num;
                do {
                    System.out.println("Entrer le nv numero");
                    num = clavier.nextLine();
                    if(Pattern.matches("(^[+][0-9]{12,13}$)|[0-9]{10}",num)){
                        client.setTel(num);
                        break;}
                    else System.out.println("Num invalide");
                }while (true);
                return true;
        }
        return false;
    }

    @Override
    public void dernièresOpérations() {
        ArrayList<Log> logList = new ArrayList<Log>(compte.getLogs().subList(Math.max(compte.getLogs().size() - 3, 0), compte.getLogs().size()));
        for (Log log : logList){
            System.out.println(log);
        }
    }

    @Override
    public Double afficherSolde() {
        return compte.getSolde();
    }

    @Override
    public Compte choisirCompte() {
        int choix=0;
        System.out.println("Entrer le compte: ");
        for(int i=0;i<client.getComptesClient().size();i++){
            System.out.println(i+1+" "+client.getComptesClient().get(i).getNumeroCompte());
        }
        System.out.println("Votre choix :");
        do{
            choix=clavier.nextInt();clavier.nextLine();
            if(choix>=1 && choix<=client.getComptesClient().size())
                break;
            else System.out.println("Choix errone");
        }while (true);
        return client.getComptesClient().get(choix-1);
    }

    @Override
    public void afficherTicket() {
        System.out.println("idk");
    }

    @Override
    public int menuGlobal() {
        return 0;
    }

    @Override
    public int menuModification() {
        return 0;
    }

    @Override
    public int menuRetrait() {
        return 0;
    }

    @Override
    public int menuInformations() {
        return 0;
    }
}
