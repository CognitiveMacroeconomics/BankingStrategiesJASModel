

public class BaselIICapital {
	
	public static final int [] rating = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	
	public static final int [] ratingABX_HE = {1, 2, 4, 7, 8};
	
	public static final double [] riskWeightingABX_HE = {0.2, 0.2, 0.5, 1.0, 1.0};
	
	public static double [] abxTrancheStructure = {0.8,0.05,0.05,0.05,0.05};
	
	public static double [] equalTrancheStructure = {0.2,0.2,0.2,0.2,0.2};
	
	public static final double[] modifiedBaselIRMBS = {0.5, 0.5, 0.5, 0.5, 0.5};
//	public static final double[] modifiedBaselIRMBS = {1.0, 1.0, 1.0, 1.0, 1.0};
	
	public static final double [] baselIISAproachSecuritizedExposureRiskWieghts = {0.2, 0.2, 0.5, 0.5, 0.5, 1.0, 1.0, 1.0, 3.5, 3.5, 3.5, 12.50};

	
	public static final double [] baselIIIRBAproachSenGranSecuritizedExposureRiskWieghts = {0.07, 0.08, 0.10, 0.12, 0.20, 0.35, 0.60, 1, 2.5, 4.25, 6.50, 12.50};

	
	public static final double [] baselIIIRBAproachGranSecuritizedExposureRiskWieghts = {0.12, 0.15, 0.18, 0.20, 0.35, 0.5, 0.75, 1, 2.5, 4.25, 6.50, 12.50};

	
	public static final double [] baselIIIRBAproachNonGranSecuritizedExposureRiskWieghts = {0.2, 0.25, 0.35, 0.35, 0.35, 0.5, 0.75, 1, 2.5, 4.25, 6.50, 12.50};
	
	public BaselIICapital(){
		
	}

}
