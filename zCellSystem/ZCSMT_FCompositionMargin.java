package org.fleen.coneflower.zCellSystem;

import java.awt.geom.AffineTransform;
import java.util.List;

import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.util.tag.TagManager;

public class ZCSMT_FCompositionMargin implements ZCSMappedThing{

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public ZCSMT_FCompositionMargin(
      int zcswidth,int zcsheight,FPolygon rootpolygon,AffineTransform rootpolygontransform,double glowspan){
      this.zcswidth=zcswidth;
      this.zcsheight=zcsheight;
      this.rootpolygon=rootpolygon;
      this.rootpolygontransform=rootpolygontransform;
      this.glowspan=glowspan;}
  
  public ZCSMT_FCompositionMargin(
      int zcswidth,int zcsheight,FPolygon rootpolygon,AffineTransform rootpolygontransform,double glowspan,String[] tags){
    this(zcswidth,zcsheight,rootpolygon,rootpolygontransform,glowspan);
    tagmanager.addTags(tags);}
  
  /*
   * ################################
   * ZCELL SYSTEM DIMENSIONS
   * ################################
   */
  
  public int zcswidth,zcsheight;
  
  /*
   * ################################
   * FORSYTHIA COMPOSITON ROOT POLYGON
   * ################################
   */
  
  public FPolygon rootpolygon;
  
  /*
   * ################################
   * FORSYTHIA COMPOSITON ROOT POLYGON TRANSFORM
   * ################################
   */
  
  public AffineTransform rootpolygontransform;
  
  /*
   * ################################
   * GLOWSPAN
   * Mapped things often have a presence that spreads a bit from their actual location
   * ################################
   */
  
  double glowspan;
  
  public double getGlowSpan(){
    return glowspan;}
  
  /*
   * ################################
   * TAGS
   * ################################
   */
  
  private TagManager tagmanager=new TagManager();
  
  public void setTags(String... tags){
    tagmanager.setTags(tags);}
  
  public void setTags(List<String> tags){
    tagmanager.setTags(tags);}
  
  public List<String> getTags(){
    return tagmanager.getTags();}
  
  public boolean hasTags(String... tags){
    return tagmanager.hasTags(tags);}
  
  public boolean hasTags(List<String> tags){
    return tagmanager.hasTags(tags);}
  
  public void addTags(String... tags){
    tagmanager.addTags(tags);}
  
  public void addTags(List<String> tags){
    tagmanager.addTags(tags);}
  
  public void removeTags(String... tags){
    tagmanager.removeTags(tags);}
  
  public void removeTags(List<String> tags){
    tagmanager.removeTags(tags);}
  
}
