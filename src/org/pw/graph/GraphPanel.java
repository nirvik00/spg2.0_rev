package org.pw.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.pw.dto.FuncNodeObj;

public class GraphPanel extends JPanel{
    ArrayList<FuncNodeObj>graphObjList;
    double GLOBAL_X,GLOBAL_Y;
    public GraphPanel(ArrayList<FuncNodeObj>graphObjList_){
        graphObjList=new ArrayList<FuncNodeObj>();
        graphObjList.clear();
        graphObjList.addAll(graphObjList_);
    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d=(Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255));
        g2d.fill(new Rectangle2D.Double(0,0,500,500));
        g2d.setColor(new Color(0,0,0));
        for(int i=0; i<graphObjList.size(); i++){
            FuncNodeObj objA=graphObjList.get(i);
            int idA=objA.getId();
            double x=objA.getxPos();
            double y=objA.getyPos();
            int[] rgb=objA.getRgb();
            g2d.setColor(new Color(rgb[0],rgb[1],rgb[2]));
            int a=30;
            g2d.fill(new Ellipse2D.Double(x-a/2,y-a/2,a,a));
            String[] adj=objA.getAdj_to().split(",");
            int[] adj_arr=new int[adj.length];
            for(int j=0;j<adj.length; j++){
                adj_arr[j]=Integer.parseInt(adj[j]);
            }
            g2d.setColor(new Color(0,0,0));
            for(int j=0; j<graphObjList.size(); j++){
                FuncNodeObj objB=graphObjList.get(j);
                int idB=objB.getId();
                for(int k=0; k<adj_arr.length; k++){
                    if(idB==adj_arr[k]){
                        double px=objB.getxPos();
                        double py=objB.getyPos();
                        g2d.draw(new Line2D.Double(px,py,x,y));
                    }
                }                
            }
            double ax=20;
            g2d.setColor(new Color(255,255,255));
            g2d.fill(new Rectangle2D.Double(x-ax/2,y-ax/2,ax,ax));
            g2d.setColor(new Color(0,0,0));
            g2d.drawString("#"+idA, (int)(x-ax/2), (int)(y+5) );
        }       
        for(int i=0; i<graphObjList.size(); i++){
            FuncNodeObj objA=graphObjList.get(i);
            int idA=objA.getId();
            double x=objA.getxPos();
            double y=objA.getyPos();
            int[] rgb=objA.getRgb();
            g2d.setColor(new Color(rgb[0],rgb[1],rgb[2]));
            int a=30;
            g2d.fill(new Ellipse2D.Double(x-a/2,y-a/2,a,a));
            double ax=20;
            g2d.setColor(new Color(255,255,255));
            g2d.fill(new Rectangle2D.Double(x-ax/2,y-ax/2,ax,ax));
            g2d.setColor(new Color(0,0,0));
            g2d.drawString("#"+idA, (int)(x-ax/2), (int)(y+5) );
        }       
        //g2d.fill(new Ellipse2D.Double(GLOBAL_X, GLOBAL_Y, 5, 5));
        repaint();
    }
}
