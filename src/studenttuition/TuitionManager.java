package studenttuition;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Takes in commands and runs the corresponding tasks as necessary
 * Checks for erros on the commmand line.
 *
 * @author Andy Li, Henry Lin
 */
public class TuitionManager {
        Roster roster;

        /**
         * Driver for the project, takes in command lines
         */
        public void run() {
                System.out.println("Tuition Manager starts running.\n");
                Scanner scanner = new Scanner(System.in);
                roster = new Roster();
                while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.equals("")) {
                                System.out.println();
                                continue;
                        }
                        StringTokenizer tokenizer = new StringTokenizer(line, ",");
                        String command = tokenizer.nextToken();
                        if (command.equals("AR")){
                                addResident(tokenizer);
                        } else if (command.equals("AN")){
                                addNonresident(tokenizer);
                        } else if (command.equals("AT")){
                                addTristate(tokenizer);
                        } else if  (command.equals("AI")) {
                                addInternational(tokenizer);
                        } else if (command.equals("R")) {
                                removeStudent(tokenizer);
                        } else if (command.equals("C")) {
                                calculateTuition();
                        } else if (command.equals("T")) {
                                payTuition(tokenizer);
                        } else if (command.equals("S")) {
                                studyAbroad(tokenizer);
                        } else if (command.equals("F")) {
                                financialAid(tokenizer);
                        } else if ((command.equals("PN") || command.equals("PT")
                                || command.equals("P")) && !tokenizer.hasMoreTokens()) {
                                display(command);
                        } else if (command.equals("Q")) {
                                break;
                        } else {
                                System.out.println("Command" + "'" + command + "'" + "not supported!");
                        }
                }
                System.out.println("\nTuition Manager terminated.");
        }

        // statics used
        private static final int FIRST_PARAMETER = 0;
        private static final int SECOND_PARAMETER = 1;
        private static final int THIRD_PARAMETER = 2;
        private static final int FOURTH_PARAMETER = 3;
        private static final int TOTAL_PARAMETERS = 4;
        private static final int STARTING_CREDITS = 0;
        private static final double DECISTARTING_CREDITS = 0;
        private static final int VALID_CREDITS = 16;
        private static final int OVER_PAY = 2;
        private static final int STUDENT_NOT_FOUND = 0;
        private static final int PART_TIME_INELIGIBLE = 1;
        private static final int ALREADY_AWARDED = 2;
        private static final int NOT_RESIDENT = 3;
        private static final int INVALID_AWARD = 5;

        /**
         * Adds a resident student to the roster
         *
         * @param tokenizer
         */
        private void addResident(StringTokenizer tokenizer) {
                int counter = FIRST_PARAMETER;
                String name = "", major = "";
                int credits = STARTING_CREDITS;
                while (tokenizer.hasMoreTokens()) {
                        if (counter == FIRST_PARAMETER) {
                                name = tokenizer.nextToken();
                        } else if (counter == SECOND_PARAMETER) {
                                major = tokenizer.nextToken();
                        } else if (counter == THIRD_PARAMETER) {
                                String fakeCredits;
                                fakeCredits = tokenizer.nextToken();
                                if (fakeCredits.matches("[a-zA-Z]+")){
                                        System.out.println("Invalid credit hours");
                                        return;
                                }
                                credits = Integer.parseInt(fakeCredits);
                        }
                        counter++;
                }
                if (counter != FOURTH_PARAMETER) {
                        if (counter == THIRD_PARAMETER){
                                System.out.println("Credit hours missing.");
                                return;
                        }
                        System.out.println("Missing data in command line.");
                        return;
                }
                Student student = new Student();
                major = major.toLowerCase();
                boolean nonMajor = false;
                switch (major){
                        case "cs" -> student = new Resident(name, Major.CS, credits, "resident");
                        case "it" -> student = new Resident(name, Major.IT, credits, "resident");
                        case "ba" -> student = new Resident(name, Major.BA, credits, "resident");
                        case "ee" -> student = new Resident(name, Major.EE, credits, "resident");
                        case "me" -> student = new Resident(name, Major.ME, credits, "resident");
                        default -> nonMajor = true;
                }
                if (nonMajor){
                         System.out.println("'" + major + "' is not a valid major.");
                         return;
                }
                if (credits < 3 && credits >= 0){
                        System.out.println("Minimum credit hours is 3.");
                        return;
                } else if (credits > 24){
                        System.out.println("Credit hours exceed the maximum 24.");
                        return;
                } else if (credits < 0){
                        System.out.println("Credit hours cannot be negative.");
                        return;
                }
                if (roster.add(student)) {
                        System.out.println("Student added.");
                } else {
                        System.out.println("Student is already in the roster.");
                }
        }

        /**
         * adds a non-resident student to the roster
         *
         * @param tokenizer
         */
        private void addNonresident(StringTokenizer tokenizer){
                int counter = FIRST_PARAMETER;
                String name = "", major = "";
                int credits = STARTING_CREDITS;
                while (tokenizer.hasMoreTokens()) {
                        if (counter == FIRST_PARAMETER) {
                                name = tokenizer.nextToken();
                        } else if (counter == SECOND_PARAMETER) {
                                major = tokenizer.nextToken();
                        } else if (counter == THIRD_PARAMETER) {
                                String fakeCredits =  tokenizer.nextToken();
                                if (fakeCredits.matches("[a-zA-Z]+")){
                                        System.out.println("Invalid credit hours");
                                        return;
                                }
                                credits = Integer.parseInt(fakeCredits);
                        }
                        counter++;
                }
                if (counter != FOURTH_PARAMETER) {
                        if (counter == THIRD_PARAMETER){
                                System.out.println("Credit hours missing.");
                                return;
                        }
                        System.out.println("Missing data in command line.");
                        return;
                }
                Student student = new Student();
                major = major.toLowerCase();
                boolean notMajor = false;
                switch (major){
                        case "cs" -> student = new NonResident(name, Major.CS, credits, "non-resident");
                        case "it" -> student = new NonResident(name, Major.IT, credits, "non-resident");
                        case "ba" -> student = new NonResident(name, Major.BA, credits, "non-resident");
                        case "ee" -> student = new NonResident(name, Major.EE, credits, "non-resident");
                        case "me" -> student = new NonResident(name, Major.ME, credits, "non-resident");
                        default -> notMajor = true;
                }
                if (notMajor) {
                        System.out.println("'" + major + "' is not a valid major.");
                        return;
                }
                if (credits < 3 && credits >= 0){
                        System.out.println("Minimum credit hours is 3.");
                        return;
                } else if (credits > 24){
                        System.out.println("Credit hours exceed the maximum 24.");
                        return;
                } else if (credits < 0){
                        System.out.println("Credit hours cannot be negative.");
                        return;
                }
                if (roster.add(student)) {
                        System.out.println("Student added.");
                } else {
                        System.out.println("Student is already in the roster.");
                }
        }

        /**
         * adds a tri-state student to the roster
         *
         * @param tokenizer
         */
        private void addTristate(StringTokenizer tokenizer){
                int counter = FIRST_PARAMETER;
                String name = "", major = "", state = "";
                int credits = STARTING_CREDITS;
                while (tokenizer.hasMoreTokens()) {
                        if (counter == FIRST_PARAMETER) {
                                name = tokenizer.nextToken();
                        } else if (counter == SECOND_PARAMETER) {
                                major = tokenizer.nextToken();
                        } else if (counter == THIRD_PARAMETER) {
                                String fakeCredits =  tokenizer.nextToken();
                                if (fakeCredits.matches("[a-zA-Z]+")){
                                        System.out.println("Invalid credit hours");
                                        return;
                                }
                                credits = Integer.parseInt(fakeCredits);
                        } else if (counter == FOURTH_PARAMETER) {
                                state = tokenizer.nextToken();
                                state = state.toLowerCase();
                                if (!state.equals("ct") && !state.equals("ny")){
                                        System.out.println("Not part of the tri-state area");
                                        return;
                                }

                        }
                        counter++;
                }
                if (counter != TOTAL_PARAMETERS) {
                        if (counter == THIRD_PARAMETER){
                                System.out.println("Credit hours missing.");
                                return;
                        }
                       System.out.println("Missing data in command line.");
                       return;
                }
                Student student = new Student();
                major = major.toLowerCase();
                boolean notMajor = false;
                switch (major){
                        case "cs" -> student = new TriState(name, Major.CS, credits, state, "non-resident(tri-state):" + state);
                        case "it" -> student = new TriState(name, Major.IT, credits, state, "non-resident(tri-state):" + state);
                        case "ba" -> student = new TriState(name, Major.BA, credits, state, "non-resident(tri-state):" + state);
                        case "ee" -> student = new TriState(name, Major.EE, credits, state, "non-resident(tri-state):" + state);
                        case "me" -> student = new TriState(name, Major.ME, credits, state, "non-resident(tri-state):" + state);
                        default -> notMajor = true;
                }
                if (notMajor){
                        System.out.println("'" + major + "' is not a valid major.");
                        return;
                }
                if (credits < 3 && credits >= 0){
                        System.out.println("Minimum credit hours is 3.");
                        return;
                } else if (credits > 24){
                        System.out.println("Credit hours exceed the maximum 24.");
                        return;
                } else if (credits < 0){
                        System.out.println("Credit hours cannot be negative.");
                        return;
                }
                if (roster.add(student)) {
                        System.out.println("Student added.");
                } else {
                        System.out.println("Student is already in the roster.");
                }
        }

        /**
         * adds an international student to the roster
         *
         * @param tokenizer
         */
        private void addInternational(StringTokenizer tokenizer){
                int counter = FIRST_PARAMETER;
                String name = "", major = "";
                boolean studyAbroad = false;
                int credits = STARTING_CREDITS;
                while (tokenizer.hasMoreTokens()) {
                        if (counter == FIRST_PARAMETER) {
                                name = tokenizer.nextToken();
                        } else if (counter == SECOND_PARAMETER) {
                                major = tokenizer.nextToken();
                        } else if (counter == THIRD_PARAMETER) {
                                String fakeCredits =  tokenizer.nextToken();
                                if (fakeCredits.matches("[a-zA-Z]+")){
                                        System.out.println("Invalid credit hours");
                                        return;
                                }
                                credits = Integer.parseInt(fakeCredits);
                        } else if (counter == FOURTH_PARAMETER) {
                                studyAbroad = Boolean.parseBoolean(tokenizer.nextToken());
                        }
                        counter++;
                }
                if (counter != TOTAL_PARAMETERS) {
                        if (counter == THIRD_PARAMETER){
                                System.out.println("Credit hours missing.");
                                return;
                        }
                        System.out.println("Missing data in command line.");
                        return;
                }
                Student student = new Student();
                major = major.toLowerCase();
                boolean notMajor = false;
                switch (major){
                        case "cs" -> student = new International(name, Major.CS, credits, studyAbroad, "non-resident:international");
                        case "it" -> student = new International(name, Major.IT, credits, studyAbroad, "non-resident:international");
                        case "ba" -> student = new International(name, Major.BA, credits, studyAbroad, "non-resident:international");
                        case "ee" -> student = new International(name, Major.EE, credits, studyAbroad, "non-resident:international");
                        case "me" -> student = new International(name, Major.ME, credits, studyAbroad, "non-resident:international");
                        default -> notMajor = true;
                }
                if (notMajor){
                        System.out.println("'" + major + "' is not a valid major.");
                        return;
                }
                if (credits < 12 && credits >= 3){
                        System.out.println("International students must enroll at least 12 credits.");
                        return;
                } else if (credits < 3 && credits >= 0){
                        System.out.println("Minimum credit hours is 3.");
                        return;
                } else if (credits > 24){
                        System.out.println("Credit hours exceed the maximum 24.");
                        return;
                } else if (credits < 0){
                        System.out.println("Credit hours cannot be negative.");
                        return;
                }
                if (roster.add(student)) {
                        System.out.println("Student added.");
                } else {
                        System.out.println("Student is already in the roster.");
                }
        }

        /**
         * removes a student from the roster
         *
         * @param tokenizer
         */
        private void removeStudent(StringTokenizer tokenizer) {
                int counter = FIRST_PARAMETER;
                String name = "", major = "";
                int credits = STARTING_CREDITS;
                while (tokenizer.hasMoreTokens()) {
                        if (counter == FIRST_PARAMETER) {
                                name = tokenizer.nextToken();
                        } else if (counter == SECOND_PARAMETER){
                                major = tokenizer.nextToken();
                        }
                        counter++;
                }
                if (counter != THIRD_PARAMETER){
                        System.out.println("Invalid command!");
                        return;
                }
                Student student = new Student();
                major = major.toLowerCase();
                boolean notMajor = false;
                switch (major){
                        case "cs" -> student = new Student(name, Major.CS, credits, "");
                        case "it" -> student = new Student(name, Major.IT, credits, "");
                        case "ba" -> student = new Student(name, Major.BA, credits, "");
                        case "ee" -> student = new Student(name, Major.EE, credits, "");
                        case "me" -> student = new Student(name, Major.ME, credits, "");
                        default -> notMajor = true;

                }
                if (notMajor){
                        System.out.println("'" + major + "' is not a valid major.");
                        return;
                }
                if (roster.remove(student)){
                        System.out.println("Student removed from the roster");
                } else {
                        System.out.println("Student is not in the roster");
                }
        }

        /**
         * calculates the tuition of all students on the roster
         */
        private void calculateTuition() {
                roster.calculateTuition();
                System.out.println("Calculation completed.");
        }

        /**
         * pays the tuition of a student at a specific date in 2021
         *
         * @param tokenizer
         */
        private void payTuition(StringTokenizer tokenizer) {
                int counter = FIRST_PARAMETER;
                String name = "", major = "";
                double tuitionPayment = DECISTARTING_CREDITS;
                Date date = new Date();
                while (tokenizer.hasMoreTokens()) {
                        if (counter == FIRST_PARAMETER) {
                                name = tokenizer.nextToken();
                        } else if (counter == SECOND_PARAMETER) {
                                major = tokenizer.nextToken();
                        } else if (counter == THIRD_PARAMETER) {
                                tuitionPayment = Double.parseDouble(tokenizer.nextToken());
                                if (tuitionPayment <= 0) {
                                        System.out.println("Invalid amount.");
                                        return;
                                }
                        } else if (counter == FOURTH_PARAMETER) {
                                date = new Date(tokenizer.nextToken());
                                if (!date.isValid()){
                                        System.out.println("Payment date invalid.");
                                        return;
                                }
                        }
                        counter++;
                }
                if (counter != TOTAL_PARAMETERS) {
                        if (counter  == THIRD_PARAMETER){
                                System.out.println("Payment amount missing.");
                                return;
                        } else {
                                System.out.println("Missing data in command line.");
                                return;
                        }
                }
                Student student = new Student();
                major = major.toLowerCase();
                boolean notMajor = false;
                switch (major) {
                        case "cs" -> student = new Student(name, Major.CS, VALID_CREDITS, "");
                        case "it" -> student = new Student(name, Major.IT, VALID_CREDITS, "");
                        case "ba" -> student = new Student(name, Major.BA, VALID_CREDITS, "");
                        case "ee" -> student = new Student(name, Major.EE, VALID_CREDITS, "");
                        case "me" -> student = new Student(name, Major.ME, VALID_CREDITS, "");
                        default -> notMajor = true;

                }
                if (notMajor) {
                        System.out.println("'" + major + "' is not a valid major.");
                        return;
                }
                int payment = roster.payTuition(student, tuitionPayment, date);
                if (payment == OVER_PAY){
                        System.out.println("Amount is greater than amount due");
                } else{
                        System.out.println("Payment applied.");
                }
        }

        /**
         * changes an international student's study abroad status
         *
         * @param tokenizer
         */
        private void studyAbroad(StringTokenizer tokenizer) {
                int counter = FIRST_PARAMETER;
                String name = "", major = "";
                boolean studyAbroad = true;
                Date date = new Date();
                while (tokenizer.hasMoreTokens()) {
                        if (counter == FIRST_PARAMETER) {
                                name = tokenizer.nextToken();
                        } else if (counter == SECOND_PARAMETER){
                                major = tokenizer.nextToken();
                        } else if (counter == THIRD_PARAMETER) {
                               studyAbroad = Boolean.parseBoolean((tokenizer.nextToken()));
                        }
                        counter++;
                }
                Student student = new Student();
                major = major.toLowerCase();
                boolean notMajor = false;
                switch (major){
                        case "cs" -> student = new Student(name, Major.CS, VALID_CREDITS, "");
                        case "it" -> student =new Student(name, Major.IT, VALID_CREDITS, "");
                        case "ba" -> student =new Student(name, Major.BA, VALID_CREDITS, "");
                        case "ee" -> student =new Student(name, Major.EE, VALID_CREDITS, "");
                        case "me" -> student =new Student(name, Major.ME, VALID_CREDITS, "");
                        default -> notMajor = true;

                }
                if (notMajor) {
                        System.out.println("'" + major + "' is not a valid major.");
                        return;
                }
                if (roster.studyAbroad(student, studyAbroad)) {
                        System.out.println("Tuition updated.");
                } else{
                        System.out.println("Couldn't find the international student.");
                }
        }

        /**
         * awards a resident student financial aid
         *
         * @param tokenizer
         */
        private void financialAid(StringTokenizer tokenizer) {
                int counter = FIRST_PARAMETER;
                String name = "", major = "";
                double financialAid = STARTING_CREDITS;
                while (tokenizer.hasMoreTokens()) {
                        if (counter == FIRST_PARAMETER) {
                                name = tokenizer.nextToken();
                        } else if (counter == SECOND_PARAMETER){
                                major = tokenizer.nextToken();
                        } else if (counter == THIRD_PARAMETER) {
                                String fakeAid =  tokenizer.nextToken();
                                if (fakeAid.matches("[a-zA-Z]+")){
                                        System.out.println("Invalid credit hours");
                                        return;
                                }
                                financialAid = Double.parseDouble(fakeAid);
                        }
                        counter++;
                }
                if (counter != FOURTH_PARAMETER) {
                        if (counter == THIRD_PARAMETER) {
                                System.out.println("Missing the amount.");
                                return;
                        } else {
                                System.out.println("Missing data in command line.");
                                return;
                        }
                }
                Student student = new Student();
                major = major.toLowerCase();
                boolean notMajor = false;
                switch (major){
                        case "cs" -> student = new Student(name, Major.CS, VALID_CREDITS, "");
                        case "it" -> student = new Student(name, Major.IT, VALID_CREDITS, "");
                        case "ba" -> student = new Student(name, Major.BA, VALID_CREDITS, "");
                        case "ee" -> student = new Student(name, Major.EE, VALID_CREDITS, "");
                        case "me" -> student = new Student(name, Major.ME, VALID_CREDITS, "");
                        default -> notMajor = true;

                }
                if (notMajor){
                        System.out.println("'" + major + "' is not a valid major.");
                        return;
                }
                int finAid = roster.payAid(student, financialAid);
                if (finAid == STUDENT_NOT_FOUND){
                        System.out.println("Student not in the roster.");
                } else if (finAid == ALREADY_AWARDED){
                        System.out.println("Awarded once already.");
                } else if (finAid == NOT_RESIDENT){
                        System.out.println("Not a resident student.");
                } else if (finAid == PART_TIME_INELIGIBLE){
                        System.out.println("Parttime student doesn't qualify for the award.");
                } else if (finAid == INVALID_AWARD){
                        System.out.println("Invalid amount");
                } else{
                        System.out.println("Tuition updated.");
                }
        }

        /**
         * Displays the entire roster with one of the printing methods
         * Can be displayed randomly, sorted by name, or sorted by payment date
         *
         * @param command stores the method to print the roster
         */
        private void display(String command) {
                if (roster.isEmpty()) {
                        System.out.println("Student roster is empty!");
                } else if (command.equals("P")){
                        System.out.println("* list of students in the roster **");
                        roster.print();
                        System.out.println("* end of roster **");
                }
                else if (command.equals("PT")) {
                        System.out.println("* list of students made payments ordered by payment date **");
                        roster.printByPaymentDate();
                        System.out.println("* end of roster **");
                } else {
                        System.out.println("* list of students ordered by name **");
                        roster.printByName();
                        System.out.println("* end of roster **");
                }
        }
}
