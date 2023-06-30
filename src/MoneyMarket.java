import java.util.ArrayList;


public class MoneyMarket {
	
	public static StochaststicPathGenModel riskFreeRatePathModel;
	private static String riskFreeRateName = "LIBOR";
	ModelParameters parameters;
	public static double[] riskFreeRatePath;
	public static double  currentRiskFreeRate;//this will be taken as the average rate of the path
	ArrayList<Double> riskFreeRateHistory = new ArrayList<Double>();
	
	
	public MoneyMarket(ModelParameters param){
		setModelParameters(param);
		MoneyMarket.setRiskFreeRateName(parameters.getAssetName());
		setCurrentRiskFreeRate(parameters.getInitialAssetvalue());
		initaliseRiskFreePath();
	}

	
	/**
	 * this method generates the next path for the risk free asset
	 * it then set the path and computes the next market rate.
	 * This next market rate will be the average rate of the generated path.
	 * It then sets this average rate as the initial value for the next generated path
	 * This is an assumption
	 * 
	 * This method will be called at every full model iteration 
	 * (i.e. if model is based on quarterly data then it will be called each quarter)
	 */
	public void generateNextPath(){
		MoneyMarket.riskFreeRatePathModel.generatePaths();
		setRiskFreeRatePath();
		setCurrentRiskFreeRate();
		MoneyMarket.riskFreeRatePathModel.setInitialAssetValue(MoneyMarket.currentRiskFreeRate);
	}
	
	private void setCurrentRiskFreeRate(double initialValue) {
		// TODO Auto-generated method stub
		MoneyMarket.currentRiskFreeRate = initialValue;
		riskFreeRateHistory.add(MoneyMarket.currentRiskFreeRate);
	}

	private void setCurrentRiskFreeRate() {
		// TODO Auto-generated method stub
		MoneyMarket.currentRiskFreeRate = Means.geometricMean(MoneyMarket.riskFreeRatePath);
		riskFreeRateHistory.add(MoneyMarket.currentRiskFreeRate);
	}



	private void setRiskFreeRatePath() {
		// It is assumed that there is only one riskfree rate and so the size of the 
		//singleIterationAssetPaths arraylist will always be 1 hence the 0 index
		MoneyMarket.riskFreeRatePath = MoneyMarket.riskFreeRatePathModel.getSingleIterationAssetPaths().get(0).getPath();
	}



	private void initaliseRiskFreePath() {
		// TODO Auto-generated method stub
		MoneyMarket.riskFreeRatePathModel = new StochaststicPathGenModel(this.parameters);
	}


	private void setModelParameters(ModelParameters param) {
		// TODO Auto-generated method stub
		this.parameters = param;
	}


	public static String getRiskFreeRateName() {
		return riskFreeRateName;
	}


	public static void setRiskFreeRateName(String riskFreeRateName) {
		MoneyMarket.riskFreeRateName = riskFreeRateName;
	}

}
