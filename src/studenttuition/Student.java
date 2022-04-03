package studenttuition;

/**
 * This is the parent class for all students and contains the basic
 * public variables and methods needed by children classes.
 * Stores profile, credit hours, date, tuition, and type of student.
 *
 * @author Andy Li, Henry Lin
 */
public class Student {
    public int creditHours;
    public Profile profile;
    public double tuitionDue;
    public double tuitionPaid;
    public Date date;
    public String type;

    /**
     * Empty constructor in order for child classes to be created.
     */
    public Student(){
    }

    /**
     * Constructor that stores the major information.
     * Tuition and date are initially set to nothing.
     *
     * @param name student name
     * @param major student major
     * @param creditHours number of credits the student is taking
     * @param type type of student -> resident/non-resident
     */
    public Student (String name, Major major, int creditHours, String type) {
        this.profile = new Profile(name,major);
        this.creditHours = creditHours;
        this.tuitionPaid = 0;
        this.tuitionDue = 0;
        this.date = null;
        this.type = type;
    }

    /**
     * default method for calculating tuition
     * every child class overrides this method
     */
    public void tuitionDue() {
    }

    /**
     * default method for converting the student's data
     * into a string to print.
     *
     * @return the string of the student's data
     */
    public String toString(){
        return "";
    }

    /**
     * Default method for checking if student has already
     * been awarded financial aid.
     * Only used by resident child class
     *
     * @return false as base case
     */
    public boolean alreadyAwarded(){
        return false;
    }

    /**
     * Default method for setting financial aid amount
     * Only used by resident child class
     *
     * @param financialAid amount to be set to
     */
    public void setFinancialAid(double financialAid){
    }

    /**
     * Default method for changing a student's study
     * abroad status used by the international class.
     *
     * @param studyAbroad the status to be changed to
     */
    public void studyingAbroad(boolean studyAbroad){
    }
}
