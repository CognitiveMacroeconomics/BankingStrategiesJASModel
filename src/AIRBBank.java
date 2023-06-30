

public class AIRBBank extends BaselIIBank {

	public AIRBBank(int RSSID, String bankName, double cashBalances, double assets,
			double liabilities, double equity, double rmbsAssets,
			double cdsProtectionSold, double cdsProtectionBought,
			double teir1Capital, 
			double rmbsSecuritisation, double rmbsHESecuritisation,
			double rmbsCreditEnhancementAndLiquidityProvissions,
			double rmbsHECreditEnhancementAndLiquidityProvissions, double vThreshold, double counterpartyRiskWeight, boolean baselII) {
		super(RSSID, bankName, cashBalances, assets, liabilities, equity, rmbsAssets,
				cdsProtectionSold, cdsProtectionBought, teir1Capital, 
				rmbsSecuritisation, rmbsHESecuritisation,
				rmbsCreditEnhancementAndLiquidityProvissions,
				rmbsHECreditEnhancementAndLiquidityProvissions, vThreshold, counterpartyRiskWeight, baselII);
		// TODO Auto-generated constructor stub
	}

	
	
	
	@Override
	public void checkLife() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void reproduce() {
		// TODO Auto-generated method stub
		
	}


	
}
