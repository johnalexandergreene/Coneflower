package org.fleen.coneflower.hCellSystem.test.test005_ForsythiaThing;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.coneflower.hCellSystem.HCSMappedThing;
import org.fleen.coneflower.hCellSystem.HCellSystem;
import org.fleen.coneflower.hCellSystem.R_FattenBoiledEdge;
import org.fleen.coneflower.hCellSystem.R_Smooth;
import org.fleen.coneflower.hCellSystem.Rule;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.ForsythiaComposition;

public class Test{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  /*
   *
   * 
create composition

create list of mapped things 
  (in order of mapping I guess, for example the boiled edges should be mapped after the polygon areas)
  margin
  leaf polygons
  polygons with edges to be boiled
  
create cellsystem : cellsystem0
  new CellSystem(w,h,mappedthings)
  
(maybe clean it. are there dead cells? if so then do something about them)
   
create second, empty cellsystem of same size but nothing mapped : cellsystem1
  cellsystem1=cellsystem0.getBlankCopy();
  
create rulesystem
  new RuleSystem() : rulesystem 

boolean flipflop=true
while(notdone){
  if(flipflip){
    rulesystem.doRules(cellsystem0,cellsystem1)
    render(cellsystem1)
  }else{
    rulesystem.doRules(cellsystem1,cellsystem0)
    render(cellsystem0)}
  flipflop=!flipflop;
   */
  
  Test(){
    initUI();
    initRenderer();
    //
    initComposition();
    initCompositionCellSystemTransform();
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
    render(cellsystem0);
    doRule0(1);
//    doRule1(1);
//    doRule0(1);
//    doRule1(1);
//    doRule0(1);
//    doRule1(1);
//    doRule0(1);
//    doRule1(1);
    
    
  }
  
  boolean flipflop=true;
  
  private void doRule0(int t){
    Rule rule=new R_FattenBoiledEdge();
    for(int i=0;i<t;i++){
      //
      try{
        Thread.sleep(500);
      }catch(Exception x){}
      //
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
      //
      try{
        Thread.sleep(500);
      }catch(Exception x){}
      //
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
  
  /*
   * ################################
   * COMPOSITION
   * ################################
   */
  
  ForsythiaComposition composition;
  
  private void initComposition(){
//    composition=new TestComposition000();
    composition=new FComposition001();
    }
  
  /*
   * ################################
   * COMPOSITION CELLSYSTEM TRANSFORM
   * scale up the composition because, dimensionally, it's pretty small
   * translate it to put the left and top edges at 0, + margin 
   * ################################
   */
  
  int margin=16;
  double scale=99;
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
   * MAPPED THINGS LIST
   * ################################
   */
  
  List<HCSMappedThing> mappedthings;
  
  private void initMappedThingsList(){
    mappedthings=new ArrayList<HCSMappedThing>();
    //
    HCSMappedThing margin=new HCSMappedThing(composition.getRootPolygon(),compositioncellsystemtransform,new String[]{"margin"});
    mappedthings.add(margin);
    //
    HCSMappedThing leaf;
    for(FPolygon p:composition.getLeafPolygons()){
      leaf=new HCSMappedThing(p,compositioncellsystemtransform,new String[]{"leaf"});
      mappedthings.add(leaf);}
    //
    mappedthings.addAll(getBoiledPolygonEdgeThings());}
  
  /*
   * TODO
   * a nice symmetricrandom type selection
   */
  private List<HCSMappedThing> getBoiledPolygonEdgeThings(){
    Random r=new Random();
    List<HCSMappedThing> a=new ArrayList<HCSMappedThing>();
    for(FPolygon p:composition.getPolygons())
      if(r.nextDouble()>0.95)
        a.add(new HCSMappedThing(p,compositioncellsystemtransform,new String[]{"boiled"}));
    return a;}
  
  /*
   * ################################
   * CELL SYSTEM
   * ################################
   */
  
  HCellSystem cellsystem0,cellsystem1;
  
  int getCellSystemWidth(){
    Rectangle2D.Double bounds=composition.getRootPolygon().getDPolygon().getBounds();
    return (int)(bounds.width*scale+margin+margin);}
  
  int getCellSystemHeight(){
    Rectangle2D.Double bounds=composition.getRootPolygon().getDPolygon().getBounds();
    return (int)(bounds.height*scale+margin+margin);}
  
  void initCellSystem0(){
    cellsystem0=new HCellSystem(getCellSystemWidth(),getCellSystemHeight(),mappedthings);}
  
  void initCellSystem1(){
    cellsystem1=new HCellSystem(getCellSystemWidth(),getCellSystemHeight());}
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  Renderer renderer;
  BufferedImage image=null;
  
  private void initRenderer(){
    renderer=new Renderer(this);}
  
  private void render(HCellSystem cs){
    renderer.render(cs);
    ui.repaint();}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    Test t=new Test();
    t.run();}
  
}
