import java.util.Random;


public class DistributionBasedRandomNumberGenerator {
	
	private static double second;
	private static boolean secondValid = false;
	private static Random randomGenerator = new Random();
	
	/**
	 * Method created to return an integer generated from a poisson process
	 * @param lambda
	 * @return
	 */
	public static int getPoisson(double lambda){
		double L = Math.exp(-lambda);
		double p = 1.0;
		int k = 0;
		
		do {
			k++;
			p*=Math.random();
		} while (p > L);
		
		return k - 1;
	}
	
	
	/**
	 * Method created to return an integer generated from a binomial process
	 * @param n
	 * @param p
	 * @return
	 */
	public static int getBinomial(int n, double p){
		int x = 0;
		for(int i = 0; i < n; i++){
			if(Math.random() < p){
				x++;
			}
		}
		return x;
	}
	
	
	/**
	 * This method returns an approximately Gaussian distributed random number with mean mean and standard deviation std
	 * @param mean
	 * @param std
	 * @return
	 */
	static double getNormal(double mean, double std){
		
		return mean + DistributionBasedRandomNumberGenerator.randomGenerator.nextGaussian()*Math.pow(std, 2);
		
	}
	

}
