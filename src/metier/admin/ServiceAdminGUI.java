package metier.admin;

import dao.daoFiles.ClientDao;
import dao.daoFiles.CompteDao;
import metier.forms.ClientFormValidator;
import metier.forms.CompteFormValidator;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ActionResult;
import presentation.modele.util.Sexe;
import presentation.modele.util.TableauDeBord;
import presentation.modele.util.TypeLog;

import java.util.*;


public class ServiceAdminGUI implements IServiceAdminGUI{
    private final Banque banque;
    private final ClientDao clientDao;
    public ServiceAdminGUI(Banque banque) {
        this.banque = banque;
        clientDao=new ClientDao();
    }
    @Override
    public ActionResult nouveauClient(String prenom, String nom, String email, String pass, String passConfirmation, String cin, String tel, String sexe) {
        ClientFormValidator clientFormValidator=new ClientFormValidator();
        Client client = clientFormValidator.validerUtilisateur(prenom,nom,email,pass,passConfirmation,cin,tel,sexe);
        if(client!=null){
            banque.getClientsDeBanque().add(client);
            clientDao.save(client);
            return nouveauCompte(client,0);
        }
            return new ActionResult(false,clientFormValidator.getErrors());

    }
    private ActionResult nouveauCompte(Client client, double solde){
        CompteDao compteDao=new CompteDao(client);
        Compte compte=new Compte();
        compte.setLog(TypeLog.CREATION," du compte "+compte.getNumeroCompte()+" pour le client "+client.getNomComplet());
        compte.setPropriétaire(client);
        compte.setSolde(solde);
        client.getComptesClient().add(compte);
        compteDao.save(compte);
        return new ActionResult(true,null);
    }
    @Override
    public ActionResult nouveauCompteClientExistant(String idClient,String solde) {
        CompteFormValidator compteFormValidator=new CompteFormValidator(clientDao,banque);
        Client client=compteFormValidator.validerCompte(idClient,solde);
        return new ActionResult(client!=null,compteFormValidator.getErrors());
    }

    @Override
    public List<Client> getClients() {
        return new ClientDao().findAll();
    }

    @Override
    public List<Client> chercherClient(String mot) {
        return clientDao.findByKeyWord(mot);
    }
    @Override
    public Client chercherClientParId(Long id) {
        return clientDao.findById(id);
    }

    @Override
    public List<Compte> getComptes() {
        List<Compte> comptes=new ArrayList<>();
        for(Client client: new ClientDao().findAll()){
            comptes.addAll(new CompteDao(client).findAll());
        }
        return comptes;
    }

    @Override
    public List<Compte> chercherCompte(String mot) {
        List<Compte> comptes=new ArrayList<>();
        for(Client client : clientDao.findAll()){
            CompteDao compteDao=new CompteDao(client);
            comptes.addAll(compteDao.findByKeyWord(mot));
        }
        return comptes;
    }

    @Override
    public Compte chercherCompteParNum(String numCompte) {
            for(Client client : clientDao.findAll()){
                CompteDao compteDao=new CompteDao(client);
                Compte compte=compteDao.findById(numCompte);
                if(compte!=null)
                    return compte;
            }
            return null;
    }

    @Override
    public ActionResult modifierClient(String prenom, String nom, String email, String pass, String passConfirmation, String cin, String tel, String sexe,Client client) {
        ClientFormValidator clientFormValidator=new ClientFormValidator();
        if(clientFormValidator.modifierUtilisateurAdmin(prenom,nom,email,pass,passConfirmation,cin,tel,sexe)){
            client.setPrenom(prenom);
            client.setNom(nom);
            client.setEmail(email);
            client.setMotDePasse(pass);
            client.setCin(cin);
            client.setTel(tel);
            client.setSexe(sexe.equals("HOMME")?Sexe.HOMME:Sexe.FEMME);
            clientDao.update(client);
            return new ActionResult(true,null);
        }
        return new ActionResult(false,clientFormValidator.getErrors());
    }

    @Override
    public ActionResult supprimerClient(Long id) {
        Client client=clientDao.findById(id);
        if(client!=null){
            clientDao.delete(client);
            client=banque.getClientsDeBanque().stream().filter(client::equals).findFirst().orElse(null);
            CompteDao compteDao=new CompteDao(client);
            client.getComptesClient().forEach(compteDao::delete);
            banque.getClientsDeBanque().remove(client);
            return new ActionResult(true,null);
        }
        return new ActionResult(false,Map.of("client","Client introuvable"));
    }

    @Override
    public ActionResult supprimerCompte(String numCompte) {
        for(Client client: clientDao.findAll()){
            CompteDao compteDao=new CompteDao(client);
            if(compteDao.deleteById(numCompte)){
                return new ActionResult(true,null);}
        }
        return new ActionResult(false,null);
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
}
