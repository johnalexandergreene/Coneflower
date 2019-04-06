package org.fleen.coneflower.core;

import java.awt.Point;
import java.util.Arrays;

/*
 * a geometry generator
 * forsythia geometry refined for boiling, foaming and whatever
 * forsythia handles the macro structure, point-cell-chains handle the fine structure
 */
public class Coneflower{
  
  public Coneflower(ConeflowerListener listener){
    this.listener=listener;
//    initRootShape();
  }
  
  ConeflowerListener listener;
  
  public void run(){
    for(int i=0;i<100;i++){
      try{
        Thread.sleep(500);
      }catch(Exception x){}
      incrementTime();}}
  
  public int time=0;
  
  void incrementTime(){
    time++;
    process();
    listener.tick();
  }
  
  /*
   * ################################
   * CSHAPE TREE
   * ################################
   */
  
  public CShape root=new CShape();
  
  private void process(){
    
  }
  
  

}
