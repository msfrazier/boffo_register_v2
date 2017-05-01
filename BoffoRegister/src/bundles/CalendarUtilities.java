package bundles;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class CalendarUtilities {

    /**
     * yyyy-MM-dd 
     * MM-dd-yyyy
     *
     * @param input
     * @return
     */
    public static Calendar stringToCalendar(String input) {
        SimpleDateFormat sdf;
        Calendar cal;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal = Calendar.getInstance();
            cal.setTime(sdf.parse(input));
            return cal;
        } catch (Exception e1) {
            try {
                sdf = new SimpleDateFormat("MM-dd-yyyy");
                cal = Calendar.getInstance();
                cal.setTime(sdf.parse(input));
                return cal;
            } catch (Exception e2) {

            }
        }
        return null;
    }


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


    public static String formatCalendar(Calendar cal) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }


}
