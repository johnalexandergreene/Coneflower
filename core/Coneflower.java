package org.fleen.coneflower.core;

/*
 * a field where square-based cells and polygons play in an easily rendered way, from which we get nice pictures and animations.
 */
public class Coneflower{
  
  public Coneflower(int w,int h,ConeflowerListener listener){
    width=w;
    height=h;
    this.listener=listener;
  }
  
  public int width,height;
  
  ConeflowerListener listener;
  
  public void run(){
    for(int i=0;i<100;i++){
      try{
        Thread.sleep(500);
      }catch(Exception x){}
      incrementTime();}}
  
  public int time=0;
  
  void incrementTime(){
    time++;
    listener.ticked();
  }
  
  

}
