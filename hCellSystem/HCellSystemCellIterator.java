package org.fleen.coneflower.hCellSystem;

import java.util.Iterator;

class HCellSystemCellIterator implements Iterator<HCell> {
  
  HCellSystemCellIterator(HCellSystem rastermap){
    this.rastermap=rastermap;}
  
  HCellSystem rastermap;
  int x=0,y=0;
  
  public boolean hasNext(){
    return y<rastermap.getHeight();}

  public HCell next(){
    HCell c=rastermap.cells[x][y];
    x++;
    if(x==rastermap.getWidth()){
      x=0;
      y++;}
    return c;}

  public void remove(){
    throw new IllegalArgumentException("not implemented");}}
