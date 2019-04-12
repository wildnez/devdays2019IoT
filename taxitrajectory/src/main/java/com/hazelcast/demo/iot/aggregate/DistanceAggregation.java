package com.hazelcast.demo.iot.aggregate;

import com.hazelcast.demo.iot.data.TaxiData;
import com.hazelcast.jet.aggregate.AggregateOperation;
import com.hazelcast.jet.aggregate.AggregateOperation1;
import com.hazelcast.jet.function.BiFunctionEx;

import java.io.Serializable;


public class DistanceAggregation {

    /**
     * Aggregate operation to calculate a derivative in rolling aggregation.
     * The
     */
    public static AggregateOperation1<TaxiData, DerivativeAccumulator, Double> derivative(

            BiFunctionEx<TaxiData, TaxiData, Double> differenceFn
    ) {
        return AggregateOperation.withCreate(DerivativeAccumulator::new)
                .<TaxiData>andAccumulate((acc, item) -> {
                    if (acc.lastItem == null) {
                        acc.lastItem = item;
                    }

                    acc.lastDifference = differenceFn.apply(item, acc.lastItem);
                    acc.totalDifference = acc.totalDifference.doubleValue() + acc.lastDifference.doubleValue();

                    TaxiData lastTaxiData = acc.lastItem;
                    TaxiData thisTaxiData = item;

                    long deltaMsec = Math.abs(thisTaxiData.getTimestamp( ).getTime( ) -
                            lastTaxiData.getTimestamp( ).getTime( ));

                    double deltaHours = ((double) deltaMsec) / (1000 * 60 * 60);

                    acc.speed = ((Double) acc.lastDifference) / deltaHours;


                    acc.lastItem = item;
                })
                .andExportFinish(acc -> acc.speed);
    }

    private static final class DerivativeAccumulator implements Serializable {
        TaxiData lastItem;
        Double lastDifference;
        Double totalDifference = 0.0;
        Double speed = 0.0;
    }
}