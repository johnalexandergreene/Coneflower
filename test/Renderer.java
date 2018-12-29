package org.fleen.coneflower.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Renderer{
  
  Renderer(Viewer viewer){
    this.viewer=viewer;
  }
  
  public Viewer viewer;
  
  public BufferedImage image=null;
  
  public void render(){
    if(viewer.ui.test.coneflower==null){
      System.out.println("null coneflower @ renderer");
      return;}
    //
    System.out.println("rendering---");
    int 
      w=viewer.ui.test.coneflower.width,
      h=viewer.ui.test.coneflower.height;
    System.out.println("w="+w);
    System.out.println("h="+h);
    image=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    Graphics2D g=image.createGraphics();
    
    g.setPaint(Color.green);
    g.fillRect(0,0,w,h);
    
  }

}
