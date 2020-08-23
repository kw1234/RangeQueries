package com.workday;

import java.util.LinkedList;
import java.util.Queue;

public class IdsImpl implements Ids {

    Queue<Integer> idList;

    public IdsImpl(Queue<Integer> que) {
        idList = que;
    }

    public short nextId() {
        if (idList.size() > 0) {
            return idList.poll().shortValue();
        }
        return Ids.END_OF_IDS;
    }
}
