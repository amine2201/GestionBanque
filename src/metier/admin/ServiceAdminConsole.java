package metier.admin;

import dao.daoFiles.ClientDao;
import dao.daoFiles.CompteDao;
import metier.Verifiable;
import metier.forms.ClientFormValidator;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static metier.InteractiveConsole.clavier;

public class ServiceAdminConsole implements IServiceAdmin{
    private Banque banque;
    private ClientDao clientDao;
    private CompteDao compteDao;
    private static final String RESET = ConsoleColors.RESET.getValeur();
    private static final String RED = ConsoleColors.RED.getValeur();
    private static final String GREEN = ConsoleColors.GREEN.getValeur();
    public ServiceAdminConsole(Banque banque) {
        this.banque = banque;
        clientDao=new ClientDao();
    }

    @Override
    public Client nouveauClient() {
        String email, cin, tel, sexe, prenom, nom;
        System.out.print("| Entrer Le Nom: ");
        nom=clavier.nextLine();
        System.out.print("| Entrer Le Prenom: ");
        prenom=clavier.nextLine();
        System.out.print("| Entrer le CIN: ");
        cin=clavier.nextLine();
        System.out.print("| Entrer L'Email: ");
        email=clavier.nextLine();
        System.out.print("| Entrer Le tel: ");
        tel=clavier.nextLine();
        System.out.print("| Entrer votre sexe (H/F): ");
        sexe=clavier.nextLine();
        ClientFormValidator clientFormValidator=new ClientFormValidator();
        Client client = clientFormValidator.validerUtilisateur(prenom,nom,email,"password","password",cin,tel,sexe);
        if(client!=null){
        banque.getClientsDeBanque().add(client);
        System.out.println("| "+GREEN+ "Client "+client.getNomComplet()+" ajoute"+RESET);}
        else{
            Map<String, String> errors=clientFormValidator.getErrors();
            for(String field : errors.keySet()){
                System.out.println("| "+RED+errors.get(field)+RESET);
            }
        }
        clientDao.save(client);
        return client;
    }

    @Override
    public Client nouveauCompteClientExistant() {
        System.out.print("| Entrer numero Client: ");
        long id=clavier.nextLong();clavier.nextLine();
        Client client=chercherClientParId(id);
        if(client!=null){
            compteDao=new CompteDao(client);
            Compte compte=new Compte();
            compte.setLog(TypeLog.CREATION,"pour le client "+client.getNomComplet());
            compte.setPropriétaire(client);
            System.out.print("| Solde initial (Y/N): ");
            if(clavier.nextLine().equals("Y")){
                double solde;
                System.out.print("| Entrer le solde: ");
                String line =clavier.nextLine();
                if(Verifiable.isDecimal(line)) {
                    solde=Double.parseDouble(line);
                if (solde>0)
                    compte.setSolde(solde);
                }
                else System.out.println("| "+RED+"Solde invalide"+RESET);

            }
            client.getComptesClient().add(compte);
            compteDao.save(compte);
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
            ClientFormValidator clientFormValidator=new ClientFormValidator();
            switch (filtre){
                case "nom":
                    String nom;
                    System.out.print("| Entrer le nouveau nom: ");
                    nom = clavier.nextLine();
                    clientFormValidator.validerNom(nom,client);
                    if(clientFormValidator.getErrors().size()==0){
                    System.out.println("|"+GREEN +" Nom change "+ RESET);
                    clientDao.update(client);
                    }
                    else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_NOM)+RESET);
                    break;
                case "prenom":
                    String prenom;
                    System.out.print("| Entrer le nouveau prenom: ");
                    prenom = clavier.nextLine();
                    clientFormValidator.validerNom(prenom,client);
                    if(clientFormValidator.getErrors().size()==0){
                        System.out.println("|"+GREEN +" prenom change "+ RESET);
                        clientDao.update(client);
                    }
                    else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_PRENOM)+RESET);
                    break;
                case "mdp":
                    String mdp,mdpc;
                    System.out.print("| Entrer le nouveau mot de passe: ");
                    mdp=clavier.nextLine();
                    System.out.print("| Confirmer votre mot de passe: ");
                    mdpc=clavier.nextLine();
                    clientFormValidator.validerPass(mdp,mdpc,client);
                    if(clientFormValidator.getErrors().size()==0){
                        System.out.println("|"+GREEN +" mot de passe change "+ RESET);
                        clientDao.update(client);
                    }
                    else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_PASS)+RESET);
                    break;
                case "email":
                    String email;
                    System.out.print("| Entrer le nouveau email: ");
                    email = clavier.nextLine();
                    clientFormValidator.validerEmail(email,client);
                    if(clientFormValidator.getErrors().size()==0){
                        System.out.println("|"+GREEN +" Email change "+ RESET);
                        clientDao.update(client);
                    }
                    else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_EMAIL)+RESET);
                    break;

                case "num":
                    String num;
                    System.out.print("| Entrer le nouveau numero: ");
                    num = clavier.nextLine();
                    clientFormValidator.validerTel(num,client);
                    if(clientFormValidator.getErrors().size()==0){
                        System.out.println("|"+GREEN +" Tel change "+ RESET);
                        clientDao.update(client);
                    }
                    else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_TEL)+RESET);
                    break;
                case "cin":
                    String cin;
                    System.out.print("| Entrer le nouveau CIN: ");
                    cin = clavier.nextLine();
                    clientFormValidator.validerCIN(cin,client);
                    if(clientFormValidator.getErrors().size()==0){
                        System.out.println("|"+GREEN +" CIN change "+ RESET);
                        clientDao.update(client);
                    }
                    else System.out.println("| "+RED+clientFormValidator.getErrors().get(ClientFormValidator.CHAMP_CIN)+RESET);
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
        TableauDeBord tableauDeBord=new TableauDeBord();
        tableauDeBord.setNombreTotaleClient((long) banque.getClientsDeBanque().size());
        tableauDeBord.setNombreTotaleCompte((long) banque.getClientsDeBanque()
                .stream().map(client -> client.getComptesClient().size()).reduce(0, Integer::sum));
        tableauDeBord.setMaxSolde(banque.getClientsDeBanque()
                .stream().map(Client::getComptesClient)
                .flatMap(Collection::stream).map(Compte::getSolde)
                .max(Double::compareTo).orElse((double)0));
        tableauDeBord.setMinSolde(banque.getClientsDeBanque()
                .stream().map(Client::getComptesClient)
                .flatMap(Collection::stream).map(Compte::getSolde)
                .min(Double::compareTo).orElse((double)0));
        tableauDeBord.setNomClientLePlusRiche(Objects.requireNonNull(banque.getClientsDeBanque().stream()
                .filter(client -> client.getComptesClient()
                        .stream().anyMatch(compte -> compte.getSolde().equals(tableauDeBord.getMaxSolde()))).findFirst().orElse(null)).getNomComplet());
        tableauDeBord.setTotalClientsFemme((long) banque.getClientsDeBanque().stream().filter(client -> client.getSexe().equals(Sexe.FEMME)).count());
        tableauDeBord.setTotaleClientsHomme((long) banque.getClientsDeBanque().stream().filter(client -> client.getSexe().equals(Sexe.HOMME)).count());
        return tableauDeBord;
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
