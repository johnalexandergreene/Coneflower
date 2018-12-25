################################
CONEFLOWER
################################

This is a geometry generation system

It uses Forsythia for its macro structure 
(arrangement and orientation of polygons)

It uses a square cellular automation for its micro struture 
(elaborations like boiling and foaming. curve smoothing. nice spacing in various little geometry vignettes) 
  Our cells are points. But we might use segs instead.
  Call them CSeg (coneflower seg)
  A seg is a length1 line extending from one cell-point to its neighbor
  A CSeg has 2 points
  it has 2 sides. At least 1 side is the area of a polygon. Maybe both.
  we can't use an array then.
  We would use 2 ordered lists
    list1: segs ordered by x coor of both points
      thus each seg will be listed in this list twice
    list2: cells ordered by y
      thus each seg will be listed in this list twice
  
  Each seg is unique within the field
  Segs are oriented vertically or horizontally. No diagonals. For simplicity.
     

---

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
  
  
  

