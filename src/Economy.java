import jas.engine.Sim;

import java.util.ArrayList;


public class Economy {

	
	public static MoneyMarket moneyMarket;
	
	public static double currentCDSSpread;
	
	public static double currentCashSpread;
	
	public static double currentLiborRate;
	
	public static double currentAverageLiborRate;//used for the real time or stochastic toy model to see how changes 
												//in economic conditions and expectations affect basis
	
	public static double currentDiscountFactor;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static double currentAnnuityFactor;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static double initialLibor;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static double liborLongTermRate;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static double liborMeanReversionRate;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static double liborStandardDeviation;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static double liborPathLength;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static String liborRateName;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static double liborTimeShift;//used for the real time or stochastic toy model to see how changes 
	//in economic conditions and expectations affect basis
	
	public static ArrayList<double[][]> cDSSpreads = new ArrayList<double[][]>();
	
	public static ArrayList<double[]> cashSpreads = new ArrayList<double[]>();
	
	public static ArrayList<double[]> ChangeInABXIndexPrice = new ArrayList<double[]>();
	
	public static ArrayList<double[]> markoseABXPriceChangeData = new ArrayList<double[]>();
	
	public static double[] liborRates;
	
	public static double[] liborRatesStochasticPath;
	
	public static double[] defaultLIBORBorrowingRates;
	
	public static double[] bondCouponRates;
	
	public static boolean activeTrading = false;//varriable to determine if agents trade actively or not. Defaults to false
	
	
	
	//Variables for ABM Trading Agents
	public ArrayList<TradingAgent> tradingAgentsList = new ArrayList<TradingAgent>();//collection of trading agents in the model TAL in parameter list
	public static CDSMarket cdsMarket;//where cds prices are set
	public static BondMarket bondMarket;//where bond prices are set
	public static double tradingABMBondDriftParameter = 0.9;//mu in the model parameters list
	public static double tradingABMCDSDriftParameter = 0.1;//mu in the model parameters list
	public static double tradingABMBondMeanReversionParameter = 0.07;//mu in the model parameters list
	public static double tradingABMCDSMeanReversionParameter = 0.07;//mu in the model parameters list
	public static double tradingABMBondNoiceParameter = 0.2;//sigma in the model parameters list
	public static double tradingABMCDSNoiceParameter = 0.2;//sigma in the model parameters list
	public static double poissonBondMaturityArrivalRate = 0.2;//omega in the model parameters list
	public static double averageExpectedDefaultProbability = 0.1;//pi-bar in the model parameters list
	public static double bondSupplyDelta = 0;
	public static double riskFreeBondPrice = 1;
	public static String eventType;
	public static double eventValueDefaultProbability = 0.02;//EVP in parameter list
	public static double eventValueBondPrice = 0.1;//EV in parameter list
	public static double eventTypeMultiplier = 0; // ETM = {-1,0,1} for {bear, normal, bull} or {decline, normal, growth}
	public static double eventImpactBondPrice;//gamma in model parameter list: product of ETM*EV
	public static double eventImpactDefaultProbability;//nu in model parameter list: product of ETM*EVP
	public static int eventStartPeriod = 15;//js in the model parameter list
	public static int eventEndPeriod = 21;//je in the model parameter list
	public static boolean eventTriggered = false;
	public static double currentBondSupply = 0;
	public static double currentCDSSupply = 0;
	public static double currentBondMarketBidPrice = 0;
	public static double currentCDSMarketBidPrice = 0;
	public static double currentBondMarketAskPrice = 0;
	public static double currentCDSMarketAskPrice = 0;
	public static double previousBondSupply = 0;
	public static double previousCDSSupply = 0;
	public static double bondFundingCost = 0.05;
	public static double cdsFundingCost = 0;
	public static ArrayList<Double> bondSupplyHistory = new ArrayList<Double>();
	public static ArrayList<Double> cdsSupplyHistory = new ArrayList<Double>();

	
	
	
	
	//no arg constructor
	public Economy(){
		
		setMarkoseABXPriceChangeData();
	}
	
	
	//no arg constructor
	public Economy(ModelParameters params){
		
		setMarkoseABXPriceChangeData();
		createMoneyMarket(params);
	}


	
	
	public void setPeriodicChangeInABXIndexPrice(){
		/**
		 * This method is used to compute the percentage change in the price of the ABX.HE index
		 * from one time period to the next
		 * 
		 * 1/
		 * start by getting the Absolute time from the sim model. 
		 * 
		 * 2/
		 * Divide this amount by 3 because the default model uses quarterly data
		 * (this method can be adjusted to use more generic form by simply setting the period lengths in the 
		 * environment class and making a call to that class or simply setting the period lengths as a 
		 * variable in the Economy class)
		 * 
		 * 3/
		 * next create the time t and t-1 containers for the cds market data tables with rows {Spread, Price, Coupon} and 
		 * columns {AAA, AA, A, BBB, BBB-}
		 * 
		 * 4/
		 * finally create the container to store the computed price change percentage in its decimal form
		 * 
		 * 5/
		 * now to collect the computed data simply go through each column of the price row of both and 
		 * find the matching columns defining each respective tranche and do the calculation
		 * 
		 * the if(i == j) statement is not really necessary and is inefficient however it is used to 
		 * make sure that the correct tranches are matched from both t and t-1 tables  
		 *  
		 */
		int time = (int) (Sim.getAbsoluteTime()/3);
		double [][] abxSpread_Tm1;
		double [][] abxSpread_T;
		double [] priceChange_T;
		
		if(time > 0){
			abxSpread_T = this.getCDSSPreads().get(time);
			abxSpread_Tm1 = this.getCDSSPreads().get(time - 1);
			priceChange_T = new double[abxSpread_T[1].length];
			for(int i = 0; i < abxSpread_T[1].length; i++){
				for(int j = 0; j < abxSpread_Tm1[1].length; j++){
					if(i == j){
						priceChange_T[j] = -1*(abxSpread_T[1][j] - abxSpread_Tm1[1][j])/abxSpread_Tm1[1][j];
					}
				}
			}
		}else {
			abxSpread_T = this.getCDSSPreads().get(time);
			abxSpread_Tm1 = this.getCDSSPreads().get(time - 1);
			priceChange_T = new double[abxSpread_T[1].length];
			for(int j = 0; j < abxSpread_Tm1[1].length; j++){
				priceChange_T[j] = 0;
			}
		}
		ChangeInABXIndexPrice.add(priceChange_T);
	}
	
	
	
	private void setMarkoseABXPriceChangeData(){
		/**
		 * This method manually set the changed in the ABX.HE prices 
		 * to match the data Sheri pulled from a report where all 
		 * ABH.HE vintages and their respective tranches and initial tranche prices
		 * were listed.
		 * 
		 * I did not have this data when I initially wrote the simulation
		 */
		double[] abxPriceChange2006Q1 = {0, 0, 0, 0, 0};
		double[] abxPriceChange2006Q2 = {0, 0, 0, 0, 0};
		double[] abxPriceChange2006Q3 = {0, 0, 0, 0, 0};
		double[] abxPriceChange2006Q4 = {0, 0, 0, 0, 0};
		double[] abxPriceChange2007Q1 = {0, 0, 0, 0.05, 0.25};
		double[] abxPriceChange2007Q2 = {0, 0, 0, 0.10, 0.10};
		double[] abxPriceChange2007Q3 = {0, 0.10, 0, 0.20, 0.40};
		
		markoseABXPriceChangeData.add(abxPriceChange2006Q1);
		markoseABXPriceChangeData.add(abxPriceChange2006Q2);
		markoseABXPriceChangeData.add(abxPriceChange2006Q3);
		markoseABXPriceChangeData.add(abxPriceChange2006Q4);
		markoseABXPriceChangeData.add(abxPriceChange2007Q1);
		markoseABXPriceChangeData.add(abxPriceChange2007Q2);
		markoseABXPriceChangeData.add(abxPriceChange2007Q3);
		
	}
	
	public ArrayList<double[]> getPeriodicChangeInABXIndexPrice(){
		return ChangeInABXIndexPrice;
	}
	
	
	public ArrayList<double[]> getMarkoseABXPriceChangeData(){
		return markoseABXPriceChangeData;
	}
	
	
	
	public void setCurrentCDSSPread(double cdsSpread){
		Economy.currentCDSSpread = cdsSpread;
	}
	
	
	
	public void setCurrentCashSpread(double CashSpread){
		Economy.currentCashSpread = CashSpread;
	}
	
	
	public void setCurrentLiborRate(double LiborRate){
		Economy.currentLiborRate = LiborRate;
	}
	

	
	/**
	 * 
	 * @param arrayList
	 */
	
	public void setCurrentCDSSPreads(ArrayList<double[][]> cdsSpreads){
		Economy.cDSSpreads.addAll(cdsSpreads);
	}
	
	
	
	public void setCurrentCashSpreads(ArrayList<double[]> cashSpreads){
		Economy.cashSpreads.addAll(cashSpreads);
	}
	
	
	public void setCurrentLiborRates(double[] LiborRate){
		Economy.liborRates = LiborRate;
	}

	
	public void setDefaultLIBORBorrowingRates(double[] dLbRate){
		Economy.defaultLIBORBorrowingRates = dLbRate;
	}

	
	//<<<<<<<<<<<<<<<<<<<getters>>>>>>>>>>>>>>>>>>>
	public double getCurrentCDSSPread(){
		return currentCDSSpread;
	}
	
	
	public double getCurrentCashSPread(){
		return currentCashSpread;
	}

	
	public double getCurrentLiborRate(){
		return currentLiborRate;
	}

	
	public ArrayList<double[][]> getCDSSPreads(){
		return cDSSpreads;
	}
	
	
	public ArrayList<double[]> getCashSPreads(){
		return cashSpreads;
	}

	public double[] getLiborRates(){
		return liborRates;
	}
	
	public double[] getDefaultLIBORBorrowingRates(){
		return defaultLIBORBorrowingRates;
	}

	

	
	private void createMoneyMarket(ModelParameters params) {
		// TODO Auto-generated method stub
		this.moneyMarket = new MoneyMarket(params);
	}
	
	public MoneyMarket getMoneyMarket(){
		return this.moneyMarket;
	}



	@Override
	public String toString(){
		return new String(this.getClass() + " cashSpreads: " + Economy.cashSpreads.size() 
				+ " liborRates: " + liborRates.length + " cDSSpreads: " + Economy.cDSSpreads);
		
	}

	
}
