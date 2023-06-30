

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;



public class testAli {

	
	public static void main(String argsv[]) throws SQLException, IOException{

		//DataControllerMySQL dataControllerAli =new DataControllerMySQL();
		DataControllerMySQL mySQLDataBase =new DataControllerMySQL();
		ArrayList<String> labels = new ArrayList<String>();
		
		
//		String[] bankRSSIDList = {"917742", "480228", "541101", "968605", "476810", "60143","601050",
//				"214807","3278305","413208","852218","280110","83638","1225800","1456501","259518",
//				"210434","817824","3303298","233031","2942690","35301","675332","504713","484422","451965"};
		String[] bankRSSIDList = {"917742", "480228", "541101", "968605", "476810", "60143","601050",
				"214807","413208","852218","280110","83638","1225800","1456501","259518",
				"210434","3303298","233031","2942690","35301","675332","504713","484422","451965"};
		// out of this list are 3278305, "817824",

		ArrayList<BaselIIBank> bankList1 = new ArrayList<BaselIIBank>();

		//String table=dataControllerAli.getDatabaseTableName("q1_07_", "RCFD2221");
		//String value= dataControllerAli.getValue("65241", "q1_07_","RCFD1707");
		//System.out.println(value);
		//labels=dataControllerAli.getVariablesList();
		//System.out.println(labels);
		
		
//		dataControllerAli.getCDSRisValue("480228","q1_06_","ASSET");
//		dataControllerAli.getCDSRisValue("480228","q1_07_","SCMTGBK");
//		double CHBAL = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_06_","CHBAL");
//		double ASSET = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_06_","ASSET");
//		double LIAB = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_06_","LIAB");
//		double EQ = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_06_","EQ");
//		double CTDDFSWG = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_01_","CTDDFSWG");
//		double CTDDFSWB = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_01_","CTDDFSWB");
//		double RBCT1 = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_06_","RBCT1");
//		double RBCT2T = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_06_","RBCT2T");
//		double SZLNRES = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_01_","SZLNRES");
//		double SZLNRESHE = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_01_","SZLNRESHE");
//		double SZIORESTOT = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_01_","SZIORESTOT");
//		double SZIORESHETOT = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_01_","SZIORESHETOT");
//		double SCMTGBK = mySQLDataBase.getCDSRisValue(bankRSSIDList[2],"q1_01_","SCMTGBK");


		
//		System.out.println(CHBAL +" "+ ASSET +" "+ LIAB);


		for(int i= 0; i < bankRSSIDList.length; i++){
			
			//get bank definition values from database
			int RSSID = Integer.parseInt(bankRSSIDList[i]);

		double CHBAL = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","CHBAL");
		double ASSET = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","ASSET");
		double LIAB = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","LIAB");
		double EQ = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","EQ");
//		double SCMTGBK = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","SCMTGBK");
//		double CTDDFSWG = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","CTDDFSWG");
//		double CTDDFSWB = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","CTDDFSWB");
		double RBCT1 = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","RBCT1");
		double RBCT2T = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","RBCT2T");
//		double SZLNRES = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","SZLNRES");
//		double SZLNRESHE = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","SZLNRESHE");
//		double SZIORESTOT = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","SZIORESTOT");
//		double SZIORESHETOT = mySQLDataBase.getCDSRisValue(bankRSSIDList[i],"q1_01_","SZIORESHETOT");

		
		System.out.println(CHBAL +" "+ ASSET +" "+ LIAB);

		}


	}
}
