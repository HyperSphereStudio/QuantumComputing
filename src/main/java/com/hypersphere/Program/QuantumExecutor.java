package com.hypersphere.Program;

import com.hypersphere.Utils.QBit;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.redfx.strange.*;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;

public class QuantumExecutor {
    final Program p;
    private Int2ObjectOpenHashMap<QBit> qbitMap = new Int2ObjectOpenHashMap<>();
    private Object2IntOpenHashMap<QBit> qbitMap2 = new Object2IntOpenHashMap<>();
    public QuantumExecutor(int nQubits) {
        p = new Program(nQubits);
    }

    public QuantumExecutor add(Gate... gate){
        p.addStep(new Step(gate));
        for(Gate g : gate){
            for(int i : g.getAffectedQubitIndexes()){
                checkBit(i);
                qbitMap.get(i).getGates().add(g);
            }
        }
        return this;
    }

    public QuantumExecutor add(QuantumExecutor... quantumExecutors){
        for(QuantumExecutor p : quantumExecutors){
            for(Step s : p.p.getSteps()){
                this.p.addStep(s);
            }
        }
        return this;
    }

    public QuantumExecutor add(int[] matchIndexes, QuantumExecutor... quantumExecutors){
        for(QuantumExecutor p : quantumExecutors){
            for(Step s : p.p.getSteps()){
                for(Gate g : s.getGates()){
                    for(int i = 0; i < g.getAffectedQubitIndexes().size(); ++i){
                        int v = matchIndexes[g.getAffectedQubitIndexes().get(i)];
                        g.getAffectedQubitIndexes().set(i, v);
                        checkBit(v);
                    }
                    int v = matchIndexes[g.getMainQubitIndex()];
                    checkBit(v);
                    g.setMainQubitIndex(v);
                }
                this.p.addStep(s);
            }
        }
        return this;
    }

    private void checkBit(int i){
        if(!qbitMap.containsKey(i)){
            QBit q = new QBit(i);
            qbitMap.put(i, q);
            qbitMap2.put(q, i);
        }
    }

    public QBit getQBit(int index){
        return qbitMap.get(index);
    }

    public QuantumResult execute(boolean debug, boolean print){
        QuantumResult r;
        if(getBitCount() != 0){
            r = new QuantumResult(new SimpleQuantumExecutionEnvironment().runProgram(p));
        }else{
            r = new QuantumResult();
        }
        if(print)r.print();
        return r;
    }

    public Int2ObjectOpenHashMap<QBit> getTree(){
        return qbitMap;
    }

    public int getBitCount(){
        return qbitMap.size();
    }

    public int lineCount(){
        return p.getSteps().size();
    }
}
