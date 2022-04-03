package studenttuition;

/**
 * This is the parent class for the tri-state class,
 * and the child class of the student class.
 * This class calculates the amount of tuition due
 * for non-resident students only.
 *
 * @author Andy Li, Henry Lin
 */
public class NonResident extends Student{
    /**
     * Empty constructor method so that tri-state class can be created
     */
    public NonResident(){
    }

    /**
     * Constructor method that simply refers back to the student class
     * as there are no extra parameters in this class.
     *
     * @param name student name
     * @param major student major
     * @param creditHours number of credits the student is taking
     * @param type type of student -> resident/non-resident
     */
    public NonResident(String name, Major major, int creditHours, String type){

        super(name,major, creditHours, type);
    }

    //statics used
    private static final int FULL_NONRESIDENT = 29737;
    private static final int PART_NONRESIDENT = 966;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int CREDIT_FULL = 12;
    private static final int CREDIT_OVERLOAD = 16;
    private static final double PART_TIME_DEPRECIATION = .8;

    /**
     * Calculates the tuition due for all non-resident students.
     */
    @Override
    public void tuitionDue() {
        double tuitionDue;
        if (this.creditHours >= CREDIT_FULL){
            tuitionDue = FULL_NONRESIDENT + UNIVERSITY_FEE;
            if (this.creditHours > CREDIT_OVERLOAD){
                tuitionDue += PART_NONRESIDENT * (this.creditHours - CREDIT_OVERLOAD);
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
        if (date == null){
            return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                    ":total payment:" + tuitionPaid + ":last payment date:--/--/--:" + type;
        }
        return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                ":total payment:" + tuitionPaid + ":last payment date:" + date + ":" + type;
    }
}
