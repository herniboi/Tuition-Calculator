package student_tuition;

/**
 * Enum class for major that stores the 5 types of majors
 *
 * @author Andy Li, Henry Lin
 */
public enum Major {
    CS, IT, BA, EE, ME;

    /**
     * converts the major to a string for printing purposes
     *
     * @param major the major that needs to be converted
     * @return the major in string form
     */
    public String toString(Major major){
        if (major.equals(CS)){
            return "CS";
        } else if (major.equals(IT)){
            return "IT";
        } else if (major.equals(BA)){
            return "BA";
        } else if (major.equals(EE)){
            return "EE";
        } else if (major.equals(ME)){
            return "ME";
        } else{
            return "";
        }
    }
}
