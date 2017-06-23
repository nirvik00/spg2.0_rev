package org.pw.GA.func;

import java.util.ArrayList;
import java.util.Random;
import org.pw.dto.CellObj;
import org.pw.dto.PeriObj;

public class AllocateFunc {
    
    int num_req;
    double ar_req;
    ArrayList<CellObj>reqCellObjList;
    ArrayList<String>periCellObjStrList;
    ArrayList<String> newRectStr;
    Random rnd;
    int[] idxArr;
    
    
    public AllocateFunc(int num_req_, double ar_req_,
            ArrayList<CellObj>reqCellObjList_){ 
        rnd=new Random();
        num_req=num_req_;
        ar_req=ar_req_;
        
        reqCellObjList=new ArrayList<CellObj>();
        reqCellObjList.clear();
        reqCellObjList.addAll(reqCellObjList_);
        
        periCellObjStrList=new ArrayList<String>();
        periCellObjStrList.clear();        

        newRectStr=new ArrayList<String>();
        newRectStr.clear();
        
        idxArr=new int[num_req];
    }

    public void allocateCells(int num_ite, String type){
        int k=0;
        int idx=getIndex(num_ite);
        String s1;
        if(type.equals("peripheral")){
            s1=calcPeripheralRect(idx);
        }else if(type.equals("general")){
            //idx=rnd.nextInt(reqCellObjList.size());
            s1=calcGeneralRect(idx);
        }else{
            //idx=rnd.nextInt(reqCellObjList.size());
            s1=calcInterPeri(idx);
        }
        double x0=Double.parseDouble(s1.split(",")[0]);
        double y0=Double.parseDouble(s1.split(",")[1]);
        double x1=Double.parseDouble(s1.split(",")[2]);
        double y1=Double.parseDouble(s1.split(",")[3]);
        double x2=Double.parseDouble(s1.split(",")[4]);
        double y2=Double.parseDouble(s1.split(",")[5]);
        double x3=Double.parseDouble(s1.split(",")[6]);
        double y3=Double.parseDouble(s1.split(",")[7]);
        
        String idx_str="";
        for(int j=0; j<reqCellObjList.size(); j++){
            CellObj objB=reqCellObjList.get(j);   
            double mpx=objB.getMidPt()[0];
            double mpy=objB.getMidPt()[1];
            String pt=mpx+","+mpy;
            String ln1=x0+","+y0+","+x1+","+y1;
            String ln4=x1+","+y1+","+x2+","+y2;
            String ln2=x2+","+y2+","+x3+","+y3;
            String ln3=x3+","+y3+","+x0+","+y0;
            
            boolean t1=checkPtInFigure(pt, s1);
            boolean t2=checkPtInFigure(pt, s1);
            boolean t3=checkPtInFigure(pt, s1);
            boolean t4=checkPtInFigure(pt, s1);
            
            if(t1==true && t2==true && t3==true && t4==true){
                idx_str+=objB.getId()+",";//STRING OF INDICES OF CELL OBJECTS
            }
        }
        try{
            int sum=0;
            int n=idx_str.split(",").length;
            for(int j=0; j<n; j++){
                int req_id=Integer.parseInt(idx_str.split(",")[j]);
                CellObj obj=reqCellObjList.get(req_id);
                if(obj.getFilled()==false){
                    sum++;//CHECK IF ALL CELLOBJ ARE UN-OCCUPIED
                }
            }
            String sx="";
            double tolerance=0.15;
            
            if(type.equals("peripheral")){
                tolerance=0.25;
            }else if(type.equals("general")){
                tolerance=0.5;
            }else{
                tolerance=0.1;
            }
            
            boolean t=checkArea(s1,idx_str,tolerance);//CHECK IF THE FUNC IS JUTTING OUT 
            if(sum==n-1 && t==true){
                newRectStr.add(s1);
                num_ite++; //NOTICE THE INCREMENT IN ITERATION
                for(int j=0; j<n; j++){
                    int req_id=Integer.parseInt(idx_str.split(",")[j]);
                    sx+=req_id+",";
                    CellObj objB=reqCellObjList.get(req_id);
                    objB.setFilled(true);                
                }
                periCellObjStrList.add(sx);
            }else{
                reqCellObjList.get(idx).setFilled(false);
                reqCellObjList.get(idx).setIgnore(true);
            }
        }catch(Exception e){
            reqCellObjList.get(idx).setFilled(false);
            reqCellObjList.get(idx).setIgnore(true);
        }
        if(num_ite<num_req && idx<reqCellObjList.size()-1 && idx>0){
            allocateCells(num_ite, type);
        }
    }
        
    public String calcPeripheralRect(int idx){
        double x0=reqCellObjList.get(idx).getVerX()[0];
        double x1=reqCellObjList.get(idx).getVerX()[3];
        double y0=reqCellObjList.get(idx).getVerY()[0];
        double y1=reqCellObjList.get(idx).getVerY()[3];

        double d0=dis(x0,y0,x1,y1);
        double d1=ar_req/d0;
        double dx0=x0-x1;
        double dy0=y0-y1;
        double px0= dy0*Math.sin(Math.PI/2) + x1;
        double py0= -dx0*Math.sin(Math.PI/2) + y1;
        double di0=dis(x1,y1,px0,py0);
        double di00=(d1/di0);
        double sx0=(px0-x1)*di00+x1;
        double sy0=(py0-y1)*di00+y1;

        double dx1=x1-x0;
        double dy1=y1-y0;
        double px1= -dy1*Math.sin(Math.PI/2) + x0;
        double py1= dx1*Math.sin(Math.PI/2) + y0;
        double di1=dis(x0,y0,px1,py1);
        double di01=(d1/di0);
        double sx1=(px1-x0)*di01+x0;
        double sy1=(py1-y0)*di01+y0;
        
        double di_0=dis(x1,y1,sx0,sy0);
        double di_1=dis(x0,y0,x1,y1);
        
        String s1=x1+","+y1+","+sx0+","+sy0+","+sx1+","+sy1+","+x0+","+y0;
        return s1;
    }
    
    public String calcGeneralRect(int idx){
        double r1=Math.sqrt(ar_req);
        //double r0=rnd.nextDouble()*10+r1;
        double r0=r1;
        double x0=reqCellObjList.get(idx).getVerX()[0];
        double y0=reqCellObjList.get(idx).getVerY()[0];                
        double x1=x0+r0;
        double y1=y0;
        double x2=x0+r0;
        double y2=y0+r0;
        double x3=x0;
        double y3=y0+r0;
        String s1=x0+","+y0+","+x1+","+y1+","+x2+","+y2+","+x3+","+y3;
        return s1;
    }
    
    public String calcInterPeri(int idx){
        double d0=Math.sqrt(ar_req);
        double d1=Math.sqrt(ar_req);

        double m0=reqCellObjList.get(idx).getVerX()[0];
        double n0=reqCellObjList.get(idx).getVerY()[0];
        double m1=reqCellObjList.get(idx).getVerX()[3];        
        double n1=reqCellObjList.get(idx).getVerY()[3];
        double df0=dis(m0,n0,m1,n1);

        double x0=m0;
        double x1=((m1-m0)*(d0/df0))+m0;
        double y0=n0;
        double y1=((n1-n0)*(d0/df0))+n0;
        
        double dx0=x0-x1;
        double dy0=y0-y1;
        
        double px0= dy0*Math.sin(Math.PI/2) + x1;
        double py0= -dx0*Math.sin(Math.PI/2) + y1;
        double di0=dis(x1,y1,px0,py0);
        double di00=(d1/di0);
        double sx0=(px0-x1)*di00+x1;
        double sy0=(py0-y1)*di00+y1;

        double dx1=x1-x0;
        double dy1=y1-y0;
        double px1= -dy1*Math.sin(Math.PI/2) + x0;
        double py1= dx1*Math.sin(Math.PI/2) + y0;
        double di1=dis(x0,y0,px1,py1);
        double di01=(d1/di1);
        double sx1=(px1-x0)*di01+x0;
        double sy1=(py1-y0)*di01+y0;
        
        double di_0=dis(x1,y1,sx0,sy0);
        double di_1=dis(x0,y0,x1,y1);
        //System.out.println(di_0+","+di_1);
        String s1=x1+","+y1+","+sx0+","+sy0+","+sx1+","+sy1+","+x0+","+y0;
        return s1;
    }
    
    public int getIndex(int counter){
        int newIdx=-1;
        int i=1;
        while(newIdx==-1 && i<reqCellObjList.size()-1){
            CellObj obj=reqCellObjList.get(i);            
            boolean t= obj.getFilled();
            if(t==false && obj.ignore==false){
                obj.setFilled(true);
                newIdx=obj.getId();
            }else{
                newIdx=-1;
            }
            i++;
        }
        if(newIdx==-1){
            return 0;
        }else{
            return newIdx;
        }
    }
    
    public boolean checkPtInFigProj(String pt, String ln){
        boolean t=false;
        
        double mpx=Double.parseDouble(pt.split(",")[0]);
        double mpy=Double.parseDouble(pt.split(",")[1]);
        
        double x1=Double.parseDouble(ln.split(",")[0]);
        double y1=Double.parseDouble(ln.split(",")[1]);
        double x2=Double.parseDouble(ln.split(",")[2]);
        double y2=Double.parseDouble(ln.split(",")[3]);
        
        double cenX=(x1+x2)/2;
        double cenY=(y1+y2)/2;
        double d0=dis(cenX,cenY,x1,y1);
        
        double ux=(mpx-x1);
        double uy=(mpy-y1);
        double vx=(x2-x1);
        double vy=(y2-y1);
        
        double norm=vx*vx + vy*vy;
        double dotProd=ux*vx + uy*vy;
        double req_x=((vx*dotProd)/norm) + x1;
        double req_y=((vy*dotProd)/norm) + y1;
        double d1=dis(cenX,cenY,req_x,req_y);
        
        double di00=dis(mpx,mpy,req_x,req_y);
        
        if(d1<=d0){
            t=true;
        }

        return t;
    }
    
    public boolean checkPtInFigure(String pt, String ptList){
 double x1=Double.parseDouble(pt.split(",")[0]);
        double y1=Double.parseDouble(pt.split(",")[1]);
        ArrayList<String> GLOBAL_figPtList=new ArrayList<String>();
        GLOBAL_figPtList.clear();
        String[] figArr=ptList.split(",");
        for(int i=0; i<figArr.length; i++){
            double p1=Double.parseDouble(figArr[i]);
            double q1=Double.parseDouble(figArr[i+1]);     
            GLOBAL_figPtList.add(p1+","+q1);
            i++;
        }
        GLOBAL_figPtList.add(GLOBAL_figPtList.get(0));
        double r=8;
        double x2=x1+2000;
        double y2=y1+20;
        double m1=(y2-y1)/(x2-x1);
        double c1=y1-(m1*x1);
        int sum=0;
        for(int j=0; j<GLOBAL_figPtList.size()-1; j++){
            double x3=Double.parseDouble(GLOBAL_figPtList.get(j).split(",")[0]);
            double y3=Double.parseDouble(GLOBAL_figPtList.get(j).split(",")[1]);
            double x4=Double.parseDouble(GLOBAL_figPtList.get(j+1).split(",")[0]);
            double y4=Double.parseDouble(GLOBAL_figPtList.get(j+1).split(",")[1]);
            double cenX=(x3+x4)/2;
            double cenY=(y3+y4)/2;
            if(x4-x3==0){
                x3+=0.000001;
            }
            double m2=(y4-y3)/(x4-x3);
            double c2=y4-(m2*x4);
            double a=(c2-c1)/(m1-m2);
            double b=m2*a+c2;
            double d1=dis(a,b,cenX,cenY);
            double d2=dis(x3,y3,cenX,cenY);
            if(a>x1 && d1<d2){
                sum++;
            }else{
                // NOT REQUIRED
            }
        }
        boolean t;
        if(sum%2 != 0 && sum>0){
            t=true;
        }else{
            t=false;
        }
        return t;
    }
    
    public boolean checkArea(String s, String id_str_, double tolerance){
        boolean t=false;
        String ln=s;
        double x0=Double.parseDouble(s.split(",")[0]);
        double y0=Double.parseDouble(s.split(",")[1]);
        double x1=Double.parseDouble(s.split(",")[2]);
        double y1=Double.parseDouble(s.split(",")[3]);
        double x2=Double.parseDouble(s.split(",")[4]);
        double y2=Double.parseDouble(s.split(",")[5]);
        double d0=dis(x0,y0,x1,y1);
        double d1=dis(x1,y1,x2,y2);
        double ar0=d0*d1;                
        String[] id_arr=id_str_.split(",");
        double ar1=0;
        for(int i=0; i<id_arr.length; i++){
            CellObj obj=reqCellObjList.get(Integer.parseInt(id_arr[i]));
            double ar_i=obj.getNumericalArea();
            ar1+=ar_i;
        }
        double v=Math.abs((ar1-ar0)/ar0);
        if(v<tolerance){
            t=true;
        }else{
            t=false;
        }
        //System.out.println(ar0 +","+ ar1+ " : "+ t +" % "+v);
        return t;
    }
    
    public double dis(double x0, double y0, double x1, double y1){
        double d=Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
        return d;
    }
}
