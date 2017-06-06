package com.helio.cypher.utils;

import android.content.SharedPreferences;

import com.helio.cypher.views.ChartView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {

    private static final int DATE_START_RANGE = 26;
    private static final int DATE_END_RANGE = 3;

    public static String timeCalculate(long createTime) {

        long countDown = (System.currentTimeMillis() / 1000) - (createTime / 1000);
        long days, hours, minutes;
        days = (Math.round(countDown) / 86400);
        hours = (Math.round(countDown) / 3600) - (days * 24);
        minutes = (Math.round(countDown) / 60) - (days * 1440) - (hours * 60);

        String time = null;
        if (days == 0) {
            if (hours == 0) {
                if (minutes == 0) {
                    time = "right now";
                } else {
                    if (minutes == 1) {
                        time = "one minute ago";
                    } else {
                        time = String.format("%d " + "minutes ago", minutes);
                    }

                }
            } else {
                if (hours > 1) {
                    time = String.format("%d " + "hours ago", hours);
                } else {
                    time = "An hour ago";
                }
            }
        } else {
            if (days < 30) {
                if (days >= 1 && days <= 7) {
                    time = "Few days ago";
                } else {
                    if (days == 1) {
                        time = "Yesterday";
                    } else {
                        time = String.format("%d " + "days ago", days);
                    }

                }
            } else {
                if (days < 365) {
                    time = "Few month ago";
                } else {
                    time = "Few years ago";
                }
            }
        }
        return time;
    }

    private static final long SIX_MONTH = ChartView.ONE_DAY * 60;

    public static boolean countSixTime(String code, SharedPreferences pref) {
        if ((System.currentTimeMillis() - pref.getLong(code, 0)) > SIX_MONTH)
            return true;
        else
            return false;
    }

    public static String dayStringFormat(long msecs) {
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(new Date(msecs));

        int dow = cal.get(Calendar.DAY_OF_WEEK);

        switch (dow) {
            case Calendar.MONDAY:
                return "MON";
            case Calendar.TUESDAY:
                return "TUE";
            case Calendar.WEDNESDAY:
                return "WED";
            case Calendar.THURSDAY:
                return "THU";
            case Calendar.FRIDAY:
                return "FRI";
            case Calendar.SATURDAY:
                return "SAT";
            case Calendar.SUNDAY:
                return "SUN";
        }

        return "U";
    }

   /* public static String daynumber(Date msecs) {
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(msecs);

        int dow = cal.DAY_OF_YEAR;

        return ""+dow;
    }*/


    public static Date checkDateRange() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month == Calendar.DECEMBER) {
            if (day >= DATE_START_RANGE) {
                return genreStartOverDate(calendar.get(Calendar.YEAR));
            }
        } else if (month == Calendar.JANUARY) {
            if (day <= DATE_END_RANGE) {
                return genreStartOverDate(calendar.get(Calendar.YEAR) - 1);
            }
        }
        return null;
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        if (month == Calendar.JANUARY) {
            return calendar.get(Calendar.YEAR) - 1;
        }
        return calendar.get(Calendar.YEAR);
    }

    private static Date genreStartOverDate(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }

    public static Date getPreviousMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int month = calendar.get(Calendar.MONTH);

        if (month == Calendar.JANUARY) {
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            return calendar.getTime();
        } else {
            calendar.set(Calendar.MONTH, month - 1);
            return calendar.getTime();
        }
    }

    public static Date getCurrentMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static String getPreviousMonthName() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        if (month == Calendar.JANUARY) {
            return getMonth(Calendar.DECEMBER);
        } else {
            return getMonth(month - 1);

        }
    }

    private static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}
