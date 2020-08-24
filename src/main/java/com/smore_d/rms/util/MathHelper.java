package com.smore_d.rms.util;

public class MathHelper {

    public static double pythag(double a, double b) {
        return (Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
    }

    public static double[] toRect(double r, double theta) {
        if (theta != 0) {
            double[] coords = {0, 0};
            coords[0] = r * Math.cos(theta); // x
            coords[1] = r * Math.sin(theta); // y
            return coords;
        }
        return new double[]{0, 0}; // if theta is zero, x and y should be zero to avoid insanely huge numbers
    }

    public static double[] toPolar(double x, double y) {
        double[] val = {0, 0};
        val[0] = pythag(x, y); //magnitude
        val[1] = Math.atan(y / x); //angle

        if (x > 0 && y > 0) { // quadrant I
            return val;
        } else if (x < 0 && y > 0) { // quadrant II
            val[1] = Math.PI + val[1];
            return val;
        } else if (x < 0 && y < 0) { // quadrant III
            val[1] = Math.PI + val[1];
            return val;
        } else if (x > 0 && y < 0) { // quadrant IV
            val[1] = (Math.PI * 2) + val[1];
            return val;
        }
        return new double[]{0, 0}; // return zero if magnitude is zero
    }

    public static double[] toPolar(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return toPolar(deltaX, deltaY);
    }
}
