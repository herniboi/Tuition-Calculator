package student_tuition;

/**
 * This class stores a roster of all the students
 * in an array list and performs the necessary
 * functions needed upon the roster.
 *
 * @author Andy Li, Henry Lin
 */
public class Roster {
    private Student[] roster;
    private int size; //keep track of the number of students in the roster

    // statics used
    private static final int NOT_FOUND = -1;
    private static final int START = 0;
    private static final int GROWTH_CONSTANT = 4;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int ADDITIONAL_FEE = 2650;
    private static final int BEFORE = 0;
    private static final int INVALID_DATE = 0;
    private static final int INVALID_PAYMENT= 1;
    private static final int OVER_PAY = 2;
    private static final int VALID_PAYMENT = 3;
    private static final int STUDENT_NOT_FOUND = 0;
    private static final int PART_TIME_INELIGIBLE = 1;
    private static final int ALREADY_AWARDED = 2;
    private static final int NOT_RESIDENT = 3;
    private static final int AWARD_ELIGIBLE = 4;
    private static final int INVALID_AWARD = 5;

    /**
     * finds a specific student in the roster
     *
     * @param student to be found
     * @return the index of the student in the array list, or -1 if not found
     */
    private int find(Student student) {
        for (int i = 0; i < size; i++) {
            if (roster[i].profile.equals(student.profile)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * grows the size of the array list by 4
     */
    private void grow() {
        Student[] newRoster = new Student[size + GROWTH_CONSTANT];
        if (size >= START) {
            int index = START;
            for (int i = 0; i < size; i++) {
                newRoster[i] = roster[i];
            }
            roster = newRoster;
        }
    }

    /**
     * adds a student to the roster
     *
     * @param student to be added
     * @return whether or not the student was successfully added
     */
    public boolean add(Student student) {
        if (size == 0) {
            roster = new Student[GROWTH_CONSTANT];
        }
        if (find(student) != NOT_FOUND) {
            return false;
        }
        if (size % GROWTH_CONSTANT == 0) {
            grow();
        }

        roster[size++] = student;
        return true;
    }

    /**
     * removes a student from the roster
     *
     * @param student to be removed
     * @return whether or not the student was successfully removed
     */
    public boolean remove(Student student) {
        int index = find(student);
        if (index == NOT_FOUND) {
            return false;
        }
        Student[] removedStudent = new Student[roster.length];
        int counter = START;
        for (int i = 0; i < size ; i++){
            if (i != index) {
                removedStudent[counter] = roster[i];
                counter++;
            }
        }
        roster = removedStudent;
        size--;
        return true;
    }

    /**
     * calculates the tuition for every student on the roster
     */
    public void calculateTuition(){
        for (int i = 0; roster[i] != null; i++){
            roster[i].tuitionDue();
        }
    }

    /**
     * pays the tuition for a specific student
     *
     * @param student to pay the tuition for
     * @param payment the amount the student is paying
     * @param date the date on which the payment was processed
     * @return a different integer based on whether the payment succeeded
     */
    public int payTuition(Student student, double payment, Date date){
        int index = find(student);
        if (!date.isValid()){
            return INVALID_DATE;
        } else if (payment < 0){
            return INVALID_PAYMENT;
        }
        if (payment <= roster[index].tuitionDue && payment > 0){
            roster[index].tuitionDue = roster[index].tuitionDue - payment;
            roster[index].tuitionPaid += payment;
            roster[index].date = date;
        } else{
            if (payment > roster[index].tuitionDue){
                return OVER_PAY;
            }
        }
        return VALID_PAYMENT;
    }

    /**
     * grants a resident student financial aid
     *
     * @param student student to be granted financial aid
     * @param financialAid amount of financial aid granted
     * @return a different integer based on whether the financial aid was succesfully granted
     */
    public int payAid(Student student, double financialAid){
        int index = find(student);

        if (index == -1){
            return STUDENT_NOT_FOUND;
        } else if (!roster[index].type.equals("Resident")){
            return NOT_RESIDENT;
        }  else if (roster[index].alreadyAwarded()){
            return ALREADY_AWARDED;
        } else if (roster[index].creditHours < 12){
            return PART_TIME_INELIGIBLE;
        }
        if (financialAid <= 10000 && financialAid > 0){
            roster[index].tuitionDue = roster[index].tuitionDue - financialAid;
            roster[index].setFinancialAid(financialAid);
            return AWARD_ELIGIBLE;
        } else{
            return INVALID_AWARD;
        }
    }

    /**
     * sets an international student's study abroad status to true/false
     * and recalculates their tuition.
     *
     * @param student to be studying abroad
     * @param studyAbroad the value true/false for the student's status
     * @return whether the student is able to study abroad
     */
    public boolean studyAbroad(Student student, Boolean studyAbroad) {
        int index = find(student);
        if (index == -1){
            return false;
        } else if (studyAbroad) {
            if (roster[index].creditHours > 12) {
                roster[index].creditHours = 12;
            }
            roster[index].tuitionPaid = 0;
            roster[index].date = null;
            roster[index].tuitionDue = ADDITIONAL_FEE + UNIVERSITY_FEE;
            roster[index].studyingAbroad(studyAbroad);
        } else {
            return false;
        }
        return true;
    }

    /**
     * prints the entire roster of students
     */
    public String print() {
        String text = roster[0].toString() + "\n";
        for (int i = 1; i<size; i++){
            text = text.concat(roster[i].toString() + "\n");
        }
        return text;
    } //display the list without specifying the order

    /**
     * prints only students who have paid sorted by payment date
     */
    public String printByPaymentDate() {
        int totalPaid = START;
        Student[] newRoster = new Student[size];
        for (int i = 0; i < size; i++) {
            if (roster[i].date != null) {
                newRoster[totalPaid] = roster[i];
                totalPaid++;
            }
        }

        for (int i = 0; i < totalPaid - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < totalPaid; j++) {
                if (newRoster[j].date.compareTo(newRoster[minIndex].date) < BEFORE) {
                    minIndex = j;
                }
            }
            Student temp = newRoster[minIndex];
            newRoster[minIndex] = newRoster[i];
            newRoster[i] = temp;
        }
        String text = new String();
        for (int i = 0; i < size; i++){
            text += roster[i].toString();
        }
        return text;
    }

    /**
     * prints the entire roster of students sorted by name
     */
    public String printByName() {
        for (int i = 0; i < size - 1; i++){
            int minIndex = i;
            for (int j = i + 1; j < size; j++){
                if (roster[j].profile.getName().compareTo(roster[minIndex].profile.getName()) < BEFORE ){
                    minIndex = j;
                }
            }
            Student temp = roster[minIndex];
            roster[minIndex] = roster[i];
            roster[i] = temp;
        }
        String text = new String();
        for (int i = 0; i < size; i++){
            text += roster[i].toString();
        }
        return text;
    }

    /**
     * checks if the roster is empty
     * @return true if there are no students and false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * checks to see if a student exists in the roster
     * @param student to find
     * @return the index of the student in the roster, and -1 if the student doesn't exist
     */
    public int findStudent(Student student){
        return find(student);
    }

    /**
     * gets the tuition for a single student
     * @param student to find tuition for
     * @return the student's tuition
     */
    public Double getTuition(Student student){
        int index = findStudent(student);
        roster[index].tuitionDue();
        return roster[index].tuitionDue;
    }
}

