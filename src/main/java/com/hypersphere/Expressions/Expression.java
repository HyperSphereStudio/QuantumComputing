package com.hypersphere.Expressions;

import com.hypersphere.Program.QuantumExecutor;
import com.hypersphere.Program.QuantumProgram;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.redfx.strange.Gate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Expression {
    private Expression() {}

    public static class ConstantExpression extends Expression {
        private final Object o;
        private ConstantExpression(Object o) {
            this.o = o;
        }
        @Override protected void write(Compiled c) {
            if (o instanceof Integer) {
                int i = (int) o;
                writeBits(c, new byte[] {
                        (byte)(i >>> 24),
                        (byte)(i >>> 16),
                        (byte)(i >>> 8),
                        (byte)i});
            }else if(o instanceof Byte){
                writeBits(c, new byte[]{(byte) o});
            }else if(o instanceof Short){
                short i = (short) o;
                writeBits(c, new byte[] {
                        (byte)(i >>> 8),
                        (byte)i});
            }else if (o instanceof Long) {
                long lng = (long) o;
                writeBits(c, new byte[] {
                        (byte) lng,
                        (byte) (lng >> 8),
                        (byte) (lng >> 16),
                        (byte) (lng >> 24),
                        (byte) (lng >> 32),
                        (byte) (lng >> 40),
                        (byte) (lng >> 48),
                        (byte) (lng >> 56)});
            }
        }

        private static void writeBits(Compiled c, byte[] b) {
            CodeLine line = new CodeLine();
            for (byte bytes : b) {
                for (int i = 0; i < 8; ++i) {
                    line.add(new Bit(bytes << ~i < 0));
                }
            }
            c.add(line);
        }
    }
    public static Expression constant(Object o) {
         return new ConstantExpression(o);
    }
    public static class CodeExpression extends Expression{
        protected CodeLine codeLine;
        private CodeExpression(CodeLine codeLine){
            this.codeLine = codeLine;
        }
        public void add(Op... ops){
            codeLine.ops.addAll(Arrays.asList(ops));
        }
        public void add(Bit... bits){
            for(Bit b : bits){
                codeLine.add(b);
            }
        }
        @Override protected void write(Compiled c) {
            c.add(codeLine);
        }
    }
    public static class BlockExpression extends Expression{
        private final List<Expression> expressions = new ArrayList<>();
        private BlockExpression(Expression... expressions){
            addExpressions(expressions);
        }
        public void addExpressions(Expression... expressions){
            this.expressions.addAll(Arrays.asList(expressions));
        }
        @Override
        protected void write(Compiled c) {
            for(Expression e : expressions){
                e.write(c);
            }
        }
    }
    public static BlockExpression block(Expression... expressions){
        return new BlockExpression(expressions);
    }
    public static CodeExpression codeLine(Op... ops){
        return new CodeExpression(new CodeLine(ops));
    }
    public static CodeExpression init(Bit... bits){
        return codeLine(Op.pass(bits));
    }
    public static CodeExpression add(CodeExpression expression, Op... ops){
        expression.add(ops);
        return expression;
    }
    public static CodeExpression add(CodeExpression expression, Bit... bits){
        expression.add(bits);
        return expression;
    }
    public static <T> QuantumProgram<T> compile(String name, int readStart, int readEnd, BitReader<T> reader, Expression e) {
        Compiled c = new Compiled();
        e.write(c);
        c.compile();
        QuantumExecutor exec = new QuantumExecutor(c.bits.size());
        for(int i = 0; i < c.bits.size(); ++i) c.bits.get(i).index = i;
        ArrayList<Op> carryGates = new ArrayList<>();
        IntArrayList used = new IntArrayList();
        ArrayList<Gate> gates = new ArrayList<>();
        for(int i = 0; i < c.lines.size() || carryGates.size() > 0; ++i){
            used.clear();
            gates.clear();
            for(int i2 = 0; i2 < carryGates.size(); ++i2){
                Op o = carryGates.get(i2);
                for(Bit b : o.bits){
                    if(used.contains(b.index)){
                        carryGates.add(o);
                        break;
                    }else{
                        used.add(b.index);
                    }
                }
                gates.add(o.getGate(o.bits));
                carryGates.remove(o);
            }
            if(i < c.lines.size()) {
                CodeLine line = c.lines.get(i);
                outer:
                for (int i2 = 0; i2 < line.ops.size(); ++i2) {
                    Op o = line.ops.get(i2);
                    for (Bit b : o.bits) {
                        if (used.contains(b.index)) {
                            carryGates.add(o);
                            break outer;
                        } else {
                            used.add(b.index);
                        }
                    }
                    gates.add(o.getGate(o.bits));
                }
            }
            exec.add(gates.toArray(new Gate[0]));
        }
        for(Bit b : c.bits){
            if(!exec.getTree().containsKey(b.index)){
                exec.add(Gate.measurement(b.index));
            }
        }
        return new QuantumProgram<>(name, readStart, readEnd, reader, exec);
    }
    public static QuantumProgram<boolean[]> compile(String name, Expression e) {
        return compile(name, -1, -1, BitReaders.PASS, e);
    }
    public static QuantumProgram<boolean[]> compile(Expression e) {
        return compile("Circ:" + Math.random(), -1, -1, BitReaders.PASS, e);
    }
    protected abstract void write(Compiled c);
}class Compiled {
    public ArrayList<CodeLine> lines = new ArrayList<>();
    public ArrayList<Bit> bits = new ArrayList<>();
    public Compiled() {}
    public CodeLine add(CodeLine line) {
        lines.add(line);
        return line;
    }
    void compile(){
        for(CodeLine line : lines){
            line.compile();
            for(Bit b : line.bits)if(!bits.contains(b))bits.add(b);
        }
    }
}
class CodeLine {
    ArrayList<Op> ops = new ArrayList<>();
    ArrayList<Bit> bits = new ArrayList<>();
    public CodeLine(Op... ops) {
        for(Op o : ops){
            add(o);
        }
    }
    public CodeLine add(Op op) {
        ops.add(op);
        return this;
    }
    public Bit add(Bit b){
        ops.add(Op.pass(b));
        return b;
    }
    void compile(){
        for(int i = 0; i < ops.size(); ++i) {
            for (int i2 = 0; i2 < ops.get(i).bits.size(); ++i2) {
                Bit b = ops.get(i).bits.get(i2);
                if (!bits.contains(b)) {
                    bits.add(b);
                    if (b.getValue()) ops.add(Op.x(b));
                }
            }
            if(ops.get(i) instanceof Pass)ops.remove(i);
        }
    }

}