package org.fleen.coneflower.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Viewer extends JPanel{
  
  public Viewer(UI ui){
    this.ui=ui;}
  
  UI ui;
  
  public void paint(Graphics g){
    Graphics2D g2=(Graphics2D)g;
    if(ui.test.image==null)return;
    g2.drawImage(ui.test.image,null,null);
  }

}
