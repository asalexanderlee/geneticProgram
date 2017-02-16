import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
  
  



public class main { 
  
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
    double fitness = 0;
    for (int i = 0; i < xValues.size(); i++){
      double error = Math.abs(tree.getY(xValues.get(i)) - yValues.get(i));
      fitness += error * error;
    }
    return fitness;
    
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
      double fitness = getFitness(xValues, yValues, trees[i]);
      fitnessLst[i] = fitness;
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
    for (int i = 0; i < distribution.length - 1; i++){
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
    Tree tree2Copy = tree2.treeCopy();
    //select tree1 one crossover point
    //select terminal node as crossover with 10 percent probability. o\w pick function
    double r1 = new Random().nextDouble();
    Node tree1Cross;
    if (r1 < 0.1){
      tree1Cross = tree1Copy.getCross("terminal");
    }
    else {
      tree1Cross = tree1Copy.getCross("function");
    }
    //select tree2 crossover points
    double r2 = new Random().nextDouble();
    Node tree2Cross;
    if (r2 < 0.1){
      tree2Cross = tree2Copy.getCross("terminal");
    }
    else {
       tree2Cross = tree2Copy.getCross("function");
    }
   
    tree1Cross.setData(tree2Cross.getData());
    tree1Cross.setLeft(tree2Cross.getLeft());
    tree1Cross.setRight(tree2Cross.getRight());
    return tree1Copy;
    
    
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
    while (tree1.equals(tree2)){
      tree2 = chooseRandTree(distribution,trees);
    }
    return reproduce(tree1, tree2);
    
  }
  
  
  
 private static String[] getIntArray(int numInt){
  String[] intArray = new String[numInt];
  for (int i = 1; i < numInt+1; i++){
   intArray[i-1] = i + "";
  }
  return intArray;
 }
 public static void main(String args[]){
  
  String[] possOperators = {"+", "-", "*", "/"};
  String[] ints = getIntArray(50);
  int maxDepth = 2;
  String method = "grow";
  Tree test = new Tree(possOperators, ints, maxDepth, method);
  List<Double> xValues = new ArrayList<Double>();
  List<Double> yValues = new ArrayList<Double>();
  readCSV(xValues, yValues);
  System.out.println(xValues.size());
  System.out.println(yValues.size());
  System.out.println(xValues.get(24999));
  
  
  
 }
 
}
