package org.pw.dto;

public class FuncCellObj {
    
    double X,Y, area;
    int id;
    double area_multiplier;
    int groupId;
    int[] rgb;
    int startCell;
    String department;
    public FuncCellObj(int id_, int groupId_, double x_, double y_, double area_,
            int[]rgb_, double mul_, String dept_){
        X=x_;
        Y=y_;
        id=id_;
        area_multiplier=mul_;
        area=area_*area_multiplier;
        groupId=groupId_;
        rgb=rgb_;
        department=dept_;
    }
    public void setId(int id_){
        id=id_;
    }
    public int getId(){
        return id;
    }
    public void setGroupId(int gid_){
        groupId=gid_;
    }
    public int getGroupId(){
        return groupId;
    }
    public void setX(double x_){
        X=x_;
    }
    public double getX(){
        return X;
    }
    public void setY(double y_){
        Y=y_;
    }
    public double getY(){
        return Y;
    }
    public void setArea(double a_){
        area=a_;
    }
    public double getArea(){
        return area;
    }
    public void setRgb(int[] rgb_){
        rgb=rgb_;
    }
    public int[] getRgb(){
        return rgb;
    }
    public int getStartCell(){
        return startCell;
    }
    public String getDepartment(){
        return department;
    }
    public void setDepartment(String dept_){
        department=dept_;
    }
}
