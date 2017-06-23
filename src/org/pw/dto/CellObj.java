package org.pw.dto;

import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import javafx.scene.shape.Path;

public class CellObj {
    int id;
    String zone;
    String vertices;
    double[] verX;
    double[] verY;
    double numerical_ar;
    String name;
    boolean filled;
    String locationOnTree;
    String func;
    int[] Rgb;
    public boolean ignore; //sometimes loops are getting stuck
    
    public CellObj(int id_, String[] ver_, String zone_){
        Rgb=new int[4];
        locationOnTree="0,";
        id=id_;
        verX=new double[ver_.length];
        verY=new double[ver_.length];
        zone=zone_;
        for (int i=0; i<ver_.length; i++){
            double x=Double.parseDouble(ver_[i].split(",")[0]);
            double y=Double.parseDouble(ver_[i].split(",")[1]);
            verX[i]=x;
            verY[i]=y;
        }
        filled=false;
        ignore=false;
    }
    public GeneralPath genPath(){
        GeneralPath path=new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        if(verX.length==4){
            path.moveTo(verX[0],verY[0]);
            path.lineTo(verX[1],verY[1]);
            path.lineTo(verX[2],verY[2]);
            path.lineTo(verX[3],verY[3]);
            path.closePath();
        }else if(verY.length==3){
            path.moveTo(verX[0],verY[0]);
            path.lineTo(verX[1],verY[1]);
            path.lineTo(verX[2],verY[2]);
            path.closePath(); 
        }
        return path;
    }
    public int getId(){
        return id;
    }
    public double[] getVerX(){
        return verX;
    }
    public double[] getVerY(){
        return verY;
    }
    public double getDiagLength(){
        double x0=verX[0];
        double y0=verY[0];
        double x1=verX[2];
        double y1=verY[2];
        double d1= Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
        return d1;
    }
    
    public void setId(int id_){
        id=id_;
    }
    public String getVertices(){
        return vertices;
    }    
    public void setVertices(String v_){
        vertices=v_;
    }
    public double[] getMidPt(){
        double mpx=(verX[0]+verX[2])/2;
        double mpy=(verY[0]+verY[2])/2;
        double[] mp=new double[2];
        mp[0]=mpx;
        mp[1]=mpy;        
        return mp;
    }
    public String getZone(){
        return zone;
    }
    public void setZone(String zone_){
        zone=zone_;
    }
    public double getNumericalArea(){
        double x0=verX[0];
        double y0=verY[0];
        double x1=verX[1];
        double y1=verY[1];
        double x2=verX[2];
        double y2=verY[2];
        if(verX.length==4){
            double x3=verX[3];
            double y3=verY[3];
            double d0=dis(x0,y0,x1,y1);
            double d1=dis(x1,y1,x2,y2);
            numerical_ar=d0*d1;
        }else{
            double d01=dis(x0,y0,x1,y1);
            double d12=dis(x1,y1,x2,y2);
            double d20=dis(x2,y2,x0,y0);
            double s=(d01+d12+d20)/2;
            double numerical_ar=Math.sqrt(s*(s-d01)*(s-d12)*(s-d20));
        }        
        return numerical_ar;
    }
    public void setNumericalArea(double ar_){
        numerical_ar=ar_;
    }
    public String getName(){
        return name;
    }
    public void setName(String name_){
        name=name_;
    }
    public double[] getPos(){
        double x=verX[0];
        double y=verY[0];
        double[] pt=new double[2];
        pt[0]=x;
        pt[1]=y;
        return pt;
    }
    public boolean getFilled(){
        return filled;
    }
    public void setFilled(boolean t){
        filled=t;
    }
    public String getLocationOnTree(){
        return locationOnTree;
    }
    public void setLocationOnTree(String t){
        locationOnTree+=t+",";
    }
    public int[] getRGB(){
        return Rgb;
    }
    public void setRGB(int r, int g, int b, int tr_){
        Rgb[0]=r;
        Rgb[1]=g;
        Rgb[2]=b;
        Rgb[3]=tr_;
    }
    public void clearAttribute(){
        Rgb[0]=255;
        Rgb[1]=255;
        Rgb[2]=255;
        locationOnTree="";
    }
    public String getFunc(){
        return func;
    }
    public void setFunc(String func_){
        func=func_;
    }
    public boolean pointInQuad(double[] pt){
        boolean t=false;
        double a=pt[0];
        double b=pt[1];
        if(verX.length>2){
            if(a>verX[0] && a<verX[2] && b>verY[0] && b<verY[2]){
                t=true;
            }
        }
        return t;
    }
    public void setIgnore(boolean t){
        ignore=t;
    }
    public boolean getIgnore(){
        return ignore; 
    }
    public double dis(double x0, double y0, double x1, double y1){
        double d=Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
        return d;
    }

    public void clrAttributes(){
        id=1;
        zone="";
        vertices="";
        double[] verX;
        double[] verY;
        numerical_ar=0;
        name="";
        filled=false;
        locationOnTree="";
        func="";
        int[] Rgb;
        ignore=false; 
    }
}
