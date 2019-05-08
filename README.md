###################
CONEFLOWER
###################

General 2D geometry generation system

Create geometry from polygons and yards

(A Yard is a 2D area defined by one outer edge and n inner edges, holes)

Use progressive differentiation process. IE given a polygon, split it into some polygons, then split those polygons into polygons, and so on. 

---

There will be multiple geometry-manipulating subsystems. IE splitting, boiling, crushing, etc (see below)

There will be processes for deriving one kind of geometry from another : IE curve smoothing, curve elaboration (see below)

We are building a tree of shapes. Each shape contained within its parent. All shapes contained within the root.  

A shape is a polygon or yard. 

A shape has a basic geometry made of simple polygon/s and an elaborate geometry derived from that basic geometry. 

The elaborate geometry will be a simple transformation of the basic. Many points will have direct correspondence. 
Doing it this way will give us a wealth of useful info for child geometry production (jigging)  

---

