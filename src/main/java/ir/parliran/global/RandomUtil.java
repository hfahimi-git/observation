package ir.parliran.global;

import java.security.SecureRandom;

/**
 * ©hFahimi.com @ 2019/12/22 11:22
 * copied from https://stackoverflow.com/questions/3804591/efficient-method-to-generate-uuid-string-in-java-uuid-randomuuid-tostring-w
 */

public class RandomUtil {
    private static volatile SecureRandom numberGenerator = null;
    private static final long MSB = 0x8000000000000000L;

    public static String unique() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
    }
}
