package com.workday;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IdsImpl implements Ids {

    Queue<Integer> idList;
    List<List<Integer>> indices;

    public IdsImpl(Queue<Integer> que, List<List<Integer>> indices) {
        idList = que;
        this.indices = indices;
        for (List<Integer> indexLst: indices) {
            for (int i: indexLst){
                System.out.print(i+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public short nextId() {
        if (idList.size() > 0) {
            return idList.poll().shortValue();
        }
        return Ids.END_OF_IDS;
    }
}
