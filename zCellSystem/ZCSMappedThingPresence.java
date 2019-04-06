package org.fleen.coneflower.zCellSystem;

/*
 * the degree to which a mapped thing exists at a particular zcell
 */
public class ZCSMappedThingPresence{
  
  public ZCSMappedThingPresence(ZCSMappedThing thing,double intensity){
    this.thing=thing;
    this.intensity=intensity;}
  
  public ZCSMappedThingPresence(ZCSMappedThingPresence p){
    this(p.thing,p.intensity);}
  
  public ZCSMappedThing thing;
  public double intensity;

}
