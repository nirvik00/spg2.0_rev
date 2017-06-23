package org.pw.dto;

import java.awt.geom.Area;
import java.util.ArrayList;

public class FuncNodeObj {
	int id;
	String rel_id;
	String name;
	double qnty;
	double ar_each;
	double dim;
	double xPos;
	double yPos;
	int[] rgb;
	String adj_to;
	boolean selected=false;
	double weight;
	String zone;
        String department;
	ArrayList<Area>graphArea;
	
	public FuncNodeObj(double xPos_, double yPos_){
		xPos=xPos_;
		yPos=yPos_;
	}
	
	public FuncNodeObj(int id_, String name_, double qnty_, double ar_each_, double dim_,
			String adj_to_, int[] rgb_, double weight, String zone_, String dept_){
		id=id_;
		name=name_;
		qnty=qnty_;
		ar_each=ar_each_;
		dim=dim_;
		adj_to=adj_to_;      
		rgb=new int[3];
		for(int i=0; i<rgb_.length; i++){
			rgb[i]=rgb_[i];
		}
		weight=dim*qnty;
		rel_id="0";
		graphArea=new ArrayList<Area>();
		zone=zone_;
                String department=dept_;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRel_id() {
		return rel_id;
	}

	public void setRel_id(String rel_id) {
		this.rel_id = rel_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQnty() {
		return qnty;
	}

	public void setQnty(double qnty) {
		this.qnty = qnty;
	}

	public double getAr_each() {
		return ar_each;
	}

	public void setAr_each(double ar_each) {
		this.ar_each = ar_each;
	}

	public double getDim() {
		return dim;
	}

	public void setDim(double dim) {
		this.dim = dim;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public int[] getRgb() {
		return rgb;
	}

	public void setRgb(int[] rgb) {
		this.rgb = rgb;
	}

	public String getAdj_to() {
		return adj_to;
	}

	public void setAdj_to(String adj_to) {
		this.adj_to = adj_to;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public ArrayList<Area> getGraphNodeArea() {
		return graphArea;
	}

	public void setGraphNodeArea(ArrayList<Area> graphicArea) {
		this.graphArea = graphicArea;
	}
        public String getDeparment(){
            return department;
        }
        public void setDepartment(String s){
            department=s;
        }
        public String returnInfo(){
            String s=(id+","+name+","+zone+","+department);
            return s;
        }
	
}
