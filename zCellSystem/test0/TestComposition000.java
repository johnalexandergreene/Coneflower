package org.fleen.coneflower.zCellSystem.test0;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.fleen.forsythia.core.composition.ForsythiaComposition;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;
import org.fleen.forsythia.core.grammar.FJig;

@SuppressWarnings("serial")
public class TestComposition000 extends ForsythiaComposition{
  
  static final String GRAMMARPATH="/home/john/Desktop/grammars/rdtest.grammar";
  
  static final String GRAMMARPATH000="/home/john/projects/code/Forsythia/src/org/fleen/forsythia/grammars/a011.grammar";
  
  public TestComposition000(){
    super();
    System.out.println("TEST COMPOISITION 0000");
    initGrammar();
    FMetagon rm=gleanRootMetagon(grammar);
    initTree(rm);
    FJig j=grammar.getJigs(rm).get(0);
    j.createNodes(getRootPolygon());
    System.out.println("polygoncount="+getPolygons().size());}
  
  private FMetagon gleanRootMetagon(ForsythiaGrammar grammar){
    for(FMetagon m:grammar.getMetagons()){
      if(m.hasTags("root"))
        return m;}
    throw new IllegalArgumentException("exception is root metagon acquirement");}
  
  private void initGrammar(){
    File file=new File(GRAMMARPATH000);
    FileInputStream fis;
    ObjectInputStream ois;
    ForsythiaGrammar g=null;
    try{
      fis=new FileInputStream(file);
      ois=new ObjectInputStream(fis);
      g=(ForsythiaGrammar)ois.readObject();
      ois.close();
    }catch(Exception x){
      System.out.println("exception in gramar import");
      x.printStackTrace();}
    setGrammar(g);}

}
