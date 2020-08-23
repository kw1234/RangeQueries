package com.workday;

import java.util.Queue;
import java.util.LinkedList;

public class RangeContainerImpl implements RangeContainer {

    long[] data;

    public RangeContainerImpl(long[] data) {
        this.data = data;
    }

    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {

        Queue<Integer> que = new LinkedList<>();
        for (int i = 0; i < data.length; i++) {
            long elem = data[i];
            addElemToQueue(elem, i, fromValue, toValue, fromInclusive, toInclusive, que);
        }
        Ids result = new IdsImpl(que);
        return result;
    }

    private void addElemToQueue(long val, int index, long lowerBound, long upperBound, boolean fromInclusive, boolean toInclusive, Queue<Integer> que) {
        if (val >= lowerBound && val <= upperBound) {
            if (!fromInclusive && val == lowerBound) return;
            if (!toInclusive && val == upperBound) return;
            que.add(index);
        }
    }

}
