package org.pw.main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame{
	
	MainPanel pnl;
	
	MainFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(450,1000);
		setLocation(0,0);
		setTitle("test frame");
		setVisible(true);
		
		pnl=new MainPanel();
		add(pnl);
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new MainFrame();
			}
		});
	}

}
