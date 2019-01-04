package org.fleen.coneflower.core;

import java.util.ArrayList;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;

@SuppressWarnings("serial")
public class CellChain extends ArrayList<CCell>{
  
  CellChain(DPolygon polygon,double scale){
    init(polygon,scale);
    
  }
  
  private void init(DPolygon polygon,double scale){
    int s=polygon.size(),ip1;
    DPoint p0,p1;
    CCell c0,c1;
    for(int ip0=0;ip0<s;ip0++){
      ip1=ip0+1;
      if(ip1==s)ip1=0;
      p0=polygon.get(ip0);
      p1=polygon.get(ip1);
      c0=deGetCellAtPoint(p0.x*scale,p0.y*scale);
      c1=deGetCellAtPoint(p1.x*scale,p1.y*scale);
      bresenhamSegDraw(c0.x,c0.y,c1.x,c1.y);}}
  
  private CCell deGetCellAtPoint(double x,double y){
    if(x-Math.floor(x)<0.5)
      x=Math.floor(x);
    else
      x=Math.ceil(x);
    if(y-Math.floor(y)<0.5)
      y=Math.floor(y);
    else
      y=Math.ceil(y);
    return new CCell((int)x,(int)y);}
  
  private void bresenhamSegDraw(int x0,int y0,int x1,int y1){
    CCell cell;
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
      cell=new CCell(x0,y0);
      add(cell);
      numerator+=shortest;
      if(!(numerator<longest)){
        numerator-=longest;
        x0+=dx1;
        y0+=dy1;
      }else{
        x0+=dx2;
        y0+=dy2;}}}

}
