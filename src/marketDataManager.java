/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Quarter;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * 
 * @author segun
 */
public class marketDataManager {

	private int coreBanks;
	private boolean ModelRMBSType;

	public static double allBanksCashBalances;
	public static double allBanksAssets;
	public static double allBanksLiabilities;
	public static double allBanksEquity;
	public static double allBanksSCMTGBK;
	public static double allBanksCTDDFSWG;
	public static double allBanksCTDDFSWB;
	public static double allBanksRBCT1;
	public static double allBanksSZLNRES;
	public static double allBanksSZIORESTOT;
	public static double allBanksSZIORESHETOT;
	
	
	public static double remainingBanksCashBalances;
	public static double remainingBanksAssets;
	public static double remainingBanksLiabilities;
	public static double remainingBanksEquity;
	public static double remainingBanksSCMTGBK;
	public static double remainingBanksCTDDFSWG;
	public static double remainingBanksCTDDFSWB;
	public static double remainingBanksRBCT1;
	public static double remainingBanksSZLNRES;
	public static double remainingBanksSZIORESTOT;
	public static double remainingBanksSZIORESHETOT;
	
	
	public static double listedBanksCashBalances;
	public static double listedBanksAssets;
	public static double listedBanksLiabilities;
	public static double listedBanksEquity;
	public static double listedBanksSCMTGBK;
	public static double listedBanksCTDDFSWG;
	public static double listedBanksCTDDFSWB;
	public static double listedBanksRBCT1;
	public static double listedBanksSZLNRES;
	public static double listedBanksSZIORESTOT;
	public static double listedBanksSZIORESHETOT;
	
	public String[] bankRSSIDList;
	public String[] dataTimeSereisTimeSpan;
	public String[][] bankNamesList;
	public double[][] bankData;

	public double[][] top12andOthersTotalRMBSData;
	public double[][] top12andOthersStructuredRMBSData;
	public double[][] allBanksTotalRMBSData;
	public double[][] allBanksStructuredRMBSData;
	public double[][] AllBanksBalancesheetCashData;
	public double[][] AllBanksCDSBuyData;
	public double[][] AllBanksCDSSellData;
	public double[][] AllBanksTotalAssetsData;

	public ArrayList<BaselIIBank> bankList;
	public ArrayList<Double> AggregateHoldingArrayList;
	public ArrayList<double[][]> cdsSpreadsList;
	public double[][] ds1;
	public double[][] ds2;
	public double[][] ds3;
	public double[][] ds4;
	public double[][] ds5;
	public double[][] ds6;
	public double[][] ds7;
	public ArrayList<double[]> cashSpreadsList;
	public double[] cs1;
	public double[] cs2;
	public double[] cs3;
	public double[] cs4;
	public double[] cs5;
	public double[] cs6;
	public double[] cs7;
	public double[] libor;
	public double[] liborRates2002Q1to2010Q2;
	public static double[] defaultLIBORBorrowingRates = {8.39,	8.91,	8.77,	8.76,	8.87,	8.76,	9.02};

	public SABank bank;
	TimeSeries aggregatedActualExposuresSeries;
	TimeSeries actualFDICBankOfAmericaAllMBSExposuresSeries;
	TimeSeries actualFDICBankOfAmericaStructuredMBSExposuresSeries;
	TimeSeries actualFDICJPMorganAllMBSExposuresSeries;
	TimeSeries actualFDICJPMorganStructuredMBSExposuresSeries;
	TimeSeries actualFDICCitiBankMBSExposuresSeries;
	TimeSeries actualFDICMerrilLynchMBSExposuresSeries;
	private TimeSeries actualFDICAllOtherBanksMBSExposuresSeries;
	private TimeSeries actualFDICHSBCMBSExposuresSeries;
	private TimeSeries actualFDICKeyBankMBSExposuresSeries;

	TimeSeries aggregatedActualWithouCitiBankExposuresSeries;

	TimeSeries ABX_HE_AAA_Spread = new TimeSeries("ABX.HE AAA ");
	TimeSeries ABX_HE_AA_Spread = new TimeSeries("ABX.HE AA ");
	TimeSeries ABX_HE_A_Spread = new TimeSeries("ABX.HE A ");
	TimeSeries ABX_HE_BBB_Spread = new TimeSeries("ABX.HE BBB ");
	TimeSeries ABX_HE_BBB3_Spread = new TimeSeries("ABX.HE BBB- ");

	TimeSeries RMBS_CDO_AAA_Spread = new TimeSeries("RMBS_CDO AAA ");
	TimeSeries RMBS_CDO_AA_Spread = new TimeSeries("RMBS_CDO AA ");
	TimeSeries RMBS_CDO_A_Spread = new TimeSeries("RMBS_CDO A ");
	TimeSeries RMBS_CDO_BBB_Spread = new TimeSeries("RMBS_CDO BBB ");
	TimeSeries RMBS_CDO_BBB3_Spread = new TimeSeries("RMBS_CDO BBB- ");

	TimeSeries CDS_BASIS_AAA_Spread = new TimeSeries("CDS Basis AAA ");
	TimeSeries CDS_BASIS_AA_Spread = new TimeSeries("CDS Basis AA ");
	TimeSeries CDS_BASIS_A_Spread = new TimeSeries("CDS Basis A ");
	TimeSeries CDS_BASIS_BBB_Spread = new TimeSeries("CDS Basis BBB ");
	TimeSeries CDS_BASIS_BBB3_Spread = new TimeSeries("CDS Basis BBB- ");

	TimeSeries ABX_HE_AAA_Price = new TimeSeries("ABX.HE AAA");
	TimeSeries ABX_HE_AA_Price = new TimeSeries("ABX.HE AA");
	TimeSeries ABX_HE_A_Price = new TimeSeries("ABX.HE A");
	TimeSeries ABX_HE_BBB_Price = new TimeSeries("ABX.HE BBB");
	TimeSeries ABX_HE_BBB3_Price = new TimeSeries("ABX.HE BBB-");

	TimeSeries ABX_HE_AAA_Discount = new TimeSeries("ABX.HE AAA");
	TimeSeries ABX_HE_AA_Discount = new TimeSeries("ABX.HE AA");
	TimeSeries ABX_HE_A_Discount = new TimeSeries("ABX.HE A");
	TimeSeries ABX_HE_BBB_Discount = new TimeSeries("ABX.HE BBB");
	TimeSeries ABX_HE_BBB3_Discount = new TimeSeries("ABX.HE BBB-");

	TimeSeries ABX_HE_AAA_Coupon = new TimeSeries("ABX.HE AAA");
	TimeSeries ABX_HE_AA_Coupon = new TimeSeries("ABX.HE AA");
	TimeSeries ABX_HE_A_Coupon = new TimeSeries("ABX.HE A");
	TimeSeries ABX_HE_BBB_Coupon = new TimeSeries("ABX.HE BBB");
	TimeSeries ABX_HE_BBB3_Coupon = new TimeSeries("ABX.HE BBB-");

	TimeSeriesCollection cdsAndBondSpreads = new TimeSeriesCollection();
	TimeSeriesCollection cdsBasis = new TimeSeriesCollection();
	TimeSeriesCollection cdsPrices = new TimeSeriesCollection();
	TimeSeriesCollection cdsTradingDiscount = new TimeSeriesCollection();
	private double[][] top12andOthersCDSProtectionBoughtData;
	private double[][] top12andOthersCDSProtectionSoldData;
	private double[][] top12andOthersOutstandingNotionalSecuritisedData;
	private double[][] bankTop12andOthersGenericData;

	
	public marketDataManager(){
		
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND
	// SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public ArrayList<BaselIIBank> getBanks(boolean defaultModel, boolean modelTypeRMBS,
			int coreBanks, marketEnviromentConfigurator mEC, Economy econ){
		
		if (defaultModel == true) {
			for (int i = 0; i < bankRSSIDList.length; i++) {
				// get bank definition values from database
				int RSSID = (int) bankData[i][0];
				String BANKNAME = null;
				if (bankRSSIDList[i] == bankNamesList[i][0]) {
					BANKNAME = bankNamesList[i][1];

				}
				double CHBAL = bankData[i][1];
				double ASSET = bankData[i][2];
				double LIAB = bankData[i][3];
				double EQ = bankData[i][4];
				double SCMTGBK = bankData[i][5];
				double CTDDFSWG = bankData[i][6];
				double CTDDFSWB = bankData[i][7];
				double RBCT1 = bankData[i][8];
				// double RBCT2T = bankData[i][9];
				double SZLNRES = bankData[i][10];
				double SZIORESTOT = bankData[i][11];
				double SZIORESHETOT = bankData[i][12];

				// create a new bank object
				if (mEC.baselII == true) {
					bank = new SABank(RSSID, BANKNAME, CHBAL, ASSET, LIAB, EQ,
							SCMTGBK, CTDDFSWG, CTDDFSWB, RBCT1, SZLNRES,
							SZIORESTOT, SZIORESHETOT, mEC.vThreshold,
							mEC.counterpartyRiskWeight, mEC.baselII,
							modelTypeRMBS, mEC, econ);
				} else {
					bank = new SABank(RSSID, BANKNAME, CHBAL, ASSET, LIAB, EQ,
							SCMTGBK, CTDDFSWG, CTDDFSWB, RBCT1, SZLNRES,
							SZIORESTOT, SZIORESHETOT, mEC.vThreshold,
							mEC.counterpartyRiskW, mEC.baselII, modelTypeRMBS,  mEC, econ);
				}
				// add new bank object to bank list
				this.bankList.add(bank);
			}// end for
				// loopFile(System.getProperty("user.home")+"/totalSubprimeExposures.pdf"
		}// end if

		return this.bankList;
	}
	
	public boolean getModelRMBSType() {
		// TODO Auto-generated method stub
		return this.ModelRMBSType;
	}

	public int getCoreBanks() {
		// TODO Auto-generated method stub
		return this.coreBanks;
	}
	
	public void setCoreBanksIDsAndNames(int corebnks){
		switch (corebnks) {
		case 0:
			this.bankRSSIDList = new String[] { "214807", "280110",
					"413208", "451965", "476810", "480228", "484422",
					"541101", "852218", "2182786", "1225800", "1456501",
					"9999999" };


			this.bankNamesList = new String[][] {

					{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
					{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
					{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
					{ "451965", "WELLS FARGO BANK NATIONAL ASSOCIATION" },
					{ "476810", "CITIBANK N.A." },
					{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
					{ "484422", "WACHOVIA BANK NATIONAL ASSOCIATION" },
					{ "541101", "BANK OF NEW YORK THE" },
					{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
					{ "2182786", "GOLDMAN SACHS BANK USA" },
					{ "1225800", "MERRILL LYNCH BANK USA" },
					{ "1456501", "MORGAN STANLEY BANK" },
					{ "9999999", "All Non-Top 12 Banks" } };
			break;
		case 1:
		
		this.bankRSSIDList = new String[] { "214807", "280110",
					"413208", "451965", "476810", "480228", "484422",
					"541101", "852218", "2182786", "1225800", "1456501",
					"9999999" };


			this.bankNamesList = new String[][] {

					{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
					{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
					{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
					{ "451965", "WELLS FARGO BANK NATIONAL ASSOCIATION" },
					{ "476810", "CITIBANK N.A." },
					{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
					{ "484422", "WACHOVIA BANK NATIONAL ASSOCIATION" },
					{ "541101", "BANK OF NEW YORK THE" },
					{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
					{ "2182786", "GOLDMAN SACHS BANK USA" },
					{ "1225800", "MERRILL LYNCH BANK USA" },
					{ "1456501", "MORGAN STANLEY BANK" },
					{ "9999999", "All Non-Top 12 Banks" } };
			break;

		case 2:
			
			this.bankRSSIDList = new String[] { "917742", "480228",
					"541101", "968605", "476810", "60143", "601050",
					"214807", "2182786", "413208", "852218", "280110",
					"83638", "1225800", "1456501", "259518", "210434",
					"817824", "3303298", "233031", "2942690", "35301",
					"675332", "504713", "484422", "451965" };

			this.bankNamesList = new String[][] {
					{ "917742", "ASSOCIATED BANK, NATIONAL ASSOCIATION" },
					{ "480228", "BANK OF AMERICA, NATIONAL ASSOCIATION" },
					{ "541101", "BANK OF NEW YORK MELLON, THE" },
					{ "968605",
							"BANK OF TOKYO-MITSUBISHI UFJ TRUST COMPANY" },
					{ "476810", "CITIBANK, N.A." },
					{ "60143", "COMERICA BANK" },
					{ "601050", "COMMERCE BANK, NATIONAL ASSOCIATION" },
					{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
					{ "2182786", "GOLDMAN SACHS BANK USA" },
					{ "413208", "HSBC BANK USA, NATIONAL ASSOCIATION" },
					{ "852218", "JPMORGAN CHASE BANK, NATIONAL ASSOCIATION" },
					{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
					{ "83638",
							"MERCANTIL COMMERCEBANK NATIONAL ASSOCIATION" },
					{ "1225800", "MERRILL LYNCH BANK USA" },
					{ "1456501",
							"MORGAN STANLEY BANK, NATIONAL ASSOCIATION" },
					{ "259518", "NATIONAL CITY BANK" },
					{ "210434", "NORTHERN TRUST COMPANY, THE" },
					{ "817824", "PNC BANK, NATIONAL ASSOCIATION" },
					{ "3303298", "RBS CITIZENS, NATIONAL ASSOCIATION" },
					{ "233031", "REGIONS BANK" },
					{ "2942690", "SIGNATURE BANK" },
					{ "35301", "STATE STREET BANK AND TRUST COMPANY" },
					{ "675332", "SUNTRUST BANK" },
					{ "504713", "U.S. BANK NATIONAL ASSOCIATION" },
					{ "484422", "WACHOVIA BANK, NATIONAL ASSOCIATION" },
					{ "451965", "WELLS FARGO BANK, NATIONAL ASSOCIATION" } };
			break;

		case 3:

							
			this.bankRSSIDList = new String[] { "14409", "35301", "60143",
					"83638", "101019", "210434", "214807", "233031",
					"259518", "280110", "413208", "451965", "455534",
					"476810", "480228", "484422", "504713", "541101",
					"675332", "817824", "852218", "934329", "968605",
					"3041974", "3303298", "62101", "917742", "2942690",
					"2182786", "601050", "1225800", "1456501" };

			this.bankNamesList = new String[][] {
					{ "14409", "CITIZENS BANK OF MASSACHUSETTS" },
					{ "35301", "STATE STREET BANK AND TRUST COMPANY" },
					{ "60143", "COMERICA BANK" },
					{ "83638", "COMMERCEBANK NATIONAL ASSOCIATION" },
					{ "101019", "BANK LEUMI USA" },
					{ "210434", "NORTHERN TRUST COMPANY THE" },
					{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
					{ "233031", "REGIONS BANK" },
					{ "259518", "NATIONAL CITY BANK" },
					{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
					{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
					{ "451965", "WELLS FARGO BANK NATIONAL ASSOCIATION" },
					{ "455534", "LASALLE BANK NATIONAL ASSOCIATION" },
					{ "476810", "CITIBANK N.A." },
					{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
					{ "484422", "WACHOVIA BANK NATIONAL ASSOCIATION" },
					{ "504713", "U.S. BANK NATIONAL ASSOCIATION" },
					{ "541101", "BANK OF NEW YORK THE" },
					{ "675332", "SUNTRUST BANK" },
					{ "817824", "PNC BANK NATIONAL ASSOCIATION" },
					{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
					{ "934329", "MELLON BANK N.A." },
					{ "968605",
							"BANK OF TOKYO-MITSUBISHI UFJ TRUST COMPANY" },
					{ "3041974", "CITIZENS BANK OF PENNSYLVANIA" },
					{ "3303298",
							"CITIZENS BANK NATIONAL ASSOCIATION ALBANY" },
					{ "62101", "CITIZENS BANK NEW HAMPSHIRE" },
					{ "917742", "ASSOCIATED BANK, NATIONAL ASSOCIATION" },
					{ "2942690", "SIGNATURE BANK" },
					{ "2182786", "GOLDMAN SACHS BANK USA" },
					{ "601050",
							"COMMERCE BANK NATIONAL ASSOCIATION KANSAS CITY" },
					{ "1225800", "MERRILL LYNCH BANK USA" },
					{ "1456501", "MORGAN STANLEY BANK" } };
			break;


		case 4:
			this.bankRSSIDList = new String[] { "413208", "476810",
					"480228", "852218", "2182786" };

			this.bankNamesList = new String[][] {

					{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
					{ "476810", "CITIBANK N.A." },
					{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
					{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
					{ "2182786", "GOLDMAN SACHS BANK USA" } };
			break;

		case 5:
			this.bankRSSIDList = new String[] { "917742", "480228",
					"541101", "968605", "476810", "60143", "601050",
					"214807", "2182786", "413208", "852218", "280110",
					"83638", "1225800", "1456501", "259518", "210434",
					"817824", "3303298", "233031", "2942690", "35301",
					"675332", "504713", "484422", "451965" };

			this.bankNamesList = new String[][] {
					{ "917742", "ASSOCIATED BANK, NATIONAL ASSOCIATION" },
					{ "480228", "BANK OF AMERICA, NATIONAL ASSOCIATION" },
					{ "541101", "BANK OF NEW YORK MELLON, THE" },
					{ "968605",
							"BANK OF TOKYO-MITSUBISHI UFJ TRUST COMPANY" },
					{ "476810", "CITIBANK, N.A." },
					{ "60143", "COMERICA BANK" },
					{ "601050", "COMMERCE BANK, NATIONAL ASSOCIATION" },
					{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
					{ "2182786", "GOLDMAN SACHS BANK USA" },
					{ "413208", "HSBC BANK USA, NATIONAL ASSOCIATION" },
					{ "852218", "JPMORGAN CHASE BANK, NATIONAL ASSOCIATION" },
					{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
					{ "83638",
							"MERCANTIL COMMERCEBANK NATIONAL ASSOCIATION" },
					{ "1225800", "MERRILL LYNCH BANK USA" },
					{ "1456501",
							"MORGAN STANLEY BANK, NATIONAL ASSOCIATION" },
					{ "259518", "NATIONAL CITY BANK" },
					{ "210434", "NORTHERN TRUST COMPANY, THE" },
					{ "817824", "PNC BANK, NATIONAL ASSOCIATION" },
					{ "3303298", "RBS CITIZENS, NATIONAL ASSOCIATION" },
					{ "233031", "REGIONS BANK" },
					{ "2942690", "SIGNATURE BANK" },
					{ "35301", "STATE STREET BANK AND TRUST COMPANY" },
					{ "675332", "SUNTRUST BANK" },
					{ "504713", "U.S. BANK NATIONAL ASSOCIATION" },
					{ "484422", "WACHOVIA BANK, NATIONAL ASSOCIATION" },
					{ "451965", "WELLS FARGO BANK, NATIONAL ASSOCIATION" } };

			break;
			
			case 6:
			this.bankRSSIDList = new String[] { "14409", "35301", "60143",
					"83638", "101019", "210434", "214807", "233031",
					"259518", "280110", "413208", "451965", "455534",
					"476810", "480228", "484422", "504713", "541101",
					"675332", "817824", "852218", "934329", "968605",
					"3041974", "3303298", "62101", "917742", "2942690",
					"2182786", "601050", "1225800", "1456501" };

			this.bankNamesList = new String[][] {
					{ "14409", "CITIZENS BANK OF MASSACHUSETTS" },
					{ "35301", "STATE STREET BANK AND TRUST COMPANY" },
					{ "60143", "COMERICA BANK" },
					{ "83638", "COMMERCEBANK NATIONAL ASSOCIATION" },
					{ "101019", "BANK LEUMI USA" },
					{ "210434", "NORTHERN TRUST COMPANY THE" },
					{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
					{ "233031", "REGIONS BANK" },
					{ "259518", "NATIONAL CITY BANK" },
					{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
					{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
					{ "451965", "WELLS FARGO BANK NATIONAL ASSOCIATION" },
					{ "455534", "LASALLE BANK NATIONAL ASSOCIATION" },
					{ "476810", "CITIBANK N.A." },
					{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
					{ "484422", "WACHOVIA BANK NATIONAL ASSOCIATION" },
					{ "504713", "U.S. BANK NATIONAL ASSOCIATION" },
					{ "541101", "BANK OF NEW YORK THE" },
					{ "675332", "SUNTRUST BANK" },
					{ "817824", "PNC BANK NATIONAL ASSOCIATION" },
					{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
					{ "934329", "MELLON BANK N.A." },
					{ "968605",
							"BANK OF TOKYO-MITSUBISHI UFJ TRUST COMPANY" },
					{ "3041974", "CITIZENS BANK OF PENNSYLVANIA" },
					{ "3303298",
							"CITIZENS BANK NATIONAL ASSOCIATION ALBANY" },
					{ "62101", "CITIZENS BANK NEW HAMPSHIRE" },
					{ "917742", "ASSOCIATED BANK, NATIONAL ASSOCIATION" },
					{ "2942690", "SIGNATURE BANK" },
					{ "2182786", "GOLDMAN SACHS BANK USA" },
					{ "601050",
							"COMMERCE BANK NATIONAL ASSOCIATION KANSAS CITY" },
					{ "1225800", "MERRILL LYNCH BANK USA" },
					{ "1456501", "MORGAN STANLEY BANK" } };
			break;
		default:
			System.out.println("Invalid Selection");
			break;
		}
	}

	public void setData(boolean defaultModel, boolean modelTypeRMBS,
			int coreBanks) {
		this.setModelRMBSType(modelTypeRMBS);
		this.setCoreBanks(coreBanks);
		this.setAllBanksDatabaseTables();
		if (defaultModel == true && modelTypeRMBS == true) {
			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<DATA AND MODEL AGENT
			// COLLECTION
			// PARAMETERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			switch (coreBanks) {
			case 0:
				this.bankRSSIDList = new String[] { "214807", "280110",
						"413208", "451965", "476810", "480228", "484422",
						"541101", "852218", "2182786", "1225800", "1456501",
						"9999999" };

				this.bankData = new double[][] {
						// Available for sale and held to maturity RMBS assets
						// at fair value
						{ 214807, 2986000, 36034000, 27687000, 7905000, 
							3000,
								0, 0, 7533000, 358000, 0, 0, 0 },
						{ 280110, 2861277, 88877085, 80789687, 6964810,
								6625566, 3622206, 3703706, 6797470, 4325163, 0,
								0, 27770 },
						{ 413208, 10065459, 158753889, 146651393, 12102159,
								16645508, 265390525, 214810750, 10035380,
								4716491, 4590191, 8685, 24429 },
						{ 451965, 15300000, 415802000, 379187000, 36565000,
								39516000, 1681000, 1768000, 25820000, 9525000,
								164279000, 0, 0 },
						{ 476810, 39859000, 749335000, 690787000, 58036000,
								6089000, 415098000, 478678000, 46480000,
								23065000, 418000, 4000, 1707000 },
						{ 480228, 37948038, 1104944125, 1000820651, 102443322,
								211482264, 399549522, 373092267, 70978395,
								16046636, 66467765, 447221, 4889324 },
						{ 484422, 13908000, 496566000, 443180000, 51585000,
								91602000, 92544000, 120479000, 31712000,
								14597000, 3313000, 3895000, 390000 },
						{ 541101, 9751000, 87750000, 79046000, 8553000,
								22602000, 179000, 1023000, 6243000, 2002000, 0,
								0, 0 },
						{ 852218, 46779000, 1093394000, 1004043000, 87395000,
								40267000, 1435005000, 1437836000, 62001000,
								23227000, 32991000, 620000, 3649000 },
						{ 2182786, 2106, 21985, 897, 21088, 0, 0, 0, 21140, 0,
								0, 0, 0 },
						{ 1225800, 433039, 62040397, 56281842, 5747595,
								9281514, 0, 5517878, 5769485, 657394, 8215, 0,
								60000 },
						{ 1456501, 21498, 10844942, 9152735, 1692207, 13099, 0,
								4091773, 1692207, 601191, 0, 0, 0 },
						{ 9999999, 101651062, 2002105927, 1798632805,
								196572553, 148267802, 3371914, 4091833,
								137468718, 49596687, 149653824, 2294844,
								3958125 } };

				this.bankNamesList = new String[][] {

						{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
						{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
						{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
						{ "451965", "WELLS FARGO BANK NATIONAL ASSOCIATION" },
						{ "476810", "CITIBANK N.A." },
						{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
						{ "484422", "WACHOVIA BANK NATIONAL ASSOCIATION" },
						{ "541101", "BANK OF NEW YORK THE" },
						{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
						{ "2182786", "GOLDMAN SACHS BANK USA" },
						{ "1225800", "MERRILL LYNCH BANK USA" },
						{ "1456501", "MORGAN STANLEY BANK" },
						{ "9999999", "All Non-Top 12 Banks" } };
				break;
			case 1:

				this.bankRSSIDList = new String[] { "214807", "280110",
						"413208", "451965", "476810", "480228", "484422",
						"541101", "852218", "2182786", "1225800", "1456501",
						"9999999" };

				this.bankData = new double[][] {
						// Available for sale and held to maturity RMBS assets
						// at fair value
						{ 214807, 2986000, 36034000, 27687000, 7905000, 3000,
								0, 0, 7533000, 358000, 0, 0, 0 },
						{ 280110, 2861277, 88877085, 80789687, 6964810,
								6625566, 3622206, 3703706, 6797470, 4325163, 0,
								0, 27770 },
						{ 413208, 10065459, 158753889, 146651393, 12102159,
								16645508, 265390525, 214810750, 10035380,
								4716491, 4590191, 8685, 24429 },
						{ 451965, 15300000, 415802000, 379187000, 36565000,
								39516000, 1681000, 1768000, 25820000, 9525000,
								164279000, 0, 0 },
						{ 476810, 39859000, 749335000, 690787000, 58036000,
								6089000, 415098000, 478678000, 46480000,
								23065000, 418000, 4000, 1707000 },
						{ 480228, 37948038, 1104944125, 1000820651, 102443322,
								211482264, 399549522, 373092267, 70978395,
								16046636, 66467765, 447221, 4889324 },
						{ 484422, 13908000, 496566000, 443180000, 51585000,
								91602000, 92544000, 120479000, 31712000,
								14597000, 3313000, 3895000, 390000 },
						{ 541101, 9751000, 87750000, 79046000, 8553000,
								22602000, 179000, 1023000, 6243000, 2002000, 0,
								0, 0 },
						{ 852218, 46779000, 1093394000, 1004043000, 87395000,
								40267000, 1435005000, 1437836000, 62001000,
								23227000, 32991000, 620000, 3649000 },
						{ 2182786, 2106, 21985, 897, 21088, 0, 0, 0, 21140, 0,
								0, 0, 0 },
						{ 1225800, 433039, 62040397, 56281842, 5747595,
								9281514, 0, 5517878, 5769485, 657394, 8215, 0,
								60000 },
						{ 1456501, 21498, 10844942, 9152735, 1692207, 13099, 0,
								4091773, 1692207, 601191, 0, 0, 0 },
						{ 9999999, 101651062, 2002105927, 1798632805,
								196572553, 148267802, 3371914, 4091833,
								137468718, 49596687, 149653824, 2294844,
								3958125 } };

				this.bankNamesList = new String[][] {

						{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
						{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
						{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
						{ "451965", "WELLS FARGO BANK NATIONAL ASSOCIATION" },
						{ "476810", "CITIBANK N.A." },
						{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
						{ "484422", "WACHOVIA BANK NATIONAL ASSOCIATION" },
						{ "541101", "BANK OF NEW YORK THE" },
						{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
						{ "2182786", "GOLDMAN SACHS BANK USA" },
						{ "1225800", "MERRILL LYNCH BANK USA" },
						{ "1456501", "MORGAN STANLEY BANK" },
						{ "9999999", "All Non-Top 12 Banks" } };
				break;
			default:
				System.out.println("Invalid Selection");
				break;
			}

		} else if (defaultModel == true && modelTypeRMBS == false) {

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<DATA AND MODEL AGENT
			// COLLECTION
			// PARAMETERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			switch (coreBanks) {
			case 0:
				this.bankRSSIDList = new String[] { "214807", "280110",
						"413208", "451965", "476810", "480228", "484422",
						"541101", "852218", "2182786", "1225800", "1456501",
						"9999999" };

				this.bankData = new double[][] {
						// Available for sale and held to maturity RMBS assets
						// at fair value
						{ 214807, 2986000, 36034000, 27687000, 7905000, 
							0, 0, 0, 7533000, 358000, 0, 0, 0 },
						{ 280110, 2861277, 88877085, 80789687, 6964810,
								6420130, 3622206, 3703706, 6797470, 4325163, 0,
								0, 27770 },
						{ 413208, 10065459, 158753889, 146651393, 12102159,
								8415458, 265390525, 214810750, 10035380,
								4716491, 4590191, 8685, 24429 },
						{ 451965, 15300000, 415802000, 379187000, 36565000,
								5936000, 1681000, 1768000, 25820000, 9525000,
								164279000, 0, 0 },
						{ 476810, 39859000, 749335000, 690787000, 58036000, 
									0,
								415098000, 478678000, 46480000, 23065000,
								418000, 4000, 1707000 },
						{ 480228, 37948038, 1104944125, 1000820651, 102443322,
								4852353, 399549522, 373092267, 70978395,
								16046636, 66467765, 447221, 4889324 },
						{ 484422, 13908000, 496566000, 443180000, 51585000,
								15730000, 92544000, 120479000, 31712000,
								14597000, 3313000, 3895000, 390000 },
						{ 541101, 9751000, 87750000, 79046000, 8553000,
								19705000, 179000, 1023000, 6243000, 2002000, 0,
								0, 0 },
						{ 852218, 46779000, 1093394000, 1004043000, 87395000,
								165000, 1435005000, 1437836000, 62001000,
								23227000, 32991000, 620000, 3649000 },
						{ 2182786, 2106, 21985, 897, 21088, 0, 0, 0, 21140, 0,
								0, 0, 0 },
						{ 1225800, 433039, 62040397, 56281842, 5747595,
								9280105, 0, 5517878, 5769485, 657394, 8215, 0,
								60000 },
						{ 1456501, 21498, 10844942, 9152735, 1692207, 0, 0,
								4091773, 1692207, 601191, 0, 0, 0 },
						{ 9999999, 101651062, 2002105927, 1798632805,
								196572553, 58188165, 3371914, 4091833,
								137468718, 49596687, 149653824, 2294844,
								3958125 } };

				this.bankNamesList = new String[][] {

						{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
						{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
						{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
						{ "451965", "WELLS FARGO BANK NATIONAL ASSOCIATION" },
						{ "476810", "CITIBANK N.A." },
						{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
						{ "484422", "WACHOVIA BANK NATIONAL ASSOCIATION" },
						{ "541101", "BANK OF NEW YORK THE" },
						{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
						{ "2182786", "GOLDMAN SACHS BANK USA" },
						{ "1225800", "MERRILL LYNCH BANK USA" },
						{ "1456501", "MORGAN STANLEY BANK" },
						{ "9999999", "All Non-Top 12 Banks" } };
				break;
			case 1:
				
				this.bankRSSIDList = new String[] { "214807", "280110",
						"413208", "451965", "476810", "480228", "484422",
						"541101", "852218", "2182786", "1225800", "1456501",
						"9999999" };

				this.bankData = new double[][] {
						// Available for sale and held to maturity RMBS assets
						// at fair value
						{ 214807, 2986000, 36034000, 27687000, 7905000, 0, 0,
								0, 7533000, 358000, 0, 0, 0 },
						{ 280110, 2861277, 88877085, 80789687, 6964810,
								6420130, 3622206, 3703706, 6797470, 4325163, 0,
								0, 27770 },
						{ 413208, 10065459, 158753889, 146651393, 12102159,
								8415458, 265390525, 214810750, 10035380,
								4716491, 4590191, 8685, 24429 },
						{ 451965, 15300000, 415802000, 379187000, 36565000,
								5936000, 1681000, 1768000, 25820000, 9525000,
								164279000, 0, 0 },
						{ 476810, 39859000, 749335000, 690787000, 58036000, 0,
								415098000, 478678000, 46480000, 23065000,
								418000, 4000, 1707000 },
						{ 480228, 37948038, 1104944125, 1000820651, 102443322,
								4852353, 399549522, 373092267, 70978395,
								16046636, 66467765, 447221, 4889324 },
						{ 484422, 13908000, 496566000, 443180000, 51585000,
								15730000, 92544000, 120479000, 31712000,
								14597000, 3313000, 3895000, 390000 },
						{ 541101, 9751000, 87750000, 79046000, 8553000,
								19705000, 179000, 1023000, 6243000, 2002000, 0,
								0, 0 },
						{ 852218, 46779000, 1093394000, 1004043000, 87395000,
								165000, 1435005000, 1437836000, 62001000,
								23227000, 32991000, 620000, 3649000 },
						{ 2182786, 2106, 21985, 897, 21088, 0, 0, 0, 21140, 0,
								0, 0, 0 },
						{ 1225800, 433039, 62040397, 56281842, 5747595,
								9280105, 0, 5517878, 5769485, 657394, 8215, 0,
								60000 },
						{ 1456501, 21498, 10844942, 9152735, 1692207, 0, 0,
								4091773, 1692207, 601191, 0, 0, 0 },
						{ 9999999, 101651062, 2002105927, 1798632805,
								196572553, 58188165, 3371914, 4091833,
								137468718, 49596687, 149653824, 2294844,
								3958125 } };

				this.bankNamesList = new String[][] {

						{ "214807", "DEUTSCHE BANK TRUST COMPANY AMERICAS" },
						{ "280110", "KEYBANK NATIONAL ASSOCIATION" },
						{ "413208", "HSBC BANK USA NATIONAL ASSOCIATION" },
						{ "451965", "WELLS FARGO BANK NATIONAL ASSOCIATION" },
						{ "476810", "CITIBANK N.A." },
						{ "480228", "BANK OF AMERICA NATIONAL ASSOCIATION" },
						{ "484422", "WACHOVIA BANK NATIONAL ASSOCIATION" },
						{ "541101", "BANK OF NEW YORK THE" },
						{ "852218", "JPMORGAN CHASE BANK NATIONAL ASSOCIATION" },
						{ "2182786", "GOLDMAN SACHS BANK USA" },
						{ "1225800", "MERRILL LYNCH BANK USA" },
						{ "1456501", "MORGAN STANLEY BANK" },
						{ "9999999", "All Non-Top 12 Banks" } };
				break;
			default:
				System.out.println("Invalid Selection");
				break;
			}

		} else {
			JOptionPane.showMessageDialog(null, "Model Not Loaded" + "\n"
					+ "This model configuration remains a work in progress "
					+ "at this time" + "\n",
					"Please select the default data range model",
					JOptionPane.INFORMATION_MESSAGE);
		}

		this.bankList = new ArrayList<BaselIIBank>();

		this.AggregateHoldingArrayList = new ArrayList<Double>();

		this.cdsSpreadsList = new ArrayList<double[][]>();

		this.ds1 = new double[][] { { 10, 22, 45, 121, 233 },
				{ 100.31, 100.36, 100.3, 101.06, 101.26 },
				{ 18, 32, 54, 154, 267 } };
		this.ds2 = new double[][] { { 11.14, 18.5, 46.98, 146.62, 260.18 },
				{ 100.27, 100.46, 100.22, 100.22, 100.2 },
				{ 18, 32, 54, 154, 267 } };
		this.ds3 = new double[][] { { 8.9, 14.23, 50.91, 146.93, 272.06 },
				{ 100.09, 100.1, 99.77, 99.56, 99.08 },
				{ 11, 17, 44, 133, 242 } };
		this.ds4 = new double[][] { { 8.64, 14.35, 58.63, 233.3, 379.12 },
				{ 100.1, 100.09, 99.54, 97.04, 96.09 },
				{ 11, 17, 44, 133, 242 } };
		this.ds5 = new double[][] { { 19, 34, 278, 942, 1415 },
				{ 99.5, 99.25, 92.42, 75.62, 66.62 }, { 9, 15, 64, 224, 389 } };
		this.ds6 = new double[][] { { 21.2, 41.5, 632.4, 1929, 2708.6 },
				{ 99.49, 99.13, 84.27, 62.43, 54.54 }, { 9, 15, 64, 224, 389 } };
		this.ds7 = new double[][] { { 107, 330, 1067, 2060, 2506 },
				{ 95.05, 88.36, 65.5, 44.55, 41.79 }, { 9, 15, 64, 224, 389 } };

		this.cashSpreadsList = new ArrayList<double[]>();

		this.cs1 = new double[] { 25.80054972, 59.9005781, 118.5201776,
				351.0466276, 410.837 };
		this.cs2 = new double[] { 25.60336212, 51.80670833, 133.1281888,
				303.4756145, 335 };
		this.cs3 = new double[] { 26.09418657, 52.69759425, 145.2037907,
				231.7442611, 385.1449275 };
		this.cs4 = new double[] { 33.43243458, 45.51142429, 149.64797,
				319.5186603, 481.0836751 };
		this.cs5 = new double[] { 42.21069651, 78.51795212, 241.5841542,
				522.7943071, 678.7846671 };
		this.cs6 = new double[] { 37.11998878, 115.4176658, 324.9278082,
				602.0691353, 633.1692308 };
		this.cs7 = new double[] { 60.40213646, 184.0286448, 462.1276383,
				697.011532, 1007.293958 };

		this.libor = new double[] { 0.0496, 0.055085, 0.053725, 0.053601,
				0.0535, 0.05355, 0.0535953 };

		this.cdsSpreadsList.add(this.ds1);
		this.cdsSpreadsList.add(this.ds2);
		this.cdsSpreadsList.add(this.ds3);
		this.cdsSpreadsList.add(this.ds4);
		this.cdsSpreadsList.add(this.ds5);
		this.cdsSpreadsList.add(this.ds6);
		this.cdsSpreadsList.add(this.ds7);

		this.cashSpreadsList.add(this.cs1);
		this.cashSpreadsList.add(this.cs2);
		this.cashSpreadsList.add(this.cs3);
		this.cashSpreadsList.add(this.cs4);
		this.cashSpreadsList.add(this.cs5);
		this.cashSpreadsList.add(this.cs6);
		this.cashSpreadsList.add(this.cs7);

		setSpreadsAndBasisSeries(defaultModel);

	}// end setData
	
	public void setDefaultLIBORSpreadsAndBasisData(){
		this.cdsSpreadsList = new ArrayList<double[][]>();
		
		this.cashSpreadsList = new ArrayList<double[]>();
		
		this.ds1 = new double[][] { { 10, 22, 45, 121, 233 },
				{ 100.31, 100.36, 100.3, 101.06, 101.26 },
				{ 18, 32, 54, 154, 267 } };
		this.ds2 = new double[][] { { 11.14, 18.5, 46.98, 146.62, 260.18 },
				{ 100.27, 100.46, 100.22, 100.22, 100.2 },
				{ 18, 32, 54, 154, 267 } };
		this.ds3 = new double[][] { { 8.9, 14.23, 50.91, 146.93, 272.06 },
				{ 100.09, 100.1, 99.77, 99.56, 99.08 },
				{ 11, 17, 44, 133, 242 } };
		this.ds4 = new double[][] { { 8.64, 14.35, 58.63, 233.3, 379.12 },
				{ 100.1, 100.09, 99.54, 97.04, 96.09 },
				{ 11, 17, 44, 133, 242 } };
		this.ds5 = new double[][] { { 19, 34, 278, 942, 1415 },
				{ 99.5, 99.25, 92.42, 75.62, 66.62 }, { 9, 15, 64, 224, 389 } };
		this.ds6 = new double[][] { { 21.2, 41.5, 632.4, 1929, 2708.6 },
				{ 99.49, 99.13, 84.27, 62.43, 54.54 }, { 9, 15, 64, 224, 389 } };
		this.ds7 = new double[][] { { 107, 330, 1067, 2060, 2506 },
				{ 95.05, 88.36, 65.5, 44.55, 41.79 }, { 9, 15, 64, 224, 389 } };

		

		this.cs1 = new double[] { 25.80054972, 59.9005781, 118.5201776,
				351.0466276, 410.837 };
		this.cs2 = new double[] { 25.60336212, 51.80670833, 133.1281888,
				303.4756145, 335 };
		this.cs3 = new double[] { 26.09418657, 52.69759425, 145.2037907,
				231.7442611, 385.1449275 };
		this.cs4 = new double[] { 33.43243458, 45.51142429, 149.64797,
				319.5186603, 481.0836751 };
		this.cs5 = new double[] { 42.21069651, 78.51795212, 241.5841542,
				522.7943071, 678.7846671 };
		this.cs6 = new double[] { 37.11998878, 115.4176658, 324.9278082,
				602.0691353, 633.1692308 };
		this.cs7 = new double[] { 60.40213646, 184.0286448, 462.1276383,
				697.011532, 1007.293958 };

		this.libor = new double[] { 0.0496, 0.055085, 0.053725, 0.053601,
				0.0535, 0.05355, 0.0535953 };

		this.cdsSpreadsList.add(this.ds1);
		this.cdsSpreadsList.add(this.ds2);
		this.cdsSpreadsList.add(this.ds3);
		this.cdsSpreadsList.add(this.ds4);
		this.cdsSpreadsList.add(this.ds5);
		this.cdsSpreadsList.add(this.ds6);
		this.cdsSpreadsList.add(this.ds7);

		this.cashSpreadsList.add(this.cs1);
		this.cashSpreadsList.add(this.cs2);
		this.cashSpreadsList.add(this.cs3);
		this.cashSpreadsList.add(this.cs4);
		this.cashSpreadsList.add(this.cs5);
		this.cashSpreadsList.add(this.cs6);
		this.cashSpreadsList.add(this.cs7);

	}
	
	public ArrayList<double[]> getDefaultCashSpreads(){
		return this.cashSpreadsList;
	}
	
	public ArrayList<double[][]> getDefaultCreditDerivativesSpreads(){
		return this.cdsSpreadsList;
	}

	public double[] getDefaultLIBORRate(){
		return this.libor;
	}


	public void setAllBanksBalancesheetCashData() {
		AllBanksBalancesheetCashData = new double[][] {
				{ 231242, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 245333, 975153.00, 1164217.00, 0, 0, 0, 0, 0, 0, 1087339.00,
						1066887.00, 1241413.00, 1142086.00, 1295211.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 917742, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 498303.00, 622273.00, 626348.00,
						545767.00, 384061.00, 471188.00, 439480.00, 792904.00,
						0, 0 },
				{ 623454, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12040.00, 10610.00,
						9437.00, 8445.00, 10084.00, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 101019, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 480228, 28656000.00, 25685000.00, 28805000.00, 28324000.00,
						27791000.00, 29321000.00, 26431977.00, 32001909.00,
						26569638.00, 30503018.00, 30442227.00, 29749436.00,
						29304790.00, 38828204.00, 37462382.00, 42407071.00,
						37948038.00, 42533379.00, 44598657.00, 50723059.00,
						41049261.00, 42533379.00, 42378008.00, 49700191.00,
						46847896.00, 48058397.00, 51942931.00, 46682005.00,
						150739599.00, 96696613.00, 148120382.00, 121847261.00,
						136246125.00, 146328503.00 },
				{ 541101, 7600523.00, 9785844.00, 7552436.00, 9125141.00,
						7677704.00, 10306153.00, 8068685.00, 10906548.00,
						11461385.00, 12991858.00, 12070961.00, 12321670.00,
						9525000.00, 8798000.00, 9651000.00, 10889000.00,
						9751000.00, 14460000.00, 18270000.00, 15412000.00,
						14275000.00, 14460000.00, 23826000.00, 27325000.00,
						34340000.00, 34695000.00, 92336000.00, 92247000.00,
						69916000.00, 59256000.00, 62230000.00, 61110000.00,
						62468000.00, 72990000.00 },
				{ 968605, 0, 0, 0, 0, 0, 0, 1249496.00, 1883614.00, 1403852.00,
						1419350.00, 1316416.00, 1506725.00, 1533547.00,
						1238702.00, 1195606.00, 1079974.00, 1000339.00,
						1051060.00, 777438.00, 863325.00, 1050416.00,
						1051060.00, 703296.00, 749885.00, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0 },
				{ 173333, 10504000.00, 15785000.00, 21466000.00, 16500000.00,
						23009000.00, 23016000.00, 18117000.00, 17799000.00,
						18079000.00, 19788000.00, 13573000.00, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 214807, 2272000.00, 2717000.00, 2509000.00, 2536000.00,
						2164000.00, 2594000.00, 2920000.00, 2129000.00,
						2460000.00, 2786000.00, 2414000.00, 2849000.00,
						2703000.00, 3299000.00, 2538000.00, 2786000.00, 0, 0,
						0, 0, 0, 0, 0, 2463000.00, 2248000.00, 2671000.00,
						3550000.00, 24589000.00, 10746000.00, 13806000.00,
						18139000.00, 18396000.00, 17694000.00, 8915000.00 },
				{ 497404, 478669.00, 515456.00, 627099.00, 657886.00,
						613483.00, 665322.00, 565470.00, 665498.00, 519780.00,
						616862.00, 581355.00, 537843.00, 554558.00, 807634.00,
						751483.00, 763743.00, 847794.00, 1063661.00, 858250.00,
						996145.00, 812352.00, 1063661.00, 729765.00, 918181.00,
						908956.00, 2390610.00, 2014866.00, 5451595.00,
						2203675.00, 2621881.00, 4293935.00, 11878582.00,
						14763143.00, 10899689.00 },
				{ 852320, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						2651150.00, 2475787.00, 2141285.00, 3128300.00,
						1982868.00, 2425990.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 837260, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 897273, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 476810, 24649000.00, 25032000.00, 22149000.00, 24544000.00,
						27227000.00, 29888000.00, 33568000.00, 32756000.00,
						36794000.00, 39476000.00, 38105000.00, 35110000.00,
						42400000.00, 46669000.00, 44826000.00, 38410000.00,
						39859000.00, 43205000.00, 42394000.00, 57294000.00,
						58293000.00, 43205000.00, 75427000.00, 86182000.00,
						81598000.00, 86903000.00, 117881000.00, 178881000.00,
						172942000.00, 192026000.00, 228098000.00, 174618000.00,
						165108000.00, 153074000.00 },
				{ 62101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 207248.00,
						123617.00, 53954.00, 93329.00, 53177.00, 108398.00,
						52424.00, 93329.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 586205, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 14409, 0, 0, 0, 0, 184532.00, 117674.00, 193285.00,
						176410.00, 265859.00, 276102.00, 294058.00, 179429.00,
						211625.00, 532852.00, 313519.00, 214440.00, 192103.00,
						206798.00, 327699.00, 641521.00, 619322.00, 206798.00,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 3041974, 0, 0, 0, 608923.00, 670213.00, 705508.00, 660489.00,
						617706.00, 640834.00, 821697.00, 696528.00, 570651.00,
						498196.00, 1300784.00, 517466.00, 643086.00, 662353.00,
						995563.00, 584840.00, 740123.00, 552426.00, 995563.00,
						1483825.00, 1224673.00, 0, 917861.00, 1010572.00,
						2254683.00, 0, 0, 0, 0, 0, 0 },
				{ 856748, 0, 0, 0, 0, 16485.00, 25474.00, 34884.00, 23971.00,
						19214.00, 12483.00, 12976.00, 12808.00, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 60143, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						1713238.00, 1772878.00, 1819265.00, 1532473.00,
						1669894.00, 1384019.00, 1819265.00, 1325078.00,
						1592823.00, 1964440.00, 1772105.00, 1513017.00,
						3295637.00, 3589406.00, 4520965.00, 3036225.00,
						5634437.00, 4652056.00, 0 },
				{ 363415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 601050, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 470511.00, 1132210.00,
						969132.00, 0, 0, 0, 0, 0 },
				{ 83638, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						102431.00, 93262.00, 78624.00, 74453.00, 99224.00,
						82367.00, 70042.00, 38162.00, 121603.00, 167407.00,
						204419.00, 0, 0, 0, 0, 0 },
				{ 1444580, 1646.00, 2149.00, 3620.00, 36961.00, 3596.00, 0,
						1816.00, 4449.00, 3792.00, 3594.00, 2261.00, 2770.00,
						3492.00, 4356.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0 },
				{ 723112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2450404.00,
						2138147.00, 2518611.00, 3137973.00, 2711705.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 913940, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 329633.00,
						610655.00, 612230.00, 1374207.00, 1484073.00, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 653648, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 633154, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 485559, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 484422, 15253129.00, 11895000.00, 13924000.00, 13481000.00,
						16272000.00, 18391000.00, 12428000.00, 12797000.00,
						15205000.00, 12415000.00, 17351000.00, 14198000.00,
						13283000.00, 14042000.00, 14976000.00, 16500000.00,
						13908000.00, 14348000.00, 17156000.00, 16542000.00,
						14039000.00, 14348000.00, 16629000.00, 17059000.00,
						17877000.00, 25455000.00, 25419000.00, 37193000.00,
						15164000.00, 13208000.00, 13695000.00, 12602000.00, 0,
						0 },
				{ 76201, 7594000.00, 10208000.00, 12481000.00, 11067000.00,
						10188000.00, 10238000.00, 10039000.00, 9993000.00,
						8611000.00, 10920006.00, 8813532.00, 13416923.00,
						12210436.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 2182786, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 46973.00, 80913.00, 118507.00,
						109419.00, 74960.00, 12270000.00, 26315000.00,
						9469000.00, 15981000.00, 28265000.00, 17101000.00,
						22470000.00 },
				{ 2897763, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 75633, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1239857.00, 1550193.00,
						1477149.00, 0, 1860287.00, 2318788.00, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 528849, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2747587, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17398.00,
						19051.00, 16579.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 413208, 4500274.00, 3474880.00, 2724489.00, 2787780.00,
						3112540.00, 3483894.00, 3511029.00, 3086432.00,
						3169524.00, 4645815.00, 5531203.00, 5321782.00,
						7182821.00, 5804812.00, 6563594.00, 7356205.00,
						10065459.00, 10774996.00, 6272182.00, 5489527.00,
						6337484.00, 10774996.00, 10544994.00, 8946879.00,
						7748592.00, 8016427.00, 8924352.00, 18725669.00,
						8343609.00, 12350920.00, 20227164.00, 23052885.00,
						34549322.00, 16641708.00 },
				{ 12311, 678879.00, 895391.00, 1007888.00, 993526.00,
						892253.00, 1202120.00, 811482.00, 912039.00, 852264.00,
						1065263.00, 924912.00, 821061.00, 809583.00, 862180.00,
						830519.00, 993355.00, 825023.00, 934223.00, 902973.00,
						1140789.00, 921629.00, 934223.00, 1388080.00,
						1590578.00, 1381734.00, 1352065.00, 1094512.00,
						957802.00, 2511980.00, 2326207.00, 2126629.00,
						1686283.00, 1516671.00, 1256140.00 },
				{ 3822016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 569242.00, 7407791.00,
						5554595.00, 6277882.00, 6611276.00, 9649768.00 },
				{ 58243, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 852218, 31217000.00, 31307000.00, 30705000.00, 26518000.00,
						28316000.00, 33281000.00, 27415000.00, 29050000.00,
						54693000.00, 61701000.00, 52382000.00, 66508000.00,
						60620000.00, 42669000.00, 46071000.00, 58083000.00,
						46779000.00, 51806000.00, 53114000.00, 53264000.00,
						63436000.00, 51806000.00, 61782000.00, 52657000.00,
						61810000.00, 46766000.00, 89822000.00, 172616000.00,
						118981000.00, 90918000.00, 85597000.00, 90335000.00,
						93053000.00, 73072000.00 },
				{ 2980209, 0, 0, 0, 0, 0, 28247.00, 41031.00, 31812.00,
						53113.00, 51143.00, 58085.00, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 280110, 0, 0, 0, 0, 0, 0, 0, 2860447.00, 2461286.00,
						2724219.00, 3178404.00, 2531523.00, 3391603.00,
						3423899.00, 3286648.00, 3428536.00, 2861277.00,
						3254983.00, 3365840.00, 2366334.00, 2200454.00,
						3254983.00, 2254680.00, 2339119.00, 2018526.00,
						2354660.00, 2411594.00, 6290692.00, 3360899.00,
						3959404.00, 3366876.00, 1934659.00, 4584011.00,
						2299204.00 },
				{ 455534, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1626171.00,
						1972451.00, 1698407.00, 1720483.00, 1595675.00,
						2239816.00, 1929896.00, 1720483.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 934329, 4128495.00, 0, 0, 0, 4410634.00, 4926738.00,
						4476697.00, 4863431.00, 3931875.00, 5582651.00,
						5610302.00, 5041018.00, 5328025.00, 4234197.00,
						4579693.00, 3244203.00, 3521308.00, 3280992.00,
						6520956.00, 4514270.00, 4572792.00, 3280992.00,
						7709269.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 208927, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 428444.00, 481431.00, 396889.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 1225800, 41758.00, 335151.00, 17777.00, 13175.00, 24376.00,
						640167.00, 355260.00, 634358.00, 223824.00, 936260.00,
						85217.00, 12242.00, 75606.00, 116159.00, 54768.00,
						71071.00, 433039.00, 118337.00, 179997.00, 187937.00,
						70509.00, 118337.00, 161100.00, 271779.00, 108679.00,
						199372.00, 204167.00, 22752834.00, 15615743.00,
						38798952.00, 0, 0, 0, 0 },
				{ 968436, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1456501, 0, 0, 0, 2063.00, 30615.00, 7117.00, 4660.00,
						3260.00, 4826.00, 2940.00, 12695.00, 8080.00, 7831.00,
						15705.00, 1832.00, 15150.00, 21498.00, 23037.00,
						75229.00, 28757.00, 46000.00, 23037.00, 59000.00,
						92000.00, 96000.00, 142000.00, 84000.00, 30093000.00,
						30018000.00, 14475000.00, 10148000.00, 12546000.00,
						16286000.00, 11768000.00 },
				{ 841472, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 259518, 1466320.00, 1643483.00, 1886680.00, 1643393.00,
						1813672.00, 1912168.00, 1749787.00, 1659077.00,
						1834581.00, 1576095.00, 1644678.00, 4604504.00,
						1929857.00, 2980399.00, 2760206.00, 2719740.00,
						2469304.00, 2795682.00, 3159169.00, 8142368.00,
						3236961.00, 2795682.00, 2784027.00, 3208065.00,
						5233036.00, 7370082.00, 4807397.00, 10331551.00,
						10335596.00, 11826751.00, 2286086.00, 0, 0, 0 },
				{ 178020, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 429295.00,
						437760.00, 866619.00, 1538067.00, 2519707.00, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 210434, 8801014.00, 9463164.00, 9236224.00, 10846557.00,
						7760308.00, 10251100.00, 9626079.00, 10237031.00,
						10952609.00, 13152722.00, 13386965.00, 13791453.00,
						14623766.00, 11843460.00, 13269241.00, 13964942.00,
						14585354.00, 16291113.00, 17281313.00, 20253571.00,
						19011741.00, 16291113.00, 20544073.00, 24947499.00,
						28863157.00, 26367143.00, 28857786.00, 28631505.00,
						23336854.00, 21442696.00, 26871255.00, 30165382.00,
						25273969.00, 26441189.00 },
				{ 245276, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 44656.00, 0,
						73878.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0 },
				{ 277820, 0, 0, 7775.00, 9424.00, 12986.00, 12690.00, 11405.00,
						8812.00, 8016.00, 13649.00, 7106.00, 7838.00, 12237.00,
						12121.00, 11633.00, 6945.00, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1160152, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 817824, 2717719.00, 2955784.00, 3700104.00, 3365705.00,
						3816134.00, 3779771.00, 3452094.00, 3185648.00,
						2924112.00, 3067155.00, 3259197.00, 3568206.00,
						2728331.00, 3319607.00, 3311545.00, 3371550.00,
						3126293.00, 3282357.00, 2991386.00, 3593622.00,
						3258094.00, 3282357.00, 3384506.00, 3560443.00,
						3896604.00, 3400056.00, 3115399.00, 8527231.00,
						7829179.00, 2254015.00, 2130513.00, 8461270.00,
						3924380.00, 8338766.00 },
				{ 612618, 572398.00, 604052.00, 744192.00, 778085.00,
						782206.00, 1073999.00, 791566.00, 643257.00, 660653.00,
						726314.00, 1639653.00, 983173.00, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 3303298, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 2665956.00, 2264549.00, 0, 2950167.00,
						2587627.00, 0, 0, 0, 0, 0, 0, 0 },
				{ 233031, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2182503.00,
						2152874.00, 2377750.00, 2021926.00, 2238126.00,
						2019009.00, 3577575.00, 2958801.00, 2238126.00,
						2907568.00, 3740208.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 918918, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1469594.00, 0 },
				{ 2942690, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 170378.00, 119692.00,
						113549.00, 185470.00, 159992.00, 150735.00, 104727.00,
						252376.00, 119731.00 },
				{ 543253, 30678.00, 38464.00, 47338.00, 44933.00, 45743.00,
						38620.00, 33869.00, 31616.00, 30811.00, 38469.00,
						35515.00, 38971.00, 38287.00, 51699.00, 58630.00,
						70044.00, 68096.00, 96004.00, 87654.00, 115038.00,
						74126.00, 96004.00, 89721.00, 104508.00, 91003.00,
						104658.00, 83845.00, 81359.00, 64692.00, 306053.00,
						338099.00, 368001.00, 364918.00, 72417.00 },
				{ 676656, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 445843, 0, 0, 0, 1148088.00, 1268396.00, 1365347.00,
						1165098.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 730468.00, 637516.00, 713526.00, 818566.00, 0, 0,
						0, 0, 0, 0, 0 },
				{ 2631172, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 35301, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						13800851.00, 12133773.00, 12164879.00, 7507905.00,
						7947255.00, 12133773.00, 11023066.00, 10141544.00,
						19755178.00, 25194090.00, 76459766.00, 58715853.00,
						38265719.00, 30220177.00, 32340275.00, 29003816.00,
						26082177.00, 25226960.00 },
				{ 675332, 4052896.00, 4173912.00, 0, 0, 4608922.00, 4484838.00,
						3939265.00, 4182973.00, 3607754.00, 4216331.00,
						3670423.00, 3617071.00, 4014162.00, 4649632.00,
						4483662.00, 4986298.00, 4286717.00, 4454456.00,
						4325627.00, 4498080.00, 4052397.00, 4454456.00,
						4161564.00, 4282282.00, 4058275.00, 3533219.00,
						3079351.00, 5609413.00, 5808280.00, 2483372.00,
						4261443.00, 6394590.00, 4654297.00, 3815163.00 },
				{ 1017658, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 504713, 0, 0, 0, 0, 0, 0, 9363408.00, 8631361.00, 7180778.00,
						7475658.00, 6973101.00, 6340324.00, 5894661.00,
						6450815.00, 6913461.00, 8000884.00, 0, 0, 6436856.00,
						8644951.00, 6303662.00, 7250783.00, 6710248.00,
						9024655.00, 7494457.00, 8040113.00, 7232911.00,
						8077564.00, 6290222.00, 6526915.00, 5280939.00,
						6198904.00, 8396049.00, 5021509.00 },
				{ 451965, 12543000.00, 12691000.00, 12409000.00, 11714000.00,
						9378000.00, 9669000.00, 9995000.00, 15256000.00,
						20141000.00, 20329000.00, 15965000.00, 15934000.00,
						15208000.00, 15680000.00, 15859000.00, 16843000.00,
						15300000.00, 15170000.00, 14897000.00, 16403000.00,
						13747000.00, 15170000.00, 13324000.00, 15703000.00,
						14867000.00, 14896000.00, 14733000.00, 27555000.00,
						22659000.00, 13399000.00, 13800000.00, 48800000.00,
						61283000.00, 78085000.00 },
				{ 2332808, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2505424, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	}

	public double[][] getAllBanksCDSBuyData() {
		return AllBanksCDSBuyData;
	}
	
	
	public void setAllDataTables(){
		this.setAllBanksCDSBuyData(); 
		this.setAllBanksCDSSellData(); 
		this.setAllBanksTotalAssetsData();
		this.setTop12andOthersTotalRMBSData();
		this.setTop12andOthersStructuredRMBSData();
		this.setAllBanksTotalRMBSData();
		this.setAllBanksStructuredRMBSData();
		this.setLIBORRates();
		this.setDataTimeSeriesTimeSpan();
		this.setAllBanksDatabaseTables();
		this.setAllBanksTotalAssetsData();
		this.setTop12andOthersOutstandingNotionalSecuritisedData();
		this.setTop12andOthersCDSProtectionSoldData();
		this.setTop12andOthersCDSProtectionBoughtData();
		this.setTop12andOthersGenericData();
	}
	
	
	
	public double[][] getTop12andOthersCDSProtectionBoughtData() {
		return this.top12andOthersCDSProtectionBoughtData; 
	}

	public double[][] getTop12andOthersCDSProtectionSoldData() {
		return this.top12andOthersCDSProtectionSoldData; 
	}
	
	public double[][] getTop12andOthersOutstandingNotionalSecuritisedData() {
		return this.top12andOthersOutstandingNotionalSecuritisedData; 
	}


	public double[][] getTop12andOthersGenericData() {
		return this.bankTop12andOthersGenericData; 
	}
	
	public double[][] getAllBanksTotalAssetsData1() {
		return this.AllBanksTotalAssetsData; 
	}
	
	public void setAllBanksCDSBuyData() {
		AllBanksCDSBuyData = new double[][] {
				{ 480228, 45219000, 52118000, 53853000, 60420000, 67438000,
						76882000, 75005875, 83226831, 101546772, 161633600,
						234375857, 285549150, 499276436, 806027274, 960807865,
						1226083745, 373092267, 452845428, 547809414, 704579880,
						959187215, 1140222069, 1441627241, 1483958464,
						1509905197, 1326855438, 1224909731, 1028649827,
						937776486, 947036420, 1008391084, 1972633388,
						2394614343.00, 2324991748.00 },
				{ 541101, 304728, 428485, 539857, 446916, 653304, 762342,
						813176, 852948, 770112, 1006986, 1031591, 1184023,
						1196000, 1274000, 1261000, 1099000, 1023000, 1353000,
						1508000, 1655000, 1751000, 1794000, 1849000, 2089000,
						1884000, 1514000, 1260000, 1175000, 1090000, 864000,
						834000, 804000, 814000, 774000 },
				{ 476810, 36075000, 43746000, 47782000, 55714000, 59003000,
						61573000, 69649000, 75959000, 116862000, 141976000,
						187799000, 249851000, 300825000, 351857000, 420661000,
						449122000, 478678000, 566365000, 682480000, 833701000,
						1055795000, 1311309000, 1582420000, 1610324000,
						1728307000, 1636972000, 1495078000, 1397546000,
						1367600000, 1289612000, 1295113000, 1160557000,
						1192231000, 1219929000 },
				{ 214807, 189000, 569000, 502000, 1983000, 1462000, 2479000,
						3140000, 3739000, 2156000, 1744000, 1753000, 1429000,
						1229000, 2453000, 3111000, 4755000, 0, 0, 0, 0, 0, 0,
						0, 100000, 100000, 100000, 100000, 100000, 75000,
						68000, 68000, 68000, 68000, 68000 },
				{ 2182786, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 190000, 555000, 1554305, 3480729,
						4551750, 718013000, 661380186, 490675000, 434005000,
						374417000, 235452000, 210684000 },
				{ 413208, 375000, 1293000, 3807962, 5327532, 7789622, 10345122,
						12107642, 14918569, 22129111, 30211151, 43271443,
						60892250, 86200169, 133499827, 171849686, 169463285,
						214810750, 280454552, 341184549, 375507520, 409881725,
						471526748, 531553879, 586646669, 634636670, 584319845,
						542577133, 457089844, 441120746, 397478910, 387637324,
						366613338, 371925465, 364317502 },
				{ 852218, 135672000, 153139000, 171707000, 193056000,
						217173000, 242389000, 256427000, 301738000, 330055000,
						381959000, 471605000, 535548000, 659395000, 826454000,
						1050518000, 1156800000, 1437836000, 1752736000,
						2111683000, 2318540000.00, 2811876000.00,
						3268460000.00, 3920948000.00, 4016580000.00,
						4119788000.00, 3994756000.00, 4649831000.00,
						4262320000.00, 3810720000.00, 3442183000.00,
						3202276000.00, 3007303000.00, 2797881000.00,
						2632094000.00 },
				{ 280110, 0, 0, 0, 0, 0, 0, 0, 40000, 234000, 1280000, 2423500,
						2843000, 2985000, 3195000, 3216000, 3378166, 3703706,
						3964705, 4214705, 4319705, 4413706, 4463706, 4441706,
						4346627, 4436195, 4683720, 4069364, 3876800, 3839836,
						3584841, 2519927, 2496491, 2184133, 2022235 },
				{ 1225800, 889500, 1564500, 1883000, 2068000, 1881500, 1961933,
						2607983, 2966538, 40000, 3175029, 3529921, 4117814,
						4204441, 4388799, 4488435, 4966903, 5517878, 5894095,
						6120236, 6170762, 6795564, 7492890, 8043700, 8728016,
						9247743, 9146086, 0, 0, 0, 8535654, 2619, 0, 0, 0 },
				{ 1456501, 0, 0, 0, 443500, 443500, 613930, 585100, 541100, 0,
						1400323, 1903698, 2025120, 2236794, 2705127, 3472254,
						3729880, 4091773, 5863468, 8628036, 6836090, 6738000,
						6281000, 9132000, 15322000, 23478000, 18997000,
						20401000, 22058000, 28200000, 32776000, 28604000,
						24606000, 21878000, 19582000 },
				{ 484422, 2751842, 4610000, 6582000, 7442000, 9524000,
						12901000, 17446000, 20358000, 23955000, 29132000,
						38450000, 51185000, 64229000, 75142000, 98838000,
						115065000, 120479000, 91003000, 129552000, 160155000,
						160254000, 170004000, 155296000, 179633000, 220471000,
						188712000, 161054000, 150748000, 159748000, 122278000,
						140832000, 90859000, 0, 0 },
				{ 451965, 1077000, 1112000, 1215000, 1272000, 1237000, 1385000,
						1442000, 0, 1652000, 1646000, 1691000, 1761000,
						1751000, 1649000, 1691000, 1696000, 1768000, 2019000,
						2074000, 914000, 1050000, 1279000, 1529000, 1880000,
						1246000, 1411000, 1113000, 1036000, 1167000, 1000000,
						1020000, 865000, 84841000, 63058000 },
				{ 231242, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 245333, 85000, 85000, 0, 0, 0, 0, 0, 0, 6000, 6000, 6000,
						6000, 6000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 917742, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 7500, 7500, 7500, 7500, 7500, 7500,
						7500, 7500, 0, 0 },
				{ 623454, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22000, 22000, 22000,
						22000, 22000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 101019, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 968605, 0, 0, 0, 0, 0, 0, 5000, 5000, 5000, 5000, 5000, 5000,
						5000, 5000, 25000, 25000, 25000, 25000, 25000, 25000,
						5000, 5000, 5000, 10000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 173333, 4701000, 6013000, 7094000, 8792000, 9950000, 9984000,
						10170000, 11675000, 14921000, 7098000, 734000, 0, 0, 0,
						0, 0, 263200, 263200, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0 },
				{ 497404, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 134371, 115123, 114957,
						114733, 112825, 112596 },
				{ 852320, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						15003, 15003, 15003, 15003, 15003, 10002, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 837260, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 897273, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 62101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, 65, 106,
						130, 71, 67, 50, 70, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 586205, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 14409, 0, 0, 0, 0, 200, 242, 184, 140, 174, 177, 549, 398,
						51, 157, 238, 254, 647, 819, 436, 405, 305, 388, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 3041974, 0, 0, 0, 673, 723, 906, 676, 2253, 1573, 1330, 1616,
						1276, 739, 492, 436, 82, 637, 1105, 1686, 1001, 686,
						1310, 227, 138, 0, 1058, 701, 1, 0, 0, 0, 0, 0, 0 },
				{ 856748, 0, 0, 0, 0, 20000, 20000, 20000, 20000, 20000, 20000,
						20000, 20000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0 },
				{ 60143, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7462,
						7836, 8251, 8236, 7995, 6999, 5615, 5331, 10683, 5648,
						5162, 4815, 5273, 4939, 4549, 4133, 3608, 3190, 0 },
				{ 363415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 601050, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 17865, 17385, 19567, 0, 0, 0,
						0, 0 },
				{ 83638, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						38000, 74000, 84000, 88000, 50000, 13500, 19500, 18000,
						22500, 10500, 10500, 0, 0, 0, 0, 0 },
				{ 1444580, 50000, 50000, 50000, 50000, 50000, 0, 40000, 40000,
						1155400, 40000, 40000, 40000, 40000, 40000, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 723112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14631, 14357, 15833,
						26059, 21926, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 },
				{ 913940, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 28748, 28425, 29872,
						30185, 34368, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 },
				{ 653648, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 633154, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 485559, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 76201, 3869000, 4270000, 4758000, 4469000, 5332000, 5826000,
						6776000, 8930000, 15891000, 15914473, 26322829,
						20199628, 11208742, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2897763, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 75633, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10000, 10000, 10000, 0,
						15462, 14075, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 },
				{ 528849, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2747587, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20000, 40000,
						50000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 12311, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 3822016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1331964065,
						2206272798.00, 3301673718.00, 4565632967.00,
						6195092827.00 },
				{ 58243, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2980209, 0, 0, 0, 0, 0, 18400, 32784, 34880, 34280, 35530,
						36993, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 455534, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 285000,
						285000, 285000, 285000, 285000, 285000, 15000, 15000,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 934329, 471023, 0, 0, 0, 43000, 206335, 409704, 612443,
						608789, 671010, 691572, 694054, 642769, 703155, 732461,
						598484, 574494, 506084, 460740, 320518, 271116, 241667,
						227105, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 208927, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 1726, 1742, 592, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 968436, 0, 0, 0, 0, 0, 0, 0, 0, 3465456, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 841472, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 259518, 131136, 0, 0, 0, 0, 0, 0, 85212, 93133, 176943,
						189439, 198572, 802289, 781710, 758488, 1157484,
						454105, 529436, 866051, 927408, 1060775, 1556244,
						1265239, 1384731, 1406734, 1359999, 1197588, 1285226,
						152951, 0, 0, 0, 0, 0 },
				{ 178020, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 263200,
						263200, 263200, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0 },
				{ 210434, 52500, 62500, 82500, 83750, 78750, 88750, 98750,
						78750, 82500, 57500, 77500, 97500, 135000, 135000,
						125000, 116250, 165250, 235250, 230250, 300250, 237750,
						260750, 270750, 278750, 263750, 253750, 269250, 235500,
						185500, 132500, 102500, 127000, 127000, 127000 },
				{ 245276, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 106703, 0,
						87524, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 277820, 0, 0, 472, 523, 523, 1088, 1106, 1114, 71, 67, 66,
						605, 612, 625, 623, 624, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1160152, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 9024830, 8897423, 8814654,
						0, 0, 3423, 2749, 2619 },
				{ 817824, 169000, 159000, 207483, 227448, 232000, 242000,
						225000, 165625, 175625, 301625, 322832, 381710, 503625,
						905625, 973625, 995425, 1426425, 1705675, 2202175,
						2720050, 2616050, 3252050, 3433800, 3956000, 3985000,
						3654500, 3412500, 2000500, 1787694, 1425621, 1242184,
						1046000, 971000, 750000 },
				{ 612618, 2130881, 1980867, 1823067, 1672850, 1515978, 1362541,
						1211541, 1147546, 837115, 847020, 706866, 581479, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 3303298, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 153, 51, 0, 2433, 2295, 0, 0, 0, 0, 0,
						0, 0 },
				{ 233031, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 75000, 75000,
						75000, 75000, 75000, 20000, 20000, 82276, 47276, 62276,
						72051, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 918918, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1351, 0 },
				{ 2942690, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 3000, 3000, 3000, 21000, 8000,
						8000, 8000, 3000, 3000 },
				{ 543253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 910, 954, 875, 790 },
				{ 676656, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 445843, 0, 0, 0, 6700361, 6271561, 8974135, 4207434, 1689000,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2097736,
						2023021, 1933906, 138191, 0, 0, 0, 0, 0, 0, 0 },
				{ 2631172, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 35301, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 95000,
						127500, 165000, 155000, 165000, 202500, 227500, 237500,
						237500, 237500, 145000, 145000, 170000, 170000, 170000,
						170000, 145000, 155000 },
				{ 675332, 20000, 20000, 0, 0, 45000, 126000, 183000, 246000,
						267000, 338000, 538000, 792000, 844000, 920000, 839000,
						902947, 719133, 738419, 709419, 634419, 595419, 662419,
						765419, 781419, 756419, 831419, 609133, 585219, 603369,
						585369, 531169, 525226, 460226, 393476 },
				{ 1017658, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 504713, 0, 0, 0, 0, 0, 0, 1149, 126842, 127366, 125200,
						137530, 136870, 138209, 144266, 108397, 142679, 0, 0,
						25000, 25000, 25000, 65500, 45500, 55500, 55500, 55500,
						50500, 63500, 88500, 153500, 121000, 116000, 116000,
						116000 },
				{ 2332808, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2505424, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	}

	public void setAllBanksCDSSellData() {
		AllBanksCDSSellData = new double[][] {
				{ 480228, 20514000, 26993000, 30998000, 36024000, 40812000,
						47331000, 48933172, 55369909, 66829707, 104831444,
						154713601, 215285063, 358096975, 523904491, 655697353,
						808274050, 399549522, 491816735, 585244397, 745502945,
						1014171675, 1200960239, 1482301956, 1522460858,
						1545768882, 1344014677, 1231267090, 1004736144,
						912522462, 930567036, 992899433, 1964463832,
						2403694143.00, 2342950196.00 },
				{ 541101, 1615212, 1412628, 1355748, 1323400, 1120242, 1005009,
						845503, 527380, 527380, 527380, 442729, 417400, 417000,
						417000, 529000, 370000, 179000, 0, 0, 0, 0, 2000, 2000,
						2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000,
						2000, 2000 },
				{ 476810, 41083000, 55326000, 58878000, 62704000, 64113000,
						67703000, 76327000, 90144000, 125133000, 147404000,
						187567000, 232543000, 281584000, 312764000, 375618000,
						402199000, 415098000, 505877000, 624825000, 772315000,
						991254000, 1140887000, 1411020000, 1505618000,
						1578005000, 1527573000, 1392546000, 1290310000,
						1277791000, 1226198000, 1175270000, 1089611000,
						1130024000, 1146270000 },
				{ 2182786, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 640462000, 589672186,
						433888000, 403764000, 339144000, 216036000, 189988000 },
				{ 413208, 425989, 1118989, 3842141, 5730411, 8465821, 11442321,
						13570218, 16448998, 27872542, 38574192, 54862908,
						75187432, 101240555, 152748753, 206522601, 222420239,
						265390525, 324942253, 384745555, 424468407, 461419181,
						521034600, 579998976, 638067652, 676384976, 623282704,
						576815849, 473629328, 445873554, 398645617, 392765734,
						372604526, 381278448, 368695762 },
				{ 852218, 151413000, 157786000, 161965000, 172994000,
						191623000, 218450000, 240134000, 275955000, 292791000,
						361649000, 455090000, 530612000, 669772000, 834368000,
						1044040000, 1144264000, 1435005000, 1764236000,
						2115480000, 2291867000.00, 2819307000.00,
						3210473000.00, 3798768000.00, 3860565000.00,
						3970637000.00, 3817140000.00, 4478653000.00,
						4103539000.00, 3661843000.00, 3317959000.00,
						3121126000.00, 2939911000.00, 2761366000.00,
						2622841000.00 },
				{ 280110, 0, 0, 0, 0, 0, 0, 0, 20000, 215000, 1011000, 2310000,
						2710000, 2843000, 2969000, 3184000, 3395706, 3622206,
						3291705, 3320206, 3356205, 3371206, 3391206, 3353206,
						3328206, 3573206, 3645206, 3435839, 3309302, 3302269,
						3276349, 2202435, 1916952, 1554310, 1492287 },
				{ 1225800, 0, 0, 25000, 25000, 25000, 25000, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						9500, 0, 0, 0 },
				{ 1456501, 0, 0, 0, 0, 0, 0, 0, 0, 10918, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 484422, 1456842, 5021000, 6679000, 5528000, 5852000, 7109000,
						12790000, 15239000, 22885000, 30219000, 39470000,
						46341000, 57617000, 61638000, 80146000, 96293000,
						92544000, 81275000, 125699000, 148610000, 155762000,
						164655000, 172124000, 188351000, 201304000, 178621000,
						150829000, 141959000, 146717000, 114923000, 136072000,
						85699000, 0, 0 },
				{ 451965, 1054000, 1156000, 1372000, 1348000, 1340000, 1422000,
						1447000, 0, 1637000, 1504000, 1537000, 1542000,
						1596000, 1586000, 1587000, 1618000, 1681000, 1760000,
						1719000, 596000, 686000, 616000, 833000, 870000,
						832000, 817000, 607000, 488000, 465000, 404000, 362000,
						340000, 76888000, 62492000 },
				{ 231242, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4100, 4100, 4100, 4100,
						4100, 4100 },
				{ 245333, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 917742, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 88045, 120645, 120645, 120645,
						125381, 111439, 110993, 109781, 108927, 73075 },
				{ 623454, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 101019, 70000, 90000, 100000, 87000, 62000, 27000, 27000,
						47000, 75000, 115000, 120000, 110000, 85000, 95000,
						70000, 60000, 60000, 55000, 55000, 55000, 55000, 15000,
						10000, 10000, 10000, 10000, 10000, 10000, 10000, 10000,
						5000, 5000, 5000, 0 },
				{ 968605, 0, 0, 0, 0, 5000, 5000, 0, 0, 0, 0, 0, 0, 0, 0,
						50000, 150000, 150000, 150000, 150000, 150000, 150000,
						150000, 150000, 150000, 100000, 100000, 100000, 50000,
						0, 0, 0, 0, 0, 0 },
				{ 173333, 390000, 703000, 1038000, 1371000, 2353000, 5074000,
						6475000, 8749000, 12432000, 4363000, 558000, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 214807, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26000, 26000, 68000,
						68000, 68000 },
				{ 497404, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 57722, 52273, 52273, 108731,
						101333, 98215, 93996, 81543, 74718 },
				{ 852320, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						32385, 31703, 31143, 30584, 60074, 68821, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 837260, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						9, 38, 84, 132, 178, 221, 285, 347, 358, 371, 391, 0,
						0, 0, 0, 0 },
				{ 897273, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						553, 757, 723, 89, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 62101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 586205, 0, 0, 0, 0, 0, 0, 0, 0, 71, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 14409, 38360, 63360, 63360, 3805, 4397, 2057, 1531, 1205,
						1392, 552, 1019, 677, 338, 673, 249, 744, 32, 842,
						1306, 1292, 1229, 1264, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 3041974, 13390, 18649, 10001, 38044, 33205, 29070, 16810,
						21021, 18917, 13205, 6273, 4755, 3331, 542, 2116, 896,
						1617, 896, 676, 366, 832, 1186, 646, 3601, 7579, 5807,
						3306, 9295, 12453, 0, 0, 0, 0, 0 },
				{ 856748, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 60143, 10768, 10768, 10768, 10768, 6793, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 4960, 5564, 9284, 7697, 7546, 6890, 7553,
						7075, 6089, 28336, 22241, 19104, 45558, 42841, 32743,
						31150, 26560, 26748, 0 },
				{ 363415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						17715, 17684, 33703, 33622, 69085, 68912, 73919, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 601050, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 10000, 10000, 21151, 25389, 24947, 24497, 31046,
						30365, 35794, 0, 0, 0, 0, 0 },
				{ 83638, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						23000, 28000, 24500, 23000, 0, 0, 5000, 8000, 12000, 0,
						0, 0, 0, 0, 0, 0 },
				{ 1444580, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 723112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 49478, 54240, 49914,
						65152, 129145, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0 },
				{ 913940, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29300, 20000, 23188,
						21625, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0 },
				{ 653648, 0, 0, 0, 0, 0, 0, 0, 0, 707, 759, 1039, 1020, 1155,
						1197, 1276, 1314, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 633154, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4346, 2977, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 485559, 217046, 239653, 231027, 232555, 74461, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 76201, 3340000, 3628000, 4009000, 3566000, 3615000, 3759000,
						4963000, 7000000, 13872000, 14094211, 22532886,
						21231780, 11721226, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2897763, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, 46, 40, 45, 45, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 75633, 13499, 12973, 12704, 32431, 42153, 41324, 40491,
						39652, 38810, 61138, 50062, 48980, 57894, 46741, 93690,
						81901, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 528849, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 6000, 6000, 6000, 6000, 6000, 6000,
						5600, 5600, 5600, 5600, 5200 },
				{ 2747587, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 12311, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 3822016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1331964065,
						2206272798.00, 3301673718.00, 4565632967.00,
						6195092827.00 },
				{ 58243, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 8570, 17470, 17470, 17470, 0, 0, 0, 0,
						0, 0, 0 },
				{ 2980209, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 455534, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 934329, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 208927, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 968436, 0, 0, 20000, 50000, 50000, 50000, 50000, 0, 569335,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 841472, 0, 0, 0, 0, 0, 0, 0, 0, 0, 417, 195, 0, 750, 805,
						573, 287, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0 },
				{ 259518, 44645, 0, 0, 0, 0, 0, 0, 62543, 64543, 79314, 112407,
						150409, 222067, 338320, 429458, 492913, 633627, 639884,
						637116, 620103, 699456, 912556, 711506, 853729, 842876,
						1047655, 1095644, 943218, 10000, 10000, 10000, 0, 0, 0 },
				{ 178020, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 210434, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 245276, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 277820, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1160152, 25000, 25000, 25000, 20000, 15000, 15000, 15000,
						10000, 10000, 10000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9274, 8723, 7606 },
				{ 817824, 0, 0, 81265, 109389, 0, 0, 0, 0, 0, 0, 0, 0, 110000,
						557500, 603000, 484000, 338000, 889000, 1188500,
						1071000, 1173500, 1820500, 2062000, 2099500, 1808000,
						1697000, 1527000, 1054500, 974694, 657621, 609184,
						542000, 517000, 417000 },
				{ 612618, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 3303298, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 5035, 22666, 34939, 19629, 18115, 55477,
						52844, 0, 0, 0, 0, 0 },
				{ 233031, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 142381, 117877, 112876, 130224, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 },
				{ 918918, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22000, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1351, 0 },
				{ 2942690, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 543253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 589, 625, 576, 513 },
				{ 676656, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 125, 125, 175, 175, 175, 175, 175, 175, 175, 301,
						301, 301, 301, 301 },
				{ 445843, 0, 0, 0, 0, 0, 0, 0, 1587000, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2631172, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16491, 16349, 16206,
						16059, 15903, 13853 },
				{ 35301, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 675332, 225000, 235000, 205000, 180000, 205000, 225000,
						215000, 195000, 285000, 475000, 594000, 757000, 768000,
						765000, 630000, 664267, 497133, 482419, 462894, 323419,
						303419, 298419, 303419, 313419, 313419, 313419, 218419,
						195819, 220819, 170819, 136619, 144476, 127476, 142476 },
				{ 1017658, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9015,
						10138, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 504713, 0, 0, 0, 0, 0, 0, 1103, 63626, 63626, 68626, 72376,
						84489, 97689, 157635, 168098, 169437, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2332808, 0, 0, 6701, 6638, 10211, 0, 0, 0, 40, 41, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0 },
				{ 2505424, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 122, 419,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
	}

	public void setAllBanksTotalAssetsData() {

		AllBanksTotalAssetsData = new double[][] {
				{ 231242, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 245333, 38236209, 38500534, 0, 0, 0, 0, 0, 0, 47407870,
						48212819, 49821444, 49711241, 50151064, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 917742, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 21625399, 22059071, 22276617,
						23843152, 23999487, 23607133, 22471845, 22564122, 0, 0 },
				{ 623454, 0, 0, 0, 0, 0, 0, 0, 0, 0, 256910, 257993, 255510,
						254887, 251224, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 101019, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 480228, 540610000, 562116000, 576004000, 565423000,
						574410000, 656219000, 624722850, 617857587, 690572545,
						706888184, 740695499, 771594340, 838247353, 1047545743,
						1057306654, 1082250290, 1104944125, 1160260442,
						1185580956, 1196123794, 1204471773, 1160260442,
						1290376229, 1312794218, 1355154455, 1327429079,
						1359070851, 1470276918, 1434036734, 1450829889,
						1460147344, 1465221449, 1496322329, 1518957843 },
				{ 541101, 73967030, 78314898, 78383876, 74959841, 76683021,
						96515713, 92285656, 89299040, 89477590, 94536383,
						90515457, 92138427, 80116000, 86079000, 85737000,
						85868000, 87750000, 93863000, 91155000, 85952000,
						83608000, 93863000, 112524000, 115672000, 128342000,
						130062000, 218699000, 195164000, 163006000, 162003000,
						166539000, 164275000, 162064000, 175994000 },
				{ 968605, 0, 0, 0, 0, 0, 0, 7288595, 5715844, 5972711, 5780852,
						5424748, 6323150, 4531914, 4421570, 4399052, 4708609,
						4430709, 4863578, 4027440, 4048602, 4148473, 4863578,
						3893768, 4235113, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 173333, 157768000, 183869000, 206244000, 217537000,
						226331000, 231100000, 216452000, 256787000, 256701000,
						245783000, 259527000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 214807, 40856000, 43639000, 40447000, 40885000, 38406000,
						41394000, 35838000, 34068000, 33981000, 30969000,
						32961000, 33341000, 35234000, 33459000, 31950000,
						33675000, 0, 0, 0, 0, 0, 0, 0, 35424000, 38216000,
						46071000, 43932000, 50801000, 41700000, 41418000,
						45503000, 45875000, 45147000, 42306000 },
				{ 497404, 20773214, 21240851, 22529981, 23378958, 26228183,
						25714346, 25713869, 26439985, 26846850, 29277702,
						28964125, 28654814, 32070611, 31690679, 31750480,
						32082074, 40784064, 40111799, 39925776, 39581731,
						41239729, 40111799, 44445911, 45485973, 43416567,
						98855014, 98613149, 101632075, 106984963, 104413336,
						108026470, 140038551, 148084280, 152617020 },
				{ 852320, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						88815279, 117134085, 118083229, 85214955, 126290166,
						127698351, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 837260, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 897273, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 476810, 454867000, 487074000, 481408000, 498676000,
						514803000, 523115000, 554540000, 582123000, 606191000,
						648243000, 651345000, 694529000, 684592000, 704855000,
						704616000, 706497000, 749335000, 777345000, 816362000,
						1019497000, 1076949000, 777345000, 1233325000,
						1251715000, 1292503000, 1228445000, 1207007000,
						1227040000, 1143561000, 1165400000, 1186754000,
						1161361000, 1171094000, 1157877000 },
				{ 62101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9217006,
						9723617, 9458006, 9984349, 9828929, 10185131, 9977966,
						9984349, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 586205, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 14409, 0, 0, 0, 0, 24273852, 24958446, 26483497, 28289790,
						28965168, 29630166, 30411592, 31251938, 31726337,
						33847210, 33589830, 34500279, 34573718, 37112775,
						35515457, 35276018, 35131324, 37112775, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 3041974, 0, 0, 0, 20038191, 23041195, 23869185, 24795833,
						26738196, 27816626, 28374750, 28886428, 29788816,
						30681979, 32586694, 34050148, 34718624, 35076460,
						35585892, 35504885, 34417990, 32626979, 35585892,
						35635166, 39643178, 0, 38174843, 35619036, 34712685, 0,
						0, 0, 0, 0, 0 },
				{ 856748, 0, 0, 0, 0, 397846, 412799, 432469, 432252, 445501,
						430493, 429518, 425492, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 60143, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 53576580,
						57065230, 57652104, 58822549, 58543306, 57607155,
						57652104, 60070971, 62539056, 66967229, 65961348,
						65502164, 67597552, 67462382, 63561550, 59448930,
						59143523, 56965482, 0 },
				{ 363415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 601050, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 15634806, 17422344, 17846459,
						0, 0, 0, 0, 0 },
				{ 83638, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						4664250, 4898714, 5038242, 4449149, 5871653, 5458889,
						6500908, 6035838, 6266016, 6024301, 6197587, 0, 0, 0,
						0, 0 },
				{ 1444580, 110707, 109488, 123985, 155991, 134144, 0, 139118,
						145027, 156241, 170907, 177603, 185331, 199963, 195063,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 723112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 56807417, 57191087,
						56879658, 58100498, 57612849, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 913940, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 37844547, 46372807,
						46887540, 47241348, 47605076, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 653648, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 633154, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 485559, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 484422, 299014458, 301645000, 311936000, 318870000,
						323783000, 331621000, 344056000, 353541000, 364474000,
						368871000, 380236000, 389963000, 454751000, 459529000,
						477994000, 472143000, 496566000, 504270000, 517174000,
						518123000, 518753000, 504270000, 557018000, 653269000,
						665817000, 670639000, 664223000, 635476000, 579258000,
						560556000, 518389000, 510083000, 0, 0 },
				{ 76201, 178224000, 178020000, 175483000, 179362000, 192100000,
						191041000, 188775000, 192265000, 195323000, 213732068,
						209561316, 218740377, 213056075, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2182786, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 14965549, 19127078, 25571480, 25726832,
						21630286, 163066000, 161455000, 119678000, 114868000,
						91016000, 89744000, 95515000 },
				{ 2897763, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 75633, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20514196, 21493019,
						22261172, 0, 33880728, 34507021, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 528849, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2747587, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 460413,
						618892, 650528, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0 },
				{ 413208, 84760144, 84301682, 87174980, 86415769, 85935960,
						90400980, 90157211, 92958123, 99867085, 110305393,
						118454499, 138296274, 138568614, 141451990, 145949107,
						150679481, 158753889, 168899000, 166631767, 165673017,
						169010168, 168899000, 181810863, 184491526, 188284200,
						177466246, 181587239, 181604079, 177777752, 158958805,
						168262874, 167165244, 183562481, 183595108 },
				{ 12311, 24605964, 25136788, 26475408, 27399278, 27773443,
						28313561, 29892589, 30245697, 30833846, 31056820,
						31223665, 32105960, 31731672, 32586447, 32389308,
						32414250, 35241416, 35886969, 35307912, 34914009,
						34489760, 35886969, 54724424, 54098791, 55566801,
						54842484, 53709880, 53586626, 51061394, 50950048,
						51988299, 51111245, 51418039, 51211535 },
				{ 3822016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 572617, 7416896,
						5565426, 6283232, 6622025, 9658530 },
				{ 58243, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 852218, 541342000, 581407000, 605071000, 621962000,
						621240000, 661427000, 638134000, 628681000, 648692000,
						654641000, 661772000, 967365000, 983049000, 973113000,
						1008426000, 1013985000, 1093394000, 1144680000,
						1173732000, 1179390000, 1224104000, 1144680000,
						1244049000, 1318888000, 1407568000, 1378468000,
						1768657000, 1746242000, 1688164000, 1663998000,
						1669868000, 1627684000, 1674523000, 1568093000 },
				{ 2980209, 0, 0, 0, 0, 0, 386269, 505380, 395440, 439050,
						437216, 443073, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 280110, 0, 0, 0, 0, 0, 0, 0, 74320522, 73926760, 74847093,
						77374961, 86061798, 85297323, 86504869, 87574022,
						88960716, 88877085, 90534912, 91771537, 88081220,
						89408200, 90534912, 93178474, 95861810, 97978986,
						98047883, 97811238, 101868610, 95515169, 95248904,
						93760366, 90179122, 91953232, 90662569 },
				{ 455534, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 72983109,
						71060868, 75194010, 72245639, 71435411, 72966529,
						75051808, 72245639, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 934329, 24244743, 0, 0, 0, 26450195, 29561486, 20829804,
						20838836, 21918147, 23663458, 23315510, 25203827,
						25505553, 25088193, 25778510, 25025855, 24487508,
						26180441, 28114733, 26225646, 25200799, 26180441,
						32183240, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 208927, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 8123257, 8572343, 7591874, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 },
				{ 1225800, 64410112, 62867872, 66882511, 68117478, 64467730,
						64582516, 66735012, 66643140, 67079011, 66421616,
						63042761, 66709070, 64753692, 63295272, 58789582,
						60367705, 62040397, 60951179, 60286591, 67234664,
						61365783, 60951179, 77648574, 78133893, 63002659,
						58042116, 61643285, 61809503, 65240441, 67995054, 0, 0,
						0, 0 },
				{ 968436, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1456501, 0, 0, 0, 4243920, 2918035, 2918562, 3309329,
						4190682, 4137822, 3832315, 3812882, 5376940, 5842373,
						5597976, 7015829, 8682004, 10844942, 13156950,
						14385263, 21032312, 25080000, 13156950, 29286000,
						35064000, 38491000, 38530000, 37638000, 58054000,
						66238000, 65328000, 65487000, 66159000, 72292000,
						65746000 },
				{ 841472, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 259518, 37357742, 38394727, 43014009, 43721511, 43813811,
						46658358, 45798869, 46276022, 46865872, 47666649,
						51833074, 52974916, 67317318, 69600658, 71185792,
						69482177, 72949011, 69838107, 135661518, 134344792,
						131741508, 69838107, 141885485, 138755343, 152519145,
						151164598, 141500812, 146057789, 146013198, 141714026,
						131594152, 0, 0, 0 },
				{ 178020, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15749515,
						14798864, 14471413, 14583483, 15091736, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 210434, 31183809, 30746494, 32491133, 31974422, 28920517,
						31553376, 33026172, 33403079, 32233741, 35218239,
						33053157, 37043931, 39413821, 37880802, 40151340,
						44864893, 41825206, 45406399, 47296281, 52312832,
						51027990, 45406399, 54113351, 58398420, 67961839,
						65199779, 68930403, 70433589, 65796327, 62156001,
						64932863, 68809308, 63109667, 66624485 },
				{ 245276, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4215451, 0,
						4510223, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0 },
				{ 277820, 0, 0, 190988, 197108, 207605, 211178, 214583, 219409,
						214808, 225756, 216603, 238687, 234797, 248633, 248755,
						250185, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 1160152, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 817824, 59298685, 59279350, 61009369, 59635661, 62330832,
						60693212, 62602751, 62020896, 67822327, 67526820,
						71753372, 73809165, 75524305, 82691094, 84741642,
						82876683, 84414443, 85946560, 87663638, 90142449,
						90405030, 85946560, 119504220, 124782289, 128622866,
						128348405, 134780471, 140777455, 140011311, 136387819,
						146901593, 260309849, 254518132, 251075292 },
				{ 612618, 16245428, 16555321, 17059413, 17521326, 17765277,
						17838495, 17654060, 16980253, 16713948, 16616542,
						17951845, 16548147, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 3303298, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 132408547, 128863082, 0, 132050955,
						132609483, 0, 0, 0, 0, 0, 0, 0 },
				{ 233031, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 81820601,
						81274525, 81074402, 80357053, 81954710, 82465164,
						138667948, 133224309, 81954710, 133920777, 137049763,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 918918, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3522830, 0 },
				{ 2942690, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 6369088, 6699455, 7192199,
						7425397, 7881212, 8601252, 9146119, 9737907, 10383204 },
				{ 543253, 1820826, 1838435, 1851831, 2202915, 2202344, 2245679,
						2221684, 2282321, 2354021, 2424657, 2488630, 2644650,
						2738861, 2824161, 2870969, 2982311, 3096889, 3221972,
						4103360, 4170165, 4146734, 3221972, 4191159, 4462543,
						4667403, 5729321, 6162748, 6127660, 6509465, 6410125,
						6466920, 6124212, 5884617, 5374091 },
				{ 676656, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 445843, 0, 0, 0, 45882072, 47622833, 49714291, 50488503, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 36921668,
						37008195, 37642708, 37562880, 0, 0, 0, 0, 0, 0, 0 },
				{ 2631172, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 35301, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						91926883, 89692165, 96873356, 96295548, 97977953,
						89692165, 131321484, 134001964, 147472027, 138858742,
						276290703, 171227778, 142457847, 150464685, 160208203,
						155031966, 149611355, 157474300 },
				{ 675332, 103675077, 105158409, 0, 0, 118314981, 118745163,
						125027074, 124453567, 124303619, 125922072, 126288794,
						130780100, 136163072, 167395304, 170774237, 177231290,
						178281551, 181442168, 182529091, 182588156, 184810394,
						181442168, 171510822, 175107526, 174716429, 171500853,
						170007323, 185098787, 174236666, 170139951, 166171009,
						164340844, 160993399, 160508913 },
				{ 1017658, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 504713, 0, 0, 0, 0, 0, 0, 186464489, 189159050, 191605569,
						189736629, 192453403, 194436638, 197847178, 203477573,
						206667322, 208867410, 0, 0, 215893215, 217802326,
						219825070, 212553949, 225976029, 232759503, 237269315,
						242307928, 242596810, 261775591, 258526747, 260444694,
						259942982, 276376130, 277509284, 278464643 },
				{ 451965, 141221000, 147687000, 165861000, 183712000,
						196755000, 203468000, 224376000, 250474000, 347560000,
						364698000, 362973000, 366256000, 367427000, 364120000,
						380109000, 403258000, 415802000, 415859000, 400807000,
						398671000, 396847000, 415859000, 445446000, 467861000,
						486886000, 503327000, 514853000, 538958000, 552170000,
						539621000, 547690000, 608778000, 1065890000, 1073280000 },
				{ 2332808, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2505424, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	}

	public void setTop12andOthersTotalRMBSData() {
		this.top12andOthersTotalRMBSData = new double[][] {
				// Available for sale and held to maturity RMBS assets at fair
				// value
				{ 480228, 62417000.00, 71961000.00, 79413000.00, 59246000.00,
						67373000.00, 105768000.00, 55586667.00, 55441093.00,
						128936005.00, 136463907.00, 130617573.00, 161411535.00,
						179229154.00, 211911954.00, 205804232.00, 196202705.00,
						211482264.00, 206993344.00, 162670437.00, 163181138.00,
						155995188.00, 146611600.00, 150553289.00, 172204849.00,
						180927812.00, 206387041.00, 194446429.00, 212678849.00,
						160457510.00, 165480409.00, 152852807.00, 208078599.00,
						216097548.00, 221324043.00 },
				{ 541101, 5189515.00, 7995303.00, 9450470.00, 10220822.00,
						14765224.00, 16147729.00, 18494594.00, 18677223.00,
						19916491.00, 18709207.00, 18915456.00, 19323157.00,
						19737000.00, 20854000.00, 21893000.00, 22406000.00,
						22602000.00, 23326000.00, 17587000.00, 17709000.00,
						20808000.00, 24201000.00, 25648000.00, 25715000.00,
						24182000.00, 24327000.00, 32684000.00, 28257000.00,
						27351000.00, 29370000.00, 30411000.00, 29802000.00,
						29382000.00, 29408000.00 },
				{ 476810, 15754000.00, 16056000.00, 13403000.00, 16660000.00,
						17600000.00, 18283000.00, 14527000.00, 12722000.00,
						13235000.00, 11803000.00, 8059000.00, 7320000.00,
						7373000.00, 6322000.00, 5567000.00, 6596000.00,
						6089000.00, 7535000.00, 24829000.00, 90538000.00,
						115860000.00, 83594000.00, 71506000.00, 59864000.00,
						55350000.00, 54527000.00, 45852000.00, 52118000.00,
						53002000.00, 53082000.00, 51327000.00, 48913000.00,
						45380000.00, 43871000.00 },
				{ 214807, 64000.00, 63000.00, 42000.00, 42000.00, 42000.00,
						42000.00, 3000.00, 5000.00, 5000.00, 5000.00, 5000.00,
						4000.00, 4000.00, 4000.00, 4000.00, 83000.00, 0, 0, 0,
						0, 0, 0, 0, 198000.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2182786, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 413208, 9799199.00, 9794626.00, 11174038.00, 11634101.00,
						11321523.00, 12457355.00, 12751236.00, 12728288.00,
						12761394.00, 12711422.00, 14139122.00, 13858879.00,
						14585248.00, 14277309.00, 14087566.00, 16192690.00,
						16645508.00, 17351747.00, 17170498.00, 16695231.00,
						15961420.00, 17270486.00, 18608169.00, 18434512.00,
						21878687.00, 21256319.00, 19649106.00, 20977099.00,
						18289565.00, 12523366.00, 12458014.00, 11818167.00,
						14215101.00, 17857425.00 },
				{ 852218, 28540000.00, 25176000.00, 36323000.00, 44223000.00,
						45950000.00, 56016000.00, 37695000.00, 33426000.00,
						37838000.00, 36459000.00, 32638000.00, 47761000.00,
						40032000.00, 26406000.00, 29266000.00, 21183000.00,
						40267000.00, 56983000.00, 70534000.00, 75490000.00,
						77633000.00, 79109000.00, 75159000.00, 66698000.00,
						80958000.00, 85911000.00, 106561000.00, 130329000.00,
						182126000.00, 191136000.00, 195373000.00, 181517000.00,
						180145000.00, 170945000.00 },
				{ 280110, 0, 0, 0, 0, 0, 0, 0, 7067616.00, 6991904.00,
						6618196.00, 6674231.00, 6688372.00, 6613109.00,
						6665853.00, 6509280.00, 6521284.00, 6625566.00,
						6748707.00, 6848813.00, 7325893.00, 7385600.00,
						7417005.00, 7519645.00, 7552058.00, 8031867.00,
						7927487.00, 8002158.00, 8090288.00, 8205644.00,
						10122769.00, 15206533.00, 16434088.00, 16339487.00,
						19572832.00 },
				{ 1225800, 9155123.00, 7568341.00, 8005396.00, 7665822.00,
						6895924.00, 6298803.00, 5489300.00, 5409801.00,
						5198834.00, 6387575.00, 6358369.00, 7665633.00,
						6244030.00, 7364225.00, 5276028.00, 7138968.00,
						9281514.00, 10169469.00, 16095384.00, 18176764.00,
						18130438.00, 16776351.00, 18929195.00, 19222152.00,
						16031635.00, 14624329.00, 12945996.00, 2998115.00,
						1398181.00, 127021.00, 0, 0, 0, 0 },
				{ 1456501, 0, 0, 0, 90869.00, 85859.00, 92273.00, 95432.00,
						101568.00, 89994.00, 80816.00, 73728.00, 64778.00,
						59958.00, 55797.00, 12064.00, 11230.00, 13099.00,
						15312.00, 23397.00, 40090.00, 722000.00, 3020000.00,
						98000.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 484422, 35938140.00, 37756000.00, 47297000.00, 50792000.00,
						47406000.00, 46658000.00, 54495000.00, 67550000.00,
						71136000.00, 70406000.00, 70574000.00, 74611000.00,
						86154000.00, 87799000.00, 85752000.00, 88653000.00,
						91602000.00, 92105000.00, 79305000.00, 80547000.00,
						79267000.00, 77277000.00, 78643000.00, 79450000.00,
						79050000.00, 79068000.00, 71392000.00, 32829000.00,
						31464000.00, 57834000.00, 54834000.00, 51996000.00, 0,
						0 },
				{ 451965, 5604000.00, 5069000.00, 4148000.00, 3495000.00,
						3585000.00, 3220000.00, 11306000.00, 14311000.00,
						23364000.00, 27503000.00, 26052000.00, 24455000.00,
						21518000.00, 20279000.00, 23057000.00, 31892000.00,
						39516000.00, 59075000.00, 40394000.00, 30981000.00,
						34188000.00, 60056000.00, 41339000.00, 54344000.00,
						60560000.00, 67672000.00, 64368000.00, 60149000.00,
						88633000.00, 79447000.00, 56467000.00, 55413000.00,
						95034000.00, 87947000.00 },
				{ 9999999, 78188245.00, 78699001.00, 66081452.00, 83380480.00,
						127265065.00, 126046998.00, 149884608.00, 148437611.00,
						167273609.00, 139402866.00, 134045120.00, 143347305.00,
						146232922.00, 134985050.00, 145848948.00, 147629270.00,
						148267802.00, 145958105.00, 157222036.00, 168420602.00,
						159399210.00, 159670183.00, 172342585.00, 162721593.00,
						115617886.00, 156589961.00, 154233625.00, 142676463.00,
						137206966.00, 116842112.00, 126639368.00, 134683797.00,
						128549579.00, 131096913.00 }
						

		};

	}

	public void setTop12andOthersStructuredRMBSData() {
		this.top12andOthersStructuredRMBSData = new double[][] {
				// Available for sale and held to maturity RMBS assets at fair
				// value
				{ 480228, 28127000.00, 2786000.00, 2567000.00, 2421000.00,
						2255000.00, 2101000.00, 1603121.00, 520839.00,
						474407.00, 429960.00, 399683.00, 3754013.00, 419355.00,
						6442796.00, 5884923.00, 5416403.00, 4852353, 4517072,
						4384106, 4163527, 4084869, 3668935, 7439624,
						20100203.00, 23466854.00, 23104243.00, 18873573.00,
						15634451.00, 18413418.00, 28659456.00, 29972002.00,
						42410288.00, 64102253.00, 68339328.00 },
				{ 541101, 3014664.00, 5921203.00, 7051481.00, 8085306.00,
						12417303.00, 14024529.00, 15787085.00, 16204780.00,
						17187993.00, 16057695.00, 16372571.00, 16886052.00,
						17234000.00, 17837000.00, 18835000.00, 19380000.00,
						19705000, 20368000, 15606000, 15776000, 18928000,
						22421000, 23203000, 22987000.00, 21544000.00,
						20740000.00, 27804000.00, 22911000.00, 22162000.00,
						18264000.00, 17963000.00, 15225000.00, 15061000.00,
						14088000.00 },
				{ 476810, 87000.00, 6000.00, 7000.00, 7000.00, 6000.00,
						5000.00, 3000.00, 1000.00, 1000.00, 1000.00, 1000.00,
						1000.00, 1000.00, 1000.00, 0, 0, 0, 0, 0, 21428000,
						37534000, 37354000, 42157000, 39751000.00, 38208000.00,
						35701000.00, 29580000.00, 30249000.00, 27450000.00,
						28139000.00, 31830000.00, 31585000.00, 28049000.00,
						26562000.00 },
				{ 214807, 64000.00, 63000.00, 42000.00, 42000.00, 42000.00,
						42000.00, 3000.00, 5000.00, 5000.00, 5000.00, 5000.00,
						4000.00, 4000.00, 4000.00, 4000.00, 83000.00, 0, 0, 0,
						0, 0, 0, 0, 198000.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2182786, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 413208, 2057477.00, 3261566.00, 5021424.00, 5738797.00,
						6244676.00, 7562211.00, 7871695.00, 8376967.00,
						9117837.00, 8351834.00, 8906051.00, 9582108.00,
						9577936.00, 9481901.00, 9240711.00, 8849916.00,
						8415458, 8033767, 7998806, 7802096, 7938566, 8967311,
						9266279, 9197389.00, 10177222.00, 9983443.00,
						9544737.00, 10711385.00, 11114736.00, 7886844.00,
						7870960.00, 7415267.00, 7348786.00, 7026497.00 },
				{ 852218, 3018000.00, 3912000.00, 5793000.00, 4063000.00,
						3873000.00, 2466000.00, 1225000.00, 2257000.00,
						1611000.00, 186000.00, 161000.00, 2363000.00,
						2369000.00, 2843000.00, 2915000.00, 165000.00, 165000,
						2206000, 2991000, 162000, 130000, 121000, 600000,
						3573000.00, 7380000.00, 10168000.00, 19493000.00,
						19903000.00, 21796000.00, 24261000.00, 23831000.00,
						25030000.00, 35256000.00, 43891000.00 },
				{ 280110, 0, 0, 0, 0, 0, 0, 0, 6606254.00, 6577119.00,
						6255637.00, 6347175.00, 6371128.00, 6323225.00,
						6398356.00, 6266012.00, 6298623.00, 6420130, 6558858,
						6649901, 7001672, 6505123, 6313224, 6347496,
						6161537.00, 6516629.00, 6349786.00, 6467981.00,
						6523401.00, 6504828.00, 8523474.00, 13680842.00,
						15006355.00, 14984364.00, 18289774.00 },
				{ 1225800, 8973631.00, 7469169.00, 7925422.00, 7602371.00,
						6847368.00, 6193188.00, 5403594.00, 5323680.00,
						5104161.00, 6314087.00, 6299724.00, 7615616.00,
						6201836.00, 7277246.00, 5244211.00, 7109808.00,
						9280105, 10168329, 16094335, 18175870, 18129662,
						16775600, 18928458, 19221438.00, 16030928.00,
						14617412.00, 12939107.00, 2997478.00, 1297170.00,
						24798.00, 0, 0, 0, 0 },
				{ 1456501, 0, 0, 0, 88852.00, 83684.00, 90815.00, 93807.00, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 644000, 2941000,
						20000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 484422, 8521881.00, 6347000.00, 13210000.00, 16995000.00,
						15989000.00, 14895000.00, 19845000.00, 21132000.00,
						20649000.00, 20635000.00, 20770000.00, 14160000.00,
						13582000.00, 13763000.00, 14435000.00, 14894000.00,
						15730000, 15101000, 11106000, 11545000, 12142000,
						12802000, 16136000, 19575000.00, 22036000.00,
						21488000.00, 18483000.00, 15938000.00, 16374000.00,
						13955000.00, 14649000.00, 13817000.00, 0, 0 },
				{ 451965, 1017000.00, 726000.00, 879000.00, 834000.00,
						1290000.00, 1256000.00, 1182000.00, 2903000.00,
						4563000.00, 4290000.00, 4059000.00, 4522000.00,
						4238000.00, 4759000.00, 5725000.00, 7013000.00,
						5936000, 8296000, 5107000, 3948000, 3820000, 3633000,
						11954000, 19853000.00, 22472000.00, 22383000.00,
						21433000.00, 17362000.00, 19687000.00, 15060000.00,
						16034000.00, 14892000.00, 21225000.00, 21063000.00 },
				{ 9999999, 30174263.00, 28533088.00, 16835755.00, 21867596.00,
						45838445.00, 47241332.00, 49333967.00, 52958322.00,
						60204208.00, 47574034.00, 49569924.00, 55612718.00,
						55642039.00, 57919677.00, 59598728.00, 63185889.00,
						58188165, 59368202, 75966892, 81460321, 77749624,
						77965739, 86732574, 76814013.00, 46207813.00,
						76869884.00, 83599127.00, 67787307.00, 67046282.00,
						57582671.00, 72862629.00, 76183304.00, 73411862.00,
						77620367.00 } 
						
		};

	}

	public void setTop12andOthersCDSProtectionBoughtData() {
		this.top12andOthersCDSProtectionBoughtData = new double[][] {
				// Gross Notional CDS protection bought
				// value
				{480228,45219000.00,52118000.00,53853000.00,60420000.00,
					67438000.00,76882000.00,75005875.00,83226831.00,
					101546772.00,161633600.00,234375857.00,285549150.00,
					499276436.00,806027274.00,960807865.00,1226083745.00,
					373092267.00,452845428.00,547809414.00,704579880.00,
					959187215.00,1140222069.00,1441627241.00,1483958464.00,
					1509905197.00,1326855438.00,1224909731.00,1028649827.00,
					937776486.00,947036420.00,1008391084.00,1972633388.00,
					2394614343.00,2324991748.00 },
				{541101,304728.00,428485.00,539857.00,446916.00,
						653304.00,762342.00,813176.00,852948.00,
						770112.00,1006986.00,1031591.00,1184023.00,
						1196000.00,1274000.00,1261000.00,1099000.00,
						1023000.00,1353000.00,1508000.00,1655000.00,
						1751000.00,1794000.00,1849000.00,2089000.00,
						1884000.00,1514000.00,1260000.00,1175000.00,
						1090000.00,864000.00,834000.00,804000.00,
						814000.00,774000.00 },
				{476810,36075000.00,43746000.00,47782000.00,55714000.00,
							59003000.00,61573000.00,69649000.00,75959000.00,
							116862000.00,141976000.00,187799000.00,249851000.00,
							300825000.00,351857000.00,420661000.00,449122000.00,
							478678000.00,566365000.00,682480000.00,833701000.00,
							1055795000.00,1311309000.00,1582420000.00,1610324000.00,
							1728307000.00,1636972000.00,1495078000.00,1397546000.00,
							1367600000.00,1289612000.00,1295113000.00,1160557000.00,
							1192231000.00,1219929000.00 },
				{214807,189000.00,569000.00,502000.00,1983000.00,
								1462000.00,2479000.00,3140000.00,3739000.00,
								2156000.00,1744000.00,1753000.00,1429000.00,
								1229000.00,2453000.00,3111000.00,4755000.00,
								0.00,0.00,0.00,0.00,
								0.00,0.00,0.00,100000.00,
								100000.00,100000.00,100000.00,100000.00,
								75000.00,68000.00,68000.00,68000.00,
								68000.00,68000.00 },
				{2182786,0.00,0.00,0.00,0.00,
									0.00,0.00,0.00,0.00,
									0.00,0.00,0.00,0.00,
									0.00,0.00,0.00,0.00,
									0.00,0.00,0.00,0.00,
									0.00,0.00,190000.00,555000.00,
									1554305.00,3480729.00,4551750.00,718013000.00,
									661380186.00,490675000.00,434005000.00,374417000.00,
									235452000.00,210684000.00 },
				{413208,375000.00,1293000.00,3807962.00,5327532.00,
										7789622.00,10345122.00,12107642.00,14918569.00,
										22129111.00,30211151.00,43271443.00,60892250.00,
										86200169.00,133499827.00,171849686.00,169463285.00,
										214810750.00,280454552.00,341184549.00,375507520.00,
										409881725.00,471526748.00,531553879.00,586646669.00,
										634636670.00,584319845.00,542577133.00,457089844.00,
										441120746.00,397478910.00,387637324.00,366613338.00,
										371925465.00,364317502.00 },
				{852218,135672000.00,153139000.00,171707000.00,193056000.00,
											217173000.00,242389000.00,256427000.00,301738000.00,
											330055000.00,381959000.00,471605000.00,535548000.00,
											659395000.00,826454000.00,1050518000.00,1156800000.00,
											1437836000.00,1752736000.00,2111683000.00,2318540000.00,
											2811876000.00,3268460000.00,3920948000.00,4016580000.00,
											4119788000.00,3994756000.00,4649831000.00,4262320000.00,
											3810720000.00,3442183000.00,3202276000.00,3007303000.00,
											2797881000.00,2632094000.00 },
				{280110,0.00,0.00,0.00,0.00,
						0.00,0.00,0.00,40000.00,
						234000.00,1280000.00,2423500.00,2843000.00,
						2985000.00,3195000.00,3216000.00,3378166.00,
						3703706.00,3964705.00,4214705.00,4319705.00,
						4413706.00,4463706.00,4441706.00,4346627.00,
						4436195.00,4683720.00,4069364.00,3876800.00,
						3839836.00,3584841.00,2519927.00,2496491.00,
						2184133.00,2022235.00 },
				{1225800,889500.00,1564500.00,1883000.00,2068000.00,
						1881500.00,1961933.00,2607983.00,2966538.00,
						40000.00,3175029.00,3529921.00,4117814.00,
						4204441.00,4388799.00,4488435.00,4966903.00,
						5517878.00,5894095.00,6120236.00,6170762.00,
						6795564.00,7492890.00,8043700.00,8728016.00,
						9247743.00,9146086.00,0.00,0.00,
						0.00,8535654.00,2619.00,0.00,
						0.00,0.00 },
				{1456501,	0.00,0.00,443500.00,443500.00,613930.00,585100.00,541100.00,0.00,1400323.00,1903698.00,2025120.00,2236794.00,2705127.00,3472254.00,3729880.00,4091773.00,5863468.00,8628036.00,6836090.00,6738000.00,6281000.00,9132000.00,15322000.00,23478000.00,18997000.00,20401000.00,22058000.00,28200000.00,32776000.00,28604000.00,24606000.00,21878000.00,19582000.00 },
				{484422,2751842.00,4610000.00,6582000.00,7442000.00,9524000.00,12901000.00,17446000.00,20358000.00,23955000.00,29132000.00,38450000.00,51185000.00,64229000.00,75142000.00,98838000.00,115065000.00,120479000.00,91003000.00,129552000.00,160155000.00,160254000.00,170004000.00,155296000.00,179633000.00,220471000.00,188712000.00,161054000.00,150748000.00,159748000.00,122278000.00,140832000.00,90859000.00,0.00,0.00 },
				{451965,1077000.00,1112000.00,1215000.00,1272000.00,1237000.00,1385000.00,1442000.00,0.00,1652000.00,1646000.00,1691000.00,1761000.00,1751000.00,1649000.00,1691000.00,1696000.00,1768000.00,2019000.00,2074000.00,914000.00,1050000.00,1279000.00,1529000.00,1880000.00,1246000.00,1411000.00,1113000.00,1036000.00,1167000.00,1000000.00,1020000.00,865000.00,84841000.00,63058000.00},
				{9999999,11679540.00,12640367.00,14015522.00,21996605.00,23539735.00,26850397.00,23382328.00,24859805.00,37691482.00,25659875.00,29862792.00,23230471.00,14401818.00,4168638.00,4298214.00,4777849.00,4091833.00,4500869.00,5052067.00,5512842.00,5182171.00,6419384.00,6373303.00,8908061.00,8760572.00,8363727.00,14905668.00,13256027.00,12000545.00,2602162.00,2302353.00,2122444.00,1943216.00,1660481.00} 
						
		};

	}
	
	
	public void setTop12andOthersCDSProtectionSoldData() {
		this.top12andOthersCDSProtectionSoldData = new double[][] {
				// Gross Notional CDS protection sold
				// value
				{480228,20514000.00,26993000.00,30998000.00,36024000.00,40812000.00,47331000.00,48933172.00,55369909.00,66829707.00,104831444.00,154713601.00,215285063.00,358096975.00,523904491.00,655697353.00,808274050.00,399549522.00,491816735.00,585244397.00,745502945.00,1014171675.00,1200960239.00,1482301956.00,1522460858.00,1545768882.00,1344014677.00,1231267090.00,1004736144.00,912522462.00,930567036.00,992899433.00,1964463832.00,2403694143.00,2342950196.00 },
				{541101,1615212.00,1412628.00,1355748.00,1323400.00,1120242.00,1005009.00,845503.00,527380.00,527380.00,527380.00,442729.00,417400.00,417000.00,417000.00,529000.00,370000.00,179000.00,0.00,0.00,0.00,0.00,2000.00,2000.00,2000.00,2000.00,2000.00,2000.00,2000.00,2000.00,2000.00,2000.00,2000.00,2000.00,2000.00 },
				{476810,41083000.00,55326000.00,58878000.00,62704000.00,64113000.00,67703000.00,76327000.00,90144000.00,125133000.00,147404000.00,187567000.00,232543000.00,281584000.00,312764000.00,375618000.00,402199000.00,415098000.00,505877000.00,624825000.00,772315000.00,991254000.00,1140887000.00,1411020000.00,1505618000.00,1578005000.00,1527573000.00,1392546000.00,1290310000.00,1277791000.00,1226198000.00,1175270000.00,1089611000.00,1130024000.00,1146270000.00 },
				{2182786,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,640462000.00,589672186.00,433888000.00,403764000.00,339144000.00,216036000.00,189988000.00 },
				{413208,425989.00,1118989.00,3842141.00,5730411.00,8465821.00,11442321.00,13570218.00,16448998.00,27872542.00,38574192.00,54862908.00,75187432.00,101240555.00,152748753.00,206522601.00,222420239.00,265390525.00,324942253.00,384745555.00,424468407.00,461419181.00,521034600.00,579998976.00,638067652.00,676384976.00,623282704.00,576815849.00,473629328.00,445873554.00,398645617.00,392765734.00,372604526.00,381278448.00,368695762.00 },
				{852218,151413000.00,157786000.00,161965000.00,172994000.00,191623000.00,218450000.00,240134000.00,275955000.00,292791000.00,361649000.00,455090000.00,530612000.00,669772000.00,834368000.00,1044040000.00,1144264000.00,1435005000.00,1764236000.00,2115480000.00,2291867000.00,2819307000.00,3210473000.00,3798768000.00,3860565000.00,3970637000.00,3817140000.00,4478653000.00,4103539000.00,3661843000.00,3317959000.00,3121126000.00,2939911000.00,2761366000.00,2622841000.00 },
				{280110,0.00,0.00,0.00,0.00,0.00,0.00,0.00,20000.00,215000.00,1011000.00,2310000.00,2710000.00,2843000.00,2969000.00,3184000.00,3395706.00,3622206.00,3291705.00,3320206.00,3356205.00,3371206.00,3391206.00,3353206.00,3328206.00,3573206.00,3645206.00,3435839.00,3309302.00,3302269.00,3276349.00,2202435.00,1916952.00,1554310.00,1492287.00 },
				{1225800,0.00,0.00,25000.00,25000.00,25000.00,25000.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,9500.00,0.00,0.00,0.00 },
				{1456501,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,10918.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00 },
				{484422,1456842.00,5021000.00,6679000.00,5528000.00,5852000.00,7109000.00,12790000.00,15239000.00,22885000.00,30219000.00,39470000.00,46341000.00,57617000.00,61638000.00,80146000.00,96293000.00,92544000.00,81275000.00,125699000.00,148610000.00,155762000.00,164655000.00,172124000.00,188351000.00,201304000.00,178621000.00,150829000.00,141959000.00,146717000.00,114923000.00,136072000.00,85699000.00,0.00,0.00 },
				{451965,1054000.00,1156000.00,1372000.00,1348000.00,1340000.00,1422000.00,1447000.00,0.00,1637000.00,1504000.00,1537000.00,1542000.00,1596000.00,1586000.00,1587000.00,1618000.00,1681000.00,1760000.00,1719000.00,596000.00,686000.00,616000.00,833000.00,870000.00,832000.00,817000.00,607000.00,488000.00,465000.00,404000.00,362000.00,340000.00,76888000.00,62492000.00 },
				{9999999,4387708.00,5026403.00,5812826.00,5707630.00,6476220.00,9227451.00,11804935.00,17776047.00,27431441.00,19281263.00,24048297.00,22467934.00,13163730.00,2040913.00,2147396.00,2250421.00,1685973.00,2227325.00,2576851.00,2306908.00,2632985.00,3421907.00,3513220.00,3767316.00,3360990.00,3450607.00,3231555.00,2573696.00,1620714.00,1146305.00,1053957.00,1025772.00,971248.00,806842.00 }
						
		};

	}
	
	
	public void setTop12andOthersOutstandingNotionalSecuritisedData() {
		this.top12andOthersOutstandingNotionalSecuritisedData = new double[][] {
				// Gross Notional CDS protection sold
				// value
				{480228,57477000.00 ,59627000.00 ,60554000.00 ,48112000.00 ,44938000.00 ,44437000.00 ,42093096.00 ,42831345.00 ,43676882.00 ,46088735.00 ,47636709.00 ,52799037.00 ,57199117.00 ,60813122.00 ,64250000.00 ,66383438.00 ,66882005.00 ,66862121.00 ,68883627.00 ,73289128.00 ,77104730.00 ,76456614.00 ,65593516.00 ,63262306.00 ,62394493.00 ,66150087.00 ,64067276.00 ,62155582.00 ,59258195.00 ,60194805.00 ,56951326.00 ,54264112.00 ,68383093.00 ,66412575.00 },
				{541101,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 },
				{476810,72590000.00 ,78005000.00 ,79909000.00 ,77053000.00 ,2192000.00 ,2245000.00 ,2106000.00 ,2228000.00 ,2387000.00 ,2146000.00 ,2139000.00 ,2089000.00 ,1930000.00 ,1775000.00 ,1265000.00 ,1193000.00 ,1255000.00 ,1259000.00 ,1337000.00 ,258650000.00 ,579515000.00 ,507630000.00 ,502001000.00 ,511142000.00 ,518714000.00 ,527341000.00 ,535731000.00 ,538694000.00 ,526374000.00 ,516662000.00 ,506865000.00 ,490186000.00 ,476229000.00 ,463791000.00 },
				{214807,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 },
				{2182786,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,18251000.00 ,9719000.00 ,8561000.00 ,8113000.00 ,7640000.00 ,8254000.00 ,7805000.00 },
				{413208,23518466.00 ,25420448.00 ,25619125.00 ,25906245.00 ,27290504.00 ,29261411.00 ,30855345.00 ,30507649.00 ,3876384.00 ,3764764.00 ,3490497.00 ,3522844.00 ,3473923.00 ,3438099.00 ,3954543.00 ,4226720.00 ,4590191.00 ,4821969.00 ,5330178.00 ,5790842.00 ,6214878.00 ,6519542.00 ,6647151.00 ,6676066.00 ,6645477.00 ,6674691.00 ,6678670.00 ,6789044.00 ,6619273.00 ,6411050.00 ,6338507.00 ,6158048.00 ,6056342.00 ,5862300.00 },
				{852218,10811000.00 ,10506000.00 ,21970000.00 ,19459000.00 ,18362000.00 ,18894000.00 ,18954000.00 ,20623000.00 ,21214000.00 ,19925000.00 ,18148000.00 ,18434000.00 ,20151000.00 ,20305000.00 ,24157000.00 ,29668000.00 ,32991000.00 ,37031000.00 ,51823000.00 ,57652000.00 ,68541000.00 ,80501000.00 ,82971000.00 ,82467000.00 ,79590000.00 ,76091000.00 ,196093000.00 ,198500000.00 ,190459000.00 ,181700000.00 ,172478000.00 ,165260000.00 ,156522000.00 ,149728000.00 },
				{280110,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,130016.00 ,116352.00 ,90924.00 ,79982.00 ,53831.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 },
				{1225800,14578.00 ,14568.00 ,14559.00 ,14547.00 ,14258.00 ,14520.00 ,14507.00 ,14496.00 ,14483.00 ,14471.00 ,13668.00 ,13063.00 ,12378.00 ,11493.00 ,9999.00 ,8930.00 ,8215.00 ,7250.00 ,6337.00 ,5728.00 ,5294.00 ,4839.00 ,4430.00 ,4188.00 ,4037.00 ,3858.00 ,3721.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 },
				{1456501,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 ,0.00 },
				{484422,18095000.00 ,19876000.00 ,24991000.00 ,24185000.00 ,22654000.00 ,20412000.00 ,19623000.00 ,17676000.00 ,16717000.00 ,15019000.00 ,14337000.00 ,10705000.00 ,9845000.00 ,13236000.00 ,12481000.00 ,11262000.00 ,10412000.00 ,10028000.00 ,9474000.00 ,9584000.00 ,10218000.00 ,11817000.00 ,11065000.00 ,10374000.00 ,9361000.00 ,8635000.00 ,7884000.00 ,7714000.00 ,7514000.00 ,7282000.00 ,7036000.00 ,6758000.00 ,0.00 ,0.00 },
				{451965,127957000.00 ,136708000.00 ,144643000.00 ,134817000.00 ,139124000.00 ,149987000.00 ,152372000.00 ,161400000.00 ,164994000.00 ,173260000.00 ,193670000.00 ,207529000.00 ,222590000.00 ,251426000.00 ,278663000.00 ,314977000.00 ,164279000.00 ,180926000.00 ,193080000.00 ,200401000.00 ,203678000.00 ,213232000.00 ,219746000.00 ,228821000.00 ,232654000.00 ,237199000.00 ,247861000.00 ,259651000.00 ,265375000.00 ,271241000.00 ,284538000.00 ,295216000.00 ,293262000.00 ,308203000.00 },
				{9999999,38768529.00 ,38371984.00 ,2677429.00 ,2416902.00 ,45068849.00 ,47590276.00 ,49554515.00 ,53032529.00 ,55961491.00 ,56448835.00 ,59721327.00 ,55436883.00 ,58045257.00 ,62956866.00 ,66697041.00 ,71595878.00 ,74991289.00 ,79405425.00 ,80760979.00 ,89298616.00 ,98871589.00 ,102391727.00 ,103688393.00 ,111256822.00 ,117490772.00 ,122706613.00 ,123528080.00 ,126676170.00 ,128002636.00 ,133333082.00 ,141342237.00 ,142672268.00 ,140345581.00 ,136339632.00 }
						
		};

	}

	
	public void setTop12andOthersGenericData(){
		
		this.bankTop12andOthersGenericData = new double[][] {
				// Available for sale and held to maturity RMBS assets
				// at fair value
				{ 214807, 2986000, 36034000, 27687000, 7905000, 3000,
						0, 0, 7533000, 358000, 0, 0, 0 },
				{ 280110, 2861277, 88877085, 80789687, 6964810,
						6625566, 3622206, 3703706, 6797470, 4325163, 0,
						0, 27770 },
				{ 413208, 10065459, 158753889, 146651393, 12102159,
						16645508, 265390525, 214810750, 10035380,
						4716491, 4590191, 8685, 24429 },
				{ 451965, 15300000, 415802000, 379187000, 36565000,
						39516000, 1681000, 1768000, 25820000, 9525000,
						164279000, 0, 0 },
				{ 476810, 39859000, 749335000, 690787000, 58036000,
						6089000, 415098000, 478678000, 46480000,
						23065000, 418000, 4000, 1707000 },
				{ 480228, 37948038, 1104944125, 1000820651, 102443322,
						211482264, 399549522, 373092267, 70978395,
						16046636, 66467765, 447221, 4889324 },
				{ 484422, 13908000, 496566000, 443180000, 51585000,
						91602000, 92544000, 120479000, 31712000,
						14597000, 3313000, 3895000, 390000 },
				{ 541101, 9751000, 87750000, 79046000, 8553000,
						22602000, 179000, 1023000, 6243000, 2002000, 0,
						0, 0 },
				{ 852218, 46779000, 1093394000, 1004043000, 87395000,
						40267000, 1435005000, 1437836000, 62001000,
						23227000, 32991000, 620000, 3649000 },
				{ 2182786, 2106, 21985, 897, 21088, 0, 0, 0, 21140, 0,
						0, 0, 0 },
				{ 1225800, 433039, 62040397, 56281842, 5747595,
						9281514, 0, 5517878, 5769485, 657394, 8215, 0,
						60000 },
				{ 1456501, 21498, 10844942, 9152735, 1692207, 13099, 0,
						4091773, 1692207, 601191, 0, 0, 0 },
				{ 9999999, 101651062, 2002105927, 1798632805,
						196572553, 148267802, 3371914, 4091833,
						137468718, 49596687, 149653824, 2294844,
						3958125 } };
	}
	
	
	public void setAllBanksTotalRMBSData() {
		this.allBanksTotalRMBSData = new double[][] {
				// Available for sale and held to maturity RMBS assets at fair
				// value
				{ 231242, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 245333, 7483979.00, 7626318.00, 0, 0, 0, 0, 0, 0,
						11536955.00, 11282500.00, 11511837.00, 11556569.00,
						11532812.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 917742, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 2325493.00, 2325025.00, 2379903.00,
						4075053.00, 4298047.00, 4879634.00, 4655193.00,
						4881972.00, 0, 0 },
				{ 623454, 0, 0, 0, 0, 0, 0, 0, 0, 0, 108086.00, 99896.00,
						91016.00, 83608.00, 76574.00, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 101019, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 480228, 62417000.00, 71961000.00, 79413000.00, 59246000.00, //2002
						67373000.00, 105768000.00, 55586667.00, 55441093.00, //2003
						128936005.00, 136463907.00, 130617573.00, 161411535.00, //2004
						179229154.00, 211911954.00, 205804232.00, 196202705.00, //2005
						211482264.00, 206993344.00, 162670437.00, 163181138.00, //2006
						155995188.00, 146611600.00, 150553289.00, 172204849.00,
						180927812.00, 206387041.00, 194446429.00, 212678849.00,
						160457510.00, 165480409.00, 152852807.00, 208078599.00,
						216097548.00, 221324043.00 },
				{ 541101, 5189515.00, 7995303.00, 9450470.00, 10220822.00,
						14765224.00, 16147729.00, 18494594.00, 18677223.00,
						19916491.00, 18709207.00, 18915456.00, 19323157.00,
						19737000.00, 20854000.00, 21893000.00, 22406000.00,
						22602000.00, 23326000.00, 17587000.00, 17709000.00,
						20808000.00, 24201000.00, 25648000.00, 25715000.00,
						24182000.00, 24327000.00, 32684000.00, 28257000.00,
						27351000.00, 29370000.00, 30411000.00, 29802000.00,
						29382000.00, 29408000.00 },
				{ 968605, 0, 0, 0, 0, 0, 0, 143027.00, 142281.00, 102811.00,
						91604.00, 88308.00, 84208.00, 78091.00, 72484.00,
						68962.00, 62166.00, 57546.00, 81491.00, 124509.00,
						119370.00, 113078.00, 66887.00, 88325.00, 85912.00, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 173333, 22352000.00, 28707000.00, 28467000.00, 22912000.00,
						30531000.00, 33097000.00, 29122000.00, 30639000.00,
						32928000.00, 21674000.00, 14485000.00, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 214807, 64000.00, 63000.00, 42000.00, 42000.00, 42000.00,
						42000.00, 3000.00, 5000.00, 5000.00, 5000.00, 5000.00,
						4000.00, 4000.00, 4000.00, 4000.00, 83000.00, 0, 0, 0,
						0, 0, 0, 0, 198000.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 497404, 5032337.00, 5067727.00, 5115792.00, 4621309.00,
						5130517.00, 4371219.00, 4116912.00, 4268283.00,
						5131413.00, 5700617.00, 5135293.00, 5819290.00,
						4252779.00, 3782959.00, 4038631.00, 4040447.00,
						1424727.00, 1466155.00, 1530113.00, 1559872.00,
						1641652.00, 1826928.00, 1913458.00, 1945266.00,
						1888572.00, 11728148.00, 11239239.00, 10657331.00,
						9521418.00, 9098243.00, 10102444.00, 11248267.00,
						10824807.00, 11242657.00 },
				{ 852320, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						3268297.00, 9867966.00, 9949183.00, 10055180.00,
						10108784.00, 9939460.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 837260, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 897273, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 476810, 15754000.00, 16056000.00, 13403000.00, 16660000.00,
						17600000.00, 18283000.00, 14527000.00, 12722000.00,
						13235000.00, 11803000.00, 8059000.00, 7320000.00,
						7373000.00, 6322000.00, 5567000.00, 6596000.00,
						6089000.00, 7535000.00, 24829000.00, 90538000.00,
						115860000.00, 83594000.00, 71506000.00, 59864000.00,
						55350000.00, 54527000.00, 45852000.00, 52118000.00,
						53002000.00, 53082000.00, 51327000.00, 48913000.00,
						45380000.00, 43871000.00 },
				{ 62101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2574445.00,
						2828066.00, 2792247.00, 2971813.00, 2816816.00,
						2725744.00, 2413156.00, 2816498.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 586205, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 14409, 0, 0, 0, 0, 7618277.00, 7910035.00, 7940457.00,
						8363378.00, 7313807.00, 7508525.00, 7426833.00,
						7580265.00, 7645845.00, 7237165.00, 7146390.00,
						7091731.00, 7486524.00, 7243580.00, 7286099.00,
						5885818.00, 5658588.00, 5562323.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 3041974, 0, 0, 0, 7921869.00, 7523719.00, 7550316.00,
						6988652.00, 8155888.00, 7643677.00, 7252024.00,
						7049097.00, 7644955.00, 7851220.00, 7592776.00,
						8423397.00, 9060878.00, 9266200.00, 9129395.00,
						9371890.00, 7866024.00, 7690840.00, 7472261.00,
						7901818.00, 9442993.00, 0, 7315071.00, 7192783.00,
						6706082.00, 0, 0, 0, 0, 0, 0 },
				{ 856748, 0, 0, 0, 0, 92654.00, 83853.00, 86134.00, 88944.00,
						100860.00, 85414.00, 80414.00, 74959.00, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 60143, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						4001178.00, 4010684.00, 3730931.00, 3665160.00,
						3515627.00, 3833451.00, 4211440.00, 4773859.00,
						6166904.00, 8362036.00, 7984793.00, 7966726.00,
						7860606.00, 9578925.00, 6507615.00, 4727972.00,
						6261455.00, 6298692.00, 0 },
				{ 363415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 601050, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 1911096.00, 2331884.00,
						2915034.00, 0, 0, 0, 0, 0 },
				{ 83638, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						997047.00, 1113247.00, 1012191.00, 1108324.00,
						1563508.00, 1391062.00, 1386667.00, 1227784.00,
						1304908.00, 1428115.00, 1663112.00, 0, 0, 0, 0, 0 },
				{ 1444580, 13655.00, 15823.00, 22245.00, 29469.00, 39990.00, 0,
						43055.00, 39616.00, 49509.00, 43239.00, 47543.00,
						62030.00, 69235.00, 67748.00, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 723112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13956928.00,
						12705212.00, 12137840.00, 11780499.00, 11450099.00, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 913940, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5358713.00,
						7234410.00, 7338030.00, 6161172.00, 5967875.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 653648, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 633154, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 485559, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 484422, 35938140.00, 37756000.00, 47297000.00, 50792000.00,
						47406000.00, 46658000.00, 54495000.00, 67550000.00,
						71136000.00, 70406000.00, 70574000.00, 74611000.00,
						86154000.00, 87799000.00, 85752000.00, 88653000.00,
						91602000.00, 92105000.00, 79305000.00, 80547000.00,
						79267000.00, 77277000.00, 78643000.00, 79450000.00,
						79050000.00, 79068000.00, 71392000.00, 32829000.00,
						31464000.00, 57834000.00, 54834000.00, 51996000.00, 0,
						0 },
				{ 76201, 15679000.00, 16016000.00, 17648000.00, 21739000.00,
						26683000.00, 26226000.00, 24399000.00, 23815000.00,
						24005000.00, 15902077.00, 15384952.00, 14655640.00,
						13852442.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 2182786, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2897763, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 75633, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 470186.00, 457355.00,
						476245.00, 0, 570014.00, 558810.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 528849, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2747587, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100975.00,
						135999.00, 158613.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 413208, 9799199.00, 9794626.00, 11174038.00, 11634101.00,
						11321523.00, 12457355.00, 12751236.00, 12728288.00,
						12761394.00, 12711422.00, 14139122.00, 13858879.00,
						14585248.00, 14277309.00, 14087566.00, 16192690.00,
						16645508.00, 17351747.00, 17170498.00, 16695231.00,
						15961420.00, 17270486.00, 18608169.00, 18434512.00,
						21878687.00, 21256319.00, 19649106.00, 20977099.00,
						18289565.00, 12523366.00, 12458014.00, 11818167.00,
						14215101.00, 17857425.00 },
				{ 12311, 1516393.00, 1678611.00, 1895366.00, 1982803.00,
						2375669.00, 2175904.00, 2061718.00, 2207369.00,
						2353474.00, 2075626.00, 1591627.00, 1430773.00,
						1091732.00, 1563092.00, 1472162.00, 1672591.00,
						1876901.00, 1952884.00, 2112892.00, 1850015.00,
						1781264.00, 1894136.00, 2104119.00, 2355040.00,
						2227031.00, 2592777.00, 2659720.00, 2473518.00,
						2610077.00, 2655424.00, 4235078.00, 4074361.00,
						4062844.00, 5012792.00 },
				{ 3822016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 58243, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 852218, 28540000.00, 25176000.00, 36323000.00, 44223000.00,
						45950000.00, 56016000.00, 37695000.00, 33426000.00,
						37838000.00, 36459000.00, 32638000.00, 47761000.00,
						40032000.00, 26406000.00, 29266000.00, 21183000.00,
						40267000.00, 56983000.00, 70534000.00, 75490000.00,
						77633000.00, 79109000.00, 75159000.00, 66698000.00,
						80958000.00, 85911000.00, 106561000.00, 130329000.00,
						182126000.00, 191136000.00, 195373000.00, 181517000.00,
						180145000.00, 170945000.00 },
				{ 2980209, 0, 0, 0, 0, 0, 46382.00, 50630.00, 56116.00,
						52733.00, 38673.00, 30899.00, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 280110, 0, 0, 0, 0, 0, 0, 0, 7067616.00, 6991904.00,
						6618196.00, 6674231.00, 6688372.00, 6613109.00,
						6665853.00, 6509280.00, 6521284.00, 6625566.00,
						6748707.00, 6848813.00, 7325893.00, 7385600.00,
						7417005.00, 7519645.00, 7552058.00, 8031867.00,
						7927487.00, 8002158.00, 8090288.00, 8205644.00,
						10122769.00, 15206533.00, 16434088.00, 16339487.00,
						19572832.00 },
				{ 455534, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						12134142.00, 8330990.00, 10012257.00, 10303112.00,
						10714017.00, 10300819.00, 9547185.00, 9811015.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 934329, 7576866.00, 0, 0, 0, 10553960.00, 10395405.00,
						5344695.00, 5248271.00, 7150612.00, 6177424.00,
						7128711.00, 8204314.00, 8764007.00, 9509607.00,
						9799709.00, 10133975.00, 11170615.00, 11064689.00,
						11067726.00, 11067413.00, 11009791.00, 11302275.00,
						10621037.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 208927, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 838848.00, 1508218.00, 1368283.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 1225800, 9155123.00, 7568341.00, 8005396.00, 7665822.00,
						6895924.00, 6298803.00, 5489300.00, 5409801.00,
						5198834.00, 6387575.00, 6358369.00, 7665633.00,
						6244030.00, 7364225.00, 5276028.00, 7138968.00,
						9281514.00, 10169469.00, 16095384.00, 18176764.00,
						18130438.00, 16776351.00, 18929195.00, 19222152.00,
						16031635.00, 14624329.00, 12945996.00, 2998115.00,
						1398181.00, 127021.00, 0, 0, 0, 0 },
				{ 968436, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1456501, 0, 0, 0, 90869.00, 85859.00, 92273.00, 95432.00,
						101568.00, 89994.00, 80816.00, 73728.00, 64778.00,
						59958.00, 55797.00, 12064.00, 11230.00, 13099.00,
						15312.00, 23397.00, 40090.00, 722000.00, 3020000.00,
						98000.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 841472, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 259518, 1767785.00, 1710643.00, 1817764.00, 1544914.00,
						1314477.00, 963107.00, 1365810.00, 1465447.00,
						1631911.00, 1379625.00, 1470454.00, 1437206.00,
						3540269.00, 3049755.00, 2823495.00, 3040948.00,
						3094311.00, 3274269.00, 5579756.00, 4861431.00,
						4474699.00, 4831360.00, 5286163.00, 5159402.00,
						6262371.00, 7333411.00, 7261331.00, 11954815.00,
						12830967.00, 10154059.00, 8421693.00, 0, 0, 0 },
				{ 178020, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 447075.00,
						401506.00, 367197.00, 346282.00, 323582.00, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 210434, 10879.00, 9846.00, 8869.00, 11856.00, 10752.00,
						10040.00, 8966.00, 8432.00, 7835.00, 7027.00, 6756.00,
						6293.00, 5990.00, 5396.00, 4913.00, 24159.00, 33377.00,
						60297.00, 51070.00, 46544.00, 39731.00, 213540.00,
						593971.00, 646734.00, 584314.00, 777682.00, 901458.00,
						1374393.00, 1760911.00, 1914804.00, 2076085.00,
						2156030.00, 2916574.00, 2929973.00 },
				{ 245276, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1974282.00, 0,
						1949888.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 },
				{ 277820, 0, 0, 5859.00, 12769.00, 19802.00, 32680.00,
						27328.00, 26899.00, 28519.00, 24414.00, 24906.00,
						25052.00, 23163.00, 21477.00, 19466.00, 17914.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1160152, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 817824, 6115677.00, 6811115.00, 7887442.00, 8244217.00,
						10039439.00, 9707631.00, 8985217.00, 8318393.00,
						8646224.00, 8142780.00, 8934334.00, 9334174.00,
						11733290.00, 13803915.00, 14133484.00, 15050545.00,
						15773651.00, 16168018.00, 16511724.00, 19954824.00,
						19881193.00, 19446683.00, 24902517.00, 25595661.00,
						24653724.00, 25899185.00, 26349873.00, 24927323.00,
						24822717.00, 20754079.00, 24029771.00, 32658618.00,
						30750312.00, 29019889.00 },
				{ 612618, 2960238.00, 2913449.00, 3213115.00, 3200344.00,
						3490870.00, 3558349.00, 3648438.00, 3400628.00,
						3433514.00, 3252242.00, 2915534.00, 2646752.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 3303298, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 20000590.00, 18961629.00, 0,
						21288168.00, 20253973.00, 0, 0, 0, 0, 0, 0, 0 },
				{ 233031, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7336062.00,
						7329583.00, 7367642.00, 7495794.00, 7539998.00,
						7252009.00, 12729731.00, 12438379.00, 11711953.00,
						11340579.00, 11042161.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 918918, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 67463.00, 0 },
				{ 2942690, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 2602703.00, 2632572.00,
						2781575.00, 2829685.00, 2945071.00, 3343694.00,
						3584244.00, 3751396.00, 4357425.00 },
				{ 543253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 676656, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 445843, 0, 0, 0, 11159930.00, 11837922.00, 11338286.00,
						11323862.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2631172, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 35301, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						20252614.00, 19820815.00, 24025438.00, 23360521.00,
						25439935.00, 25156608.00, 30509822.00, 29593940.00,
						28010637.00, 25706292.00, 23087567.00, 21914959.00,
						20558282.00, 14530674.00, 23123346.00, 22359482.00,
						22334214.00, 27299098.00 },
				{ 675332, 7679436.00, 8142469.00, 0, 0, 10003017.00,
						8580791.00, 12375254.00, 12272208.00, 13043094.00,
						12969173.00, 13469707.00, 13482316.00, 13739153.00,
						18096912.00, 17493382.00, 17022125.00, 17286631.00,
						16743594.00, 16720043.00, 17130437.00, 8039893.00,
						9523623.00, 9649285.00, 9934580.00, 9835446.00,
						9674438.00, 9606535.00, 14853490.00, 14463379.00,
						14577941.00, 12641148.00, 16089216.00, 13898893.00,
						16431602.00 },
				{ 1017658, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 504713, 0, 0, 0, 0, 0, 0, 31853453.00, 39921458.00,
						42113661.00, 35687796.00, 36692833.00, 39438497.00,
						41553419.00, 40770926.00, 39337597.00, 37431433.00, 0,
						0, 34127430.00, 33626351.00, 32926783.00, 31290866.00,
						30984750.00, 30460849.00, 30081595.00, 30134484.00,
						29485941.00, 29337319.00, 29354412.00, 28824568.00,
						29282944.00, 31370152.00, 33644384.00, 34803477.00 },
				{ 451965, 5604000.00, 5069000.00, 4148000.00, 3495000.00,
						3585000.00, 3220000.00, 11306000.00, 14311000.00,
						23364000.00, 27503000.00, 26052000.00, 24455000.00,
						21518000.00, 20279000.00, 23057000.00, 31892000.00,
						39516000.00, 59075000.00, 40394000.00, 30981000.00,
						34188000.00, 60056000.00, 41339000.00, 54344000.00,
						60560000.00, 67672000.00, 64368000.00, 60149000.00,
						88633000.00, 79447000.00, 56467000.00, 55413000.00,
						95034000.00, 87947000.00 },
				{ 2332808, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2505424, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },


		};

	}

	public void setAllBanksStructuredRMBSData() {

		this.allBanksStructuredRMBSData = new double[][] {
				// Available for sale and held to maturity RMBS assets at fair
				// value
				{ 480228, 28127000.00, 2786000.00, 2567000.00, 2421000.00,
						2255000.00, 2101000.00, 1603121.00, 520839.00,
						474407.00, 429960.00, 399683.00, 3754013.00, 419355.00,
						6442796.00, 5884923.00, 5416403.00, 4852353.00,
						4517072.00, 4384106.00, 4163527.00, 4084869.00,
						3668935.00, 7439624.00, 20100203.00, 23466854.00,
						23104243.00, 18873573.00, 15634451.00, 18413418.00,
						28659456.00, 29972002.00, 42410288.00, 64102253.00,
						68339328.00 },
				{ 541101, 3014664.00, 5921203.00, 7051481.00, 8085306.00,
						12417303.00, 14024529.00, 15787085.00, 16204780.00,
						17187993.00, 16057695.00, 16372571.00, 16886052.00,
						17234000.00, 17837000.00, 18835000.00, 19380000.00,
						19705000.00, 20368000.00, 15606000.00, 15776000.00,
						18928000.00, 22421000.00, 23203000.00, 22987000.00,
						21544000.00, 20740000.00, 27804000.00, 22911000.00,
						22162000.00, 18264000.00, 17963000.00, 15225000.00,
						15061000.00, 14088000.00 },
				{ 476810, 87000.00, 6000.00, 7000.00, 7000.00, 6000.00,
						5000.00, 3000.00, 1000.00, 1000.00, 1000.00, 1000.00,
						1000.00, 1000.00, 1000.00, 0, 0, 0, 0, 0, 21428000.00,
						37534000.00, 37354000.00, 42157000.00, 39751000.00,
						38208000.00, 35701000.00, 29580000.00, 30249000.00,
						27450000.00, 28139000.00, 31830000.00, 31585000.00,
						28049000.00, 26562000.00 },
				{ 214807, 64000.00, 63000.00, 42000.00, 42000.00, 42000.00,
						42000.00, 3000.00, 5000.00, 5000.00, 5000.00, 5000.00,
						4000.00, 4000.00, 4000.00, 4000.00, 83000.00, 0, 0, 0,
						0, 0, 0, 0, 198000.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2182786, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 413208, 2057477.00, 3261566.00, 5021424.00, 5738797.00,
						6244676.00, 7562211.00, 7871695.00, 8376967.00,
						9117837.00, 8351834.00, 8906051.00, 9582108.00,
						9577936.00, 9481901.00, 9240711.00, 8849916.00,
						8415458.00, 8033767.00, 7998806.00, 7802096.00,
						7938566.00, 8967311.00, 9266279.00, 9197389.00,
						10177222.00, 9983443.00, 9544737.00, 10711385.00,
						11114736.00, 7886844.00, 7870960.00, 7415267.00,
						7348786.00, 7026497.00 },
				{ 852218, 3018000.00, 3912000.00, 5793000.00, 4063000.00,
						3873000.00, 2466000.00, 1225000.00, 2257000.00,
						1611000.00, 186000.00, 161000.00, 2363000.00,
						2369000.00, 2843000.00, 2915000.00, 165000.00,
						165000.00, 2206000.00, 2991000.00, 162000.00,
						130000.00, 121000.00, 600000.00, 3573000.00,
						7380000.00, 10168000.00, 19493000.00, 19903000.00,
						21796000.00, 24261000.00, 23831000.00, 25030000.00,
						35256000.00, 43891000.00 },
				{ 280110, 0, 0, 0, 0, 0, 0, 0, 6606254.00, 6577119.00,
						6255637.00, 6347175.00, 6371128.00, 6323225.00,
						6398356.00, 6266012.00, 6298623.00, 6420130.00,
						6558858.00, 6649901.00, 7001672.00, 6505123.00,
						6313224.00, 6347496.00, 6161537.00, 6516629.00,
						6349786.00, 6467981.00, 6523401.00, 6504828.00,
						8523474.00, 13680842.00, 15006355.00, 14984364.00,
						18289774.00 },
				{ 1225800, 8973631.00, 7469169.00, 7925422.00, 7602371.00,
						6847368.00, 6193188.00, 5403594.00, 5323680.00,
						5104161.00, 6314087.00, 6299724.00, 7615616.00,
						6201836.00, 7277246.00, 5244211.00, 7109808.00,
						9280105.00, 10168329.00, 16094335.00, 18175870.00,
						18129662.00, 16775600.00, 18928458.00, 19221438.00,
						16030928.00, 14617412.00, 12939107.00, 2997478.00,
						1297170.00, 24798.00, 0, 0, 0, 0 },
				{ 1456501, 0, 0, 0, 88852.00, 83684.00, 90815.00, 93807.00, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 644000.00,
						2941000.00, 20000.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 484422, 8521881.00, 6347000.00, 13210000.00, 16995000.00,
						15989000.00, 14895000.00, 19845000.00, 21132000.00,
						20649000.00, 20635000.00, 20770000.00, 14160000.00,
						13582000.00, 13763000.00, 14435000.00, 14894000.00,
						15730000.00, 15101000.00, 11106000.00, 11545000.00,
						12142000.00, 12802000.00, 16136000.00, 19575000.00,
						22036000.00, 21488000.00, 18483000.00, 15938000.00,
						16374000.00, 13955000.00, 14649000.00, 13817000.00, 0,
						0 },
				{ 451965, 1017000.00, 726000.00, 879000.00, 834000.00,
						1290000.00, 1256000.00, 1182000.00, 2903000.00,
						4563000.00, 4290000.00, 4059000.00, 4522000.00,
						4238000.00, 4759000.00, 5725000.00, 7013000.00,
						5936000.00, 8296000.00, 5107000.00, 3948000.00,
						3820000.00, 3633000.00, 11954000.00, 19853000.00,
						22472000.00, 22383000.00, 21433000.00, 17362000.00,
						19687000.00, 15060000.00, 16034000.00, 14892000.00,
						21225000.00, 21063000.00 },
				{ 231242, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 245333, 4093603.00, 3988337.00, 0, 0, 0, 0, 0, 0, 5360642.00,
						5151380.00, 5280557.00, 5195654.00, 5186443.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 917742, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 1318779.00, 1283924.00, 1282283.00,
						1763848.00, 2082413.00, 2108079.00, 2016257.00,
						2020825.00, 0, 0 },
				{ 623454, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1908.00, 1437.00, 1073.00,
						748.00, 446.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 101019, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 968605, 0, 0, 0, 0, 0, 0, 23.00, 9962.00, 40929.00, 39668.00,
						39364.00, 38392.00, 36140.00, 33726.00, 30587.00,
						27569.00, 24724.00, 22009.00, 19640.00, 17202.00,
						14807.00, 11868.00, 9835.00, 7699.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0 },
				{ 173333, 6444000.00, 7913000.00, 4518000.00, 2260000.00,
						959000.00, 989000.00, 923000.00, 1307000.00,
						1574000.00, 1595000.00, 1813000.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 497404, 933345.00, 862441.00, 968056.00, 822088.00,
						792699.00, 637291.00, 468081.00, 405026.00, 464372.00,
						394567.00, 554765.00, 687372.00, 322403.00, 303459.00,
						328572.00, 306178.00, 143369.00, 134694.00, 130132.00,
						120493.00, 114563.00, 107167.00, 102615.00, 99679.00,
						96755.00, 9691147.00, 9253220.00, 8790241.00,
						8700895.00, 8318973.00, 9338930.00, 9730236.00,
						9324571.00, 8823079.00 },
				{ 852320, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						1266850.00, 5305715.00, 5526235.00, 5684365.00,
						5800096.00, 5709228.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 837260, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 897273, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 62101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2052893.00,
						2144676.00, 2043730.00, 2179521.00, 2053683.00,
						1995755.00, 1979377.00, 2347210.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 586205, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 14409, 0, 0, 0, 0, 5024520.00, 5936732.00, 5529244.00,
						5172724.00, 4452147.00, 4358048.00, 4299184.00,
						4448594.00, 4514186.00, 4363394.00, 4323037.00,
						4273482.00, 4546564.00, 4270414.00, 4340497.00,
						3928325.00, 3760709.00, 3778697.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 3041974, 0, 0, 0, 5933913.00, 6055468.00, 6388910.00,
						4987076.00, 5236011.00, 5001461.00, 4663150.00,
						4609060.00, 5185318.00, 5517885.00, 5777090.00,
						5893589.00, 6322489.00, 6336722.00, 6184127.00,
						6415704.00, 5584209.00, 5490263.00, 5402754.00,
						5779344.00, 6478122.00, 0, 4939835.00, 5024285.00,
						4730994.00, 0, 0, 0, 0, 0, 0 },
				{ 856748, 0, 0, 0, 0, 9529.00, 5415.00, 2739.00, 1451.00,
						1255.00, 71.00, 24.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 60143, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						2636269.00, 2589655.00, 2392072.00, 2374488.00,
						2281684.00, 2174244.00, 2017931.00, 1924706.00,
						1830609.00, 1786416.00, 1641593.00, 1566009.00,
						1494212.00, 1414807.00, 1283318.00, 378701.00,
						332377.00, 281888.00, 0 },
				{ 363415, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 601050, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 1514841.00, 1736348.00,
						2092360.00, 0, 0, 0, 0, 0 },
				{ 83638, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						478857.00, 501129.00, 423709.00, 447807.00, 473131.00,
						373157.00, 278900.00, 247060.00, 213515.00, 306497.00,
						299952.00, 0, 0, 0, 0, 0 },
				{ 1444580, 6867.00, 13320.00, 16975.00, 19544.00, 24565.00, 0,
						26068.00, 20998.00, 25077.00, 22840.00, 28697.00,
						44750.00, 53577.00, 53074.00, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 723112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3509236.00,
						3267348.00, 3083178.00, 3038490.00, 2949970.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 913940, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3905063.00,
						4413972.00, 4373182.00, 3864804.00, 3700010.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 653648, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 633154, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 485559, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 76201, 5211000.00, 5514000.00, 5796000.00, 6469000.00,
						8407000.00, 8408000.00, 7160000.00, 6531000.00,
						6208000.00, 2524.00, 2280.00, 2054.00, 711.00, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2897763, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 75633, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33366.00,
						54723.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0 },
				{ 528849, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2747587, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 80212.00,
						90719.00, 103795.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0 },
				{ 12311, 658727.00, 633487.00, 758866.00, 777763.00,
						1113018.00, 1210201.00, 1253389.00, 1360314.00,
						1434815.00, 1296817.00, 1182742.00, 1089569.00,
						769554.00, 794020.00, 746482.00, 970111.00, 1206970.00,
						1268104.00, 1338993.00, 590062.00, 556342.00,
						723515.00, 701039.00, 783047.00, 729368.00, 1207013.00,
						1493322.00, 1324923.00, 1403582.00, 1809424.00,
						3135086.00, 3123992.00, 3200267.00, 3883437.00 },
				{ 3822016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 58243, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2980209, 0, 0, 0, 0, 0, 46382.00, 50630.00, 56116.00,
						52733.00, 38673.00, 30899.00, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 455534, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1004437.00,
						458441.00, 966841.00, 1197304.00, 1161508.00,
						951367.00, 680101.00, 611897.00, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0 },
				{ 934329, 3345900.00, 0, 0, 0, 8740911.00, 9209063.00,
						4587233.00, 4381408.00, 6097319.00, 5175427.00,
						6154694.00, 7305122.00, 7896975.00, 8670373.00,
						8995608.00, 9368929.00, 10414756.00, 10328815.00,
						10350939.00, 10368908.00, 10316439.00, 10710499.00,
						10014603.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 208927, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 525298.00, 264325.00, 241217.00, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0 },
				{ 968436, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 841472, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 259518, 237467.00, 223935.00, 407515.00, 318449.00,
						212753.00, 138749.00, 175039.00, 377380.00, 351176.00,
						296126.00, 408402.00, 475782.00, 2101385.00,
						1969164.00, 1814184.00, 1955599.00, 1858348.00,
						2085391.00, 2888933.00, 2463824.00, 2058185.00,
						2058630.00, 2256485.00, 2222695.00, 3030297.00,
						2877178.00, 2716101.00, 2324516.00, 2386516.00,
						2074461.00, 1560637.00, 0, 0, 0 },
				{ 178020, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 153187.00,
						135645.00, 124011.00, 117710.00, 111232.00, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 210434, 9397.00, 8418.00, 7501.00, 6366.00, 5287.00, 4580.00,
						4102.00, 3690.00, 3336.00, 2979.00, 2674.00, 2425.00,
						2175.00, 1931.00, 1717.00, 21226.00, 30662.00,
						57771.00, 42538.00, 38060.00, 31291.00, 205351.00,
						585889.00, 639079.00, 577177.00, 517594.00, 635634.00,
						552786.00, 703333.00, 869679.00, 1080583.00,
						1193753.00, 1973789.00, 2036962.00 },
				{ 245276, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 231068.00, 0,
						406294.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0 },
				{ 277820, 0, 0, 0, 0, 0, 103.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1160152, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 817824, 3097914.00, 2802765.00, 3475807.00, 3920042.00,
						5914772.00, 7017973.00, 6463107.00, 6738503.00,
						6939406.00, 5812319.00, 4571156.00, 4126876.00,
						3852358.00, 4219065.00, 4098475.00, 4186524.00,
						4614850.00, 4546861.00, 4147151.00, 4969730.00,
						6253422.00, 6386871.00, 7544232.00, 7905122.00,
						8276863.00, 8624352.00, 16399528.00, 14268476.00,
						15789887.00, 13141228.00, 16212586.00, 19744905.00,
						17197485.00, 17499955.00 },
				{ 612618, 1128245.00, 1000469.00, 887035.00, 612514.00,
						380870.00, 1011907.00, 2014587.00, 2144865.00,
						2343966.00, 2107076.00, 1959743.00, 1752555.00, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0 },
				{ 3303298, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 13252597.00, 12956306.00, 0,
						12344957.00, 11768606.00, 0, 0, 0, 0, 0, 0, 0 },
				{ 233031, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4013211.00,
						4136442.00, 3874389.00, 3658539.00, 3450505.00,
						3319867.00, 6726704.00, 6537374.00, 6204096.00,
						6057455.00, 5922089.00, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 918918, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2942690, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 1893655.00, 1899009.00,
						1909268.00, 1928032.00, 1946881.00, 2393762.00,
						2650053.00, 2921596.00, 3379844.00 },
				{ 543253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 676656, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 445843, 0, 0, 0, 727917.00, 958205.00, 701517.00, 333186.00,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2631172, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 35301, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						13928024.00, 15549963.00, 17797714.00, 17213032.00,
						18657529.00, 18580957.00, 19424388.00, 18893729.00,
						17712803.00, 17010881.00, 15707638.00, 14676864.00,
						14158993.00, 9526385.00, 20026583.00, 19883091.00,
						19601697.00, 22286104.00 },
				{ 675332, 5007798.00, 5572916.00, 0, 0, 7239848.00, 5535509.00,
						6491577.00, 6098772.00, 5859781.00, 5255120.00,
						5192032.00, 4670609.00, 3849924.00, 6121334.00,
						5310116.00, 5282929.00, 5706701.00, 5589419.00,
						5249202.00, 5251336.00, 343502.00, 343042.00,
						634909.00, 949557.00, 1037018.00, 995448.00, 955756.00,
						937084.00, 950549.00, 922083.00, 921491.00, 895998.00,
						902308.00, 946071.00 },
				{ 1017658, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 504713, 0, 0, 0, 0, 0, 0, 8864886.00, 13113102.00,
						13993793.00, 11360341.00, 13439214.00, 13172274.00,
						13856255.00, 13678563.00, 13699565.00, 14018275.00, 0,
						0, 12590196.00, 12627488.00, 12567207.00, 12101865.00,
						12171250.00, 12043895.00, 11363437.00, 13595247.00,
						13169380.00, 12971250.00, 15134963.00, 15582160.00,
						15798013.00, 16608074.00, 18008261.00, 18764915.00 },
				{ 2332808, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 2505424, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };

	}

	public void setLIBORRates() {
		this.liborRates2002Q1to2010Q2 = new double[] { 00.00, 0.02031, 0.0186,
				0.01806, 0.01383, 0.01288, 0.011164, 0.011598, 0.01157,
				0.011107, 0.016039, 0.020054, 0.025582, 0.030995, 0.035045,
				0.040551, 0.045298, 0.049898, 0.055085, 0.053725, 0.053601,
				0.053479, 0.053593, 0.054939, 0.049794, 0.027825, 0.027654,
				0.031217, 0.018294, 0.012667, 0.006207, 0.00347, 0.002566,
				0.002517, 0.0029563 };
	}

	public void setDataTimeSeriesTimeSpan() {

		this.dataTimeSereisTimeSpan = new String[] { "2002Q1", "2002Q2",
				"2002Q3", "2002Q4", "2003Q1", "2003Q2", "2003Q3", "2003Q4",
				"2004Q1", "2004Q2", "2004Q3", "2004Q4", "2005Q1", "2005Q2",
				"2005Q3", "2005Q4", " 2006Q1 ", " 2006Q2 ", " 2006Q3 ",
				" 2006Q4 ", " 2007Q1 ", " 2007Q2 ", " 2007Q3 ", "2007Q4",
				"2008Q1", "2008Q2", "2008Q3", "2008Q4", "2009Q1", "2009Q2",
				"2009Q3", "2009Q4", "2010Q1", "2010Q2" };

	}

	public void setAllBanksDatabaseTables() {
		// TODO Auto-generated method stub

		setDataTimeSeriesTimeSpan();
		setAllBanksTotalRMBSData();
		setAllBanksStructuredRMBSData();
		setTop12andOthersStructuredRMBSData();
		setTop12andOthersTotalRMBSData();
		setAllBanksTotalAssetsData();
		setAllBanksCDSSellData();
		setAllBanksCDSBuyData();
		setAllBanksBalancesheetCashData();

	}

	public void setModelRMBSType(boolean modelTypeRMBS) {
		// TODO Auto-generated method stub
		this.ModelRMBSType = modelTypeRMBS;

	}

	public void setCoreBanks(int coreBanks) {
		// TODO Auto-generated method stub
		this.coreBanks = coreBanks;
	}

	public double[][] getAllBanksTotalAssetsData() {
		return AllBanksTotalAssetsData;
	}

	public double[][] getAllBanksBalancesheetCashData() {
		return AllBanksBalancesheetCashData;
	}

	public double[][] getAllBanksCDSSellData() {
		return AllBanksCDSSellData;
	}

	public double[][] getTop12andOthersTotalRMBSData() {
		return top12andOthersTotalRMBSData;
	}

	public double[][] getAllBanksStructuredRMBSData() {
		return allBanksStructuredRMBSData;
	}

	public double[][] getTop12andOthersStructuredRMBSData() {
		return top12andOthersStructuredRMBSData;
	}

	public double[][] getAllBanksTotalRMBSData() {
		return allBanksTotalRMBSData;
	}

	public void setSpreadsAndBasisSeries(boolean defaultModel) {
		// TODO Auto-generated method stub
		if (defaultModel == true) {

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,,ADD THE SERIES
			// DATA>>>>>>>>>>>>>>>>>>>>>>>>>

			// CDS Spreads
			ABX_HE_AAA_Spread.add(new Quarter(1, 2006), 10);
			ABX_HE_AAA_Spread.add(new Quarter(2, 2006), 11.14);
			ABX_HE_AAA_Spread.add(new Quarter(3, 2006), 8.9);
			ABX_HE_AAA_Spread.add(new Quarter(4, 2006), 8.64);
			ABX_HE_AAA_Spread.add(new Quarter(1, 2007), 19);
			ABX_HE_AAA_Spread.add(new Quarter(2, 2007), 21.2);
			ABX_HE_AAA_Spread.add(new Quarter(3, 2007), 107);

			ABX_HE_AA_Spread.add(new Quarter(1, 2006), 22);
			ABX_HE_AA_Spread.add(new Quarter(2, 2006), 18.5);
			ABX_HE_AA_Spread.add(new Quarter(3, 2006), 14.23);
			ABX_HE_AA_Spread.add(new Quarter(4, 2006), 14.35);
			ABX_HE_AA_Spread.add(new Quarter(1, 2007), 34);
			ABX_HE_AA_Spread.add(new Quarter(2, 2007), 41.5);
			ABX_HE_AA_Spread.add(new Quarter(3, 2007), 330);

			ABX_HE_A_Spread.add(new Quarter(1, 2006), 45);
			ABX_HE_A_Spread.add(new Quarter(2, 2006), 46.98);
			ABX_HE_A_Spread.add(new Quarter(3, 2006), 50.91);
			ABX_HE_A_Spread.add(new Quarter(4, 2006), 58.63);
			ABX_HE_A_Spread.add(new Quarter(1, 2007), 278);
			ABX_HE_A_Spread.add(new Quarter(2, 2007), 632.4);
			ABX_HE_A_Spread.add(new Quarter(3, 2007), 1067);

			ABX_HE_BBB_Spread.add(new Quarter(1, 2006), 121);
			ABX_HE_BBB_Spread.add(new Quarter(2, 2006), 146.62);
			ABX_HE_BBB_Spread.add(new Quarter(3, 2006), 146.93);
			ABX_HE_BBB_Spread.add(new Quarter(4, 2006), 233.3);
			ABX_HE_BBB_Spread.add(new Quarter(1, 2007), 942);
			ABX_HE_BBB_Spread.add(new Quarter(2, 2007), 1929);
			ABX_HE_BBB_Spread.add(new Quarter(3, 2007), 2060);

			ABX_HE_BBB3_Spread.add(new Quarter(1, 2006), 233);
			ABX_HE_BBB3_Spread.add(new Quarter(2, 2006), 260.18);
			ABX_HE_BBB3_Spread.add(new Quarter(3, 2006), 272.06);
			ABX_HE_BBB3_Spread.add(new Quarter(4, 2006), 379.12);
			ABX_HE_BBB3_Spread.add(new Quarter(1, 2007), 1415);
			ABX_HE_BBB3_Spread.add(new Quarter(2, 2007), 2708.6);
			ABX_HE_BBB3_Spread.add(new Quarter(3, 2007), 2506);

			// ABX.HE Prices
			ABX_HE_AAA_Price.add(new Quarter(1, 2006), 100.31 * 100);
			ABX_HE_AAA_Price.add(new Quarter(2, 2006), 100.27 * 100);
			ABX_HE_AAA_Price.add(new Quarter(3, 2006), 100.09 * 100);
			ABX_HE_AAA_Price.add(new Quarter(4, 2006), 100.1 * 100);
			ABX_HE_AAA_Price.add(new Quarter(1, 2007), 99.5 * 100);
			ABX_HE_AAA_Price.add(new Quarter(2, 2007), 99.49 * 100);
			ABX_HE_AAA_Price.add(new Quarter(3, 2007), 95.05 * 100);

			ABX_HE_AA_Price.add(new Quarter(1, 2006), 100.36 * 100);
			ABX_HE_AA_Price.add(new Quarter(2, 2006), 100.46 * 100);
			ABX_HE_AA_Price.add(new Quarter(3, 2006), 100.1 * 100);
			ABX_HE_AA_Price.add(new Quarter(4, 2006), 100.09 * 100);
			ABX_HE_AA_Price.add(new Quarter(1, 2007), 99.25 * 100);
			ABX_HE_AA_Price.add(new Quarter(2, 2007), 99.13 * 100);
			ABX_HE_AA_Price.add(new Quarter(3, 2007), 88.36 * 100);

			ABX_HE_A_Price.add(new Quarter(1, 2006), 100.3 * 100);
			ABX_HE_A_Price.add(new Quarter(2, 2006), 100.22 * 100);
			ABX_HE_A_Price.add(new Quarter(3, 2006), 99.77 * 100);
			ABX_HE_A_Price.add(new Quarter(4, 2006), 99.54 * 100);
			ABX_HE_A_Price.add(new Quarter(1, 2007), 92.42 * 100);
			ABX_HE_A_Price.add(new Quarter(2, 2007), 84.27 * 100);
			ABX_HE_A_Price.add(new Quarter(3, 2007), 65.5 * 100);

			ABX_HE_BBB_Price.add(new Quarter(1, 2006), 101.06 * 100);
			ABX_HE_BBB_Price.add(new Quarter(2, 2006), 100.22 * 100);
			ABX_HE_BBB_Price.add(new Quarter(3, 2006), 99.56 * 100);
			ABX_HE_BBB_Price.add(new Quarter(4, 2006), 97.04 * 100);
			ABX_HE_BBB_Price.add(new Quarter(1, 2007), 75.62 * 100);
			ABX_HE_BBB_Price.add(new Quarter(2, 2007), 62.43 * 100);
			ABX_HE_BBB_Price.add(new Quarter(3, 2007), 44.55 * 100);

			ABX_HE_BBB3_Price.add(new Quarter(1, 2006), 101.26 * 100);
			ABX_HE_BBB3_Price.add(new Quarter(2, 2006), 100.2 * 100);
			ABX_HE_BBB3_Price.add(new Quarter(3, 2006), 99.08 * 100);
			ABX_HE_BBB3_Price.add(new Quarter(4, 2006), 96.09 * 100);
			ABX_HE_BBB3_Price.add(new Quarter(1, 2007), 66.62 * 100);
			ABX_HE_BBB3_Price.add(new Quarter(2, 2007), 54.54 * 100);
			ABX_HE_BBB3_Price.add(new Quarter(3, 2007), 41.79 * 100);

			// ABX.HE Trading Discount
			ABX_HE_AAA_Discount.add(new Quarter(1, 2006),
					(100 * 100) - 100.31 * 100);
			ABX_HE_AAA_Discount.add(new Quarter(2, 2006),
					(100 * 100) - 100.27 * 100);
			ABX_HE_AAA_Discount.add(new Quarter(3, 2006),
					(100 * 100) - 100.09 * 100);
			ABX_HE_AAA_Discount.add(new Quarter(4, 2006),
					(100 * 100) - 100.1 * 100);
			ABX_HE_AAA_Discount.add(new Quarter(1, 2007),
					(100 * 100) - 99.5 * 100);
			ABX_HE_AAA_Discount.add(new Quarter(2, 2007),
					(100 * 100) - 99.49 * 100);
			ABX_HE_AAA_Discount.add(new Quarter(3, 2007),
					(100 * 100) - 95.05 * 100);

			ABX_HE_AA_Discount.add(new Quarter(1, 2006),
					(100 * 100) - 100.36 * 100);
			ABX_HE_AA_Discount.add(new Quarter(2, 2006),
					(100 * 100) - 100.46 * 100);
			ABX_HE_AA_Discount.add(new Quarter(3, 2006),
					(100 * 100) - 100.1 * 100);
			ABX_HE_AA_Discount.add(new Quarter(4, 2006),
					(100 * 100) - 100.09 * 100);
			ABX_HE_AA_Discount.add(new Quarter(1, 2007),
					(100 * 100) - 99.25 * 100);
			ABX_HE_AA_Discount.add(new Quarter(2, 2007),
					(100 * 100) - 99.13 * 100);
			ABX_HE_AA_Discount.add(new Quarter(3, 2007),
					(100 * 100) - 88.36 * 100);

			ABX_HE_A_Discount.add(new Quarter(1, 2006),
					(100 * 100) - 100.3 * 100);
			ABX_HE_A_Discount.add(new Quarter(2, 2006),
					(100 * 100) - 100.22 * 100);
			ABX_HE_A_Discount.add(new Quarter(3, 2006),
					(100 * 100) - 99.77 * 100);
			ABX_HE_A_Discount.add(new Quarter(4, 2006),
					(100 * 100) - 99.54 * 100);
			ABX_HE_A_Discount.add(new Quarter(1, 2007),
					(100 * 100) - 92.42 * 100);
			ABX_HE_A_Discount.add(new Quarter(2, 2007),
					(100 * 100) - 84.27 * 100);
			ABX_HE_A_Discount.add(new Quarter(3, 2007),
					(100 * 100) - 65.5 * 100);

			ABX_HE_BBB_Discount.add(new Quarter(1, 2006),
					(100 * 100) - 101.06 * 100);
			ABX_HE_BBB_Discount.add(new Quarter(2, 2006),
					(100 * 100) - 100.22 * 100);
			ABX_HE_BBB_Discount.add(new Quarter(3, 2006),
					(100 * 100) - 99.56 * 100);
			ABX_HE_BBB_Discount.add(new Quarter(4, 2006),
					(100 * 100) - 97.04 * 100);
			ABX_HE_BBB_Discount.add(new Quarter(1, 2007),
					(100 * 100) - 75.62 * 100);
			ABX_HE_BBB_Discount.add(new Quarter(2, 2007),
					(100 * 100) - 62.43 * 100);
			ABX_HE_BBB_Discount.add(new Quarter(3, 2007),
					(100 * 100) - 44.55 * 100);

			ABX_HE_BBB3_Discount.add(new Quarter(1, 2006),
					(100 * 100) - 101.26 * 100);
			ABX_HE_BBB3_Discount.add(new Quarter(2, 2006),
					(100 * 100) - 100.2 * 100);
			ABX_HE_BBB3_Discount.add(new Quarter(3, 2006),
					(100 * 100) - 99.08 * 100);
			ABX_HE_BBB3_Discount.add(new Quarter(4, 2006),
					(100 * 100) - 96.09 * 100);
			ABX_HE_BBB3_Discount.add(new Quarter(1, 2007),
					(100 * 100) - 66.62 * 100);
			ABX_HE_BBB3_Discount.add(new Quarter(2, 2007),
					(100 * 100) - 54.54 * 100);
			ABX_HE_BBB3_Discount.add(new Quarter(3, 2007),
					(100 * 100) - 41.79 * 100);

			// Bond Spreads
			RMBS_CDO_AAA_Spread.add(new Quarter(1, 2006), 25.80);
			RMBS_CDO_AAA_Spread.add(new Quarter(2, 2006), 25.60);
			RMBS_CDO_AAA_Spread.add(new Quarter(3, 2006), 26.09);
			RMBS_CDO_AAA_Spread.add(new Quarter(4, 2006), 33.43);
			RMBS_CDO_AAA_Spread.add(new Quarter(1, 2007), 42.21);
			RMBS_CDO_AAA_Spread.add(new Quarter(2, 2007), 37.12);
			RMBS_CDO_AAA_Spread.add(new Quarter(3, 2007), 60.40);

			RMBS_CDO_AA_Spread.add(new Quarter(1, 2006), 59.90);
			RMBS_CDO_AA_Spread.add(new Quarter(2, 2006), 51.81);
			RMBS_CDO_AA_Spread.add(new Quarter(3, 2006), 52.70);
			RMBS_CDO_AA_Spread.add(new Quarter(4, 2006), 45.51);
			RMBS_CDO_AA_Spread.add(new Quarter(1, 2007), 78.52);
			RMBS_CDO_AA_Spread.add(new Quarter(2, 2007), 115.42);
			RMBS_CDO_AA_Spread.add(new Quarter(3, 2007), 184.03);

			RMBS_CDO_A_Spread.add(new Quarter(1, 2006), 118.52);
			RMBS_CDO_A_Spread.add(new Quarter(2, 2006), 133.13);
			RMBS_CDO_A_Spread.add(new Quarter(3, 2006), 145.20);
			RMBS_CDO_A_Spread.add(new Quarter(4, 2006), 149.65);
			RMBS_CDO_A_Spread.add(new Quarter(1, 2007), 241.58);
			RMBS_CDO_A_Spread.add(new Quarter(2, 2007), 324.93);
			RMBS_CDO_A_Spread.add(new Quarter(3, 2007), 462.13);

			RMBS_CDO_BBB_Spread.add(new Quarter(1, 2006), 351.05);
			RMBS_CDO_BBB_Spread.add(new Quarter(2, 2006), 303.48);
			RMBS_CDO_BBB_Spread.add(new Quarter(3, 2006), 231.74);
			RMBS_CDO_BBB_Spread.add(new Quarter(4, 2006), 319.52);
			RMBS_CDO_BBB_Spread.add(new Quarter(1, 2007), 522.79);
			RMBS_CDO_BBB_Spread.add(new Quarter(2, 2007), 602.07);
			RMBS_CDO_BBB_Spread.add(new Quarter(3, 2007), 697.01);

			RMBS_CDO_BBB3_Spread.add(new Quarter(1, 2006), 410.84);
			RMBS_CDO_BBB3_Spread.add(new Quarter(2, 2006), 335.00);
			RMBS_CDO_BBB3_Spread.add(new Quarter(3, 2006), 385.14);
			RMBS_CDO_BBB3_Spread.add(new Quarter(4, 2006), 481.08);
			RMBS_CDO_BBB3_Spread.add(new Quarter(1, 2007), 678.78);
			RMBS_CDO_BBB3_Spread.add(new Quarter(2, 2007), 633.17);
			RMBS_CDO_BBB3_Spread.add(new Quarter(3, 2007), 1007.29);

			// CDS Basis
			CDS_BASIS_AAA_Spread.add(new Quarter(1, 2006), -15.80);
			CDS_BASIS_AAA_Spread.add(new Quarter(2, 2006), -14.46);
			CDS_BASIS_AAA_Spread.add(new Quarter(3, 2006), -17.19);
			CDS_BASIS_AAA_Spread.add(new Quarter(4, 2006), -24.79);
			CDS_BASIS_AAA_Spread.add(new Quarter(1, 2007), -23.21);
			CDS_BASIS_AAA_Spread.add(new Quarter(2, 2007), -15.92);
			CDS_BASIS_AAA_Spread.add(new Quarter(3, 2007), 46.60);

			CDS_BASIS_AA_Spread.add(new Quarter(1, 2006), -37.90);
			CDS_BASIS_AA_Spread.add(new Quarter(2, 2006), -33.31);
			CDS_BASIS_AA_Spread.add(new Quarter(3, 2006), -38.47);
			CDS_BASIS_AA_Spread.add(new Quarter(4, 2006), -31.16);
			CDS_BASIS_AA_Spread.add(new Quarter(1, 2007), -44.52);
			CDS_BASIS_AA_Spread.add(new Quarter(2, 2007), -73.92);
			CDS_BASIS_AA_Spread.add(new Quarter(3, 2007), 145.97);

			CDS_BASIS_A_Spread.add(new Quarter(1, 2006), -73.52);
			CDS_BASIS_A_Spread.add(new Quarter(2, 2006), -86.15);
			CDS_BASIS_A_Spread.add(new Quarter(3, 2006), -94.29);
			CDS_BASIS_A_Spread.add(new Quarter(4, 2006), -91.02);
			CDS_BASIS_A_Spread.add(new Quarter(1, 2007), 36.42);
			CDS_BASIS_A_Spread.add(new Quarter(2, 2007), 307.47);
			CDS_BASIS_A_Spread.add(new Quarter(3, 2007), 604.87);

			CDS_BASIS_BBB_Spread.add(new Quarter(1, 2006), -230.05);
			CDS_BASIS_BBB_Spread.add(new Quarter(2, 2006), -156.86);
			CDS_BASIS_BBB_Spread.add(new Quarter(3, 2006), -84.81);
			CDS_BASIS_BBB_Spread.add(new Quarter(4, 2006), -86.22);
			CDS_BASIS_BBB_Spread.add(new Quarter(1, 2007), 419.21);
			CDS_BASIS_BBB_Spread.add(new Quarter(2, 2007), 1326.93);
			CDS_BASIS_BBB_Spread.add(new Quarter(3, 2007), 1362.99);

			CDS_BASIS_BBB3_Spread.add(new Quarter(1, 2006), -177.84);
			CDS_BASIS_BBB3_Spread.add(new Quarter(2, 2006), -74.82);
			CDS_BASIS_BBB3_Spread.add(new Quarter(3, 2006), -113.08);
			CDS_BASIS_BBB3_Spread.add(new Quarter(4, 2006), -101.96);
			CDS_BASIS_BBB3_Spread.add(new Quarter(1, 2007), 736.22);
			CDS_BASIS_BBB3_Spread.add(new Quarter(2, 2007), 2075.43);
			CDS_BASIS_BBB3_Spread.add(new Quarter(3, 2007), 1498.71);

		} else {

		}

	}// end setSpreadsAndBasisSeries() method

	public ArrayList<BaselIIBank> getBankList() {
		return this.bankList;
	}

	public ArrayList<double[][]> getCDSSpreadsList() {
		return this.cdsSpreadsList;
	}

	public ArrayList<double[]> getCashSpreadsList() {
		return this.cashSpreadsList;
	}

	public double[] getLibor() {
		return this.libor;
	}

	public TimeSeries getAggregateActualSeries() {
		return this.aggregatedActualExposuresSeries;
	}

	public TimeSeries getActualFDICBankOfAmericaAllMBSExposuresSeries() {
		return actualFDICBankOfAmericaAllMBSExposuresSeries;
	}

	public void setActualFDICBankOfAmericaAllMBSExposuresSeries(
			TimeSeries actualFDICBankOfAmericaAllMBSExposuresSeries) {
		this.actualFDICBankOfAmericaAllMBSExposuresSeries = actualFDICBankOfAmericaAllMBSExposuresSeries;
	}

	public TimeSeries getActualFDICBankOfAmericaStructuredMBSExposuresSeries() {
		return actualFDICBankOfAmericaStructuredMBSExposuresSeries;
	}

	public void setActualFDICBankOfAmericaStructuredMBSExposuresSeries(
			TimeSeries actualFDICBankOfAmericaStructuredMBSExposuresSeries) {
		this.actualFDICBankOfAmericaStructuredMBSExposuresSeries = actualFDICBankOfAmericaStructuredMBSExposuresSeries;
	}

	public TimeSeries getActualFDICJPMorganAllMBSExposuresSeries() {
		return actualFDICJPMorganAllMBSExposuresSeries;
	}

	public void setActualFDICJPMorganAllMBSExposuresSeries(
			TimeSeries actualFDICJPMorganAllMBSExposuresSeries) {
		this.actualFDICJPMorganAllMBSExposuresSeries = actualFDICJPMorganAllMBSExposuresSeries;
	}

	public TimeSeries getActualFDICJPMorganStructuredMBSExposuresSeries() {
		return actualFDICJPMorganStructuredMBSExposuresSeries;
	}

	//
	public TimeSeries getAggregatedActualWithouCitiBankExposuresSeries() {
		return aggregatedActualWithouCitiBankExposuresSeries;
	}

	public void setActualFDICJPMorganStructuredMBSExposuresSeries(
			TimeSeries actualFDICJPMorganStructuredMBSExposuresSeries) {
		this.actualFDICJPMorganStructuredMBSExposuresSeries = actualFDICJPMorganStructuredMBSExposuresSeries;
	}

	public TimeSeries getActualFDICCitiBankMBSExposuresSeries() {
		return actualFDICCitiBankMBSExposuresSeries;
	}

	public void setActualFDICCitiBankMBSExposuresSeries(
			TimeSeries actualFDICCitiBankMBSExposuresSeries) {
		this.actualFDICCitiBankMBSExposuresSeries = actualFDICCitiBankMBSExposuresSeries;
	}

	public TimeSeries getActualFDICMerrilLynchMBSExposuresSeries() {
		return actualFDICMerrilLynchMBSExposuresSeries;
	}

	public void setActualFDICMerrilLynchMBSExposuresSeries(
			TimeSeries actualFDICMerrilLynchMBSExposuresSeries) {
		this.actualFDICMerrilLynchMBSExposuresSeries = actualFDICMerrilLynchMBSExposuresSeries;
	}

	public TimeSeries getActualFDICAllOtherBanksMBSExposuresSeries() {
		return actualFDICAllOtherBanksMBSExposuresSeries;
	}

	public void setActualFDICAllOtherBanksMBSExposuresSeries(
			TimeSeries actualFDICAllOtherBanksMBSExposuresSeries) {
		this.actualFDICAllOtherBanksMBSExposuresSeries = actualFDICAllOtherBanksMBSExposuresSeries;
	}

	public TimeSeries getActualFDICHSBCMBSExposuresSeries() {
		return actualFDICHSBCMBSExposuresSeries;
	}

	public void setActualFDICHSBCMBSExposuresSeries(
			TimeSeries actualFDICHSBCMBSExposuresSeries) {
		this.actualFDICHSBCMBSExposuresSeries = actualFDICHSBCMBSExposuresSeries;
	}

	public TimeSeries getActualFDICKeyBankMBSExposuresSeries() {
		return actualFDICKeyBankMBSExposuresSeries;
	}

	public void setActualFDICKeyBankMBSExposuresSeries(
			TimeSeries actualFDICKeyBankMBSExposuresSeries) {
		this.actualFDICKeyBankMBSExposuresSeries = actualFDICKeyBankMBSExposuresSeries;
	}

	public TimeSeries getABX_HE_AAA_Spread() {
		return this.ABX_HE_AAA_Spread;
	}

	public TimeSeries getABX_HE_AA_Spread() {
		return this.ABX_HE_AA_Spread;
	}

	public TimeSeries getABX_HE_A_Spread() {
		return this.ABX_HE_A_Spread;
	}

	public TimeSeries getABX_HE_BBB_Spread() {
		return this.ABX_HE_BBB_Spread;
	}

	public TimeSeries getABX_HE_BBB3_Spread() {
		return this.ABX_HE_BBB3_Spread;
	}

	public TimeSeries getRMBS_CDO_AAA_Spread() {
		return this.RMBS_CDO_AAA_Spread;
	}

	public TimeSeries getRMBS_CDO_AA_Spread() {
		return this.RMBS_CDO_AA_Spread;
	}

	public TimeSeries getRMBS_CDO_A_Spread() {
		return this.RMBS_CDO_A_Spread;
	}

	public TimeSeries getRMBS_CDO_BBB_Spread() {
		return this.RMBS_CDO_BBB_Spread;
	}

	public TimeSeries getRMBS_CDO_BBB3_Spread() {
		return this.RMBS_CDO_BBB3_Spread;
	}

	public TimeSeries getCDS_BASIS_AAA_Spread() {
		return this.RMBS_CDO_AAA_Spread;
	}

	public TimeSeries getCDS_BASIS_AA_Spread() {
		return this.CDS_BASIS_AA_Spread;
	}

	public TimeSeries getCDS_BASIS_A_Spread() {
		return this.CDS_BASIS_A_Spread;
	}

	public TimeSeries getCDS_BASIS_BBB_Spread() {
		return this.CDS_BASIS_BBB_Spread;
	}

	public TimeSeries getCDS_BASIS_BBB3_Spread() {
		return this.CDS_BASIS_BBB3_Spread;
	}

	public TimeSeries getABX_HE_AAA_Price() {
		return this.ABX_HE_AAA_Price;
	}

	public TimeSeries getABX_HE_AA_Price() {
		return this.ABX_HE_AA_Price;
	}

	public TimeSeries getABX_HE_A_Price() {
		return this.ABX_HE_A_Price;
	}

	public TimeSeries getABX_HE_BBB_Price() {
		return this.ABX_HE_BBB_Price;
	}

	public TimeSeries getABX_HE_BBB3_Price() {
		return this.ABX_HE_BBB3_Price;
	}

	public TimeSeries getABX_HE_AAA_Discount() {
		return this.ABX_HE_AAA_Discount;
	}

	public TimeSeries getABX_HE_AA_Discount() {
		return this.ABX_HE_AA_Discount;
	}

	public TimeSeries getABX_HE_A_Discount() {
		return this.ABX_HE_A_Discount;
	}

	public TimeSeries getABX_HE_BBB_Discount() {
		return this.ABX_HE_BBB_Discount;
	}

	public TimeSeries getABX_HE_BBB3_Discount() {
		return this.ABX_HE_BBB3_Discount;
	}

	public TimeSeries getABX_HE_AAA_Coupon() {
		return this.ABX_HE_AAA_Coupon;
	}

	public TimeSeries getABX_HE_AA_Coupon() {
		return this.ABX_HE_AA_Coupon;
	}

	public TimeSeries getABX_HE_A_Coupon() {
		return this.ABX_HE_A_Coupon;
	}

	public TimeSeries getABX_HE_BBB_Coupon() {
		return this.ABX_HE_BBB_Coupon;
	}

	public TimeSeries getABX_HE_BBB3_Coupon() {
		return this.ABX_HE_BBB3_Coupon;
	}

}// end class defintion
