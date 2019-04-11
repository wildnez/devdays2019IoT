package com.hazelcast.demo.iot;

import com.hazelcast.demo.iot.aggregate.DerivativeAggregation;
import com.hazelcast.demo.iot.data.EarthTools;
import com.hazelcast.demo.iot.data.TaxiData;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Job;
import com.hazelcast.jet.aggregate.AggregateOperation1;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.function.BiFunctionEx;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;

import java.util.Map;

import static com.hazelcast.jet.pipeline.JournalInitialPosition.START_FROM_OLDEST;


public class TaxiSource {

    private static final String MAP_NAME = "taxi";

    public static void main(String[] args) {
        System.setProperty("hazelcast.logging.type", "log4j");
        JetConfig jetConfig = getJetConfig();
        JetInstance jet = Jet.newJetInstance(jetConfig);
        Pipeline p = buildPipeline();

        try {
            Job job = jet.newJob(p);

            job.join();
        }
        finally {
            Jet.shutdownAll();
        }
    }

    private static Pipeline buildPipeline() {
        Pipeline p = Pipeline.create();
        long lagTime = 0L; //12 * 60 * 1000L;
        p.drawFrom(Sources.<Integer, TaxiData>mapJournal(MAP_NAME, START_FROM_OLDEST))
            .withNativeTimestamps(lagTime)
            .groupingKey(Map.Entry::getKey)
            .rollingAggregate(
                    DerivativeAggregation.derivative(
                            (taxiData1, taxiData2) -> EarthTools.distanceBetween(taxiData1.getValue().getLocation(), taxiData2.getValue().getLocation())))
                .drainTo(Sinks.logger());




//

        return p;
    }

    private static JetConfig getJetConfig() {
        JetConfig cfg = new JetConfig();
        // Add an event journal config for map which has custom capacity of 1000 (default 10_000)
        // and time to live seconds as 10 seconds (default 0 which means infinite)
        cfg.getHazelcastConfig()
                .getMapEventJournalConfig(MAP_NAME)
                .setEnabled(true)
                .setCapacity(1000)
                .setTimeToLiveSeconds(10);
        return cfg;
    }
}
