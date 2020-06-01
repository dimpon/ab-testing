package com.dimpon.abtesting;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Utils {

    //significance level
    public static final double ALPHA = 0.05;
    public static final double ALPHA_DIV_TWO = 0.025;


    public static double studentQuantile(double alpha, double degreesOfFreedom) {
        return new TDistribution(degreesOfFreedom).inverseCumulativeProbability(alpha);
    }

    public static double normalQuantile(double alpha) {
        return new NormalDistribution().inverseCumulativeProbability(alpha);
    }

    public static double normalDistribution(double value) {
        return new NormalDistribution().cumulativeProbability(value);
    }

    public static double round2(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double round4(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static final Predicate<Double> isInTwoSidesInterval = (t) -> {
        double left = normalQuantile(ALPHA_DIV_TWO);
        double right = normalQuantile(1 - ALPHA_DIV_TWO);
        return t > left && t < right;
    };

    public static final Predicate<Double> isGreater = (t) -> {
        double left = normalQuantile(ALPHA);
        return t > left;
    };

    public static final Predicate<Double> isSmaller = (t) -> {
        double right = normalQuantile(1 - ALPHA);
        return t < right;
    };

    public static final Function<Double, Double> POfAInequalB = (t) -> Math.min(2 * normalDistribution(t), 2 - 2 * normalDistribution(t));

    public static final Function<Double, Double> POfALessThanB = Utils::normalDistribution;

    public static final Function<Double, Double> POfAGreaterThanB = (t) -> 1 - normalDistribution(t);

    public static final Consumer<Double> printAInequalB = (t) -> {
        double left = normalQuantile(ALPHA_DIV_TWO);
        double right = normalQuantile(1 - ALPHA_DIV_TWO);
        if (t > left && t < right)
            System.out.println("=========" + round2(left) + "----(T)" + round2(t) + "----" + round2(right) + "=========");
        if (t < left)
            System.out.println("====(T)" + round2(t) + "====" + round2(left) + "---------" + round2(right) + "=========");
        if (t > right)
            System.out.println("=========" + round2(left) + "---------" + round2(right) + "====(T)" + round2(t) + "====");
    };


    public static final Consumer<Double> printALessThanB = (t) -> {
        double left = normalQuantile(ALPHA);
        if (t > left)
            System.out.println("=========" + round2(left) + "----(T)" + round2(t) + "----");
        if (t < left)
            System.out.println("====(T)" + round2(t) + "====" + round2(left) + "---------");
    };

    public static final Consumer<Double> printAGreaterThanB = (t) -> {

        double right = normalQuantile(1 - ALPHA);
        if (t < right)
            System.out.println("----(T)" + round2(t) + "----" + round2(right) + "=========");

        if (t > right)
            System.out.println("---------" + round2(right) + "====(T)" + round2(t) + "====");
    };


}
