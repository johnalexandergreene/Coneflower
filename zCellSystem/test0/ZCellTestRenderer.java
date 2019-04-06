package org.fleen.coneflower.zCellSystem.test0;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.fleen.coneflower.zCellSystem.ZCSMT_FPolygonArea;
import org.fleen.coneflower.zCellSystem.ZCSMT_FPolygonBoiledEdge;
import org.fleen.coneflower.zCellSystem.ZCSMappedThing;
import org.fleen.coneflower.zCellSystem.ZCSMappedThingPresence;
import org.fleen.coneflower.zCellSystem.ZCell;
import org.fleen.coneflower.zCellSystem.ZCellSystem;
import org.fleen.forsythia.app.compositionGenerator.Palette;
import org.fleen.forsythia.core.composition.FPolygonSignature;

/*
 * convert each cell to a pixel
 * render to image
 * scale image to fit viewer
 */
public class ZCellTestRenderer{
  
  public static final HashMap<RenderingHints.Key,Object> RENDERING_HINTS=
      new HashMap<RenderingHints.Key,Object>();
    
  static{
    RENDERING_HINTS.put(
      RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT);
    RENDERING_HINTS.put(
      RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_DEFAULT);
    RENDERING_HINTS.put(
      RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    RENDERING_HINTS.put(
      RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY); 
    RENDERING_HINTS.put(
      RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);}
  
  ZCellTest test;
  
  public ZCellTestRenderer(ZCellTest test){
    this.test=test;}
  
  /*
   * render rds to image, cells to pixels
   * then scale to whatever and return that.
   */
  public BufferedImage render(ZCellSystem zcellsystem){
    //TODO we should have a scale param here, and a final transform, 
    //then scale up the rendered image to a bigger image or whatever to fit the viewer
    int 
      w=test.getCellSystemWidth(),
      h=test.getCellSystemHeight();
    //
    BufferedImage image0=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    for(ZCell c:zcellsystem){
      image0.setRGB(c.x,c.y,getColor(c).getRGB());}
    //scale to center and fit in viewer
    int
      imagewidth=image0.getWidth(),
      imageheight=image0.getHeight(),
      viewerwidth=test.getUI().viewer.getPaddedWidth(),
      viewerheight=test.getUI().viewer.getPaddedHeight();
    double 
      imagedimsratio=((double)imagewidth)/((double)imageheight),
      viewerdimsratio=((double)viewerwidth)/((double)viewerheight),
      scale;
    //scale to width
    if(imagedimsratio>viewerdimsratio){
      scale=((double)viewerwidth)/((double)imagewidth);
    //scale to height  
    }else{
      scale=((double)viewerheight)/((double)imageheight);}
    BufferedImage image1=new BufferedImage(
      (int)(scale*imagewidth),
      (int)(scale*imageheight),
      BufferedImage.TYPE_INT_RGB);
    Graphics2D g=image1.createGraphics();
    AffineTransform t=AffineTransform.getScaleInstance(scale,scale);
    g.setRenderingHints(RENDERING_HINTS);
    g.drawImage(image0,t,null);
    //
    return image1;}
  
  /*
   * ################################
   * CELL COLOR LOGIC
   * ################################
   */
  
  private Color getColor(ZCell c){
    int r=0,g=0,b=0;
    Color color;
    for(ZCSMappedThingPresence p:c.presences){
      color=getMappedThingColor(p.thing);
      r+=(int)(color.getRed()*p.intensity);
      g+=(int)(color.getGreen()*p.intensity);
      b+=(int)(color.getBlue()*p.intensity);}
    if(r>255)r=255;
    if(g>255)g=255;
    if(b>255)b=255;
    return new Color(r,g,b);}
  
  //COLOR
  
  Random rnd=new Random();
  Map<FPolygonSignature,Color> colorbythingpolygonsignature=new HashMap<FPolygonSignature,Color>();
  
  private Color getMappedThingColor(ZCSMappedThing thing){
    Color c;
    if(thing instanceof ZCSMT_FPolygonArea){
      FPolygonSignature s=((ZCSMT_FPolygonArea)thing).fpolygon.getSignature();
      c=colorbythingpolygonsignature.get(s);
      if(c==null){
        c=getColorForPolygon();
        colorbythingpolygonsignature.put(s,c);}
    }else if(thing instanceof ZCSMT_FPolygonBoiledEdge){
      FPolygonSignature s=((ZCSMT_FPolygonBoiledEdge)thing).fpolygon.getSignature();
      c=colorbythingpolygonsignature.get(s);
      if(c==null){
        c=getColorForPolygon();
        colorbythingpolygonsignature.put(s,c);}
    }else{//it's a margin thing
      c=getColorForMargin();}
    return c;}
  
  private Color getColorForPolygon(){
    int i=rnd.nextInt(Palette.P_CRUDERAINBOW.length);
    Color c=Palette.P_CRUDERAINBOW[i];
    return c;}
  
  private Color getColorForMargin(){
    return Color.GRAY;}
  
}
