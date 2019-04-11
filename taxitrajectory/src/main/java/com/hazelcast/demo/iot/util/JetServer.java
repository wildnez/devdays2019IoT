package com.hazelcast.demo.iot.util;

import com.hazelcast.config.Config;
import com.hazelcast.config.EventJournalConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.config.JetConfig;

public class JetServer {
    private static String MAP_IDENTIFIER = "devdaysIOTmap";

    public static void main(String[] args) {
        Config config = new Config();

        config.getGroupConfig().setName("jet");
        config.setNetworkConfig(buildNetworkConfig());
        config.addMapConfig(buildMapConfig());
        config.addEventJournalConfig(buildEventJournalConfig());

        JetConfig jetConfig = new JetConfig();
        jetConfig.getMetricsConfig().setMetricsForDataStructuresEnabled(true);
        jetConfig.setHazelcastConfig(config);

        Jet.newJetInstance(jetConfig);
    }

    private static NetworkConfig buildNetworkConfig() {
        NetworkConfig config = new NetworkConfig();
        config.getJoin().getMulticastConfig().setEnabled(false);
        config.getJoin().getTcpIpConfig().setEnabled(true);
        config.getJoin().getTcpIpConfig().addMember("127.0.0.1");
        return config;
    }

    private static MapConfig buildMapConfig() {
        MapConfig mapConfig = new MapConfig(MAP_IDENTIFIER);
        mapConfig.setAsyncBackupCount(1);
        return mapConfig;
    }

    private static EventJournalConfig buildEventJournalConfig() {
        EventJournalConfig eventJournalConfig = new EventJournalConfig()
                .setMapName(MAP_IDENTIFIER)
                .setEnabled(true)
                .setCapacity(20000);
        return eventJournalConfig;
    }
}
