package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestString {
    public static void main(String[] args) {
        Date today = new Date();
        String pattern = "yyyyMMddHHmmssS"; // ss = seconds, S = milliseconds
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(today);
        System.out.println(todayAsString);

        String expr = "6hourS";
        String hour = expr.split("hourS")[0];
        System.out.print(hour);
    }
}
