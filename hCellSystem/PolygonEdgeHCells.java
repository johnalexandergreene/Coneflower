package org.fleen.coneflower.hCellSystem;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;

/*
 * get edge cells via bresenham : c0
 * get cells neighboring c0 : c1
 * and so on : c2, c3... a few times. 
 *   Out to cell layers count = glow + 1 or something
 * Mark all cells with presences according to distance from edge (inward or outward)
 *  
 */
public class PolygonEdgeHCells implements HCellMass{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  PolygonEdgeHCells(DPolygon polygon,AffineTransform transform){
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
   * MAP POLYGON EDGE
   * create null cell array to enclose polygon, with offsets
   * draw edge with bresenham
   * keep those edge cells
   * put all the keepers in our cells list
   * ################################
   */
  
  private void mapPolygonArea(){
    createEnclosingArray();
    drawEdge();
    storeEdgeCells();}
  
  private void storeEdgeCells(){
    for(HCell c:edgecells){
      cells.add(
        new HCell(
          c.x+eaoffsetx,
          c.y+eaoffsety));}}
  
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
  
}