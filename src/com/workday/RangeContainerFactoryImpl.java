package com.workday;

import java.util.Arrays;

public class RangeContainerFactoryImpl implements RangeContainerFactory {

    public RangeContainerFactoryImpl() {}

    public RangeContainer createContainer(long[] data) {
        RangeContainer container = new RangeContainerImpl(data);
        return container;
    }

}
