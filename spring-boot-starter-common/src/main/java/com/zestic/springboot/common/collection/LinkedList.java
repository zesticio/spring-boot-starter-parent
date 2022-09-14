package com.zestic.springboot.common.collection;

import com.google.common.collect.Lists;

public class LinkedList<T> implements List<T> {

    private java.util.LinkedList<T> list = Lists.newLinkedList();

    public LinkedList() {
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public void remove(T value) {
        list.remove(value);
    }

    @Override
    public int count() {
        return list.size();
    }
}
