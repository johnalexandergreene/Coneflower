package org.fleen.coneflower.test;

import java.awt.image.BufferedImage;

public class Test{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Test(){
    this.ui=new UI(this);
    
    
  }
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  static final int UIWIDTH=800,UIHEIGHT=900;
  
  UI ui;
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  static Test test;
  
  public static final void main(String[] a){
    System.out.println("test start");
    test=new Test();}

}
