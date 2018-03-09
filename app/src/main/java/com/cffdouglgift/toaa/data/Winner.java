package com.cffdouglgift.toaa.data;


public class Winner extends BasePerson {

    public Winner() {
    }


    @Override
    public long calculateHash() {
        return name.hashCode();
    }
}
