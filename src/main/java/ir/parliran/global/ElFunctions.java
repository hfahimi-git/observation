package ir.parliran.global;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ©hFahimi @ 2019/10/8 13:54
 */
public class ElFunctions {
    public static String g2j(LocalDateTime date) {
        return Utils.g2j(date);
    }

    public static String g2j(LocalDate date) {
        return Utils.g2j(date);
    }

    public static String digit2persian(String str) {
        return Utils.replaceAll(str,
                new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"},
                new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"}
        );
    }

    public static String parseFileSize(long bytes) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.#");
        if(bytes < 1024) {
            return decimalFormat.format(bytes) + " B";
        }
        if (bytes < 1024*1024) {
            return decimalFormat.format(bytes/1024) + " KB";
        }
        return decimalFormat.format(bytes/1024/1024) + " MB";
    }

    public static boolean fileExists(String fullPath) {
        Path p;
        try {
            p = Paths.get(fullPath);
            return Files.exists(p) && !Files.isDirectory(p);
        }
        catch (Exception ignore) {}
        return false;
    }

    public static String formatDate(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

}
