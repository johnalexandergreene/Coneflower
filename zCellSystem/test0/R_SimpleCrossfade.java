package org.fleen.coneflower.zCellSystem.test0;

import java.util.List;

import org.fleen.coneflower.zCellSystem.PresenceSystem;
import org.fleen.coneflower.zCellSystem.ZCSMT_FPolygonBoiledEdge;
import org.fleen.coneflower.zCellSystem.ZCSMappedThingPresence;
import org.fleen.coneflower.zCellSystem.ZCell;
import org.fleen.coneflower.zCellSystem.ZCellSystem;

/*
 * a crossfade
 * render composition0 and composition 1, weightedly
 * we only address workingcellsystem0 here
 */
public class R_SimpleCrossfade implements Rule{
  
  public ZCellSystem c0cellsystem,c1cellsystem;
  
  int tick;
  public void setTick(int t){
    tick=t;}
  
  //s1 is null
  public void doRule(ZCellSystem s0,ZCellSystem s1){
    ZCell cc0,cc1;
    double weight=((double)tick)/100;
    System.out.println("weight="+weight);
    for(ZCell w:s0){
      cc0=c0cellsystem.getCell(w.x,w.y);
      cc1=c1cellsystem.getCell(w.x,w.y);
      w.presences.clear();
      w.presences.addPresences(cc0.presences,weight);
      w.presences.addPresences(cc1.presences,1.0-weight);
//      w.presences.clean();
      }}
  
}
