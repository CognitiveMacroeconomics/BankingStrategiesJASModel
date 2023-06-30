import javax.swing.JOptionPane;


public class ModelParameters {
	
	
	
	
	
	private boolean defaultModel;
	private boolean modelRMBSDataType;
	private boolean tradePositionUnwindsPermited;
	private int modelBanksSubset;
	private boolean creditRiskMitigation;
	private int basisMaximisationStrategy;
	private boolean regulatoryRegime;
	private double volatilityThreshold;
	private int numberOfSimulationQuarters;
	private int simulationStartYear;
	private int simulationStartQuarter;
	private double leverageHaircut;
	private boolean leveragedTrading;
	private int leverageStartYear;
	private int leverageStartQuarter;
	private int cdsPriceChangeDataChoiceSelection;
	
	private String stochasticProcessTypeStrng;
	private int stochasticProcessTypeStringIndex;
	

	private int numberOfIterations;
	private int numberOfPaths;
	private int pathLength;
	private String assetName;
	private double initialAssetvalue;
	private double driftMean;
	private double standardDeviation;
	private double timeShit_dt;
	private double cir_Alpha;
	private double cir_Theta;
	private double jumpIntensity;
	private double jump_Mean_Size;
	private double jump_Size_Distribution_Width;
	private double jumpCorrelation;
	private double hestonLongTermVariance;
	private double hestonMeanReversionRate;
	private double hestonVarianceVolatility;
	
	
	private int natureOfEconomicExpectations; 
	private boolean homogeneousAgents;
	
	
	private double AAATrancheProbSurvivalAfterT;
	private double AATrancheProbSurvivalAfterT;
	private double ATrancheProbSurvivalAfterT;
	private double BBBTrancheProbSurvivalAfterT;
	private double BBB3TrancheProbSurvivalAfterT;

	private double AAATrancheProbSurvivalAfterTm1;
	private double ATrancheProbSurvivalAfterTm1;
	private double BBBTrancheProbSurvivalAfterTm1;
	private double AATrancheProbSurvivalAfterTm1;
	private double BBB3TrancheProbSurvivalAfterTm1;

	private double AAATrancheCoupon;
	private double AATrancheCoupon;
	private double ATrancheCoupon;
	private double BBBTrancheCoupon;
	private double BBB3TrancheCoupon;
	
	private double discountFactor;
	private double annuityFactor;

	
	
	//ABM Trading Model Data
	public static double buyBondOlnyShareOfTraders = 1/6;
	public static double sellBondOnlyShareOfTraders = 1/6;
	public static double buyCDSOnlyShareOfTraders = 1/6;
	public static double sellCDSOnlyShareOfTraders = 1/6;
	public static double buyBondAndCDSShareOfTraders = 1/6;
	public static double sellBondAndCDSShareOfTraders = 1/6;
	public static double maximumHoldingPeriod = 0.8;
	public static double minimumHoldingPeriod = 0.1;
	public static double maximumExpectedDefaultRate = 0.15;
	public static double minimumExpectedDefaultRate = 0.01;
	public static double tradingABMDriftParameter = 0.2;
	public static double poissonBondMaturityArrivalRate = 0.2;
	public static int eventPeriodWindowStart = 12;
	public static int eventPeriodWindow = 21;
	public static double genericImpactFraction = 0.1;
	public static String genericImpactEconomicCondition = EconomicConditions.CONDITION_2;
	
	
	public ModelParameters(){
		
	}
	
	

	public void setDefualtModel(boolean modelType) {
		// TODO Auto-generated method stub
		this.defaultModel = modelType;
	}
	
	public void setUnwindingOfPositions(boolean modelType) {
		// TODO Auto-generated method stub
		this.tradePositionUnwindsPermited = modelType;
	}

	public void setModelRMBSDataType(boolean modelRMBSDataType) {
		// TODO Auto-generated method stub
		this.modelRMBSDataType = modelRMBSDataType;
	}

	public void setBanksSubset(int modelBanksSubset) {
		// TODO Auto-generated method stub
		this.modelBanksSubset = modelBanksSubset;
	}

	public void setCreditRiskMitigation(boolean crm) {
		// TODO Auto-generated method stub
		this.creditRiskMitigation = crm;
	}
	
	public void setBasisMaximisationStrategy(int basisMaximisationStrategy) {
		// TODO Auto-generated method stub
		this.basisMaximisationStrategy = basisMaximisationStrategy;
	}


	public void setRegulatoryRegime(boolean regR) {
		// TODO Auto-generated method stub
		this.regulatoryRegime = regR;
	}

	public void setVolatilityThreshold(double vThreshold) {
		// TODO Auto-generated method stub
		this.volatilityThreshold = vThreshold;
	}

	public void setNumberOfSimulationQuarters(int numOfQuarters) {
		// TODO Auto-generated method stub
		this.numberOfSimulationQuarters = numOfQuarters;
	}

	public void setSimulationStartYear(int startyear) {
		// TODO Auto-generated method stub
		this.simulationStartYear = startyear;
	}
	
	public void setSimulationStartQuarter(int startQutr) {
		// TODO Auto-generated method stub
		this.simulationStartQuarter = startQutr;
	}

	public void setLeverageHaircut(double haircut) {
		// TODO Auto-generated method stub
		this.leverageHaircut = haircut;
	}

	public void setLeveragedTrading(boolean leveragedTrades) {
		// TODO Auto-generated method stub
		this.leveragedTrading = leveragedTrades;
	}

	public void setLeverageStartYear(int leverageStartyear) {
		// TODO Auto-generated method stub
		this.leverageStartYear = leverageStartyear;
	}

	public void setLeverageStartQuarter(int leverageStartQutr) {
		// TODO Auto-generated method stub
		this.leverageStartQuarter = leverageStartQutr;
	}


	
	
	
	public boolean isDefaultModel() {
		return this.defaultModel;
	}
	
	public boolean isUnwindingOfPositions() {
		return this.tradePositionUnwindsPermited;
	}


	public boolean isModelRMBSDataType() {
		return this.modelRMBSDataType;
	}

	public int getModelBanksSubset() {
		return this.modelBanksSubset;
	}

	public int getBasisMaximisationStrategy() {
		return this.basisMaximisationStrategy;
	}

	public boolean isCreditRiskMitigation() {
		return this.creditRiskMitigation;
	}

	public boolean isRegulatoryRegime() {
		return this.regulatoryRegime;
	}

	public double getVolatilityThreshold() {
		return this.volatilityThreshold;
	}

	public int getNumberOfSimulationQuarters() {
		return this.numberOfSimulationQuarters;
	}

	public int getSimulationStartYear() {
		return this.simulationStartYear;
	}

	public int getSimulationStartQuarter() {
		return this.simulationStartQuarter;
	}

	public double getLeverageHaircut() {
		return this.leverageHaircut;
	}

	public boolean isLeveragedTrading() {
		return this.leveragedTrading;
	}

	public int getLeverageStartYear() {
		return this.leverageStartYear;
	}

	public int getLeverageStartQuarter() {
		return this.leverageStartQuarter;
	}
	
	

	public void setMarkToMarketPrices(int cdsPriceChangeDataChoiceSelection) {
		// TODO Auto-generated method stub
		this.cdsPriceChangeDataChoiceSelection = cdsPriceChangeDataChoiceSelection;
	}

	public int getMarkToMarketPrices() {
		// TODO Auto-generated method stub
		return this.cdsPriceChangeDataChoiceSelection;
	}


	

	/**
	 * @return the numberOfIterations
	 */
	public int getNumberOfIterations() {
		return numberOfIterations;
	}



	/**
	 * @param numberOfIterations the numberOfIterations to set
	 */
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}



	/**
	 * @return the numberOfPaths
	 */
	public int getNumberOfPaths() {
		return numberOfPaths;
	}



	/**
	 * @param numberOfPaths the numberOfPaths to set
	 */
	public void setNumberOfPaths(int numberOfPaths) {
		this.numberOfPaths = numberOfPaths;
	}



	/**
	 * @return the pathLength
	 */
	public int getPathLength() {
		return pathLength;
	}



	/**
	 * @param pathLength the pathLength to set
	 */
	public void setPathLength(int pathLength) {
		this.pathLength = pathLength;
	}



	/**
	 * @return the assetName
	 */
	public String getAssetName() {
		return assetName;
	}



	/**
	 * @param assetName the assetName to set
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}



	/**
	 * @return the initialAssetvalue
	 */
	public double getInitialAssetvalue() {
		return initialAssetvalue;
	}



	/**
	 * @param initialAssetvalue the initialAssetvalue to set
	 */
	public void setInitialAssetvalue(double initialAssetvalue) {
		this.initialAssetvalue = initialAssetvalue;
	}



	/**
	 * @return the driftMean
	 */
	public double getDriftMean() {
		return driftMean;
	}



	/**
	 * @param driftMean the driftMean to set
	 */
	public void setDriftMean(double driftMean) {
		this.driftMean = driftMean;
	}



	/**
	 * @return the standardDeviation
	 */
	public double getStandardDeviation() {
		return standardDeviation;
	}



	/**
	 * @param standardDeviation the standardDeviation to set
	 */
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}



	/**
	 * @return the timeShit_dt
	 */
	public double getTimeShit_dt() {
		return timeShit_dt;
	}



	/**
	 * @param timeShit_dt the timeShit_dt to set
	 */
	public void setTimeShit_dt(double timeShit_dt) {
		this.timeShit_dt = timeShit_dt;
	}



	/**
	 * @return the cir_Alpha
	 */
	public double getCir_Alpha() {
		return cir_Alpha;
	}



	/**
	 * @param cir_Alpha the cir_Alpha to set
	 */
	public void setCir_Alpha(double cir_Alpha) {
		this.cir_Alpha = cir_Alpha;
	}



	/**
	 * @return the cir_Theta
	 */
	public double getCir_Theta() {
		return cir_Theta;
	}



	/**
	 * @param cir_Theta the cir_Theta to set
	 */
	public void setCir_Theta(double cir_Theta) {
		this.cir_Theta = cir_Theta;
	}



	/**
	 * @return the jumpIntensity
	 */
	public double getJumpIntensity() {
		return jumpIntensity;
	}



	/**
	 * @param jumpIntensity the jumpIntensity to set
	 */
	public void setJumpIntensity(double jumpIntensity) {
		this.jumpIntensity = jumpIntensity;
	}



	/**
	 * @return the jump_Mean_Size
	 */
	public double getJump_Mean_Size() {
		return jump_Mean_Size;
	}



	/**
	 * @param jump_Mean_Size the jump_Mean_Size to set
	 */
	public void setJump_Mean_Size(double jump_Mean_Size) {
		this.jump_Mean_Size = jump_Mean_Size;
	}



	/**
	 * @return the jump_Size_Distribution_Width
	 */
	public double getJump_Size_Distribution_Width() {
		return jump_Size_Distribution_Width;
	}



	/**
	 * @param jump_Size_Distribution_Width the jump_Size_Distribution_Width to set
	 */
	public void setJump_Size_Distribution_Width(double jump_Size_Distribution_Width) {
		this.jump_Size_Distribution_Width = jump_Size_Distribution_Width;
	}



	public void setStochasticProcessTypeStringIndex(
			int stochasticProcessTypeStringIndex) {
		// TODO Auto-generated method stub
		this.stochasticProcessTypeStringIndex = stochasticProcessTypeStringIndex;
		
	}



	public void setStochasticProcessTypeStrng(String stochasticProcessTypeStrng) {
		// TODO Auto-generated method stub
		this.stochasticProcessTypeStrng = stochasticProcessTypeStrng;
	}


	public int getStochasticProcessTypeStringIndex() {
		// TODO Auto-generated method stub
		return stochasticProcessTypeStringIndex;
		
	}



	public String getStochasticProcessTypeStrng() {
		// TODO Auto-generated method stub
		return stochasticProcessTypeStrng;
	}
	
	
	
	/**
	 * @return the hestonLongTermVariance
	 */
	public double getHestonLongTermVariance() {
		return hestonLongTermVariance;
	}



	/**
	 * @param hestonLongTermVariance the hestonLongTermVariance to set
	 */
	public void setHestonLongTermVariance(double hestonLongTermVariance) {
		this.hestonLongTermVariance = hestonLongTermVariance;
	}



	/**
	 * @return the hestonMeanReversionRate
	 */
	public double getHestonMeanReversionRate() {
		return hestonMeanReversionRate;
	}



	/**
	 * @param hestonMeanReversionRate the hestonMeanReversionRate to set
	 */
	public void setHestonMeanReversionRate(double hestonMeanReversionRate) {
		this.hestonMeanReversionRate = hestonMeanReversionRate;
	}



	/**
	 * @return the hestonVarianceVolatility
	 */
	public double getHestonVarianceVolatility() {
		return hestonVarianceVolatility;
	}



	/**
	 * @param hestonVarianceVolatility the hestonVarianceVolatility to set
	 */
	public void setHestonVarianceVolatility(double hestonVarianceVolatility) {
		this.hestonVarianceVolatility = hestonVarianceVolatility;
	}
	
	
	
	/**
	 * @return the tradePositionUnwindsPermited
	 */
	public boolean isTradePositionUnwindsPermited() {
		return tradePositionUnwindsPermited;
	}



	/**
	 * @param tradePositionUnwindsPermited the tradePositionUnwindsPermited to set
	 */
	public void setTradePositionUnwindsPermited(boolean tradePositionUnwindsPermited) {
		this.tradePositionUnwindsPermited = tradePositionUnwindsPermited;
	}



	/**
	 * @return the cdsPriceChangeDataChoiceSelection
	 */
	public int getCdsPriceChangeDataChoiceSelection() {
		return cdsPriceChangeDataChoiceSelection;
	}



	/**
	 * @param cdsPriceChangeDataChoiceSelection the cdsPriceChangeDataChoiceSelection to set
	 */
	public void setCdsPriceChangeDataChoiceSelection(
			int cdsPriceChangeDataChoiceSelection) {
		this.cdsPriceChangeDataChoiceSelection = cdsPriceChangeDataChoiceSelection;
	}



	/**
	 * @return the jumpCorrelation
	 */
	public double getJumpCorrelation() {
		return jumpCorrelation;
	}



	/**
	 * @param jumpCorrelation the jumpCorrelation to set
	 */
	public void setJumpCorrelation(double jumpCorrelation) {
		this.jumpCorrelation = jumpCorrelation;
	}



	/**
	 * @return the natureOfEconomicExpectations
	 */
	public int getNatureOfEconomicExpectations() {
		return natureOfEconomicExpectations;
	}



	/**
	 * @param natureOfEconomicExpectations the natureOfEconomicExpectations to set
	 */
	public void setNatureOfEconomicExpectations(int natureOfEconomicExpectations) {
		this.natureOfEconomicExpectations = natureOfEconomicExpectations;
	}



	/**
	 * @return the homogeneousAgents
	 */
	public boolean isHomogeneousAgents() {
		return homogeneousAgents;
	}



	/**
	 * @param homogeneousAgents the homogeneousAgents to set
	 */
	public void setHomogeneousAgents(boolean homogeneousAgents) {
		this.homogeneousAgents = homogeneousAgents;
	}



	/**
	 * @return the aAATrancheProbSurvivalAfterT
	 */
	public double getAAATrancheProbSurvivalAfterT() {
		return AAATrancheProbSurvivalAfterT;
	}



	/**
	 * @param aAATrancheProbSurvivalAfterT the aAATrancheProbSurvivalAfterT to set
	 */
	public void setAAATrancheProbSurvivalAfterT(double aAATrancheProbSurvivalAfterT) {
		AAATrancheProbSurvivalAfterT = aAATrancheProbSurvivalAfterT;
	}



	/**
	 * @return the aATrancheProbSurvivalAfterT
	 */
	public double getAATrancheProbSurvivalAfterT() {
		return AATrancheProbSurvivalAfterT;
	}



	/**
	 * @param aATrancheProbSurvivalAfterT the aATrancheProbSurvivalAfterT to set
	 */
	public void setAATrancheProbSurvivalAfterT(double aATrancheProbSurvivalAfterT) {
		AATrancheProbSurvivalAfterT = aATrancheProbSurvivalAfterT;
	}



	/**
	 * @return the aTrancheProbSurvivalAfterT
	 */
	public double getATrancheProbSurvivalAfterT() {
		return ATrancheProbSurvivalAfterT;
	}



	/**
	 * @param aTrancheProbSurvivalAfterT the aTrancheProbSurvivalAfterT to set
	 */
	public void setATrancheProbSurvivalAfterT(double aTrancheProbSurvivalAfterT) {
		ATrancheProbSurvivalAfterT = aTrancheProbSurvivalAfterT;
	}



	/**
	 * @return the bBBTrancheProbSurvivalAfterT
	 */
	public double getBBBTrancheProbSurvivalAfterT() {
		return BBBTrancheProbSurvivalAfterT;
	}



	/**
	 * @param bBBTrancheProbSurvivalAfterT the bBBTrancheProbSurvivalAfterT to set
	 */
	public void setBBBTrancheProbSurvivalAfterT(double bBBTrancheProbSurvivalAfterT) {
		BBBTrancheProbSurvivalAfterT = bBBTrancheProbSurvivalAfterT;
	}



	/**
	 * @return the bBB3TrancheProbSurvivalAfterT
	 */
	public double getBBB3TrancheProbSurvivalAfterT() {
		return BBB3TrancheProbSurvivalAfterT;
	}



	/**
	 * @param bBB3TrancheProbSurvivalAfterT the bBB3TrancheProbSurvivalAfterT to set
	 */
	public void setBBB3TrancheProbSurvivalAfterT(
			double bBB3TrancheProbSurvivalAfterT) {
		BBB3TrancheProbSurvivalAfterT = bBB3TrancheProbSurvivalAfterT;
	}



	/**
	 * @return the aAATrancheProbSurvivalAfterTm1
	 */
	public double getAAATrancheProbSurvivalAfterTm1() {
		return AAATrancheProbSurvivalAfterTm1;
	}



	/**
	 * @param aAATrancheProbSurvivalAfterTm1 the aAATrancheProbSurvivalAfterTm1 to set
	 */
	public void setAAATrancheProbSurvivalAfterTm1(
			double aAATrancheProbSurvivalAfterTm1) {
		AAATrancheProbSurvivalAfterTm1 = aAATrancheProbSurvivalAfterTm1;
	}



	/**
	 * @return the aTrancheProbSurvivalAfterTm1
	 */
	public double getATrancheProbSurvivalAfterTm1() {
		return ATrancheProbSurvivalAfterTm1;
	}



	/**
	 * @param aTrancheProbSurvivalAfterTm1 the aTrancheProbSurvivalAfterTm1 to set
	 */
	public void setATrancheProbSurvivalAfterTm1(double aTrancheProbSurvivalAfterTm1) {
		ATrancheProbSurvivalAfterTm1 = aTrancheProbSurvivalAfterTm1;
	}



	/**
	 * @return the bBBTrancheProbSurvivalAfterTm1
	 */
	public double getBBBTrancheProbSurvivalAfterTm1() {
		return BBBTrancheProbSurvivalAfterTm1;
	}



	/**
	 * @param bBBTrancheProbSurvivalAfterTm1 the bBBTrancheProbSurvivalAfterTm1 to set
	 */
	public void setBBBTrancheProbSurvivalAfterTm1(
			double bBBTrancheProbSurvivalAfterTm1) {
		BBBTrancheProbSurvivalAfterTm1 = bBBTrancheProbSurvivalAfterTm1;
	}



	/**
	 * @return the aATrancheProbSurvivalAfterTm1
	 */
	public double getAATrancheProbSurvivalAfterTm1() {
		return AATrancheProbSurvivalAfterTm1;
	}



	/**
	 * @param aATrancheProbSurvivalAfterTm1 the aATrancheProbSurvivalAfterTm1 to set
	 */
	public void setAATrancheProbSurvivalAfterTm1(
			double aATrancheProbSurvivalAfterTm1) {
		AATrancheProbSurvivalAfterTm1 = aATrancheProbSurvivalAfterTm1;
	}



	/**
	 * @return the bBB3TrancheProbSurvivalAfterTm1
	 */
	public double getBBB3TrancheProbSurvivalAfterTm1() {
		return BBB3TrancheProbSurvivalAfterTm1;
	}



	/**
	 * @param bBB3TrancheProbSurvivalAfterTm1 the bBB3TrancheProbSurvivalAfterTm1 to set
	 */
	public void setBBB3TrancheProbSurvivalAfterTm1(
			double bBB3TrancheProbSurvivalAfterTm1) {
		BBB3TrancheProbSurvivalAfterTm1 = bBB3TrancheProbSurvivalAfterTm1;
	}



	/**
	 * @return the aAATrancheCoupon
	 */
	public double getAAATrancheCoupon() {
		return AAATrancheCoupon;
	}



	/**
	 * @param aAATrancheCoupon the aAATrancheCoupon to set
	 */
	public void setAAATrancheCoupon(double aAATrancheCoupon) {
		AAATrancheCoupon = aAATrancheCoupon;
	}



	/**
	 * @return the aATrancheCoupon
	 */
	public double getAATrancheCoupon() {
		return AATrancheCoupon;
	}



	/**
	 * @param aATrancheCoupon the aATrancheCoupon to set
	 */
	public void setAATrancheCoupon(double aATrancheCoupon) {
		AATrancheCoupon = aATrancheCoupon;
	}



	/**
	 * @return the aTrancheCoupon
	 */
	public double getATrancheCoupon() {
		return ATrancheCoupon;
	}



	/**
	 * @param aTrancheCoupon the aTrancheCoupon to set
	 */
	public void setATrancheCoupon(double aTrancheCoupon) {
		ATrancheCoupon = aTrancheCoupon;
	}



	/**
	 * @return the bBBTrancheCoupon
	 */
	public double getBBBTrancheCoupon() {
		return BBBTrancheCoupon;
	}



	/**
	 * @param bBBTrancheCoupon the bBBTrancheCoupon to set
	 */
	public void setBBBTrancheCoupon(double bBBTrancheCoupon) {
		BBBTrancheCoupon = bBBTrancheCoupon;
	}



	/**
	 * @return the bBB3TrancheCoupon
	 */
	public double getBBB3TrancheCoupon() {
		return BBB3TrancheCoupon;
	}



	/**
	 * @param bBB3TrancheCoupon the bBB3TrancheCoupon to set
	 */
	public void setBBB3TrancheCoupon(double bBB3TrancheCoupon) {
		BBB3TrancheCoupon = bBB3TrancheCoupon;
	}



	/**
	 * @return the discountFactor
	 */
	public double getDiscountFactor() {
		return discountFactor;
	}



	/**
	 * @param discountFactor the discountFactor to set
	 */
	public void setDiscountFactor(double discountFactor) {
		this.discountFactor = discountFactor;
	}



	/**
	 * @return the annuityFactor
	 */
	public double getAnnuityFactor() {
		return annuityFactor;
	}



	/**
	 * @param annuityFactor the annuityFactor to set
	 */
	public void setAnnuityFactor(double annuityFactor) {
		this.annuityFactor = annuityFactor;
//		 JOptionPane.showMessageDialog(null,
//		 "annuityFactor: "
//		 + annuityFactor, "",
//		 JOptionPane.INFORMATION_MESSAGE);
	}



	/**
	 * @param defaultModel the defaultModel to set
	 */
	public void setDefaultModel(boolean defaultModel) {
		this.defaultModel = defaultModel;
	}



	/**
	 * @param modelBanksSubset the modelBanksSubset to set
	 */
	public void setModelBanksSubset(int modelBanksSubset) {
		this.modelBanksSubset = modelBanksSubset;
	}






	
	public String toString(){
		return new String(this.getClass() + " The Default Model is to be run " + this.defaultModel 
				+ " the simulation start year is: " + this.simulationStartYear + " the simulation start quarter is: " + this.simulationStartQuarter 
				+ " the simulation volatility threshold is: " + this.volatilityThreshold);
		
	}

}
