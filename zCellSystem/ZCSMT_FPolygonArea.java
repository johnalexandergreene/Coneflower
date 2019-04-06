package org.fleen.coneflower.zCellSystem;

import java.awt.geom.AffineTransform;
import java.util.List;

import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.util.tag.TagManager;

/*
 * everything we need for mapping an fpolygon area to a zcs
 */
public class ZCSMT_FPolygonArea implements ZCSMappedThing{

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public ZCSMT_FPolygonArea(FPolygon fpolygon,AffineTransform fpolygontransform,double glowspan){
    this.fpolygon=fpolygon;
    this.fpolygontransform=fpolygontransform;
    this.glowspan=glowspan;}
  
  public ZCSMT_FPolygonArea(FPolygon fpolygon,AffineTransform fpolygontransform,double glowspan,String[] tags){
    this(fpolygon,fpolygontransform,glowspan);
    tagmanager.addTags(tags);}
  
  /*
   * ################################
   * POLYGON
   * ################################
   */
  
  public FPolygon fpolygon;
  
  /*
   * ################################
   * POLYGON TRANSFORM
   * ################################
   */
  
  public AffineTransform fpolygontransform;
  
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
