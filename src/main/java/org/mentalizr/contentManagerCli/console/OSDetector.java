package org.mentalizr.contentManagerCli.console;

import java.util.Locale;

/**
 * Detects the type of OS the VM is running on.
 * Check before using if result is not UNKNOWN, if using code relies on a successful detection.
 */
public class OSDetector {

    public enum OS { WIN, MAC, LINUX, UNIX, SUN, UNKNOWN }

    private static final String osName;
    private static final OS os;

    static {
        String osNamePropValue = System.getProperty("os.name");
        if (osNamePropValue == null || osNamePropValue.equals(""))
            throw new RuntimeException("Could not read system property 'os.name': is null or empty.");

        osName = osNamePropValue.toLowerCase(Locale.ROOT);

        if (osName.contains("linux")) {
            os = OS.LINUX;
        } else if (osName.contains("nix") || osName.contains("aix") || osName.contains("irix") || osName.contains("freebsd") || osName.contains("mpe/ix") || osName.contains("hp-ux")) {
            os = OS.UNIX;
        } else if (osName.contains("win")) {
            os = OS.WIN;
        } else if (osName.contains("mac")) {
            os = OS.MAC;
        } else if (osName.contains("sunos") || osName.contains("solaris")) {
            os = OS.SUN;
        } else {
            os = OS.UNKNOWN;
        }
    }

    public static String getOsName() {
        return osName;
    }

    public static OS getOS() {
        return os;
    }

    public static boolean isLinux() {
        return os == OS.LINUX;
    }

    public static boolean isUnix() {
        return os == OS.UNIX;
    }

    public static boolean isWindows() {
        return os == OS.WIN;
    }

    public static boolean isMac() {
        return os == OS.MAC;
    }

    public static boolean isSun() {
        return os == OS.SUN;
    }

    public static boolean isUnknown() {
        return os == OS.UNKNOWN;
    }

    public static boolean isDetected() {
        return os != OS.UNKNOWN;
    }

}
