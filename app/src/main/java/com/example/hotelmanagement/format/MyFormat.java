package com.example.hotelmanagement.format;

import android.os.Build;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MyFormat {
    public static String VND(long m)
    {
        String text = "";
        long num = m / 1000 + 1;
        int num2 = 0;
        while ((long)num2 < num)
        {
            if (m < 1000L)
            {
                text = m + text;
                break;
            }
            long num3 = m % 1000L;
            text = ((num3 != 0L) ? ((num3 >= 10L) ? ((num3 >= 100L) ? ("." + num3 + text) : (".0" + num3 + text)) : (".00" + num3 + text)) : (".000" + text));
            m /= 1000;
            num2++;
        }
        return text;
    }

    public static String getDateTimeNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }


}
