package metier.forms;

import dao.daoFiles.ClientDao;
import metier.Verifiable;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Compte;
import presentation.modele.entitesDeLaBanque.Utilisateur;
import presentation.modele.util.TypeLog;

import java.util.HashMap;
import java.util.Map;

import static metier.Verifiable.isDecimal;
import static metier.Verifiable.isNumeric;

public class CompteFormValidator {
    private ClientDao clientDao;
    public static final String CHAMP_CLIENT = "client";
    public static final String CHAMP_SOLDE = "solde";

    private Map<String, String> errors= new HashMap<>();
    private String resultMsg;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setError(String field, String errorMsg) {
        errors.put(field,errorMsg);
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    public CompteFormValidator(ClientDao clientDao) {
        this.clientDao=clientDao;
    }
    private Client verifierClient(String idClient) throws FormException{
        if(idClient!=null && idClient.trim().length()!=0){
            if(!isNumeric(idClient))
                throw new FormException("Le champs Identifiant du client est numerique");
            Client client=clientDao.findById(Long.valueOf(idClient));
            if(client==null)
                throw new FormException("Le Client est introuvable");
            return client;
        }
        else {
            throw new FormException("Le champs Identifiant du client est obligatoire");
        }
    }


    private Client validerClient(String idClient){
        try {
            return verifierClient(idClient);
        } catch (FormException e) {
            setError(CHAMP_CLIENT,e.getMessage());
        }
        return null;
    }

    private double verifierSolde(String solde) throws FormException{
        if(solde!=null && solde.trim().length()!=0){
            if(!isDecimal(solde))
                throw new FormException("Le champs solde est numerique");
            return Double.parseDouble(solde);
        }
        else {
            throw new FormException("Le champs solde est obligatoire");
        }
    }


    private Double validerSolde(String solde){
        try {
            return verifierSolde(solde);
        } catch (FormException e) {
            setError(CHAMP_SOLDE,e.getMessage());
        }
        return null;
    }


    public  Client validerCompte(String idClient, String soldeString){
        Client client= validerClient(idClient) ;
        Double solde=validerSolde(soldeString);

        if(errors.isEmpty()) {
            Compte compte=new Compte();
            compte.setPropriétaire(client);
            compte.setSolde(solde);
            if(client != null)
                compte.setLog(TypeLog.CREATION,"pour le client "+client.getNomComplet());

        }
        else {
            client=null;
            setResultMsg("Connexion echoue");
        }
        return client;
    }
}
