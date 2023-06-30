

public class contractBlocks {
	
	double capacity; // should be private
	
	double weight; // should be private for good practice
	
	int id;

	private static int edgeCount = 1;

	
	public contractBlocks(double weight, double capacity){
		
		this.id = edgeCount;
		increaseEdgeCount();
		this.weight = weight;
		this.capacity = capacity;
		
	}
	
	private void increaseEdgeCount() {
		// TODO Auto-generated method stub
		this.edgeCount++;
	}

	public String toString(){
		return "E"+id;
	}


}
