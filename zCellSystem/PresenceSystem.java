package org.fleen.coneflower.zCellSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class PresenceSystem extends ArrayList<ZCSMappedThingPresence>{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public PresenceSystem(){
    super();}
  
  public PresenceSystem(Collection<ZCSMappedThingPresence> p){
    super(p);}
  
  public PresenceSystem(int s){
    super(s);}
  
  /*
   * ################################
   * PRESENCE ACCESS
   * ################################
   */
  
  public void addPresence(ZCSMappedThingPresence p){
    ZCSMappedThingPresence a=getPresence(p.thing);
    if(a!=null){
      a.intensity+=p.intensity;
    }else{
      add(p);}}
  
  public void addPresence(ZCSMappedThing thing,double intensity){
    addPresence(new ZCSMappedThingPresence(thing,intensity));}
  
  public void addPresences(List<ZCSMappedThingPresence> presences){
    for(ZCSMappedThingPresence p:presences)
      addPresence(p);}
  
  /*
   * returns true if the specified thing has nonzero presence at this cell
   */
  public boolean hasPresence(ZCSMappedThing thing){
    for(ZCSMappedThingPresence p:this)
      if(p.thing==thing)
        return true;
    return false;}
  
  /*
   * get the presence of the thing at this cell
   * if the thing has no presence at this cell then return null
   */
  ZCSMappedThingPresence getPresence(ZCSMappedThing thing){
    for(ZCSMappedThingPresence p:this)
      if(p.thing==thing)
        return p;
    return null;}
  
  /*
   * return the intensity of the specified thing's presence at this cell
   * if the thing has no presence at this cell then return 0
   */
  double getPresenceIntensity(ZCSMappedThing thing){
    for(ZCSMappedThingPresence p:this)
      if(p.thing==thing)
        return p.intensity;
    return 0;}
  
  /*
   * set all the presences
   * used in cell rule process
   */
  public void setPresences(List<ZCSMappedThingPresence> presences){
    clear();
    addAll(presences);
    clean();}
  
  public void addPresences(List<ZCSMappedThingPresence> presences,double weight){
    ZCSMappedThing t;
    double i;
    for(ZCSMappedThingPresence p:presences){
      t=p.thing;
      i=p.intensity*weight;
      addPresence(t,i);}}
  
  /*
   * ################################
   * PRESENCE MATH
   * ################################
   */
  
  public void factorIntensities(double f){
    for(ZCSMappedThingPresence p:this)
      p.intensity*=f;}
  
  /*
   * ################################
   * CLEAN
   * remove zero intensity presences
   * normalize presence intensities 
   * ################################
   */
  
  private static final double ZEROISHINTENSITY=0.0000001;
  
  public void clean(){
    cullZeroIntensityPresences();
    normalizePresenceIntensities();}
  
  private void cullZeroIntensityPresences(){
    Iterator<ZCSMappedThingPresence> i=iterator();
    ZCSMappedThingPresence p;
    while(i.hasNext()){
      p=i.next();
      if(p.intensity<ZEROISHINTENSITY){
        i.remove();}}}
  
  private void normalizePresenceIntensities(){
    double s=getPresenceIntensitySum();
    if(s>0){
      double n=1.0/s;
      for(ZCSMappedThingPresence p:this)
        p.intensity*=n;}}
  
  /*
   * pre-normalization it's something >0
   * post normalization it should be 1.0
   */
  private double getPresenceIntensitySum(){
    double s=0;
    for(ZCSMappedThingPresence p:this)
      s+=p.intensity;
    return s;}
  
}
