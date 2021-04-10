package com.hypersphere.Program;

import org.redfx.strange.Qubit;
import org.redfx.strange.Result;

public class QuantumResult {
    private boolean[] bitValues;
    private double prob = 1;
    private double[] probs;
    public QuantumResult(Result r){
        Qubit[] bits = r.getQubits();
        bitValues = new boolean[bits.length];
        probs = new double[bits.length];
        for(int i = 0; i < bits.length; ++i){
            double p0 = (1 - bits[i].getProbability());
            double p1 = (bits[i].getProbability());
            int value = bits[i].measure();
            probs[i] = p0;
            prob *= value == 1 ? p1 : p0;
            bitValues[i] = value == 1;
        }
    }

    public QuantumResult(){
        bitValues = new boolean[0];
        probs = new double[0];
    }

    public void print(){
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < bitValues.length; ++i){
            if(sb.length() != 1)sb.append(",");
            System.out.println("Qbit #" + i + "\tP0:" + probs[i] + "\tP1:" + (1 - probs[i]) + "\tValue:" + (bitValues[i] ? "1" : "0"));
            sb.append(bitValues[i] ? "1" : "0");
        }
        System.out.println("State:" + sb + "]\tP:" + prob);
    }

    public boolean[] getBits(){
        return bitValues;
    }
}