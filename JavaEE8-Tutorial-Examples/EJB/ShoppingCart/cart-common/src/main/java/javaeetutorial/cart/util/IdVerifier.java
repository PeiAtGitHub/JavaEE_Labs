package javaeetutorial.cart.util;

public class IdVerifier {

    public boolean validate(String id) {
        return isAllDigit(id);
    }

    /*
     * Is this more complete than judging by Regex "\d*"?
     */
    private boolean isAllDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}
