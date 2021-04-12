package com.hypersphere;
/**@author Johnathan Bizzano**/
public class Operation{
    private final Runnable r;
    public Operation(Runnable r){
        this.r = r;
        r.run();
    }
    public void repeat(int times){
        for(int i = 0; i < times; ++i){
            r.run();
        }
    }
}