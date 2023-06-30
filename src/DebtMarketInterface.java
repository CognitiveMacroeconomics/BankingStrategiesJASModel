import java.util.ArrayList;


public class DebtMarketInterface {
	
	double riskFreeRate;
	double[] riskFreeRatePath;
	double discountFactor;
	double annuityFactor;
	double changeInProbabilities;
	ArrayList<ZhouAnnuityPricingCDSBasis> cdsBasisList = new ArrayList<ZhouAnnuityPricingCDSBasis>();
	ModelParameters parameters;
	
	
	public DebtMarketInterface(ModelParameters params){
		
		setParameters(params);
		setRiskFreeRate(MoneyMarket.currentRiskFreeRate);
		createCDSBasisList();
	}



	public void updateCDSBasis(){
		//this method is called at each calculation/decision making epoch or time period
		//it updates the current market cds basis for each of the tranches by calling each tranche's computeCBSBasis method
		setRiskFreeRate(MoneyMarket.currentRiskFreeRate);
		setRiskFreeRatePath(MoneyMarket.riskFreeRatePath);
		for(int i = 0; i < cdsBasisList.size(); i++){
			cdsBasisList.get(i).computeCDSBasis(changeInProbabilities, discountFactor, annuityFactor, riskFreeRatePath);
		}
	}
	
	private void createCDSBasisList() {
		// TODO Auto-generated method stub
		if(cdsBasisList.isEmpty()){
			createNewBasisList();
		}else {
			cdsBasisList.clear();
			createNewBasisList();
		}
	}


	private void createNewBasisList() {
		// TODO Auto-generated method stub
		//ZhouAnnuityPricingCDSBasis(String seniority, double coupon, double initPXgT, double initPXgTm1)
		cdsBasisList.add(new ZhouAnnuityPricingCDSBasis("AAA-Tranche", parameters.getAAATrancheCoupon(), 
				parameters.getAAATrancheProbSurvivalAfterT(), parameters.getAAATrancheProbSurvivalAfterTm1()));
		cdsBasisList.add(new ZhouAnnuityPricingCDSBasis("AA-Tranche", parameters.getAATrancheCoupon(), 
				parameters.getAATrancheProbSurvivalAfterT(), parameters.getAATrancheProbSurvivalAfterTm1()));
		cdsBasisList.add(new ZhouAnnuityPricingCDSBasis("A-Tranche", parameters.getATrancheCoupon(), 
				parameters.getATrancheProbSurvivalAfterT(), parameters.getATrancheProbSurvivalAfterTm1()));
		cdsBasisList.add(new ZhouAnnuityPricingCDSBasis("BBB-Tranche", parameters.getBBBTrancheCoupon(), 
				parameters.getBBBTrancheProbSurvivalAfterT(), parameters.getBBBTrancheProbSurvivalAfterTm1()));
		cdsBasisList.add(new ZhouAnnuityPricingCDSBasis("BBB--Tranche", parameters.getBBB3TrancheCoupon(), 
				parameters.getBBB3TrancheProbSurvivalAfterT(), parameters.getBBB3TrancheProbSurvivalAfterTm1()));
	}


	private void setParameters(ModelParameters params) {
		// TODO Auto-generated method stub
		this.parameters = params;
		this.discountFactor = this.parameters.getDiscountFactor();
		this.annuityFactor = this.parameters.getAnnuityFactor();
	}
	
	
	private void setRiskFreeRate(double currentRiskFreeRate) {
		// sets the current risk free rate to be used in computing the basis on the trahcnes
		riskFreeRate = currentRiskFreeRate;
	}


	
	private void setRiskFreeRatePath(double[] currentRiskFreeRates) {
		// sets the current risk free rate path to be used in computing the basis on the trahcnes
		riskFreeRatePath = currentRiskFreeRates;
	}



	
	

}
