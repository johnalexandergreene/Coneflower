package org.fleen.coneflower.hCellSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.geom_2D.DPolygon;

/* 
 * A cellular automata system
 * For a forsythia post-process
 * For boiling, curve-smoothing, maybe some proportion tweaking
 * It might get fancy. We might translate vertices into special cells, doing a vector-CA combo thing
 * We prefer this to RD because CA is simpler
 */
public class HCellSystem implements HCellMass{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */

  public HCellSystem(int w,int h){
    System.out.println("HCELL SYSTEM INIT");
    System.out.println(w+"x"+h);
    System.out.println("cellcount="+(w*h));
    initCells(w,h);}
  
  public HCellSystem(int w,int h,List<HCSMappedThing> mappedthings){
    this(w,h);
    mapTheThings(mappedthings);
    clean();}
  
  /*
   * ################################
   * CELLS
   * A cell's center is its coordinates
   * its square ranges x-0.5 to x+0.5 and y-0.5 to y+0.5
   * ################################
   */
  
  //the height and width of a cell relative to any mapped geometry
  static final double CELLSPAN=1.0;
  
  HCell[][] cells;
  
  public int getWidth(){
    return cells.length;}
  
  public int getHeight(){
    return cells[0].length;}
  
  public int getCellCount(){
    return cells.length*cells[0].length;}
  
  private void initCells(int w,int h){
    cells=new HCell[w][h];
    for(int x=0;x<w;x++){
      for(int y=0;y<h;y++){
        cells[x][y]=new HCell(x,y);}}}
  
  public HCell getCell(int x,int y){
    if(x<0||x>=cells.length||y<0||y>=cells[0].length)
      return null;
    return cells[x][y];}
  
  public HCell[] getNeighbors(HCell c){
   HCell[] n=new HCell[8];
   n[0]=getCell(c.x,c.y+1);
   n[1]=getCell(c.x+1,c.y+1);
   n[2]=getCell(c.x+1,c.y);
   n[3]=getCell(c.x+1,c.y-1);
   n[4]=getCell(c.x,c.y-1);
   n[5]=getCell(c.x-1,c.y-1);
   n[6]=getCell(c.x-1,c.y);
   n[7]=getCell(c.x-1,c.y+1);
   return n;}
  
  public HCell[] getR2Neighbors(HCell c){
    HCell[] n=new HCell[20];
    //r1
    n[0]=getCell(c.x,c.y+1);
    n[1]=getCell(c.x+1,c.y+1);
    n[2]=getCell(c.x+1,c.y);
    n[3]=getCell(c.x+1,c.y-1);
    n[4]=getCell(c.x,c.y-1);
    n[5]=getCell(c.x-1,c.y-1);
    n[6]=getCell(c.x-1,c.y);
    n[7]=getCell(c.x-1,c.y+1);
    //r2
    n[8]=getCell(c.x-1,c.y+2);
    n[9]=getCell(c.x,c.y+2);
    n[10]=getCell(c.x+1,c.y+2);
    n[11]=getCell(c.x+2,c.y+1);
    n[12]=getCell(c.x+2,c.y);
    n[13]=getCell(c.x+2,c.y-1);
    n[14]=getCell(c.x+1,c.y-2);
    n[15]=getCell(c.x,c.y-2);
    n[16]=getCell(c.x-1,c.y-2);
    n[17]=getCell(c.x-2,c.y+1);
    n[18]=getCell(c.x-2,c.y);
    n[19]=getCell(c.x-2,c.y-1);
    return n;}
  
  public Iterator<HCell> iterator(){
    return new HCellSystemCellIterator(this);}
  
  /*
   * return the cell that contains the specified point
   * the cell's coors are also the cell's center point
   * the cell's square spans cell.x-0.5 to cell.y+0.5 and cell.y-0.5 to cell.y+0.5
   */
  public HCell getCellContainingPoint(double x,double y){
    if(x-Math.floor(x)<0.5)
      x=Math.floor(x);
    else
      x=Math.ceil(x);
    if(y-Math.floor(y)<0.5)
      y=Math.floor(y);
    else
      y=Math.ceil(y);
    return getCell((int)x,(int)y);}
  
  /*
   * ################################
   * MAP THINGS TO THIS CELL SYSTEM
   * ################################
   */
  
  private void mapTheThings(List<HCSMappedThing> mappedthings){
    for(HCSMappedThing t:mappedthings){
      if(t.hasTags("margin")){
        mapMargin(t);
      }else if(t.hasTags("polygon")){
        mapPolygon(t);
      }else if(t.hasTags("yard")){
        mapYard(t);
      }else{
        throw new IllegalArgumentException("mapping thing failed");}}}
  
  private MarginHCells mapMargin(HCSMappedThing t){
    MarginHCells c=new MarginHCells(getWidth(),getHeight(),(DPolygon)t.thing,t.transform);
    HCell b;
    for(HCell a:c){
        b=cells[a.x][a.y];
        b.thing=t;}
    return c;}
  
  private PolygonAreaHCells mapPolygon(HCSMappedThing t){
    PolygonAreaHCells c=new PolygonAreaHCells((DPolygon)t.thing,t.transform);
    HCell b;
    for(HCell a:c){
        b=cells[a.x][a.y];
        b.thing=t;}
    return c;}
  
  private PolygonEdgeHCells mapYard(HCSMappedThing t){
    //REDO THIS
    
    PolygonEdgeHCells c=new PolygonEdgeHCells(((FPolygon)t.thing).getDPolygon(),t.transform);
    HCell b;
    for(HCell a:c){
        b=cells[a.x][a.y];
        b.thing=t;}
    return c;}
  
  /*
   * ################################
   * CELL POLLING
   * Given a cell
   * Get it's neighbors. That is, get it's immediate neighbors. Then their neighbors, and so on. Out to an arbitrary number of layers.
   * Then we might count them, see which cells have which things, stuff like that
   * This is fast. We cache the offsets for each radius
   * we return nulls too, like when a cell is near the cellsystem edge. So keep that in mind.
   * ################################
   */
  
  public HCell[] getNeighbors(HCell c,int radius){
    int[][] offsets=getOffsets(radius);
    HCell[] neighbors=new HCell[offsets.length];
    for(int i=0;i<offsets.length;i++){
      neighbors[i]=getCell(c.x+offsets[i][0],c.y+offsets[i][1]);}
    return neighbors;}
    
  Map<Integer,int[][]> offsetsbyradius=new HashMap<Integer,int[][]>();
  
  private int[][] getOffsets(int radius){
    int[][] offsets=offsetsbyradius.get(radius);
    if(offsets!=null){
      return offsets;
    }else{
      offsets=createOffsets(radius);
      offsetsbyradius.put(radius,offsets);}
    return offsets;}
  
  private int[][] createOffsets(int radius){
    Set<OffsetVector> 
      vectors=new HashSet<OffsetVector>(),
      newvectors=new HashSet<OffsetVector>();
    vectors.add(new OffsetVector(0,0));
    for(int i=0;i<radius;i++){
      for(OffsetVector v:vectors){
        newvectors.add(new OffsetVector(v.dx,v.dy-1));
        newvectors.add(new OffsetVector(v.dx,v.dy-1));
        newvectors.add(new OffsetVector(v.dx,v.dy-1));
        newvectors.add(new OffsetVector(v.dx,v.dy-1));
        newvectors.add(new OffsetVector(v.dx,v.dy-1));
        newvectors.add(new OffsetVector(v.dx,v.dy-1));
        newvectors.add(new OffsetVector(v.dx,v.dy-1));
        newvectors.add(new OffsetVector(v.dx,v.dy-1));
      }
    }
      
    
  }
  
  class OffsetVector{
    OffsetVector(int dx,int dy){
      this.dx=dx;
      this.dy=dy;}
    int dx,dy;
    public boolean equals(Object a){
      OffsetVector b=(OffsetVector)a;
      return b.dx==dx&&b.dy==dy;}
    
    }
    
}
