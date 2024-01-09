package org.algotithmcontestdatacollect.managebackend.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author CharmingZe
 * @version 1.0
 * @data 2023/2/23 9:17
 */
public class TimeUtils {
    public static String timestampToString(Long timestamp) {
        String s = String.valueOf(timestamp);
        if(!(s.length()==10||s.length()==13))
            return "非法时间戳";
        if(s.length()==10)
            timestamp*=1000;
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

}
