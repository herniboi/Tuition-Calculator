package studenttuition;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * This class stores and compares dates and checks if the dates are valid.
 * Stores day, month and year to validate, access and compare.
 *
 * @author Andy Li, Henry Lin
 */

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    /**
     * Constructor method given a String of the date.
     *
     * @param date format "mm/dd/yyyy"
     */
    public Date(String date) {
        StringTokenizer tokenizer = new StringTokenizer(date, "/");
        this.month = Integer.parseInt(tokenizer.nextToken());
        this.day = Integer.parseInt(tokenizer.nextToken());
        this.year = Integer.parseInt(tokenizer.nextToken());
    } //take “mm/dd/yyyy” and create a Date object

    /**
     * Constructor method given no parameters.
     */
    public Date() {
    } //create an object with today’s date (see Calendar class)

    // statics
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;
    private static final int THIS_YEAR = 2021;
    private static final int SUBTRACT_TO_COMPARE = 1;
    private static final int TWENTY_NINTH_DAY = 29;
    private static final int THIRTIETH_DAY = 30;
    private static final int THIRTY_FIRST_DAY = 31;
    private static final int IS_DIVISIBLE = 0;
    private static final int BEFORE = -1;
    private static final int SAME = 0;
    private static final int AFTER = 1;

    /**
     * Checks if the date is valid
     * refer to google doc for all cases of valid dates.
     *
     * @return true if date is valid and false otherwise
     */
    public boolean isValid() {
        if (this.year > THIS_YEAR || this.year < THIS_YEAR) {
            return false;
        }
        int month = this.month - SUBTRACT_TO_COMPARE;
        Calendar calendar = Calendar.getInstance();
        if (month > (Calendar.DECEMBER)) {
            return false;
        }
        if (this.day > THIRTY_FIRST_DAY && (month == Calendar.JANUARY || month == Calendar.MARCH
                || month == Calendar.MAY || month == Calendar.JULY || month == Calendar.AUGUST
                || month == Calendar.OCTOBER || month == Calendar.DECEMBER)) {
            return false;
        }
        if (this.day > THIRTIETH_DAY && (month == Calendar.APRIL || month == Calendar.JUNE
                || month == Calendar.SEPTEMBER || month == Calendar.NOVEMBER)) {
            return false;
        }
        if (this.day > TWENTY_NINTH_DAY && month == Calendar.FEBRUARY) {
            return false;
        }
        if (this.day == TWENTY_NINTH_DAY && month == Calendar.FEBRUARY) {
            if (this.year % QUADRENNIAL != IS_DIVISIBLE || this.year % CENTENNIAL != IS_DIVISIBLE || this.year % QUATERCENTENNIAL != IS_DIVISIBLE) {
                return false;
            }
        }

        if (this.year > calendar.get(Calendar.YEAR)){
            return false;
        }
        else if (this.year == calendar.get(Calendar.YEAR) && month > calendar.get(Calendar.MONTH)){
            return false;
        }
        else return this.year != calendar.get(Calendar.YEAR) || month != calendar.get(Calendar.MONTH)
                    || this.day <= calendar.get(Calendar.DATE);
    }

    /**
     * Compares two dates and returns whether it is before, after, or on the same date as the respective date.
     *
     * @param date in format mm/dd/yyyy
     * @return AFTER if date after date, BEFORE if date is before date, SAME if the dates are the same
     */
    @Override
    public int compareTo(Date date) {
        if (this.year > date.year) {
            return AFTER;
        } else if (this.year < date.year) {
            return BEFORE;
        } else {
            if (this.month > date.month) {
                return AFTER;
            } else if (this.month < date.month) {
                return BEFORE;
            } else {
                if (this.day > date.day) {
                    return AFTER;
                } else if (this.day < date.day) {
                    return BEFORE;
                }
            }
        }
        return SAME;
    }

    /**
     * Returns date as string.
     *
     * @return date
     */
    public String toString() {
        String date;
        date = this.month + "/" + this.day + "/" + this.year;
        return date;
    }
}
