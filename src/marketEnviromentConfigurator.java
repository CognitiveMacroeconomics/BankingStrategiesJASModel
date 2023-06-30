/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 * 
 * @author segun
 */
public class marketEnviromentConfigurator {

	int maximumEarningMethod; // determines how investor chooses to allocate
								// wealth between the various tranches of notes.
	// maximumEarningMethod can be a member of {0,1,2,3}.
	boolean crm;// determines if model uses Credit risk mitigation Default value
				// is true
	boolean leveragedTrades;// determines if model uses leveraged trades Default

	// value
	// is false
	boolean baselII;// determines if the risk weights to use come from Basel I
					// or Basel II.
	double vThreshold; // determines the volatility threshold. Note that the
						// lower this value is the more likey
	// investors are to unwind their trade positions.
	// high values are more akin with creditors and interest groups who want an
	// investment to succeed
	// or have a long term view of the investment.
	double counterpartyRiskWeight; // DEFAULT COUNTER PARTY RISK WEIGHT. WHERE
									// THE CRM OPTION IS CHOSEN THE COUNTERPARTY
									// RISK WEIGHTS OF THE INDIVIDUAL
	// TRANCHES WIL BE USED BY THE CDSBASISTRADE CLASS THIS IS A DEFAULT BY
	// DESIGN FOR RISK WEIGHTS IN FORM OF ARRAYS THE RELEVANT METHOD CALL WILL
	// NEED TO BE
	// OVERLOADED.
	double[] counterpartyRiskW;
	int numOfQuarters;// This is defendant on the number of data points the user
						// has
	int startYear; // required for plotting series into JFreeChart.
	int startQuarter;
	int leverageStartYear; // required for leveraged trades.
	int leverageStartQuarter; // required for leveraged trades.
	double haircut;// THIS IS REQUIRED WHEN LEVERAGE IS CONSIDERED. LEVERAGED
					// TRADES HAVE SO FAR NOT BEEN CODED BUT I MAY ADD THEM
					// LATER ON
	private boolean allRMBSBoolean;
	private boolean tradePositionUnwindsPermited;
	private int cdsPriceChangeDataChoiceSelection;

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public marketEnviromentConfigurator() {
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Base Model
		// Configuration>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		this.maximumEarningMethod = 3; // determines how investor chooses to
										// allocate wealth between the various
										// tranches of notes.
		// maximumEarningMethod can be a member of {0,1,2,3}.
		this.crm = true;// determines if model uses Credit risk mitigation
						// Default value is true
		this.baselII = false;// determines if the risk weights to use come from
								// Basel I or Basel II.
		this.vThreshold = 7.7; // determines the volatility threshold. Note that
								// the lower this value is the more likey
		// investors are to unwind their trade positions.
		// high values are more akin with creditors and interest groups who want
		// an investment to succeed
		// or have a long term view of the investment.
		this.counterpartyRiskWeight = 0.2; // DEFAULT COUNTER PARTY RISK WEIGHT.
											// WHERE THE CRM OPTION IS CHOSEN
											// THE COUNTERPARTY RISK WEIGHTS OF
											// THE INDIVIDUAL
		// TRANCHES WIL BE USED BY THE CDSBASISTRADE CLASS THIS IS A DEFAULT BY
		// DESIGN FOR RISK WEIGHTS IN FORM OF ARRAYS THE RELEVANT METHOD CALL
		// WILL NEED TO BE
		// OVERLOADED.
		this.counterpartyRiskW = new double[] { 0.2, 0.0 };
		this.numOfQuarters = 7;// This is defendant on the number of data points
								// the user has

		this.startYear = 2006; // required for plotting series into JFreeChart.
		this.haircut = 1.0;// THIS IS REQUIRED WHEN LEVERAGE IS CONSIDERED.
							// LEVERAGED TRADES HAVE SO FAR NOT BEEN CODED BUT I
							// MAY ADD THEM LATER ON

	}// end base model configuration constructor method

	public marketEnviromentConfigurator(int maxEM, boolean unwinds, boolean CRM,
			boolean regRegime, double vThres, int quaters, int startY, int startQ,
			double hCut, boolean leverage, int leverageStartYear, int leverageStartQtr) {
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<USER DEFINED
		// CONFIGURATION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		this.maximumEarningMethod = maxEM; // determines how investor chooses to
											// allocate wealth between the
											// various tranches of notes.
		// maximumEarningMethod can be a member of {0,1,2,3}.
		this.setUnwindingOfPositions(unwinds);
		this.crm = CRM;// determines if model uses Credit risk mitigation
						// Default value is true
		this.baselII = regRegime;// determines if the risk weights to use come
									// from Basel I or Basel II.
		this.vThreshold = vThres; // determines the volatility threshold. Note
									// that the lower this value is the more
									// likey
		// investors are to unwind their trade positions.
		// high values are more akin with creditors and interest groups who want
		// an investment to succeed
		// or have a long term view of the investment.
		this.counterpartyRiskWeight = 0.2; // DEFAULT COUNTER PARTY RISK WEIGHT.
											// WHERE THE CRM OPTION IS CHOSEN
											// THE COUNTERPARTY RISK WEIGHTS OF
											// THE INDIVIDUAL
		// TRANCHES WIL BE USED BY THE CDSBASISTRADE CLASS THIS IS A DEFAULT BY
		// DESIGN FOR RISK WEIGHTS IN FORM OF ARRAYS THE RELEVANT METHOD CALL
		// WILL NEED TO BE
		// OVERLOADED.
		this.counterpartyRiskW = new double[] { 0.2, 0.0 };
		this.numOfQuarters = quaters;// This is defendant on the number of data
										// points the user has

		this.startYear = startY; // required for plotting series into
									// JFreeChart.
		
		this.startQuarter = startQ;
		
		this.haircut = hCut;// THIS IS REQUIRED WHEN LEVERAGE IS CONSIDERED.
							// LEVERAGED TRADES HAVE SO FAR NOT BEEN CODED BUT I
							// MAY ADD THEM LATER ON
		
		this.leveragedTrades = leverage;
		
		this.leverageStartYear = leverageStartYear;
		
		this.leverageStartQuarter = leverageStartQtr;

	}// end user defined simple model conficuration constructor

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND
	// SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public int getLeverageStartYear() {
		return leverageStartYear;
	}

	public void setLeverageStartYear(int leverageStartYear) {
		this.leverageStartYear = leverageStartYear;
	}

	public int getLeverageStartQuarter() {
		return leverageStartQuarter;
	}

	public void setLeverageStartQuarter(int leverageStartQuarter) {
		this.leverageStartQuarter = leverageStartQuarter;
	}

	public int getMaximumEarningMethod() {
		return this.maximumEarningMethod;
	}

	public boolean getCRM() {
		return this.crm;
	}

	public boolean getBasel() {
		return this.baselII;
	}
	
	public boolean isUnwindingOfPositions() {
		return this.tradePositionUnwindsPermited;
	}


	public double getVolThreshold() {
		return this.vThreshold;
	}

	public double getCounterPartyRiskWeight() {
		return this.counterpartyRiskWeight;
	}

	public double[] getCounterPartyRiskWeights() {
		return this.counterpartyRiskW;
	}

	public int getNumOfQuarters() {
		return this.numOfQuarters;
	}

	public int getStartYear() {
		return this.startYear;
	}

	public int getStartQuarter() {
		return this.startQuarter;
	}
	
	public double getHaircut() {
		return this.haircut;
	}
	
	public void setUnwindingOfPositions(boolean modelType) {
		// TODO Auto-generated method stub
		this.tradePositionUnwindsPermited = modelType;
	}
	
	public boolean isLeveragedTrades() {
		return leveragedTrades;
	}

	public void setLeveragedTrades(boolean leveragedTrades) {
		this.leveragedTrades = leveragedTrades;
	}
	
	public void setAllRMBSBoolean(boolean allRMBS){
		this.allRMBSBoolean = allRMBS;
	}
	
	public boolean isAllRMBSBoolean() {
		return allRMBSBoolean;
	}


	public void setMarkToMarketPrices(int cdsPriceChangeDataChoiceSelection) {
		// TODO Auto-generated method stub
		this.cdsPriceChangeDataChoiceSelection = cdsPriceChangeDataChoiceSelection;
	}

	public int getMarkToMarketPrices() {
		// TODO Auto-generated method stub
		return this.cdsPriceChangeDataChoiceSelection;
	}

	
	public String toString(){
		return new String(this.getClass() + " The Basel I Model is to be run " + this.getBasel() 
				+ " the simulation start year is: " + this.getStartYear() + " the simulation start quarter is: " + this.getStartQuarter() 
				+ " the simulation volatility threshold is: " + this.getVolThreshold());
		
	}

}
