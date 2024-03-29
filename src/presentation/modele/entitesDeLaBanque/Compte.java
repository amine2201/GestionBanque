package presentation.modele.entitesDeLaBanque;

import presentation.modele.util.Log;
import presentation.modele.util.TypeLog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Compte {
    private static long          compteur = 1;
    private String          numeroCompte;
    private Double          solde;
    private LocalDateTime   dateCreation;
    private Client propriétaire;
    private List<Log>       logs = new ArrayList<>();

    public void setDateCreation() { this.dateCreation = LocalDateTime.now(); }
    public void setPropriétaire(Client propriétaire) {
        this.propriétaire = propriétaire;
    }
    public void setSolde(Double solde) {
        this.solde = solde;
    }
    public void setLog(TypeLog type, String msg) {

        Log log = new Log(LocalDate.now(), LocalTime.now(), type, msg);
        logs.add(log);
    }

    public static long getCompteur() {
        return compteur;
    }

    public static void setCompteur(long compteur) {
        Compte.compteur = compteur;
    }

    public Client           getPropriétaire() {
        return propriétaire;
    }
    public Double           getSolde() {
        return solde;
    }
    public String getNumeroCompte() {
        return numeroCompte;
    }
    public void setNumeroCompte() {
        this.numeroCompte = "b-co00" + compteur++;
    }
    public LocalDateTime    getDateCreation() {
        return dateCreation;
    }
    public List<Log>        getLogs() {
        return logs;
    }

    public Compte(){
        setNumeroCompte();
        setDateCreation();
        setSolde(0.0);
        setPropriétaire(null);
    }

    public Compte(String numeroCompte, Double solde, LocalDateTime dateCreation, Client propriétaire, List<Log> logs) {
        this.numeroCompte = numeroCompte;
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.propriétaire = propriétaire;
        this.logs = logs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compte compte = (Compte) o;

       return Objects.equals(numeroCompte, compte.numeroCompte);
    }


    @Override
    public String toString() {

        String      compteStr  = "------------------------------------------------------\n";
                    compteStr += "| N° de Compte            : "   + getNumeroCompte()   + "\n";
                    compteStr += "| Solde du Compte         : "   + getSolde()    + " Dh\n" ;
                    compteStr += "| Propriétaire du Compte  : "   + String.valueOf(getPropriétaire().getNomComplet()) + "\n" ;
                    compteStr += "------------------------------------------------------\n";

        return compteStr;

    }



}
