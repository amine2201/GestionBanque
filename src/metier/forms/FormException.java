package metier.forms;

public class FormException extends Exception {
    public FormException() {
        super("Erreur dans le formulaire!!");
    }
    public FormException(String message){
        super(message);
    }

}
