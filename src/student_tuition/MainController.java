package student_tuition;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import java.text.DecimalFormat;

/**
 * This class is the controller for the GUI that handles
 * all functionality for buttons in the GUI.
 *
 * @author Andy Li, Henry Lin
 */
public class MainController {
    private Roster roster = new Roster();

    @FXML
    private TextField name, credHours, paymentAmount, finAidAmount, tuitionDue, name2;

    @FXML
    private DatePicker date;

    @FXML
    private TextArea text;

    @FXML
    private ToggleGroup major, resident, tristate, major2;

    @FXML
    private CheckBox abroadStatus;


    // statics used
    private static final double DECISTARTING_CREDITS = 0;
    private static final int VALID_CREDITS = 16;
    private static final int OVER_PAY = 2;
    private static final int STUDENT_NOT_FOUND = 0;
    private static final int PART_TIME_INELIGIBLE = 1;
    private static final int ALREADY_AWARDED = 2;
    private static final int NOT_RESIDENT = 3;
    private static final int INVALID_AWARD = 5;
    public static final DecimalFormat df2 = new DecimalFormat( "#0.00" );

    /**
     * clears first tab in GUI
     */
    void clear() {
        credHours.clear();
        name.clear();
        abroadStatus.setDisable(true);
        abroadStatus.setSelected(false);

        major.getToggles().forEach(toggle -> {
            RadioButton tempButton = (RadioButton) toggle;
            tempButton.setSelected(false);
        });

        tristate.getToggles().forEach(toggle -> {
            RadioButton tempButton = (RadioButton) toggle;
            tempButton.setSelected(false);
            tempButton.setDisable(true);
        });

        resident.getToggles().forEach(toggle -> {
            RadioButton tempButton = (RadioButton) toggle;
            tempButton.setSelected(false);
        });
    }

    /**
     * Event handler for the Add student button
     * Adds specified student to the Roster
     * @param event
     */
    @FXML
    void addStudent(ActionEvent event) {
        boolean tempAbroadStatus = abroadStatus.isSelected();
        if (name.getText().trim().isEmpty()){
            text.appendText("Missing name.\n");
            return;
        }
        String tempName = name.getText();
        String tempMajor;
        String tempResident;
        String tempState = null;

        if (resident.getSelectedToggle() != null)
            tempResident = ((RadioButton) resident.getSelectedToggle()).getText();
        else {
            text.appendText("Missing status.\n");
            return;
        }

        if (tristate.getSelectedToggle() != null) {
            tempState = ((RadioButton) tristate.getSelectedToggle()).getText();
            if (tempState.equals("New York")) {
                tempState = "ny";
            } else {
                tempState = "ct";
            }
        }
        else if (tempResident.equals("TriState")) {
            text.appendText("Missing state.\n");
            return;
        }

        int credits;
        if (credHours.getText().matches("[a-zA-Z]+")){
            text.appendText("Invalid credit hours\n");
            return;
        }
        credits = Integer.parseInt(credHours.getText());
        if (credits < 3 & credits >= 0){
            text.appendText("Minimum credit hours is 3.\n");
            return;
        } else if (tempResident.equals("International") && credits < 12) {
            text.appendText("Minimum credit hours for international students is 12.\n");
            return;
        } else if (tempAbroadStatus && credits > 12) {
            text.appendText("Maximum credit hours for students studying abroad is 12.\n");
            return;
        } else if (credits > 24){
            text.appendText("Credit hours exceed the maximum 24.\n");
            return;
        } else if (credits < 0) {
            text.appendText("Credit hours cannot be negative.\n");
            return;
        }
        if (major.getSelectedToggle() != null)
            tempMajor = ((RadioButton) major.getSelectedToggle()).getText();
        else {
            text.appendText("Missing major.\n");
            return;
        }
        Student student;
        tempMajor = tempMajor.toLowerCase();
        boolean nonMajor = false;
        student = switch (tempResident) {
            case "Resident" -> switch (tempMajor) {
                case "cs" -> new Resident(tempName, Major.CS, credits, tempResident);
                case "it" -> new Resident(tempName, Major.IT, credits, tempResident);
                case "ba" -> new Resident(tempName, Major.BA, credits, tempResident);
                case "ee" -> new Resident(tempName, Major.EE, credits, tempResident);
                case "me" -> new Resident(tempName, Major.ME, credits, tempResident);
                default -> null;
            };
            case "Non-Resident" -> switch (tempMajor) {
                case "cs" -> new NonResident(tempName, Major.CS, credits, tempResident);
                case "it" -> new NonResident(tempName, Major.IT, credits, tempResident);
                case "ba" -> new NonResident(tempName, Major.BA, credits, tempResident);
                case "ee" -> new NonResident(tempName, Major.EE, credits, tempResident);
                case "me" -> new NonResident(tempName, Major.ME, credits, tempResident);
                default -> null;
            };
            case "TriState" -> switch (tempMajor) {
                case "cs" -> new TriState(tempName, Major.CS, credits, tempState, "non-resident(tristate)");
                case "it" -> new TriState(tempName, Major.IT, credits, tempState, "non-resident(tristate)");
                case "ba" -> new TriState(tempName, Major.BA, credits, tempState, "non-resident(tristate)");
                case "ee" -> new TriState(tempName, Major.EE, credits, tempState, "non-resident(tristate)");
                case "me" -> new TriState(tempName, Major.ME, credits, tempState, "non-resident(tristate)");
                default -> null;
            };
            default -> switch (tempMajor) {
                case "cs" -> new International(tempName, Major.CS, credits, tempAbroadStatus, "non-resident:international");
                case "it" -> new International(tempName, Major.IT, credits, tempAbroadStatus, "non-resident:international");
                case "ba" -> new International(tempName, Major.BA, credits, tempAbroadStatus, "non-resident:international");
                case "ee" -> new International(tempName, Major.EE, credits, tempAbroadStatus, "non-resident:international");
                case "me" -> new International(tempName, Major.ME, credits, tempAbroadStatus, "non-resident:international");
                default -> null;
            };
        };

        if (roster.add(student)) {
            text.appendText("Student added.\n");
        } else {
            text.appendText("Student is already in the roster.\n");
        }
        clear();
    }

    /**
     * Event handler for the Remove student button
     * Removes specified student from the Roster
     * @param event
     */
    @FXML
    void removeStudent(ActionEvent event){
        String tempName = name.getText();
        String tempMajor = null;
        if (major.getSelectedToggle() != null)
            tempMajor = ((RadioButton) major.getSelectedToggle()).getText();
        else {
            text.appendText("Missing major.\n");
            return;
        }
        int credits = 12;
        Student student;
        tempMajor = tempMajor.toLowerCase();
        student = switch (tempMajor){
            case "cs" -> new Student(tempName, Major.CS, credits, "");
            case "it" -> new Student(tempName, Major.IT, credits, "");
            case "ba" -> new Student(tempName, Major.BA, credits, "");
            case "ee" -> new Student(tempName, Major.EE, credits, "");
            case "me" -> new Student(tempName, Major.ME, credits, "");
            default -> null;
        };
        if (roster.remove(student)) {
            text.appendText("Student removed from the roster\n");
        } else {
            text.appendText("Student is not in the roster\n");
        }
        clear();
    }

    /**
     * Event handler for the tuitionDue
     * Calculates tuition for speciifc student
     * @param event
     */
    @FXML
    void tuitionDue(ActionEvent event){
        if(!tuitionDue.getText().isEmpty()){
            tuitionDue.clear();
        }
        if (name.getText().trim().isEmpty()){
            text.appendText("Missing name.\n");
            return;
        }
        String tempName = name.getText();
        String tempMajor;
        int credits = 12;
        if (major.getSelectedToggle() != null)
            tempMajor = ((RadioButton) major.getSelectedToggle()).getText();
        else {
            text.appendText("Missing major.\n");
            return;
        }
        Student student;
        tempMajor = tempMajor.toLowerCase();
        student = switch (tempMajor){
            case "cs" -> new Student(tempName, Major.CS, credits, "");
            case "it" -> new Student(tempName, Major.IT, credits, "");
            case "ba" -> new Student(tempName, Major.BA, credits, "");
            case "ee" -> new Student(tempName, Major.EE, credits, "");
            case "me" -> new Student(tempName, Major.ME, credits, "");
            default -> null;
        };
        int index = roster.findStudent(student);
        if ((index >= 0) && student.tuitionPaid == 0){
            Double tuition = roster.getTuition(student);
            tuitionDue.appendText(tuition + "");
        } else{
            text.appendText("Student is not in roster. Tuition cannot be calculated.\n");
        }

        clear();
    }
    /**
     * Event handler for calculating tuition
     * Calculates the tuition of all students in the roster
     * @param event
     */
    @FXML
    void calculateTuition(ActionEvent event){
        roster.calculateTuition();
        text.appendText("Calculation Completed\n");
    }

    /**
     * Event handler for paying tuiiton
     * pays the tuition of a student at a specific date
     * @param event
     */
    @FXML
    void payTuition(ActionEvent event){
        String tempName = name.getText();
        String tempMajor = null;
        if (major.getSelectedToggle() != null)
            tempMajor = ((RadioButton) major.getSelectedToggle()).getText();
        String newDate = date.getValue().toString();

        Date tempDate = new Date(newDate);
        if (!tempDate.isValid()){
            text.appendText("Payment date invalid.\n");
            return;
        }
        double tuitionPayment = DECISTARTING_CREDITS;
        tuitionPayment = Double.parseDouble(paymentAmount.getText());
        if (tuitionPayment <= 0){
            text.appendText("Invalid amount.\n");
            return;
        }
        Student student;
        tempMajor = tempMajor.toLowerCase();
        student = switch (tempMajor){
            case "cs" -> new Student(tempName, Major.CS, VALID_CREDITS, "");
            case "it" -> new Student(tempName, Major.IT, VALID_CREDITS, "");
            case "ba" -> new Student(tempName, Major.BA, VALID_CREDITS, "");
            case "ee" -> new Student(tempName, Major.EE, VALID_CREDITS, "");
            case "me" -> new Student(tempName, Major.ME, VALID_CREDITS, "");
            default -> null;
        };
        int index = roster.findStudent(student);
        if (index == -1) {
            text.appendText("Student does not exist in roster.\n");
            name2.clear();
            paymentAmount.clear();
            date.setValue(null);
            major2.getToggles().forEach(toggle -> {
                RadioButton tempButton = (RadioButton) toggle;
                tempButton.setSelected(false);
            });
            return;
        }
        int payment = roster.payTuition(student, tuitionPayment, tempDate);
        if (payment == OVER_PAY){
            text.appendText("Amount is greater than amount due\n");
            return;
        } else{
            text.appendText("Payment applied.\n");
        }
        name.clear();
        paymentAmount.clear();
        date.setValue(null);
        major.getToggles().forEach(toggle -> {
            RadioButton tempButton = (RadioButton) toggle;
            tempButton.setSelected(false);
        });
    }

    /**
     * Event Handler for awarding students financial aid
     * awards a resident student financial aid
     *
     * @param event
     */
    @FXML
    private void setFinAidAmount(ActionEvent event) {
        String tempName;
        if (name2.getText().trim().isEmpty()){
            text.appendText("Missing name.\n");
            return;
        }
        tempName = name2.getText();
        String tempMajor = null;
        if (major2.getSelectedToggle() != null)
            tempMajor = ((RadioButton) major2.getSelectedToggle()).getText();
        else {
            text.appendText("Missing major.\n");
        }
        if (finAidAmount.getText().trim().isEmpty()){
            text.appendText("Missing financial aid amount.\n");
            return;
        } else if (finAidAmount.getText().matches("[a-zA-Z]+")){
            text.appendText("Invalid credit hours\n");
            return;
        }
        double financialAid = Double.parseDouble(finAidAmount.getText());
        Student student;
        tempMajor = tempMajor.toLowerCase();
        student = switch (tempMajor){
            case "cs" -> new Student(tempName, Major.CS, VALID_CREDITS, "");
            case "it" -> new Student(tempName, Major.IT, VALID_CREDITS, "");
            case "ba" -> new Student(tempName, Major.BA, VALID_CREDITS, "");
            case "ee" -> new Student(tempName, Major.EE, VALID_CREDITS, "");
            case "me" -> new Student(tempName, Major.ME, VALID_CREDITS, "");
            default -> null;
        };
        if (finAidAmount.getText().equals("")){
            text.appendText("Missing the amount.\n");
            return;
        }
        int finAid = roster.payAid(student, financialAid);
        if (finAid == STUDENT_NOT_FOUND){
            text.appendText("Student not in the roster.\n");
        } else if (finAid == ALREADY_AWARDED){
            text.appendText("Awarded once already.\n");
        } else if (finAid == NOT_RESIDENT){
            text.appendText("Not a resident student.\n");
        } else if (finAid == PART_TIME_INELIGIBLE){
            text.appendText("Parttime student doesn't qualify for the award.\n");
        } else if (finAid == INVALID_AWARD){
            text.appendText("Invalid amount\n");
        } else{
            text.appendText("Tuition updated.\n");
        }
        name2.clear();
        finAidAmount.clear();
        major2.getToggles().forEach(toggle -> {
            RadioButton tempButton = (RadioButton) toggle;
            tempButton.setSelected(false);
        });
    }

    /**
     * enables NY and CT button if tristate is selected
     * disables abroad checkbox
     * @param event
     */
    @FXML
    void triState(ActionEvent event) {
        tristate.getToggles().forEach(toggle -> {
            RadioButton node = (RadioButton) toggle ;
            node.setDisable(false);
        });
        abroadStatus.setDisable(true);
        abroadStatus.setSelected(false);
    }

    @FXML
    /**
     * when the resident or non resident button is selected
     * disables NY and CT button
     * disables abroad checkbox
     * @param event
     */
    void resNonRes(ActionEvent event) {
        tristate.getToggles().forEach(toggle -> {
            RadioButton node = (RadioButton) toggle ;
            node.setDisable(true);
            node.setSelected(false);
        });
        abroadStatus.setDisable(true);
        abroadStatus.setSelected(false);
    }

    /**
     * when the international button is selected
     * disables NY and CT button
     * enables abroad checkbox
     * @param event
     */
    @FXML
    void international(ActionEvent event) {
        tristate.getToggles().forEach(toggle -> {
            RadioButton node = (RadioButton) toggle ;
            node.setDisable(true);
            node.setSelected(false);
        });
        abroadStatus.setDisable(false);
    }
    /**
     * Displays the entire roster randomly
     *
     * @param event
     */
    @FXML
    void print(ActionEvent event) {
        if (roster.isEmpty()) {
            text.appendText("Student roster is empty!\n");
        }
        text.appendText("* list of students in the roster **\n" + roster.print() +
                "* end of roster **\n");
    }

    /**
     * Displays the entire roster sorted by payment date
     *
     * @param event
     */
    @FXML
    void printByDate(ActionEvent event){
        if (roster.isEmpty()) {
            text.appendText("Student roster is empty!\n");
        }
        text.appendText("* list of students made payments ordered by payment date **\n" +
                roster.printByPaymentDate() + "* end of roster **\n");

    }

    /**
     * Displays the entire roster sorted by name
     *
     * @param event
     */
    @FXML
    void printByName(ActionEvent event){
        if (roster.isEmpty()) {
            text.appendText("Student roster is empty!\n");
        }
        text.appendText("* list of students in the roster ordered by name **\n" +
                roster.printByName() + "* end of roster **\n");
    }

    /**
     * Gives attribution to contributors to the project
     *
     * @param event
     */
    @FXML
    void help(ActionEvent event){
        text.appendText("Project was completed by Andy Li and Henry Lin\n");
    }
}
