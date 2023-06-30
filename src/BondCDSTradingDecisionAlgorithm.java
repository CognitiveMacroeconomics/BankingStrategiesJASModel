import java.util.ArrayList;
import java.util.Random;


public class BondCDSTradingDecisionAlgorithm {
	/**
	 * This is the class used to determine the trading agents market bids and offers. When constructed a message String identifying the 
	 * market type the decision making is for must be specified.
	 * 
	 * This helps determine whether the price forecasting will use the bond forecast or the CDS forecast
	 * This is particularly important where the trader is both trading bonds and CDS. In these instances the agent will have two active
	 * BondCDSTradingDecisionAlgorithm objects one for engaging in the Bond Market, the other for activities in the CDS market.
	 * 
	 * The class must also be provided a role of the agent using the BondCDSTradingDecisionAlgorithm object. This role must be reduced to
	 * a simple "buyer" or "seller" type from the possible trading agent type enumerations
	 * 
	 * 		public static final String TRADINGAGENTGROUP_1 = "Bond Buyer";
	 * 		public static final String TRADINGAGENTGROUP_2 = "Bond Seller";
	 * 		public static final String TRADINGAGENTGROUP_3 = "CDS Buyer";
	 * 		public static final String TRADINGAGENTGROUP_4 = "CDS Seller";
	 * 		public static final String TRADINGAGENTGROUP_5 = "Bond and CDS Buyer";
	 * 		public static final String TRADINGAGENTGROUP_6 = "Bond and CDS Seller";
	 * 		public static final String TRADINGAGENTGROUP_7 = "Zero Intelligence";
	 * 
	 * as specified in the TradingAgentMarketRoles() class. Roles are also enumerated in this class as follows
	 * 		public static final String MARKETROLE_1 = "Buyer";
	 * 		public static final String MARKETROLE_2 = "Seller";	
	 */
	
	
	private String message;//market to which bid will be submitted
	private String role;
	private String economicGrowth;
	private double priceForecast;
	private double bidPrice;
	private double askPrice;
	private double submittedBidPrice;
	private int quantityBid;
	private double whiteNoice;
	private double eventImpact;
	private ArrayList<Double> estimationErrors = new ArrayList();
	private Random stdRandomGenerator = new Random();
	private int minQuatity;
	private int maxQuantity;
	private double pricePeception;
	private double meanReversionRate;
	private double equlibriumPrice;
	private double positionHoldingPeriod;
	private double maturityArrivalRate;
	private double probabilityOfPositionUnwind;
	private double probabilityOfHoldingPositionToMaturity;
	private double bondDefaultExpectation;
	private double bondFinancingCost;
	private double cdsFinancingCost;
	private double impactFraction;
	private MarketEntryBid bidSubmitted;
	private ArrayList<MarketEntryBid> bidHistory = new ArrayList<MarketEntryBid>();
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CONSTRUCTORS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public BondCDSTradingDecisionAlgorithm(){

		this.message = TradingMarketTypes.MARKETTYPE_1;
		this.role = TradingAgentMarketRoles.MARKETROLE_1;
		this.minQuatity = 0;
		this.maxQuantity = 1;
		this.estimationErrors.add(0.0);
	}
	
	
	public BondCDSTradingDecisionAlgorithm(String mes, String rle, int Qmin, int Qmax){
		
		this.message = mes;
		this.role = rle;
		this.minQuatity = Qmin;
		this.maxQuantity = Qmax;
		this.estimationErrors.add(0.0);
		
	}
	
	
	
	/**
	 * This method is used to update all parameters required to compute the bid price and quantity a trading agent will submit
	 * The method will also compute the price forecasts the trading agent makes in order to compute the bid price and quantity to submit
	 * This method must be called first each time the trading agent is to submit a bid to a market. Once this method is called, the agent
	 * can then call the makeBid() method to actually submit the bid
	 * @param eventType
	 * @param holdingPeriod
	 * @param expectedDefault
	 * @param bondMaturity
	 * @param bidP
	 * @param askP
	 * @param whiteNoiseParam
	 * @param priceImpact
	 * @param meanPrice
	 * @param meanReversion
	 * @param fundingCost
	 */
	public void updateDecisionParametersAndForecat(String eventType, double holdingPeriod, double expectedDefault, double bondMaturity, 
			double bidP, double askP, double whiteNoiseParam, double priceImpact, double meanPrice, 
			double meanReversion, double fundingCost){
		this.economicGrowth = eventType;
		this.bidPrice = bidP;
		this.askPrice = askP;
		this.setWhiteNoise(whiteNoiseParam);
		this.impactFraction = priceImpact;
		this.pricePeception = meanPrice;
		this.meanReversionRate = meanReversion;
		this.positionHoldingPeriod = holdingPeriod;
		this.maturityArrivalRate = bondMaturity;
		this.bondDefaultExpectation = expectedDefault;
		this.computeProbabilityOfHoldingPositionTillMaturity();
		this.computeProbabilityOfUnwindingPosition();
		this.computeEquilibriumPrice();
		this.setQuantity();
		
		
		if(this.message == TradingMarketTypes.MARKETTYPE_1){
			this.bondFinancingCost = fundingCost;
			this.computeBondPriceForcast();
		} else
		if(this.message == TradingMarketTypes.MARKETTYPE_2){
			this.cdsFinancingCost = fundingCost;
			this.computeCDSPriceForcast();
		}

		
	}
	
	
	/**
	 * This method is called for the trading agent to submit a bid to the market. 
	 * This method must be called from the trader agent class after the updateDecisionParametersAndForecat method is called
	 */
	public MarketEntryBid makeABid(){
//		if(this.bidSubmitted != null) {
//			bidHistory.add(this.bidSubmitted);
//		}
		this.bidSubmitted = this.submitBid();
		bidHistory.add(this.bidSubmitted);
		return this.bidSubmitted;
	}
	
	
	private MarketEntryBid submitBid(){
		double averagePrice = 0;
		double std = 1;
		MarketEntryBid bid = null;
		if(this.role == TradingAgentMarketRoles.MARKETROLE_1){
			averagePrice = (this.priceForecast + this.bidPrice)/2;
			this.submittedBidPrice = DistributionBasedRandomNumberGenerator.getNormal(averagePrice, std);
			bid = new MarketEntryBid(message, averagePrice, this.quantityBid);
		}else 
			if(this.role == TradingAgentMarketRoles.MARKETROLE_2){
				averagePrice = (this.priceForecast + this.askPrice)/2;
				this.submittedBidPrice = DistributionBasedRandomNumberGenerator.getNormal(averagePrice, std);
				bid = new MarketEntryBid(message, averagePrice, -1*this.quantityBid);
			}
		
		return bid;
	}

	/**
	 * method to compute the price forecast for a bond trade position
	 */
	private void computeBondPriceForcast(){
		if(this.role == TradingAgentMarketRoles.MARKETROLE_1){
			this.priceForecast = (this.equlibriumPrice - this.bondFinancingCost) 
					- this.probabilityOfPositionUnwind*this.equlibriumPrice 
					- this.probabilityOfHoldingPositionToMaturity*(1-this.bondDefaultExpectation) 
					+ this.getSimpleEventImpact(this.economicGrowth, this.impactFraction) + this.whiteNoice;
		} else 
			if(this.role == TradingAgentMarketRoles.MARKETROLE_2){
				this.priceForecast =  this.probabilityOfHoldingPositionToMaturity*(1-this.bondDefaultExpectation) 
						- this.equlibriumPrice + this.probabilityOfPositionUnwind*(this.equlibriumPrice - this.bondFinancingCost) +
						this.getSimpleEventImpact(this.economicGrowth, this.impactFraction) + this.whiteNoice;
			} 
	}
	
	

	/**
	 * method to compute the price forecast for a CDS trade position
	 */
	private void computeCDSPriceForcast(){
		//if CDS protection buyer
		if(this.role == TradingAgentMarketRoles.MARKETROLE_1){
			this.priceForecast = this.probabilityOfPositionUnwind*(this.equlibriumPrice - this.cdsFinancingCost) 
					- this.equlibriumPrice 
					+ this.probabilityOfHoldingPositionToMaturity*(this.bondDefaultExpectation) 
					+ this.getSimpleEventImpact(this.economicGrowth, this.impactFraction) + this.whiteNoice;
		} else 
			//if CDS protection seller
			if(this.role == TradingAgentMarketRoles.MARKETROLE_2){
				this.priceForecast =  (this.equlibriumPrice - this.cdsFinancingCost) 
						- this.probabilityOfPositionUnwind*this.equlibriumPrice 
						- this.probabilityOfHoldingPositionToMaturity*(this.bondDefaultExpectation)
						+ this.getSimpleEventImpact(this.economicGrowth, this.impactFraction) + this.whiteNoice;
			}  
	}
	
	
	/**
	 * this method computed the agents equilibrium price, based on a Ornstein-Uhlenbeck (OU) process 
	 * Pe(t) = u + lambda*[((Pa+Pb)/2) - u]
	 */
	private void computeEquilibriumPrice(){
		this.equlibriumPrice = this.pricePeception + this.meanReversionRate*(((this.askPrice+this.bidPrice)/2)-this.pricePeception);
	}
	
	
	
	/**
	 * generates the white noise term for the price forecasting
	 * @param sd
	 * @return whiteNoice
	 */
	private void setWhiteNoise(double sd){
		
		this.whiteNoice = sd*stdRandomGenerator.nextGaussian();
		this.estimationErrors.add(this.whiteNoice);
	}
	
	private double getWhiteNoise(){
		return whiteNoice;
		
	}
	
	
	/**
	 * This method return a random quantity within the range of minQuantity and the maxQuantity that the trader will offer to sell 
	 * or bid to buy at the price to be determined
	 * q = U[Qmin, Qmax]
	 * @return quantityBid
	 */
	private int setQuantity(){
		
		
		this.quantityBid = stdRandomGenerator.nextInt(1+(this.maxQuantity - this.minQuatity)) + this.minQuatity;
		
		return this.quantityBid;
		
	}
	
	
	/**
	 * This method returns the price the trader offers or bids for the asset on the market 
	 * 
	 * p = Normal[(Pf - Pa)/2, 1] if trader is a seller
	 * p = Normal[(Pf - Pb)/2, 1] if trader is a buyer 
	 * 
	 */
	private void generateSubmittedBidPrice(){
		
		double mean = 0;
		
		if(role == "Seller"){
			mean = (this.priceForecast + this.askPrice)/2;
		}
		
		if(role == "Buyer"){
			mean = (this.priceForecast + this.bidPrice)/2;
		}

		double std = 1;
		
		this.submittedBidPrice = DistributionBasedRandomNumberGenerator.getNormal(mean, std);
		
	}
	
	
	/**
	 * generated the impact of an event on the agents forecast of prices
	 * 
	 *  The EconomicConditions() class enumerates all possible economic conditions
	 *  
	 *  public static final String CONDITION_1 = "Economic Growth";
	 *  	public static final String CONDITION_2 = "Normal Path";
	 *  	public static final String CONDITION_3 = "Economic Decline";
	 *  	public static final String CONDITIONE_4 = "";
	 *  	public static final String CONDITION_5 = "";
	 *  	public static final String CONDITION_6 = "";
	 *  	public static final String CONDITION_7 = "";
	 * @return
	 */
	private double getSimpleEventImpact(String economicGrowth, double impactFraction){
		
		if(economicGrowth == EconomicConditions.CONDITION_1){
			eventImpact = ((this.askPrice+this.bidPrice)/2)*(1+impactFraction);
		}
		else if(economicGrowth == EconomicConditions.CONDITION_2) {
			eventImpact = ((this.askPrice+this.bidPrice)/2)*(1-0);
		}
		else if(economicGrowth == EconomicConditions.CONDITION_3) {
			eventImpact = ((this.askPrice+this.bidPrice)/2)*(1-impactFraction);
		}
			
		return eventImpact;
	}
	
	/**
	 * This method computes the probability that the trading agent will hold a particular trading position till maturity
	 */
	private void computeProbabilityOfHoldingPositionTillMaturity(){
		this.probabilityOfHoldingPositionToMaturity = this.positionHoldingPeriod/(this.positionHoldingPeriod + this.maturityArrivalRate);
	}
	
	/**
	 * This method computes the probability that the trading agent will unwind a particular trading position before maturity
	 */
	private void computeProbabilityOfUnwindingPosition(){
		this.probabilityOfPositionUnwind = this.maturityArrivalRate/(this.positionHoldingPeriod + this.maturityArrivalRate);
			
	}
	

}
