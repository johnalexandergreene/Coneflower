package org.fleen.coneflower.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Viewer extends JPanel{
  
  public Viewer(UI ui){
    this.ui=ui;
    renderer=new Renderer(this);}
  
  public UI ui;
  
  AffineTransform offsetalittle=AffineTransform.getTranslateInstance(50,50);
  
  public void paint(Graphics g){
    super.paint(g);
    Graphics2D g2=(Graphics2D)g;
    if(renderer.image==null)return;
    System.out.println("renderer.image="+renderer.image);
    g2.drawImage(renderer.image,offsetalittle,null);
  }
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  Renderer renderer;

}
