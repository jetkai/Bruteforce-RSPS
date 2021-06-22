package aniski.flags.impl;

import aniski.flags.Flag;
import aniski.rs.Store;

public class Crack implements Flag {
    @Override
    public void init(Object[] args) {
        System.out.println("Wrong thread Kai.");

    }

    public Store getStore() {
        return null;
    }
}
