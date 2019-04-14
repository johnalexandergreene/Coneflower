package org.fleen.coneflower.hCellSystem;

import java.util.ArrayList;
import java.util.List;

/*
 * given a mass of edge to be boiled, expand it
 */
public class R_FattenBoiledEdge implements Rule{
  
  static final int[][] NOFF_RANGE3={
    {-1,3},{0,3},{1,3},
    {-2,2},{-1,2},{0,2},{1,2},{2,2},
    {-3,1},{-2,1},{-1,1},{0,1},{1,1},{2,1},{3,1},
    {-3,0},{-2,0},{-1,0},{1,0},{2,0},{3,0},//skipping center
    {-3,-1},{-2,-1},{-1,-1},{0,-1},{1,-1},{2,-1},{3,-1},
    {-2,-2},{-1,-2},{0,-2},{1,-2},{2,-2},
    {-1,-3},{0,-3},{1,-3}};
  
  /*
   * given c0
   * look at the cells within range
   * if any of them has a boiled thing then the new cell gets that boiled thing
   * otherwise the new cell gets whatever c0 had 
   */
  public void doRule(HCellSystem cs0,HCellSystem cs1){
    HCell d;
    for(HCell c: cs0){
      d=getBoiledNeighbor(c,cs0);
      if(d!=null){
        cs1.getCell(c.x,c.y).thing=d.thing;
      }else{
        cs1.getCell(c.x,c.y).thing=c.thing;}}}
  
  private HCell getBoiledNeighbor(HCell c,HCellSystem cs){
    List<HCell> n=getNeighbors(c,cs);
    for(HCell d:n){
      if(d!=null&&d.thing!=null&&d.thing.hasTags("boiled")){
        return d;}}
    return null;}
  
  private List<HCell> getNeighbors(HCell c,HCellSystem cs){
    List<HCell> n=new ArrayList<HCell>(NOFF_RANGE3.length);
    for(int[] a:NOFF_RANGE3)
      n.add(cs.getCell(c.x+a[0],c.y+a[1]));
    return n;}
  
}
