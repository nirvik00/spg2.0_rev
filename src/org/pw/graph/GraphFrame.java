package org.pw.graph;

import java.util.ArrayList;
import javax.swing.JFrame;
import org.pw.dto.FuncNodeObj;

public class GraphFrame extends JFrame{
    
    GraphPanel pnl;
    int WIDTH=500;
    int HEIGHT=500;    
    
    public GraphFrame(ArrayList<FuncNodeObj>graphObjList_){
        setLocation(500,0);
        setSize(WIDTH, HEIGHT);
        setTitle("new graph frame");
        
        ArrayList<FuncNodeObj>temp=new ArrayList<FuncNodeObj>();
        temp.clear();
        temp.addAll(setCircle(graphObjList_));
        pnl= new GraphPanel(temp);

        add(pnl);        
    }
    public ArrayList<FuncNodeObj> setCircle(ArrayList<FuncNodeObj>graphObjList_){
        ArrayList<FuncNodeObj>temp =new ArrayList<FuncNodeObj>();
        temp.clear();
        temp.addAll(graphObjList_);
        int n=temp.size();
        int ang=(int) 360/n;
        for(int i=0; i<temp.size(); i++){
            FuncNodeObj objA=temp.get(i);
            //center point
            double x=WIDTH/2;
            double y=HEIGHT/2;
            //radius 
            double r=(x+y)/3-50;
            //increment in angles
            double theta=ang*(i+1);
            //set coordinates
            double dx=x+r*Math.cos(Math.toRadians(theta));
            double dy=y+r*Math.sin(Math.toRadians(theta));
            objA.setxPos(dx);
            objA.setyPos(dy);
        }
        
        return temp;
    }
}
