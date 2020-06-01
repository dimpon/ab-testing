package com.dimpon.abtesting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dimpon.abtesting.Utils.*;

@Builder
public class MannWhitneyApproach implements Approach {

    private AlternativeHypothesis alternativeHypothesis;

    private List<Double> aSample;

    private List<Double> bSample;

    private List<ElementWithRange> $ranged;

    private double $statCriteria;

    @AllArgsConstructor
    enum Sample {
        A('+'), B('o');
        char symb;
    }

    @AllArgsConstructor
    enum AlternativeHypothesis {
        TetaGreaterThenNull("Teta > 0", isSmaller, POfAGreaterThanB, printAGreaterThanB, 1),
        TetaLessThenNull("Teta < 0", isGreater, POfALessThanB, printALessThanB, 2),
        TetaNotNull("Teta <> 0", isInTwoSidesInterval, POfAInequalB, printAInequalB, 3);
        private String stringRepresentation;
        private Predicate<Double> checkInterval;
        private Function<Double, Double> getP;
        private Consumer<Double> printAxis;
        private int index;

        public static AlternativeHypothesis of(int index) {
            for (AlternativeHypothesis t : AlternativeHypothesis.values()) {
                if (t.index == index) return t;
            }
            throw new IllegalArgumentException("index:" + index);
        }
    }

    void range() {
        AtomicInteger index = new AtomicInteger(1);
        List<ElementWithRange> collect = Stream.concat(
                aSample.stream().map(d -> new ElementWithRange(Sample.A, d)),
                bSample.stream().map(d -> new ElementWithRange(Sample.B, d)))
                .sorted(Comparator.comparingDouble(o -> o.value))
                .peek(e -> e.setRange(index.getAndIncrement()))
                .collect(Collectors.toList());

        List<ElementWithRange> buffer = new ArrayList<>();

        for (int i = 0; i < collect.size() - 1; i++) {
            ElementWithRange elementWithRange = collect.get(i);
            if (buffer.size() == 0) {
                buffer.add(elementWithRange);
            }
            ElementWithRange elementWithRangeNext = collect.get(i + 1);
            if (elementWithRange.value == elementWithRangeNext.value) {
                buffer.add(elementWithRangeNext);
            } else {
                setAverage(buffer);
            }

            if (i == collect.size() - 2) {
                setAverage(buffer);
            }
        }

        $ranged = collect;
    }

    void calculateStatCriteria() {
        double W = $ranged.stream().filter(e -> e.type == Sample.A).mapToDouble(v -> v.range).sum();
        int sum = aSample.size() + bSample.size() + 1;
        double MW = (double) aSample.size() * sum / 2;
        double DW = (double) aSample.size() * bSample.size() * sum / 12;
        double W1 = (W - MW) / Math.sqrt(DW);
        $statCriteria = W1;
    }


    private void setAverage(List<ElementWithRange> buffer) {
        if (buffer.size() > 1) {

            final double average = buffer.stream().mapToDouble(value -> value.range).average().getAsDouble();

            for (ElementWithRange elementWithRange : buffer) {
                elementWithRange.setRange(average);
            }
        }
        buffer.clear();
    }

    @Override
    public boolean checkInterval() {
        return alternativeHypothesis.checkInterval.test($statCriteria);
    }

    @Override
    public double getP() {
        return alternativeHypothesis.getP.apply($statCriteria);
    }

    @Override
    public double getStatCriteria() {
        return $statCriteria;
    }

    @Override
    public void pritAxis() {
        alternativeHypothesis.printAxis.accept($statCriteria);
        pritDistribution();
    }

    public void pritDistribution() {
        System.out.println("Distribution:");
        for (ElementWithRange e : $ranged) {
            System.out.print(e.type.symb);
        }
    }

    @Data
    static class ElementWithRange {
        private final Sample type;
        private double range;
        private final double value;
    }

}
