# QuantumComputing
 


Wrapper for the Java Strange Quantum Computing API to allow larger scale programs to be developed
Working on allowing integregation with the IBM Qisket so one can execute the Quantum program's developed in Java on a real quantum computer. Also working on being able to create Qisket python scripts from the Strange Quantum Circuit and vice versa.


HOW TO USE:
You can extend the class QuantumEnvironment to allow methods to be called easily from it

Examples:

public class Example extends QuantumEnvironment {

    //Limited documentation available in the QuantumEnvironment class file about the different operations that can
    //be performed.

    public static void main(String[] args) throws Exception {

        for(int i = 0; i < 4; ++i){
            //Basic Quantum Circuit
            System.out.println("Basic Quantum Circuit #" + i + " Result:" + myBasicCircuit(.3d));
        }

        //Stored Quantum Circuit example
        QBit b = new QBit();
        for(int i = 2; i < 5; ++i){
            Circuit c = myCircuitExample(PI / i);
            //Apply the circuit to a qubit
            b.applyCircuit(c);
        }
        //Alternate way to measure
        System.out.println("Stored Quantum Circuit Example Result:" + b.measure() + " Circuit Size:" + steps(b));

        /*
        //Keeps the quantum sim initialized so that it may execute a lot faster for certain problems
        //Note that the randomness might not occur depending on the problem
       // QuantumExecutor executor = myStoredCircuitExample(new QBit());
        for(int i = 0; i < 3; ++i){
            //System.out.println(executor.measure()[0]);
        }*/
    }

    public static QuantumExecutor myStoredCircuitExample(QBit b) throws Exception{
        for(int i = 2; i < 5; ++i){
            Circuit c = myCircuitExample(PI / i);
            //Apply the circuit to a qubit
            //All operations return an operation object which can do further operations such as repeat n times
            b.applyCircuit(c).repeat(2);
        }
        return store(b);
    }

    public static Circuit myCircuitExample(double theta) throws Exception {
        QBit parameterQBit = new QBit();
        h(parameterQBit);
        ry(theta, parameterQBit);
        return build("My Simple Circuit", parameterQBit);
    }

    public static boolean myBasicCircuit(double d) throws Exception {
        QBit bit = new QBit();
        QBit bit2 = new QBit(d);
        x(bit);
        h(bit);
        y(bit);
        cx(bit, bit2);
        return measure(bit2);
    }
}
