package com.workday;

interface RangeContainerFactory {
    /**
     * builds an immutable container optimized for range queries.
     * Data is expected to be 32k items or less.
     * The position in the “data” array represents the “id” for that instance
     * in question. For the “PayrollResult” example before, the “id” might be
     * the worker’s employee number, the data value is the corresponding
     * net pay. E.g, data[5]=2000 means that employee #6 has net pay of 2000.
     */
    RangeContainer createContainer(long[] data);
}

/**
 *a specialized container of records optimized for efficient range queries
 * on an attribute of the data.
 */
interface RangeContainer {
    /**
     * @return the Ids of all instances found in the container that
     * have data value between fromValue and toValue with optional
     * inclusivity. The ids should be returned in ascending order when retrieved
     *  using nextId().
     */
    Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive);
}

/**
 * an iterator of Ids
 */
interface Ids { /**
 * return the next id in sequence, -1 if at end of data.
 * The ids should be in sorted order (from lower to higher) to facilitate * the query distribution into multiple containers.
    */
    static final short END_OF_IDS = -1;
    short nextId();
}