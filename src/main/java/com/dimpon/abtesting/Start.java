package com.dimpon.abtesting;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.dimpon.abtesting.MannWhitneyApproach.AlternativeHypothesis.TetaLessThenNull;


public class Start {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("EqualsProbabilityApproach[1] MannWhitneyApproach[2]: ");

        String approach = scanner.next();

        Approach calculate;

        if (approach.endsWith("1")) {
            calculate = equalsProbabilityApproach(scanner);
        } else if (approach.endsWith("2")) {
            calculate = mannWhitneyApproach(scanner);
        } else {
            throw new IllegalArgumentException();
        }

        double statCriteria = calculate.getStatCriteria();
        boolean isInInterval = calculate.checkInterval();
        double p = calculate.getP();
        System.out.println();
        System.out.println("T = " + statCriteria);
        System.out.println("Main Hypothesis is accepted? :" + isInInterval);
        System.out.println("P = " + p);
        System.out.println();
        calculate.pritAxis();


    }

    private static Approach equalsProbabilityApproach(Scanner scanner) {

        System.out.print("Number A Success: ");
        int aSuccess = Integer.valueOf(scanner.next());

        System.out.print("Number A Failures: ");
        int aFailures = Integer.valueOf(scanner.next());

        System.out.print("Number B Success: ");
        int bSuccess = Integer.valueOf(scanner.next());

        System.out.print("Number B Failures: ");
        int bFailures = Integer.valueOf(scanner.next());

        System.out.print("Type of Alternative Hypothesis A<>B [1] A<B [2] A>B [3]: ");
        EqualsProbabilityApproach.TypeOfAlternativeHypothesis typeOfAlternativeHypothesis = EqualsProbabilityApproach.TypeOfAlternativeHypothesis.of(Integer.parseInt(scanner.next()));

        EqualsProbabilityApproach calculate = EqualsProbabilityApproach.builder()
                .aSuccess(aSuccess)
                .aFailures(aFailures)
                .bSuccess(bSuccess)
                .bFailures(bFailures)
                .alternativeHypothesis(typeOfAlternativeHypothesis)
                .build();

        calculate.fillTable();
        calculate.calculateStatCriteria();
        return calculate;
    }

    private static Approach mannWhitneyApproach(Scanner scanner) {

        System.out.print("Enter selection A: ");
        List<Double> aSelection = Arrays.stream(scanner.next().split(","))
                .map(Double::parseDouble).collect(Collectors.toList());

        System.out.print("Enter selection B: ");
        List<Double> bSelection = Arrays.stream(scanner.next().split(","))
                .map(Double::parseDouble).collect(Collectors.toList());


        System.out.print("Type of Alternative Hypothesis Teta>0 [1] Teta<0 [2] Teta<>0 [3]: ");
        MannWhitneyApproach.AlternativeHypothesis typeOfAlternativeHypothesis = MannWhitneyApproach.AlternativeHypothesis.of(Integer.parseInt(scanner.next()));


        MannWhitneyApproach calculate = MannWhitneyApproach.builder()
                .aSample(aSelection)
                .bSample(bSelection)
                .alternativeHypothesis(typeOfAlternativeHypothesis)
                .build();


        calculate.range();
        calculate.calculateStatCriteria();
        return calculate;
    }
}
