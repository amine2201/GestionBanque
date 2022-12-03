package metier.admin;

import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.Sexe;
import presentation.modele.util.TableauDeBord;
import presentation.modele.util.TypeLog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static metier.InteractiveConsole.clavier;

public class ServiceAdmin implements IServiceAdmin{
    private Banque banque;

    public ServiceAdmin(Banque banque) {
        this.banque = banque;
    }

    @Override
    public Client nouveauClient() {
        String email, cin, tel;
        Sexe sexe;
        String prenom, nom;
        System.out.println("Entrer Le Nom: ");
        nom=clavier.nextLine();
        System.out.println("Entrer Le Prenom: ");
        prenom=clavier.nextLine();
        System.out.println("Entrer le CIN: ");
        cin=clavier.nextLine();
        System.out.println("Entrer L'Email: ");
        email=clavier.nextLine();
        System.out.println("Entrer Le tel: ");
        tel=clavier.nextLine();
        System.out.println("Entrer votre sexe: ");
        sexe=clavier.nextLine().equals("H")?Sexe.HOMME:Sexe.FEMME;
        Client client=new Client("Client","pass",nom,prenom,email,cin,tel,sexe);
        banque.getClientsDeBanque().add(client);
        return client;
    }

    @Override
    public Client nouveauCompteClientExistant() {
        System.out.println("Entrer numero Client");
        long id=clavier.nextLong();clavier.nextLine();
        Client client=chercherClientParId(id);
        if(client!=null){
            Compte compte=new Compte();
            compte.setLog(TypeLog.CREATION,"idk");
            compte.setPropriétaire(client);
            System.out.println("Solde initial (Y/N)");
            if(clavier.nextLine().equals("Y")){
                double solde;
                System.out.println("Entrer le solde: ");
                solde=clavier.nextLong();clavier.nextLine();
                compte.setSolde(solde);
            }
            client.getComptesClient().add(compte);
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
        List<Compte> comptes=new ArrayList<>();
        for (Client client : banque.getClientsDeBanque()){
            comptes.addAll(client.getComptesClient().stream().filter(compte -> compte.getPropriétaire().equals(propriétaire))
                    .collect(Collectors.toList()));
        }
        return comptes;
    }

    @Override
    public Client modifierClient(String filtre) {
        return null;
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
