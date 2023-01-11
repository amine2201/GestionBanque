package metier.clients;


import metier.forms.ClientFormValidator;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ConsoleColors;
import presentation.modele.util.Log;
import presentation.modele.util.TypeLog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static metier.InteractiveConsole.clavier;

public class ServiceClient implements IServiceClient{
    private Client client;
    private Compte compte;
    private Banque banque;
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    public ServiceClient(Client client,Banque banque){
        this.client=client;
        this.banque=banque;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client){
        this.client=client;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    @Override
    public boolean versement() {
        System.out.print("| Entrer le montant: ");
        Double solde=clavier.nextDouble();clavier.nextLine();
        if(solde>0){
            compte.setSolde(compte.getSolde()+solde);
            compte.setLog(TypeLog.VERSEMENT,"de "+solde);
            System.out.println("|"+GREEN + " Versement effectue" + RESET);
            return true;
        }
        System.out.println("|"+RED +" Solde invalide" + RESET);
        return false;
    }

    @Override
    public boolean retrait() {
        System.out.print("| Entrer le montant: ");
        Double solde=clavier.nextDouble();clavier.nextLine();
        if(solde<=compte.getSolde()){
            compte.setSolde(compte.getSolde()-solde);
            compte.setLog(TypeLog.RETRAIT,"de "+solde);
            System.out.println("|"+GREEN + " Retrait effectue" + RESET);
            return true;
        }
        System.out.println("|"+RED +" Solde insuffisant" + RESET);
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
            System.out.println("|"+GREEN + " Retrait effectue" + RESET);
            return true;
        }
        System.out.println("|"+RED +" Solde insuffisant" + RESET);
        return false;
    }

    @Override
    public boolean virement() {
        Long id;
        System.out.println("| Entrer le compte :");
        System.out.print("| b-co00");
        id=clavier.nextLong();clavier.nextLine();
        Compte compte1=chercherCompte(id);
        if(compte1!=null){
        System.out.print("| Entrer le montant: ");
        Double solde=clavier.nextDouble();clavier.nextLine();
        if(solde>0){
            compte.setSolde(compte.getSolde()+solde);
            compte.setLog(TypeLog.VIREMENT,"de "+solde);
            System.out.println("|"+GREEN + " Virement effectue" + RESET);
            return true;
        }
        System.out.println("|"+RED +" Solde invalide" + RESET);
        }
        else System.out.println("|"+RED +" Compte introuvable" + RESET);
        return false;
    }

    private Compte chercherCompte(Long id) {
        for(Client client : banque.getClientsDeBanque()){
            for(Compte compte : client.getComptesClient()){
                if(compte.getNumeroCompte().equals("b-co00"+id))
                    return compte;
            }
        }
        return null;
    }

    @Override
    public boolean modifierProfile(int choixModification) {
        ClientFormValidator clientFormValidator=new ClientFormValidator();
        switch (choixModification){
            case 1:
                String mdp,mdpc;
                System.out.print("| Entrer le nouveau mot de passe: ");
                mdp=clavier.nextLine();
                System.out.print("| Confirmer votre mot de passe: ");
                mdpc=clavier.nextLine();
                clientFormValidator.validerPass(mdp,mdpc,client);
                if(clientFormValidator.getErrors().size()==0)
                    System.out.println("|"+GREEN +" mot de passe change "+ RESET);
                else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_PASS)+RESET);
                break;
            case 2:
                String email;
                System.out.print("| Entrer le nouveau email: ");
                email = clavier.nextLine();
                clientFormValidator.validerEmail(email,client);
                if(clientFormValidator.getErrors().size()==0)
                    System.out.println("|"+GREEN +" Email change "+ RESET);
                else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_EMAIL)+RESET);
                break;
            case 3:
                String num;
                System.out.print("| Entrer le nouveau numero: ");
                num = clavier.nextLine();
                clientFormValidator.validerTel(num,client);
                if(clientFormValidator.getErrors().size()==0)
                    System.out.println("|"+GREEN +" Tel change "+ RESET);
                else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_TEL)+RESET);
                break;
        }
        return false;
    }

    @Override
    public void dernièresOpérations() {
        List<Log> logList = new ArrayList<>(compte.getLogs().subList(Math.max(compte.getLogs().size() - 3, 0), compte.getLogs().size()));
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
        System.out.println("| Entrer le compte: ");
        for(int i=0;i<client.getComptesClient().size();i++){
            System.out.println("| "+(i+1)+". "+client.getComptesClient().get(i).getNumeroCompte());
        }
        do{
            System.out.print("| Votre choix : ");
            choix=clavier.nextInt();clavier.nextLine();
            if(choix>=1 && choix<=client.getComptesClient().size())
                break;
            else System.out.println("|"+RED +" Choix invalide"+RESET);
        }while (true);
        compte=client.getComptesClient().get(choix-1);
        return client.getComptesClient().get(choix-1);
    }

    @Override
    public void afficherTicket() {
        System.out.println("idk");
    }

    @Override
    public List<Log> afficherLogDeType(TypeLog typeLog) {
        List<Log> logList=new ArrayList<>();
        logList.addAll(compte.getLogs().stream().filter(log -> log.getType().equals(typeLog))
                .collect(Collectors.toList()));
        return logList;
    }
}
