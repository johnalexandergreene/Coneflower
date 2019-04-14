package org.fleen.coneflower.hCellSystem;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;


/*
 * the cells who's center point is contained within the polygon
 */
public class PolygonAreaHCells implements HCellMass{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  PolygonAreaHCells(DPolygon polygon,AffineTransform transform){
    this.polygon=polygon;
    initTransformedPolygon(transform);
    mapPolygonArea();}
  
  /*
   * ################################
   * POLYGON
   * ################################
   */
  
  DPolygon 
    polygon,
    transformedpolygon;
  
  private void initTransformedPolygon(AffineTransform t){
    int s=polygon.size();
    transformedpolygon=new DPolygon(s);
    double[] a=new double[2];
    for(DPoint p:polygon){
      a[0]=p.x;
      a[1]=p.y;
      t.transform(a,0,a,0,1);
      transformedpolygon.add(new DPoint(a));}}
  
  /*
   * ################################
   * CELLS
   * ################################
   */
  
  private List<HCell> cells=new ArrayList<HCell>();
  
  public Iterator<HCell> iterator(){
    return cells.iterator();}
  
  public int getCellCount(){
    return cells.size();}
  
  /*
   * ################################
   * MAP POLYGON AREA
   * create null cell array to enclose polygon, with offsets
   * draw edge with bresenham
   * get a null cell inside the edge
   * floodfill
   * keep
   *   all floodfill cells
   *   all edge cells where center is within edge
   * put all the keepers in our cells list
   * ################################
   */
  
  private void mapPolygonArea(){
    createEnclosingArray();
    drawEdge();
    gleanNullInteriorCell();
    floodFill(nicx,nicy);
    storeEdgeCells();
    storeInteriorCells();}
  
  //only the cells that are actually interior
  private void storeEdgeCells(){
    for(HCell c:edgecells){
      if(isInterior(c)){
        cells.add(
          new HCell(
            c.x+eaoffsetx,
            c.y+eaoffsety));}}}
  
  private void storeInteriorCells(){
    HCell c;
    for(int x=0;x<enclosingarray.length;x++){
      for(int y=0;y<enclosingarray[0].length;y++){
        c=enclosingarray[x][y];
        if(c!=null){
          c=new HCell(
            c.x+eaoffsetx,
            c.y+eaoffsety);
          cells.add(c);}}}}
  
  private boolean isInterior(HCell c){
    double x=c.x+eaoffsetx;
    double y=c.y+eaoffsety;
    return transformedpolygon.containsPoint(x,y);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * ENCLOSING ARRAY
   * ++++++++++++++++++++++++++++++++
   */
  
  HCell[][] enclosingarray;
  int eaoffsetx,eaoffsety;
  
  private void createEnclosingArray(){
    List<HCell> vertexcells=new ArrayList<HCell>(transformedpolygon.size());
    for(DPoint p:transformedpolygon)
      vertexcells.add(ceaGetCellAtPoint(p.x,p.y));
    //
    int xmin=Integer.MAX_VALUE,xmax=Integer.MIN_VALUE,ymin=Integer.MAX_VALUE,ymax=Integer.MIN_VALUE;
    for(HCell c:vertexcells){
      if(c.x<xmin)xmin=c.x;
      if(c.x>xmax)xmax=c.x;
      if(c.y<ymin)ymin=c.y;
      if(c.y>ymax)ymax=c.y;}
    //a little padding, so we don't have to worry about testing off-array cells
    xmin--;
    xmax++;
    ymin--;
    ymax++;
    //
    eaoffsetx=xmin;
    eaoffsety=ymin;
    enclosingarray=new HCell[xmax-xmin+1][ymax-ymin+1];}
  
  private HCell ceaGetCellAtPoint(double x,double y){
    if(x-Math.floor(x)<0.5)
      x=Math.floor(x);
    else
      x=Math.ceil(x);
    if(y-Math.floor(y)<0.5)
      y=Math.floor(y);
    else
      y=Math.ceil(y);
    return new HCell((int)x,(int)y);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DRAW EDGE
   * ++++++++++++++++++++++++++++++++
   */
  
  Set<HCell> edgecells;
  
  private void drawEdge(){
    edgecells=new HashSet<HCell>();
    int s=transformedpolygon.size(),i1;
    HCell c0,c1;
    DPoint p0,p1;
    for(int i0=0;i0<s;i0++){
      //get end points of a side seg of the polygon
      i1=i0+1;
      if(i1==s)i1=0;
      p0=transformedpolygon.get(i0);
      p1=transformedpolygon.get(i1);
      c0=deGetCellAtPoint(p0.x,p0.y);
      c1=deGetCellAtPoint(p1.x,p1.y);
      bresenhamSegDraw(c0.x,c0.y,c1.x,c1.y);}}
  
  private HCell deGetCellAtPoint(double x,double y){
    if(x-Math.floor(x)<0.5)
      x=Math.floor(x);
    else
      x=Math.ceil(x);
    if(y-Math.floor(y)<0.5)
      y=Math.floor(y);
    else
      y=Math.ceil(y);
    return new HCell((int)x-eaoffsetx,(int)y-eaoffsety);}
  
  private void bresenhamSegDraw(int x0,int y0,int x1,int y1){
    HCell cell;
    int w=x1-x0;
    int h=y1-y0;
    int dx1=0,dy1=0,dx2=0,dy2=0;
    if(w<0)
      dx1=-1; 
    else if(w>0)
      dx1=1;
    if(h<0)
      dy1=-1; 
    else if(h>0) 
      dy1=1;
    if(w<0)
      dx2=-1; 
    else if(w>0) 
      dx2=1;
    int longest=Math.abs(w);
    int shortest=Math.abs(h);
    if(!(longest>shortest)){
      longest=Math.abs(h);
      shortest=Math.abs(w);
      if(h<0)
        dy2=-1; 
      else if(h>0) 
        dy2=1;
        dx2=0;}
    int numerator=longest>>1;
    for(int i=0;i<=longest;i++){
      cell=new HCell(x0,y0);
      enclosingarray[x0][y0]=cell;
      edgecells.add(cell);
      numerator+=shortest;
      if(!(numerator<longest)){
        numerator-=longest;
        x0+=dx1;
        y0+=dy1;
      }else{
        x0+=dx2;
        y0+=dy2;}}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * NULL INTERIOR CELL
   * Where the floodfill starts
   * Get all of the edge cells
   * for each cell
   *   for each cell neighbor
   *     if the cell is interior to the polygon and also null then we have a winner
   * ++++++++++++++++++++++++++++++++
   */
  
  private int nicx,nicy;
  private static final Boolean NULLTAG=true;
  
  private void gleanNullInteriorCell(){
    HCell[] n;
    for(HCell c0:edgecells){
      n=getNICNeighbors(c0);
      for(HCell c1:n){
        if(c1.gpobject==NULLTAG&&isInterior(c1)){
          nicx=c1.x;
          nicy=c1.y;
          return;}}}
    throw new IllegalArgumentException("couldn't get edge interior cell");}
  
  private HCell[] getNICNeighbors(HCell c){
    HCell[] n=new HCell[8];
    n[0]=enclosingarray[c.x][c.y+1];
    if(n[0]==null)n[0]=new HCell(c.x,c.y+1,NULLTAG);
    n[1]=enclosingarray[c.x+1][c.y+1];
    if(n[1]==null)n[1]=new HCell(c.x+1,c.y+1,NULLTAG);
    n[2]=enclosingarray[c.x+1][c.y];
    if(n[2]==null)n[2]=new HCell(c.x+1,c.y,NULLTAG);
    n[3]=enclosingarray[c.x+1][c.y-1];
    if(n[3]==null)n[3]=new HCell(c.x+1,c.y-1,NULLTAG);
    n[4]=enclosingarray[c.x][c.y-1];
    if(n[4]==null)n[4]=new HCell(c.x,c.y-1,NULLTAG);
    n[5]=enclosingarray[c.x-1][c.y-1];
    if(n[5]==null)n[5]=new HCell(c.x-1,c.y-1,NULLTAG);
    n[6]=enclosingarray[c.x-1][c.y];
    if(n[6]==null)n[6]=new HCell(c.x-1,c.y,NULLTAG);
    n[7]=enclosingarray[c.x-1][c.y+1];
    if(n[7]==null)n[7]=new HCell(c.x-1,c.y+1,NULLTAG);
    return n;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * FLOOD FILL
   * ++++++++++++++++++++++++++++++++
   */
  
  private void floodFill(int x,int y){
    Queue<HCell> queue=new LinkedList<HCell>();
    queue.add(new HCell(x,y));
    while(!queue.isEmpty()) {
      HCell c=queue.remove();
      if(floodFill_(c.x,c.y)){     
        queue.add(new HCell(c.x,c.y-1)); 
        queue.add(new HCell(c.x,c.y+1)); 
        queue.add(new HCell(c.x-1,c.y)); 
        queue.add(new HCell(c.x+1,c.y));}}}

  private boolean floodFill_(int x,int y){
    if(enclosingarray[x][y]!=null)return false;
    enclosingarray[x][y]=new HCell(x,y);
    return true;}
  
}