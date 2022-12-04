package metier.admin;

import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static metier.InteractiveConsole.clavier;

public class ServiceAdmin implements IServiceAdmin, IServiceIHMAdmin{
    private Banque banque;
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    public ServiceAdmin(Banque banque) {
        this.banque = banque;
    }

    @Override
    public Client nouveauClient() {
        String email, cin, tel;
        Sexe sexe;
        String prenom, nom;
        System.out.print("| Entrer Le Nom: ");
        nom=clavier.nextLine();
        System.out.print("| Entrer Le Prenom: ");
        prenom=clavier.nextLine();
        System.out.print("| Entrer le CIN: ");
        cin=clavier.nextLine();
        do{
        System.out.print("| Entrer L'Email: ");
        email=clavier.nextLine();
        if (Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email))
            break;
        System.out.println("|"+RED +" Email invalide"+ RESET);
        }while (true);
        do{
        System.out.print("| Entrer Le tel: ");
        tel=clavier.nextLine();
        if(Pattern.matches("(^[+][0-9]{12,13}$)|[0-9]{10}",tel))
            break;
            System.out.println("|"+RED +" Numero invalide"+ RESET);
        }while (true);
        System.out.print("| Entrer votre sexe (H/F): ");
        sexe=clavier.nextLine().equals("H")?Sexe.HOMME:Sexe.FEMME;
        Client client=new Client();
        client.setLogin("Client"+client.getId());
        client.setMotDePasse("pass");
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setCin(cin);
        client.setTel(tel);
        client.setSexe(sexe);
        client.setEmail(email);
        banque.getClientsDeBanque().add(client);
        System.out.println("| "+GREEN+ "Client "+client.getNomComplet()+" ajoute"+RESET);
        return client;
    }

    @Override
    public Client nouveauCompteClientExistant() {
        System.out.print("| Entrer numero Client: ");
        long id=clavier.nextLong();clavier.nextLine();
        Client client=chercherClientParId(id);
        if(client!=null){
            Compte compte=new Compte();
            compte.setLog(TypeLog.CREATION,"pour le client "+client.getNomComplet());
            compte.setPropriétaire(client);
            System.out.print("| Solde initial (Y/N): ");
            if(clavier.nextLine().equals("Y")){
                double solde;
                do{
                System.out.print("| Entrer le solde: ");
                solde=clavier.nextLong();clavier.nextLine();
                if (solde>0)
                    break;
                else System.out.println("| "+RED+"Solde invalide"+RESET);
                }while (true);
                compte.setSolde(solde);
            }
            client.getComptesClient().add(compte);
            System.out.println("| "+GREEN+"Compte "+compte.getNumeroCompte()+" cree "+RESET);
            return client;

        }
        return null;
    }

    @Override
    public Client chercherClientParId(Long id) {
        return banque.getClientsDeBanque()
                .stream().filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Client> chercherClientParNom(String nom) {
        return banque.getClientsDeBanque()
                .stream().filter(c -> c.getNom().equals(nom))
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> chercherClientParPrénom(String prenom) {
        return banque.getClientsDeBanque()
                .stream().filter(c -> c.getPrenom().equals(prenom))
                .collect(Collectors.toList());
    }

    @Override
    public Client chercherClientParCin(String cin) {
        return banque.getClientsDeBanque()
                .stream().filter(c -> c.getCin().equals(cin))
                .findFirst().orElse(null);
    }

    @Override
    public Client chercherClientParEmail(String email) {
        return banque.getClientsDeBanque()
                .stream().filter(c -> c.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    @Override
    public Compte chercherCompteParId(Long idCompte) {
        for(Client client : banque.getClientsDeBanque()){
            for(Compte compte : client.getComptesClient()){
                if(compte.getNumeroCompte().equals("b-co00"+idCompte))
                    return compte;
            }
        }
        return null;
    }

    @Override
    public List<Compte> chercherCompteParSolde(double solde) {
        List<Compte> comptes=new ArrayList<>();
        for (Client client : banque.getClientsDeBanque()){
            comptes.addAll(client.getComptesClient().stream().filter(compte -> compte.getSolde().equals(solde)).collect(Collectors.toList()));
        }
        return comptes;
    }

    @Override
    public List<Compte> chercherCompteParDateCreation(LocalDateTime date) {
        List<Compte> comptes=new ArrayList<>();
        for (Client client : banque.getClientsDeBanque()){
            comptes.addAll(client.getComptesClient().stream().filter(compte -> compte.getDateCreation().equals(date)).collect(Collectors.toList()));
        }
        return comptes;
    }

    @Override
    public List<Compte> chercherCompteParPropriétaire(Client propriétaire) {
        Client client=banque.getClientsDeBanque()
                .stream().filter(client1 -> client1.equals(propriétaire))
                .findFirst().orElse(null);
        if(client!=null)
            return client.getComptesClient();
        return null;
    }

    @Override
    public Client modifierClient(String filtre) {
        Client client;
        Long id;
        System.out.print("| Entrer l'ID du client: ");
        id=clavier.nextLong();
        client=chercherClientParId(id);
        if(client!=null){
            switch (filtre){
                case "nom":
                    String nom;
                    System.out.print("| Entrer le nouveau nom: ");
                    nom = clavier.nextLine();
                    client.setNom(nom);
                    System.out.println("|"+GREEN +" Nom change "+ RESET);
                    break;
                case "prenom":
                    String prenom;
                    System.out.print("| Entrer le nouveau prenom: ");
                    prenom = clavier.nextLine();
                    client.setPrenom(prenom);
                    System.out.println("|"+GREEN +" Prenom change "+ RESET);
                    break;
                case "mdp":
                    String mdp;
                    System.out.print("| Entrer le nouveau mot de passe: ");
                    mdp=clavier.nextLine();
                    System.out.print("| Confirmer votre mot de passe: ");
                    if(clavier.nextLine().equals(mdp)){
                        client.setMotDePasse(mdp);
                        System.out.println("|"+GREEN +" mot de passe change "+ RESET);
                    }
                    else System.out.println("|"+RED +" Confirmation incorrecte "+ RESET);
                    break;
                case "email":
                    String email;
                    System.out.print("| Entrer le nouveau email: ");
                    email = clavier.nextLine();
                    if (Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)){
                        client.setEmail(email);
                        System.out.println("|"+GREEN +" Email change "+ RESET);
                    }
                    else System.out.println("|"+RED +" Email invalide"+ RESET);break;
                case "num":
                    String num;
                    System.out.print("| Entrer le nouveau numero: ");
                    num = clavier.nextLine();
                    if(Pattern.matches("(^[+][0-9]{12,13}$)|[0-9]{10}",num)){
                        client.setTel(num);
                        System.out.println("|"+GREEN +" Num change "+ RESET);
                    }
                    else System.out.println("|"+RED +" Numero invalide"+ RESET);
                    break;
                case "cin":
                    String cin;
                    System.out.print("| Entrer le nouveau CIN: ");
                    cin = clavier.nextLine();
                    client.setCin(cin);
                    System.out.println("|"+GREEN +" Num change "+ RESET);
                    break;
            }
        }
        return client;
    }

    @Override
    public boolean supprimerClient(Long id) {
        Client client=chercherClientParId(id);
        if(client!=null)
            return banque.getClientsDeBanque().remove(client);
        return false;
    }

    @Override
    public TableauDeBord calculerEtAfficherStatistiques() {
        return null;
    }

    @Override
    public List<Client> trierClientParNom() {
        return banque.getClientsDeBanque()
                .stream().sorted((c1,c2)->c1.getNom().compareTo(c2.getNom()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> trierClientParCin() {
        return banque.getClientsDeBanque()
                .stream().sorted((c1,c2)->c1.getCin().compareTo(c2.getCin()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> trierClientParEmail() {
        return banque.getClientsDeBanque()
                .stream().sorted((c1,c2)->c1.getEmail().compareTo(c2.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> trierClientParAdresse() {
        return null;
    }

    @Override
    public List<Client> trierClientParSoldeCompte() {
        return banque.getClientsDeBanque()
                .stream().sorted((c1,c2)->soldeTotal(c1).compareTo(soldeTotal(c2)))
                .collect(Collectors.toList());
    }
    private Double soldeTotal(Client client){
        Double solde=0.0;
        for(Compte compte : client.getComptesClient()){
            solde+=compte.getSolde();
        }
        return solde;
    }
    @Override
    public List<Compte> trierComptesParSolde() {
        List<Compte> comptes= new ArrayList<>();
        banque.getClientsDeBanque().forEach(c-> comptes.addAll(c.getComptesClient()));
        return comptes.stream().sorted((c1,c2)->c1.getSolde().compareTo(c2.getSolde()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Compte> trierComptesParDateDeCreation() {
        List<Compte> comptes= new ArrayList<>();
        banque.getClientsDeBanque().forEach(c-> comptes.addAll(c.getComptesClient()));
        return comptes.stream().sorted((c1,c2)->c1.getDateCreation().compareTo(c2.getDateCreation()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Compte> trierComptesParNomPropriétaire() {
        List<Compte> comptes= new ArrayList<>();
        banque.getClientsDeBanque().forEach(c-> comptes.addAll(c.getComptesClient()));
        return comptes.stream().sorted((c1,c2)->c1.getPropriétaire().getNom().compareTo(c2.getPropriétaire().getNom()))
                .collect(Collectors.toList());
    }

    @Override
    public int menuModification() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Modifier Client");
            System.out.println("| 3. retour au menu principal");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 3);
            switch (choix){
                case 1:MenuModification();break;
            }
            if (choix==3)
                break;
        }while (true);
        return 0;
    }

    private int MenuModification() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Modifier le nom");
            System.out.println("| 2. Modifier le prenom");
            System.out.println("| 3. Modifier le mot de passe");
            System.out.println("| 4. Modifier l'email");
            System.out.println("| 5. Modifier le numero de telephone");
            System.out.println("| 6. Modifier le CIN");
            System.out.println("| 7. Retourner au menu principal");
            choix=getChoix(1,7);
            System.out.println("------------------------------------------------------");
            if(choix==7)
                break;
            String filtre="";
            switch (choix){
                case 1:filtre="nom";break;
                case 2:filtre="prenom";break;
                case 3:filtre="mdp";break;
                case 4:filtre="email";break;
                case 5:filtre="num";break;
                case 6:filtre="cin";break;
            }
            modifierClient(filtre);

        }while (true);
        return choix;
    }

    @Override
    public int menuRecherche() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Chercher client");
            System.out.println("| 2. Chercher compte");
            System.out.println("| 3. retour au menu principal");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 3);
            switch (choix){
                case 1 : menuRechercheClient();break;
                case 2 : menuRechercheCompte(); break;
            }
            if (choix==3)
                break;
        }while (true);
        return 0;
    }
    private int menuRechercheClient() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Chercher par ID");
            System.out.println("| 2. Chercher par Nom");
            System.out.println("| 3. Chercher par Prenom");
            System.out.println("| 4. Chercher par CIN");
            System.out.println("| 5. Chercher par Email");
            System.out.println("| 6. Retour au menu precedent");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 6);
            List<Client> clients=new ArrayList<>();
            switch (choix){
                case 1 :
                    Long id;
                    System.out.print("| Entrer l'ID:");
                    id=clavier.nextLong();clavier.nextLine();
                    clients.add(chercherClientParId(id));break;
                case 2 :
                    String nom;
                    System.out.print("| Entrer le nom: ");
                    nom=clavier.nextLine();
                    clients.addAll(chercherClientParNom(nom));break;
                case 3 :
                    String prenom;
                    System.out.print("| Entrer le prenom: ");
                    prenom=clavier.nextLine();
                    clients.addAll(chercherClientParPrénom(prenom));break;
                case 4 :
                    String cin;
                    System.out.print("| Entrer le CIN: ");
                    cin=clavier.nextLine();
                    clients.add(chercherClientParCin(cin));break;
                case 5 :
                    String email;
                    System.out.print("| Entrer l'email: ");
                    email=clavier.nextLine();
                    clients.add(chercherClientParEmail(email));break;
            }
            if (choix==6)
                break;
            if(clients.size()==0)
                System.out.println("| "+RED+" Aucun client trouve"+RESET);
            else System.out.println(clients);
        }while (true);
        return 0;
    }

    private int menuRechercheCompte() {
        int choix;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Chercher par ID");
            System.out.println("| 2. Chercher par Solde");
            System.out.println("| 3. Chercher par Date de creation");
            System.out.println("| 4. Chercher par Proprietaire");
            System.out.println("| 5. Retour au menu precedent");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 5);
            List<Compte> comptes = new ArrayList<>();
            switch (choix){
                case 1:
                    Long id;
                    System.out.print("| Entrer l'ID: ");
                    id=clavier.nextLong();
                    comptes.add(chercherCompteParId(id));break;
                case 2:
                    Double solde;
                    System.out.print("| Entrer le solde: ");
                    solde=clavier.nextDouble();
                    comptes.addAll(chercherCompteParSolde(solde));break;
                case 3:
                    LocalDateTime date;
                    System.out.print("| Entrer le solde: ");
                    date=LocalDateTime.parse(clavier.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                    comptes.addAll(chercherCompteParDateCreation(date));break;
                case 4:
                    System.out.print("| Entrer l'ID:");
                    id=clavier.nextLong();clavier.nextLine();
                    Client client=chercherClientParId(id);
                    if(client!=null)
                    comptes.addAll(chercherCompteParPropriétaire(client));
                    break;
            }
            if(choix==5)
                break;
            if(comptes.size()==0)
                System.out.println("| "+RED+" Aucun comtpe trouve"+RESET);
            else System.out.println(comptes);
        }while (true);
        return 0;
    }

    @Override
    public int menuInformations() {
        return 0;
    }

    @Override
    public int menuAjout() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Ajouter un nouveau client");
            System.out.println("| 2. Ajouter un nouveau compte pour un client");
            System.out.println("| 3. Retourner au menu principal");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 3);
            switch (choix){
                case 1 : nouveauClient();break;
                case 2 : nouveauCompteClientExistant(); break;
            }
            if (choix==3)
                break;
        }while (true);
        return 0;
    }

    @Override
    public int menuSuppression() {
        int choix;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Supprimer client");
            System.out.println("| 2. Retourner au menu principal");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 2);
            if (choix==2)
                break;
                System.out.print("| Entrer ID du client: ");
                Long id=clavier.nextLong();clavier.nextLine();
                if(supprimerClient(id))
                    System.out.println("| "+GREEN+" Client supprime"+RESET);
                else System.out.println("| "+RED+" Client inrouvable"+RESET);
        }while (true);
        return 0;
    }

    @Override
    public int tableauDeBord() {
        return 0;
    }

    @Override
    public int menuTrie() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Trier les clients");
            System.out.println("| 2. Trier les comptes");
            System.out.println("| 3. Retour au menu principal");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 3);
            switch (choix){
                case 1 : menuTrieClient();break;
                case 2 : menuTrieCompte(); break;
            }
            if (choix==3)
                break;
        }while (true);
        return 0;
    }

    private int menuTrieClient() {
        int choix;
        do{
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Trier par Nom");
            System.out.println("| 2. Trier par CIN");
            System.out.println("| 3. Trier par Email");
            System.out.println("| 4. Trier par Adresse");
            System.out.println("| 5. Trier par Solde");
            System.out.println("| 6. Retour au menu precedent");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 6);
            if(choix==6)
                break;
            List<Client> clients=new ArrayList<>();
            switch (choix){
                case 1:clients.addAll(trierClientParNom());break;
                case 2:clients.addAll(trierClientParCin());break;
                case 3:clients.addAll(trierClientParEmail());break;
                case 4:clients.addAll(trierClientParAdresse());break;
                case 5:clients.addAll(trierClientParSoldeCompte());break;
            }
            if(clients.size()==0)
                System.out.println("| "+RED+"Aucun compte est cree"+RESET);
            else System.out.println(clients);
        }while (true);
        return 0;
    }

    private int menuTrieCompte() {
        int choix;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("| 1. Trier par Solde");
            System.out.println("| 2. Trier par Date de creation");
            System.out.println("| 3. Trier par Proprietaire");
            System.out.println("| 4. Retour au menu precedent");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 4);
            List<Compte> comptes = new ArrayList<>();
            switch (choix){
                case 1:comptes.addAll(trierComptesParSolde());break;
                case 2:comptes.addAll(trierComptesParDateDeCreation());break;
                case 3:comptes.addAll(trierComptesParNomPropriétaire());break;
            }
            if(comptes.size()==0)
                System.out.println("| "+RED+"Aucun compte est cree"+RESET);
            else System.out.println(comptes);
            if(choix==4)
                break;
        }while (true)  ;
        return 0;
    }

    @Override
    public int menuComptabilité() {
        System.out.println("| pas encore disponible");
        return 0;
    }

    @Override
    public int menuGlobal() {
        int choix;
        do{
        System.out.println("------------------------------------------------------");
        System.out.println("| 1. Menu Ajout");
        System.out.println("| 2. Menu Recherche");
        System.out.println("| 3. Menu Modification");
        System.out.println("| 4. Menu Supression");
        System.out.println("| 5. Menu trie");
        System.out.println("| 6. Menu Informations");
        System.out.println("| 7. Menu Comptabilite");
        System.out.println("| 8. Tableau de bord");
        System.out.println("| 9. Se deconnecter");
        System.out.println("------------------------------------------------------");
        choix = getChoix(1, 9);
        switch (choix){
            case 1: menuAjout(); break;
            case 2: menuRecherche();break;
            case 3: menuModification();break; //not yet
            case 4: menuSuppression();break;
            case 5: menuTrie();break;
            case 6: menuInformations();break; //not yet
            case 7: menuComptabilité();break; //not yet
            case 8: tableauDeBord();break; //not yet
        }
        if (choix==9)
            break;
        }while (true);
        return 0;
    }
    private int getChoix(int d,int f) {
        int choix;
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            choix=clavier.nextInt();clavier.nextLine();
            if(choix>=d && choix<=f)
                break;
            else System.out.println("|"+RED +" Choix invalide"+RESET);
        }while(true);
        System.out.println("------------------------------------------------------");
        return choix;
    }
}
