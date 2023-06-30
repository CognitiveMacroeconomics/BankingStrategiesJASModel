

public class SPV {
	
	int id;
	int totalNumOfPayments;
	int paymentCount;
	double faceValue;
	double balance;
	double coupon;
	double survivalProbability;
	double degreeOfsubordination;
	double seniorTrancheReturn;
	double equityTrancheReturn;
	double survivedFaceValue;
	double seniorTranchePayment;
	double equityTranchePayment;
	private double monthlyPayments;


	public SPV(int id2, double faceValue2, double returnOnAssets, double spvSurvivalProb, double absSubordination, int maturity){
		this.id = id2;
		this.faceValue = faceValue2;
		this.coupon = returnOnAssets;
		this.survivalProbability = spvSurvivalProb;
		this.degreeOfsubordination = absSubordination;
		this.seniorTrancheReturn = this.coupon*(1-this.degreeOfsubordination);
		this.equityTrancheReturn = this.coupon*this.degreeOfsubordination;
		this.totalNumOfPayments = 12*maturity;
		this.setMonthlyPayments();
		this.paymentCount = 0;
	}
	

	public void makePayment(){
		this.seniorTranchePayment = this.survivalProbability*(1-this.degreeOfsubordination)*this.monthlyPayments;
		this.equityTranchePayment = this.survivalProbability*this.degreeOfsubordination*this.monthlyPayments;
		this.paymentCount++;
		this.setBalance(this.paymentCount);
	}
	
	
	//sets the outstanding balance
	private void setBalance(int paymentCount){
		/*
		 * the outstanding balance is defined by
		 * 
		 * OB(m − 1) =  OB(0)*{[(1 + c/12)^n − (1 + c/12)^m−1]/(1 + c/12)^n − 1}
		 * 
		 */
		this.balance = this.faceValue*((Math.pow((1+this.coupon),this.totalNumOfPayments)
				-Math.pow((1+this.coupon),paymentCount))
				/Math.pow((1+this.coupon),this.totalNumOfPayments-1));
	}// end of setBalance
	
	
	//computes the value of the monthly repayments
	private void setMonthlyPayments(){
		/*
		 * 
		 * for a normal maturing loan
		 *  	MP = OB(0)*{[(c/12)*(1 + c/12)^n]/[(1 + c/12)^n − 1]}
		 *   
		 *   
		 *   OB(m − 1) =  OB(0)*{[(1 + c/12)^n − (1 + c/12)^m−1]/(1 + c/12)^n − 1}
		 * 
		 * 
		 * for a perpetuity
		 * 		perMP = facevalue*r/12months in a year
		 */

		this.monthlyPayments = this.faceValue*(((this.coupon)
				*Math.pow((1+(this.coupon)),this.totalNumOfPayments))
				/((Math.pow((1+(this.coupon)),this.totalNumOfPayments))-1));
	}// end of setMonthlyPayments
	


}
