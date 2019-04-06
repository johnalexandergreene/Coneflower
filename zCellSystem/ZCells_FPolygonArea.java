package org.fleen.coneflower.zCellSystem;

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
 * A polygon's shadow upon the raster cell array
 * all of the cells in which the Polygon manifests as a non-zero intensity Presence
 */
public class ZCells_FPolygonArea implements ZCellMass{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  ZCells_FPolygonArea(ZCSMT_FPolygonArea a){
    mappedpolygonarea=a;
    initTransformedPolygon();
    mapPolygonArea();}
  
  /*
   * ################################
   * MAPPED POLYGON AREA
   * ################################
   */
  
  private ZCSMT_FPolygonArea mappedpolygonarea;
  
  /*
   * ################################
   * POLYGON
   * ################################
   */
  
  private DPolygon transformedpolygon;
  
  private void initTransformedPolygon(){
    DPolygon dpolygon=mappedpolygonarea.fpolygon.getDPolygon();
    int s=dpolygon.size();
    transformedpolygon=new DPolygon(s);
    double[] a=new double[2];
    for(DPoint p:dpolygon){
      a[0]=p.x;
      a[1]=p.y;
      mappedpolygonarea.fpolygontransform.transform(a,0,a,0,1);
      transformedpolygon.add(new DPoint(a));}}
  
  /*
   * ################################
   * GLOWSPAN
   * The distance to which a thing's presence bleeds out beyond its edge
   * ################################
   */
  
  private double getGlowSpan(){
    return mappedpolygonarea.glowspan;}
  
  /*
   * ################################
   * CELLS
   * ################################
   */
  
  private List<ZCell> cells=new ArrayList<ZCell>();
  
  public Iterator<ZCell> iterator(){
    return cells.iterator();}
  
  public int getCellCount(){
    return cells.size();}
  
  /*
   * ################################
   * MAP POLYGON AREA
   * 
   * create cell array to enclose polygon, with offsets : enclosingarray 
   *   And margins to allow for glow and safety
   *   
   * get edge of polygon using supercover bresenhams : edge
   *   (supercover gives us a fatter stroke, less chance of leaks during floodfill)
   *   
   * fatten up the edge with a little expand, the better to get 2 unambiguously distinguished masses,
   *   inneredge and outeredge
   *   1 or 2 cycles should do it.
   *   
   * distinguish the edge into 2 masses, inneredge and outer edge, using inside test 
   * 
   * floodfill from the outer edge outwards
   *   testing and storing the distancefrompolygon (in terms of proportion of glowspan, ie presence) for each cell
   *   constrain that floodfill to glowspan distance from the polygon
   *   
   * floodfill from the inneredge similarly
   *   we will hold on to cells that test for too far from polygon, 
   *   where the floodfill stops, to use as seeds for the final floodfill of the inner area
   * 
   * the remaining inner area gets a plain floodfill
   *   presence is set to 1.0
   * 
   * ################################
   */
  
  static final int SAFETYOFFSET=3;//a bit of padding so we don't have to test for off-array cell queries
  ZCell[][] enclosingarray;
  int eaoffsetx,eaoffsety;
  Set<ZCell> 
    edge=new HashSet<ZCell>(),
    inneredge=new HashSet<ZCell>(),
    outeredge=new HashSet<ZCell>(),
    innermost=new HashSet<ZCell>(),
    innermostfloodseednulls=new HashSet<ZCell>();
  
  int testthing;
  
  private void mapPolygonArea(){
    createEnclosingArray();
    doEdge();
    expandEdge();
    mapEdgeCellsToEnclosingArray();
    doInnerAndOuterEdge();
    doFloodFromOuterEdge();
    doFloodFromInnerEdge();
    doInnermostFlood();
    copyCellsToList();}
  
  /*
   * copy the nonnull cells from the enclosing array to the cells list
   * apply offsets too
   */
  private void copyCellsToList(){
    ZCell c;
    for(int x=0;x<enclosingarray.length;x++){
      for(int y=0;y<enclosingarray[0].length;y++){
        if(enclosingarray[x][y]!=null){
          c=enclosingarray[x][y];
          c.x+=eaoffsetx;
          c.y+=eaoffsety;
          cells.add(c);}}}}
  
  private void mapEdgeCellsToEnclosingArray(){
    for(ZCell a:edge){
      enclosingarray[a.x][a.y]=a;}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DO INNERMOST FLOOD
   * ++++++++++++++++++++++++++++++++
   */
  
  private void doInnermostFlood(){
    for(ZCell a:innermostfloodseednulls)
      innermostFlood(a);}
  
  private void innermostFlood(ZCell a){
    if(enclosingarray[a.x][a.y]!=null)return;
    Queue<ZCell> queue=new LinkedList<ZCell>();
    ZCell c;
    queue.add(a);
    while(!queue.isEmpty()) {
      c=queue.remove();
      if(innermostFlood(c.x,c.y)){     
        queue.add(new ZCell(c.x,c.y-1)); 
        queue.add(new ZCell(c.x,c.y+1)); 
        queue.add(new ZCell(c.x-1,c.y)); 
        queue.add(new ZCell(c.x+1,c.y));}}}

  private boolean innermostFlood(int x,int y){
    if(
      //so it doesn't crash on weird tiny situations, just delivers a graphic glitch
      x<0||x>=enclosingarray.length||y<0||y>=enclosingarray[0].length||
      //because we're filling only nulls
      enclosingarray[x][y]!=null)return false;
    enclosingarray[x][y]=new ZCell(x,y);
    enclosingarray[x][y].presences.addPresence(mappedpolygonarea,1.0);//1.0 because the thing is at max presence in the interior
    innermost.add(enclosingarray[x][y]);
    return true;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DO FLOOD FROM OUTER EDGE
   * ++++++++++++++++++++++++++++++++
   */
  
  private void doFloodFromOuterEdge(){
    ZCell a=getOuterEdgeNullCell();
    if(a==null)return;
    Queue<ZCell> queue=new LinkedList<ZCell>();
    ZCell c;
    queue.add(a);
    while(!queue.isEmpty()) {
      c=queue.remove();
      if(floodFromOuterEdge(c.x,c.y)){     
        queue.add(new ZCell(c.x,c.y-1)); 
        queue.add(new ZCell(c.x,c.y+1)); 
        queue.add(new ZCell(c.x-1,c.y)); 
        queue.add(new ZCell(c.x+1,c.y));}}}

  private boolean floodFromOuterEdge(int x,int y){
    if(
      //so it doesn't crash on weird tiny situations, just delivers a graphic glitch
      x<0||x>=enclosingarray.length||y<0||y>=enclosingarray[0].length||
      //because we're filling only nulls
      enclosingarray[x][y]!=null)return false;
    //check distance constraint, because we are constrining our flood to glowspan distance from the polygon's edge
    double 
      d=transformedpolygon.getDistance(x+eaoffsetx,y+eaoffsety),
      g=getGlowSpan();
    if(d>g)return false;
    //that distance is our presence
    enclosingarray[x][y]=new ZCell(x,y);
    enclosingarray[x][y].presences.addPresence(mappedpolygonarea,0.5-(d/g)*0.5);
    outeredge.add(enclosingarray[x][y]);
    //
    return true;}
  
  private ZCell getOuterEdgeNullCell(){
    for(ZCell c:outeredge){
      if(enclosingarray[c.x-1][c.y+1]==null)return new ZCell(c.x-1,c.y+1);
      if(enclosingarray[c.x][c.y+1]==null)return new ZCell(c.x,c.y+1);
      if(enclosingarray[c.x+1][c.y+1]==null)return new ZCell(c.x+1,c.y+1);
      if(enclosingarray[c.x+1][c.y]==null)return new ZCell(c.x+1,c.y);
      if(enclosingarray[c.x+1][c.y-1]==null)return new ZCell(c.x+1,c.y-1);
      if(enclosingarray[c.x][c.y+1]==null)return new ZCell(c.x,c.y+1);
      if(enclosingarray[c.x-1][c.y+1]==null)return new ZCell(c.x-1,c.y+1);
      if(enclosingarray[c.x-1][c.y]==null)return new ZCell(c.x-1,c.y);}
    return null;}//fail
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DO FLOOD FROM INNER EDGE
   * ++++++++++++++++++++++++++++++++
   */
  
  private void doFloodFromInnerEdge(){
    ZCell a=getInnerEdgeNullCell();
    if(a==null)return;//fail. maybe there was no room there
    Queue<ZCell> queue=new LinkedList<ZCell>();
    ZCell c;
    queue.add(a);
    while(!queue.isEmpty()) {
      c=queue.remove();
      if(floodFromInnerEdge(c.x,c.y)){     
        queue.add(new ZCell(c.x,c.y-1)); 
        queue.add(new ZCell(c.x,c.y+1)); 
        queue.add(new ZCell(c.x-1,c.y)); 
        queue.add(new ZCell(c.x+1,c.y));}}}

  private boolean floodFromInnerEdge(int x,int y){
    if(
      //so it doesn't crash on weird tiny situations, just delivers a graphic glitch
      x<0||x>=enclosingarray.length||y<0||y>=enclosingarray[0].length||
      //because we're filling only nulls
      enclosingarray[x][y]!=null)return false;
    //check distance constraint, because we are constrining our flood to glowspan distance from the polygon's edge
    double 
      d=transformedpolygon.getDistance(x+eaoffsetx,y+eaoffsety),
      g=getGlowSpan();
    //when the cell fails at this point it makes a good null 
    //cell for seeding our innermost flood, so we save it
    if(d>g){
      innermostfloodseednulls.add(new ZCell(x,y));
      return false;}
    //that distance is our presence
    enclosingarray[x][y]=new ZCell(x,y);
    enclosingarray[x][y].presences.addPresence(mappedpolygonarea,0.5+(d/g)*0.5);
    inneredge.add(enclosingarray[x][y]);
    //
    return true;}
  
  private ZCell getInnerEdgeNullCell(){
    for(ZCell c:inneredge){
      if(enclosingarray[c.x-1][c.y+1]==null)return new ZCell(c.x-1,c.y+1);
      if(enclosingarray[c.x][c.y+1]==null)return new ZCell(c.x,c.y+1);
      if(enclosingarray[c.x+1][c.y+1]==null)return new ZCell(c.x+1,c.y+1);
      if(enclosingarray[c.x+1][c.y]==null)return new ZCell(c.x+1,c.y);
      if(enclosingarray[c.x+1][c.y-1]==null)return new ZCell(c.x+1,c.y-1);
      if(enclosingarray[c.x][c.y+1]==null)return new ZCell(c.x,c.y+1);
      if(enclosingarray[c.x-1][c.y+1]==null)return new ZCell(c.x-1,c.y+1);
      if(enclosingarray[c.x-1][c.y]==null)return new ZCell(c.x-1,c.y);}
    return null;}//it happens when the glow fills the whole interior
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DO INNER AND OUTER EDGE
   * given the edge, distinguish it into inner and outer edges
   * we do it by resting for polygon.contained
   * then we do the presences
   * ++++++++++++++++++++++++++++++++
   */
  
  private void doInnerAndOuterEdge(){
    for(ZCell c:edge){
      if(transformedpolygon.containsPoint(c.x+eaoffsetx,c.y+eaoffsety)){
        inneredge.add(c);
      }else{
        outeredge.add(c);}}
    doPresencesForInnerEdge();
    doPresencesForOuterEdge();}
  
  private void doPresencesForInnerEdge(){
    double d,g=getGlowSpan();
    for(ZCell c:inneredge){
      d=transformedpolygon.getDistance(c.x+eaoffsetx,c.y+eaoffsety);
      if(d>g){//this probably won't happen, but just in case
        c.presences.addPresence(mappedpolygonarea,1.0);
      }else{
        c.presences.addPresence(mappedpolygonarea,0.5+(d/g)*0.5);}}}
  
  private void doPresencesForOuterEdge(){
    double d,g=getGlowSpan();
    for(ZCell c:outeredge){
      d=transformedpolygon.getDistance(c.x+eaoffsetx,c.y+eaoffsety);
      if(d>g){//this probably won't happen, but just in case
        c.presences.addPresence(mappedpolygonarea,0.0);
      }else{
        c.presences.addPresence(mappedpolygonarea,0.5-(d/g)*0.5);}}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DO EDGE ADJACENTS
   * get the cells adjacent to the edge cells
   * add them to the edge cells set
   * don't even test them or anything
   * ++++++++++++++++++++++++++++++++
   */
  
  private void expandEdge(){
    List<ZCell> a=new ArrayList<ZCell>();
    for(ZCell b:edge)
      a.addAll(getAdjacents(b));
    edge.addAll(a);}
  
  private List<ZCell> getAdjacents(ZCell c){
    List<ZCell> a=new ArrayList<ZCell>(8);
    a.add(new ZCell(c.x-1,c.y+1));
    a.add(new ZCell(c.x,c.y+1));
    a.add(new ZCell(c.x+1,c.y+1));
    a.add(new ZCell(c.x+1,c.y));
    a.add(new ZCell(c.x+1,c.y-1));
    a.add(new ZCell(c.x,c.y-1));
    a.add(new ZCell(c.x-1,c.y-1));
    a.add(new ZCell(c.x-1,c.y));
    return a;}
  
  
  /*
   * ++++++++++++++++++++++++++++++++
   * INIT EDGE CELLS
   * get the edge cells with bresenhams
   * ++++++++++++++++++++++++++++++++
   */
  
  private void doEdge(){
    int s=transformedpolygon.size(),i1;
    ZCell c0,c1;
    DPoint p0,p1;
    for(int i0=0;i0<s;i0++){
      //get end points of a side seg of the polygon
      i1=i0+1;
      if(i1==s)i1=0;
      p0=transformedpolygon.get(i0);
      p1=transformedpolygon.get(i1);
      c0=deGetCellAtPoint(p0.x,p0.y);
      c1=deGetCellAtPoint(p1.x,p1.y);
      bresenhamSupercoverSegDraw(c0.x,c0.y,c1.x,c1.y);}}
  
  private ZCell deGetCellAtPoint(double x,double y){
    if(x-Math.floor(x)<0.5)
      x=Math.floor(x);
    else
      x=Math.ceil(x);
    if(y-Math.floor(y)<0.5)
      y=Math.floor(y);
    else
      y=Math.ceil(y);
    return new ZCell((int)x-eaoffsetx,(int)y-eaoffsety);}
  
  /*
   * gleaned from 
   * http://www.intranet.ca/~sshah/waste/art7.html 
   */
  void bresenhamSupercoverSegDraw(int x0,int y0,int x1,int y1){
    int i;               // loop counter 
    int ystep, xstep;    // the step on y and x axis 
    int error;           // the error accumulated during the increment 
    int errorprev;       // vision the previous value of the error variable 
    int y = y0, x = x0;  // the line points 
    double ddy, ddx;        // compulsory variables: the double values of dy and dx 
    int dx = x1 - x0; 
    int dy = y1 - y0;
//    segcells.add(cells[x][y]); //skip the first cell, otherwise some cells get selected twice
    // NB the last point can't be here, because of its previous point (which has to be verified) 
    if (dy < 0){ 
      ystep = -1; 
      dy = -dy; 
    }else 
      ystep = 1; 
    if (dx < 0){ 
      xstep = -1; 
      dx = -dx; 
    }else 
      xstep = 1; 
    ddy = 2 * dy;  // work with double values for full precision 
    ddx = 2 * dx; 
    if (ddx >= ddy){  // first octant (0 <= slope <= 1) 
      // compulsory initialization (even for errorprev, needed when dx==dy) 
      errorprev = error = dx;  // start in the middle of the square 
      for (i=0 ; i < dx ; i++){  // do not use the first point (already done) 
        x += xstep; 
        error += ddy; 
        if (error > ddx){  // increment y if AFTER the middle ( > ) 
          y += ystep; 
          error -= ddx; 
          // three cases (octant == right->right-top for directions below): 
          if (error + errorprev < ddx){  // bottom square also
            edge.add(new ZCell(x,y-ystep));
          }else if(error + errorprev > ddx){  // left square also 
            edge.add(new ZCell(x-xstep,y));
          }else{  // corner: bottom and left squares also 
            edge.add(new ZCell(x,y-ystep));
            edge.add(new ZCell(x-xstep,y));}} 
        edge.add(new ZCell(x,y));
        errorprev = error;} 
    }else{// the same as above 
      errorprev = error = dy; 
      for (i=0 ; i < dy ; i++){ 
        y += ystep; 
        error += ddx; 
        if (error > ddy){ 
          x += xstep; 
          error -= ddy; 
          if (error + errorprev < ddy){ 
            edge.add(new ZCell(x-xstep,y));
          }else if (error + errorprev > ddy){ 
            edge.add(new ZCell(x,y-ystep));
          }else{ 
            edge.add(new ZCell(x-xstep,y));
            edge.add(new ZCell(x,y-ystep));}}
        edge.add(new ZCell(x,y));
        errorprev = error;}}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * ENCLOSING ARRAY
   * ++++++++++++++++++++++++++++++++
   */
  
  private void createEnclosingArray(){
    List<ZCell> vertexcells=new ArrayList<ZCell>(transformedpolygon.size());
    for(DPoint p:transformedpolygon)
      vertexcells.add(ceaGetCellAtPoint(p.x,p.y));
    //
    int xmin=Integer.MAX_VALUE,xmax=Integer.MIN_VALUE,ymin=Integer.MAX_VALUE,ymax=Integer.MIN_VALUE;
    for(ZCell c:vertexcells){
      if(c.x<xmin)xmin=c.x;
      if(c.x>xmax)xmax=c.x;
      if(c.y<ymin)ymin=c.y;
      if(c.y>ymax)ymax=c.y;}
    //pad it out for glowspan and safety
    int padding=(int)Math.ceil(getGlowSpan()+SAFETYOFFSET);
    xmin-=padding;
    xmax+=padding;
    ymin-=padding;
    ymax+=padding;
    //
    eaoffsetx=xmin;
    eaoffsety=ymin;
    enclosingarray=new ZCell[xmax-xmin+1][ymax-ymin+1];}
  
  private ZCell ceaGetCellAtPoint(double x,double y){
    if(x-Math.floor(x)<0.5)
      x=Math.floor(x);
    else
      x=Math.ceil(x);
    if(y-Math.floor(y)<0.5)
      y=Math.floor(y);
    else
      y=Math.ceil(y);
    return new ZCell((int)x,(int)y);}
  
  
}