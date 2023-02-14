package metier.clients;

import dao.daoFiles.ClientDao;
import dao.daoFiles.CompteDao;
import metier.forms.ClientFormValidator;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.util.ActionResult;
import presentation.modele.util.Log;
import presentation.modele.util.TypeLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static metier.Verifiable.isDecimal;

public class ServiceClientGUI implements IServiceClientGUI{
    private ClientDao clientDao;
    private CompteDao compteDao;
    private Client client;

    public ServiceClientGUI(ClientDao clientDao, Client client) {
        this.clientDao = clientDao;
        this.client = client;
        compteDao=new CompteDao(client);
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public ActionResult versement(String compte, String solde) {
        Compte _compte=compteDao.findById(compte);
        if(_compte==null)
            return new ActionResult(false, Map.of("compte","Compte introuvable"));
        if(!isDecimal(solde)) return new ActionResult(false,Map.of("solde","Solde invalide"));
        double val=Double.parseDouble(solde);
        if(val<0) return new ActionResult(false,Map.of("solde","Solde negatif"));
        _compte.setSolde(_compte.getSolde()+val);
        _compte.setLog(TypeLog.VERSEMENT," de "+solde+" DH");
         compteDao.update(_compte);
        return new ActionResult(true,null);
    }

    @Override
    public ActionResult retrait(String compte, String solde) {
        Compte _compte=compteDao.findById(compte);
        if(_compte==null)
            return new ActionResult(false, Map.of("compte","Compte introuvable"));
        if(!isDecimal(solde)) return new ActionResult(false,Map.of("solde","Solde invalide"));
        double val=Double.parseDouble(solde);
        if(val<0) return new ActionResult(false,Map.of("solde","Solde negatif"));
        if(val>_compte.getSolde())  return new ActionResult(false,Map.of("solde","Solde insuffisant"));
        _compte.setSolde(_compte.getSolde()-val);
        _compte.setLog(TypeLog.RETRAIT," de "+solde+" DH");
        compteDao.update(_compte);
        return new ActionResult(true,null);
    }

    @Override
    public ActionResult virement(String compte, String ben, String solde) {
        Compte compte1=compteDao.findById(compte);
        Client client2=null;
        if(compte1==null)
            return new ActionResult(false, Map.of("compte","Compte introuvable"));
        Compte compte2=null;
        for(Client client1: clientDao.findAll()){
            compte2=new CompteDao(client1).findById(ben);
            if(compte2!=null){
                client2=client1;
                break;}
        }
        if(compte2==null) return new ActionResult(false, Map.of("ben","Compte introuvable"));
        if(!isDecimal(solde)) return new ActionResult(false,Map.of("solde","Solde negatif"));
        double val=Double.parseDouble(solde);
        if(val<0) return new ActionResult(false,Map.of("solde","Solde negatif"));
        if(val>compte1.getSolde())  return new ActionResult(false,Map.of("solde","Solde insuffisant"));
        compte1.setSolde(compte1.getSolde()-val);
        compte2.setSolde(compte2.getSolde()+val);
        compte1.setLog(TypeLog.VIREMENT," de "+solde+" DH vers le compte "+compte2.getNumeroCompte());
        compte2.setLog(TypeLog.VIREMENT," de "+solde+" DH du  compte "+compte1.getNumeroCompte());
        compteDao.update(compte1);
        new CompteDao(client2).update(compte2);
        return new ActionResult(true,null);
    }

    @Override
    public ActionResult modifierProfile(String email, String mdp, String mdpc, String tel) {
        ClientFormValidator clientFormValidator=new ClientFormValidator();
        if(!clientFormValidator.modifierUtilisateurClient(email,mdp,mdpc,tel,client))
            return new ActionResult(false,clientFormValidator.getErrors());
        client.setEmail(email);
        client.setMotDePasse(mdp);
        client.setTel(tel);
        clientDao.update(client);
        return new ActionResult(true,null);
    }

    @Override
    public List<Log> getLogs(String compte) {
        Compte _compte=compteDao.findById(compte);
        if(_compte!=null)
            return _compte.getLogs();
        else return null;
    }

    @Override
    public List<Compte> comptes() {
        return compteDao.findAll();
    }
}
