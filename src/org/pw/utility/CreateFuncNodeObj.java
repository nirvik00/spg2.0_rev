package org.pw.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.pw.compare.CompareNodeWeight;
import org.pw.dto.FuncNodeObj;

public class CreateFuncNodeObj {
    ArrayList<String>dataList;
    ArrayList<FuncNodeObj>graphObjList;
    Random rnd;
    public CreateFuncNodeObj(ArrayList<String>dataList_){
        rnd=new Random();
        dataList=new ArrayList<String>();
        dataList.clear();
        dataList.addAll(dataList_);
        graphObjList= new ArrayList<FuncNodeObj>();
        graphObjList.clear();
    }
    public ArrayList<FuncNodeObj> makeNodes(){
        for(int i=1; i<dataList.size(); i++){
            String s=dataList.get(i);
            //System.out.println(s);
            int id=i-1;
            String prog_name=dataList.get(i).split(";")[1];
            double qnty=Double.parseDouble(dataList.get(i).split(";")[3]);
            double ar_each=Double.parseDouble(dataList.get(i).split(";")[4]);
            double dim_each=Math.sqrt(ar_each);
            String adj_to=dataList.get(i).split(";")[8];
            int red=rnd.nextInt(255);
            int grn=rnd.nextInt(255);
            int blu=rnd.nextInt(255);
            int[] rgb={red,grn,blu};
            int weight=(int)Double.parseDouble(dataList.get(i).split(";")[6]);
            String zone=dataList.get(i).split(";")[7];
            String dept=dataList.get(i).split(";")[1];
            FuncNodeObj node=new FuncNodeObj(id, prog_name, qnty, ar_each, dim_each, adj_to, rgb, weight, zone, dept);
            graphObjList.add(node);
            //System.out.println(node.returnInfo());
        }
        Collections.sort(graphObjList, new CompareNodeWeight());
        return graphObjList;
    }    
}
