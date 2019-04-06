package org.fleen.coneflower.zCellSystem;

import org.fleen.util.tag.Tagged;

/*
 * a thing that we map to a zcellsystem
 * a piece of vector geometry, or something
 * this is basically to decouple the mapped stuff from the map.
 * so we are free to, say, map the same piece of geometry a couple different time in different ways
 */
public interface ZCSMappedThing extends Tagged{
  
  //all mapped things in a ZCS have a glowspan
  //it's how the edge is treated.
  //how the presence of the thing ramps down outwards and ramps up inwards
  //with 0.5 presence right at the edge
  //a fuzzy edge, to blend with the neighbors' edges
  double getGlowSpan();

}
