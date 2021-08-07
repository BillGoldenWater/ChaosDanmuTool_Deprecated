package indi.goldenwater.chaosdanmutool.utils;

public class IntColorToHex {
    public static String toHex(int intColor) {
        return String.format("%06X", (0xFFFFFF & intColor));
    }
}
