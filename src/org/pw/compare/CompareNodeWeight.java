package org.pw.compare;

import java.util.Comparator;
import org.pw.dto.FuncNodeObj;

public class CompareNodeWeight implements Comparator<FuncNodeObj>{
    @Override
    public int compare(FuncNodeObj o1, FuncNodeObj o2) {
        double w1=o1.getWeight();
        double w2=o2.getWeight();
        if(w1>w2){
            return 1;
        }else if(w1<w2){
            return -1;
        }else{
            return 0;
        }
    }    
}
