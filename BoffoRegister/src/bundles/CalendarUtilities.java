package bundles;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A class that cannot be instantiated which only offers Calender related
 * utilities.
 *
 * @author Michael Resnik
 * @author Travis Cox
 * @lastEdited 05/05/2017
 */
public abstract class CalendarUtilities {

    /**
     * Returns a String representation of the passed Calendar object in the
     * following format.
     *
     * @format "yyyy-MM-dd"
     * @param _calendarToBeFormatted
     * @return A String representation of the passed Calendar object.
     */
    public static String calendarToString(Calendar _calendarToBeFormatted) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(_calendarToBeFormatted.getTime());
    }


    /**
     * Returns the current Calendar date.
     *
     * @return The current Calendar date, starting from midnight [00:00:00 AM].
     */
    public static Calendar currentDate() {
        Date current = new Date(System.currentTimeMillis());
        Calendar calCurrent = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            current = new Date(sdf.parse(current.toString()).getTime());
        } catch (Exception e) {

        }
        calCurrent.setTime(current);
        return calCurrent;
    }


    /**
     *
     * Converts a passed String into a Calendar date by the following formats.
     *
     * @format "yyyy-MM-dd"
     * @format "MM-dd-yyyy"
     * @param _input A passed String to be converted into a Calendar date.
     * @return A Calendar date based on the input.
     */
    public static Calendar stringToCalendar(String _input) {
        SimpleDateFormat sdf;
        Calendar cal;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal = Calendar.getInstance();
            cal.setTime(sdf.parse(_input));
            return cal;
        } catch (Exception e1) {
            try {
                sdf = new SimpleDateFormat("MM-dd-yyyy");
                cal = Calendar.getInstance();
                cal.setTime(sdf.parse(_input));
                return cal;
            } catch (Exception e2) {

            }
        }
        return null;
    }


}
