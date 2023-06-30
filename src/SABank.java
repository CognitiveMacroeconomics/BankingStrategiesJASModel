




public class SABank extends BaselIIBank {
	
	/**
	 * SABank is a subclass of BaselIIBank and refer to the structures of all those banks which under the new regulatory
	 * rules will use the Standard Approach to Credit Risk. The new rules are slightly more complex than this when operational
	 * and market risk are introduced into the equation. However, the sole interest at the time of writing this code was Credit
	 * risk.
	 * The variables common to all subclasses of BaselIIBank are as follows:
	 *  
	 * @param RSSID
	 * @param cashBalances
	 * @param assets
	 * @param liabilities
	 * @param equity
	 * @param rmbsAssets
	 * @param cdsProtectionSold
	 * @param cdsProtectionBought
	 * @param teir1Capital
	 * @param teir2Capital
	 * @param rmbsSecuritisation
	 * @param rmbsHESecuritisation
	 * @param rmbsCreditEnhancementAndLiquidityProvissions
	 * @param econ 
	 * @param mEC 
	 * @param rmbsHECreditEnhancementAndLiquidityProvissions
	 */
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class Constructor>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//	public SABank(int RSSID,  String bankName, double cashBalances, double assets,
//			double liabilities, double equity, double rmbsAssets,
//			double cdsProtectionSold, double cdsProtectionBought,
//			double teir1Capital, double teir2Capital,
//			double rmbsSecuritisation, 
//			double rmbsCreditEnhancementAndLiquidityProvissions,
//			double SIVCreditEnhancementAndLiquidityProvissions, double vThreshold, double counterpartyRiskWeight, boolean baselII,
//			boolean modelTypeRMBS) {
//		
//		//Constructor call to superclass to initialize all class tree common variables
//		super(RSSID, bankName, cashBalances, assets, liabilities, equity, rmbsAssets,
//				cdsProtectionSold, cdsProtectionBought, teir1Capital, teir2Capital,
//				rmbsSecuritisation, 
//				rmbsCreditEnhancementAndLiquidityProvissions,
//				SIVCreditEnhancementAndLiquidityProvissions, vThreshold, counterpartyRiskWeight, baselII, modelTypeRMBS);
//		
//		
//		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class/instance Specific variables>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 		
//		this.securitisationExposureRiskWeights = BaselIICapital.baselIISAproachSecuritizedExposureRiskWieghts;
//		
//		this.ratingABX = BaselIICapital.ratingABX_HE;
//	}
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class Constructor For multiple Counter-Parties>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public SABank(int RSSID, String bankName, double cashBalances, double assets,
			double liabilities, double equity, double rmbsAssets,
			double cdsProtectionSold, double cdsProtectionBought,
			double teir1Capital, 
			double rmbsSecuritisation, 
			double rmbsCreditEnhancementAndLiquidityProvissions,
			double SIVCreditEnhancementAndLiquidityProvissions, double vThreshold, double[] counterpartyRiskWeight, boolean baselII, 
			boolean modelTypeRMBS, marketEnviromentConfigurator mEC, Economy econ) {
		
		//Constructor call to superclass to initialize all class tree common variables
		super(RSSID, bankName, cashBalances, assets, liabilities, equity, rmbsAssets,
				cdsProtectionSold, cdsProtectionBought, teir1Capital, 
				rmbsSecuritisation, 
				rmbsCreditEnhancementAndLiquidityProvissions,
				SIVCreditEnhancementAndLiquidityProvissions, vThreshold, counterpartyRiskWeight, baselII, modelTypeRMBS,  mEC, econ);
		
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class/instance Specific variables>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 		
		this.securitisationExposureRiskWeights = BaselIICapital.baselIISAproachSecuritizedExposureRiskWieghts;
		
		this.ratingABX = BaselIICapital.ratingABX_HE;
	}
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<Constructor which Includes Home Equity>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public SABank(int RSSID, String bankName, double cashBalances, double assets,
			double liabilities, double equity, double rmbsAssets,
			double cdsProtectionSold, double cdsProtectionBought,
			double teir1Capital, 
			double rmbsSecuritisation, 
			double rmbsCreditEnhancementAndLiquidityProvissions,
			double rmbsHECreditEnhancementAndLiquidityProvissions, double vThreshold, double counterpartyRiskWeight, boolean baselII, 
			boolean modelTypeRMBS, marketEnviromentConfigurator mEC, Economy econ) {
		
		//Constructor call to superclass to initialize all class tree common variables
		super(RSSID, bankName, cashBalances, assets, liabilities, equity, rmbsAssets,
				cdsProtectionSold, cdsProtectionBought, teir1Capital, 
				rmbsSecuritisation, 
				rmbsCreditEnhancementAndLiquidityProvissions,
				rmbsHECreditEnhancementAndLiquidityProvissions, vThreshold, counterpartyRiskWeight, baselII, modelTypeRMBS, mEC, econ);
		
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class/instance Specific variables>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 		
		this.securitisationExposureRiskWeights = BaselIICapital.baselIISAproachSecuritizedExposureRiskWieghts;
		
		this.ratingABX = BaselIICapital.ratingABX_HE;
	}
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class Constructor>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//	public SABank(int RSSID,  String bankName, double cashBalances, double assets,
//			double liabilities, double equity, double rmbsAssets,
//			double cdsProtectionSold, double cdsProtectionBought,
//			double teir1Capital, 
//			double rmbsSecuritisation, 
//			double rmbsCreditEnhancementAndLiquidityProvissions,
//			double SIVCreditEnhancementAndLiquidityProvissions, double vThreshold, double counterpartyRiskWeight, boolean baselII, boolean modelTypeRMBS, marketEnviromentConfigurator mEC, Economy econ) {
//		
//		//Constructor call to superclass to initialize all class tree common variables
//		super(RSSID, bankName, cashBalances, assets, liabilities, equity, rmbsAssets,
//				cdsProtectionSold, cdsProtectionBought, teir1Capital, 
//				rmbsSecuritisation, 
//				rmbsCreditEnhancementAndLiquidityProvissions,
//				SIVCreditEnhancementAndLiquidityProvissions, vThreshold, counterpartyRiskWeight, baselII,  modelTypeRMBS, mEC, econ);
//		
//		
//		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class/instance Specific variables>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 		
//		this.securitisationExposureRiskWeights = BaselIICapital.baselIISAproachSecuritizedExposureRiskWieghts;
//		
//		this.ratingABX = BaselIICapital.ratingABX_HE;
//	}
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class Constructor For multiple Counter-Parties>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public SABank(int RSSID, String bankName, double cashBalances, double assets,
			double liabilities, double equity, double rmbsAssets,
			double cdsProtectionSold, double cdsProtectionBought,
			double teir1Capital, 
			double rmbsSecuritisation, 
			double rmbsCreditEnhancementAndLiquidityProvissions,
			double SIVCreditEnhancementAndLiquidityProvissions, double vThreshold, double[] counterpartyRiskWeight, boolean baselII) {
		
		//Constructor call to superclass to initialize all class tree common variables
		super(RSSID, bankName, cashBalances, assets, liabilities, equity, rmbsAssets,
				cdsProtectionSold, cdsProtectionBought, teir1Capital,
				rmbsSecuritisation, 
				rmbsCreditEnhancementAndLiquidityProvissions,
				SIVCreditEnhancementAndLiquidityProvissions, vThreshold, counterpartyRiskWeight, baselII);
		
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class/instance Specific variables>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 		
		this.securitisationExposureRiskWeights = BaselIICapital.baselIISAproachSecuritizedExposureRiskWieghts;
		
		this.ratingABX = BaselIICapital.ratingABX_HE;
	}
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<Constructor which Includes Home Equity>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public SABank(int RSSID, String bankName, double cashBalances, double assets,
			double liabilities, double equity, double rmbsAssets,
			double cdsProtectionSold, double cdsProtectionBought,
			double teir1Capital,
			double rmbsSecuritisation, double rmbsHESecuritisation,
			double rmbsCreditEnhancementAndLiquidityProvissions,
			double rmbsHECreditEnhancementAndLiquidityProvissions, double vThreshold, double counterpartyRiskWeight, boolean baselII) {
		
		//Constructor call to superclass to initialize all class tree common variables
		super(RSSID, bankName, cashBalances, assets, liabilities, equity, rmbsAssets,
				cdsProtectionSold, cdsProtectionBought, teir1Capital, 
				rmbsSecuritisation, rmbsHESecuritisation,
				rmbsCreditEnhancementAndLiquidityProvissions,
				rmbsHECreditEnhancementAndLiquidityProvissions, vThreshold, counterpartyRiskWeight, baselII);
		
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class/instance Specific variables>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 		
		this.securitisationExposureRiskWeights = BaselIICapital.baselIISAproachSecuritizedExposureRiskWieghts;
		
		this.ratingABX = BaselIICapital.ratingABX_HE;
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
