package com.example.cropdoctor.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static final String APP_ID="8e5a8572b79c35ecc50c37d5917fb456";
    public static Location current_location=null;

    public static String convertUnixToDate(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm EEE MM yyyy");
        String formatted=sdf.format(date);
        return formatted;

    }

    public static String convertUnixToHour(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String formatted=sdf.format(date);
        return formatted;
    }
}
