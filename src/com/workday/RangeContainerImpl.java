package com.workday;

import java.util.Queue;
import java.util.LinkedList;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RangeContainerImpl implements RangeContainer {

    long[] data;
    List<List<long[]>> partitions;
    int numWorkers;

    public RangeContainerImpl(long[] data) {
        numWorkers = 5;
        this.data = data;
        partitions = partitionData(data, numWorkers);
    }

    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {

        Queue<Integer> que = new LinkedList<>();
        List<Integer> lst = Collections.synchronizedList(new ArrayList());

        // needed to use this to await for all threads that were spun up to then terminate.
        // Once all threads are terminated, then it is known the full dataset has been passed through
        ExecutorService es = Executors.newCachedThreadPool();
        boolean finished = false;
        for(int i=0;i<partitions.size();i++) {
            List<long[]> partition = partitions.get(i);
            es.execute(new Runnable() {
                public void run() {
                    addElemToQueue(partition, fromValue, toValue, fromInclusive, toInclusive, lst);
                }

            });
        }

        es.shutdown();
        try {
            finished = es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            System.out.println(e);
        }

        // sort the indices and add them to the queue
        if (finished) {
            Collections.sort(lst);
            for (int n: lst) {
                que.add(n);
            }
        }


        Ids result = new IdsImpl(que);
        return result;
    }

    /**
     *
     * @param data
     * @param numWorkers
     * @return data evenly distributed through partitions, though the original order of indices is not preserved
     */
    private List<List<long[]>> partitionData(long[] data, int numWorkers) {
        List<List<long[]>> partitions = new ArrayList<>();
        for (int i = 0; i < numWorkers; i++) {
            partitions.add(new ArrayList<>());
        }

        int partitionIndex = 0;
        for (int i = 0; i < data.length; i++) {
            if (partitionIndex == numWorkers) {
                partitionIndex = 0;
            }
            List<long[]> currPartition = partitions.get(partitionIndex);
            currPartition.add(new long[]{data[i], i});
            partitionIndex++;
        }

        return partitions;
    }

    /**
     *
     * Adds all the elements that meet the range criteria to the list being updated
     */
    private void addElemToQueue(List<long[]> partition, long lowerBound, long upperBound, boolean fromInclusive, boolean toInclusive, List<Integer> lst) {
        for (int i = 0; i < partition.size(); i++) {
            long[] pair = partition.get(i);
            long val = pair[0];
            long index = pair[1];
            if (val >= lowerBound && val <= upperBound) {
                if (!fromInclusive && val == lowerBound) return;
                if (!toInclusive && val == upperBound) return;
                lst.add((int) index);
            }
        }
    }
}




