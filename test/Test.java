package org.fleen.coneflower.test;

import org.fleen.coneflower.core.Coneflower;
import org.fleen.coneflower.core.ConeflowerListener;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    this.coneflower=new Coneflower(new ConeflowerListener0());
    this.ui=new UI(this);
    coneflower.run();}
  
  /*
   * ################################
   * CONEFLOWER
   * ################################
   */
  
  public Coneflower coneflower;
  
  class ConeflowerListener0 implements ConeflowerListener{

    public void tick(){
      System.out.println("---tick---");
      System.out.println("age="+coneflower.time);
      ui.viewer.renderer.render();
      ui.viewer.repaint();}}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  UI ui;
  
  /*
   * ################################
   * ################################
   * MAIN
   * ################################
   * ################################
   */
  
  static Test test;
  
  public static final void main(String[] a){
    System.out.println("test start");
    test=new Test();}

}
