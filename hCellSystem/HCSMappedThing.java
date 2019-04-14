package org.fleen.coneflower.hCellSystem;

import java.awt.geom.AffineTransform;
import java.util.List;

import org.fleen.util.tag.TagManager;
import org.fleen.util.tag.Tagged;

/*
 * a thing that is mapped to a cellsystem
 * the thing and some associated tags
 */
public class HCSMappedThing implements Tagged{

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public HCSMappedThing(){}
  
  public HCSMappedThing(Object thing){
    this.thing=thing;}
  
  public HCSMappedThing(Object thing,AffineTransform transform,String[] tags){
    this(thing);
    this.transform=transform;
    tagmanager.addTags(tags);}
  
  /*
   * ################################
   * THING
   * The thing that got mapped
   * ################################
   */
  
  public Object thing;
  
  /*
   * ################################
   * TRANSFORM
   * Mapped things often need transforms
   * ################################
   */
  
  public AffineTransform transform;
  
  /*
   * ################################
   * GENERAL PURPOSE OBJECT
   * ################################
   */
  
  public Object gpobject;
  
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

  public boolean hasTag(String tag){
    return tagmanager.hasTag(tag);}

  public void addTag(String tag){
    tagmanager.addTags(tag);}

  public void removeTag(String tag){
    tagmanager.removeTags(tag);}
  
}
