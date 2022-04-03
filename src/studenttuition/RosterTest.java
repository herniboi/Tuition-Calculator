package studenttuition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class RosterTest {

    @Test
    void add() {
        Student student = new International("John Smith", Major.CS, 12, false, "international");
        Roster roster = new Roster();

        //Test Case 1: Student does not exist in roster and is added
        Assertions.assertTrue(roster.add(student));

        //Test Case 2: Student already exists in roster and fails to be added
        Assertions.assertFalse(roster.add(student));
    }

    @Test
    void remove() {
        Student student = new International("John Smith", Major.CS, 12, false, "international");
        Roster roster = new Roster();
        roster.add(student);

        //Test Case 1: Student exists in roster and is removed
        Assertions.assertTrue(roster.remove(student));

        //Test Case 1: Student does not exist in roster and fails to be removed
        Assertions.assertFalse(roster.remove(student));
    }
}