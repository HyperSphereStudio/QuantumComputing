package com.hypersphere;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.redfx.strange.Gate;
import org.redfx.strange.Step;

import java.util.function.Function;
/**@author Johnathan Bizzano**/
public class GateOperation {

    public Function<int[], Gate> g;
    public QBit[] bits;
    public GateOperation(Function<int[], Gate> g, QBit... bits){
        this.g = g;
        this.bits = bits;
    }

    public Step getStep(Object2IntOpenHashMap<QBit> indexMap) {
        int[] a = new int[bits.length];
        for(int i = 0; i < a.length; ++i){
            QBit bit = bits[i];
            a[i] = indexMap.getInt(bit);
        }
        return new Step(g.apply(a));
    }

    public void appendUnique(Object2IntOpenHashMap<QBit> indexMap){
        for(QBit q : bits){
            if(!indexMap.containsKey(q)){
                indexMap.put(q, indexMap.size());
            }
        }
    }
    public boolean isSingle(){
        return bits.length == 1;
    }
}
