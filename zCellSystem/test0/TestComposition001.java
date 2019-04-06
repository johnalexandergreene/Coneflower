package org.fleen.coneflower.zCellSystem.test0;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.fleen.forsythia.app.compositionGenerator.composer.Composer001_SplitBoil;
import org.fleen.forsythia.app.compositionGenerator.composer.ForsythiaCompositionGen;
import org.fleen.forsythia.core.composition.ForsythiaComposition;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;
import org.fleen.forsythia.core.grammar.FJig;

@SuppressWarnings("serial")
public class TestComposition001 extends ForsythiaComposition{
  
  static final String GRAMMARPATH="/home/john/Desktop/grammars/s008.grammar";
  
  static final double DETAILLIMIT=0.2;
  
  public TestComposition001(){
    super();
    initGrammar();
    FMetagon r=getRootMetagon();
    initTree(r);
    ForsythiaCompositionGen composer=new Composer001_SplitBoil();
    composer.compose(this,DETAILLIMIT);}
  
  private FMetagon getRootMetagon(){
    for(FMetagon m:grammar.getMetagons())
      if(m.hasTags("root"))
        return m;
    throw new IllegalArgumentException("root metagon not found");}
  
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
