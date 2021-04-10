package com.hypersphere.Program;

import com.hypersphere.Expressions.BitReader;

import java.util.Arrays;

public class QuantumProgram<T> {
    protected final QuantumExecutor executor;
    protected final BitReader<T> reader;
    public int readStart, readEnd;
    public String name;
    public QuantumProgram(String name, int readStart, int readEnd, BitReader<T> reader, QuantumExecutor executor){
        this.executor = executor;
        this.reader = reader;
        this.name = name;
        this.readStart = readStart;
        this.readEnd = readEnd;
    }

    public T run(boolean debug, boolean print){
        if(readStart != -1 && readEnd != -1)
        return reader.write(Arrays.copyOfRange(executor.execute(debug, print).getBits(), readStart, readEnd));
        else return reader.write(executor.execute(debug, print).getBits());
    }

    public QuantumDistribution run(int shots){
        QuantumDistribution distribution = new QuantumDistribution();
        QuantumResult result;
        for(int i = 0; i < shots; ++i){
            result = executor.execute(false, false);
            if(distribution.getDistribution().containsKey(result.getBits())){
                distribution.getDistribution().put(result.getBits(), distribution.getDistribution().getInt(result.getBits()) + 1);
            }else{
                distribution.getDistribution().put(result.getBits(), 0);
            }
            distribution.getResults().add(result);
        }
        return distribution;
    }

    public int lineCount(){
        return executor.lineCount();
    }

    public void printInfo(){
        System.out.println();
        System.out.println("CIRCUIT " + name + " INFO");
        System.out.println("Line Count:" + lineCount() + "\tBitCount:" + executor.getBitCount());
    }
}
