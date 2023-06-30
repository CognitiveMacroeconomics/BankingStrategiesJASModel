import jas.engine.Sim;
import jas.events.ISimEventListener;
import jas.plot.IColored;

import java.awt.Color;
import java.util.ArrayList;



public abstract class BaselIIBank implements IColored, ISimEventListener{


	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<JAS RELATED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public static final int ASSETS = 0;
	public static final int LIABILITIES = 1;
	public static final int EQUITY = 2;
	public static final int RETURN_ON_EQUITY = 3;
	public static final int LIQUID_ASSETS = 4;
	public static final int SECURITISATION_RATE = 5;
	public static final int LEVERAGE_RATIO = 6;
	public static final int RISK_BASED_CAPITAL = 7;
	public static final int ABS_OUTSTANDING = 8;
	public static final int ABS_RETAINED = 9;

	private static final long serialVersionUID = 1L;

	public static final int REPRODUCE = 0;
	public static final int CHECK_LIFE = 1;

	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Economy and Environment>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	marketEnviromentConfigurator enviroment;
    Economy economy;

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<BASEL II BANK VARIABLES>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private int RSSID;
	private String bankName;
	private double cashBalances;
	private double assets;
	private double liabilities;
	private double equity;
	private double rmbsAssets;
	private double cdsProtectionSold;
	private double cdsProtectionBought;
	private double teir1Capital;
	private double teir2Capital;
	private double rmbsSecuritisation;
	private double rmbsHESecuritisation;
	private double rmbsCreditEnhancementAndLiquidityProvissions;
	private double rmbsHECreditEnhancementAndLiquidityProvissions;
	private double SIVCreditEnhancementAndLiquidityProvissions;
	private double subprimeRMBSAssets;
	private ArrayList<double[][]> activeBasisTrades;
	
	private double injection;

	CDSBasisTrade basisTrade;
	private double basisTradeCapitalSaving;
	private double MezzinineTrancheCapitalSaving;
	private double currentQuaterBasisIncome;
	private double totalAccumulatedBasisIncome;
	private double currentQuaterTradeIncome;
	private double totalAccumulatedCapitalSavings;
	private double totalAccumulatedTrancheSavings;
	private double unwindIncome;
	private double unwindNotional;
	private double totalSubprimeRMBSExposure;
	public double[] securitisationExposureRiskWeights;
	public double[][] derivativesSpread;
	public double[] cashSpread;
	double currentLIBORRate;
	double volatilityThreshold;
	int[] ratingABX;
	double [] riskWeightsABX_HE;
	double [] abxTrancheStructure;
	double counterpartyRiskWeight;
	double[] counterpartyRiskW;
	double teir1CapitalAllocation;
	private boolean basel;
	private double basisTradeTrancheSavings;
	private double leverageInjection;
	private double MezzanineTrancheRMBSExposures;
	private double currentMezzanineTrancheRMBSExposures;

	private ArrayList<Double> totalSubprimeExposuresArrayList;
	private ArrayList<Double> capitalSavingsArrayList;
	private ArrayList<Double> capitalSavingsOnMezTrancheArrayList;
	private ArrayList<Double> basisTradeIncomeArrayList;
	private ArrayList<Double> trancheSavingsArrayList;

	private double sumLeveragableAmount;
	private double defaultMTMPnLTrancheAAA;
	private double defaultMTMPnLTrancheAA;
	private double defaultMTMPnLTrancheA;
	private double defaultMTMPnLTrancheBBB;
	private double defaultMTMPnLTrancheBBB3;
	private double MezzanineTrancheRMBSExposurePnL;
	private double accumulatedMezzanineTrancheRMBSExposurePnL;





	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Class Constructors>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public BaselIIBank(int rssid, String bankName, double cashbalances, double ast, double liab, double eq, double rmbsA,
			double cdsPS, double cdsPB,	double teir1C, double rmbsS, 
			double rmbsCEnLProvissions, double SIVCEndLProvissions, double vThreshold, double counterpartyRiskWeight, boolean baselII,
			boolean modelTypeRMBS, marketEnviromentConfigurator env, Economy econ){

		/**
		 * 
		 */

		setRSSID(rssid);
		setBankName(bankName);
		setCashBalances(cashbalances);
		setAssets(ast);
		setLiabilities(liab);
		setEquity(eq);
//		if (modelTypeRMBS == true){
//			rmbsAssets = rmbsA*0.1;
//		}else{
//			rmbsAssets = rmbsA;
//		}
		rmbsAssets = rmbsA;
		setCdsProtectionSold(cdsPS);
		setCdsProtectionBought(cdsPB);
		setTeir1Capital(teir1C);
		setRmbsSecuritisation(rmbsS);
		setRmbsHESecuritisation(rmbsS);
		setRmbsCreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setRmbsHECreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setSIVCreditEnhancementAndLiquidityProvissions(SIVCEndLProvissions);
		injection = 0;
		setSubprimeRMBSAssets(rmbsAssets);
		this.basisTrade = new CDSBasisTrade();
		this.activeBasisTrades = new ArrayList<double[][]>();
		this.totalSubprimeExposuresArrayList = new ArrayList<Double>();
		this.capitalSavingsArrayList = new ArrayList<Double>();
		this.basisTradeIncomeArrayList = new ArrayList<Double>();
		this.trancheSavingsArrayList = new ArrayList<Double>();

		setCurrentQuaterBasisIncome(0.0);
		setBasisTradeCapitalSaving(0.0);
		this.totalAccumulatedBasisIncome = 0.0;
		setUnwindIncome(0.0);
		setUnwindNotional(0.0);
		setTeir1CapitalAllocation(teir1C*1);
		this.totalAccumulatedCapitalSavings = 0.0;
		this.totalAccumulatedTrancheSavings = 0.0;
		this.currentQuaterTradeIncome = 0;
		this.totalSubprimeRMBSExposure = 0;
		this.volatilityThreshold = vThreshold;
		setCounterpartyRiskWeight(counterpartyRiskWeight);
		this.basel = baselII;
		if(baselII == true){
			this.riskWeightsABX_HE = BaselIICapital.riskWeightingABX_HE;
		}else{
			this.riskWeightsABX_HE = BaselIICapital.modifiedBaselIRMBS;
		}
		this.abxTrancheStructure  = BaselIICapital.abxTrancheStructure;
		setEnvironment(env);
		setEconomy(econ);


	}


	public BaselIIBank(int rssid, String bankName, double cashbalances, double ast, double liab, double eq, double rmbsA,
			double cdsPS, double cdsPB,	double teir1C, double rmbsS, 
			double rmbsCEnLProvissions, double SIVCEndLProvissions, double vThreshold, double[] counterpartyRiskWeight, boolean baselII,
			boolean modelTypeRMBS, marketEnviromentConfigurator env, Economy econ){

		/**
		 * 
		 */

		setRSSID(rssid);
		setBankName(bankName);
		setCashBalances(cashbalances);
		setAssets(ast);
		setLiabilities(liab);
		setEquity(eq);
//		if (modelTypeRMBS == true){
//			rmbsAssets = rmbsA*0.1;
//		}else{
//			rmbsAssets = rmbsA;
//		}
		rmbsAssets = rmbsA;
		setCdsProtectionSold(cdsPS);
		setCdsProtectionBought(cdsPB);
		setTeir1Capital(teir1C);
		setRmbsSecuritisation(rmbsS);
		setRmbsHESecuritisation(rmbsS);
		setRmbsCreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setRmbsHECreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setSIVCreditEnhancementAndLiquidityProvissions(SIVCEndLProvissions);
		setSubprimeRMBSAssets(rmbsAssets);
		this.basisTrade = new CDSBasisTrade();
		this.activeBasisTrades = new ArrayList<double[][]>();
		this.totalSubprimeExposuresArrayList = new ArrayList<Double>();
		this.capitalSavingsArrayList = new ArrayList<Double>();
		this.basisTradeIncomeArrayList = new ArrayList<Double>();
		this.trancheSavingsArrayList = new ArrayList<Double>();
		setCurrentQuaterBasisIncome(0.0);
		setBasisTradeCapitalSaving(0.0);
		this.totalAccumulatedBasisIncome = 0.0;
		this.totalAccumulatedTrancheSavings = 0.0;
		setUnwindIncome(0.0);
		setUnwindNotional(0.0);
		setTeir1CapitalAllocation(teir1C*1);
		this.totalAccumulatedCapitalSavings = 0.0;
		this.currentQuaterTradeIncome = 0;
		this.totalSubprimeRMBSExposure = 0;
		this.volatilityThreshold = vThreshold;
		this.counterpartyRiskW = counterpartyRiskWeight;
		this.basel = baselII;
		if(baselII == true){
			this.riskWeightsABX_HE = BaselIICapital.riskWeightingABX_HE;
		}else{
			this.riskWeightsABX_HE = BaselIICapital.modifiedBaselIRMBS;
		}
		this.abxTrancheStructure  = BaselIICapital.abxTrancheStructure;
		injection = 0;
		setEnvironment(env);
		setEconomy(econ);

	}


	public BaselIIBank(int rssid, String bankName, double cashbalances, double ast, double liab, double eq, double rmbsA,
			double cdsPS, double cdsPB,	double teir1C, double rmbsS, double rmbsHES,	
			double rmbsCEnLProvissions, double rmbsHECEndLProvissions, double vThreshold, double counterpartyRiskWeight, 
			boolean baselII, boolean modelTypeRMBS, marketEnviromentConfigurator env, Economy econ){

		/**
		 * 
		 */

		setRSSID(rssid);
		setBankName(bankName);
		setCashBalances(cashbalances);
		setAssets(ast);
		setLiabilities(liab);
		setEquity(eq);
//		if (modelTypeRMBS == true){
//			rmbsAssets = rmbsA*0.1;
//		}else{
//			rmbsAssets = rmbsA;
//		}
		rmbsAssets = rmbsA;
		injection = 0;
		setCdsProtectionSold(cdsPS);
		setCdsProtectionBought(cdsPB);
		setTeir1Capital(teir1C);
		setRmbsSecuritisation(rmbsS);
		setRmbsHESecuritisation(rmbsHES);
		setRmbsCreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setRmbsHECreditEnhancementAndLiquidityProvissions(rmbsHECEndLProvissions);
		setSubprimeRMBSAssets(rmbsAssets);
		this.basisTrade = new CDSBasisTrade();
		this.activeBasisTrades = new ArrayList<double[][]>();
		this.totalSubprimeExposuresArrayList = new ArrayList<Double>();
		this.capitalSavingsArrayList = new ArrayList<Double>();
		this.basisTradeIncomeArrayList = new ArrayList<Double>();
		this.trancheSavingsArrayList = new ArrayList<Double>();
		setCurrentQuaterBasisIncome(0.0);
		setBasisTradeCapitalSaving(0.0);
		this.totalAccumulatedBasisIncome = 0.0;
		this.totalAccumulatedTrancheSavings = 0.0;
		setUnwindIncome(0.0);
		setUnwindNotional(0.0);
		setTeir1CapitalAllocation(teir1C*1);
		this.totalAccumulatedCapitalSavings = 0.0;
		this.currentQuaterTradeIncome = 0;
		this.totalSubprimeRMBSExposure = 0;
		this.volatilityThreshold = vThreshold;
		setCounterpartyRiskWeight(counterpartyRiskWeight);
		if(baselII == true){
			this.riskWeightsABX_HE = BaselIICapital.riskWeightingABX_HE;
		}else{
			this.riskWeightsABX_HE = BaselIICapital.modifiedBaselIRMBS;
		}
		this.abxTrancheStructure  = BaselIICapital.abxTrancheStructure;
		setEnvironment(env);
		setEconomy(econ);


	}

	public BaselIIBank(int rssid, String bankName, double cashbalances, double ast, double liab, double eq, double rmbsA,
			double cdsPS, double cdsPB,	double teir1C, double rmbsS, 
			double rmbsCEnLProvissions, double SIVCEndLProvissions, double vThreshold, double counterpartyRiskWeight, boolean baselII){

		/**
		 * 
		 */

		setRSSID(rssid);
		setBankName(bankName);
		setCashBalances(cashbalances);
		setAssets(ast);
		setLiabilities(liab);
		setEquity(eq);
//		rmbsAssets = rmbsA*0.1;
		rmbsAssets = rmbsA;
		setCdsProtectionSold(cdsPS);
		setCdsProtectionBought(cdsPB);
		setTeir1Capital(teir1C);
		setRmbsSecuritisation(rmbsS);
		setRmbsHESecuritisation(rmbsS);
		setRmbsCreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setRmbsHECreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setSIVCreditEnhancementAndLiquidityProvissions(SIVCEndLProvissions);
		injection = 0;
		setSubprimeRMBSAssets(rmbsAssets);
		this.basisTrade = new CDSBasisTrade();
		this.activeBasisTrades = new ArrayList<double[][]>();
		this.totalSubprimeExposuresArrayList = new ArrayList<Double>();
		this.capitalSavingsArrayList = new ArrayList<Double>();
		this.basisTradeIncomeArrayList = new ArrayList<Double>();
		this.trancheSavingsArrayList = new ArrayList<Double>();

		setCurrentQuaterBasisIncome(0.0);
		setBasisTradeCapitalSaving(0.0);
		this.totalAccumulatedBasisIncome = 0.0;
		setUnwindIncome(0.0);
		setUnwindNotional(0.0);
		setTeir1CapitalAllocation(teir1C*1);
		this.totalAccumulatedCapitalSavings = 0.0;
		this.totalAccumulatedTrancheSavings = 0.0;
		this.currentQuaterTradeIncome = 0;
		this.totalSubprimeRMBSExposure = 0;
		this.volatilityThreshold = vThreshold;
		setCounterpartyRiskWeight(counterpartyRiskWeight);
		this.basel = baselII;
		if(baselII == true){
			this.riskWeightsABX_HE = BaselIICapital.riskWeightingABX_HE;
		}else{
			this.riskWeightsABX_HE = BaselIICapital.modifiedBaselIRMBS;
		}
		this.abxTrancheStructure  = BaselIICapital.abxTrancheStructure;


	}


	public BaselIIBank(int rssid, String bankName, double cashbalances, double ast, double liab, double eq, double rmbsA,
			double cdsPS, double cdsPB,	double teir1C, double rmbsS, 
			double rmbsCEnLProvissions, double SIVCEndLProvissions, double vThreshold, double[] counterpartyRiskWeight, boolean baselII
			){

		/**
		 * 
		 */

		setRSSID(rssid);
		setBankName(bankName);
		setCashBalances(cashbalances);
		setAssets(ast);
		setLiabilities(liab);
		setEquity(eq);
//		rmbsAssets = rmbsA*0.1;
		rmbsAssets = rmbsA;
		setCdsProtectionSold(cdsPS);
		setCdsProtectionBought(cdsPB);
		setTeir1Capital(teir1C);
		setRmbsSecuritisation(rmbsS);
		setRmbsHESecuritisation(rmbsS);
		setRmbsCreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setRmbsHECreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setSIVCreditEnhancementAndLiquidityProvissions(SIVCEndLProvissions);
		setSubprimeRMBSAssets(rmbsAssets);
		this.basisTrade = new CDSBasisTrade();
		this.activeBasisTrades = new ArrayList<double[][]>();
		this.totalSubprimeExposuresArrayList = new ArrayList<Double>();
		this.capitalSavingsArrayList = new ArrayList<Double>();
		this.basisTradeIncomeArrayList = new ArrayList<Double>();
		this.trancheSavingsArrayList = new ArrayList<Double>();
		setCurrentQuaterBasisIncome(0.0);
		setBasisTradeCapitalSaving(0.0);
		this.totalAccumulatedBasisIncome = 0.0;
		this.totalAccumulatedTrancheSavings = 0.0;
		setUnwindIncome(0.0);
		setUnwindNotional(0.0);
		setTeir1CapitalAllocation(teir1C*1);
		this.totalAccumulatedCapitalSavings = 0.0;
		this.currentQuaterTradeIncome = 0;
		this.totalSubprimeRMBSExposure = 0;
		this.volatilityThreshold = vThreshold;
		this.counterpartyRiskW = counterpartyRiskWeight;
		this.basel = baselII;
		if(baselII == true){
			this.riskWeightsABX_HE = BaselIICapital.riskWeightingABX_HE;
		}else{
			this.riskWeightsABX_HE = BaselIICapital.modifiedBaselIRMBS;
		}
		this.abxTrancheStructure  = BaselIICapital.abxTrancheStructure;
		injection = 0;

	}


	public BaselIIBank(int rssid, String bankName, double cashbalances, double ast, double liab, double eq, double rmbsA,
			double cdsPS, double cdsPB,	double teir1C, double rmbsS, double rmbsHES,	
			double rmbsCEnLProvissions, double rmbsHECEndLProvissions, double vThreshold, double counterpartyRiskWeight, 
			boolean baselII){

		/**
		 * 
		 */

		setRSSID(rssid);
		setBankName(bankName);
		setCashBalances(cashbalances);
		setAssets(ast);
		setLiabilities(liab);
		setEquity(eq);
//		rmbsAssets = rmbsA*0.1;
		rmbsAssets = rmbsA;
		setCdsProtectionSold(cdsPS);
		setCdsProtectionBought(cdsPB);
		setTeir1Capital(teir1C);
		setRmbsSecuritisation(rmbsS);
		setRmbsHESecuritisation(rmbsHES);
		setRmbsCreditEnhancementAndLiquidityProvissions(rmbsCEnLProvissions);
		setRmbsHECreditEnhancementAndLiquidityProvissions(rmbsHECEndLProvissions);
		setSubprimeRMBSAssets(rmbsAssets);
		this.basisTrade = new CDSBasisTrade();
		this.activeBasisTrades = new ArrayList<double[][]>();
		this.totalSubprimeExposuresArrayList = new ArrayList<Double>();
		this.capitalSavingsArrayList = new ArrayList<Double>();
		this.basisTradeIncomeArrayList = new ArrayList<Double>();
		this.trancheSavingsArrayList = new ArrayList<Double>();
		setCurrentQuaterBasisIncome(0.0);
		setBasisTradeCapitalSaving(0.0);
		this.totalAccumulatedBasisIncome = 0.0;
		this.totalAccumulatedTrancheSavings = 0.0;
		setUnwindIncome(0.0);
		setUnwindNotional(0.0);
		setTeir1CapitalAllocation(teir1C*1);
		this.totalAccumulatedCapitalSavings = 0.0;
		this.currentQuaterTradeIncome = 0;
		this.totalSubprimeRMBSExposure = 0;
		this.volatilityThreshold = vThreshold;
		setCounterpartyRiskWeight(counterpartyRiskWeight);
		if(baselII == true){
			this.riskWeightsABX_HE = BaselIICapital.riskWeightingABX_HE;
		}else{
			this.riskWeightsABX_HE = BaselIICapital.modifiedBaselIRMBS;
		}
		this.abxTrancheStructure  = BaselIICapital.abxTrancheStructure;
		injection = 0;

	}


	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Utility Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CDS-Basis Arbitrage Trade Utility Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * This method defines the time step for basis trading
	 * @param totalUnwindGain
	 * @param totalPeriodicBasisIncome
	 * @param CapitalSavings
	 */

	//	public void basisTradeTimeStep(long time, double[] tranchingStructure, double[][] derivativesSpread, 
	//			double [] cashSpread, double[] exposureRiskWeights, double counterPartyRiskWeight, double libor){
	public void basisTradeTimeStep(int maximumEarningMethod, boolean crm, int startYear, long time, double[][] derivativesSpread, 
			double [] cashSpread, double libor, double haircut){

		int model = 1; //this sets the timeseres to be plotted 1 means plot data from basis trade model
		//0 means plot data from securitisation model this should always be 1 in this method call

		if(time == 0){ // Purely the start period basis trade. 11307v 27500

			//makeBasisTrade(double notional, double[] tranchingStructure, double[][] derivativeSpread, 
			//double[] cashSpread, double[] exposureRiskWeights, double counterPartyRiskWeight, double libor)

			if(this.basel == false){
				this.makeBasisTrade(maximumEarningMethod, crm, subprimeRMBSAssets, leverageInjection, abxTrancheStructure, derivativesSpread, 
						cashSpread, riskWeightsABX_HE, counterpartyRiskW, libor, haircut);
			} else{
				this.makeBasisTrade(maximumEarningMethod, crm, subprimeRMBSAssets, leverageInjection, abxTrancheStructure, derivativesSpread, 
						cashSpread, riskWeightsABX_HE, counterpartyRiskWeight, libor, haircut);
			}


			setCurrentQuaterBasisIncomeBtime(0); //ensures full quarters worth of trade income is defined.
			setCurrentQuaterCapitalSaving(0);
			setCurrentTrancheSavings(0);
			setCurrentQuaterTradeIncome(); //add quarter basis income to capital saving.
			setSubprimeRMBSAssets(getCurrentQuaterTradeIncome() + unwindIncome);// set the new subprimeRMBSAssets
//			setTotalAccumulatedBasisIncome(0);
			setTotalAccumulatedBasisIncome();
			setTotalAccumulatedCapitalSavings();
			setTotalAccumulatedMezTrancheCapitalSavings();
			setTotalSubprimeExposure();

		} else{
			if((time % 3 == 0) && (time > 0)){//where a full quarter is reached and is not the start period
				/**
				 * The following 8-lines of code follow the logic, that at the begining of each new quater,
				 * we first determine which trading positions to close and collect all earnings from 
				 * closing those positions as well as all income from active positions and accumulated capital savings
				 * from the previous investment period or quarter.
				 * This resulting value is collected in the variable subprimeRMBSAssets which is the period on period 
				 * allocation of cash earnings to the purchase of further structured notes/bonds/ABS/MBS etc
				 */
				if(this.enviroment.isUnwindingOfPositions()){
					closeBasisTrade(derivativesSpread, cashSpread);
				}
				if(this.totalSubprimeRMBSExposure > 0){
					setSubprimeRMBSAssets(this.injection + getCurrentQuaterTradeIncome() + getUnwindIncome());// set the new subprimeRMBSAssets
				} 
//					else {
//					setSubprimeRMBSAssets(this.subprimeRMBSAssets + getCurrentQuaterTradeIncome() + getUnwindIncome());
//				}
				setTotalSubprimeExposure();

				//now make the new basis trade
				if(this.basel == false){
					this.makeBasisTrade(maximumEarningMethod, crm, subprimeRMBSAssets, leverageInjection, abxTrancheStructure, derivativesSpread, 
							cashSpread, riskWeightsABX_HE, counterpartyRiskW, libor, haircut);
				} else{
					this.makeBasisTrade(maximumEarningMethod, crm, subprimeRMBSAssets, leverageInjection, abxTrancheStructure, derivativesSpread, 
							cashSpread, riskWeightsABX_HE, counterpartyRiskWeight, libor, haircut);
				}
				setCurrentQuaterBasisIncomeBtime(time); //ensures full quarters worth of trade income is defined.
				setCurrentQuaterCapitalSaving(((int)time/3));
				setCurrentTrancheSavings(((int)time/3));
				setCurrentQuaterTradeIncome(); //add quarter basis income to capital saving.
				//setTotalAccumulatedBasisIncome(time);
				setTotalAccumulatedBasisIncome();
				setTotalAccumulatedCapitalSavings();
				setTotalAccumulatedTrancheSavings();
				setTotalAccumulatedMezTrancheCapitalSavings();
				setMezzanineTrancheRMBSExposures();
				setTotalSubprimeExposure();

			}else{// where a full quarter is not reached monthly payments are made and recieved
//				setCurrentQuaterBasisIncome(time);
//				setCurrentQuaterTradeIncome();
//				//setTotalAccumulatedBasisIncome(time);
//				setTotalAccumulatedBasisIncome();
//				setTotalAccumulatedCapitalSavings();
//				setTotalAccumulatedTrancheSavings();
//				setTotalAccumulatedMezTrancheCapitalSavings();
//				setTotalSubprimeExposure();
//				addToSeries(time, startYear, model);

			}
		}
	}
	
	
	
	
	/**
	 * This method defines the time step for basis trading
	 * this is the method used in the JAS simulation implementation
	 * @param totalUnwindGain
	 * @param totalPeriodicBasisIncome
	 * @param CapitalSavings
	 */

	//	public void basisTradeTimeStep(long time, double[] tranchingStructure, double[][] derivativesSpread, 
	//			double [] cashSpread, double[] exposureRiskWeights, double counterPartyRiskWeight, double libor){

	

	
	public void basisTradeTimeStep(){
		
		int model = 1; //this sets the timeseres to be plotted 1 means plot data from basis trade model
		//0 means plot data from securitisation model this should always be 1 in this method call
		
		long time = Sim.getAbsoluteTime();
		
		int simTime = (int) time/3;
		
		setCurrentDerivativesSpreads(this.economy.getCDSSPreads().get((int) time/3));
		setCurrentCashSpreads(this.economy.getCashSPreads().get((int) time/3));
		setCurrentLiborRate(this.economy.getLiborRates()[((int) time/3)]);
		
		

		if(time == 0){ // Purely the start period basis trade. 11307v 27500

			//makeBasisTrade(double notional, double[] tranchingStructure, double[][] derivativeSpread, 
			//double[] cashSpread, double[] exposureRiskWeights, double counterPartyRiskWeight, double libor)

			
			
			if(this.basel == false){
				this.makeBasisTrade(this.enviroment.getMaximumEarningMethod(), 
						this.enviroment.getCRM(), subprimeRMBSAssets, leverageInjection, abxTrancheStructure, derivativesSpread, 
						cashSpread, riskWeightsABX_HE, counterpartyRiskW, currentLIBORRate, this.enviroment.getHaircut());
			} else{
				this.makeBasisTrade(this.enviroment.getMaximumEarningMethod(), this.enviroment.getCRM(), subprimeRMBSAssets, leverageInjection, abxTrancheStructure, derivativesSpread, 
						cashSpread, riskWeightsABX_HE, counterpartyRiskWeight, currentLIBORRate, this.enviroment.getHaircut());
			}


			setCurrentQuaterBasisIncomeBtime(0); //ensures full quarters worth of trade income is defined.
			setCurrentQuaterCapitalSaving(0);
			setCurrentTrancheSavings(0);
			setCurrentQuaterTradeIncome(); //add quarter basis income to capital saving.
			setSubprimeRMBSAssets(getCurrentQuaterTradeIncome() + unwindIncome);// set the new subprimeRMBSAssets
//			setTotalAccumulatedBasisIncome(0);
			setTotalAccumulatedBasisIncome();
			setTotalAccumulatedCapitalSavings();
			setTotalAccumulatedMezTrancheCapitalSavings();
			setTotalSubprimeExposure();
			setMezzanineTrancheRMBSExposures();

		} else{
			if((time % 3 == 0) && (time > 0)){//where a full quarter is reached and is not the start period
				/**
				 * The following 8-lines of code follow the logic, that at the begining of each new quater,
				 * we first determine which trading positions to close and collect all earnings from 
				 * closing those positions as well as all income from active positions and accumulated capital savings
				 * from the previous investment period or quarter.
				 * This resulting value is collected in the variable subprimeRMBSAssets which is the period on period 
				 * allocation of cash earnings to the purchase of further structured notes/bonds/ABS/MBS etc
				 */
				if(this.enviroment.isUnwindingOfPositions()){
					closeBasisTrade(derivativesSpread, cashSpread);
				}
				
//				setLeveragedInjection(simTime);
				
				
				if(this.totalSubprimeRMBSExposure > 0){
					setSubprimeRMBSAssets(this.injection + getCurrentQuaterTradeIncome() + getUnwindIncome());// set the new subprimeRMBSAssets
				} 
//					else {
//					setSubprimeRMBSAssets(this.subprimeRMBSAssets + getCurrentQuaterTradeIncome() + getUnwindIncome());
//				}
				setTotalSubprimeExposure();

				//now make the new basis trade
				if(this.basel == false){
					this.makeBasisTrade(this.enviroment.getMaximumEarningMethod(), this.enviroment.getCRM(), subprimeRMBSAssets, leverageInjection, abxTrancheStructure, derivativesSpread, 
							cashSpread, riskWeightsABX_HE, counterpartyRiskW, currentLIBORRate, this.enviroment.getHaircut());
				} else{
					this.makeBasisTrade(this.enviroment.getMaximumEarningMethod(), this.enviroment.getCRM(), subprimeRMBSAssets, leverageInjection, abxTrancheStructure, derivativesSpread, 
							cashSpread, riskWeightsABX_HE, counterpartyRiskWeight, currentLIBORRate, this.enviroment.getHaircut());
				}
				setLeveragedInjection(simTime);
				
				setCurrentQuaterBasisIncomeBtime(time); //ensures full quarters worth of trade income is defined.
				setCurrentQuaterCapitalSaving(((int)time/3));
				setCurrentTrancheSavings(((int)time/3));
				setCurrentQuaterTradeIncome(); //add quarter basis income to capital saving.
				//setTotalAccumulatedBasisIncome(time);
				setTotalAccumulatedBasisIncome();
				setTotalAccumulatedCapitalSavings();
				setTotalAccumulatedTrancheSavings();
				setTotalAccumulatedMezTrancheCapitalSavings();
				setTotalSubprimeExposure();
				setMezzanineTrancheRMBSExposures();
				double cCapSav = (double) Math.round(this.basisTradeTrancheSavings * 10000) / 10000;
				if( cCapSav == 0){
					this.setDefaultMTMPnLOnMezzanineTrancheRMBSExposures();
				}

			}else{// where a full quarter is not reached monthly payments are made and recieved
//				setCurrentQuaterBasisIncome(time);
//				setCurrentQuaterTradeIncome();
//				//setTotalAccumulatedBasisIncome(time);
//				setTotalAccumulatedBasisIncome();
//				setTotalAccumulatedCapitalSavings();
//				setTotalAccumulatedTrancheSavings();
//				setTotalAccumulatedMezTrancheCapitalSavings();
//				setTotalSubprimeExposure();
//				addToSeries(time, startYear, model);

			}
		}
		
		System.out.println(this.toString());
	}

	

	private void setLeveragedInjection(int simTime) {
		// TODO Auto-generated method stub
		boolean leverage = this.enviroment.isLeveragedTrades();
		double currentBorrowingLiborRate = 0.01*this.economy.getDefaultLIBORBorrowingRates()[simTime];
		double tempInjection = this.leverageInjection;
		if(leverage == false){
			this.leverageInjection = 0;
		} else {
			if(simTime > 0){
				double [][] tempBTR = this.activeBasisTrades.get(simTime - 1);
				if(simTime == 1){
					double sLA1 = 0;
					for(int i = 0; i < tempBTR[7].length; i++){
						sLA1 += tempBTR[7][i]; 
					}
					if(this.enviroment.getCRM()){
						setSumLeveragableAmount(sLA1);
					}else {
						setSumLeveragableAmount(0.0);
					}
					this.leverageInjection = 0;
				} else{
					double sLA2 = 0;
					double bs = 0;
					this.leverageInjection = this.sumLeveragableAmount/currentBorrowingLiborRate;
					System.out.println("The notional borrowing is: " + this.leverageInjection);
					
					//Now go through the previous basis trade report and pick up the cds basis the previous trade position earned
					for(int i = 0; i < tempBTR[7].length; i++){
						if(tempBTR[7][i] > 0.0){
							bs = -1.0 * tempBTR[0][i];// remember that the the basis is defined as (CDS spread - Bond spread) so to get the correct basis income direction, you need to multiply by -1 
							
							System.out.println("This bais trade is on a cds basis of: " + bs);
						}
						
					}
					sLA2 = this.leverageInjection*bs;
					System.out.println("The notional value on which leveraged borrowing can occur is: " + sLA2);
					if(this.enviroment.getCRM()){
						setSumLeveragableAmount(sLA2);
					}else {
						setSumLeveragableAmount(0.0);
					}
				}
			}
		}
	}
	
	public void setSumLeveragableAmount(double sLA){
		this.sumLeveragableAmount = sLA;
		
	}


	public void buyBackUnwoundNotes(double totalUnwinds, double totalIssuance, int time){

		if(time % 3 != 0){

			setTeir1CapitalAllocation(this.getTeir1CapitalAllocation() - 0);

		} else {

			double dep = (this.getRmbsHESecuritisation()/totalIssuance)*totalUnwinds;

			setTeir1CapitalAllocation(this.getTeir1CapitalAllocation() - dep);

		}


	}

	private void  makeBasisTrade(int maximumEarningMethod, boolean crm, double notional, double leverageInjection, double[] tranchingStructure, double[][] derivativeSpread, 
			double[] cashSpread, double[] exposureRiskWeights, double counterPartyRiskWeight, double libor, double haircut){

		//Make a method call to the CDSBasisTrade strategy class to generate report on possible CDS basis trades
		//and assign the output report to a new report object that the bank keeps on file
		double [][] btr = CDSBasisTrade.bankOpenNegativeBasisPos(maximumEarningMethod, crm, notional, leverageInjection, tranchingStructure, derivativeSpread,
				cashSpread, exposureRiskWeights, counterPartyRiskWeight, libor, haircut);
		//checks to make sure that the bank in question actually buys credit protection on the CDS market
		//if not then the trade indicators in the basis trade report is set to zero.
		if(this.cdsProtectionBought == 0){
			for(int i = 0; i< btr[0].length; i++){
				btr[13][i] = 0;
			}

		}

		//add the new basis trade report to the list of active basis trades the bank is currently engaged in.
		this.activeBasisTrades.add(btr);
	}


	private void  makeBasisTrade(int maximumEarningMethod, boolean crm, double notional, double leverageInjection, double[] tranchingStructure, double[][] derivativeSpread, 
			double[] cashSpread, double[] exposureRiskWeights, double[] counterPartyRiskWeight, double libor, double haircut){

		//Make a method call to the CDSBasisTrade strategy class to generate report on possible CDS basis trades
		//and assign the output report to a new report object that the bank keeps on file
		double [][] btr = CDSBasisTrade.bankOpenNegativeBasisPos(maximumEarningMethod, crm, notional, leverageInjection, tranchingStructure, derivativeSpread,
				cashSpread, exposureRiskWeights, counterPartyRiskWeight, libor, haircut);
		//checks to make sure that the bank in question actually buys credit protection on the CDS market
		//if not then the trade indicators in the basis trade report is set to zero.
		if(this.cdsProtectionBought == 0){
			for(int i = 0; i< btr[0].length; i++){
				btr[13][i] = 0;
			}
		}

		//add the new basis trade report to the list of active basis trades the bank is currently engaged in.
		this.activeBasisTrades.add(btr);
	}


	private void closeBasisTrade(double[][] derivativeSpread, double[] cashSpread){

		double sum = 0;
		double unwindSum = 0;
		boolean activeTrading = Economy.activeTrading;
		for(int i = 0; i < activeBasisTrades.size(); i++){
			//go through the activeBasisTrades list and collect each active basis trade report
			double[][] btr = activeBasisTrades.get(i);
			//create an unwind report based on current market data and the active basis trade report
			double[][] btrClose = CDSBasisTrade.bankCloseNegativeBasisPos(derivativeSpread, cashSpread, btr, volatilityThreshold, activeTrading);

			//now go through and compare both basis trade reports summing the total gains from unwinding 
			//trades where appropriate.
			for(int j = 0; j < btr[0].length; j++){
				if(btr[13][j] == 1.0 && btrClose[3][j] == 1.0){
					sum += btrClose[0][j] * btr[5][j];
					unwindSum += btrClose[2][j];
					btr[13][j] = 0.0;//set the active trade report live trade indicator to zero for all those trades where 
					//an unwind is deemed profitable or maximum loss attained. This also stops the method from double
					//counting at later dates.
				}else{
					sum += 0;
					unwindSum += 0;
				}// end else
			}//end for
			activeBasisTrades.set(i, btr);//replace the basis trade report at point i on active list with the modified 
			//version accounting for trade unwinds.
		}//end for
		setUnwindIncome(sum);//set the current trade unwind income across all active basis trades
		setUnwindNotional(unwindSum);//set the current trade unwind income across all active basis trades
	}//end closeBasisTrade





	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CORE METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CBS BASIS TRADE CORE METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MARK-TO-MARKET POSITIONS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<COMPUTE P&L OF BASIS TRADES>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
	private void setDefaultMTMPnLOnMezzanineTrancheRMBSExposures(){
		
		/**
		 * This method computes the MTM P&L for each of the default tranches the 
		 * banks trade on. In this construction the default tranche structure is made up of 5 tranches as
		 * is the case of the ABX.HE non-default implementation can use this method as a basis to build on.
		 * 
		 * At the time of writing this I didn't have the time or inclination to make the code more generic.
		 * 
		 */
		
		double sumTrancheAAA = 0;
		double sumTrancheAA = 0;
		double sumTrancheA = 0;
		double sumTrancheBBB = 0;
		double sumTrancheBBB3 = 0;
		ArrayList<Double> defaultPerTrancheExposures = new ArrayList<Double>();
		
		
		int periodIndexTrancheAAA = 0;
		int periodIndexTrancheAA = 0;
		int periodIndexTrancheA = 0;
		int periodIndexTrancheBBB = 0;
		int periodIndexTrancheBBB3 = 0;
		ArrayList<Integer> defaultPerTrancheLastInvestmentDateIndex = new ArrayList<Integer>();

			for(int i = 0; i < activeBasisTrades.size(); i++){ // go through the list of active 
				//basis trades and pick out each activeBasis Trade report
				double[][] btr = activeBasisTrades.get(i);
				for(int j = 0; j< btr[0].length; j++){
					switch (j){
					case 0:
						sumTrancheAAA += btr[5][j];//recall that the notional invested is stored in the 5th row of the basis trade report
						periodIndexTrancheAAA = getIndexOfLastBasisTradeOnTranche(btr, i, j);
						System.out.println("tranche AAA sum: " + sumTrancheAAA);
						break;
					case 1:
						sumTrancheAA += btr[5][j];//recall that the notional invested is stored in the 5th row of the basis trade report
						periodIndexTrancheAA = getIndexOfLastBasisTradeOnTranche(btr, i, j);
						System.out.println("tranche AA sum: " + sumTrancheAA);
						break;
					case 2:
						sumTrancheA += btr[5][j];//recall that the notional invested is stored in the 5th row of the basis trade report
						periodIndexTrancheA = getIndexOfLastBasisTradeOnTranche(btr, i, j);
						System.out.println("tranche A sum: " + sumTrancheA);
						break;
					case 3:
						sumTrancheBBB += btr[5][j];//recall that the notional invested is stored in the 5th row of the basis trade report
						periodIndexTrancheBBB = getIndexOfLastBasisTradeOnTranche(btr, i, j);
						System.out.println("tranche BBB sum: " + sumTrancheBBB);
						break;
					case 4:
						sumTrancheBBB3 += btr[5][j];//recall that the notional invested is stored in the 5th row of the basis trade report
						periodIndexTrancheBBB3 = getIndexOfLastBasisTradeOnTranche(btr, i, j);
						System.out.println("tranche BBB- sum: " + sumTrancheBBB3);
						break;
					}
					
				}// end for to go through list of active basis trades
			}
			
			defaultPerTrancheExposures.add(sumTrancheAAA);
			defaultPerTrancheExposures.add(sumTrancheAA);
			defaultPerTrancheExposures.add(sumTrancheA);
			defaultPerTrancheExposures.add(sumTrancheBBB);
			defaultPerTrancheExposures.add(sumTrancheBBB3);
			defaultPerTrancheLastInvestmentDateIndex.add(periodIndexTrancheAAA);
			defaultPerTrancheLastInvestmentDateIndex.add(periodIndexTrancheAA);
			defaultPerTrancheLastInvestmentDateIndex.add(periodIndexTrancheA);
			defaultPerTrancheLastInvestmentDateIndex.add(periodIndexTrancheBBB);
			defaultPerTrancheLastInvestmentDateIndex.add(periodIndexTrancheBBB3);
			
			computeDefaultMTMProfitAndLoss(defaultPerTrancheExposures,defaultPerTrancheLastInvestmentDateIndex);
			

	}
	
	
	private int getIndexOfLastBasisTradeOnTranche(double[][] btr, int currentBTRIndex, int currentTrancheIndex){
		int index = 0;
		if(btr[5][currentTrancheIndex] > 0){
			index = currentBTRIndex;
		}
	return index;
	}
	
	
	private void computeDefaultMTMProfitAndLoss(ArrayList<Double> defaultPerTrancheExposures, 
			ArrayList<Integer> defaultPerTrancheLastInvestmentDateIndex){
	
		this.defaultMTMPnLTrancheAAA = 0;
		this.defaultMTMPnLTrancheAA = 0;
		this.defaultMTMPnLTrancheA = 0;
		this.defaultMTMPnLTrancheBBB = 0;
		this.defaultMTMPnLTrancheBBB3= 0;
		int indexLastChange = this.economy.getPeriodicChangeInABXIndexPrice().size();
		int time = (int) Sim.getAbsoluteTime()/3;
//		double [] priceChange = this.economy.getPeriodicChangeInABXIndexPrice().get(indexLastChange-1);
		
		double [] priceChange = this.economy.getMarkoseABXPriceChangeData().get(time);;
		
		int mtm_data = this.enviroment.getMarkToMarketPrices();
		
		switch (mtm_data){
		case 0:
			priceChange = this.economy.getPeriodicChangeInABXIndexPrice().get(indexLastChange-1);
			break;
		case 1:
			priceChange = this.economy.getPeriodicChangeInABXIndexPrice().get(indexLastChange-1);
			break;
		case 2:
			priceChange = this.economy.getMarkoseABXPriceChangeData().get(time);
			break;
		}
		
		
		for(int j = 0; j< priceChange.length; j++){
			switch (j){
			case 0:
				defaultMTMPnLTrancheAAA = defaultPerTrancheExposures.get(j)*priceChange[j];//recall that the notional invested is stored in the 5th row of the basis trade report
				break;
			case 1:
				if(time > 6){
					defaultMTMPnLTrancheAA = defaultPerTrancheExposures.get(j)*priceChange[j];//recall that the notional invested is stored in the 5th row of the basis trade report
				}
				break;
			case 2:
				defaultMTMPnLTrancheA = defaultPerTrancheExposures.get(j)*priceChange[j];//recall that the notional invested is stored in the 5th row of the basis trade report
				break;
			case 3:
				defaultMTMPnLTrancheBBB = defaultPerTrancheExposures.get(j)*priceChange[j];//recall that the notional invested is stored in the 5th row of the basis trade report
				break;
			case 4:
				defaultMTMPnLTrancheBBB3 = defaultPerTrancheExposures.get(j)*priceChange[j];//recall that the notional invested is stored in the 5th row of the basis trade report
				break;
			}
			
		}// end for to go through list of active basis trades

		this.MezzanineTrancheRMBSExposurePnL = (defaultMTMPnLTrancheAA + defaultMTMPnLTrancheA 
				+ defaultMTMPnLTrancheBBB + defaultMTMPnLTrancheBBB3);
		this.accumulatedMezzanineTrancheRMBSExposurePnL += this.MezzanineTrancheRMBSExposurePnL;
		
	}
		
		
	
	
	private void setMezzanineTrancheRMBSExposures(double mezzExp){
		this.MezzanineTrancheRMBSExposures = mezzExp;
	}
	
	private void setMezzanineTrancheRMBSExposures(){
		double sumTotal = 0;
		double sumCurrent = 0;

			for(int i = 0; i < activeBasisTrades.size(); i++){ // go through the list of active 
				//basis trades and pick out each activeBasis Trade report
				double[][] btr = activeBasisTrades.get(i);
				for(int j = 0; j< btr[0].length; j++){
					if(j > 0) {
						sumTotal += btr[5][j];//recall that the notional invested is stored in the 5th row of the basis trade report
					}
				}
			}// end for to go through list of active basis trades
			

			if(this.enviroment.isLeveragedTrades() == false){
				this.MezzanineTrancheRMBSExposures = sumTotal;
			}
			else{
				this.MezzanineTrancheRMBSExposures = sumTotal - this.accumulatedMezzanineTrancheRMBSExposurePnL;
			}
			
			//set the value of the current Mezzanine tranche exposure
			double[][] btrC = activeBasisTrades.get(activeBasisTrades.size()-1);
			
			for(int j = 0; j< btrC[0].length; j++){
				if(j > 0) {
					sumCurrent += btrC[5][j];//recall that the notional invested is stored in the 5th row of the basis trade report
				}
			} 
			
			if(this.enviroment.isLeveragedTrades() == false){
				this.currentMezzanineTrancheRMBSExposures = sumCurrent;
			}
			else{
				this.currentMezzanineTrancheRMBSExposures = sumCurrent - this.MezzanineTrancheRMBSExposurePnL;
			}
			
					
	

	}
	
	public double getTotalMezzanineTrancheExposure(){
		return this.MezzanineTrancheRMBSExposures;
	}

	public double getCurrentQtrMezzanineTrancheExposure(){
		return this.currentMezzanineTrancheRMBSExposures;
	}

	
	private void setCurrentDerivativesSpreads(double[][] dSpread){
		this.derivativesSpread = dSpread;
	}
	
	
	private void setCurrentCashSpreads(double[] cSpread){
		this.cashSpread = cSpread;
	}
	
	private void setCurrentLiborRate(double lbr){
		this.currentLIBORRate = lbr;
	}


	private void setCurrentQuaterCapitalSaving(int arrayListIndex) {
		// this method resets the capital saving at every point a new basis trade takes place
		double cSave = 0;
		double [][] btr = activeBasisTrades.get(arrayListIndex);
		for(int i = 0; i < btr[0].length; i++){
			if(btr[13][i] == 1.0){
				//				cSave += btr[6][i];
				cSave = btr[12][0];
			}
		}//end for
		if(this.enviroment.getCRM()){
			setBasisTradeCapitalSaving(cSave);
		}else {
			setBasisTradeCapitalSaving(0.0);
		}
	}

	public void setCurrentQuaterBasisIncomeBtime(long time) {
		//long payment;
		double sum = 0;
		int t = (int) time;
		int payment = Math.max(0, t - (3*((t - 1)/3)));

//		for(int i = 0; i < activeBasisTrades.size(); i++){ // go through the list of active 
//			//basis trades and pick out each activeBasis Trade report
//			double[][] btr = activeBasisTrades.get(i);
//			for(int j = 0; j < btr[0].length; j++){ //sum the income each tranche 
//				//in the current basis being looked at.
//				if(btr[13][j] == 1.0){
//					sum += (btr[7][j]*payment);
//				}
//			}//end for to sum the income from all active tranches
//		}// end for to go through list of active basis trades
		int i = activeBasisTrades.size();
		double[][] btr = activeBasisTrades.get(i-1);
		for(int j = 0; j < btr[0].length; j++){ //sum the income each tranche 
		//in the current basis being looked at.
		if(btr[13][j] == 1.0 && j > 0){
			if(btr[0][j] < 0) {
				sum += (btr[7][j]);
			}else{
				sum = 0;
			}
		}
	}//end for to sum the income from all active tranches
		setCurrentQuaterBasisIncome(sum);


	}// end of setCurrentQuaterBasisIncome

	public void setCurrentTrancheSavings(int arrayListIndex) {
		double cSave = 0;
		double [][] btr = activeBasisTrades.get(arrayListIndex);
		cSave = btr[11][0];
		if(this.enviroment.getCRM()){
			setBasisTradeCapitalSaving(cSave);
		}else {
			setBasisTradeCapitalSaving(0.0);
		}
	}// end of setCurrentQuaterBasisIncome


	private void setTotalSubprimeExposure() {
		// TODO Auto-generated method stub
		double sum = 0;
		for(int i = 0; i < activeBasisTrades.size(); i++){ // go through the list of active 
			//basis trades and pick out each activeBasis Trade report
			double[][] btr = activeBasisTrades.get(i);
			for(int j = 0; j < btr[0].length; j++){ //sum the income each tranche 
				//in the current basis being looked at.
				if(btr[13][j] == 1.0){
					sum += (btr[5][j]);
				}
			}
		}
		if(this.enviroment.isLeveragedTrades() == false){
			this.totalSubprimeRMBSExposure = sum;
		}
		else{
			this.totalSubprimeRMBSExposure = sum - this.accumulatedMezzanineTrancheRMBSExposurePnL;
		}
	}

	private void setCurrentQuaterTradeIncome(){
//		currentQuaterTradeIncome = (getCurrentQuaterBasisIncome() + getBasisTradeCapitalSaving());
		currentQuaterTradeIncome = (getBasisTradeCapitalSaving());
	}

	public double getCurrentQuaterTradeIncome(){
		return currentQuaterTradeIncome;
	}

	public void setTotalAccumulatedBasisIncome(long time) {
		double sum = 0;

		for(int i = 0; i < activeBasisTrades.size(); i++){ // go through the list of active 
			//basis trades and pick out each activeBasis Trade report
			long payment = time - (3*i);
			double[][] btr = activeBasisTrades.get(i);
			for(int j = 0; j < btr[0].length; j++){ //sum the income each tranche 
				//in the current basis being looked at.
				if(btr[13][j] == 1.0){
					sum += (btr[7][j]*payment);
				}else {
					sum += 0.0;
				}//end else
			}//end for to sum the income from all active tranches
		}// end for to go through list of active basis trades
		this.totalAccumulatedBasisIncome = sum;
	}// end of setCurrentQuaterBasisIncome
	
	public void setTotalAccumulatedBasisIncome(){
		this.totalAccumulatedBasisIncome += this.currentQuaterBasisIncome;
	}

	private void setTotalAccumulatedCapitalSavings(){

		double sum = 0;

		for(int i = 0; i < activeBasisTrades.size(); i++){ // go through the list of active 
			//basis trades and pick out each activeBasis Trade report
			double[][] btr = activeBasisTrades.get(i);
//			sum += btr[12][0];
			for(int j = 0; j< btr[0].length; j++){
				sum += btr[6][j];
			}
		}// end for to go through list of active basis trades
		if(this.enviroment.getCRM()){
			this.totalAccumulatedCapitalSavings = sum;
		}else {
			this.totalAccumulatedCapitalSavings = 0.0;
		}
		
//		this.totalAccumulatedCapitalSavings += this.getBasisTradeCapitalSaving();
	}

	private void setTotalAccumulatedMezTrancheCapitalSavings(){

		double sum = 0;

		for(int i = 0; i < activeBasisTrades.size(); i++){ // go through the list of active 
			//basis trades and pick out each activeBasis Trade report
			double[][] btr = activeBasisTrades.get(i);
			for(int j = 0; j< btr[0].length; j++){
				if(j > 0) {
					sum += btr[6][j];
				}
			}
		}// end for to go through list of active basis trades
		if(this.enviroment.getCRM()){
			this.MezzinineTrancheCapitalSaving = sum;
		}else {
			this.MezzinineTrancheCapitalSaving = 0.0;
		}
	}

	
	private void setTotalAccumulatedTrancheSavings(){

//		double sum = 0;
//
//		for(int i = 0; i < activeBasisTrades.size(); i++){ // go through the list of active 
//			//basis trades and pick out each activeBasis Trade report
//			double[][] btr = activeBasisTrades.get(i);
//			sum += btr[11][0];
//		}// end for to go through list of active basis trades
//		this.totalAccumulatedTrancheSavings = sum;
		this.totalAccumulatedTrancheSavings += this.getBasisTradeTrancheSavings();
	}

	private void setBasisTradeCapitalSaving(double bTCapSaving) {
		//this method initialises the capital savings value at class instance creation 
		this.basisTradeCapitalSaving = bTCapSaving;
	}

	private void setBasisTradeTrancheSavings(double bTCapSaving) {
		//this method initialises the capital savings value at class instance creation 
		this.basisTradeTrancheSavings = bTCapSaving;
	}


	private void setUnwindIncome(double d) {
		// TODO Auto-generated method stub
		this.unwindIncome = d;
	}

	public double getUnwindIncome() {
		// TODO Auto-generated method stub
		return unwindIncome;
	}

	private void setUnwindNotional(double unwindSum) {
		// TODO Auto-generated method stub
		this.unwindNotional = unwindSum;
	}

	public double getUnwindNotional() {
		// TODO Auto-generated method stub
		return unwindNotional;
	}

	public double getTotalSubprimeRMBSExposure(){
		return totalSubprimeRMBSExposure;
	}

	private void setTeir1CapitalAllocation(double d) {
		// TODO Auto-generated method stub
		this.teir1CapitalAllocation = d;
	}


	public double getTeir1CapitalAllocation(){
		return teir1CapitalAllocation;
	}


	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Getters and Setters>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//<<<<<<<<<<<<<<<<<<<<<<SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//<<<<<<<<<<<<<<<<<<<<<<GENERIC SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void setRSSID(int rSSID) {
		RSSID = rSSID;
	}
	
	public void setBankName( String bankName){
		this.bankName =  bankName;
	}

	public void setCashBalances(double cashBalances) {
		this.cashBalances = cashBalances;
	}

	public void setAssets(double assets2) {
		this.assets = assets2;
	}

	public void setLiabilities(double liabilities) {
		this.liabilities = liabilities;
	}

	public void setEquity(double equity) {
		this.equity = equity;
	}

	//<<<<<<<<<<<<<<<<<<<<<<CDS BASIS TRADE MODEL SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public void setCdsProtectionSold(double cdsProtectionSold) {
		this.cdsProtectionSold = cdsProtectionSold;
	}

	public void setCdsProtectionBought(double cdsProtectionBought) {
		this.cdsProtectionBought = cdsProtectionBought;
	}

	public void setTeir1Capital(double teir1Capital) {
		this.teir1Capital = teir1Capital;
	}

	public void setTeir2Capital(double teir2Capital) {
		this.teir2Capital = teir2Capital;
	}

	public void setRmbsSecuritisation(double rmbsSecuritisation) {
		this.rmbsSecuritisation = rmbsSecuritisation;
	}

	public void setRmbsHESecuritisation(double rmbsHESecuritisation) {
		this.rmbsHESecuritisation = rmbsHESecuritisation;
	}

	public void setRmbsCreditEnhancementAndLiquidityProvissions(
			double rmbsCreditEnhancementAndLiquidityProvissions) {
		this.rmbsCreditEnhancementAndLiquidityProvissions = rmbsCreditEnhancementAndLiquidityProvissions;
	}

	public void setRmbsHECreditEnhancementAndLiquidityProvissions(
			double rmbsHECELProvissions) {
		this.rmbsHECreditEnhancementAndLiquidityProvissions = rmbsHECELProvissions;
	}

	public void setSubprimeRMBSAssets(double sRMBSAssets) {
		this.subprimeRMBSAssets = sRMBSAssets;
	}
	
	
	public void addCorporateRestructuringInjections(double injection) {
		this.injection = injection;
		this.subprimeRMBSAssets += injection;
	}


	public void setCurrentQuaterBasisIncome(double basisTrade3MonthBasisIncome) {
		this.currentQuaterBasisIncome = basisTrade3MonthBasisIncome;
	}

	private void setCounterpartyRiskWeight(double cRiskWeight) {
		// TODO Auto-generated method stub
		counterpartyRiskWeight = cRiskWeight;
	}

	private void setSIVCreditEnhancementAndLiquidityProvissions(
			double sIVCEndLProvissions) {
		this.SIVCreditEnhancementAndLiquidityProvissions = sIVCEndLProvissions;
	}
	
	

    
    private void setEnvironment(marketEnviromentConfigurator env){
    	this.enviroment = env;
    }
    
    private void setEconomy(Economy econ){
    	this.economy = econ;
    } 

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<BASIS TRADE MODEL GETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public double getCurrentQuaterBasisIncome() {
		return currentQuaterBasisIncome;
	}

	public double getTotalAccumulatedCapitalSavings() {
		return totalAccumulatedCapitalSavings;
	}// end of getTotalAccumulatedBasisIncome 		this.totalAccumulatedTrancheSavings = 0.0;

	public double getTotalAccumulatedTrancheSavings() {
		return totalAccumulatedTrancheSavings;
	}// end of getTotalAccumulatedBasisIncome 

	public double getTotalAccumulatedBasisIncome() {
		return totalAccumulatedBasisIncome;
	}// end of getTotalAccumulatedBasisIncome

	public double getBasisTradeCapitalSaving() {
		return basisTradeCapitalSaving;
	}

	public double getBasisTradeTrancheSavings() {
		return basisTradeTrancheSavings;
	}


	public double getSubprimeRMBSAssets() {
		return subprimeRMBSAssets;
	}

	public double getRmbsHECreditEnhancementAndLiquidityProvissions() {
		return rmbsHECreditEnhancementAndLiquidityProvissions;
	}

	public double getRmbsHESecuritisation() {
		return rmbsHESecuritisation;
	}

	public double getRmbsSecuritisation() {
		return rmbsSecuritisation;
	}

	public double getRmbsCreditEnhancementAndLiquidityProvissions() {
		return rmbsCreditEnhancementAndLiquidityProvissions;
	}

	public double getTeir2Capital() {
		return teir2Capital;
	}

	public double getTeir1Capital() {
		return teir1Capital;
	}

	public double getCdsProtectionBought() {
		return cdsProtectionBought;
	}

	public double getCdsProtectionSold() {
		return cdsProtectionSold;
	}

	public double getEquity() {
		return equity;
	}

	public double getLiabilities() {
		return liabilities;
	}

	public double getAssets() {
		return assets;
	}

	public double getRMBSAssets() {
		return rmbsAssets;
	}


	public double getCashBalances() {
		return cashBalances;
	}

	public int getRSSID() {
		return RSSID;
	}


	private double getSIVCreditEnhancementAndLiquidityProvissions() {
		return SIVCreditEnhancementAndLiquidityProvissions;
	}

	public ArrayList<Double> getTotalSubprimeExposuresArrayList() {
		return totalSubprimeExposuresArrayList;
	}

	public ArrayList<Double> getCapitalSavingsArrayList() {
		return capitalSavingsArrayList;
	}

	public ArrayList<Double> getBasisTradeIncomeArrayList() {
		return basisTradeIncomeArrayList;
	}






	public String getBankName() {
		return bankName;
	}


	public double getMezzinineTrancheCapitalSaving() {
		return MezzinineTrancheCapitalSaving;
	}


	/* (non-Javadoc)
	 * @see jas.events.ISimEventListener#performAction(int)
	 */
	@Override
	public void performAction(int actionId) {
		switch (actionId)
		{
			case REPRODUCE:
				reproduce(); break;
			case CHECK_LIFE:
				checkLife(); break;
		}

	}

	
	public void die(){
	}


	
	
	public abstract void checkLife();
	
	public abstract void reproduce();

	


	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public String toString(){
		return new String(this.getClass() + " " + this.getBankName() + " IDRRSD: " + this.getRSSID() + " Total Assets: " + this.getAssets() 
				+ " Basis Income: " + this.getCurrentQuaterBasisIncome() + " Capital Savings: " + this.getBasisTradeCapitalSaving());
		
	}




}
