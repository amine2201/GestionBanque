package metier.authentification;

import presentation.modele.util.AuthResult;

public interface IAuthGUI {
    AuthResult seConnecter(String login, String pass);

    void SeDéconnecter();

}
