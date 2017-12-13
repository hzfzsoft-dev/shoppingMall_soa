package org.fzsoft.shoppingmall.utils.commons;

import org.springframework.util.NumberUtils;

/**
 * Created by yhj on 17/7/28.
 */
public class NumberUtil extends NumberUtils {


    public static double trimToZero(Double d) {

        return d == null ? 0 : d;
    }

    public static Number[] trimToZero(Number... d) {

        if (d == null) {
            return new Number[0];
        }

        Number[] result = new Number[d.length];
        for (int i = 0; i < d.length; i++) {
            result[i] = (d[i] == null ? new Integer(0) : d[i]);
        }

        return result;
    }

    public static int trimToZero(Integer n) {

        return n == null ? 0 : n;
    }

    public static float trimToZero(Float n) {

        return n == null ? 0 : n;
    }


}
