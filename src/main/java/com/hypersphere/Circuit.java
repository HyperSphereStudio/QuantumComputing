package com.hypersphere;

import java.util.List;

public class Circuit {
    List<GateOperation> gates;
    String name;
    public Circuit(String name, List<GateOperation> gates){
        this.gates = gates;
        this.name = name;
    }

    public boolean measure(QBit bits) {
        QuantumEnvironment.apply(this, bits);
        return QuantumEnvironment.measure(bits);
    }

    public boolean[] measure(QBit... bits) {
        QuantumEnvironment.apply(this, bits);
        return QuantumEnvironment.measure(bits);
    }

    
    public String toString(){
        return "Quantum Circuit:" + name;
    }
    
}