package org.fleen.coneflower.hCellSystem.test.test005_ForsythiaThing;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

import org.fleen.forsythia.app.compositionGenerator.composer.Composer001_SplitBoil;
import org.fleen.forsythia.app.compositionGenerator.composer.ForsythiaCompositionGen;
import org.fleen.forsythia.core.composition.ForsythiaComposition;
import org.fleen.forsythia.core.grammar.FMetagon;
import org.fleen.forsythia.core.grammar.ForsythiaGrammar;

@SuppressWarnings("serial")
public class FComposition001 extends ForsythiaComposition{
  
  static final String GRAMMARNAME="a011.grammar";
  
  static final double DETAILLIMIT=0.1;
  
  public FComposition001(){
    super();
    initGrammar();
    FMetagon r=getRootMetagon();
    initTree(r);
    ForsythiaCompositionGen composer=new Composer001_SplitBoil();
    composer.compose();}
  
  private FMetagon getRootMetagon(){
    
    List<FMetagon> a=grammar.getMetagons();
    return a.get(6);
//    for(FMetagon m:grammar.getMetagons())
//      if(m.hasTags("root"))
//        return m;
//    throw new IllegalArgumentException("root metagon not found");
    
  }
  
//  private void initGrammar(){
//    File file=new File(GRAMMARPATH);
//    FileInputStream fis;
//    ObjectInputStream ois;
//    ForsythiaGrammar g=null;
//    try{
//      fis=new FileInputStream(file);
//      ois=new ObjectInputStream(fis);
//      g=(ForsythiaGrammar)ois.readObject();
//      ois.close();
//    }catch(Exception x){
//      System.out.println("exception in grammar import");
//      x.printStackTrace();}
//    setGrammar(g);}
  
  private void initGrammar(){
    System.out.println("LOAD GRAMMAR : "+GRAMMARNAME);
    try{
      InputStream a=FComposition001.class.getResourceAsStream(GRAMMARNAME);
      ObjectInputStream b=new ObjectInputStream(a);
      grammar=(ForsythiaGrammar)b.readObject();
      b.close();
    }catch(Exception e){
      System.out.println("Load grammar failed.");
      e.printStackTrace();}}

}
