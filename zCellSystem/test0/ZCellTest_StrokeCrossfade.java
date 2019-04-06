package org.fleen.coneflower.zCellSystem.test0;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.fleen.coneflower.zCellSystem.ZCSMT_FCompositionMargin;
import org.fleen.coneflower.zCellSystem.ZCSMT_FPolygonBoiledEdge;
import org.fleen.coneflower.zCellSystem.ZCSMappedThing;
import org.fleen.coneflower.zCellSystem.ZCellSystem;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.ForsythiaComposition;

public class ZCellTest_StrokeCrossfade implements ZCellTest{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  ZCellTest_StrokeCrossfade(){
    initUI();
    initRenderer();
    //
    initCompositions();
    initCompositionCellSystemTransform();
    doMappedThingsLists();
    //
    initCellSystems();}
  
  /*
   * ################################
   * MAIN LOOP
   * ################################
   */
  
  public void run(){
    System.out.println("RUN");
    doRule_SimpleCrossfade(100);
    
    
  }
  
  boolean flipflop=true;
  
//  private void doRule_SimpleCrossfade(int t){
//    R_SimpleCrossfade r=new R_SimpleCrossfade();
//    r.c0cellsystem=c0cellsystem;
//    r.c1cellsystem=c1cellsystem;
//    for(int i=0;i<t;i++){
//      System.out.println("rule0 "+i+" / "+t);
//      if(flipflop){
//        render(workingcellsystem0);
//      }else{
//        render(workingcellsystem1);}
//      
//      
//      if(flipflop){
//        r.doRule(workingcellsystem0,workingcellsystem1);
//      }else{
//        r.doRule(workingcellsystem1,workingcellsystem0);}
//      flipflop=!flipflop;
//      try{
//        Thread.sleep(1000);
//      }catch(Exception x){
//        x.printStackTrace();}}}
  
  private void doRule_SimpleCrossfade(int t){
    R_SimpleCrossfade r=new R_SimpleCrossfade();
    r.c0cellsystem=c0cellsystem;
    r.c1cellsystem=c1cellsystem;
    for(int i=0;i<t;i++){
      System.out.println("tick="+i);
      render(workingcellsystem0);
      r.setTick(i);
      r.doRule(workingcellsystem0,null);
//      try{
//        Thread.sleep(1000);
//      }catch(Exception x){
//        x.printStackTrace();}
      
    }}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  UI ui;
  
  private void initUI(){
    ui=new UI(this);}
  
  public UI getUI(){
    return ui;}
  
  /*
   * ################################
   * COMPOSITION
   * ################################
   */
  
  ForsythiaComposition composition0,composition1;
  
  private void initCompositions(){
    composition0=new TestComposition001();
    composition1=new TestComposition001();}
  
  /*
   * ################################
   * COMPOSITION CELLSYSTEM TRANSFORM
   * scale up the composition because, dimensionally, it's pretty small
   * translate it to put the left and top edges at 0, + margin
   * ################################
   */
  
  int margin=8;
  double scale=222;
  AffineTransform compositioncellsystemtransform;
  
  private void initCompositionCellSystemTransform(){
    //note that we flip the y to convert cartesian coors to array coors
    compositioncellsystemtransform=AffineTransform.getScaleInstance(scale,-scale);
    //
    Rectangle2D.Double bounds=composition0.getRootPolygon().getDPolygon().getBounds();
    double 
      tx=-bounds.x*scale+margin/scale,
      ty=-bounds.y*scale+margin/scale-getCellSystemHeight()/scale;
    compositioncellsystemtransform.translate(tx,ty);}
  
  /*
   * ################################
   * MAPPED THINGS
   * ################################
   */
  
  static final double GLOWSPAN=1.5;
  
  List<ZCSMappedThing> c0mappedthings,c1mappedthings;
  
  private void doMappedThingsLists(){
    c0mappedthings=getMappedThings(composition0);
    c1mappedthings=getMappedThings(composition1);}
  
  private List<ZCSMappedThing> getMappedThings(ForsythiaComposition c){
    List<ZCSMappedThing> a=new ArrayList<ZCSMappedThing>();
    for(FPolygon p:c.getLeafPolygons())
      a.add(new ZCSMT_FPolygonBoiledEdge(p,compositioncellsystemtransform,GLOWSPAN));
    return a;}
  
  /*
   * ################################
   * CELL SYSTEM
   * ################################
   */
  
  ZCellSystem
    c0cellsystem,
    c1cellsystem,
    workingcellsystem0,
    workingcellsystem1;
 
  public int getCellSystemWidth(){
    Rectangle2D.Double bounds=composition0.getRootPolygon().getDPolygon().getBounds();
    return (int)(bounds.width*scale+margin+margin);}
  
  public int getCellSystemHeight(){
    Rectangle2D.Double bounds=composition0.getRootPolygon().getDPolygon().getBounds();
    return (int)(bounds.height*scale+margin+margin);}
  
  void initCellSystems(){
    c0cellsystem=new ZCellSystem(getCellSystemWidth(),getCellSystemHeight(),c0mappedthings);
    c1cellsystem=new ZCellSystem(getCellSystemWidth(),getCellSystemHeight(),c1mappedthings);
    workingcellsystem0=new ZCellSystem(getCellSystemWidth(),getCellSystemHeight());
    workingcellsystem1=new ZCellSystem(getCellSystemWidth(),getCellSystemHeight());}
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  ZCellTestRenderer2 renderer;
  BufferedImage image=null;
  
  private void initRenderer(){
    renderer=new ZCellTestRenderer2(this);}
  
  private void render(ZCellSystem zcs){
    image=renderer.render(zcs);
    ui.repaint();}
  
  public BufferedImage getImage(){
    return image;}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    ZCellTest_StrokeCrossfade t=new ZCellTest_StrokeCrossfade();
    t.run();}
  
}
