package com.shark.wheelpicker;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(5, 2 + 2);
    }

    @Test
    public void name() throws Exception {
        System.out.println("体重值和肌肉量的区间");
        System.out.println("LB Max:" + convertAndFormatWeightKgToLb(450));
        System.out.println("LB Min:" + convertAndFormatWeightKgToLb(5));
        System.out.println("ST Max:" + convertAndFormatWeightKgToSt(450));
        System.out.println("ST Min:" + convertAndFormatWeightKgToSt(5));
        System.out.println("骨量的区间");
        System.out.println("LB Max:" + convertAndFormatWeightKgToLb(5));
        System.out.println("LB Min:" + convertAndFormatWeightKgToLb(1));
        System.out.println("ST Max:" + convertAndFormatWeightKgToSt(5));
        System.out.println("ST Min:" + convertAndFormatWeightKgToSt(1));
    }
    /**
     * kg --> st(最新的)
     *
     * @author licheng 2014年5月6日
     */
    public static String convertAndFormatWeightKgToSt(float kgValue) {
        double a = kgValue * 2.20462 / 14;
        int a_i = (int) a;
        double b = (kgValue * 2.20462 - a_i * 14) + 0.005;
        BigDecimal bd = new BigDecimal(b);

        double b_i = bd.setScale(1, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        double c = b + 0.05;
        int c_i = (int) c;
        if (c_i == 14) {
            a_i = a_i + 1;
            b_i = 0;
        }
        return a_i + ":" + b_i;

    }

    /**
     * Kg->Lb
     *
     * @param keValue
     * @return
     */
    public static float convertAndFormatWeightKgToLb(float keValue) {
        double lb = keValue * 2.20462;
        BigDecimal b = new BigDecimal(lb);
        float result = b.setScale(1, BigDecimal.ROUND_DOWN).floatValue();
        return result;
    }
}