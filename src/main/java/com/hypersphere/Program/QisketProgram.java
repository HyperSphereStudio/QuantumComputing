package com.hypersphere.Program;

public class QisketProgram<T> extends QuantumProgram<T>{
    public QisketProgram(QuantumProgram<T> program) {
        super(program.name, program.readStart, program.readEnd, program.reader, program.executor);
    }

    public String getPythonScript(){
        return "";
    }

    public QuantumResult runIBMServer(int shots){
        return null;
    }
}
