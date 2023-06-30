import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;


public class CDSBasisTradeSimResultsOutput {
	
	
	CDSBasisTradeModel Simmodel;
	
	PrintStream MyOutput = null;

	
	public CDSBasisTradeSimResultsOutput(CDSBasisTradeModel model){
		this.Simmodel = model;
        createOutPutFile();

	}
	
	
	
	
	public void PrintToTextFile(){
        for(int i = 0; i < this.Simmodel.getBankList().size(); i++){// this is the individual time step data
            //this println statment writes the model generated data to file
            MyOutput.println("Bank "+ this.Simmodel.getBankList().get(i).getRSSID()
                    + " has_total_subprime_Exposures_of: " + this.Simmodel.getBankList().get(i).getTotalSubprimeRMBSExposure()
                    + " has_total_wealth_allocation_to_subprime_assets_of: " 
                    + this.Simmodel.getBankList().get(i).getSubprimeRMBSAssets()
                    + " Net_capital_savings_of: " + this.Simmodel.getBankList().get(i).getBasisTradeCapitalSaving()
                    + " Gross_capital_savings_of: " + this.Simmodel.getBankList().get(i).getBasisTradeTrancheSavings()
                    + " Basis_Trade_Income: " + this.Simmodel.getBankList().get(i).getCurrentQuaterBasisIncome()
                    + " Total_Basis_Trade_Income: " + this.Simmodel.getBankList().get(i).getTotalAccumulatedBasisIncome()
                    + " Unwind_Income: " + this.Simmodel.getBankList().get(i).getUnwindIncome()
                    + " Notional_Unwind: " + this.Simmodel.getBankList().get(i).getUnwindNotional()
                    + " has_Teir_1_Capital_of: " + this.Simmodel.getBankList().get(i).getTeir1CapitalAllocation()
                    + " has_Mez_Tranche_Capital_Savings_of: " + this.Simmodel.getBankList().get(i).getMezzinineTrancheCapitalSaving()
                    + " has_Mez_Total_Tranche_Exposures of: " + this.Simmodel.getBankList().get(i).getTotalMezzanineTrancheExposure()
                    + " has_current_Qtr_Mez_Tranche_Exposures of: " + this.Simmodel.getBankList().get(i).getCurrentQtrMezzanineTrancheExposure()
                    + " at_time: " + this.Simmodel.getSimTime());
        }// end of for individual time step data collection for-loop


	}
	
	

    private void createOutPutFile(){
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MODEL OUTPUT DATA COLLECTION SETUP>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	try {
            MyOutput = new PrintStream(new FileOutputStream(System.getProperty("user.home")+"/bankBasisTradeOutputExp02capsav.txt"));
            //MyOutput = new PrintStream(new FileOutputStream("jfreeChartSeriesTest.txt"));
        }
        catch (IOException e)
        {
            System.out.println("OOps");
        }
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    }


    

}
