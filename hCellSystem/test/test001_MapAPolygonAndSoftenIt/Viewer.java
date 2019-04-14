package org.fleen.coneflower.hCellSystem.test.test001_MapAPolygonAndSoftenIt;

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
    AffineTransform t=getScaleTransform(ui.test.image);
    t.concatenate(getCenterTransform(ui.test.image,t));
    g2.drawImage(ui.test.image,t,null);}
  
  private AffineTransform getScaleTransform(BufferedImage image){
    int
      imagewidth=image.getWidth(),
      imageheight=image.getHeight(),
      paddedviewwidth=getWidth()-(2*PADDING),
      paddedviewheight=getHeight()-(2*PADDING);
    double 
      imagedimsratio=((double)imagewidth)/((double)imageheight),
      viewerdimsratio=((double)paddedviewwidth)/((double)paddedviewheight),
      scale;
    //scale to width
    if(imagedimsratio>viewerdimsratio){
      scale=((double)paddedviewwidth)/((double)imagewidth);
    //scale to height  
    }else{
      scale=((double)paddedviewheight)/((double)imageheight);}
    AffineTransform t=AffineTransform.getScaleInstance(scale,scale);
    return t;}
  
  private AffineTransform getCenterTransform(BufferedImage image,AffineTransform tscale){
    double s=tscale.getScaleX();
    int 
      iw=(int)(image.getWidth()*s),
      ih=(int)(image.getHeight()*s),
      vw=getWidth(),
      vh=getHeight(),
      tx=(vw-iw)/2,
      ty=(vh-ih)/2;
    AffineTransform t=AffineTransform.getTranslateInstance(tx/s,ty/s);
    return t;}
  
}
