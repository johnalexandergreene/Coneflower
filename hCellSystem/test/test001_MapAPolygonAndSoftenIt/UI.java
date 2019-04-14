package org.fleen.coneflower.hCellSystem.test.test001_MapAPolygonAndSoftenIt;

import javax.swing.JFrame;

public class UI extends JFrame {

	private static final long serialVersionUID = -2749846443106819716L;
	
	Test test;
  public Viewer viewer;

	public UI(Test test){
	  this.test=test;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50,50,1600,800);
		viewer=new Viewer(this);
		setContentPane(viewer);
		setVisible(true);}
	
}
