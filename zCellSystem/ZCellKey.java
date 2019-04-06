package org.fleen.coneflower.zCellSystem;


/*
 * cell coors wrapped in an object for use in a map
 */
class ZCellKey{
  
  ZCellKey(int x,int y){
    
    this.x=x;
    this.y=y;}
  
  ZCellKey(ZCell c){
    
    this.x=c.x;
    this.y=c.y;}
  
  int x,y;
  
  public int hashCode(){
    return x+y*19;}//TODO need bgger prime
  
  public boolean equals(Object a){
    ZCellKey b=(ZCellKey)a;
    return b.x==x&&b.y==y;}}
