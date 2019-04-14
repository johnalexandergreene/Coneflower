package org.fleen.coneflower.hCellSystem;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fleen.geom_2D.DPolygon;

/*
 * map the whole RDSystem to a single cell mass with all presences at 1.0
 * then subtract the root mass
 * simple
 */
public class MarginHCells implements HCellMass{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public MarginHCells(int w,int h,DPolygon rootpolygon,AffineTransform rootpolygontransform){
    super();
    width=w;
    height=h;
    this.rootpolygon=rootpolygon;
    this.rootpolygontransform=rootpolygontransform;
    doCells();}
  
  int width,height;
  
  /*
   * ################################
   * POLYGON
   * ################################
   */
  
  AffineTransform rootpolygontransform;
  DPolygon rootpolygon;
  
  /*
   * ################################
   * GLOWSPAN
   * The distance to which a thing's presence bleeds out beyond its edge
   * ################################
   */
  
  double glowspan;
  
  /*
   * ################################
   * CELLS
   * ################################
   */
  
  private List<HCell> cells=new ArrayList<HCell>();
  
  public Iterator<HCell> iterator(){
    return cells.iterator();}
  
  public int getCellCount(){
    return cells.size();}
  
  /*
   * ################################
   * MAP MARGIN
   * make a big rectangle of cells
   * subtract the root polygon cells
   * ################################
   */
  
  HCell[][] workingarray;
  
  private void doCells(){
    workingarray=new HCell[width][height];
    for(int x=0;x<width;x++){
      for(int y=0;y<height;y++){
        workingarray[x][y]=new HCell(x,y);}}
    PolygonAreaHCells root=new PolygonAreaHCells(rootpolygon,rootpolygontransform);
    //subtract the root
    for(HCell c:root)
      workingarray[c.x][c.y]=null;
    //
    for(int x=0;x<width;x++){
      for(int y=0;y<height;y++){
        if(workingarray[x][y]!=null)
          cells.add(workingarray[x][y]);}}}
  
  

}
