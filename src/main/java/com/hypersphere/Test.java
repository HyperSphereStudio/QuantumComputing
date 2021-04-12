package com.hypersphere;

public class Test extends QuantumEnvironment {

    public static void main(String[] args) throws Exception {
        
    }

    public static boolean myCircuit(double d) throws Exception {
        QBit bit = new QBit();
        QBit bit2 = new QBit(d);
        x(bit);
        h(bit);
        y(bit);
        cx(bit, bit2);
        return measure(bit2);
    }
}
