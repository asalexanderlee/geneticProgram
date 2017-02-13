
/*The public class that defines the object Node, which will be either an internal node or a leaf node.*/

public class Node {
	private String myData; //will be null when it is a leaf node
	private Node myLeft; //will be null when it is a leaf node
	private Node myRight; //ditto
	
	/*Constructor for the Node object
	 * 
	 * Parameters: 
	 * 		operator - a String representing the operator if it is an Internal Node
	 * 		num - a double representing the number contained in a leaf node
	 * 		left - the left child of an internal node
	 * 		right - the right childe of the internal node
	 * */
	public Node(String data, Node left, Node right){
		myData = data;
		myLeft = left;
		myRight = right;
	}
	
	//A getter for myData
	public String getData(){
		return myData;
	}
	
	public void setData(String data){
		myData = data;
	}
	
	//A getter for the left child; null if there is no left child
	public Node getLeft(){
		return myLeft;
	}
	
	//A getter for the right child; null if there is no right child
	public Node getRight(){
		return myRight;
	}
	
	//A setter for the left child.
	public void setLeft(Node left){
		myLeft = left;
	}
	
	//A setter for the right child.
	public void setRight(Node right){
		myRight = right;
	}
	
	public boolean equals(Object o){
		
		if (o == null || this == null){
			return false;
		}
		Node other = (Node) o;
		String otherData = other.getData();
		String thisData = this.getData();
		
		return otherData.equals(thisData);
	}
}
