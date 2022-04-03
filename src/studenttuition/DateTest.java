package studenttuition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateTest {

    @Test
    void isValid() {
        //Test Case 1: Date with valid day and month but with the year < 2021
        Date date = new Date("11/21/2000");
        Assertions.assertFalse(date.isValid());

        //Test Case 2: A date with the month = 2, day > 28, and the year is a non-leap year
        date = new Date("2/29/2021");
        Assertions.assertFalse(date.isValid());

        //Test Case 3: A date with an invalid month
        date = new Date("13/7/2021");
        Assertions.assertFalse(date.isValid());

        //Test Case 4: A date with a month = 1, 3, 5, 7, 8, 10, 12, but day > 31
        date = new Date("5/32/2021");
        Assertions.assertFalse(date.isValid());

        //Test Case 5: A date with a valid year and month = 4, 6, 9, 11, but day> 30
        date = new Date("4/31/2021");
        Assertions.assertFalse(date.isValid());

        //Test Case 6.1: A date with the year > current year
        date = new Date("9/27/2022");
        Assertions.assertFalse(date.isValid());

        //Test Case 6.2: A date with year = to current year and the month is > current month
        date = new Date("12/27/2021");
        Assertions.assertFalse(date.isValid());

        //Test Case 6.3: A date with year = to current year and month = current month but the date > current date
        date = new Date("11/28/2021");
        Assertions.assertFalse(date.isValid());

        //Test Case 7.1: A date with month = 1, 3, 5, 7, 8, 10, 12, and day = 31
        date = new Date("1/31/2021");
        Assertions.assertTrue(date.isValid());

        //Test Case 7.2: A date with a month = 4, 6, 9, 11, and day = 30
        date = new Date("4/30/2021");
        Assertions.assertTrue(date.isValid());

        //Test Case 7.3: A date with month = 2, day = 28, and the year is a non-leap year
        date = new Date("2/28/2021");
        Assertions.assertTrue(date.isValid());

        //Test Case 7.4: A date with year >= 2021 and date < current date
        date = new Date("1/17/2021");
        Assertions.assertTrue(date.isValid());
    }
}