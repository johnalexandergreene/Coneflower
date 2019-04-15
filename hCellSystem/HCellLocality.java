package org.fleen.coneflower.hCellSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * A survey of the local cell system out to the specified radius
 * what neighbors do we have?
 * how many of them are there?
 * What are their things?
 * What are the relative thingsums?
 * How many nulls do we have? (it means that we are near the edge of the cellsystem)
 * ...stuff like that
 * 
 */
public class HCellLocality{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public HCellLocality(HCell cell, HCellSystem system,int radius){
    pcell=cell;
    psystem=system;
    pradius=radius;
    initNeighbors();
    
//    System.out.println("LOCALITY");
//    System.out.println("the cell : "+pcell);
//    System.out.println("neighbors");
//    System.out.println("count="+neighbors.length);
//    for(HCell c:neighbors)
//      System.out.println(c);
    
  }
  
  /*
   * ################################
   * PARAMETERS
   * ################################
   */
  
  HCell pcell; 
  HCellSystem psystem;
  int pradius;
  
  /*
   * ################################
   * NEIGHBORING CELLS
   * All of the cells within the radius
   * The radius being the number of layers out that we grab neighbors.
   * Get them, analyze them
   * ################################
   */
  
  public HCell[] neighbors;
  private int nullneighborcount=-1;
  
  private void initNeighbors(){
    neighbors=getNeighbors(pcell,psystem,pradius);}
  
  /*
   * all neighbors, nonnull or null
   */
  public int getNeighborCount(){
    return neighbors.length;}
  
  public int getNullNeighborCount(){
    if(nullneighborcount!=-1)
      return nullneighborcount;
    nullneighborcount=0;
    for(HCell a:neighbors)
      if(a==null)
        nullneighborcount++;
    return nullneighborcount;}
  
  public int getNonnullNeighborCount(){
    return neighbors.length-getNullNeighborCount();}
  
  //TODO some other nice analysis. Maybe move majority thing in here
  
  /*
   * ################################
   * STATIC NEIGHBORING CELLS GETTER STUFF
   * Cache the offset pattern for each radius queried
   * ################################
   */
  
  static HCell[] getNeighbors(HCell c,HCellSystem s,int radius){
    int[][] offsets=getOffsets(radius);
    HCell[] neighbors=new HCell[offsets.length];
    for(int i=0;i<offsets.length;i++){
      neighbors[i]=s.getCell(c.x+offsets[i][0],c.y+offsets[i][1]);}
    return neighbors;}
  
  //----------------
  //OFFSETS
  //every time we get the neighbors we cache the pattern of offsets for that radius so we don't have to figure it out again
  static Map<Integer,int[][]> offsetsbyradius=new HashMap<Integer,int[][]>();
  
  static int[][] getOffsets(int radius){
    int[][] offsets=offsetsbyradius.get(radius);
    if(offsets!=null){
      return offsets;
    }else{
      offsets=createOffsets(radius);
      offsetsbyradius.put(radius,offsets);}
    return offsets;}
  
  static int[][] createOffsets(int radius){
    Set<OffsetVector> 
      vectors=new HashSet<OffsetVector>(),
      newvectors=new HashSet<OffsetVector>(),
      newnewvectors=new HashSet<OffsetVector>();
    newvectors.addAll(getLocalVectors(new OffsetVector(0,0)));
    for(int i=1;i<radius;i++){
      for(OffsetVector v:newvectors){
        newnewvectors.addAll(getLocalVectors(v));}
      vectors.addAll(newvectors);
      newvectors.clear();
      newvectors.addAll(newnewvectors);
      newnewvectors.clear();}
    vectors.addAll(newvectors);
    //now we have all of the vectors in the hashmap
    //convert to an offset array.
    int[][] offsets=new int[vectors.size()][2];
    int i=0;
    for(OffsetVector v:vectors){
      if(offsets[i]!=null){
        offsets[i][0]=v.dx;
        offsets[i][1]=v.dy;}
      i++;}
    //
    return offsets;}
  
  static List<OffsetVector> getLocalVectors(OffsetVector v){
    List<OffsetVector> lv=new ArrayList<OffsetVector>();
    lv.add(new OffsetVector(v.dx,v.dy-1));
    lv.add(new OffsetVector(v.dx+1,v.dy-1));
    lv.add(new OffsetVector(v.dx+1,v.dy));
    lv.add(new OffsetVector(v.dx+1,v.dy+1));
    lv.add(new OffsetVector(v.dx,v.dy+1));
    lv.add(new OffsetVector(v.dx-1,v.dy+1));
    lv.add(new OffsetVector(v.dx-1,v.dy));
    lv.add(new OffsetVector(v.dx-1,v.dy-1));
    return lv;}
  
  static class OffsetVector{
    
    OffsetVector(int dx,int dy){
      this.dx=dx;
      this.dy=dy;}
    
    int dx,dy;
    
    public boolean equals(Object a){
      OffsetVector b=(OffsetVector)a;
      return b.dx==dx&&b.dy==dy;}
    
    public int hashCode(){
      return dx+dy*7919;}
    
    public String toString(){
      return "ov["+dx+","+dy+"]";}}
  

}
