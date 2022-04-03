package studenttuition;

/**
 * Resident class that is child class of student.
 * Calculates tuition due, checks if financial aid is awarded,
 * and awards financial aid if needed.
 *
 * @author Andy Li, Henry Lin
 */
public class Resident extends Student{
    private double financialAid;

    /**
     * Constructor method for all residents
     * Financial aid is set to 0 initially for all residents.
     *
     * @param name student name
     * @param major student major
     * @param creditHours number of credits the student is taking
     * @param type type of student -> resident/non-resident
     */
    public Resident(String name, Major major, int creditHours, String type){
        super(name,major, creditHours, type);
        this.financialAid = 0;
    }

    // All statics that are used
    private static final int FULL_RESIDENT = 12536;
    private static final int PART_RESIDENT = 404;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int CREDIT_FULL = 12;
    private static final int CREDIT_OVERLOAD = 16;
    private static final double PART_TIME_DEPRECIATION = .8;

    /**
     * Calculates the tuition due for all resident students.
     */
    @Override
    public void tuitionDue() {
        double tuitionDue;
        if (this.creditHours >= CREDIT_FULL){
            tuitionDue = FULL_RESIDENT + UNIVERSITY_FEE;
            if (this.creditHours > CREDIT_OVERLOAD){
                tuitionDue += PART_RESIDENT * (this.creditHours - 16);
            }
        }
        else{
            tuitionDue = (PART_RESIDENT * this.creditHours) + PART_TIME_DEPRECIATION * UNIVERSITY_FEE;
        }
        this.tuitionDue = tuitionDue - this.financialAid - this.tuitionPaid;

    }

    /**
     * checks if financial aid has already been awarded to the specific student.
     *
     * @return true if financial aid has been awarded, false otherwise
     */
    @Override
    public boolean alreadyAwarded(){
        return this.financialAid > 0;
    }

    /**
     * Sets the student's financial aid to the amount given
     *
     * @param financialAid amount to be set to
     */
    @Override
    public void setFinancialAid(double financialAid){
        this.financialAid = financialAid;
    }

    /**
     * Prints a student's data
     * Additionally prints the financial aid the student received
     * if they have been awarded financial aid.
     *
     * @return the string of the student's data
     */
    @Override
    public String toString(){
        if (this.financialAid == 0) {
            if (date == null){
                return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                        ":total payment:" + tuitionPaid + ":last payment date:--/--/--:" + type;
            }
            return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                    ":total payment:" + tuitionPaid + ":last payment date:" + date + ":" + type;
        }
        if (date == null) {
            return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                    ":total payment:" + tuitionPaid + ":last payment date:--/--/--:" + type +
                    ":financial aid " + this.financialAid;
        }
        return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                ":total payment:" + tuitionPaid + ":last payment date:" + date + ":" + type +
                ":financial aid " + this.financialAid;
    }
}
