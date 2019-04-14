package org.fleen.coneflower.hCellSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * A NEW CURVE SMOOTHING ALG
 * take the array
 * convert to float array
 * do blur
 *   ie : average of neighbors, 2 or 3 times
 * then convert back to cells
 * yes????
 * 
 * 
 * 
 */
public class R_Smooth implements Rule{
  
  static final int[][] VIEWRANGEOFFSETS={
    {-1,3},{0,3},{1,3},
    {-2,2},{-1,2},{0,2},{1,2},{2,2},
    {-3,1},{-2,1},{-1,1},{0,1},{1,1},{2,1},{3,1},
    {-3,0},{-2,0},{-1,0},{1,0},{2,0},{3,0},//skipping center
    {-3,-1},{-2,-1},{-1,-1},{0,-1},{1,-1},{2,-1},{3,-1},
    {-2,-2},{-1,-2},{0,-2},{1,-2},{2,-2},
    {-1,-3},{0,-3},{1,-3}};
  
//  static final int[][] VIEWRANGEOFFSETS={
//    {-1,1},{0,1},{1,1},
//    {-1,0},{1,0},//skipping center
//    {-1,-1},{0,-1},{1,-1}};
  
  /*
   * get c0 neighbors out to viewrange
   * get sum for each thing
   * set c1 cell to thing with greatest sum 
   */
  public void doRule(HCellSystem cs0,HCellSystem cs1){
    HCell c1;
    HCSMappedThing majority;
    for(HCell c0: cs0){
      majority=getGreatestSumThing(c0,cs0);
      c1=cs1.getCell(c0.x,c0.y);
      c1.thing=majority;}}
  
  private HCSMappedThing getGreatestSumThing(HCell c,HCellSystem cs){
    Map<HCSMappedThing,MappedThingSum> sumsbythings=new HashMap<HCSMappedThing,MappedThingSum>();
    MappedThingSum sum;
    List<HCell> n=getNeighbors(c,cs);
    for(HCell d:n){
      if(d==null)continue;
      sum=sumsbythings.get(d.thing);
      if(sum==null){
        sum=new MappedThingSum(d.thing);
        sumsbythings.put(d.thing,sum);}
      sum.sum++;}
    List<MappedThingSum> sums=new ArrayList<MappedThingSum>(sumsbythings.values());
    Collections.sort(sums,new MTSComparator());
    return sums.get(0).t;}
  
  private List<HCell> getNeighbors(HCell c,HCellSystem cs){
    List<HCell> n=new ArrayList<HCell>(VIEWRANGEOFFSETS.length);
    for(int[] a:VIEWRANGEOFFSETS)
      n.add(cs.getCell(c.x+a[0],c.y+a[1]));
    return n;}
  
  class MTSComparator implements Comparator<MappedThingSum>{
    public int compare(MappedThingSum a,MappedThingSum b){
      if(a.sum>b.sum){
        return 1;
      }else if(a.sum<b.sum){
        return -1;
      }else{
        return 0;}}}
  
  class MappedThingSum{
    
    MappedThingSum(HCSMappedThing t){
      this.t=t;}
    
    HCSMappedThing t;
    int sum=0;
  }
  
}
