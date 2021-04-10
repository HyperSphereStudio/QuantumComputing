package com.hypersphere.Utils;

import org.redfx.strange.Gate;

import java.util.function.Function;

public class Gates {

    public static Gate h(int index){
       return Gate.hadamard(index);
    }

    public static Gate h(QBit bit){
        return h(bit.index());
    }

    public static Gate[] h(int... indexes){
        return forEach(Gates::h, indexes);
    }

    public static Gate[] h(QBit... bits){
        return forEach(Gates::h, bits);
    }



    public static Gate x(int index){
        return Gate.x(index);
    }

    public static Gate x(QBit bit){
        return x(bit.index());
    }

    public static Gate[] x(QBit... bits){
        return forEach(Gates::x, bits);
    }

    public static Gate[] x(int... indexes){
        return forEach(Gates::x, indexes);
    }



    public static Gate z(int index){
        return Gate.z(index);
    }

    public static Gate z(QBit bit){
        return z(bit.index());
    }

    public static Gate[] z(int... indexes){
        return forEach(Gates::z, indexes);
    }

    public static Gate[] z(QBit... bits){
        return forEach(Gates::z, bits);
    }


    public static Gate y(int index){
        return Gate.y(index);
    }

    public static Gate y(QBit bit){
        return y(bit.index());
    }

    public static Gate[] y(int... indexes){
        return forEach(Gates::y, indexes);
    }

    public static Gate[] y(QBit... bits){
        return forEach(Gates::y, bits);
    }



    public static Gate cz(int index1, int index2){
        return Gate.cz(index1, index2);
    }

    public static Gate cz(QBit bit, QBit bit2){
        return cz(bit.index(), bit2.index());
    }




    public static Gate measure(int index){
        return Gate.measurement(index);
    }

    public static Gate measure(QBit bit){
        return measure(bit.index());
    }

    public static Gate[] measure(QBit... bits){
        return forEach(Gates::measure, bits);
    }

    public static Gate[] measure(int... indexes){
        return forEach(Gates::measure, indexes);
    }


    private static Gate[] forEach(Function<Integer, Gate> function, int... index){
        Gate[] gates = new Gate[index.length];
        for(int i = 0; i < index.length; ++i)gates[i] = function.apply(index[i]);
        return gates;
    }

    private static Gate[] forEach(Function<Integer, Gate> function, QBit... index){
        Gate[] gates = new Gate[index.length];
        for(int i = 0; i < index.length; ++i)gates[i] = function.apply(index[i].index());
        return gates;
    }
}
