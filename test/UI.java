package org.fleen.coneflower.test;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class UI extends JFrame{
  
  public UI(Test test){
    this.test=test;
    setBounds(100,100,Test.UIWIDTH,Test.UIHEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(new Viewer(this));
    setVisible(true);}
  
  Test test;
  
}
