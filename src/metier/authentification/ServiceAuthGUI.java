package metier.authentification;


import metier.admin.ServiceIHMAdmin;
import metier.clients.ServiceIHMClient;
import metier.forms.LoginFormValidator;
import presentation.modele.entitesDeLaBanque.Admin;
import presentation.modele.entitesDeLaBanque.Banque;
import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.entitesDeLaBanque.Utilisateur;
import presentation.vue.LoginFrame;

import javax.swing.*;
import java.util.Map;

import static metier.InteractiveConsole.clavier;

public class ServiceAuthGUI implements IAuth {
    private Banque banque;
    private IServiceIHM service;
    private LoginFrame loginFrame;
    private Admin admin;
    public ServiceAuthGUI(Banque banque) {
        this.banque=banque;
        loginFrame=new LoginFrame("Login");
    }

    @Override
    public void seConnecter() {
        loginFrame.getBtn_login().addActionListener( l -> {
            LoginFormValidator loginFormValidator=new LoginFormValidator(banque);
            Utilisateur utilisateur=loginFormValidator.validerUtilisateur(loginFrame.getTxt_login().getText(), new String(loginFrame.getTxt_pass().getPassword()));
            if(utilisateur==null) {
                Map<String, String> errors = loginFormValidator.getErrors();
                String error;
                if(errors.size()>1)
                    error=errors.get("login") +"\n"+ errors.get("pass");
                else if(errors.size()==1)
                    error=errors.get("login")==null?errors.get("pass"):errors.get("login");
                else error="Login et/ou mot de passe erroné(s)";
                JOptionPane.showMessageDialog(loginFrame,error,"Error",JOptionPane.ERROR_MESSAGE);
            }
            else if(utilisateur.getRole().equals("Admin"))
                service=new ServiceIHMAdmin(banque);
            else if(utilisateur.getRole().equals("Client"))
                service=new ServiceIHMClient((Client) utilisateur,banque);
            if(service!=null)
                service.menuGlobal();
        });
        SeDéconnecter();
    }

    @Override
    public void SeDéconnecter() {
        service=null;
    }
}
