

import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;


public final class DataControllerMySQL {

	private static String FDICdatabaseName = "Ali";  //  @jve:decl-index=0:

	private static Connection getConnection(String databasebName) throws SQLException {
		String connectionUrl = null;
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connectionUrl = "jdbc:mysql://localhost:3306/" + databasebName + "?user=root";
			connection = (Connection) DriverManager.getConnection(connectionUrl);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public static final ArrayList<String> getTableList() {

		ArrayList<String> tablesList = new ArrayList<String>();

		tablesList.add("ffiec_cdr2009");
		tablesList.add("ffiec_cdr2008");
		tablesList.add("ffiec_cdr2007");
		tablesList.add("ffiec_cdr2006");
		tablesList.add("ffiec_cdr2005");
		tablesList.add("ffiec_cdr2004");
		tablesList.add("ffiec_cdr2003");
		tablesList.add("ffiec_cdr2002");
		tablesList.add("ffiec_cdr2001");


		return tablesList;
	}

	public static final ArrayList<String> getEndQuarterList() {

		ArrayList<String> tablesList = new ArrayList<String>();

		tablesList.add("12-31");
		tablesList.add("09-30");
		tablesList.add("06-30");
		tablesList.add("03-31");

		return tablesList;
	}




	public static final ArrayList<String> getBanksList() {

		ArrayList<String> banks = new ArrayList<String>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT Financial_Institution_Name ");
			query.append("FROM ffiec_cdr2009 ");
			//			query.append("WHERE available = 1 ");
			//	  query.append("ORDER BY index_rank ");
			query.append("ORDER BY Financial_Institution_Name ");

			Connection connection = getConnection(FDICdatabaseName);
			Statement statement = connection.createStatement();
			// System.out.println("query=" + query);
			ResultSet resultSet = statement.executeQuery(query.toString());
			while (resultSet.next()) {

				banks.add(resultSet.getString("Financial_Institution_Name"));
				//				System.out.println(resultSet.getString("Financial_Institution_Name"));
			}


			resultSet.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return banks;
	}

	public static final ArrayList<String> getVariablesList() {

		ArrayList<String> labels = new ArrayList<String>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("select LABEL ");
			query.append("FROM cds_glossary ");
			//			query.append("WHERE available = 1 ");
			//			query.append("ORDER BY columns ");
			//			query.append("ORDER BY Financial_Institution_Name ");

			Connection connection = getConnection(FDICdatabaseName);
			Statement statement = connection.createStatement();
			// System.out.println("query=" + query);
			ResultSet resultSet = statement.executeQuery(query.toString());

			while (resultSet.next()) {

				labels.add(resultSet.getString("LABEL"));

				//				System.out.println(resultSet.getString("Field"));

			}


			resultSet.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return labels;
	}

	public static final ArrayList<String> getPeriodEndDateList() {

		ArrayList<String> PeriodEndDates = new ArrayList<String>();
		Connection connection = null;
		ResultSet resultSet = null;
		Statement statement;

		try {


			for(int i = 0; i < getTableList().size(); i++)
			{
				StringBuffer query = new StringBuffer();

				query.append("SELECT DISTINCT Reporting_Period_End_Date ");
				query.append("FROM ");
				query.append(getTableList().get(i));
				//	  query.append("ORDER BY index_rank ");
				//			query.append("ORDER BY Financial_Institution_Name ");

				connection = getConnection(FDICdatabaseName);
				statement = connection.createStatement();
				// System.out.println("query=" + query);
				resultSet = statement.executeQuery(query.toString());
				while (resultSet.next()) {
					if(resultSet.getString("Reporting_Period_End_Date") != "0" && 
							resultSet.getString("Reporting_Period_End_Date") != " null")
					{
						PeriodEndDates.add(resultSet.getString("Reporting_Period_End_Date"));
						//					System.out.println(resultSet.getString("Reporting_Period_End_Date"));
					}
				}
				resultSet.close();
				connection.close();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return PeriodEndDates;
	}

//	public static final String getCDSRisValue(String IDRSSD, String periodEndDate, String risName) {
	public static final double getCDSRisValue(String IDRSSD, String periodEndDate, String risName) {

		double value=0;

		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT FRB_CODE");
			query.append(" FROM ");
			query.append("cds_glossary");
			query.append(" WHERE RIS__NAME =\"" + risName );
			query.append("\"");
			//	  query.append("ORDER BY index_rank ");
			//			query.append("ORDER BY Financial_Institution_Name ");
			//System.out.println(query.toString());
			Connection connection = getConnection(FDICdatabaseName);
			Statement statement = connection.createStatement();
			// System.out.println("query=" + query);
			ResultSet resultSet = statement.executeQuery(query.toString());
			resultSet.next();
			String result=resultSet.getString("FRB_CODE");

			String[] results=result.split("_");
			//System.out.println(results[3]);

			//check check /2********************
			int length=0;
			int i = 0;
			int k;
			if (risName.compareTo("SCMTGBK")==1 && (periodEndDate.compareTo("q1_09")==1) || 
					(periodEndDate.compareTo("q2_09")==1) || (periodEndDate.compareTo("q3_09")==1)){
				k=12;
				length=results.length;
			}
			else{
				k=0;
				if (risName.compareTo("SCMTGBK")==1){
					length=results.length/2;
					}
				else 
					length=results.length;
			}


			i+=k;
			while( i < length){
			//for (int i=0+k;i<length;i++){
				//if (getValue(IDRSSD,periodEndDate,results[i]).length()!=0){
				if ((getValue(IDRSSD,periodEndDate,results[i])!=null)
						&&
						(getValue(IDRSSD,periodEndDate,results[i]).length()!=0)){
					//System.out.println(results[i]);
					value += Double.parseDouble(getValue(IDRSSD,periodEndDate,results[i]));
				}
				//System.out.println(i);
				//System.out.println(value);
				i++;
			}
			//System.out.println(value);


			//value = resultSet.getString(field);
			//	System.out.println(resultSet.getString(field));

			resultSet.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		//return null;
		return value;
	}




	public static final String getValue(String IDRSSD, String periodEndDate, String field) {

		String tableName=getDatabaseTableName(periodEndDate, field);


		String value = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT " + field);
			query.append(" FROM ");
			query.append(tableName);
			query.append(" WHERE IDRSSD =" + IDRSSD );
			//	  query.append("ORDER BY index_rank ");
			//			query.append("ORDER BY Financial_Institution_Name ");

			Connection connection = getConnection(FDICdatabaseName);
			Statement statement = connection.createStatement();
			// System.out.println("query=" + query);
			ResultSet resultSet = statement.executeQuery(query.toString());

			resultSet.next();
			value = resultSet.getString(field);
			//System.out.println(resultSet.getString(field));

			resultSet.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return value;
	}



	// function will search for table name according to quarter year and field name
	public static final String getDatabaseTableName(String quarterYear,String field){
//		Ali's original code
//		String tableNames[]={"p1_rcr","p2_rcr", "rc", "rcl","rcb","rcs", "rcp"};
//		String tableNames2[]={"p1_rcr","p2_rcr", "rc", "rcl","p1_rcb","p2_rcb","rcs", "rcp"};
		String tableNames[]={"p1_rcr","p2_rcr", "rc", "rcl","rcb","rcs"};
		String tableNames2[]={"p1_rcr","p2_rcr", "rc", "rcl","p1_rcb","p2_rcb","rcs"};
		String tableNames3[]={"rc", "rcl","p1_rcb","p2_rcb","rcp", "p1_rcl","p2_rcl", "p1_rcr","p2_rcr", "rcs"};
		String value = null;
		if (quarterYear=="q3_09_"){
			tableNames=tableNames2;

		}
		if (quarterYear=="q1_10_"){
			tableNames=tableNames3;

		}
//		Ali's original code
//		for (int i=0;i<7;i++){
		for (int i=0;i<6;i++){



			try {
				StringBuffer query = new StringBuffer();
				query.append(" SHOW columnS From ");
				query.append(quarterYear);
				query.append(tableNames[i]);

				//System.out.println(query);

				Connection connection = getConnection(FDICdatabaseName);
				Statement statement = connection.createStatement();
				// System.out.println("query=" + query);
				ResultSet resultSet = statement.executeQuery(query.toString());



				//tableName1
				while (resultSet.next()){
					value = resultSet.getString("Field");
					if (value.compareTo(field)==0)
					{//System.out.println( tableNames[i]);
						return quarterYear+tableNames[i];
					}
					//System.out.println(value.toString());

				} 

			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}


	public static final String getDescription(String field) {

		String value = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT description ");
			query.append("FROM ffiec_dic ");
			query.append("WHERE code ='" + field + "\t'");
			//			System.out.println(query);

			Connection connection = getConnection(FDICdatabaseName);
			Statement statement = connection.createStatement();
			// System.out.println("query=" + query);
			ResultSet resultSet = statement.executeQuery(query.toString());

			resultSet.next();
			value = resultSet.getString("description");
			System.out.println(resultSet.getString("description"));

			resultSet.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return value;
	}

	public static final String getCode(String description) {

		String value = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT code ");
			query.append("FROM ffiec_dic ");
			query.append("WHERE description ='" + description +"'");
			//			System.out.println(query);

			Connection connection = getConnection(FDICdatabaseName);
			Statement statement = connection.createStatement();
			// System.out.println("query=" + query);
			ResultSet resultSet = statement.executeQuery(query.toString());

			resultSet.next();
			value = resultSet.getString("description");
			//System.out.println(resultSet.getString("description"));

			resultSet.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return value;
	}

}
