

public class Means {
	
	
	
	 // Returns the geometric mean of a data set
    public static double geometricMean(double[] data)
    {
        double sum = 1.0; 
        if(data.length >0){
        	sum = data[0];
        }
        double geoMean = 0;
 
        
        if(data.length > 1){
        	for (int i = 1; i < data.length; i++){
        	    sum *= data[i];
            }
        }
        
 
        geoMean = Math.pow(sum, (1.0 / data.length));
//        System.out.println("Geometric Mean: "+geoMean);
        return geoMean;
    }
    
    
	 // Returns the geometric mean of a data set
    public static double arithmeticMean(double[] data)
    {
        double sum = data[0];
        double arithMean = 0;
 
        for (int i = 1; i < data.length; i++)
        {
            sum += data[i];
        }
 
        arithMean = (sum / data.length);
//        System.out.println("Geometric Mean: "+geoMean);
        return arithMean;
    }


}
