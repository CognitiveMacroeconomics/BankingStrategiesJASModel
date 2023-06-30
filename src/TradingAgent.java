
public class TradingAgent {
	
	public static int ID = 0;
	private int id;
	private String tradingAgentGroup;
	private String marketRole;
	private double expectedBondDefaultRate;// in equations phi
	private double bondPositionHoldingPeriod;// in equations pi
	private double bondPriceEventImpact = 0;
	private double bondDefaultEventImpact = 0;
	private double minimumBidCapacity;
	private double maximumBidCapacity;
	private boolean bondTrader = false;
	private boolean CDSTrader = false;
	private Economy economy;
	private MarketEntryBid cdsMarketBid;
	private MarketEntryBid bondMarketBid;
	private BondCDSTradingDecisionAlgorithm bondTradingDecisionAlgorithm;
	private BondCDSTradingDecisionAlgorithm cdsTradingDecisionAlgorithm;
	
	
	/**
	 * THis method sets the market role of the agent based on the enumerations defined in the TradingAgentMarketRoles() class
	 * 		public static final String MARKETROLE_1 = "Buyer";
	 * 		public static final String MARKETROLE_2 = "Seller";
	 * 
	 * depending on if the agents trading group is one of
	 * 		public static final String TRADINGAGENTGROUP_1 = "Bond Buyer";
	 *  	public static final String TRADINGAGENTGROUP_2 = "Bond Seller";
	 *  	public static final String TRADINGAGENTGROUP_3 = "CDS Buyer";
	 *  	public static final String TRADINGAGENTGROUP_4 = "CDS Seller";
	 *  	public static final String TRADINGAGENTGROUP_5 = "Bond and CDS Buyer";
	 *  	public static final String TRADINGAGENTGROUP_6 = "Bond and CDS Seller";
	 *  	public static final String TRADINGAGENTGROUP_7 = "Zero Intelligence";
	 * @param tradingAgentGroup2
	 */
	private void setTraderMarketRole(String tradingAgentGroup2){
		
		if(tradingAgentGroup2 == "Bond Buyer" || tradingAgentGroup2 == "CDS Buyer" || tradingAgentGroup2 == "Bond and CDS Buyer"){
			this.marketRole = TradingAgentMarketRoles.MARKETROLE_1;
		}
		else
			if(tradingAgentGroup2 == "Bond Seller" || tradingAgentGroup2 == "CDS Seller" || tradingAgentGroup2 == "Bond and CDS Seller"){
				this.marketRole = TradingAgentMarketRoles.MARKETROLE_2;
			}
	}
	
	
	private void setMarketTradingType(String tradingAgentGroup2){

		if(tradingAgentGroup2 == TradingAgentMarketRoles.TRADINGAGENTGROUP_1 ||
				tradingAgentGroup2 == TradingAgentMarketRoles.TRADINGAGENTGROUP_2){
			this.bondTrader = true;
			this.CDSTrader = false;
		}
		else
			if(tradingAgentGroup2 == TradingAgentMarketRoles.TRADINGAGENTGROUP_3 ||
			tradingAgentGroup2 == TradingAgentMarketRoles.TRADINGAGENTGROUP_4){
				this.bondTrader = false;
				this.CDSTrader = true;
			}
			else
				if(tradingAgentGroup2 == TradingAgentMarketRoles.TRADINGAGENTGROUP_5 ||
				tradingAgentGroup2 == TradingAgentMarketRoles.TRADINGAGENTGROUP_6){
					this.bondTrader = true;
					this.CDSTrader = true;
				}

	}
	
	
	private void updateBondPriceEventImpact(){
		this.bondPriceEventImpact = Economy.eventImpactBondPrice;
	}
	
	private void updateBondDefaultEventImpact(){
		this.bondDefaultEventImpact = Economy.eventImpactDefaultProbability;
	}

	
	private void updateBondDefaultExpectationsEventImpact(){
		this.expectedBondDefaultRate *= (1 + Economy.eventImpactDefaultProbability);
	}
	
	
	private void updateBondHoldingPeriod(){
		this.bondPositionHoldingPeriod *= (1 - Economy.bondSupplyDelta);
	}
	
	
	public void updateCDSDecisionParameters(){
		/**
		 * public void updateDecisionParametersAndForecat(String eventType, double holdingPeriod, 
		 * double expectedDefault, double bondMaturity, 
			double bidP, double askP, double whiteNoiseParam, double priceImpact, double meanPrice, 
			double meanReversion, double fundingCost
		 */
		this.cdsTradingDecisionAlgorithm.updateDecisionParametersAndForecat(Economy.eventType, this.bondPositionHoldingPeriod, 
				this.expectedBondDefaultRate, Economy.poissonBondMaturityArrivalRate,
				Economy.currentCDSMarketBidPrice, Economy.currentCDSMarketAskPrice, 
				Economy.tradingABMCDSNoiceParameter, -1*this.bondPriceEventImpact, Economy.tradingABMCDSDriftParameter, 
				Economy.tradingABMCDSMeanReversionParameter, Economy.cdsFundingCost);
	}
	
	public void updateBondDecisionParameters(){
		this.bondTradingDecisionAlgorithm.updateDecisionParametersAndForecat(Economy.eventType, this.bondPositionHoldingPeriod, 
				this.expectedBondDefaultRate, Economy.poissonBondMaturityArrivalRate,
				Economy.currentBondMarketBidPrice, Economy.currentBondMarketAskPrice, 
				Economy.tradingABMBondNoiceParameter, this.bondPriceEventImpact, Economy.tradingABMBondDriftParameter, 
				Economy.tradingABMBondMeanReversionParameter, Economy.bondFundingCost);
	}
	


}
