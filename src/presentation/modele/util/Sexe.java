package presentation.modele.util;

public enum Sexe {

    HOMME(0,"H", "HOMME"), FEMME(1,"F", "FEMME");

        String libelle, abreviation;
        Long indice;

        Sexe(){}
        Sexe(String abreviation){ this.abreviation = abreviation;}
        Sexe(long indice){ this.indice = indice;}
        Sexe(long indice, String abreviation)
                {
                    this.indice = indice;
                    this.abreviation = abreviation;
                }
        Sexe(long indice, String abreviation, String libelle)
                {
                    this.indice = indice;
                    this.abreviation = abreviation;
                    this.libelle = libelle;
                }

    public Long getIndice() {
        return indice;
    }
    public String getAbreviation() {
        return abreviation;
    }
    public String getLibelle() {
        return libelle;
    }
}
