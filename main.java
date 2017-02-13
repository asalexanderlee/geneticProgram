
public class main {
	
	private static Tree[] population;
	
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
		test.str();
		test.mutate(ints, possOperators);
		
		population = initializePop(possOperators, ints, maxDepth);
		
		//initialize population with 4 random trees //Ash
		
		//check fitness to see if they work
		//best variable to compare fitness  //Al
		//tournament to choose 2 most fit trees and crossover //Al
		//function that returns crossover 90% of the time, mutation 10% //L
		//bloat,etc //L
		//run until find answer
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
	
}
