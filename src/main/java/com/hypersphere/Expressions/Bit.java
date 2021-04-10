package com.hypersphere.Expressions;

public class Bit {
    public int index;
    private final boolean value;
    public Bit(boolean value){
        this.value = value;
    }

    public Bit(){
        this(false);
    }

    public boolean getValue(){
        return value;
    }
}
