package com.hypersphere.Expressions;
import org.redfx.strange.Gate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public abstract class Op {
    public final ArrayList<Bit> bits = new ArrayList<>();
    public Op(Bit... bits){
        this.bits.addAll(Arrays.asList(bits));
    }
    public abstract Gate getGate(int... indexes);

    public Gate getGate(List<Bit> bits){
        int[] index = new int[bits.size()];
        for(int i = 0; i < index.length; ++i){
            index[i] = bits.get(i).index;
        }
        return getGate(index);
    }

    public static Op pass(Bit... bits){return new Pass(bits);}
    public static Op[] ops(Op... ops){
        return ops;
    }
    public static Op h(Bit b1){ return op((i) -> Gate.hadamard(i[0]), b1);}
    public static Op z(Bit b1){
        return op((i) -> Gate.z(i[0]), b1);
    }
    public static Op y(Bit b1){
        return op((i) -> Gate.y(i[0]), b1);
    }
    public static Op x(Bit b1){
        return op((i) -> Gate.x(i[0]), b1);
    }
    public static Op cz(Bit b1, Bit b2){
        return op((i) -> Gate.cz(i[0], i[1]), b1, b2);
    }
    public static Op cnot(Bit b1, Bit b2){
        return op((i) -> Gate.cnot(i[0], i[1]), b1, b2);
    }
    public static Op measure(Bit b1){
        return op((i) -> Gate.measurement(i[0]), b1);
    }
    public static Op toffoli(Bit b1, Bit b2, Bit b3){
        return op((i) -> Gate.toffoli(i[0], i[1], i[2]), b1, b2, b3);
    }

    private static Op op(Function<int[], Gate> function, Bit... bits){
        return new Op(bits){
            @Override
            public Gate getGate(int... indexes) {
                return function.apply(indexes);
            }
        };
    }
}class Pass extends Op {
    public Pass(Bit... bits){
        super(bits);
    }
    @Override
    public Gate getGate(int... indexes) {
        return null;
    }
}