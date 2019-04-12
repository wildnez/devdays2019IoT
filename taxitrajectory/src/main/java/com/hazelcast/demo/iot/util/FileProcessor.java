package com.hazelcast.demo.iot.util;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.demo.iot.data.Location;
import com.hazelcast.demo.iot.data.TaxiData;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class FileProcessor {

    private static String MAP_IDENTIFIER = "devdaysIOTmap";

    public static void main(String[] args) {
        JetInstance jet = Jet.newJetClient();
        HazelcastInstance hazelcastInstance = jet.getHazelcastInstance();

        IMap map = hazelcastInstance.getMap(MAP_IDENTIFIER);

        Pattern pattern = Pattern.compile(",");
        Executor service = Executors.newFixedThreadPool(10);
        for (int i = 1; i < 11; i++) {
            int prefix = i;
            service.execute(() -> {
                try {
                    File file = new File(FileProcessor.class.getClassLoader().getResource(prefix + ".txt").getFile());
                    Stream lineStream = Files.lines(file.toPath());
                    lineStream.forEach(line -> {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String[] tokenized = pattern.split(line.toString());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                        Timestamp timestamp = null;
                        try {
                            timestamp = new Timestamp(dateFormat.parse(tokenized[1]).getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Location location = new Location(Double.valueOf(tokenized[2]), Double.valueOf(tokenized[3]));
                        String ID = tokenized[0];
                        map.set(ID, new TaxiData(ID, timestamp, location));
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}




