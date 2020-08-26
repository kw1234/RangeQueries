package com.workday;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class RangeContainerFactoryImpl implements RangeContainerFactory {

    public RangeContainerFactoryImpl() {}

    public RangeContainer createContainer(long[] data) {
        RangeContainer container = new RangeContainerImpl(data);
        return container;
    }



}