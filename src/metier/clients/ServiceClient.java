package metier.clients;


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

public class ServiceClient implements IServiceClient, IServiceIHMClient{
    private Client client;
    private Compte compte;
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    public ServiceClient(Client client){
        this.client=client;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client){
        this.client=client;
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
        System.out.print("| Entrer le montant: ");
        Double solde=clavier.nextDouble();clavier.nextLine();
        if(solde>0){
            compte.setSolde(compte.getSolde()+solde);
            compte.setLog(TypeLog.VIREMENT,"de "+solde);
            System.out.println("|"+GREEN + " Virement effectue" + RESET);
            return true;
        }
        System.out.println("|"+RED +" Solde invalide" + RESET);
        return false;
    }

    @Override
    public boolean modifierProfile(int choixModification) {
        switch (choixModification){
            case 1:
                String mdp;
                System.out.print("| Entrer le nouveau mot de passe: ");
                mdp=clavier.nextLine();
                System.out.print("| Confirmer votre mot de passe: ");
                if(clavier.nextLine().equals(mdp)){
                    client.setMotDePasse(mdp);
                    System.out.println("|"+GREEN +" mot de passe change "+ RESET);
                    return true;
                }
                else System.out.println("|"+RED +" confirmation incorrecte: "+ RESET);break;
            case 2:
                String email;
                System.out.print("| Entrer le nouveau email: ");
                email = clavier.nextLine();
                if (Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)){
                    client.setEmail(email);
                    System.out.println("|"+GREEN +" Email change "+ RESET);
                    return true;
                }
                else System.out.println("|"+RED +" Email invalide"+ RESET);break;
            case 3:
                String num;
                System.out.print("| Entrer le nouveau numero: ");
                num = clavier.nextLine();
                if(Pattern.matches("(^[+][0-9]{12,13}$)|[0-9]{10}",num)){
                    client.setTel(num);
                    System.out.println("|"+GREEN +" Num change "+ RESET);
                    return true;
                }
                else System.out.println("|"+RED +" Numero invalide"+ RESET);break;
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
        System.out.print("| Votre choix : ");
        do{
            choix=clavier.nextInt();clavier.nextLine();
            if(choix>=1 && choix<=client.getComptesClient().size())
                break;
            else System.out.println("|"+RED +" Choix invalide"+RESET);
        }while (true);
        return client.getComptesClient().get(choix-1);
    }

    @Override
    public void afficherTicket() {
        System.out.println("idk");
    }

    @Override
    public int menuGlobal() {
        compte=choisirCompte();
        int choix;
        do{
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Effectuer un versement");
        System.out.println("| 2. Effectuer un retrait");
        System.out.println("| 3. Effectuer un virement");
        System.out.println("| 4. Modifier votre profil");
        System.out.println("| 5. Afficher info compte");
        System.out.println("| 6. Afficher info client");
        System.out.println("| 7. Changer Compte");
        System.out.println("| 8. Se deconnecter");
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            choix=clavier.nextInt();clavier.nextLine();
            if(choix>0 && choix<9)
                break;
            else System.out.println("|"+RED +" Choix invalide"+RESET);
        }while (true);
        System.out.println("------------------------------------------------------");
        switch (choix){
            case 1:versement();break;
            case 2:menuRetrait();break;
            case 3:virement();break;
            case 4:menuModification();break;
            case 5:menuInformations();break;
            case 6:
                System.out.println(client);break;
            case 7: choisirCompte();break;
        }
        System.out.println("------------------------------------------------------");
        if(choix==8)
            break;
        }while (true);
        return choix;
    }

    @Override
    public int menuModification() {
        int choix;
        do{
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Modifier votre mot de passe");
        System.out.println("| 2. EModifier votre email");
        System.out.println("| 3. Modifier votre numero de telephone");
        System.out.println("| 4. Retourner au menu principal");
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            choix=clavier.nextInt();clavier.nextLine();
            if(choix>0 && choix<4)
                break;
            else System.out.println("|"+RED +" Choix invalide"+RESET);
        }while (true);
        System.out.println("------------------------------------------------------");
        modifierProfile(choix);
        System.out.println("------------------------------------------------------");
        if(choix==4)
            break;
        }while (true);
        return choix;
    }

    @Override
    public int menuRetrait() {
        int choix;
        do{
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Retrait de 50.0 dh");
        System.out.println("| 2. Retrait de 100.0 dh");
        System.out.println("| 3. Retrait de 200.0 dh");
        System.out.println("| 4. Retrait de 500.0 dh");
        System.out.println("| 5. Retrait d'un autre montant");
        System.out.println("| 6. Retour au menu principal");
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            choix=clavier.nextInt();clavier.nextLine();
            if(choix>0 && choix<7)
                break;
            else System.out.println("|"+RED +" Choix invalide"+RESET);
        }while (true);
        System.out.println("------------------------------------------------------");
        if(choix<=4)
            retrait(choix);
        else if(choix==5) retrait();
        System.out.println("------------------------------------------------------");
        if(choix==6) break;
        }while (true);
        return choix;
    }

    @Override
    public List<Log> afficherLogDeType(TypeLog typeLog) {
        List<Log> logList=new ArrayList<>();
        logList.addAll(compte.getLogs().stream().filter(log -> log.getType().equals(typeLog)).collect(Collectors.toList()));
        return logList;
    }

    @Override
    public int menuInformations() {
        int choix;
        do{
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Afficher solde");
        System.out.println("| 2. Afficher les 3 dernieres operations");
        System.out.println("| 3. Afficher tous le virements du compte");
        System.out.println("| 4. Afficher tous le retraits du compte");
        System.out.println("| 5. Afficher tous le versements du compte");
        System.out.println("| 6. Retourner au menu principal");
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            choix=clavier.nextInt();clavier.nextLine();
            if(choix>0 && choix<7)
                break;
            else System.out.println("|"+RED +" Choix invalide"+RESET);
        }while(true);
        System.out.println("------------------------------------------------------");
        switch (choix){
            case 1: afficherSolde(); break;
            case 2: dernièresOpérations(); break;
            case 3: System.out.println(afficherLogDeType(TypeLog.VIREMENT));break;
            case 4: System.out.println(afficherLogDeType(TypeLog.RETRAIT));break;
            case 5: System.out.println(afficherLogDeType(TypeLog.VERSEMENT));break;
        }
        System.out.println("------------------------------------------------------");
        if(choix==6)
            break;
        }while (true);
        return choix;
    }
}
