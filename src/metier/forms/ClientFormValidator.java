package metier.forms;


import presentation.modele.entitesDeLaBanque.Client;
import presentation.modele.util.Sexe;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ClientFormValidator {

    public static final String CHAMP_PRENOM = "prenom";
    public static final String CHAMP_NOM = "nom";
    public static final String CHAMP_EMAIL = "email";
    public static final String CHAMP_PASS = "pass";
    public static final String CHAMP_CIN = "cin";
    public static final String CHAMP_TEL = "tel";
    public static final String CHAMP_SEXE = "sexe";
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

    //---------------------------------------Verifier Prenom------------------------------------------
    private void verifierPrenom(String prenom) throws FormException{
        if(prenom!=null && prenom.trim().length()!=0){
            if(prenom.length() < 3)
                throw new FormException("Le champs prenom doit avoir plus que 3 caracteres !!!");

        }
        else {
            throw new FormException("Le champs prenom est obligatoire");
        }
    }


    public void validerPrenom(String prenom, Client client){
        try {
            verifierPrenom(prenom);
            client.setPrenom(prenom);
        } catch (FormException e) {
            setError(CHAMP_PRENOM,e.getMessage());
        }
    }

    //---------------------------------------Verifier Nom-------------------------------------------------
    private void verifierNom(String nom) throws FormException{
        if(nom!=null && nom.trim().length()!=0){
            if(nom.length() < 3)
                throw new FormException("Le champs nom doit avoir plus que 3 caracteres !!!");

        }
        else {
            throw new FormException("Le champs nom est obligatoire");
        }
    }
    public void validerNom(String nom, Client client){
        try {
            verifierNom(nom);
            client.setNom(nom);
        } catch (FormException e) {
            setError(CHAMP_NOM,e.getMessage());
        }
    }

    //-----------------------------------Verifier Email--------------------------------------
    private void verifierEmail(String email) throws FormException{
        if(email!=null && email.trim().length()!=0){
            if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email))
                throw new FormException("Le champs email doit avoir le format email@example.com");

        }
        else {
            throw new FormException("Le champs email est obligatoire");
        }
    }


    public void validerEmail(String email, Client client){
        try {
            verifierEmail(email);
            client.setEmail(email);
        } catch (FormException e) {
            setError(CHAMP_EMAIL,e.getMessage());
        }
    }

    //-----------------------------------Verifier Pass--------------------------------------
    private void verifierPass(String pass,String passConfirmation) throws FormException{
        if(pass!=null && pass.trim().length()!=0){
            if(pass.length() < 5)
                throw new FormException("Le champs mot de passe doit avoir plus que 5 caracteres !!!");
            if(!pass.equals(passConfirmation))
                throw new FormException("Le champs mot de passe ne correspond pas au mot de passe de confirmation!!!");

        }
        else {
            throw new FormException("Le champs mot de passe est obligatoire");
        }
    }


    public void validerPass(String pass,String passConfirmation, Client client){
        try {
            verifierPass(pass,passConfirmation);
            client.setMotDePasse(pass);
        } catch (FormException e) {
            setError(CHAMP_PASS,e.getMessage());
        }
    }
    //-----------------------------------Verifier CIN--------------------------------------
    private void verifierCIN(String cin) throws FormException{
        if(cin!=null && cin.trim().length()!=0){
            if(cin.length() < 5)
                throw new FormException("Le champs cin doit avoir plus que 5 caracteres !!!");

        }
        else {
            throw new FormException("Le champs cin est obligatoire");
        }
    }


    public void validerCIN(String cin, Client client){
        try {
            verifierCIN(cin);
            client.setCin(cin);
        } catch (FormException e) {
            setError(CHAMP_CIN,e.getMessage());
        }
    }

    //-----------------------------------Verifier Tel--------------------------------------
    private void verifierTel(String tel) throws FormException{
        if(tel!=null && tel.trim().length()!=0){
            if(!Pattern.matches("(^[+][0-9]{12,13}$)|[0-9]{10}",tel))
                throw new FormException("Le champs tel doit avoir le format 0612345678");

        }
        else {
            throw new FormException("Le champs tel est obligatoire");
        }
    }


    public void validerTel(String tel, Client client){
        try {
            verifierTel(tel);
            client.setTel(tel);
        } catch (FormException e) {
            setError(CHAMP_TEL,e.getMessage());
        }
    }

    //-----------------------------------Verifier Sexe--------------------------------------
    private void verifierSexe(String sexe) throws FormException{
        if(sexe!=null && sexe.trim().length()!=0){
            if(!Pattern.matches("^H|F$",sexe.toUpperCase()))
                throw new FormException("Le champs Sexe est incorrect");

        }
        else {
            throw new FormException("Le champs tel est obligatoire");
        }
    }


    public void validerSexe(String sexe, Client client){
        try {
            verifierSexe(sexe);
            if(sexe.toUpperCase().equals("H"))
                client.setSexe(Sexe.HOMME);
            else client.setSexe(Sexe.FEMME);
        } catch (FormException e) {
            setError(CHAMP_SEXE,e.getMessage());
        }
    }
    public boolean modifierUtilisateurAdmin(String prenom, String nom, String email, String pass, String passConfirmation, String cin, String tel, String sexe){
        boolean valid=true;
        try {
            verifierPrenom(prenom);
        } catch (FormException e){
            setError(CHAMP_PRENOM,e.getMessage());
            valid=false;
        }
        try {
            verifierNom(nom);
        }catch (FormException e){
            setError(CHAMP_NOM,e.getMessage());
            valid=false;
        }
        try {
            verifierEmail(email);
        }catch (FormException e){
            setError(CHAMP_EMAIL,e.getMessage());
            valid=false;
        }
        try {
            verifierPass(pass,passConfirmation);
        }catch (FormException e){
            setError(CHAMP_PASS,e.getMessage());
            valid=false;
        }
        try {
            verifierCIN(cin);
        }catch (FormException e){
            setError(CHAMP_CIN,e.getMessage());
            valid=false;
        }
        try {
            verifierTel(tel);
        }catch (FormException e){
            setError(CHAMP_TEL,e.getMessage());
            valid=false;
        }
        try {
            verifierSexe(sexe);
        }catch (FormException e){
            setError(CHAMP_SEXE,e.getMessage());
            valid=false;
        }

        return valid;
    }
    public Client validerUtilisateur(String prenom, String nom,String email,String pass,String passConfirmation, String cin, String tel,String sexe){
        Client client= new Client();
        validerPrenom(prenom,client);
        validerNom(nom,client);
        validerEmail(email,client);
        validerPass(pass,passConfirmation,client);
        validerCIN(cin,client);
        validerTel(tel,client);
        validerSexe(sexe,client);
        client.setLogin("Client"+client.getId());
        if(errors.isEmpty()) {
                setResultMsg("succes");
        }
        else {
            client=null;
            setResultMsg("echoue");
        }
        return client;
    }

    public boolean modifierUtilisateurClient(String email,String mdp,String mdpc,String tel,Client client){
        boolean valid=true;
        try {
            verifierEmail(email);
        }catch (FormException e){
            setError(CHAMP_EMAIL,e.getMessage());
            valid=false;
        }
        try {
            verifierPass(mdp,mdpc);
        }catch (FormException e){
            setError(CHAMP_PASS,e.getMessage());
            valid=false;
        }
        try {
            verifierTel(tel);
        }catch (FormException e){
            setError(CHAMP_TEL,e.getMessage());
            valid=false;
        }

        return valid;
    }
    }

