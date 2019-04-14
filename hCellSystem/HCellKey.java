package org.fleen.coneflower.hCellSystem;


/*
 * cell coors wrapped in an object for use in a map
 */
class HCellKey{
  
  HCellKey(int x,int y){
    
    this.x=x;
    this.y=y;}
  
  HCellKey(HCell c){
    
    this.x=c.x;
    this.y=c.y;}
  
  int x,y;
  
  public int hashCode(){
    return x+y*19;}//TODO need bgger prime
  
  public boolean equals(Object a){
    HCellKey b=(HCellKey)a;
    return b.x==x&&b.y==y;}}
