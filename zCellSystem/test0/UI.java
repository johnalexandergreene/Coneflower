package org.fleen.coneflower.zCellSystem.test0;

import javax.swing.JFrame;

public class UI extends JFrame {

	private static final long serialVersionUID = -2749846443106819716L;
	
	ZCellTest test;
  public Viewer viewer;

	public UI(ZCellTest test){
	  this.test=test;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350,50,1500,900);
		viewer=new Viewer(this);
		setContentPane(viewer);
		setVisible(true);}
	
}
