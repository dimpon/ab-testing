package com.dimpon.abtesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.dimpon.abtesting.EqualsProbabilityApproach.TypeOfAlternativeHypothesis.AGreaterThanB;
import static com.dimpon.abtesting.MannWhitneyApproach.AlternativeHypothesis.TetaLessThenNull;

public class SolutionsTest {
    @Test
    void test_equalsProbability() {

        EqualsProbabilityApproach approach = EqualsProbabilityApproach.builder()
                .aSuccess(46)
                .aFailures(1054)
                .bSuccess(30)
                .bFailures(970)
                .alternativeHypothesis(AGreaterThanB)
                .build();

        approach.fillTable();
        approach.calculateStatCriteria();


        double statCriteria = approach.getStatCriteria();
        boolean isInInterval = approach.checkInterval();
        double p = approach.getP();

        Assertions.assertEquals(1.45, Utils.round2(statCriteria));
        Assertions.assertTrue(isInInterval);
        Assertions.assertEquals(0.0738, Utils.round4(p));

        approach.pritAxis();
    }

    @Test
    void test_mannWhitney() {

        List<Double> b = Arrays.asList(30.0, 28.0, 46.0, 42.0, 35.0, 33.0, 44.0, 43.0, 31.0, 38.0);

        List<Double> a = Arrays.asList(26.0, 37.0, 39.0, 28.0, 31.0, 27.0, 32.0, 35.0);

        MannWhitneyApproach approach = MannWhitneyApproach.builder()
                .aSample(a)
                .bSample(b)
                .alternativeHypothesis(TetaLessThenNull)
                .build();


        approach.range();
        approach.calculateStatCriteria();

        double statCriteria = approach.getStatCriteria();
        boolean isInInterval = approach.checkInterval();
        double p = approach.getP();

        Assertions.assertEquals(-1.64, Utils.round2(statCriteria));
        Assertions.assertTrue(isInInterval);
        Assertions.assertEquals(0.0501, Utils.round4(p));

        approach.pritAxis();
        approach.pritDistribution();

    }
}
