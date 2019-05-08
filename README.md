# CONEFLOWER

This is a general 2D geometry production system

We create geometric structure. A tree of shapes.

A shape is either a polygon or a yard.

A Yard is a 2D area defined by one outer edge and n inner edges (holes).

Use progressive differentiation process. IE given a shape, differentiate it into more shapes, and so on.

---

There will be multiple geometry-manipulating subsystems. IE splitting, boiling, crushing, etc (see below)

There will be processes for deriving one kind of geometry from another : IE curve smoothing, curve elaboration (see below)

A shape has a basic geometry made of simple polygon/s and an elaborate geometry derived from that basic geometry. 

The elaborate geometry will be a simple transformation of the basic. Many points will have direct correspondence. 
Doing it this way will give us a wealth of useful info for child geometry production (jigging).

---

## GEOMETRY PRODUCTION SUBSYSTEMS

These are all refinements of plain ol 2D geometry.

### FORSYTHIA SPLITTER
 Split polygon into N polygons. This is our basic Forsythia processes. The generated shapes will have, for example, for a basic polygon an FPolygon, and then for an elaboration that polygon smoothed.

### FORSYTHIA CRUSHER
Given a Forsythia polygon, fill it with more Forsythia polygons. We are shooting for nice packing and spacing. Make a KGrid with fish=desired spacing. Then throw random polygons at it, filtering for nice composition. This could be run all week. It could be milked extensively.

### REGULAR POLYGON BOILER
Differentiate a polygon into a yard and inner polygon, creating a simple border. Do it 2D geometrywise. Very simple. This might not work for irregular polygons.

### FOAMER
Fill a polygon or yard with a fine more-or-less uniform pattern of polygons. A pattern of hexagons or random polygons or whatever. 

### FORSYTHIA SPLITTING-BOILER
Given an FPolygon, split it using standard process. Then boil those children. Then remove the children's outer skin. Now we have a nice boilish thing guided by splitter (we want to exploit those forsythia splitters maximally)

## GEOMETRY ELABORATION SUBSYSTEMS

###SOFTENER
Split-tweak curve smoothing. Simple. We will transfer points from the base geometry to the elaboration, so we get that simple correspondence, and we can use that for further splitting or whatever.






