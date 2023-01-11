package metier.forms;

import metier.Verifiable;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Utilisateur;
import metier.InteractiveConsole;

import java.util.HashMap;
import java.util.Map;

public class LoginFormValidator {
    private Banque banque;

    public static final String CHAMP_LOGIN = "login";
    public static final String CHAMP_PASS = "pass";

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

    private void verifierLogin(String login) throws FormException{
        if(login!=null && login.trim().length()!=0){
            if(login.length() < 5)
                throw new FormException("Le champs login doit avoir plus que 5 caracteres !!!");

        }
        else {
            throw new FormException("Le champs login est obligatoire");
        }
    }

    public LoginFormValidator(Banque banque) {
        this.banque = banque;
    }

    private void validerLogin(String login, Utilisateur user){
        try {
            verifierLogin(login);
            user.setLogin(login);
        } catch (FormException e) {
            setError(CHAMP_LOGIN,e.getMessage());
        }
    }

    private void verifierMotDePasse(String pass) throws FormException{
        if(pass!=null && pass.trim().length()!=0){
            if(pass.length() < 5)
                throw new FormException("Le champs mot de passe doit avoir plus que 5 caracteres !!!");

        }
        else {
            throw new FormException("Le champs mot de passe est obligatoire");
        }
    }
    private void validerMotDePasse(String pass, Utilisateur user){
        try {
            verifierMotDePasse(pass);
            user.setMotDePasse(pass);
        } catch (FormException e) {
            setError(CHAMP_PASS,e.getMessage());
        }
    }
    public Utilisateur validerUtilisateur(String login, String pass){
        Utilisateur loggedUser= new Utilisateur();
        validerLogin(login,loggedUser);
        validerMotDePasse(pass, loggedUser);

        if(errors.isEmpty()) {
            if (Verifiable.isAdmin(login,pass)) {
                loggedUser = Admin.getInstance();
            } else {
                Client clt = banque.getClientsDeBanque().stream()
                        .filter(client -> client.getLogin().equals(login) && client.getMotDePasse().equals(pass))
                        .findFirst().orElse(null);
                if (clt != null) {
                    loggedUser = clt;
                    setResultMsg("Connexion reussite");
                } else {
                    loggedUser = null;
                    setResultMsg("Connexion echoue");
                }
            }
        }
        else {
            loggedUser=null;
            setResultMsg("Connexion echoue");
        }
        return loggedUser;
    }
}
