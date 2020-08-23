package com.workday;

import java.util.Arrays;

public class RangeContainerFactoryImpl implements RangeContainerFactory {

    public RangeContainerFactoryImpl() {

    }

    public RangeContainer createContainer(long[] data) {
        for (long d: data) {
            System.out.print(d+" ");
        }
        RangeContainer container = new RangeContainerImpl(data);
        return container;
    }

}
