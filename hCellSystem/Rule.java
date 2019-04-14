package org.fleen.coneflower.hCellSystem;

public interface Rule{
  
  /*
   * for each cell in cs0
   *   apply rule to get new cell contents
   *   set contents of corresponding cell in cs1 appropriately
   */
  void doRule(HCellSystem cs0,HCellSystem cs1);
  
}
