import java.util.Random;

public class CallMethod {

	public static void Call(){
		// Tree[] Population new Tree[#Number of Random Trees] 
		//Insert Random Population 
		//for (int i =0; i < Tree[].size(); i++  ){
		Random rand = new Random();  
		double prob = rand.nextDouble(); 
		if(prob < .10){
			//Tree child = new Tree; 
			//Tree child = Mutation();
		}
		else{
			//Tree child = new Tree; 
			//child = Crossover(); 
		}
		//Tree[].add(child) 

	}
	//}


	public static void main(String args[]){ 
		Call(); 
	}



}
