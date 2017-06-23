package org.pw.GA.func;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.pw.dto.CellObj;
import org.pw.dto.FuncNodeObj;

public class GAFuncPanel extends JPanel implements ActionListener{
    
    AllocateFunc allocatePeripheralFunctions;
    
    ArrayList<FuncNodeObj>funcNodeObjList;
    
    ArrayList<CellObj>cellObjList;
    
    ArrayList<CellObj>interPeriCellObjList;
    ArrayList<String>interPeriRectList;
    
    ArrayList<CellObj>interGenCellObjList;
    ArrayList<String>interGenRectList;
    
    ArrayList<CellObj>periCellObjList;
    ArrayList<String>periCellObjStrList;
    ArrayList<String>periRectList;
    
    ArrayList<Integer>remPeriList;
    ArrayList<Double>fitnessList;

    ArrayList<CellObj>genCellObjList;
    ArrayList<String>genRectList;
    
    HashMap<String,Double> mapArea;
    
    double scaleX,scaleY,translateX, translateY;
    
    int entryclicksize=10;
    Timer timer;
    Random rnd;
    
    public GAFuncPanel(ArrayList<FuncNodeObj>graphNodeList, 
            ArrayList<CellObj>GLOBAL_CellObjList){

        timer=new Timer(150,this);
        timer.start();
        rnd=new Random();
        
        funcNodeObjList=new ArrayList<FuncNodeObj>();
        funcNodeObjList.clear();
        funcNodeObjList.addAll(graphNodeList);
        
        cellObjList=new ArrayList<CellObj>();
        cellObjList.addAll(GLOBAL_CellObjList);
        
        periCellObjList=new ArrayList<CellObj>();
        periCellObjStrList=new ArrayList<String>();
        periRectList=new ArrayList<String>();
        remPeriList=new ArrayList<Integer>();
        
        interPeriCellObjList=new ArrayList<CellObj>();
        interPeriRectList=new ArrayList<String>();
        
        interGenCellObjList=new ArrayList<CellObj>();
        interGenRectList=new ArrayList<String>();
        
        genCellObjList=new ArrayList<CellObj>();
        genRectList=new ArrayList<String>();

        fitnessList=new ArrayList<Double>();       

        scaleX=1;
        scaleY=1;
        translateX=0;
        translateY=0;
                
    }
    
    public void Update(double sx, double sy, double tx, double ty){
        scaleX=sx;
        scaleY=sy;
        translateX=tx;
        translateY=ty;
    }
    
    public void getPeriCells(String s){
        remPeriList.clear();
        periCellObjList.clear();
        
        if(s.isEmpty() || s==""){
            // NOTHING IS ADDED
        }else{
            String[] s0Arr=s.split(",");
            for(int i=0; i<s0Arr.length; i++){
                String[] s1Arr=s0Arr[i].split("-");
                int a=Integer.parseInt(s0Arr[i].split("-")[0]);
                int b=Integer.parseInt(s0Arr[i].split("-")[1]);
                for(int j=a; j<=b; j++){
                    remPeriList.add(j);
                }
            }     
        }
        int idx=1;
        for(int i=0; i<cellObjList.size(); i++){
            CellObj c=cellObjList.get(i);
            int id=c.getId();
            String zone=c.getZone();
            if(zone.equals("peripheral")){
                c.setId(idx);
                periCellObjList.add(c);
            }
        }
        periCellObjList.trimToSize();        
        //  CLEAR ALL CELLS
        for(int i=0; i<cellObjList.size(); i++){
            CellObj c=cellObjList.get(i);
            c.setFilled(false);
        }
        
    }

    public void allocatePeriCells(){
        periRectList.clear();        
        /*
        *   IN ORDER TO REMOVE REQUIRED CELLS
        *   JUST SET THEM TO TRUE (IT IS ALREADY FILLED)
        */
        for(int i=0; i<periCellObjList.size(); i++){
            CellObj c=periCellObjList.get(i);
            int id=c.getId();
            for(int j=0; j<remPeriList.size(); j++){
                if(id==remPeriList.get(j)){
                    c.setFilled(true);
                }
            }
        }

        int k=0;
        for(int i=0; i<periCellObjList.size(); i++){
            CellObj c=periCellObjList.get(i);
            if(c.getFilled()==false){
                c.setId(k);
                k++;
            }
        }
        double ar_req=150;
        int num_req=64;
        allocatePeripheralFunctions=new AllocateFunc(num_req, ar_req, periCellObjList);
        allocatePeripheralFunctions.periCellObjStrList.clear();
        allocatePeripheralFunctions.allocateCells(0,"peripheral");
        periRectList.addAll(allocatePeripheralFunctions.newRectStr);
        
    }
    
    public void getInterPeriCells(){
        interPeriCellObjList.clear();
        interPeriRectList.clear();  
        int idx=0;
        for(int i=0; i<cellObjList.size(); i++){
            CellObj obj=cellObjList.get(i);
            if(obj.getZone().equals("inter_peri")){
                interPeriCellObjList.add(obj);
                obj.setFilled(false);
                idx++;
            }
        }
        /*
        *   ALSO ADD CELLS NOT USED IN PERIPHERAL ZONE
        */
        /*
        for(int i=0; i<periCellObjList.size(); i++){
            CellObj obj=periCellObjList.get(i);
            if(obj.getFilled()==false){
                obj.setId(idx);
                interPeriCellObjList.add(obj);
                idx++;
            }
        }
        */
    }
   
    public void allocateInterPeriCells(){
        double ar_req=40;
        int num_req=100;        
        allocatePeripheralFunctions=new AllocateFunc(num_req, ar_req, 
                interPeriCellObjList);
        
        allocatePeripheralFunctions.periCellObjStrList.clear();
        allocatePeripheralFunctions.allocateCells(0,"inter-peri");
        interPeriRectList.addAll(allocatePeripheralFunctions.newRectStr);
               
    }
    
    public void getGeneralCells(){
        genCellObjList.clear();
        int idx=0;
        for(int i=0; i<cellObjList.size(); i++){
            CellObj obj=cellObjList.get(i);
            if(obj.getZone().equals("general")){
                obj.setFilled(false);
                obj.setId(idx);
                genCellObjList.add(obj);
                idx++;
            }
        }
    }
    
    public void allocateGenCells(){
        double ar_req=120;
        int num_req=20;
        ArrayList<String>interStrList=new ArrayList<String>();
        interStrList.clear();
        
        allocatePeripheralFunctions=new AllocateFunc(num_req, ar_req, 
                genCellObjList);
        
        allocatePeripheralFunctions.periCellObjStrList.clear();
        allocatePeripheralFunctions.allocateCells(0,"general");
        
        genRectList.addAll(allocatePeripheralFunctions.newRectStr);
        
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d= (Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255));
        g2d.fill(new Rectangle2D.Double(0,0,1000,1000));
        g2d.scale(scaleX, scaleY);
        g2d.translate(translateX, translateY);
        /*
        *   PLOT ALL CELLS
        */
        g2d.setColor(new Color(0,0,0));
        for(int i=0; i<cellObjList.size(); i++){
            CellObj c=cellObjList.get(i);
            GeneralPath path=c.genPath();
            String zone=c.getZone();
            g2d.setStroke(new BasicStroke(0.25F));
            g2d.setColor(new Color(0,0,0,50));
            g2d.draw(path);
        }
        
        for(int i=0; i<periRectList.size(); i++){
            double x0=Double.parseDouble(periRectList.get(i).split(",")[0]);
            double y0=Double.parseDouble(periRectList.get(i).split(",")[1]);
            double x1=Double.parseDouble(periRectList.get(i).split(",")[2]);
            double y1=Double.parseDouble(periRectList.get(i).split(",")[3]);
            double x2=Double.parseDouble(periRectList.get(i).split(",")[4]);
            double y2=Double.parseDouble(periRectList.get(i).split(",")[5]);
            double x3=Double.parseDouble(periRectList.get(i).split(",")[6]);
            double y3=Double.parseDouble(periRectList.get(i).split(",")[7]);

            g2d.setColor(new Color(255,0,0));
            g2d.setStroke(new BasicStroke(1.0F));
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
            g2d.draw(new Line2D.Double(x1,y1,x2,y2));
            g2d.draw(new Line2D.Double(x2,y2,x3,y3));
            g2d.draw(new Line2D.Double(x3,y3,x0,y0));
        }
        
        for(int i=0; i<interPeriRectList.size(); i++){
            double x0=Double.parseDouble(interPeriRectList.get(i).split(",")[0]);
            double y0=Double.parseDouble(interPeriRectList.get(i).split(",")[1]);
            double x1=Double.parseDouble(interPeriRectList.get(i).split(",")[2]);
            double y1=Double.parseDouble(interPeriRectList.get(i).split(",")[3]);
            double x2=Double.parseDouble(interPeriRectList.get(i).split(",")[4]);
            double y2=Double.parseDouble(interPeriRectList.get(i).split(",")[5]);
            double x3=Double.parseDouble(interPeriRectList.get(i).split(",")[6]);
            double y3=Double.parseDouble(interPeriRectList.get(i).split(",")[7]);

            g2d.setColor(new Color(0,0,255));
            g2d.setStroke(new BasicStroke(1.0F));
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
            g2d.draw(new Line2D.Double(x1,y1,x2,y2));
            g2d.draw(new Line2D.Double(x2,y2,x3,y3));
            g2d.draw(new Line2D.Double(x3,y3,x0,y0));
        }
        
        for(int i=0; i<interGenRectList.size(); i++){
            double x0=Double.parseDouble(interGenRectList.get(i).split(",")[0]);
            double y0=Double.parseDouble(interGenRectList.get(i).split(",")[1]);
            double x1=Double.parseDouble(interGenRectList.get(i).split(",")[2]);
            double y1=Double.parseDouble(interGenRectList.get(i).split(",")[3]);
            double x2=Double.parseDouble(interGenRectList.get(i).split(",")[4]);
            double y2=Double.parseDouble(interGenRectList.get(i).split(",")[5]);
            double x3=Double.parseDouble(interGenRectList.get(i).split(",")[6]);
            double y3=Double.parseDouble(interGenRectList.get(i).split(",")[7]);

            g2d.setColor(new Color(0,0,255));
            g2d.setStroke(new BasicStroke(1.0F));
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
            g2d.draw(new Line2D.Double(x1,y1,x2,y2));
            g2d.draw(new Line2D.Double(x2,y2,x3,y3));
            g2d.draw(new Line2D.Double(x3,y3,x0,y0));
        }
        
        for(int i=0; i<genRectList.size(); i++){
            double x0=Double.parseDouble(genRectList.get(i).split(",")[0]);
            double y0=Double.parseDouble(genRectList.get(i).split(",")[1]);
            double x1=Double.parseDouble(genRectList.get(i).split(",")[2]);
            double y1=Double.parseDouble(genRectList.get(i).split(",")[3]);
            double x2=Double.parseDouble(genRectList.get(i).split(",")[4]);
            double y2=Double.parseDouble(genRectList.get(i).split(",")[5]);
            double x3=Double.parseDouble(genRectList.get(i).split(",")[6]);
            double y3=Double.parseDouble(genRectList.get(i).split(",")[7]);

            g2d.setColor(new Color(0,120,125));
            g2d.setStroke(new BasicStroke(1.0F));
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
            g2d.draw(new Line2D.Double(x1,y1,x2,y2));
            g2d.draw(new Line2D.Double(x2,y2,x3,y3));
            g2d.draw(new Line2D.Double(x3,y3,x0,y0));
        }        
        
        
        for(int i=0; i<cellObjList.size(); i++){
            CellObj c= cellObjList.get(i);
            boolean t=c.getFilled();
            if(t==true){
                GeneralPath p=c.genPath();
                g2d.setColor(new Color(200,255,200,30));
                g2d.fill(p);
                /*
                g2d.setColor(new Color(0,0,0));
                g2d.setFont(new Font("Times New Roman", Font.PLAIN, 3));
                int mpx=(int)c.getMidPt()[0];
                int mpy=(int)c.getMidPt()[1];
                String id=""+c.getId();
                g2d.drawString(id,mpx,mpy);
                */
            }
        }
        
        repaint();
    }
    
    public double dis(double x0, double y0, double x1, double y1){
        double d=Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
        return d;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //allocatePeriCells();
        repaint();
    }

}
