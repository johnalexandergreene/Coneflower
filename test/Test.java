package org.fleen.coneflower.test;

import org.fleen.coneflower.core.Coneflower;
import org.fleen.coneflower.core.ConeflowerListener;

public class Test{
  
  static final int CWIDTH=400,CHEIGHT=600;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    this.coneflower=new Coneflower(CWIDTH,CHEIGHT,new ConeflowerListener0());
    this.ui=new UI(this);
    coneflower.run();}
  
  /*
   * ################################
   * CONEFLOWER
   * ################################
   */
  
  public Coneflower coneflower;
  
  class ConeflowerListener0 implements ConeflowerListener{

    public void ticked(){
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
