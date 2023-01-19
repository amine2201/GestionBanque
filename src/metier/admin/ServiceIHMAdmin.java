package metier.admin;

import metier.Verifiable;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ConsoleColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static metier.InteractiveConsole.clavier;

public class ServiceIHMAdmin implements IServiceIHMAdmin{
    private IServiceAdmin serviceAdmin;
    private Banque banque;
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    public ServiceIHMAdmin(Banque banque) {
        this.banque=banque;
        this.serviceAdmin = new ServiceAdmin(banque);
    }

    public IServiceAdmin getServiceAdmin() {
        return serviceAdmin;
    }

    public void setServiceAdmin(IServiceAdmin serviceAdmin) {
        this.serviceAdmin = serviceAdmin;
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
            serviceAdmin.modifierClient(filtre);

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
                    clients.add(serviceAdmin.chercherClientParId(id));break;
                case 2 :
                    String nom;
                    System.out.print("| Entrer le nom: ");
                    nom=clavier.nextLine();
                    clients.addAll(serviceAdmin.chercherClientParNom(nom));break;
                case 3 :
                    String prenom;
                    System.out.print("| Entrer le prenom: ");
                    prenom=clavier.nextLine();
                    clients.addAll(serviceAdmin.chercherClientParPrénom(prenom));break;
                case 4 :
                    String cin;
                    System.out.print("| Entrer le CIN: ");
                    cin=clavier.nextLine();
                    clients.add(serviceAdmin.chercherClientParCin(cin));break;
                case 5 :
                    String email;
                    System.out.print("| Entrer l'email: ");
                    email=clavier.nextLine();
                    clients.add(serviceAdmin.chercherClientParEmail(email));break;
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
                    comptes.add(serviceAdmin.chercherCompteParId(id));break;
                case 2:
                    Double solde;
                    System.out.print("| Entrer le solde: ");
                    solde=clavier.nextDouble();
                    comptes.addAll(serviceAdmin.chercherCompteParSolde(solde));break;
                case 3:
                    LocalDateTime date;
                    System.out.print("| Entrer le solde: ");
                    date=LocalDateTime.parse(clavier.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                    comptes.addAll(serviceAdmin.chercherCompteParDateCreation(date));break;
                case 4:
                    System.out.print("| Entrer l'ID:");
                    id=clavier.nextLong();clavier.nextLine();
                    Client client=serviceAdmin.chercherClientParId(id);
                    if(client!=null)
                        comptes.addAll(serviceAdmin.chercherCompteParPropriétaire(client));
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
            switch (choix) {
                case 1 -> serviceAdmin.nouveauClient();
                case 2 -> serviceAdmin.nouveauCompteClientExistant();
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
            if(serviceAdmin.supprimerClient(id))
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
            switch (choix) {
                case 1 -> menuTrieClient();
                case 2 -> menuTrieCompte();
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
            switch (choix) {
                case 1 -> clients.addAll(serviceAdmin.trierClientParNom());
                case 2 -> clients.addAll(serviceAdmin.trierClientParCin());
                case 3 -> clients.addAll(serviceAdmin.trierClientParEmail());
                case 4 -> clients.addAll(serviceAdmin.trierClientParAdresse());
                case 5 -> clients.addAll(serviceAdmin.trierClientParSoldeCompte());
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
            switch (choix) {
                case 1 -> comptes.addAll(serviceAdmin.trierComptesParSolde());
                case 2 -> comptes.addAll(serviceAdmin.trierComptesParDateDeCreation());
                case 3 -> comptes.addAll(serviceAdmin.trierComptesParNomPropriétaire());
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
//            System.out.println("| 6. Menu Informations");
//            System.out.println("| 7. Menu Comptabilite");
            System.out.println("| 6. Tableau de bord");
            System.out.println("| 7. Se deconnecter");
            System.out.println("------------------------------------------------------");
            choix = getChoix(1, 7);
            switch (choix) {
                case 1 -> menuAjout();
                case 2 -> menuRecherche();
                case 3 -> menuModification();
                case 4 -> menuSuppression();
                case 5 -> menuTrie();

//                case 6: menuInformations();break; //not yet
//                case 7: menuComptabilité();break; //not yet
                case 6 -> tableauDeBord();
                //not yet
            }
            if (choix==7)
                break;
        }while (true);
        return 0;
    }
    private int getChoix(int d,int f) {
        int choix;
        System.out.println("------------------------------------------------------");
        do {
            System.out.print("| Votre choix: ");
            String line =clavier.nextLine();
            if(Verifiable.isNumeric(line)) {
                choix = Integer.parseInt(line);
                if (choix >= d && choix <= f)
                    break;
            }
            System.out.println("|"+RED +" Choix invalide"+RESET);
        }while(true);
        System.out.println("------------------------------------------------------");
        return choix;
    }
}
