package org.fleen.coneflower.hCellSystem.test.test001_MapAPolygonAndSoftenIt_CoarseMap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.fleen.coneflower.hCellSystem.HCell;
import org.fleen.forsythia.app.compositionGenerator.Palette;

/*
 * convert each cell to a pixel
 * render to image
 * scale image to fit viewer
 */
public class Renderer{
  
  public int cellsize=8;
  
  public static final HashMap<RenderingHints.Key,Object> RENDERING_HINTS=
      new HashMap<RenderingHints.Key,Object>();
    
  static{
    RENDERING_HINTS.put(
      RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    RENDERING_HINTS.put(
      RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_DISABLE);
    RENDERING_HINTS.put(
      RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    RENDERING_HINTS.put(
      RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY); 
    RENDERING_HINTS.put(
      RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);}
  
  Test test;
  
  public Renderer(Test test){
    this.test=test;}
  
  /*
   * render raw and ez. viewer will scale
   */
  public void render(){
    int 
      w=test.cellsystem.getWidth(),
      h=test.cellsystem.getHeight();
    BufferedImage image0=new BufferedImage(w*cellsize,h*cellsize,BufferedImage.TYPE_INT_RGB);
    Graphics2D g0=image0.createGraphics();
    g0.setRenderingHints(RENDERING_HINTS);
    //fill cells
    Color c;
    for(int x=0;x<w;x++){
      for(int y=0;y<h;y++){
        c=getColor(test.cellsystem.getCell(x,y));
        g0.setColor(c);
        g0.fillRect(x*cellsize,y*cellsize,cellsize,cellsize);}}
//    //draw cell edges
//    if(cellsize>1){
//      float strokewidth=0.003f;
//      g0.setColor(Color.black);
//      g0.setStroke(new BasicStroke(strokewidth));
//      Path2D.Double p=new Path2D.Double();
//      for(int x=0;x<w;x++){
//        p.reset();
//        p.moveTo(x*cellsize,0);
//        p.lineTo(x*cellsize,h*cellsize);
//        g0.draw(p);}
//      for(int y=0;y<h;y++){
//        p.reset();
//        p.moveTo(0,y*cellsize);
//        p.lineTo(w*cellsize,y*cellsize);
//        g0.draw(p);}}
    //draw cell edges
    if(cellsize>1){
      g0.setColor(Color.black);
      g0.setStroke(new BasicStroke(0.09f));
      for(int x=0;x<w+1;x++)
        g0.drawLine(x*cellsize,0,x*cellsize,h*cellsize);
      for(int y=0;y<h+1;y++){
        g0.drawLine(0,y*cellsize,w*cellsize,y*cellsize);}}
    //
    test.image=image0;}
  
  /*
   * ################################
   * CELL COLOR LOGIC
   * ################################
   */
  
  private Color getColor(HCell c){
    return getThingColor(c.thing);}
  
  Random rnd=new Random();
  int colorindex=0;
  
  Map<Object,Color> colorbything=new HashMap<Object,Color>();
  
  private Color getThingColor(Object a){
    Color c=colorbything.get(a);
    if(c==null){
      c=Palette.P_CRUDERAINBOW[colorindex%Palette.P_CRUDERAINBOW.length];
      colorindex+=1;
      colorbything.put(a,c);}
    return c;}

}
