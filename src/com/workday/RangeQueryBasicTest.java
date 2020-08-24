package com.workday;

import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;

public class RangeQueryBasicTest {
    private  RangeContainer rc;
    private  RangeContainer bigRc;
    private  RangeContainer negativeRc;
    private  RangeContainer emptyRc;
    @Before
    public void setUp(){
        RangeContainerFactory rf = new RangeContainerFactoryImpl();
        rc = rf.createContainer(new long[]{10,12,17,21,2,15,16});
        bigRc = rf.createContainer(new long[]{10,12,17,21,2,15,16,1,4,5,6,2,0,9,99,38,27,45,44,52,81,79,17,20,82,24});
        negativeRc = rf.createContainer(new long[]{10,-12,17,-21,2,15,-16,1,4,-5,6,2,0,-9,99,38,27,-45,44,52,81,-79,17,20,-82,24});
        emptyRc = rf.createContainer(new long[]{});
    }

    @Test
    public void runARangeQuery(){
        Ids ids = rc.findIdsInRange(14, 17, true, true);
        assertEquals(2, ids.nextId());
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
        ids = rc.findIdsInRange(14, 17, true, false);
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
        ids = rc.findIdsInRange(20, Long.MAX_VALUE, false, true);
        assertEquals(3, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }

    @Test
    public void bigRangeQuery(){
        Ids ids = bigRc.findIdsInRange(14, 17, true, true);
    }

    @Test
    public void negativeRangeQuery(){
        Ids ids = negativeRc.findIdsInRange(14, 17, true, true);
    }

    @Test
    public void emptyRangeQuery(){
        Ids ids = emptyRc.findIdsInRange(14, 17, true, true);
    }
}
