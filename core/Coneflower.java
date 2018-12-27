package org.fleen.coneflower.core;

public class Coneflower{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Coneflower(ConeflowerListener listener){
    this.listener=listener;}
  
  /*
   * ################################
   * LISTENER
   * ################################
   */
  
  ConeflowerListener listener;
  
  /*
   * ################################
   * POLYGON TREE
   * ################################
   */
  
  CPolygon root;
  
  /*
   * ################################
   * CELL SYSTEM
   * ################################
   */
  
  public CellSystem cellsystem=new CellSystem();

}
