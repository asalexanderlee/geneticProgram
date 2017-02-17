import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
 
 private static Tree[] population;
 
 private static String[] getIntArray(int numInt){
  String[] intArray = new String[2*(numInt) + 1];
  for (int i = -numInt; i < numInt+1; i++){
   intArray[i+numInt] = i + "";
  }
  return intArray;
 }
 
 
 public static Tree createSymbReg(List<Double> xValues, List<Double> yValues,String[] termSet, String[] funcSet){
  
  for (int i = 0; i < 25; i++){
    System.out.println(i);
   call(xValues, yValues, termSet, funcSet);
  }
  int best = -1;
  double min = Integer.MAX_VALUE;
  for (int i = 0; i < population.length; i++){
   double fitness = getFitness(xValues,yValues,population[i]);
   if (fitness < min){
    min = fitness;
    best = i;
   }
  }
  System.out.println(min);
  return population[best];
 }
 
 public static Tree[] initializePop(String[] ops, String[] ints, int depth){
  Tree[] pop = new Tree[4];
  
  for (int i = 0; i < 4; i++){
   if (i < 2){
    pop[i] = new Tree(ops, ints, depth, "full");
   }else{
    pop[i] = new Tree(ops, ints, depth, "grow");
   }
  }
  
  return pop;
 }
 
 public static void call(List<Double> xValues, List<Double> yValues,String[] termSet, String[] funcSet){
   
  double[] probDis = getProbDistribution(xValues,yValues, population);
  Tree[] newPop = new Tree[4];
  for (int i = 0; i < population.length; i++){
    
   Random rand = new Random();  
   double prob = rand.nextDouble(); 
   if (prob < .10){
    Tree child = (chooseRandTree(probDis,population)).treeCopy();
    
       child.mutate(termSet,funcSet);
       while (Double.isNaN(child.getY(5))){
            child.mutate(termSet,funcSet);
    }
       newPop[i] = child;
       //System.out.println("Mutation");
    //Tree child = Mutation();
   }
   else{
     
    Tree child = crossover(probDis,population);
    while (Double.isNaN(child.getY(5))){
         child = crossover(probDis,population);
    }
    newPop[i] = child;
    //System.out.println("Crossover");
    //Tree child = new Tree; 
    //child = Crossover(); 
   }
   
   //Tree[].add(child) 
  }
  population = newPop;
  
 }
  
  public static String FILENAME = "dataset1.csv";
  
  
  public static void readCSV(List<Double> xValues, List<Double> yValues){
    
    File file = new File(FILENAME);
    try {
      Scanner inputStream = new Scanner(file);
      boolean first = true;
      while(inputStream.hasNextLine()){
                
                String[] data = (inputStream.nextLine()).split(",");
                if (first) {
                  first = false;
                  continue;
                }
                xValues.add(Double.parseDouble(data[0]));
                yValues.add(Double.parseDouble(data[1]));

            }
      inputStream.close();
    }
    catch (FileNotFoundException e){

            e.printStackTrace();
        }
    
    
    
    
  }
  
  
  /* Calculates the fitness of a given tree
   *
   * Parameters:
   *    xValues - given x -values
   *    yVlaues - give y-values
   *    tree - given tree
   * 
   *  Return:
   *     fitness - fitness of tree
   */
  
  public static double getFitness(List<Double> xValues, List<Double> yValues, Tree tree){
   //tree.str();
   //System.out.println();
   //ystem.out.println();
    double fitness = 0;
    for (int i = 0; i < xValues.size(); i++){
      if (Double.isNaN(tree.getY(xValues.get(i)))) continue;
      double error = tree.getY(xValues.get(i)) - yValues.get(i);
     
      // System.out.println(error);
      fitness += (error * error) ;
     //System.out.println(fitness);
      
      
    }
    //System.out.println(fitness);
    
    return fitness / xValues.size();
    
  }
  
  
   /* Returns cumulative probablity distribution. Determines likelihood of a tree being chosen
   *
   * Parameters:
   *    xValues - given x -values
   *    yVlaues - give y-values
   *    trees - array of all trees in a generation
   * 
   *  Return:
   *     distribution - array of probabilities
   */
  
  public static double[] getProbDistribution(List<Double> xValues, List<Double> yValues, Tree[] trees){
    double fitnessSum = 0;
    double[] fitnessLst = new double[trees.length];
    for (int i = 0; i < trees.length; i++){
      double fitness = 1.0 / (getFitness(xValues, yValues, trees[i])) ;
      fitnessLst[i] = fitness;
      //System.out.println(fitness);
      fitnessSum += fitness;
    }
    double[] distribution = new double[trees.length];
    for (int i = 0; i < trees.length; i++){
      if (i == 0){
          distribution[i] = fitnessLst[i] / fitnessSum;
      }
      else {
          distribution[i] = distribution[i - 1] + (fitnessLst[i] / fitnessSum);
      }
    }
    return distribution;
    
    
  }
  
  
   /* Chooses a random tree to reproduce
   *
   * Parameters:
   *    distribution - array of probabilities of being chosen
   *    trees - array of all trees in a generation
   * 
   *  Return:
   *     tree - random tree
   */
  public static Tree chooseRandTree(double[] distribution, Tree[] trees){
    double r = new Random().nextDouble();
    //System.out.println(r);
    //System.out.println(Arrays.toString(distribution));
    for (int i = 0; i < distribution.length; i++){
      if (r <= distribution[i]){
        return trees[i];
      }
      
    }
    return null;
    
    
  }
  
  
   /* Two trees share their genes
   *
   * Parameters:
   *    tree1 - first parent
   *    tree2 - second parent
   * 
   *  Return:
   *     tree1Copy - offspring
   */
  public static Tree reproduce(Tree tree1, Tree tree2){
    Tree tree1Copy = tree1.treeCopy();
    if (tree2 == null) System.out.println("know");
    if (tree1 == null) System.out.println("know");
    Tree tree2Copy = tree2.treeCopy();
    //select tree1 one crossover point
    //select terminal node as crossover with 10 percent probability. o\w pick function
    
    Node tree1Cross;
    tree1Cross = getCrossType(tree1Copy);
    //select tree2 crossover points
   
    Node tree2Cross;
    tree2Cross = getCrossType(tree2Copy);
   
    tree1Cross.setData(tree2Cross.getData());
    tree1Cross.setLeft(tree2Cross.getLeft());
    tree1Cross.setRight(tree2Cross.getRight());
    return tree1Copy;
    
    
  }
  
  public static Node getCrossType(Tree tree){
     double r = new Random().nextDouble();
    if (r < 0.1){
      return tree.getCross("terminal");
    }
    else {
      return tree.getCross("function");
    }
    
  }
  
  
  
   /* Chooses two trees to reproduce and then shares their genes
   *
   * Parameters:
   *    distribution - array of probabilities of being chosen
   *    trees - array of all trees in a generation
   * 
   *  Return:
   *     tree - random tree
   */
  public static Tree crossover(double[] distribution, Tree[] trees){
    Tree tree1 = chooseRandTree(distribution, trees);
    Tree tree2 = chooseRandTree(distribution, trees);
    if (tree2 == null) System.out.println("Null1");
    while (tree1.equals(tree2)){
      tree2 = chooseRandTree(distribution,trees);
      if (tree2 == null) System.out.println("Null2");
    }
    return reproduce(tree1, tree2);
    
  }
  
  public static void main(String args[]){
   
     
  List<Double> xValues = new ArrayList<Double>();
    List<Double> yValues = new ArrayList<Double>();
    readCSV(xValues, yValues);
  String[] possOperators = {"+", "-", "*", "/"};
  String[] vars = {"x"};
  String[] terms = getIntArray(50);
  int maxDepth = 3;
  population = initializePop(possOperators, terms, maxDepth);
  
  String method = "full";
  createSymbReg(xValues, yValues, terms, possOperators).str();
  
  
  /*
  for (int i = 0; i < 4; i++){
   population[i].printPostOrder();
   System.out.println();
  }
  System.out.println();
  readCSV(xValues,yValues);
  call(xValues,yValues,possOperators,ints);
  for (int i = 0; i < 4; i++){
   population[i].printPostOrder();
   System.out.println();
  }*/
  
  
 }
 
}