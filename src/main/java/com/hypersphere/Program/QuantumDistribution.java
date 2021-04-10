package com.hypersphere.Program;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;

import java.util.ArrayList;
import java.util.Arrays;

public class QuantumDistribution {

    private Object2IntOpenCustomHashMap<boolean[]> map = new Object2IntOpenCustomHashMap<>(new Hash.Strategy<>() {
        @Override public int hashCode(boolean[] o) { return Arrays.hashCode(o); }
        @Override public boolean equals(boolean[] a, boolean[] b) { return Arrays.equals(a, b); }
    });
    private ArrayList<QuantumResult> results = new ArrayList<>();

    QuantumDistribution(){

    }

    public boolean[] maxProb(){
        int max = -Integer.MAX_VALUE;
        boolean[] maxArray = null;
        for(Object2IntMap.Entry<boolean[]> i : map.object2IntEntrySet()) {
            if(i.getIntValue() > max){
                max = i.getIntValue();
                maxArray = i.getKey();
            }
        }
        return maxArray;
    }

    public Object2IntOpenCustomHashMap<boolean[]> getDistribution(){
        return map;
    }

    public ArrayList<QuantumResult> getResults(){
        return results;
    }


    public void print(){
        int count = 0;
        for(QuantumResult r : results){
            System.out.println("========(SHOT:" + count++ + ")========");
            r.print();
            System.out.println("========================");
        }
    }
}
