package com.hypersphere;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.redfx.strange.local.QuantumExecutor;
import org.redfx.strange.*;
import org.redfx.strange.gate.SingleQubitGate;
import org.redfx.strange.gate.ThreeQubitGate;
import org.redfx.strange.gate.TwoQubitGate;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import static org.redfx.strange.Complex.ONE;
import static org.redfx.strange.Complex.ZERO;

/**@author Johnathan Bizzano**/
public class QuantumEnvironment {
    private static boolean WARNING_SUPRESSION = false;

    /**Hadamard (H) gate
     * Reversible
     * If state is |0> or |1>, makes the chance of measuring either equal when applied
     * Matrix (1/Sqrt(2)) * {{1, 1}, {1, -1}}
     * @param qbits is an array of QBits to apply the transformation to
     * */
    public static Operation h(QBit... qbits) throws Exception{
        return op((i) -> Gate.hadamard(i[0]), qbits);
    }
    /**Hadamard (H) gate
     * Reversible
     * If state is |0> or |1>, makes the chance of measuring either equal when applied
     * Matrix (1/Sqrt(2)) * {{1, 1}, {1, -1}}
     * @param qbits is a list of QBits to apply the transformation to
     * */
    public static Operation h(List<QBit> qbits) throws Exception {
        return h(qbits.toArray(new QBit[0]));
    }
    /**Pauli-X (X) gate
     * Reversible
     * Is equal to a rotation around the X-axis of the Bloch sphere by pi radians
     * = rx(PI, qbits)
     * Matrix  {{0, 1}, {1, 0}}
     * @param qbits is an array of QBits to apply the transformation to
     * */
    public static Operation x(QBit... qbits) throws Exception{
        return op((i) -> Gate.x(i[0]), qbits);
    }
    /**Pauli-X (X) gate
     * Reversible
     * Is equal to a rotation around the X-axis of the Bloch sphere by pi radians
     * = rx(PI, qbits)
     * Matrix {{0, 1}, {1, 0}}
     * @param qbits is a list of QBits to apply the transformation to
     * */
    public static Operation x(List<QBit> qbits) throws Exception {
        return x(qbits.toArray(new QBit[0]));
    }
    /**Pauli-Y (Y) gate
     * Reversible
     * Is equal to a rotation around the Y-axis of the Bloch sphere by pi radians
     * = ry(PI, qbits)
     * Matrix {{0, -i}, {i, 0}}
     * @param qbits is an array of QBits to apply the transformation to
     * */
    public static Operation y(QBit... qbits) throws Exception{
        return op((i) -> Gate.y(i[0]), qbits);
    }
    /**Pauli-Y (Y) gate
     * Reversible
     * Is equal to a rotation around the Y-axis of the Bloch sphere by pi radians
     * = ry(PI, qbits)
     * Matrix {{0, -i}, {i, 0}}
     * @param qbits is a list of QBits to apply the transformation to
     * */
    public static Operation y(List<QBit> qbits) throws Exception {
        return y(qbits.toArray(new QBit[0]));
    }
    /**Pauli-Z (Z) gate
     * Reversible
     * Is equal to a rotation around the Z-axis of the Bloch sphere by pi radians
     * = rz(PI, qbits)
     * Matrix {{1, 0}, {0, -1}}
     * @param qbits is an array of QBits to apply the transformation to
     * */
    public static Operation z(QBit... qbits) throws Exception{
        return op((i) -> Gate.z(i[0]), qbits);
    }
    /**Pauli-Z (Z) gate
     * Reversible
     * Is equal to a rotation around the Z-axis of the Bloch sphere by pi radians
     * = rz(PI, qbits)
     * Matrix {{1, 0}, {0, -1}}
     * @param qbits is a list of QBits to apply the transformation to
     * */
    public static Operation z(List<QBit> qbits) throws Exception {
        return z(qbits.toArray(new QBit[0]));
    }
    /**Swap gate
     * Reversible
     * Swaps two qubits, Ex: |01> -> |10>
     * Matrix {{1, 0, 0, 0}, {0, 0, 1, 0},{0, 1, 0, 0}, {0, 0, 0, 1}}
     * @param qbit1 is a qubit to be swapped with qbit2
     * @param qbit2 is a qubit to be swapped with qbit1;
     * */
    public static Operation swap(QBit qbit1, QBit qbit2) throws Exception{
        return op((i) -> Gate.swap(i[0], i[1]), qbit1, qbit2);
    }
    /**Identity matrix (does no transformation)
     * @param qbits takes in an array of qubits**/
    public static Operation identity(QBit... qbits) throws Exception{
        return op((i) -> Gate.identity(i[0]), qbits);
    }
    /**Identity matrix (does no transformation)
     * @param qbits takes in a list of qubits**/
    public static Operation identity(List<QBit> qbits) throws Exception {
        return identity(qbits.toArray(new QBit[0]));
    }
    public static Operation oracle(QBit... qbits) throws Exception{
        return op((i) -> Gate.oracle(i[0]), qbits);
    }
    public static Operation oracle(List<QBit> qbits) throws Exception {
        return oracle(qbits.toArray(new QBit[0]));
    }

    public static Operation sqrtx(QBit... qbits) throws Exception{
        return op((i) -> new SingleQubitGate(i[0]){
            final Complex[][] matrix = new Complex[][]{
                    {new Complex(.5, .5), new Complex(.5, -.5)},
                    {new Complex(.5, -.5), new Complex(.5, .5)}};
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() { return "SQRTx";}
        }, qbits);
    }
    public static Operation sqrtx(List<QBit> qbits) throws Exception {
        return sqrtx(qbits.toArray(new QBit[0]));
    }
    public static Operation sqrtswap(QBit bit1, QBit bit2) throws Exception{
        return op((i) -> new TwoQubitGate(i[0], i[1]){
            final Complex[][] matrix = new Complex[][]{
                    {ONE, ZERO,ZERO,ZERO},
                    {ZERO,new Complex(.5, .5), new Complex(.5, -.5),ZERO},
                    {ZERO,new Complex(.5, -.5),new Complex(.5, .5),ZERO},
                    {ZERO,ZERO,ZERO,ONE}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() { return "SQRTswap";}
        }, bit1, bit2);
    }

    public static Operation rz(double theta, QBit... qbits) throws Exception{
        return op((i) -> new SingleQubitGate(i[0]){
            final Complex[][] matrix = new Complex[][]{
                    {Complex.ONE, Complex.ZERO},
                    {Complex.ZERO, e(theta)}};
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() { return "Rz";}
        }, qbits);
    }
    public static Operation rz(double theta, List<QBit> qbits) throws Exception {
        return rz(theta, qbits.toArray(new QBit[0]));
    }
    public static Operation ry(double theta, QBit... qbits) throws Exception{
        return op((i) -> new SingleQubitGate(i[0]){
            final Complex[][] matrix = new Complex[][]{
                    {new Complex(cos(theta / 2)), new Complex(-sin(theta / 2))},
                    {new Complex(sin(theta / 2)), new Complex(cos(theta / 2))}};
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() { return "Ry";}
        }, qbits);
    }
    public static Operation ry(double theta, List<QBit> qbits) throws Exception {
        return ry(theta, qbits.toArray(new QBit[0]));
    }
    public static Operation rx(double theta, QBit... qbits) throws Exception{
        return op((i) -> new SingleQubitGate(i[0]){
            final Complex[][] matrix = new Complex[][]{
                    {new Complex(cos(theta / 2)), new Complex(0, -sin(theta / 2))},
                    {new Complex(0, sin(theta / 2)), new Complex(cos(theta / 2))}};
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() { return "Rx";}
        }, qbits);
    }
    public static Operation rx(double theta, List<QBit> qbits) throws Exception {
        return rx(theta, qbits.toArray(new QBit[0]));
    }
    public static Operation rxyz(double theta, double phi, double lam, QBit... qbits) throws Exception{
        return op((i) -> new SingleQubitGate(i[0]){
            final Complex[][] matrix = new Complex[][]{
                    {new Complex(cos(theta / 2)), new Complex(-cos(lam), sin(lam)).mul(sin(theta / 2))},
                    {new Complex(cos(lam), sin(lam)).mul(sin(theta / 2)), new Complex(-cos(lam + phi), sin(lam + phi)).mul(cos(theta / 2))}};
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() { return "Rxyz";}
        }, qbits);
    }
    public static Operation rxyz(double theta, double phi, double lam, List<QBit> qbits) throws Exception {
        return rxyz(theta, phi, lam, qbits.toArray(new QBit[0]));
    }

    public static Operation toffoli(QBit controlBit1, QBit controlBit2, QBit targetBit) throws Exception{
        return op((i) -> Gate.toffoli(i[0], i[1], i[2]), controlBit1, controlBit2, targetBit);
    }
    public static Operation cry(double theta, QBit controlBit, QBit targetBit) throws Exception{
        return op((i) -> new TwoQubitGate(i[0], i[1]) {
            final Complex[][] matrix = new Complex[][]{
                    {Complex.ONE,Complex.ZERO,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ONE,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ZERO,new Complex(cos(theta / 2)), new Complex(-sin(theta / 2))},
                    {Complex.ZERO,Complex.ZERO,Complex.ZERO,new Complex(sin(theta / 2)), new Complex(cos(theta / 2))}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() {
                return "Cry";
            }
        }, controlBit, targetBit);
    }
    public static Operation crz(double theta, QBit controlBit, QBit targetBit) throws Exception{
        return op((i) -> new TwoQubitGate(i[0], i[1]) {
            final Complex[][] matrix = new Complex[][]{
                    {Complex.ONE,Complex.ZERO,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ONE,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ZERO,Complex.ONE,Complex.ZERO},
                    {Complex.ZERO,Complex.ZERO,Complex.ZERO,new Complex(cos(theta), sin(theta))}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() {
                return "Crz";
            }
        }, controlBit, targetBit);
    }
    public static Operation crx(double theta, QBit controlBit, QBit targetBit) throws Exception{
        return op((i) -> new TwoQubitGate(i[0], i[1]) {
            final Complex[][] matrix = new Complex[][]{
                    {Complex.ONE,Complex.ZERO,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ONE,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ZERO, new Complex(cos(theta / 2)), new Complex(0, -sin(theta / 2))},
                    {Complex.ZERO,Complex.ZERO, new Complex(0, sin(theta / 2)), new Complex(cos(theta / 2))}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() {
                return "Crx";
            }
        }, controlBit, targetBit);
    }
    /**Controlled Swap gate
     * Reversible
     * Swaps two qubits, Ex: |01> -> |10>
     * Matrix {{1, 0, 0, 0}, {0, 0, 1, 0},{0, 1, 0, 0}, {0, 0, 0, 1}}
     * @param controlBit If this qubit is 1, a swap is performed on qubit2 and qubit3
     * @param targetBit1 is a qubit to be swapped with targetBit2
     * @param targetBit2 is a qubit to be swapped with targetBit1;
     * */
    public static Operation cswap(QBit controlBit, QBit targetBit1, QBit targetBit2) throws Exception{
        return op((i) -> new ThreeQubitGate(i[0], i[1], i[2]) {
            final Complex[][] matrix = new Complex[][]{
                    {ONE,ZERO,ZERO,ZERO,ZERO,ZERO,ZERO,ZERO},
                    {ZERO,ONE,ZERO,ZERO,ZERO,ZERO,ZERO,ZERO},
                    {ZERO,ZERO,ONE,ZERO,ZERO,ZERO,ZERO,ZERO},
                    {ZERO,ZERO,ZERO,ONE,ZERO,ZERO,ZERO,ZERO},
                    {ZERO,ZERO,ZERO,ZERO,ONE,ZERO,ZERO,ZERO},
                    {ZERO,ZERO,ZERO,ZERO,ZERO,ZERO,ONE,ZERO},
                    {ZERO,ZERO,ZERO,ZERO,ZERO,ONE,ZERO,ZERO},
                    {ZERO,ZERO,ZERO,ZERO,ZERO,ZERO,ZERO,ONE}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() {
                return "CSwap";
            }
        }, controlBit, targetBit1, targetBit2);
    }
    public static Operation crxyz(double theta, double phi, double lam, QBit controlBit, QBit targetBit) throws Exception{
        return op((i) -> new TwoQubitGate(i[0], i[1]) {
            final Complex[][] matrix = new Complex[][]{
                    {Complex.ONE,Complex.ZERO,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ONE,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ZERO, new Complex(cos(theta / 2)), new Complex(-cos(lam), sin(lam)).mul(sin(theta / 2))},
                    {Complex.ZERO,Complex.ZERO, new Complex(cos(lam), sin(lam)).mul(sin(theta / 2)), new Complex(-cos(lam + phi), sin(lam + phi)).mul(cos(theta / 2))}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() {
                return "Crxyz";
            }
        }, controlBit, targetBit);
    }
    public static Operation cx(QBit controlBit, QBit targetBit) throws Exception {
        return crx(PI, controlBit, targetBit);
    }
    public static Operation cz(QBit controlBit, QBit targetBit) throws Exception {
        return crz(PI, controlBit, targetBit);
    }
    public static Operation cy(QBit controlBit, QBit targetBit) throws Exception {
        return cry(PI, controlBit, targetBit);
    }

    public static Operation xx(double theta, QBit bit1, QBit bit2) throws Exception{
        return op((i) -> new TwoQubitGate(i[0], i[1]) {
            final Complex[][] matrix = new Complex[][]{
                    {new Complex(cos(theta)),Complex.ZERO,Complex.ZERO,new Complex(0, -sin(theta))},
                    {Complex.ZERO,new Complex(cos(theta)),new Complex(0, -sin(theta)),Complex.ZERO},
                    {Complex.ZERO,new Complex(0, -sin(theta)), new Complex(cos(theta)), ZERO},
                    {new Complex(0, -sin(theta)),Complex.ZERO, ZERO, new Complex(cos(theta))}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() {
                return "Xx";
            }
        }, bit1, bit2);
    }
    public static Operation yy(double theta, QBit bit1, QBit bit2) throws Exception{
        return op((i) -> new TwoQubitGate(i[0], i[1]) {
            final Complex[][] matrix = new Complex[][]{
                    {new Complex(cos(theta)),Complex.ZERO,Complex.ZERO,new Complex(0, sin(theta))},
                    {Complex.ZERO,new Complex(cos(theta)),new Complex(0, -sin(theta)),Complex.ZERO},
                    {Complex.ZERO,new Complex(0, -sin(theta)), new Complex(cos(theta)), ZERO},
                    {new Complex(0, sin(theta)),Complex.ZERO, ZERO, new Complex(cos(theta))}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() {
                return "Yy";
            }
        }, bit1, bit2);
    }
    public static Operation zz(double theta, QBit bit1, QBit bit2) throws Exception {
        return op((i) -> new TwoQubitGate(i[0], i[1]) {
            final Complex[][] matrix = new Complex[][]{
                    {e(theta / 2),Complex.ZERO,Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,e(-theta / 2),Complex.ZERO,Complex.ZERO},
                    {Complex.ZERO,Complex.ZERO,e(-theta / 2),Complex.ZERO},
                    {Complex.ZERO,Complex.ZERO,Complex.ZERO,ZERO, e(theta / 2)}
            };
            @Override
            public Complex[][] getMatrix() {
                return matrix;
            }
            @Override public String getCaption() {
                return "Zz";
            }
        }, bit1, bit2);
    }


    public static int steps(QBit bit){
        return bit.gates.size();
    }
    public static Operation apply(Circuit c, QBit... qbits){
        return new Operation(() -> {
            for(QBit bit : qbits){
                for(GateOperation g : c.gates)
                    bit.mark(g);
            }
        });
    }
    public static Operation apply(Circuit c, List<QBit> qbits) {
        return apply(c, qbits.toArray(new QBit[0]));
    }
    public static Circuit build(String name, QBit bit){
        return new Circuit(name, bit.gates);
    }
    public static double peekProb0(QBit bit) {
        if(!bit.measured)measure(bit);
        warn("Remember this is a hack, you cant just peek at the alpha on a quantum computer",
                "You should instead do several trials to approximate the alpha");
        return bit.measuredProb;
    }
    public static double peekProb1(QBit bit) {
        warn("Remember this is a hack, you cant just peek at the beta on a quantum computer",
                "You should instead do several trials to approximate the beta");
        return 1 - peekProb0(bit);
    }
    public static boolean measure(QBit bit) {
        if(bit.measured)return bit.measuredValue;
        bit.mark(new GateOperation((i) -> Gate.measurement(i[0]), bit));
        Object2IntOpenHashMap<QBit> indexMap = bit.createIndexMap();
        Program p = new Program(indexMap.size());
        for(GateOperation op : bit.gates) p.addStep(op.getStep(indexMap));
        for(Object2IntMap.Entry<QBit> entry : indexMap.object2IntEntrySet())p.initializeQubit(entry.getIntValue(), entry.getKey().alpha);
        Qubit qubit = new SimpleQuantumExecutionEnvironment().runProgram(p).getQubits()[indexMap.getInt(bit)];
        bit.measured = true;
        bit.measuredValue = qubit.measure() == 1;
        bit.measuredProb = qubit.getProbability();
        bit.alpha = bit.measuredValue ? 0 : 1;
        bit.explicitMeasurement = true;
        return bit.measuredValue;
    }
    /**System measurement**/
    public static boolean[] measure(QBit... bits) {
        boolean[] results = new boolean[bits.length];
        Object2IntOpenHashMap<QBit> indexMap = new Object2IntOpenHashMap<>();
        ArrayList<GateOperation> gates = new ArrayList<>();
        for(int bitIndex = 0; bitIndex < bits.length; ++bitIndex){
            QBit bit = bits[bitIndex];
            if(!bit.measured){
                for(Object2IntMap.Entry<QBit> entry : bit.createIndexMap().object2IntEntrySet()){
                    if(!indexMap.containsKey(entry.getKey())){
                        indexMap.put(entry.getKey(), indexMap.size());
                    }
                }
                bit.mark(new GateOperation((i) -> Gate.measurement(i[0]), bit));
                for(GateOperation op : bit.gates)if(!gates.contains(op))gates.add(op);
            }else{
                results[bitIndex] = bit.measuredValue;
            }
        }
        Program p = new Program(indexMap.size());
        for(GateOperation op : gates) p.addStep(op.getStep(indexMap));
        for(Object2IntMap.Entry<QBit> entry : indexMap.object2IntEntrySet()){
            p.initializeQubit(entry.getIntValue(), entry.getKey().alpha);
        }
        Qubit[] qubits = new SimpleQuantumExecutionEnvironment().runProgram(p).getQubits();
        for(int i = 0; i < bits.length; ++i){
            QBit bit = bits[i];
            Qubit qubit = qubits[indexMap.getInt(bit)];
            if(!bit.measured) {
                bit.measured = true;
                bit.measuredValue = qubit.measure() == 1;
                bit.measuredProb = qubit.getProbability();
                bit.alpha = bit.measuredValue ? 0 : 1;
                bit.explicitMeasurement = true;
            }
            results[results.length - i - 1] = bit.measuredValue;
        }
        return results;
    }
    public static boolean[] measure(List<QBit> qbits) {
        return measure(qbits.toArray(new QBit[0]));
    }
    public static QuantumExecutor store(QBit... bits){
        boolean[] results = new boolean[bits.length];
        Object2IntOpenHashMap<QBit> indexMap = new Object2IntOpenHashMap<>();
        ArrayList<GateOperation> gates = new ArrayList<>();
        for(int bitIndex = 0; bitIndex < bits.length; ++bitIndex){
            QBit bit = bits[bitIndex];
            if(!bit.measured){
                for(Object2IntMap.Entry<QBit> entry : bit.createIndexMap().object2IntEntrySet()){
                    if(!indexMap.containsKey(entry.getKey())){
                        indexMap.put(entry.getKey(), indexMap.size());
                    }
                }
                bit.mark(new GateOperation((i) -> Gate.measurement(i[0]), bit));
                for(GateOperation op : bit.gates)if(!gates.contains(op))gates.add(op);
            }else{
                results[bitIndex] = bit.measuredValue;
            }
        }
        Program p = new Program(indexMap.size());
        for(GateOperation op : gates) p.addStep(op.getStep(indexMap));
        for(Object2IntMap.Entry<QBit> entry : indexMap.object2IntEntrySet()){
            p.initializeQubit(entry.getIntValue(), entry.getKey().alpha);
        }
        return new QuantumExecutor(p, (qubits) -> {
            for(int i = 0; i < bits.length; ++i){
                QBit bit = bits[i];
                Qubit qubit = qubits[indexMap.getInt(bit)];
                if(!bit.measured) {
                    bit.measured = true;
                    bit.measuredValue = qubit.measure() == 1;
                    bit.measuredProb = qubit.getProbability();
                    bit.alpha = bit.measuredValue ? 0 : 1;
                    bit.explicitMeasurement = true;
                }
                results[results.length - i - 1] = bit.measuredValue;
            }
            return results;
        });
    }
    public static QuantumExecutor store(List<QBit> bits){
        return store(bits.toArray(new QBit[0]));
    }


    static void warn(String... s){
        if(!WARNING_SUPRESSION) {
            System.out.print("\n\u001B[31m*****WARNING: " + s[0]);
            for (int i = 1; i < s.length - 1; ++i) System.out.println(s[i]);
            System.out.print("\n" + s[s.length - 1] + " ******\u001B[0m\n");
        }
    }
    public static void setWarnings(boolean b){
        WARNING_SUPRESSION = !b;
    }
    private static Operation op(Function<int[], Gate> g, QBit... qbits) throws Exception {
        for(QBit bit : qbits){
            if(bit.explicitMeasurement){
                throw new Exception("Measured bit(" + bit + ") attempting to be used in circuit again!");
            }
        }
        return new Operation(() -> {
            GateOperation gate = new GateOperation(g, qbits);
            for(QBit bit : qbits){
                if(!bit.measured)
                    bit.mark(gate);
            }
        });
    }
    public static double cos(double theta){
        return Math.cos(theta);
    }
    public static double sin(double theta){
        return Math.sin(theta);
    }
    public static Complex e(double power){
        return new Complex(cos(power), sin(power));
    }
    public static double PI = Math.PI;
    public static double E = Math.E;
}
