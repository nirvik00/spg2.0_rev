package org.pw.dto;

import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class PeriObj {
    ArrayList<CellObj> cellObjList;
    String vertices;
    String name;
    
    PeriObj(ArrayList<CellObj>cellobj_, String ver_, String name_){
        cellObjList=new ArrayList<CellObj>();
        cellObjList.clear();
        cellObjList.addAll(cellobj_);
        name=name_;
        vertices=ver_;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String s){
        name=s;
    }
    
    public void setVertices(String ver_){
        vertices=ver_;
    }
    
    public double[] getMidPt(){
        double[] midPt=new double[2];
        double x0=Double.parseDouble(vertices.split(",")[0]);
        double y0=Double.parseDouble(vertices.split(",")[1]);
        double x1=Double.parseDouble(vertices.split(",")[2]);
        double y1=Double.parseDouble(vertices.split(",")[3]);
        double x2=Double.parseDouble(vertices.split(",")[4]);
        double y2=Double.parseDouble(vertices.split(",")[5]);
        double x3=Double.parseDouble(vertices.split(",")[6]);
        double y3=Double.parseDouble(vertices.split(",")[7]);
        double x=(x0+x2)/2;
        double y=(y0+y2)/2;
        midPt[0]=x;
        midPt[1]=y;
        return midPt;
    }
    public String getVertices(){
        return vertices;
    }
    
    public Area genArea(){
    Area ar=new Area();   

        return ar;
    }    
}
