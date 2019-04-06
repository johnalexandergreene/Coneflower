package org.fleen.coneflower.zCellSystem.test0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleen.coneflower.zCellSystem.ZCSMappedThing;
import org.fleen.coneflower.zCellSystem.ZCSMappedThingPresence;
import org.fleen.coneflower.zCellSystem.ZCell;
import org.fleen.coneflower.zCellSystem.ZCellSystem;

/*
 * given a mass of edge to be boiled, expand it
 * 
 * take census of neighbors
 * 
 * 
 */
public class R_Blur implements Rule{
  
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
  
  /*
   * given c0
   * look at the cells within range
   * if any of them has a boiled thing then the new cell gets that boiled thing
   * otherwise the new cell gets whatever c0 had 
   */
  public void doRule(ZCellSystem s0,ZCellSystem s1){
    List<ZCSMappedThingPresence> psum;
    ZCell c1;
    for(ZCell c0:s0){
      psum=getLocalPresenceSum(c0,s0);
      c1=s1.getCell(c0.x,c0.y);
      c1.presences.setPresences(psum);}}
  
  /*
   * get normalized summed presences of the 8 neighboring cells 
   */
  private List<ZCSMappedThingPresence> getLocalPresenceSum(ZCell c,ZCellSystem s){
    Map<ZCSMappedThing,ZCSMappedThingPresence> summedpresencebything=new HashMap<ZCSMappedThing,ZCSMappedThingPresence>();
    ZCell n;
    ZCSMappedThingPresence p;
    for(int[] a:NOFF_RANGE1){
      n=s.getCell(c.x+a[0],c.y+a[1]);
      if(n!=null){
        for(ZCSMappedThingPresence p0:n.presences){
          p=summedpresencebything.get(p0.thing);
          if(p==null){
            p=new ZCSMappedThingPresence(p0);
            summedpresencebything.put(p.thing,p);
          }else{
            p.intensity+=p0.intensity;}}}}
    List<ZCSMappedThingPresence> sum=new ArrayList<ZCSMappedThingPresence>(summedpresencebything.values());
    return sum;}
  
}
