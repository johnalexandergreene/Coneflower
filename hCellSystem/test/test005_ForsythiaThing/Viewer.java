package org.fleen.coneflower.hCellSystem.test.test005_ForsythiaThing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Viewer extends JPanel{
  
  static final int PADDING=20;
  
  UI ui;
  
  public Viewer(UI ui){
    this.ui=ui;}
  
  public void paint(Graphics g){
    super.paint(g);
    if(ui==null||ui.test==null||ui.test.image==null)return;
    Graphics2D g2=(Graphics2D)g;
    AffineTransform t=getCenterTransform(ui.test.image);
    g2.drawImage(ui.test.image,t,null);}
  
  public int getPaddedWidth(){
    return getWidth()-(2*PADDING);}
  
  public int getPaddedHeight(){
    return getHeight()-(2*PADDING);}
  
  private AffineTransform getCenterTransform(BufferedImage image){
    int 
      iw=image.getWidth(),
      ih=image.getHeight(),
      vw=getWidth(),
      vh=getHeight(),
      tx=(vw-iw)/2,
      ty=(vh-ih)/2;
    AffineTransform t=AffineTransform.getTranslateInstance(tx,ty);
    return t;}
  
}
