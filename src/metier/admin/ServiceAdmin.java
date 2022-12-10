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

public class ServiceAdmin implements IServiceAdmin{
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
        id=clavier.nextLong();clavier.nextLine();
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



}
