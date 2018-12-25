given 
  a forsythia grammar
  a field of integer cells, like for a cellular automaton
  EXCEPT our cells represent points, not squares. 
    We use those points to create chains of points, thus the edges of polygons and such.

cast polygons and related structures upon cellfield
expand, contract, smooth, etc via cell ops

We will build compositions like this

  create root polygon
  (optionally) split it
  cast edges to cellfield
  adjust edges. Smooth or whatever
  Create new polygons inside of root polygons. Just shrink and center. 
    Doesn't have to be too precise or perfect.
  Split em or whatever
  cast to cellfield
  shrink, grow, smooth, etc so they fit nicely.
  repeat until satisfied
  
  
The deal here is that the forsythia polygons are abstractions of the cellfield forms. 
It's the cellforms that ultimately matter.
Use a high-rez cellfield
  speed is crucial in our get and set cell methods so we might just go with a big array
  
  
  

