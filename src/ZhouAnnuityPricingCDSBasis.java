import java.util.ArrayList;


public class ZhouAnnuityPricingCDSBasis {
	
	String seniourityString;
	double coupon;
	double basis;
	double riskFreeRate;
	double survivalProbabilityAfterT;
	double survivalProbabilityAfterTm1;
	double survivalProbabilityChange;
	double cdsSpread;
	double bondSpread;
	double LIBOR;
	double LIBORRisky;
	double PV01;
	double PV01Bar;
	
	ArrayList<Double> survivalProbabilityAfterTList = new ArrayList<Double>();
	ArrayList<Double> survivalProbabilityAfterTm1List = new ArrayList<Double>();
	ArrayList<Double> cdsBasisList = new ArrayList<Double>();
	ArrayList<Double> cdsSpreadList = new ArrayList<Double>();
	ArrayList<Double> bondSpreadList = new ArrayList<Double>();
	
	
	public ZhouAnnuityPricingCDSBasis(String seniority, double coupon, double initPXgT, double initPXgTm1){
		setSeniourityString(seniority);
		setCoupon(coupon);
		setSurvivalProbabilityAfterT(initPXgT);
		setSurvivalProbabilityAfterTm1(initPXgTm1);
		//setSurvivalProbabilityChange(changeinPX);
	}
	
	
	
	public void computeCDSBasis(double changeinPX, double discountFactor, double annuityFactor, double[] riskFreeRates){
		double cBasis = 0;
		double cSpread = 0;
		double bSpread = 0;
		setSurvivalProbabilityAfterT(survivalProbabilityAfterT + changeinPX);
		setSurvivalProbabilityAfterTm1(survivalProbabilityAfterTm1 + changeinPX);
		
		
		
		cBasis = (LIBOR - LIBORRisky) - coupon*(1 - (PV01/PV01Bar)) + (discountFactor/PV01Bar)*(1 - (PV01Bar/annuityFactor));
		
		setBasis(cBasis);
		setCDSSpread(cSpread);
		setBondpread(bSpread);
		
	}


	private void setBasis(double cdsBasis) {
		// TODO Auto-generated method stub
		basis = cdsBasis;
		cdsBasisList.add(basis);
	}
	
	
	private void setCDSSpread(double cSpread) {
		// TODO Auto-generated method stub
		cdsSpread = cSpread;
		cdsSpreadList.add(cdsSpread);
	}

	
	
	private void setBondpread(double bSpread) {
		// TODO Auto-generated method stub
		bondSpread = bSpread;
		bondSpreadList.add(bondSpread);
	}



	private void setSurvivalProbabilityAfterT(double initPXgT) {
		// TODO Auto-generated method stub
		survivalProbabilityAfterT = initPXgT;
		survivalProbabilityAfterTList.add(survivalProbabilityAfterT);
	}


	private void setSurvivalProbabilityAfterTm1(double initPXgTm1) {
		// TODO Auto-generated method stub
		survivalProbabilityAfterTm1 = initPXgTm1;
		survivalProbabilityAfterTm1List.add(survivalProbabilityAfterTm1);
	}


	private void setCoupon(double coupon2) {
		// TODO Auto-generated method stub
		coupon = coupon2;
	}


	private void setSeniourityString(String seniority) {
		// TODO Auto-generated method stub
		seniourityString = seniority;
	}
	

}
