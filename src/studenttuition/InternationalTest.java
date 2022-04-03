package studenttuition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InternationalTest {

    @Test
    void tuitionDue() {
        //Test Case 1: International student with valid credit hours < 16 who is not studying abroad
        Student student = new International("John Smith", Major.CS, 12, false, "international");
        student.tuitionDue();
        Assertions.assertEquals(student.tuitionDue, 29737 + 3268 + 2650);

        //Test Case 2: International student with valid credit hours > 16 who is not studying abroad
        student = new International("John Smith", Major.CS, 18, false, "international");
        student.tuitionDue();
        Assertions.assertEquals(student.tuitionDue, 29737 + 3268 + 2650 + 966 * 2);

        //Test Case 3: International student who is studying abroad.
        student = new International("John Smith", Major.CS, 20, true, "international");
        student.tuitionDue();
        Assertions.assertEquals(student.tuitionDue, 3268 + 2650);
    }
}