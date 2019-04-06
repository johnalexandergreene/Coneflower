package org.fleen.coneflower.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.fleen.coneflower.core.CCell;
import org.fleen.geom_2D.DPolygon;

/*
 * render the fpolygon AND its cellprint
 */
public class Renderer{
  
  static final double SCALE=500;
  
  static final int MARGIN=50;
  
  static final Color BACKGROUNDCOLOR=Color.gray;
  
  Renderer(Viewer viewer){
    this.viewer=viewer;
  }
  
  public Viewer viewer;
  
  public BufferedImage image=null;
  
  public void render(){
    if(viewer.ui.test.coneflower==null){
      System.out.println("null coneflower @ renderer");
      return;}
    //get dims of scaled image
    DPolygon p=viewer.ui.test.coneflower.root.polygon;
    Rectangle2D.Double b=p.getBounds();
    double 
      xmin=b.getMinX()*SCALE,
      xmax=b.getMaxX()*SCALE,
      ymin=b.getMinY()*SCALE,
      ymax=b.getMaxY()*SCALE,
      imagewidth=xmax-xmin+2*MARGIN,
      imageheight=ymax-ymin+2*MARGIN,
      imageoffsetx=-xmin+MARGIN,
      imageoffsety=-ymin+MARGIN;
    //
    System.out.println("rendering---");
    System.out.println("w="+imagewidth);
    System.out.println("h="+imageheight);
    image=new BufferedImage((int)imagewidth,(int)imageheight,BufferedImage.TYPE_INT_ARGB);
    Graphics2D g=image.createGraphics();
    g.setPaint(BACKGROUNDCOLOR);
    g.fillRect(0,0,(int)imagewidth,(int)imageheight);

    //chain transform
    AffineTransform tchain=new AffineTransform();
    tchain.translate(imageoffsetx,imageoffsety);
    //chain
    g.setTransform(tchain);
    g.setPaint(new Color(0,255,0,64));
    Rectangle2D.Double square;
    System.out.println("chain:"+viewer.ui.test.coneflower.root.chain.size());
    for(CCell c:viewer.ui.test.coneflower.root.chain){
      square=new Rectangle2D.Double(c.x-3,c.y-3,6,6);
      g.fill(square);}
    
    //polygon transform
    AffineTransform tpolygon=new AffineTransform();
    tpolygon.translate(imageoffsetx,imageoffsety);
    tpolygon.scale(SCALE,SCALE);
    //polygon
    g.setTransform(tpolygon);
    g.setPaint(new Color(255,0,0,128));
    g.setStroke(new BasicStroke((float)(2.0/SCALE)));
    g.draw(p.getPath2D());
    
  }

}
