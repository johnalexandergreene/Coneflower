package org.fleen.coneflower.core;

/*
 * in a Coneflower there is a fixed set of cells and a fixed set of points
 * if a point exists then it is part of the perimeter of 1..4 CShapes
 */
public class CPoint{
  
  public CPoint(int x,int y){
    this.x=x;
    this.y=y;
  }
  
  public int x,y;

}
