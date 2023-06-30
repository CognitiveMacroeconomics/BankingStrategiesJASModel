/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




import jas.engine.Sim;
import jas.engine.SimModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

import org.jfree.data.time.Quarter;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author segun
 */
public class CDSBasisTradeModel  extends SimModel {
	
	int missingBankDataKickIn; //this is used to set bank rmbs data later in the simulation 
								//where the bank has zeros at the simulation starting point
	
	int leverageLIBORDataKickIn;

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    marketEnviromentConfigurator enviroment;
    Economy economy;
    marketDataManager dataManager;
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<FOR JFREECHART OUTPUTTING TEST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    CDSBasisTradeSimResultsOutput textDataExporter;
    
	private double totalAccumulatedMezTrancheSavings;

	private TimeSeries totalAccumulatedMezTrancheSavingsSeries =  new TimeSeries("Aggregate Gross Capital Saving on Mez Tranches");;




	
	private ModelParameters params;

	CDSBasisTradeJASApplicationFrame frame = null;
	private ArrayList<BaselIIBank> bankList = new ArrayList<BaselIIBank>();
	private int maxRun;
	
	long time;

	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CLASS CONSTRUCTORS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	public CDSBasisTradeModel (CDSBasisTradeJASApplicationFrame frame){
		this.frame = frame;
	}

	public CDSBasisTradeModel (){

	}


	public CDSBasisTradeModel (ModelParameters param){
		this.params = param;
	}


    public CDSBasisTradeModel(marketDataManager mDM, marketEnviromentConfigurator mEC){
        this.dataManager = mDM;
        this.enviroment = mEC;
        //runAccessTest();
    }
    
    



	private void buildDataManagement(boolean defaultModel, boolean modelDataTypeOfRMBS,
			int coreBanks) {
		// TODO Auto-generated method stub
		this.dataManager = new marketDataManager();
		this.dataManager.setData(defaultModel, modelDataTypeOfRMBS, coreBanks);
		this.dataManager.setCoreBanksIDsAndNames(coreBanks);
		this.dataManager.setAllDataTables();
	}


	private void buildEnviroment() {
		
		// TODO Auto-generated method stub
		this.enviroment = new marketEnviromentConfigurator(
				this.params.getBasisMaximisationStrategy(), this.params.isUnwindingOfPositions(), this.params.isCreditRiskMitigation(),
				this.params.isRegulatoryRegime(), this.params.getVolatilityThreshold(), this.params.getNumberOfSimulationQuarters(),
				this.params.getSimulationStartYear(), this.params.getSimulationStartQuarter(), this.params.getLeverageHaircut(),
				this.params.isLeveragedTrading(), this.params.getLeverageStartYear(),
				this.params.getLeverageStartQuarter());
		this.enviroment.setAllRMBSBoolean(this.params.isModelRMBSDataType());
		this.enviroment.setMarkToMarketPrices(this.params.getMarkToMarketPrices());
//		System.out.println(this.enviroment.toString());
		
	}
	
	public void buildEconomy() {
		this.economy = new Economy();
		this.economy.setCurrentCDSSPreads(this.dataManager.getCDSSpreadsList());
		this.economy.setCurrentCashSpreads(this.dataManager.getCashSpreadsList());
		this.economy.setCurrentLiborRates(this.dataManager.getLibor());
		this.economy.setDefaultLIBORBorrowingRates(this.dataManager.defaultLIBORBorrowingRates);
//		System.out.println(this.economy.toString());
	}
	
	
	private void buildAgents(){
		buildBanks();
	}
	
	
	private void buildBanks(){
		
		int stYr = this.enviroment.getStartYear();
		int stQtr = this.enviroment.getStartQuarter();
		int dataItemIndexFixed = (stYr - 2002) * 4 + (stQtr);
		
		String[] bankRSSIDList = this.dataManager.bankRSSIDList;
		String[][] bankNamesList = this.dataManager.bankNamesList;
		int RSSID;
		String BANKNAME = null;
		double CHBAL = 0;
		double ASSET = 0;
		double LIAB = 0;
		double EQ = 0;
		double SCMTGBK = 0;
		double CTDDFSWG = 0;
		double CTDDFSWB = 0;
		double RBCT1 = 0;
		double SZLNRES = 0;
		double SZIORESTOT = 0;
		double SZIORESHETOT = 0;
		
		/**
		 * double LIAB = bankData[i][3];
				double EQ = bankData[i][4];
				double SCMTGBK = bankData[i][5];
				double CTDDFSWG = bankData[i][6];
				double CTDDFSWB = bankData[i][7];
				double RBCT1 = bankData[i][8];
				// double RBCT2T = bankData[i][9];
				double SZLNRES = bankData[i][10];
				double SZIORESTOT = bankData[i][11];
				double SZIORESHETOT = bankData[i][12];
				
				getTop12andOthersGenericData
				getTop12andOthersTotalRMBSData
				getTop12andOthersStructuredRMBSData
				
				getTop12andOthersCDSProtectionSoldData
				getTop12andOthersCDSProtectionBoughtData
				getTop12andOthersOutstandingNotionalSecuritisedData

				
		 */
		SABank bank;
		if (this.params.isDefaultModel() == true) {
			for (int i = 0; i < bankRSSIDList.length; i++) {
				// get bank definition values from database
				RSSID = Integer.parseInt(bankRSSIDList[i]);
				if (bankRSSIDList[i] == bankNamesList[i][0]) {
					BANKNAME = bankNamesList[i][1];
				}
				for(int j = 0; j < this.dataManager.getAllBanksBalancesheetCashData().length; j++){
					if(RSSID == this.dataManager.getAllBanksBalancesheetCashData()[j][0]){
						CHBAL = this.dataManager.getAllBanksBalancesheetCashData()[j][dataItemIndexFixed];
					}
				}//end cash balance for-loop
				
				for(int j = 0; j < this.dataManager.getAllBanksTotalAssetsData().length; j++){
					if(RSSID == this.dataManager.getAllBanksTotalAssetsData()[j][0]){
						ASSET = this.dataManager.getAllBanksTotalAssetsData()[j][dataItemIndexFixed];
					}
				}//end Total Assets for-loop
				
				for(int j = 0; j < this.dataManager.getTop12andOthersGenericData().length; j++){
					if(RSSID == this.dataManager.getTop12andOthersGenericData()[j][0]){
						LIAB = this.dataManager.getTop12andOthersGenericData()[j][3];
					}
				}//end Libailities for-loop
				
				for(int j = 0; j < this.dataManager.getTop12andOthersGenericData().length; j++){
					if(RSSID == this.dataManager.getTop12andOthersGenericData()[j][0]){
						EQ = this.dataManager.getTop12andOthersGenericData()[j][4];
					}
				}//end equity for-loop
				
				if(this.params.isModelRMBSDataType() == true){
					for(int j = 0; j < this.dataManager.getTop12andOthersTotalRMBSData().length; j++){
						if(RSSID == this.dataManager.getTop12andOthersTotalRMBSData()[j][0]){
							SCMTGBK = this.dataManager.getTop12andOthersTotalRMBSData()[j][dataItemIndexFixed];
						}
					}//end All RMBS for-loop
				} else if(this.params.isModelRMBSDataType() == false){
					for(int j = 0; j < this.dataManager.getTop12andOthersStructuredRMBSData().length; j++){
						if(RSSID == this.dataManager.getTop12andOthersStructuredRMBSData()[j][0]){
							SCMTGBK = this.dataManager.getTop12andOthersStructuredRMBSData()[j][dataItemIndexFixed];
						}
					}//end Structured RMBS for-loop
				} 
				
				for(int j = 0; j < this.dataManager.getTop12andOthersCDSProtectionSoldData().length; j++){
					if(RSSID == this.dataManager.getTop12andOthersCDSProtectionSoldData()[j][0]){
						CTDDFSWG = this.dataManager.getTop12andOthersCDSProtectionSoldData()[j][dataItemIndexFixed];
					}
				}//end cash balance for-loop
				for(int j = 0; j < this.dataManager.getTop12andOthersCDSProtectionBoughtData().length; j++){
					if(RSSID == this.dataManager.getTop12andOthersCDSProtectionBoughtData()[j][0]){
						CTDDFSWB = this.dataManager.getTop12andOthersCDSProtectionBoughtData()[j][dataItemIndexFixed];
					}
				}//end cash balance for-loop
				for(int j = 0; j < this.dataManager.getTop12andOthersGenericData().length; j++){
					if(RSSID == this.dataManager.getTop12andOthersGenericData()[j][0]){
						RBCT1 = this.dataManager.getTop12andOthersGenericData()[j][8];
					}
				}//end cash balance for-loop
				
				for(int j = 0; j < this.dataManager.getTop12andOthersGenericData().length; j++){
					if(RSSID == this.dataManager.getTop12andOthersGenericData()[j][0]){
						SZLNRES = this.dataManager.getTop12andOthersGenericData()[j][10];
					}
				}//end cash balance for-loop
				
				for(int j = 0; j < this.dataManager.getTop12andOthersGenericData().length; j++){
					if(RSSID == this.dataManager.getTop12andOthersGenericData()[j][0]){
						SZIORESTOT = this.dataManager.getTop12andOthersGenericData()[j][11];
					}
				}//end cash balance for-loop
				
				for(int j = 0; j < this.dataManager.getTop12andOthersGenericData().length; j++){
					if(RSSID == this.dataManager.getTop12andOthersGenericData()[j][0]){
						SZIORESHETOT = this.dataManager.getTop12andOthersGenericData()[j][12];
					}
				}//end cash balance for-loop
				

				// create a new bank object
				if (this.params.isRegulatoryRegime() == true) {
					bank = new SABank(RSSID, BANKNAME, CHBAL, ASSET, LIAB, EQ,
							SCMTGBK, CTDDFSWG, CTDDFSWB, RBCT1, SZLNRES,
							SZIORESTOT, SZIORESHETOT, this.params.getVolatilityThreshold(),
							this.enviroment.counterpartyRiskWeight, this.params.isRegulatoryRegime(),
							this.params.isModelRMBSDataType(), this.enviroment, this.economy);
				} else {
					bank = new SABank(RSSID, BANKNAME, CHBAL, ASSET, LIAB, EQ,
							SCMTGBK, CTDDFSWG, CTDDFSWB, RBCT1, SZLNRES,
							SZIORESTOT, SZIORESHETOT, this.params.getVolatilityThreshold(),
							this.enviroment.counterpartyRiskW, this.params.isRegulatoryRegime(),
							this.params.isModelRMBSDataType(), this.enviroment, this.economy);
				}
				// add new bank object to bank list
				this.bankList.add(bank);
			}// end for
				// loopFile(System.getProperty("user.home")+"/totalSubprimeExposures.pdf"
		}// end if

	}


	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<JAS SIMULATION REQUIRED METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	@Override
	public void buildModel() {
		// TODO Auto-generated method stub
		this.params = this.frame.getModelParameters();
//		System.out.println(params.toString());
		setModelDefaultParameters(params);
		buildDataManagement(params.isDefaultModel(), params.isModelRMBSDataType(), params.getModelBanksSubset());
		buildEconomy(); 
		buildEnviroment();
		buildAgents();
//		System.out.println(this.bankList.size());
		buildDataExport();
		buildActions();

	}
	
	
	public void buildActions(){
		eventList.scheduleSimple(0, 1, this, "time0");
		eventList.scheduleSimple(1, 1, this,"injectMissingData");
//		eventList.scheduleSystem(this.maxRun, Sim.EVENT_SIMULATION_END);
		eventList.scheduleCollection(0, 1, bankList, getObjectClass("BaselIIBank"),"basisTradeTimeStep");
		 if (this.frame != null){
			 eventList.scheduleSimple(0, 1, this.frame, "updatedModelSimulationState");
		 }
		eventList.scheduleSimple(0, 1, this.textDataExporter, "PrintToTextFile");
		eventList.scheduleSystem(this.maxRun, Sim.EVENT_SIMULATION_END);
	}
	
	private void buildDataExport(){
		this.textDataExporter = new CDSBasisTradeSimResultsOutput(this);
	}





	@Override
	public void setParameters() {
		Sim.openProbe(this.params, "ModelParameters");		
	}
	
	
	public void setModelDefaultParameters(ModelParameters param) {
		// TODO Auto-generated method stub
		this.maxRun = (param.getNumberOfSimulationQuarters()*3);
	}

	
	
	
	public void time0(){
		/**
		 * This method accounts for any actions that need to be taken by any aspect of the simulation at time t = 0
		 * 
		 * For some models it is required for others it is not. Use at your discretion
		 */
		this.time = Sim.getAbsoluteTime();

	}
	
	public long getSimTime(){
		return this.time;
	}



	public marketDataManager getdDtaManager(){
		return this.dataManager;
	}

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public void runTimeStep(){
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MODEL TIME STEP OR EVENT SCHDUALER>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //This is the primary method call of the model
        //run the model for the prescribed number of time steps and print output to a text file and execute the time step method to
        //run the model.

    }// end runTimeStep
    
    
    
    public void injectMissingData(){
    	/**
    	 * This method is used to scroll through the data at the begining of each time period and dertermine if a 
    	 * bank has recently joined the CDS issuance market.
    	 * 
    	 * If so then, the method picks up the new data and applies it to the bank as an injection
    	 */

    	for (int t = 0; t < this.dataManager.getCashSpreadsList().size()*3; t++){
    		this.leverageLIBORDataKickIn = 1+((this.enviroment.getLeverageStartYear() - 2002)*4)
    				+ this.enviroment.getLeverageStartQuarter();
    		long time = (long) t;
    		for(int k = 0; k < this.getBankList().size(); k++){

    			this.missingBankDataKickIn = 1+((this.enviroment.getStartYear() - 2002)*4) + (t/3);

    			if(this.getBankList().get(k).getTotalSubprimeRMBSExposure()==0 && t != 0){
    				if(this.enviroment.isAllRMBSBoolean() == true){
    					//runs subprime exposure = 0 check for the ALL RMBS data model
    					for(int id = 0; id < this.dataManager.allBanksTotalRMBSData.length; id++){
    						if(this.getBankList().get(k).getRSSID() == 
    								this.dataManager.allBanksTotalRMBSData[id][0]){
    							if(t % 3 == 0){
    								System.out.println(this.getBankList().get(k).getBankName()
    										+" " + this.getBankList().get(k).getRSSID());
    							}
    							this.getBankList().get(k)
    							.setSubprimeRMBSAssets(this.dataManager.allBanksTotalRMBSData[id][missingBankDataKickIn]);
    							if(t % 3 == 0){
    								System.out.println(this.getBankList().get(k).getSubprimeRMBSAssets());
    							}
    						}//end if
    					}//end for
    				}else{//runs subprime exposure = 0 check for the structured RMBS data model
    					for(int id = 0; id < this.dataManager.allBanksStructuredRMBSData.length; id++){
    						if(this.getBankList().get(k).getRSSID() == 
    								this.dataManager.allBanksStructuredRMBSData[id][0]){
    							if(t % 3 == 0){
    								System.out.println(this.getBankList().get(k).getBankName()
    										+" " + this.getBankList().get(k).getRSSID());
    							}

    							this.getBankList().get(k)
    							.setSubprimeRMBSAssets(this.dataManager.allBanksStructuredRMBSData[id][missingBankDataKickIn]);
    							if(t % 3 == 0){
    								System.out.println(this.getBankList().get(k).getSubprimeRMBSAssets());
    							}


    						}//end if
    					}//end for

    				}//end of else
    			}//end of main if-statement for the subprime exposure = 0 check


    			/**
    			 * This section adds the injections in for Citibank and other banks
    			 */

    			if(this.enviroment.getStartYear()== 2006 && t != 0){
    				if(this.enviroment.isAllRMBSBoolean() == true){

    					for(int id = 0; id < this.dataManager.allBanksTotalRMBSData.length; id++){
    						if(this.getBankList().get(k).getRSSID() == 
    								this.dataManager.allBanksTotalRMBSData[id][0] && 
    								this.getBankList().get(k).getRSSID() ==
    								476810){
    							double injection = 0;
    							if(t % 3 == 0 && this.missingBankDataKickIn == 20){
    								injection = this.dataManager.allBanksTotalRMBSData[id][missingBankDataKickIn] -
    										this.dataManager.allBanksTotalRMBSData[id][missingBankDataKickIn - 1];

    							}
    							this.getBankList().get(k).addCorporateRestructuringInjections(injection);
    						}//end if
    					}//end for
    				}
    			}//end if

    			if(this.enviroment.getStartYear()== 2006 && t != 0){
    				if(this.enviroment.isAllRMBSBoolean() == true){

    					for(int id = 0; id < this.dataManager.allBanksTotalRMBSData.length; id++){
    						if(this.getBankList().get(k).getRSSID() == 
    								this.dataManager.allBanksTotalRMBSData[id][0] && 
    								this.getBankList().get(k).getRSSID() == 9999999){
    							double injection = 0;
    							if(t % 3 == 0 && this.missingBankDataKickIn == 19){
    								System.out.println(this.getBankList().get(k).getBankName()+": "
    										+ this.getBankList().get(k).getSubprimeRMBSAssets());
    								injection = 4265344;
    								//injection = 4265344;

    							}
    							this.getBankList().get(k).addCorporateRestructuringInjections(injection);

    							if(t % 3 == 0 && this.missingBankDataKickIn == 20){
    								System.out.println(this.getBankList().get(k).getBankName()+": "
    										+ this.getBankList().get(k).getSubprimeRMBSAssets());
    								injection = 838848;

    							}
    							this.getBankList().get(k).addCorporateRestructuringInjections(injection);

    						}//end if
    					}//end for
    				}
    			}//end if
    		}
    	}
    }//end of data injection check method
  
    private void runAccessTest(){
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<ACCESS TEST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //banks can be accessed from the bank list without any problems all banks with values 0.0 are due to
        //missing data in the database
        System.out.println("This CDS-basis trading model consists of " + this.dataManager.getBankList().size() + " banks");
        double tsum = 0;
        double tsum2 = 0;
        double tsum3 = 0;
        for(int i = 0; i < this.dataManager.getBankList().size(); i++){
            tsum += this.dataManager.getBankList().get(i).getSubprimeRMBSAssets();
            tsum2 += this.dataManager.getBankList().get(i).getTotalSubprimeRMBSExposure();
            if(this.dataManager.getBankList().get(i).getCdsProtectionBought() > 0){
                tsum3 += this.dataManager.getBankList().get(i).getSubprimeRMBSAssets();
            }
            System.out.println("Bank "+ this.dataManager.getBankList().get(i).getRSSID() + " has total assets of: " +
                    this.dataManager.getBankList().get(i).getSubprimeRMBSAssets());
        }
        System.out.println("Total RMBS ASSETS " + tsum);
        System.out.println("Total RMBS Exposures " + tsum3);
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    }






    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public ArrayList<BaselIIBank> getBankList(){
        return this.bankList;
    }
    
    public boolean getCRM(){
    	return this.enviroment.getCRM();
    }
    
    public boolean getBasel(){
    	return this.enviroment.getBasel();
    }
    
    public double getVThresold(){
    	return this.enviroment.getVolThreshold();
    }
    
    public int getStrategy(){
    	return this.enviroment.getMaximumEarningMethod();
    }



    



}
