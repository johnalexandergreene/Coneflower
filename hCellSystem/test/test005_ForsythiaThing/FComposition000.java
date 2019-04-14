package org.fleen.coneflower.hCellSystem.test.test005_ForsythiaThing;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.fleen.forsythia.core.composition.ForsythiaComposition;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;
import org.fleen.forsythia.core.grammar.FJig;

@SuppressWarnings("serial")
public class FComposition000 extends ForsythiaComposition{
  
  static final String GRAMMARPATH="/home/john/Desktop/grammars/rdtest.grammar";
  
  public FComposition000(){
    super();
    initGrammar();
    FMetagon rm=gleanRootMetagon(grammar);
    initTree(rm);
    FJig j=grammar.getRandomJig(rm,null,100);
    j.createNodes(getRootPolygon());}
  
  private FMetagon gleanRootMetagon(ForsythiaGrammar grammar){
    FMetagon m=grammar.getRandomMetagon(new String[]{"root"});
    return m;}
  
  private void initGrammar(){
    File file=new File(GRAMMARPATH);
    FileInputStream fis;
    ObjectInputStream ois;
    ForsythiaGrammar g=null;
    try{
      fis=new FileInputStream(file);
      ois=new ObjectInputStream(fis);
      g=(ForsythiaGrammar)ois.readObject();
      ois.close();
    }catch(Exception x){
      System.out.println("exception in grammar import");
      x.printStackTrace();}
    setGrammar(g);}

}
