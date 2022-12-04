package presentation.modele.util;

public enum ConsoleColors {
    RED( "\033[0;31m"), GREEN("\033[0;32m"), RESET("\033[0m");
    String valeur;
    ConsoleColors(String valeur) {
        this.valeur = valeur;
    }

    public String getValeur() {
        return valeur;
    }
}
