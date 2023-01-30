package metier.admin;

import dao.daoFiles.ClientDao;
import dao.daoFiles.CompteDao;
import metier.forms.ClientFormValidator;
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
    private CompteDao compteDao;
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
            nouveauCompteClientExistant(client,0);
            return new ActionResult(true,null);
        }
            return new ActionResult(false,clientFormValidator.getErrors());

    }

    @Override
    public ActionResult nouveauCompteClientExistant(Client client,double solde) {
        if(client!=null){
            compteDao=new CompteDao(client);
            Compte compte=new Compte();
            compte.setLog(TypeLog.CREATION,"pour le client "+client.getNomComplet());
            compte.setPropriétaire(client);
            if(solde!=0)
                compte.setSolde(solde);
            client.getComptesClient().add(compte);
            compteDao.save(compte);
            return new ActionResult(true,null);
        }
        return new ActionResult(false,Map.of("client","client est null"));
    }

    @Override
    public List<Client> chercherClient(String mot) {
        return clientDao.findByKeyWord(mot);
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
    public ActionResult modifierClient(String prenom, String nom, String email, String pass, String passConfirmation, String cin, String tel, String sexe,Client client) {
        ClientFormValidator clientFormValidator=new ClientFormValidator();
        if(clientFormValidator.modifierUtilisateur(prenom,nom,email,pass,passConfirmation,cin,tel,sexe)){
            client.setPrenom(prenom);
            client.setNom(nom);
            client.setEmail(email);
            client.setMotDePasse(pass);
            client.setCin(cin);
            client.setTel(tel);
            client.setSexe(Sexe.valueOf(sexe));
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
            banque.getClientsDeBanque().remove(client);
            return new ActionResult(true,null);
        }
        return new ActionResult(false,Map.of("client","Client introuvable"));
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
