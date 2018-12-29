package org.fleen.coneflower.test;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class UI extends JFrame{
  
  public Test test;
  public Viewer viewer;
  
  public UI(Test test){
    this.test=test;
    viewer=new Viewer(this);
    setBounds(100,100,Test.CWIDTH+100,Test.CHEIGHT+200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setContentPane(viewer);
    setVisible(true);}
  
  
  
}
