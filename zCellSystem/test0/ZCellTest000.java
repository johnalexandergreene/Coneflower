package org.fleen.coneflower.zCellSystem.test0;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.coneflower.zCellSystem.ZCSMT_FCompositionMargin;
import org.fleen.coneflower.zCellSystem.ZCSMT_FPolygonArea;
import org.fleen.coneflower.zCellSystem.ZCSMT_FPolygonBoiledEdge;
import org.fleen.coneflower.zCellSystem.ZCSMappedThing;
import org.fleen.coneflower.zCellSystem.ZCellSystem;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.ForsythiaComposition;

public class ZCellTest000 implements ZCellTest{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  ZCellTest000(){
    initUI();
    initRenderer();
    //
    initComposition();
    initCompositionCellSystemTransform();
//    initMappedThingsListForSimpleTest();
    initMappedThingsList();
    //
    initCellSystem0();
    initCellSystem1();}
  
  /*
   * ################################
   * MAIN LOOP
   * ################################
   */
  
  public void run(){
    System.out.println("RUN");
    render(cellsystem0);
    doRule0(1);
    doRule2(1);
    doRule0(1);
    doRule2(1);
    doRule0(1);
    doRule2(1);
    doRule1(1);
    doRule0(1);
    doRule1(1);
    doRule0(1);
    doRule1(1);
    doRule0(1);
    doRule1(1);
    
    
  }
  
  boolean flipflop=true;
  
  private void doRule0(int t){
    Rule rule=new R_FattenBoiledEdge();
    for(int i=0;i<t;i++){
      System.out.println("fatten boiled edge "+i+" / "+t);
      if(flipflop){
        rule.doRule(cellsystem0,cellsystem1);
        render(cellsystem1);
      }else{
        rule.doRule(cellsystem1,cellsystem0);
        render(cellsystem0);}
      flipflop=!flipflop;}}
  
  private void doRule1(int t){
    Rule rule=new R_Smooth();
    for(int i=0;i<t;i++){
      if(flipflop){
        rule.doRule(cellsystem0,cellsystem1);
        render(cellsystem1);
      }else{
        rule.doRule(cellsystem1,cellsystem0);
        render(cellsystem0);}
      flipflop=!flipflop;}}
  
  private void doRule2(int t){
    Rule rule=new R_Blur();
    for(int i=0;i<t;i++){
      System.out.println("blur "+i+" / "+t);
      if(flipflop){
        rule.doRule(cellsystem0,cellsystem1);
        render(cellsystem1);
      }else{
        rule.doRule(cellsystem1,cellsystem0);
        render(cellsystem0);}
      flipflop=!flipflop;}}
  
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
  
  ForsythiaComposition composition;
  
  private void initComposition(){
//    composition=new TestComposition001();
    composition=new TestComposition000();
    
  }
  
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
    Rectangle2D.Double bounds=composition.getRootPolygon().getDPolygon().getBounds();
    double 
      tx=-bounds.x*scale+margin/scale,
      ty=-bounds.y*scale+margin/scale-getCellSystemHeight()/scale;
    compositioncellsystemtransform.translate(tx,ty);}
  
  /*
   * ################################
   * MAPPED THINGS
   * ################################
   */
  
  //this is a nice glowspan, makes for nice transitions
  //we use this value for all mappings in the test
  //we could, of course, use different glowspans for all the mappings if we so chose.
  static final double GLOWSPAN=1.5;
//  static final double GLOWSPAN=3.5;//TEST
  
  List<ZCSMappedThing> mappedthings;
  
  
//  private void initMappedThingsListForSimpleTest(){
//    mappedthings=new ArrayList<ZCSMappedThing>();
//    ZCSMappedThing leaf;
//    for(FPolygon p:composition.getLeafPolygons()){
//      if(p.hasTags("hex")){
//        leaf=new ZCSMappedThing(p,compositioncellsystemtransform,GLOWSPAN,new String[]{"leaf"});
//        mappedthings.add(leaf);}}
//  }
  
  private void initMappedThingsList(){
    mappedthings=new ArrayList<ZCSMappedThing>();
    //ADD MARGIN
    ZCSMT_FCompositionMargin margin=
      new ZCSMT_FCompositionMargin(getCellSystemWidth(),getCellSystemHeight(),composition.getRootPolygon(),
      compositioncellsystemtransform,GLOWSPAN,new String[]{"margin"});
    mappedthings.add(margin);
    //ADD FORSYTHIA COMPOSITION LEAVES
    ZCSMT_FPolygonArea leaf;
    for(FPolygon p:composition.getLeafPolygons()){
      leaf=new ZCSMT_FPolygonArea(p,compositioncellsystemtransform,GLOWSPAN,new String[]{"leaf"});
      mappedthings.add(leaf);}
    //ADD FORSYTHIA COMPOSITON BOILED POLYGON EDGES
    mappedthings.addAll(getBoiledPolygonEdgeThings());
    }
  
  /*
   * TODO
   * a nice symmetricrandom type selection
   */
  private List<ZCSMT_FPolygonBoiledEdge> getBoiledPolygonEdgeThings(){
    Random r=new Random();
    List<ZCSMT_FPolygonBoiledEdge> a=new ArrayList<ZCSMT_FPolygonBoiledEdge>();
    for(FPolygon p:composition.getPolygons())
      if(r.nextDouble()>0.95)
        a.add(new ZCSMT_FPolygonBoiledEdge(p,compositioncellsystemtransform,GLOWSPAN));
    return a;}
  
  /*
   * ################################
   * CELL SYSTEM
   * ################################
   */
  
  ZCellSystem 
    cellsystem0,
    cellsystem1;
 
  public int getCellSystemWidth(){
    Rectangle2D.Double bounds=composition.getRootPolygon().getDPolygon().getBounds();
    return (int)(bounds.width*scale+margin+margin);}
  
  public int getCellSystemHeight(){
    Rectangle2D.Double bounds=composition.getRootPolygon().getDPolygon().getBounds();
    return (int)(bounds.height*scale+margin+margin);}
  
  void initCellSystem0(){
    cellsystem0=new ZCellSystem(getCellSystemWidth(),getCellSystemHeight(),mappedthings);}
  
  void initCellSystem1(){
    cellsystem1=new ZCellSystem(getCellSystemWidth(),getCellSystemHeight());}

  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  ZCellTestRenderer renderer;
  BufferedImage image=null;
  
  private void initRenderer(){
    renderer=new ZCellTestRenderer(this);}
  
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
    ZCellTest000 t=new ZCellTest000();
    t.run();}
  
}
