

public class maximum {
	
	
	static double [] numbers = {0.00234633621199996, 0.0047306708329999374, 0.017414818879999988, 0.06648556145, 0.04678200000000003};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getMaximum(numbers));
	}
	private static double getMaximum(double[] numbers) {
		// TODO Auto-generated method stub
		// finding it with a loop 
		double max = numbers[0]; 
		for(int i = 0; i < numbers.length; ++i) { 
			if(numbers[i] > max) { 
				max = numbers[i]; 
			} 
		} 
		// max is now the largest 
		return max;
	}


}
