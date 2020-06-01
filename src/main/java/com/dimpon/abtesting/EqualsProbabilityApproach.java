package com.dimpon.abtesting;


import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.dimpon.abtesting.Utils.*;

@Builder
public class EqualsProbabilityApproach implements Approach {


    @AllArgsConstructor
    enum TypeOfAlternativeHypothesis {
        AInequalB("A <> B", isInTwoSidesInterval, POfAInequalB, printAInequalB, 1),
        ALessThanB("A < B", isGreater, POfALessThanB, printALessThanB, 2),
        AGreaterThanB("A > B", isSmaller, POfAGreaterThanB, printAGreaterThanB, 3);
        private String stringRepresentation;
        private Predicate<Double> checkInterval;
        private Function<Double, Double> getP;
        private Consumer<Double> printAxis;
        private int index;

        public static TypeOfAlternativeHypothesis of(int index) {
            for (TypeOfAlternativeHypothesis t : TypeOfAlternativeHypothesis.values()) {
                if (t.index == index) return t;
            }
            throw new IllegalArgumentException("index:" + index);
        }
    }


    private final int aSuccess;
    private final int bSuccess;

    private final int aFailures;
    private final int bFailures;

    private final TypeOfAlternativeHypothesis alternativeHypothesis;

    private int $total;
    private int $aTotal;
    private int $bTotal;

    private int $successTotal;
    private int $failuresTotal;

    private double $statCriteria;

    void fillTable() {
        $successTotal = aSuccess + bSuccess;
        $failuresTotal = aFailures + bFailures;
        $aTotal = aSuccess + aFailures;
        $bTotal = bSuccess + bFailures;
        $total = aSuccess + bSuccess + aFailures + bFailures;
    }

    void calculateStatCriteria() {
        $statCriteria = ((double) aSuccess / (double) $aTotal - (double) bSuccess / (double) $bTotal) /
                Math.sqrt(((double) $successTotal / (double) $total) * (1 - (double) $successTotal / (double) $total) * (1 / (double) $aTotal + 1 / (double) $bTotal));
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
    }
}
