package org.fleen.coneflower.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fleen.util.tag.TagManager;
import org.fleen.util.tag.Tagged;
import org.fleen.util.tree.TreeNode;
import org.fleen.util.tree.TreeNodeServices;

/*
 * a closed curve composed of integer points arranged clockwise
 * describes a rectangle or whatever
 */
public class CShape implements TreeNode,Tagged{
  
  CShape(List<Point> points){
    this.points=new ArrayList<Point>(points);
  }
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  List<Point> points;
  
  List<Point> getEdge(){
    return points;}

  /*
   * ################################
   * CHILD ACCESS
   * ################################
   */
  
  public List<CShape> getChildren(String tag){
    List<CShape> a=new ArrayList<CShape>();
    CShape c;
    for(TreeNode b:getChildren()){
      c=(CShape)b;
      if(c.hasTag(tag))
        a.add(c);}
    return a;}
  
  /*
   * ################################
   * TREENODE
   * ################################
   */
  
  TreeNodeServices tns=new TreeNodeServices();

  public TreeNode getParent(){
    return tns.getParent();}

  public void setParent(TreeNode node){
    tns.setParent(node);}

  public List<? extends TreeNode> getChildren(){
    return tns.getChildren();}

  public TreeNode getChild(){
    return tns.getChild();}

  public void setChildren(List<? extends TreeNode> nodes){
    tns.setChildren(nodes);}

  public void setChild(TreeNode node){
    tns.setChild(node);}

  public void addChild(TreeNode node){
    tns.addChild(node);}

  public int getChildCount(){
    return tns.getChildCount();}

  public boolean hasChildren(){
    return tns.hasChildren();}

  public void removeChild(TreeNode child){
    tns.removeChild(child);}

  public void removeChildren(Collection<? extends TreeNode> children){
    tns.removeChildren(children);}

  public void clearChildren(){
    tns.clearChildren();}

  public boolean isRoot(){
    return tns.isRoot();}

  public boolean isLeaf(){
    return tns.isLeaf();}

  public int getDepth(){
    return tns.getDepth(this);}

  public TreeNode getRoot(){
    return tns.getRoot(this);}

  public TreeNode getAncestor(int levels){
    return getAncestor(levels);}

  public List<TreeNode> getSiblings(){
    return tns.getSiblings(this);}

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
  
  public boolean hasTag(String tag){
    return tagmanager.hasTag(tag);}
  
  public boolean hasTags(String... tags){
    return tagmanager.hasTags(tags);}
  
  public boolean hasTags(List<String> tags){
    return tagmanager.hasTags(tags);}
  
  public void addTag(String tag){
    tagmanager.addTags(tag);}
  
  public void addTags(String... tags){
    tagmanager.addTags(tags);}
  
  public void addTags(List<String> tags){
    tagmanager.addTags(tags);}
  
  public void removeTag(String tag){
    tagmanager.removeTags(tag);}
  
  public void removeTags(String... tags){
    tagmanager.removeTags(tags);}
  
  public void removeTags(List<String> tags){
    tagmanager.removeTags(tags);}

}
