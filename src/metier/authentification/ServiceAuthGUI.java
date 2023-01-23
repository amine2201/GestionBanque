package metier.authentification;


import metier.admin.ServiceIHMAdminConsole;
import metier.clients.ServiceIHMClient;
import metier.forms.LoginFormValidator;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Utilisateur;
import presentation.modele.util.AuthResult;
import presentation.vue.LoginFrame;

import javax.swing.*;
import java.util.Map;

public class ServiceAuthGUI implements IAuthGUI {
    private Banque banque;
    private IServiceIHM service;
    private Admin admin;
    public ServiceAuthGUI(Banque banque) {
        this.banque=banque;
    }

    @Override
    public AuthResult seConnecter(String login, String pass) {
        LoginFormValidator loginFormValidator=new LoginFormValidator(banque);
        Utilisateur utilisateur=loginFormValidator.validerUtilisateur(login,pass);
        if(utilisateur==null) {
            Map<String, String> errors = loginFormValidator.getErrors();
            String error;
            if(errors.size()>1)
                error=errors.get("login") +"\n"+ errors.get("pass");
            else if(errors.size()==1)
                error=errors.get("login")==null?errors.get("pass"):errors.get("login");
            else error="Login et/ou mot de passe erroné(s)";
            return new AuthResult(false,error,null);
        }
        return new AuthResult(true,null,utilisateur);
    }

    @Override
    public void SeDéconnecter() {
        service=null;
    }
}
