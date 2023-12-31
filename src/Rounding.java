

import java.text.DecimalFormat;

public class Rounding {
	
	
	/**
	 * Rounding values to 2 decimal places.
	 * 
	 * I want all values rounded to 2dps
	 * 
	 */
	
	public static double  roundTwoDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
	return Double.valueOf(twoDForm.format(d));
	}
	

	public static double  roundThreeDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.###");
	return Double.valueOf(twoDForm.format(d));
	}
	
	
	public static double  roundFourDecimals(double d) {
	   return (double)Math.round(d * 100000) / 100000;
	}
	

}
