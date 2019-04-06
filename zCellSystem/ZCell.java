package org.fleen.coneflower.zCellSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * A cell has a location in the array (x,y) and a number of polygon presences
 * A polygon presence is the presence of a polygon. 
 *   A reference to a polygon and a value describing how present that polygon is range : [0,1]
 * Presences come in 2 varieties
 * Interior presence means that the cell is inside the polygon, presence is 1.0
 * Edge presence means that the cell is near the edge of the polygon.
 *   Right at the edge presence is 0.5. 
 *   Move inward and the presence increases to 1.0
 *   Move outward and the presence decreases to 0.0 
 * 
 */
public class ZCell{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  ZCell(){}
  
  ZCell(int x,int y){
    this.x=x;
    this.y=y;}
  
  ZCell(int x,int y,ZCSMappedThing thing,double intensity){
    this(x,y);
    presences.addPresence(thing,intensity);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  //coors within the cellarray
  //the cell center is also the cell coors
  public int x,y;
  
  /*
   * ################################
   * PRESENCE SYSTEM
   * A list of presences + some fancy services
   * The cell holds the presence of 0..n things
   * The things are polygons or polygon edges or whatever
   * ################################
   */
  
  //98% of cells will have just 1 presence
  //1% will have 2
  //1% will have 2..6
  //right?
  private static final int INITPRESENCELISTSIZE=6;
  
  public PresenceSystem presences=new PresenceSystem(INITPRESENCELISTSIZE);
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  private static final int PRIME=104729;
  
  public int hashCode(){
    return x+y*PRIME;}
  
  public boolean equals(Object a){
    ZCell b=(ZCell)a;
    return b.x==x&&b.y==y;}

}
