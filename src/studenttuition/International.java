package studenttuition;

/**
 * This is the child class of the student class.
 * This class calculates the amount of tuition due
 * for international students only.
 *
 * @author Andy Li, Henry Lin
 */
public class International extends Student{
    private Boolean studyAbroad;

    /**
     * Constructor method for all international students
     *
     * @param name student name
     * @param major student major
     * @param creditHours number of credits the student is taking
     * @param studyAbroad whether or not the student is studying abroad
     * @param type type of student -> resident/non-resident
     */
    public International(String name, Major major, int creditHours, boolean studyAbroad, String type){
        super(name,major, creditHours, type);
        this.studyAbroad = studyAbroad;
    }

    // statics used
    private static final int FULL_TIME = 29737;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int INITIAL_TUITION = 0;
    private static final int ADDITIIONAL_FEE = 2650;
    private static final int CREDIT_OVERLOAD = 16;
    private static final int EXTRA_CREDIT_EXPENSE = 966;

    /**
     * Calculates the tuition due for all international students.
     */
    @Override
    public void tuitionDue() {
        double tuitionDue = INITIAL_TUITION;
        if (!studyAbroad){
            tuitionDue = FULL_TIME + UNIVERSITY_FEE + ADDITIIONAL_FEE;
            if (this.creditHours > CREDIT_OVERLOAD){
                tuitionDue += EXTRA_CREDIT_EXPENSE * (this.creditHours - 16);
            }
        }
        else{
            tuitionDue = UNIVERSITY_FEE + ADDITIIONAL_FEE;
        }
        this.tuitionDue = tuitionDue - this.tuitionPaid;
    }

    /**
     * Prints a student's data
     * Additionally prints the students study abroad status
     * if they are studying abroad.
     *
     * @return the string of the student's data
     */
    @Override
    public String toString(){
        if (this.studyAbroad) {
            if (date == null){
                return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                        ":total payment:" + tuitionPaid + ":last payment date:--/--/--:" + type +
                        ":study abroad";
            }
            return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                    ":total payment:" + tuitionPaid + ":last payment date:" + date + ":" + type +
                    ":study abroad";
        }

        if (date == null){
            return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                    ":total payment:" + tuitionPaid + ":last payment date:--/--/--:" + type;
        }
        return profile.toString() + ":" + creditHours + " credit hours:tuition due:" + tuitionDue +
                ":total payment:" + tuitionPaid + ":last payment date:" + date + ":" + type;
    }

    /**
     * changes the student's study abroad status
     *
     * @param studyAbroad the status to be changed to
     */
   @Override
   public void studyingAbroad(boolean studyAbroad) {
        this.studyAbroad = studyAbroad;
   }
}
