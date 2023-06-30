/**
 * 
 * @author Oluwasegun Bewaji
 *
 * This is a utility class used to collect pairs or tuples of integer points/co-ordinates used for
 * defining the bids the trading agents make
 * 
 *
 *   
 */
public class MarketEntryBid {
	
	public static int MarketEntryBid_ID = 0;
	int marketEntryBidID;
	String marketType;
	int bidQuantity;
	double bidPrice;
	
	
	public MarketEntryBid(String mktType, double p, int q){
		MarketEntryBid_ID++;
		marketEntryBidID = MarketEntryBid_ID;
		this.marketType = mktType;
		bidQuantity = q;
		bidPrice = p;
	}

	
 public double getSubmittedPrice(){
	 return this.bidPrice;
 }

 
 public int getSubmittedQuantity(){
	 return this.bidQuantity;
 }

 public String getMarketType(){
	 return this.marketType;
 }

 
}
