package vnot.mynursingtb.v1.createalarm;
import android.content.res.Resources;
import java.util.Calendar;
import vnot.mynursingtb.v1.R;
import vnot.mynursingtb.v1.alarmslist.*;

public final class DayUtil {
    public static final String toDay(int day) throws Exception {
        switch (day) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
//            case Calendar.SUNDAY:
//                return hariMinggu;
//            case Calendar.MONDAY:
//                return hariSenin;
//            case Calendar.TUESDAY:
//                return hariSelasa;
//            case Calendar.WEDNESDAY:
//                return hariRabu;
//            case Calendar.THURSDAY:
//                return hariKamis;
//            case Calendar.FRIDAY:
//                return hariJumat;
//            case Calendar.SATURDAY:
//                return hariSabtu;
        }
        throw new Exception("Could not locate day");
//        throw new Exception(notLocatedDay);
    }
}
