package org.redfx.strange.local;

import org.redfx.strange.*;
import org.redfx.strange.gate.PermutationGate;
import org.redfx.strange.gate.ProbabilitiesGate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**@author johan vos (From SimpleQuantumExecutionEnvironment.class)**/
public class QuantumExecutor implements Cloneable {
    private final List<Step> simpleSteps, steps;
    private final int nQubits;
    private final Qubit[] qubit;
    private final Complex[] probs;
    private final Program p;
    private final Function<Qubit[], boolean[]> functs;

    public QuantumExecutor(Program p, Function<Qubit[], boolean[]> functs) {
        this.p = p;
        this.functs = functs;
        nQubits = p.getNumberQubits();
        qubit = new Qubit[nQubits];

        int dim;
        for (dim = 0; dim < nQubits; ++dim) {
            qubit[dim] = new Qubit();
        }

        dim = 1 << nQubits;
        double[] initalpha = p.getInitialAlphas();
        probs = new Complex[dim];

        int cnt;
        int i;
        for (i = 0; i < dim; ++i) {
            probs[i] = Complex.ONE;

            for (int j = 0; j < nQubits; ++j) {
                int pw = nQubits - j - 1;
                cnt = 1 << pw;
                int div = i / cnt;
                i = div % 2;
                if (i == 0) {
                    probs[i] = probs[i].mul(initalpha[j]);
                } else {
                    probs[i] = probs[i].mul(Math.sqrt(1.0D - initalpha[j] * initalpha[j]));
                }
            }
        }
        steps = p.getSteps();
        if(p.getDecomposedSteps() != null)
            simpleSteps = p.getDecomposedSteps();
        else {
            simpleSteps = new ArrayList<>();
            for (Step step : steps) {
                (simpleSteps).addAll(Computations.decomposeStep(step, nQubits));
            }
            p.setDecomposedSteps(simpleSteps);
        }

    }

    public boolean[] measure() {
        Complex[] probs = this.probs.clone();
        Qubit[] qubit = this.qubit.clone();
        List<Step> simpleSteps = (List<Step>) ((ArrayList)this.simpleSteps).clone();
        Result result = new Result(nQubits, steps.size());
        if ((simpleSteps).isEmpty()) {
            result.setIntermediateProbability(0, probs);
        }
        for (Object o : simpleSteps) {
            Step step = (Step) o;
            if (!step.getGates().isEmpty()) {
                probs = this.applyStep(step, probs, qubit);
                int idx = step.getComplexStep();
                if (idx > -1) {
                    result.setIntermediateProbability(idx, probs);
                }
            }
        }
        double[] qp = this.calculateQubitStatesFromVector(probs);
        for (int i = 0; i < nQubits; ++i) {
            qubit[i].setProbability(qp[i]);
        }
        result.measureSystem();
        p.setResult(result);
        return functs.apply(result.getQubits());
    }

    private Complex[] applyStep(Step step, Complex[] vector, Qubit[] qubits) {
        List<Gate> gates = step.getGates();
        if (!gates.isEmpty() && gates.get(0) instanceof ProbabilitiesGate) {
            return vector;
        } else if (gates.size() == 1 && gates.get(0) instanceof PermutationGate) {
            PermutationGate pg = (PermutationGate)gates.get(0);
            return Computations.permutateVector(vector, pg.getIndex1(), pg.getIndex2());
        } else {
            return Computations.calculateNewState(gates, vector, qubits.length);
        }
    }

    private Complex[][] calculateStepMatrix(List<Gate> gates, int nQubits) {
        return Computations.calculateStepMatrix(gates, nQubits);
    }

    private double[] calculateQubitStatesFromVector(Complex[] vectorresult) {
        int nq = (int)Math.round(Math.log(vectorresult.length) / Math.log(2.0D));
        double[] answer = new double[nq];
        int ressize = 1 << nq;

        for(int i = 0; i < nq; ++i) {
            int div = 1 << i;

            for(int j = 0; j < ressize; ++j) {
                int p1 = j / div;
                if (p1 % 2 == 1) {
                    answer[i] += vectorresult[j].abssqr();
                }
            }
        }
        return answer;
    }

}
