package org.fleen.coneflower.hCellSystem.test.test002_MapAPolygonAndSoftenIt_FineMap;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.fleen.coneflower.hCellSystem.HCSMappedThing;
import org.fleen.coneflower.hCellSystem.HCellSystem;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;

public class Test{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
 
  Test(){
    initUI();
    initRenderer();
    //
    initPolygon();
    initPolygonTransform();
    initMappedThingsList();
    //
    initCellSystem();}
  
  /*
   * ################################
   * POLYGON
   * ################################
   */
  
  public DPolygon polygon;
  
  private void initPolygon(){
    polygon=new DPolygon(new DPoint(0,0),new DPoint(0,2),new DPoint(3,2),new DPoint(3,0));}
  
  /*
   * ################################
   * CELL SYSTEM
   * ################################
   */
  
  HCellSystem cellsystem;
  
  void initCellSystem(){
    Rectangle2D.Double bounds=polygon.getBounds();
    int 
      w=(int)(bounds.width*scale+margin+margin),
      h=(int)(bounds.height*scale+margin+margin);
    cellsystem=new HCellSystem(w,h,mappedthings);}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  UI ui;
  
  private void initUI(){
    ui=new UI(this);}
  
  /*
   * ################################
   * POLYGON TRANSFORM
   * scale up the polygon a bit
   * translate it so it fits inside a rectangular cell system with a nice margin 
   * ################################
   */
  
  double 
    margin=16,
    scale=200;
  AffineTransform polygontransform;
  
  private void initPolygonTransform(){
    //note that we flip the y to convert cartesian coors to array coors
    polygontransform=AffineTransform.getScaleInstance(scale,-scale);
    //
    Rectangle2D.Double bounds=polygon.getBounds();
    double 
      tx=-bounds.x*scale+margin/scale,
      cellsystemheight=(int)(bounds.height*scale+margin+margin),
      ty=-bounds.y*scale+margin/scale-cellsystemheight/scale;
    polygontransform.translate(tx,ty);}
  
  /*
   * ################################
   * MAPPED THINGS LIST
   * ################################
   */
  
  List<HCSMappedThing> mappedthings;
  
  private void initMappedThingsList(){
    mappedthings=new ArrayList<HCSMappedThing>();
    HCSMappedThing mt_margin=new HCSMappedThing(polygon,polygontransform,new String[]{"margin"});
    mappedthings.add(mt_margin);
    HCSMappedThing mt_polygon=new HCSMappedThing(polygon,polygontransform,new String[]{"polygon"});
    mappedthings.add(mt_polygon);}
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  Renderer renderer;
  BufferedImage image=null;
  
  private void initRenderer(){
    renderer=new Renderer(this);}
  
  private void render(){
    renderer.render();
    ui.repaint();}
  
  /*
   * ################################
   * MAIN LOOP
   * ################################
   */
  
  Process000Test process=new Process000Test();
  
  void run(){
    render();
    for(int i=0;i<22;i++){
      System.out.println("process "+i);
//      try{
//        Thread.sleep(1000);
//      }catch(Exception x){}
      cellsystem=process.process(cellsystem);
      render();}}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    Test t=new Test();
    t.run();}
  
}
