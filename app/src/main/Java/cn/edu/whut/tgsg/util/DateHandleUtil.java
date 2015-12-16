package cn.edu.whut.tgsg.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * <p/>
 * Created by xwh on 2015/12/15.
 */
public class DateHandleUtil {

    /**
     * 转换为标准显示
     *
     * @param date
     * @return
     */
    public static String convertToStandard(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
