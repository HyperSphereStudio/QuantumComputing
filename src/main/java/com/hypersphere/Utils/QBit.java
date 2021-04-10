package com.hypersphere.Utils;

import org.redfx.strange.Gate;

import java.util.ArrayList;

public class QBit {
    private String name;
    private final int index;
    private final ArrayList<Gate> gates = new ArrayList<>();

    public QBit(int index, String name){
        this.index = index;
        this.name = name;
    }

    public QBit(int index){
        this.index = index;
    }

    public ArrayList<Gate> getGates(){
        return gates;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int index(){
        return index();
    }
}
