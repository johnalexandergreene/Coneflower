package org.fleen.coneflower.zCellSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * map the whole RDSystem to a single cell mass with all presences at 1.0
 * then subtract the root mass
 * simple
 */
public class ZCells_FCompositionMargin implements ZCellMass{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public ZCells_FCompositionMargin(ZCSMT_FCompositionMargin m){
    marginthing=m;
    mapMargin();}
  
  /*
   * ################################
   * MAPPED THING
   * The zcell system mapped thing composition margin object
   * ################################
   */
  
  ZCSMT_FCompositionMargin marginthing;
  
  /*
   * ################################
   * CELLS
   * ################################
   */
  
  List<ZCell> cells=new ArrayList<ZCell>();
  
  public int getCellCount(){
    return cells.size();}
  
  public Iterator<ZCell> iterator(){
    return cells.iterator();}
  
  /*
   * ################################
   * MAP THE MARGIN
   * make a cell array the size of the zcs
   * fill the cells with 1.0 presences for this mapped thing 
   * get a ZCSMT_FPolygonArea for the root polygon (which is the bounding polygon) of the composition
   * subtract that from the cell array, substituting this mapped thing for the polygonarea mapped thing
   * copy the array to the list
   * ################################
   */
  
  ZCell[][] cellarray;
  
  private void mapMargin(){
    initCellArray();
    subtractRoot();
    copyCellsToList();}
  
  private void copyCellsToList(){
    for(int x=0;x<cellarray.length;x++){
      for(int y=0;y<cellarray[0].length;y++){
        cells.add(cellarray[x][y]);}}}
  
  private void subtractRoot(){
    ZCSMT_FPolygonArea t=new ZCSMT_FPolygonArea(
      marginthing.rootpolygon,marginthing.rootpolygontransform,marginthing.glowspan);
    ZCells_FPolygonArea a=new ZCells_FPolygonArea(t);
    ZCell c1;
    ZCSMappedThingPresence p0,p1;
    for(ZCell c0:a){
      c1=cellarray[c0.x][c0.y];
      p0=c0.presences.getPresence(t);
      p1=c1.presences.getPresence(marginthing);
      p1.intensity-=p0.intensity;}}
  
  private void initCellArray(){
    int 
      w=marginthing.zcswidth,
      h=marginthing.zcsheight;
    cellarray=new ZCell[w][h];
    for(int x=0;x<w;x++){
      for(int y=0;y<h;y++){
        cellarray[x][y]=new ZCell(x,y,marginthing,1.0);}}}
  
}
