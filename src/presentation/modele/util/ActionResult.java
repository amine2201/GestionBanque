package presentation.modele.util;


import java.util.Map;

public class ActionResult {
    private final boolean success;
    private final Map<String,String> errorMessage;

    public ActionResult(boolean success, Map<String, String> errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, String> getErrorMessage() {
        return errorMessage;
    }
}
