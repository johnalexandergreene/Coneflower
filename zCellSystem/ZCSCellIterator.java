package org.fleen.coneflower.zCellSystem;

import java.util.Iterator;


class ZCSCellIterator implements Iterator<ZCell> {
  
  ZCSCellIterator(ZCellSystem zcellsystem){
    this.zcellsystem=zcellsystem;}
  
  ZCellSystem zcellsystem;
  int x=0,y=0;
  
  public boolean hasNext(){
    return y<zcellsystem.getHeight();}

  public ZCell next(){
    ZCell c=zcellsystem.cells[x][y];
    x++;
    if(x==zcellsystem.getWidth()){
      x=0;
      y++;}
    return c;}

  public void remove(){
    throw new IllegalArgumentException("not implemented");}}
