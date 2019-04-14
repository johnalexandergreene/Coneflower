package org.fleen.coneflower.hCellSystem;

import java.util.Iterator;

/*
 * basic cell ops
 * implemented by CellSystem, PolygonAreaCells and PolygonEdgeCells
 */
public interface HCellMass extends Iterable<HCell>{
  
  int getCellCount();
  
  Iterator<HCell> iterator();
  
}
