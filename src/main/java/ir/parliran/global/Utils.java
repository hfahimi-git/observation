package ir.parliran.global;

import com.github.mfathi91.time.PersianDate;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ©hFahimi @ 2019/8/27 15:02
 */
public class Utils {
    public final static Map<String, Object> pojo2Map(Object obj) {
        Map<String, Object> hashMap = new HashMap<>();
        try {
            Class<? extends Object> c = obj.getClass();
            Method m[] = c.getMethods();
            for (int i = 0; i < m.length; i++) {
                if (m[i].getName().indexOf("get") == 0) {
                    String name = m[i].getName().toLowerCase().substring(3, 4) + m[i].getName().substring(4);
                    hashMap.put(name, m[i].invoke(obj));
                }
            }
        } catch (Throwable e) {
        }
        return hashMap;
    }

    public final static Map<String, Object> pojo2PropMap(Object obj) {
        Map<String, Object> hashMap = new HashMap<>();
        try {
            Class<? extends Object> c = obj.getClass();
            Field f[] = c.getFields();
            for (int i = 0; i < f.length; i++) {
                hashMap.put(f[i].getName(), f[i].get(obj));
            }
        } catch (Throwable e) {
        }
        return hashMap;
    }

    public static List<String> getFiles(Path directory) {
        try (Stream<Path> walk = java.nio.file.Files.walk(directory)) {

            return walk.filter(java.nio.file.Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String normalize(String str) {
        return str.replaceAll("\\t+", " ")
                .replaceAll("[\u200C]", " ")
                .replaceAll(" +", " ")
                .replaceAll("ي", "ی")
                .replaceAll("ك", "ک").trim();
    }

    public static String sanitize(String str) {
        return str.replaceAll("\\t+", " ")
                .replaceAll("[\u200C]", " ")
                .replaceAll(" +", " ").trim();
    }

    public static String getString(String value) {
        return getString(value, null);
    }

    public static String getString(String value, String defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Integer getInteger(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static long getLong(String value, long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Long getLongObject(String value) {
        if (value == null) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static double getDouble(String key) {
        return getDouble(key, 0);
    }

    public static double getDouble(String value, double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBool(String value) {
        return getBool(value, false);
    }

    public static boolean getBool(String value, boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value.equals("1") || value.equalsIgnoreCase("true");
    }

    public static long getElementAsLong(String[] params, int index) {
        if (index < 0 || params.length < index || params.length == 0) {
            return -1;
        }
        return Utils.getLong(params[index], -1);
    }

    public static String replaceAll(String str, String[] needles, String[] replace) {
        if (str == null)
            return null;
        for (int i = 0; i < needles.length; i++) {
            str = str.replaceAll(needles[i], replace[i]);

        }
        return str;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() < 1;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() < 1;
    }

    public static LocalDate j2g(String date) {
        try {
            String[] dateParts = date.split("[\\./-]");
            return PersianDate.of(
                    Integer.parseInt(dateParts[0]),
                    Integer.parseInt(dateParts[1]),
                    Integer.parseInt(dateParts[2])
            ).toGregorian();
        } catch (Exception ignored) {}
        return null;
    }

    public static String g2j(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            PersianDate jDate = PersianDate.fromGregorian(
                    LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
            );
            jDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            return jDate.getYear() + "/" + jDate.getMonthValue() + "/" + jDate.getDayOfMonth();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String g2j(String date) {
        try {
            String[] dateParts = date.split("[\\./-]");
            PersianDate jDate = PersianDate.fromGregorian(
                    LocalDate.of(
                            Integer.parseInt(dateParts[0]),
                            Integer.parseInt(dateParts[1]),
                            Integer.parseInt(dateParts[2])
                    )
            );
            return jDate.getYear() + "/" + jDate.getMonthValue() + "/" + jDate.getDayOfMonth();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String g2j(LocalDate date) {
        try {
            PersianDate jDate = PersianDate.fromGregorian(date);
            jDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            return jDate.getYear() + "/" + jDate.getMonthValue() + "/" + jDate.getDayOfMonth();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String g2j(LocalDateTime date) {
        try {
            PersianDate jDate = PersianDate.fromGregorian(LocalDate.from(date));
            jDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            return jDate.getYear() + "/" + jDate.getMonthValue() + "/" + jDate.getDayOfMonth() + " " +
                    date.getHour() + ":" + date.getMinute() + ":" + date.getSecond();
        } catch (Exception ignored) {
        }
        return null;
    }
}
