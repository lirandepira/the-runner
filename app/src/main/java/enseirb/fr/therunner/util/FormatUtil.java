package enseirb.fr.therunner.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class FormatUtil {

    public static String formatDistance(double distance) {
        String dist = String.valueOf(distance);
        dist = dist.replace(",", ".");
        String km = "0";
        if (dist.contains(".")) {
            km = dist.substring(0, dist.indexOf("."));
        } else {
            return dist + "km";
        }
        String meters = "0";
        if (dist.contains(".")) {
            meters = dist.substring(dist.indexOf(".")+1);
            meters = StringUtils.rightPad(meters, 3, "0");
            meters = meters.substring(0, 3);
        }

        return km + "km " + meters + "m";
    }

    public static String formatTime(long time) {
        long total = time / 1000;
        BigDecimal var3600 = new BigDecimal("3600");
        BigDecimal var60 = new BigDecimal("60");
        BigDecimal roundThreeCalc = new BigDecimal(String.valueOf(total));
        BigDecimal hours = roundThreeCalc.divide(var3600, BigDecimal.ROUND_FLOOR);
        BigDecimal myremainder = roundThreeCalc.remainder(var3600);
        BigDecimal minutes = myremainder.divide(var60, BigDecimal.ROUND_FLOOR);
        BigDecimal seconds = myremainder.remainder(var60);
        StringBuilder result = new StringBuilder();
        if (!hours.equals(BigDecimal.ZERO)){
            result.append(hours.toString()).append("h ");
        }
        if (!minutes.equals(BigDecimal.ZERO)){
            result.append(minutes.toString()).append("m ");
        }
        if (!seconds.equals(BigDecimal.ZERO)){
            result.append(seconds.toString()).append("s");
        }
        return result.toString().trim();
    }


    public static String formatSpeed(double kilometers, long milliseconds){
        double kilomerPerHour = kilometers / (milliseconds/1000) * 3600;
        return String.format ("%.2f",kilomerPerHour) + "km/h";
    }
}
