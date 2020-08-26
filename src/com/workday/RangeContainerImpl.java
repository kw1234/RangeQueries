package com.workday;

import java.util.Queue;
import java.util.LinkedList;

import java.io.*;
import java.util.*;

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
        List<List<Integer>> indices = new ArrayList();
        List<Integer> lst = Collections.synchronizedList(new ArrayList());

        for (int i = 0; i < partitions.size(); i++) {
            List<long[]> partition = partitions.get(i);
            Runnable r1 = new Runnable() {
                public void run() {
                    //List<Integer> lst = new ArrayList();
                    addElemToQueue(partition, fromValue, toValue, fromInclusive, toInclusive, lst);
                    /*for (int n: lst) {
                        que.add(n);
                    }
                    indices.add(lst);*/
                }
            };
            Thread t1 = new Thread(r1, "Thread t"+i);
            t1.start();
        }

        Collections.sort(lst);
        for (int n: lst) {
            que.add(n);
        }

        Ids result = new IdsImpl(que, indices);
        return result;
    }

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




