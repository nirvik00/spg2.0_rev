package org.pw.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pw.dto.CellObj;
import org.pw.dto.FuncNodeObj;
import org.pw.GA.func.GAFuncFrame;
import org.pw.graph.GraphFrame;
import org.pw.compare.*;
import org.pw.utility.CreateDynamoCells;
import org.pw.utility.CreateFuncNodeObj;

public class MainPanel extends JPanel{
    /*
    * USER INTERFACE ELEMENTS
    */
    JLabel jLblPath, jlblGridDim, jLblGenCellPath, jLblInterCellPath, 
            jLblPeriCellPath, jLblCornerCellPath, jLblInterPeriCellPath;
    JTextField jtfPath, jtfGridDimension, jtfGenCellPath, jtfInterCellPath, 
            jtfPeriCellPath, jtfCornerCellPath, jtfInterPeriCellPath;
    JButton jbtnRead, jbtnGraph, jbtnGetFigDXF, jbtnBuildCells, jbtnGetCells;
    /*
    * GLOBAL VARIABLES
    */
    ArrayList<FuncNodeObj> GLOBAL_graphObjList;
    ArrayList<String>GLOBAL_figPtList;
    ArrayList<CellObj>GLOBAL_CellObjList;
    ArrayList<String> genPtList;
    ArrayList<String> interPtList;  
    ArrayList<String> interPeriPtList;  
    ArrayList<String> periPtList;    
    ArrayList<String> cornerPtList; 
    
    double GRIDSPACING=50;
    
    public MainPanel(){
    
    GLOBAL_graphObjList=new ArrayList<FuncNodeObj>();
    GLOBAL_graphObjList.clear();
    
    GLOBAL_figPtList=new ArrayList<String>();

    GLOBAL_CellObjList=new ArrayList<CellObj>();
    GLOBAL_CellObjList.clear();
    
    genPtList=new ArrayList<String>();
    interPtList=new ArrayList<String>();
    interPeriPtList=new ArrayList<String>();
    periPtList=new ArrayList<String>();
    cornerPtList=new ArrayList<String>();
    
    jLblPath=new JLabel("PATH of EXCEL FILE : ");
    jLblPath.setBounds(30,10,350,50);        

    jtfPath=new JTextField("C:\\nir_dev\\site_geometry\\cell_exchange\\programDocSample.xlsx");
    jtfPath.setBounds(30,70,350,50);        

    jbtnRead=new JButton("1. READ EXCEL FILE");
    jbtnRead.setBounds(30,135,150,40);
    
    jbtnGraph=new JButton("*_Graph / Data");
    jbtnGraph.setBounds(230,135,150,40);
    
    jlblGridDim=new JLabel("GRID DIMENSION");
    jlblGridDim.setBounds(230,215,120,40);

    jtfGridDimension=new JTextField("25");
    jtfGridDimension.setBounds(340,215,35,40);    

    jbtnGetFigDXF=new JButton("1. getDXF_python");
    jbtnGetFigDXF.setBounds(30,215,150,40);      
    
    jLblGenCellPath= new JLabel("General Cells Path :");
    jLblGenCellPath.setBounds(30,300,350,30);
    
    jtfGenCellPath=new JTextField("C:\\nir_dev\\site_geometry\\cell_exchange\\file_gen.dat");
    jtfGenCellPath.setBounds(30,340,350,40);
    
    jLblInterCellPath= new JLabel("Interstitial Cells Path :");
    jLblInterCellPath.setBounds(30,400,350,40);
    
    jtfInterCellPath= new JTextField("C:\\nir_dev\\site_geometry\\cell_exchange\\file_inter.dat");
    jtfInterCellPath.setBounds(30,440,350,40);
    
    jLblPeriCellPath= new JLabel("Peripheral Cells Path :");
    jLblPeriCellPath.setBounds(30,500,350,40);
    
    jtfPeriCellPath= new JTextField("C:\\nir_dev\\site_geometry\\cell_exchange\\file_peri.dat");
    jtfPeriCellPath.setBounds(30,540,350,40);
    
    jLblCornerCellPath= new JLabel("Corner Cells Path :");
    jLblCornerCellPath.setBounds(30,600,350,40);
    
    jtfCornerCellPath= new JTextField("C:\\nir_dev\\site_geometry\\cell_exchange\\file_cor.dat");
    jtfCornerCellPath.setBounds(30,640,350,40);
    
    jLblInterPeriCellPath= new JLabel("Interstitial Peripheral Cells Path :");
    jLblInterPeriCellPath.setBounds(30,700,350,40);
    
    jtfInterPeriCellPath= new JTextField("C:\\nir_dev\\site_geometry\\cell_exchange\\file_peri_inter.dat");
    jtfInterPeriCellPath.setBounds(30,740,350,40);
    
    jbtnGetCells=new JButton("2. GET CELLS");
    jbtnGetCells.setBounds(30,820,150,40); 
    
    jbtnBuildCells=new JButton("2. BUILD");
    jbtnBuildCells.setBounds(230,820,150,40);    
    
    setLayout(null);
    add(jtfPath);
    add(jLblPath);
    add(jbtnRead);
    add(jbtnGraph);
    //add(jlblGridDim);
    //add(jtfGridDimension);
    //add(jbtnGetFigDXF);
    add(jbtnGetCells);
    add(jtfPeriCellPath);
    add(jLblPeriCellPath);
    add(jtfInterCellPath);
    add(jLblInterCellPath);
    add(jtfGenCellPath);
    add(jLblGenCellPath);
    add(jbtnBuildCells);
    add(jLblCornerCellPath);
    add(jtfCornerCellPath);
    add(jLblInterPeriCellPath);
    add(jtfInterPeriCellPath);
    
    /*
    * READ THE EXCEL FILE
    * CREATE 'Graph Object' nodes GLOBAL_graphObjList
    */
    jbtnRead.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            GLOBAL_graphObjList.clear();
            ArrayList<String>dataList=new ArrayList<String>();
            dataList.clear();
            try {
                dataList.clear();
                String filePath=jtfPath.getText();
                FileInputStream fis=new FileInputStream(new File(filePath));
                XSSFWorkbook workbook=new XSSFWorkbook(fis);
                XSSFSheet spreadsheet=workbook.getSheetAt(0);
                Iterator<Row>rowIterator=spreadsheet.iterator();
                while(rowIterator.hasNext()){
                    XSSFRow row=(XSSFRow) rowIterator.next();
                    Iterator<Cell> cellIterator=row.cellIterator();
                    String s="";
                    while(cellIterator.hasNext()){
                        Cell cell=cellIterator.next();
                        s+=String.valueOf(cell)+";";
                    }
                    dataList.add(s);
                    //System.out.println(s);
                }
                CreateFuncNodeObj cnodes=new CreateFuncNodeObj(dataList);
                GLOBAL_graphObjList.addAll(cnodes.makeNodes());                  
            } catch (FileNotFoundException ex) {
                System.out.println("file not found");
            } catch (IOException ex) {
                System.out.println("problem with \"class not found \" ...");
            }
        }
    });		
    /*
    *   DISPLAY THE GRAPH AND CONNECTIONS BETWEEN THEM
    */
    jbtnGraph.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            GraphFrame frm=new GraphFrame(GLOBAL_graphObjList);
            frm.setVisible(true);
        }
    });    
    /*
    *   CELL EXCHANGE
    */    
    jbtnGetCells.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            /*
            *    general cells in middle
            */
            genPtList.clear();
            String genCellsFile=jtfGenCellPath.getText();
            String genPtStr="";
            try{
                 BufferedReader br=new BufferedReader(new FileReader(genCellsFile));
                 String line;
                 while((line=br.readLine())!=null){
                     genPtStr+=line;
                 }
                 br.close();
            }catch(Exception exc){
            }
            String[] split_genCells=genPtStr.split("o");
            for (int i=0; i<split_genCells.length; i++){
                genPtList.add(split_genCells[i]);
            }
            /*
            *    inter cells in middle
            */
            interPtList.clear();
            String interCellsFile=jtfInterCellPath.getText();
            String interPtStr="";
            try{
                BufferedReader br=new BufferedReader(new FileReader(interCellsFile));
                String line;
                while((line=br.readLine())!=null){
                    interPtStr+=line;
                }
                br.close();
            }catch(Exception exc){
            }
            String[] split_interCells=interPtStr.split("o");
            for (int i=0; i<split_interCells.length; i++){
                interPtList.add(split_interCells[i]);
            }
            /*
            *    peripheral cells
            */
            periPtList.clear();
            String periCellsFile=jtfPeriCellPath.getText();
            String periPtStr="";
            try{
                BufferedReader br=new BufferedReader(new FileReader(periCellsFile));
                String line;
                while((line=br.readLine())!=null){
                    periPtStr+=line;
                }
                br.close();
            }catch(Exception exc){
            }
            String[] split_periCells=periPtStr.split("o");
            for (int i=0; i<split_periCells.length; i++){
                periPtList.add(split_periCells[i]);
            }
            /*
            *    corner cells
            */
            cornerPtList.clear();
            String cornerCellsFile=jtfCornerCellPath.getText();
            String cornerPtStr="";
            try{
                BufferedReader br=new BufferedReader(new FileReader(cornerCellsFile));
                String line;
                while((line=br.readLine())!=null){
                    cornerPtStr+=line;
                }
                br.close();
            }catch(Exception exc){
            }
            String[] split_cornerCells=cornerPtStr.split("o");
            for (int i=0; i<split_cornerCells.length; i++){
                cornerPtList.add(split_cornerCells[i]);
            }
               
            /*
            *    interstitial peripheral cells
            */
            interPeriPtList.clear();
            String interPeriCellsFile=jtfInterPeriCellPath.getText();
            String interPeriPtStr="";
            try{
                BufferedReader br=new BufferedReader(new FileReader(interPeriCellsFile));
                String line;
                while((line=br.readLine())!=null){
                    interPeriPtStr+=line;
                }
                br.close();
            }catch(Exception exc){
            }
            String[] split_interPeriCells=interPeriPtStr.split("o");
            for (int i=0; i<split_interPeriCells.length; i++){
                interPeriPtList.add(split_interPeriCells[i]);
            }
            /*
            *   NOW CREATE THE CELLS FROM DYNAMO
            */
            CreateDynamoCells cells= new CreateDynamoCells(genPtList, interPtList,
                    periPtList, cornerPtList, interPeriPtList);
            GLOBAL_CellObjList.clear();
            GLOBAL_CellObjList.addAll(cells.getCellList());
        }
    });
    
    jbtnBuildCells.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){   
            GAFuncFrame gafuncfrm=new GAFuncFrame(GLOBAL_graphObjList, GLOBAL_CellObjList);
            gafuncfrm.setVisible(true);
        }    
    });    
}
    
    public double dis(double a, double b){
        double d=Math.sqrt(a*a + b*b);
        return d;
    }
}
