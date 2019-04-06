package org.fleen.coneflower.zCellSystem.test0;

import java.util.ArrayList;
import java.util.List;

import org.fleen.coneflower.zCellSystem.PresenceSystem;
import org.fleen.coneflower.zCellSystem.ZCSMT_FPolygonBoiledEdge;
import org.fleen.coneflower.zCellSystem.ZCSMappedThingPresence;
import org.fleen.coneflower.zCellSystem.ZCell;
import org.fleen.coneflower.zCellSystem.ZCellSystem;

/*
 * dupe present presences state
 * get sum of neighboring boiled edge presences : s
 * add s*k presence to cell
 * 
 */
public class R_FattenBoiledEdge implements Rule{
  
  static final int[][] NOFF_RANGE1={
      {-1,1},{0,1},{1,1},
      {1,0},
      {1,-1},{0,-1},{-1,-1},
      {-1,0}};
  
//  static final int[][] NOFF_RANGE3={
//    {-1,3},{0,3},{1,3},
//    {-2,2},{-1,2},{0,2},{1,2},{2,2},
//    {-3,1},{-2,1},{-1,1},{0,1},{1,1},{2,1},{3,1},
//    {-3,0},{-2,0},{-1,0},{1,0},{2,0},{3,0},//skipping center
//    {-3,-1},{-2,-1},{-1,-1},{0,-1},{1,-1},{2,-1},{3,-1},
//    {-2,-2},{-1,-2},{0,-2},{1,-2},{2,-2},
//    {-1,-3},{0,-3},{1,-3}};
  
  public void doRule(ZCellSystem s0,ZCellSystem s1){
    List<ZCSMappedThingPresence> psum;
    ZCell c1;
    for(ZCell c0:s0){
      c1=s1.getCell(c0.x,c0.y);
      c1.presences.setPresences(c0.presences);
      psum=getBoiledEdgeWeightedPresenceSum(c0,s0);
      c1.presences.addPresences(psum);
      c1.presences.clean();}}
  
  /*
   * TODO we need a PresenceSum object, not just a list
   * something we can add to, remove from, copy, etc
   */
  private List<ZCSMappedThingPresence> getBoiledEdgeWeightedPresenceSum(ZCell c,ZCellSystem s){
    PresenceSystem sum=new PresenceSystem();
    ZCell n;
    for(int[] a:NOFF_RANGE1){
      n=s.getCell(c.x+a[0],c.y+a[1]);
      if(n!=null){
        for(ZCSMappedThingPresence p:n.presences){
          if(p.thing instanceof ZCSMT_FPolygonBoiledEdge){
            sum.addPresence(p);}}}}
    sum.factorIntensities(0.3);
    return sum;}
  
}
