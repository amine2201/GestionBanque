package metier;

import presentation.modele.entitesDeLaBanque.Admin;

public interface Verifiable {


    static boolean isAdmin(String login, String pass){
        return Admin.getInstance().getLogin().equals(login) && Admin.getInstance().getMotDePasse().equals(pass);

    }
    static boolean isNumeric(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch(Exception e){
            return false;
        }
    }
    static boolean isDecimal(String value){
        try {
            Double.parseDouble(value);
            return true;
        } catch(Exception e){
            return false;
        }
    }

}
