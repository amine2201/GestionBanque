package presentation.modele.util;

import presentation.modele.entitesDeLaBanque.Utilisateur;

public class AuthResult {
    private final boolean success;
    private final String errorMessage;
    private final Utilisateur utilisateur;

    public AuthResult(boolean success, String errorMessage, Utilisateur utilisateur) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.utilisateur = utilisateur;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

}