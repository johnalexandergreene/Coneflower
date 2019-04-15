package org.fleen.coneflower.hCellSystem.test.test001_MapAPolygonAndSoftenIt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleen.coneflower.hCellSystem.HCSMappedThing;
import org.fleen.coneflower.hCellSystem.HCell;
import org.fleen.coneflower.hCellSystem.HCellLocality;
import org.fleen.coneflower.hCellSystem.HCellSystem;

/*
 * process an hcellsystem 
 */
public class Process000Test{
  
  public HCellSystem process(HCellSystem cs){
    int 
      w=cs.getWidth(),
      h=cs.getHeight();
    HCellSystem csnew=new HCellSystem(w,h);
    HCell c;
    HCSMappedThing t;
    for(int x=0;x<w;x++){
      for(int y=0;y<h;y++){
        c=cs.getCell(x,y);
        t=getMajorityThing(cs,c);
        if(t!=null)
          csnew.getCell(x,y).thing=t;
        else
          csnew.getCell(x,y).thing=c.thing;}}
    //
    return csnew;}
  
  /*
   * get all cells within some radius
   * count the things. return the majority thing
   * if it's a tie return null
   */
  private HCSMappedThing getMajorityThing(HCellSystem cs,HCell c){
    //get cells
    List<HCell> n=new ArrayList<HCell>();
    n.add(c);
//    for(HCell a:cs.getNeighbors(c))
    HCellLocality l0=new HCellLocality(c,cs,2);
//    for(HCell a:cs.getR2Neighbors(c))
    for(HCell a:l0.neighbors)
      if(a!=null)
        n.add(a);
    //count things
    Map<HCSMappedThing,Integer> thingcounts=new HashMap<HCSMappedThing,Integer>();
    HCSMappedThing t;
    Integer i;
    for(HCell a:n){
      t=a.thing;
      i=thingcounts.get(t);
      if(i==null){
        thingcounts.put(t,new Integer(1));
      }else{
        thingcounts.put(t,new Integer(i+1));}}
    //get biggest
    HCSMappedThing tbiggest=null;
    Integer ibiggest=-1,itest;
    for(HCSMappedThing t0:thingcounts.keySet()){
      if(ibiggest==-1){
        tbiggest=t0;
        ibiggest=thingcounts.get(t0);
      }else{
        itest=thingcounts.get(t0);
        if(itest>ibiggest){
          tbiggest=t0;
          ibiggest=itest;}}}
    //
    return tbiggest;}

}
