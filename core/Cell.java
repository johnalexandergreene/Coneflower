package org.fleen.coneflower.core;

import java.util.List;

/*
 * a cell is a point
 */
public class Cell{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public Cell(int x,int y){
    this.x=x;
    this.y=y;}
  
  /*
   * ################################
   * GEOMETRY
   * This cell is a point
   * ################################
   */
  
  public int x,y;
  
  /*
   * ################################
   * POLYGON EDGE
   * a cell has edge data object for 1..n polygons
   * a cell with no polygon edges is null
   * ################################
   */
  
  public List<PolygonEdge> polygonedge;
  
  /*
   * ################################
   * ORDERED LINKED LIST LINKS
   * In the cellsystem we have 2 ordered doubley-linked lists
   * x ordered and y ordered
   * we use them for inspecting the system of cells
   * ################################
   */
  
  public Cell 
    xorderedminus,
    xorderedplus,
    yorderedminus,
    yorderedplus;
  
}
