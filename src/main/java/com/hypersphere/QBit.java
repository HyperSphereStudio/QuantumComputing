package com.hypersphere;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

import java.util.ArrayList;
import java.util.List;

public class QBit {
    double alpha;
    List<GateOperation> gates = new ArrayList<>();
    boolean measured, measuredValue, explicitMeasurement;
    double measuredProb;
    public QBit(double alpha){
        this.alpha = alpha;
    }
    public QBit(){
        this(1);
    }
    void mark(GateOperation operation){
        gates.add(operation);
    }
    public void print() {
        System.out.println("Value:" + QuantumEnvironment.measure(this) + " P0:" + QuantumEnvironment.peekProb0(this) + " P1:" + QuantumEnvironment.peekProb1(this));
    }
    public boolean measure() {
        return QuantumEnvironment.measure(this);
    }
    Object2IntOpenHashMap<QBit> createIndexMap(){
        Object2IntOpenHashMap<QBit> indexes = new Object2IntOpenHashMap<>();
        indexes.put(this, 0);
        for(GateOperation op : gates){
            op.appendUnique(indexes);
        }
        return indexes;
    }
    public QBit create(){
        return measured ? new QBit(alpha) : new QBit(measuredValue ? 0 : 1);
    }
    boolean isSingle(){
        for(GateOperation op : gates){
            if(!op.isSingle())return false;
        }
        return true;
    }


}
