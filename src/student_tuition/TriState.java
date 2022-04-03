package student_tuition;

/**
 * This is the child class of the non-resident class.
 * This class calculates the amount of tuition due
 * for tri-state students only.
 *
 * @author Andy Li, Henry Lin
 */
public class TriState extends NonResident{
    private Boolean triState;

    /**
     * Constructor method for all tri-state students
     *
     * @param name student name
     * @param major student major
     * @param creditHours number of credits the student is taking
     * @param triState either NY or CT based on where the student is from
     * @param type type of student -> resident/non-resident
     */
    public TriState(String name, Major major, int creditHours, String triState, String type){
        super(name,major, creditHours, type);
        if (triState.equals("ny")){
            this.triState = NEW_YORK;
        } else{
            this.triState = CONNETICUT;
        }
    }

    // statics used
    private static final int FULL_NONRESIDENT = 29737;
    private static final int PART_NONRESIDENT = 966;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int CREDIT_FULL = 12;
    private static final int CREDIT_OVERLOAD = 16;
    private static final double PART_TIME_DEPRECIATION = .8;
    private static final Boolean NEW_YORK = false;
    private static final Boolean CONNETICUT = true;
    private static final int NY_DISCOUNT = 4000;
    private static final int CT_DISCOUNT = 5000;

    /**
     * Calculates the tuition due for all tri-state students.
     */
    @Override
    public void tuitionDue() {
        double tuitionDue;
        if (this.creditHours >= CREDIT_FULL){
            tuitionDue = FULL_NONRESIDENT + UNIVERSITY_FEE ;
            if (this.creditHours > CREDIT_OVERLOAD){
                tuitionDue += PART_NONRESIDENT * (this.creditHours - CREDIT_OVERLOAD);
            }
            if (!this.triState){
                tuitionDue -= NY_DISCOUNT;
            }
            else{
                tuitionDue -= CT_DISCOUNT;
            }
        }
        else{
            tuitionDue = (PART_NONRESIDENT * this.creditHours) + PART_TIME_DEPRECIATION * UNIVERSITY_FEE;
        }
        this.tuitionDue = tuitionDue - this.tuitionPaid;
    }

    /**
     * Prints a student's data
     *
     * @return the string of the student's data
     */
    @Override
    public String toString(){
        if (this.triState == NEW_YORK)
            return super.toString() + ":NY";
        return super.toString() + ":CT";
    }
}
