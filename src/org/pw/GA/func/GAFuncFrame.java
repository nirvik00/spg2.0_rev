package org.pw.GA.func;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import org.pw.dto.CellObj;
import org.pw.dto.FuncNodeObj;

public class GAFuncFrame extends JFrame implements ActionListener{
    
    JLabel jlblScale, jlblTranslate , jlblRemPeriIndex, jlblentry, jlblslidersize;
    JTextField jtfScale, jtfTranslate, jtfRemPeriIndex, jtfentry; 
    JButton jbtnTransform, jbtnRemCalcPeri, jbtnEntry;
    JSlider jsliderSize;
    GAFuncPanel pnl;
    
    Timer timer;
    
    public GAFuncFrame(ArrayList<FuncNodeObj>graphNodeList, 
            ArrayList<CellObj>GLOBAL_CellObjList){
    
        setLocation(430,0);
        setSize(1500,1000);
        setTitle("Demonstration of Genetic Algorithm");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        timer=new Timer(150,this);
        timer.start();
        
        jlblScale=new JLabel("Scale");
        jlblScale.setBounds(1025,20,100,40);
        
        jtfScale=new JTextField("3,3"); 
        jtfScale.setBounds(1150,20,100,40);
        
        jlblTranslate= new JLabel("Translate");
        jlblTranslate.setBounds(1025,70,100,40);
        
        jtfTranslate=new JTextField("-20,0");
        jtfTranslate.setBounds(1150,70,100,40);
        
        jbtnTransform=new JButton("Transform");
        jbtnTransform.setBounds(1275,40,175,50);

        jlblRemPeriIndex=new JLabel("Remove Cells from Periphery");
        jlblRemPeriIndex.setBounds(1025,150,175,40);
        
        //jtfRemPeriIndex=new JTextField("42-45,142-149,92-99,192-231");
        jtfRemPeriIndex=new JTextField("");
        jtfRemPeriIndex.setBounds(1025,195,200,30);
        
        jbtnRemCalcPeri=new JButton("Recalculate Periphery");
        jbtnRemCalcPeri.setBounds(1275,175,175,50);
                
        jlblentry=new JLabel("Set Entrance Grid Number : Click size");
        jlblentry.setBounds(1025,250,250,30);
        
        jlblslidersize=new JLabel("0");
        jlblslidersize.setBounds(1225,285,50,30);
        
        
        jbtnEntry=new JButton("Generate Entry");
        jbtnEntry.setBounds(1275,265,175,50);
        
        jsliderSize=new JSlider(1,100,20);
        jsliderSize.setBounds(1025,285,175,30);
        
        pnl=new GAFuncPanel(graphNodeList, GLOBAL_CellObjList);
        pnl.setBounds(0,0,1000,1000);
        
        setLayout(null);
        add(pnl);
        add(jlblScale);
        add(jlblTranslate);
        add(jtfScale);
        add(jtfTranslate);
        add(jbtnTransform);
        add(jlblRemPeriIndex);
        add(jtfRemPeriIndex);
        add(jbtnRemCalcPeri);
        add(jlblentry);
        //add(jtfentry);
        add(jbtnEntry);
        add(jsliderSize);
        add(jlblslidersize);
        
        jbtnTransform.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                double scaleX=Double.parseDouble(jtfScale.getText().split(",")[0]);
                double scaleY=Double.parseDouble(jtfScale.getText().split(",")[1]);
                double translateX=Double.parseDouble(jtfTranslate.getText().split(",")[0]);
                double translateY=Double.parseDouble(jtfTranslate.getText().split(",")[1]);
                pnl.scaleX=scaleX;
                pnl.scaleY=scaleY;
                pnl.translateX=translateX;
                pnl.translateY=translateY;
                pnl.Update(scaleX,scaleY,translateX,translateY);
            }
        });
        jbtnRemCalcPeri.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                constructCells();
            }
        });
        jbtnEntry.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               int sliderVal=jsliderSize.getValue();
               jlblslidersize.setText(""+sliderVal);
           }
        }); 
    }
    
    public void constructCells(){
        String s=jtfRemPeriIndex.getText();
        pnl.remPeriList.clear();
        pnl.genCellObjList.clear();
        pnl.periCellObjList.clear();
        pnl.interPeriCellObjList.clear();
        pnl.genRectList.clear();
        pnl.interGenRectList.clear();
        pnl.interPeriRectList.clear();
        pnl.periRectList.clear();
        pnl.getPeriCells(s);
        
        if(pnl.cellObjList.size()>0){
            //pnl.allocatePeriCells();
        }
        pnl.getInterPeriCells();
        if(pnl.interPeriCellObjList.size()>0){
            pnl.allocateInterPeriCells();
        }
        pnl.getGeneralCells();
        if(pnl.genCellObjList.size()>0){
            //pnl.allocateGenCells();                
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int sliderVal=jsliderSize.getValue();
        jlblslidersize.setText(""+sliderVal);
        repaint();
    }
}
