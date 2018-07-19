package javaeetutorial.cdi.producermethods;

public class Shifter implements Coder {

    /**
     * Shifts an input string.
     * @param tval  the number of characters to shift
     * @return      the transformed string
     */
    @Override
    public String codeString(String s, int tval) {
        
        final int SPACE_CHAR = 32;
        final int CAPITAL_A = 65;
        final int CAPITAL_Z = 90;
        final int SMALL_A = 97;
        final int SMALL_Z = 122;

        StringBuilder sb = new StringBuilder(s);

        for (int i = 0; i < sb.length(); i++) {
            
            int cp = sb.codePointAt(i);
            int cplus = cp + tval;
            if (cp == SPACE_CHAR) {
                cplus = SPACE_CHAR;
            }
            if ((cp >= CAPITAL_A) && (cp <= CAPITAL_Z)) {//uppercase
                if (cplus > CAPITAL_Z) {
                    cplus = cplus - 26;
                }
            } else if ((cp >= SMALL_A) && (cp <= SMALL_Z)) {//lowercase
                if (cplus > SMALL_Z) {
                    cplus = cplus - 26;
                }
            } else {// punctuation, etc.
                cplus = cp;
            }
            sb.setCharAt(i, (char) cplus);
        }

        return (sb.toString());

    }
}
