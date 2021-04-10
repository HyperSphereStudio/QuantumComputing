package com.hypersphere;

import com.hypersphere.Expressions.Bit;
import com.hypersphere.Expressions.Expression;
import com.hypersphere.Program.QuantumProgram;


public class Test {

    public static void main(String[] args){
        Bit b = new Bit(true);
        Bit b2 = new Bit(true);
        QuantumProgram<boolean[]> p = Expression.compile(Expression.init(b2));
        p.printInfo();
        p.run(1).print();
    }


}
