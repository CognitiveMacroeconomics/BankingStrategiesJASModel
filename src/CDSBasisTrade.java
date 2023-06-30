

import java.util.ArrayList;

public class CDSBasisTrade extends BasisTrading {

	public CDSBasisTrade() {
		super();

	}

	public static double[][] bankOpenNegativeBasisPos(int maximumEarningMethod,
			boolean CRM, double notional, double leverageInjection, double[] tranchingStructure,
			double[][] derivativeSpread, double[] cashSpread,
			double[] exposureRiskWeights, double counterPartyRiskWeight,
			double libor, double haircut) {

		/**
		 * Note that the credit ratings are defined as the columns and the
		 * spreads, price and coupons etc are the rows in all the arrays used in
		 * this method.
		 * 
		 * Also note that where the array being passed to this method contains
		 * asset class spreads, the row of all arrays, i.e. x[0][], should
		 * always contain the spreads of the asset classes.
		 */

		// Part 1 compute capital saving on each tranche of Bonds/Securities
		// held on balance sheet
		// assuming predefined CDS counter party credit rating and associated
		// regulatory capital
		// risk weight. (Note that in the case of an SPV under Basel II this
		// would be the collateral
		// credit rating and risk weighting)

		double[] trancheSaving;

		if (CRM == true) {
			trancheSaving = computeTrancheSavings(exposureRiskWeights,
					counterPartyRiskWeight);
		} else {
			trancheSaving = computeTrancheSavingsNoCRM(exposureRiskWeights,
					exposureRiskWeights);
		}

		// Part 2 compute daily funding gain or initial CDS Basis on each
		// tranche of securities/bonds
		// held on balance sheet assuming CDS protection was bought.
		double[][] basisnCoupon = computeBasisCoupons(cashSpread,
				derivativeSpread);

		// Part 3 compute the net regulatory capital saving from buying
		// protection against the
		// bond tranches held on the balance sheet.
		double[] netCapSaving = computeNetCapSaving(trancheSaving, basisnCoupon);

		// Part 4 collect the computed CDS-Basis, net capital saving and initial
		// coupons and
		// determing for each tranche if a negative basis trade is profitable.

		double[][] basisTradeReport = getBasisTradeReport(maximumEarningMethod,
				notional, leverageInjection, tranchingStructure, netCapSaving, trancheSaving,
				basisnCoupon, cashSpread, libor, haircut);

		return basisTradeReport;
	}

	public static double[][] bankOpenNegativeBasisPos(int maximumEarningMethod,
			boolean CRM, double notional, double leverageInjection, double[] tranchingStructure,
			double[][] derivativeSpread, double[] cashSpread,
			double[] exposureRiskWeights, double counterPartyRiskWeight[],
			double libor, double haircut) {

		/**
		 * Note that the credit ratings are defined as the columns and the
		 * spreads, price and coupons etc are the rows in all the arrays used in
		 * this method.
		 * 
		 * Also note that where the array being passed to this method contains
		 * asset class spreads, the row of all arrays, i.e. x[0][], should
		 * always contain the spreads of the asset classes.
		 */

		// Part 1 compute capital saving on each tranche of Bonds/Securities
		// held on balance sheet
		// assuming predefined CDS counter party credit rating and associated
		// regulatory capital
		// risk weight. (Note that in the case of an SPV under Basel II this
		// would be the collateral
		// credit rating and risk weighting)

		double[] trancheSaving;

		if (CRM == true) {
			trancheSaving = computeTrancheSavings(exposureRiskWeights,
					counterPartyRiskWeight);
		} else {
			trancheSaving = computeTrancheSavingsNoCRM(exposureRiskWeights,
					exposureRiskWeights);
		}

		// Part 2 compute daily funding gain or initial CDS Basis on each
		// tranche of securities/bonds
		// held on balance sheet assuming CDS protection was bought.
		double[][] basisnCoupon = computeBasisCoupons(cashSpread,
				derivativeSpread);

		// Part 3 compute the net regulatory capital saving from buying
		// protection against the
		// bond tranches held on the balance sheet.
		double[] netCapSaving = computeNetCapSaving(trancheSaving, basisnCoupon);

		// Part 4 collect the computed CDS-Basis, net capital saving and initial
		// coupons and
		// determing for each tranche if a negative basis trade is profitable.

		double[][] basisTradeReport = getBasisTradeReport(maximumEarningMethod,
				notional, leverageInjection, tranchingStructure, netCapSaving, trancheSaving,
				basisnCoupon, cashSpread, libor, haircut);

		return basisTradeReport;
	}

	public static double[][] bankCloseNegativeBasisPos(
			double[][] derivativeSpread, double[] cashSpread,
			double[][] basisTradeReport, double volatilityThreshold, boolean activeTrading) {

		/**
		 * Note that the credit ratings are defined as the columns and the
		 * spreads, price and coupons etc are the rows in all the arrays used in
		 * this method.
		 * 
		 * Also note that where the array being passed to this method contains
		 * asset class spreads, the row of all arrays, i.e. x[0][], should
		 * always contain the spreads of the asset classes.
		 */

		// Part 1 compute daily funding gain or initial CDS Basis on each
		// tranche of securities/bonds
		// held on balance sheet assuming CDS protection was bought.
		double[][] basisnCoupon = computeBasisCoupons(cashSpread,
				derivativeSpread);

		// Part 2 produce trade unwind report based on opening basis, current
		// basis and volatility threshold.
		double[][] basisTradeUnwindReport = getBasisTradeUnwindReport(
				basisTradeReport, basisnCoupon, volatilityThreshold, activeTrading);

		return basisTradeUnwindReport;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY
	// METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private static double[][] getBasisTradeUnwindReport(
			double[][] basisTradeReport, double[][] basisnCoupon,
			double volatilityThreshold, boolean activeTrading) {
		/**
		 * Calculates the trade gain from a negative basis trade at the point of
		 * unwind and determines if a negative basis trade should be closed or
		 * not based on some given risk aversion measure.
		 */

		double[][] btor = new double[4][basisnCoupon[0].length];

		for (int i = 0; i < btor[0].length; i++) {
			if(activeTrading == true){
			if ((basisTradeReport[13][i] == 1.0)
					&& (basisnCoupon[0][i] > basisTradeReport[0][i])
					&& (Math.abs(((basisnCoupon[0][i] - basisTradeReport[0][i]) / basisTradeReport[0][i])) > volatilityThreshold)) {
				// condition if a previous negative basis trade was made and the
				// new basis has widened
				// i.e. positive or negative with an absolute value smaller than
				// that of the old basis
				// i.e. the negative basis tends towards zero or +infinity
				btor[0][i] = basisnCoupon[0][i] - basisTradeReport[0][i];
				btor[1][i] = 100 * ((basisnCoupon[0][i] - basisTradeReport[0][i]) / Math
						.abs(basisTradeReport[0][i]));
				btor[2][i] = basisTradeReport[5][i];// notional to be unwound
				btor[3][i] = 1.0;
			} else {
				if ((basisTradeReport[10][i] == 1)
						&& (basisnCoupon[0][i] < basisTradeReport[0][i])
						&& (((basisnCoupon[0][i] - basisTradeReport[0][i]) / basisTradeReport[0][i]) > volatilityThreshold)) {
					// condition if a previous negative basis trade was made and
					// the new basis has narrowed
					// i.e. negative with an absolute value greater than that of
					// the old basis
					// i.e. the negative basis tends towards -infinity
					btor[0][i] = basisnCoupon[0][i] - basisTradeReport[0][i];
					btor[1][i] = 100
					* (basisnCoupon[0][i] - basisTradeReport[0][i])
					/ basisTradeReport[0][i];
					btor[2][i] = basisTradeReport[5][i];// notional to be
					// unwound
					btor[3][i] = 1.0;
				} else {
					btor[0][i] = basisnCoupon[0][i] - basisTradeReport[0][i];
					btor[1][i] = 100
					* (basisnCoupon[0][i] - basisTradeReport[0][i])
					/ basisTradeReport[0][i];
					btor[2][i] = 0.0;// notional to be unwound
					btor[3][i] = 0.0;
				}
			}
			}else{// unwinds happen only at recording a positive basis
				if ((basisTradeReport[13][i] == 1.0)
						&& (basisnCoupon[0][i] > basisTradeReport[0][i])
						&& (basisnCoupon[0][i] > 0)) {
					// condition if a previous negative basis trade was made and the
					// new basis has widened
					// i.e. positive or negative with an absolute value smaller than
					// that of the old basis
					// i.e. the negative basis tends towards zero or +infinity
					btor[0][i] = basisnCoupon[0][i] - basisTradeReport[0][i];
					btor[1][i] = 100 * ((basisnCoupon[0][i] - basisTradeReport[0][i]) / Math
							.abs(basisTradeReport[0][i]));
					btor[2][i] = basisTradeReport[5][i];// notional to be unwound
					btor[3][i] = 1.0;
				} else {
					if ((basisTradeReport[10][i] == 1)
							&& (basisnCoupon[0][i] < basisTradeReport[0][i])
							&& (basisnCoupon[0][i] > 0)) {
						// condition if a previous negative basis trade was made and
						// the new basis has narrowed
						// i.e. negative with an absolute value greater than that of
						// the old basis
						// i.e. the negative basis tends towards -infinity
						btor[0][i] = basisnCoupon[0][i] - basisTradeReport[0][i];
						btor[1][i] = 100
						* (basisnCoupon[0][i] - basisTradeReport[0][i])
						/ basisTradeReport[0][i];
						btor[2][i] = basisTradeReport[5][i];// notional to be
						// unwound
						btor[3][i] = 1.0;
					} else {
						btor[0][i] = basisnCoupon[0][i] - basisTradeReport[0][i];
						btor[1][i] = 100
						* (basisnCoupon[0][i] - basisTradeReport[0][i])
						/ basisTradeReport[0][i];
						btor[2][i] = 0.0;// notional to be unwound
						btor[3][i] = 0.0;
					}
				}
			}
		}
		return btor;
	}

	private static double[][] getBasisTradeReport(int maximumEarningMethod,
			double notional, double leverageInjection, double[] tranchingStructure,
			double[] netCapSaving, double[] trancheSaving,
			double[][] basisnCoupon, double[] cashSpread, double libor, double haircut) {
		/**
		 * this method produces the basis trade report strategy which the banks
		 * use to determine whether or not to under take a negative basis trade.
		 */
		double[][] btr = new double[14][basisnCoupon[0].length];
		double sum = 0;
		double sumTNS = 0;
		double sumTGC = 0;
		double notionalRemaining = (leverageInjection + notional);
		int negativeBasisTranchCount = 0;
		ArrayList<Double> numbers = new ArrayList<Double>();
		ArrayList<Double> numbersBasis = new ArrayList<Double>();
		ArrayList<Double> numbersSaving = new ArrayList<Double>();

		int maxIndex = 0;
		int maxIndexBasis = 0;
		int maxIndexSaving = 0;

		for (int i = 0; i < btr[0].length; i++) {
			btr[0][i] = basisnCoupon[0][i]; // add cds-basis to report
			numbersBasis.add((-1 * basisnCoupon[0][i]));
			btr[1][i] = basisnCoupon[1][i]; // add initial coupon to report
			btr[2][i] = trancheSaving[i]; // add regulatory capital saving
			btr[3][i] = netCapSaving[i]; // add net capital saving to report
			numbersSaving.add((netCapSaving[i]));
			btr[4][i] = (-1 * basisnCoupon[0][i]) + netCapSaving[i]; // add
			// cds-basis
			// to
			// report
			numbers.add(notional * (-1 * basisnCoupon[0][i]) + netCapSaving[i]);

		}// end of for-loop

		switch (maximumEarningMethod) {
		case 0: // in this case investor buys across all asset grades but only
			// earns basis income on those
			// tranches where a negative basis and positive net capital
			// saving is made.
			// i.e. not all cash investment earns a basis income.
			for (int i = 0; i < btr[0].length; i++) {
				btr[5][i] = (leverageInjection + notional) * tranchingStructure[i];// add face value
				// of MBS
				// represented
				// by each
				// tranche
				// to cds-basis report
				if (btr[4][i] > 0.0) {
					btr[6][i] = btr[5][i] * netCapSaving[i];// add per tranche
					// dollar amount of
					// capital savings
					// to cds-basis
					// report
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add per
					// tranche
					// dollar
					// amount
					// of
					// basis
					// income
					// to
					// cds-basis
					// report
					btr[8][i] = btr[5][i]
					                   * (libor + (-1.0 * basisnCoupon[0][i]));// add per
					// tranche
					// dollar
					// amount of
					// trade
					// income
					sum += btr[4][i]; // if there is a negative basis and the
					// net capital saving is positive
					// execute basis-trade;
					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
							* libor)
							/ (100 * haircut); // if the total gain from trading
					// is positive
					// execute basis-trade;
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][0] = sumTGC;
					btr[12][0] = sumTNS;
					btr[13][i] = 1.0; // if the total gain from trading is
					// positive
					// execute basis-trade;
				} else {
					btr[6][i] = btr[5][i] * 0;
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * 0;// add per tranche dollar amount
					// of basis income
					// to cds-basis report
					btr[8][i] = btr[5][i] * 0;// add per tranche dollar amount
					// of trade income
					// to cds-basis report
					btr[9][i] = 0; // if the total gain from trading is positive
					btr[10][i] = btr[5][i] * 0;
					sumTGC += btr[10][i];
					btr[11][0] = sumTGC;
					btr[12][0] = sumTNS;
					btr[13][i] = 0.0;
				}

			}// end for loop
			break;

		case 1:// in this case investor buys only in the asset grade with both
			// the largest basis income
			// and positive net capital saving combined.
			// i.e. all cash investment is in one tranche.
			// checks if the investor is seeking to invest in the tranche with
			// the largest basis
			// create a double varriable and set its value to the maximum total
			// basis income
			// double max = Math.max(numbers[0], Math.max(numbers[1],
			// Math.max(numbers[2], Math.max(numbers[3], numbers[4]))));

			// now loop through the tranches to see which of them contains the
			// maximum basis
			// and overwrite the initial trading strategy decisions.
			for (int i = 0; i < btr[4].length; i++) {
				if (getMaximumIndex(numbers) == i) {
					if (-1.0 * basisnCoupon[0][i] > 0) {
						btr[5][i] = (leverageInjection + notional); // set notional per tranche
						// portfolio weights according
						// to returns relative
						// to the total income obtained
						// from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						sumTNS += btr[6][i];
						btr[7][i] = btr[5][i] * (-1.0 * basisnCoupon[0][i]);// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// basis
						// income
						// to
						// cds-basis
						// report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to
						// cds-basis
						// report
						btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
								* libor)
								/ (100 * haircut); // if the total gain from
						// trading is positive
						btr[10][i] = btr[5][i] * btr[2][i];
						sumTGC += btr[10][i];
						btr[11][0] = sumTGC;
						btr[12][0] = sumTNS;
						btr[13][i] = 1.0;
					}

				} else {
					btr[5][i] = 0.0;
					btr[6][i] = btr[5][i] * netCapSaving[i];
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * (-1.0 * basisnCoupon[0][i]);// add
					// per
					// tranche
					// dollar
					// amount
					// of
					// basis
					// income
					// to
					// cds-basis
					// report
					btr[8][i] = btr[5][i]
					                   * (libor + (-1.0 * basisnCoupon[0][i]));// add per
					// tranche
					// dollar
					// amount of
					// trade
					// income
					// to
					// cds-basis
					// report
					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
							* libor)
							/ (100 * haircut); // if the total gain from trading
					// is positive
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][0] = sumTGC;
					btr[12][0] = sumTNS;
					btr[13][i] = 0.0;
				}// end of else
			}// end of for-loop

			break;

//		case 2:// in this case investor buys across all asset grades relative to
//			// how much trade income each tranche gives
//			// However like Case 0 only earns basis income on those
//			// tranches where a negative basis and positive net capital
//			// saving is made.
//			// i.e. not all cash investment earns a basis income.
//			for (int i = 0; i < btr[0].length; i++) {
//				for (int j = 0; j < btr[0].length; j++) {
//					if (btr[4][j] > 0.0) {
//						sum += btr[4][j]; // if there is a negative basis and
//						// the net capital saving is
//						// positive
//						// execute basis-trade;
//					}// end sumation if
//				}// end summation for
//				//divide notional investment across tranches with negative basis according to size of basis income earned
//				for (int j = 0; j < btr[0].length; j++) {
//					if (btr[4][j] > 0.0) {
//						btr[5][i] = (leverageInjection + notional) * (btr[4][i] / sum); // set notional
//						// per tranche
//						// portfolio
//						// weights
//						// according to
//						// returns
//						// relative
//						// to the total
//						// income
//						// obtained from
//						// trade and add
//						// to cds-basis
//						// report
//						notionalRemaining -= btr[5][i];
//						negativeBasisTranchCount++;
//					}// end sumation if
//				}// end summation for
//				
//				//divide the residue across positive basis tranches
//				for (int j = 0; j < btr[0].length; j++) {
//					if (btr[4][j] <= 0.0) {
//						btr[5][i] = notionalRemaining / (btr[0].length - negativeBasisTranchCount); // set notional
//					}
//				}
////				if (btr[4][i] >= 0.0) {
////					btr[5][i] = (leverageInjection + notional) * (btr[4][i] / sum); // set notional
////					// per tranche
////					// portfolio
////					// weights
////					// according to
////					// returns
////					// relative
////					// to the total
////					// income
////					// obtained from
////					// trade and add
////					// to cds-basis
////					// report
//					btr[6][i] = btr[5][i] * netCapSaving[i];// add per tranche
//					// dollar amount of
//					// capital savings
//					// to cds-basis
//					// report
//					sumTNS += btr[6][i];
//					btr[7][i] = btr[5][i] * (-1.0 * basisnCoupon[0][i]);// add
//					// per
//					// tranche
//					// dollar
//					// amount
//					// of
//					// basis
//					// income
//					// to
//					// cds-basis
//					// report
//					btr[8][i] = btr[5][i]
//					                   * (libor + (-1.0 * basisnCoupon[0][i]));// add per
//					// tranche
//					// dollar
//					// amount of
//					// trade
//					// income
//					// to
//					// cds-basis
//					// report
//					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
//							* libor)
//							/ (100 * haircut); // if the total gain from trading
//					// is positive
//					btr[10][i] = btr[5][i] * btr[2][i];
//					sumTGC += btr[10][i];
//					btr[11][0] = sumTGC;
//					btr[12][0] = sumTNS;
//					btr[13][i] = 1.0;
////				} else {
////					btr[5][i] = 0.0;
////					btr[6][i] = btr[5][i] * netCapSaving[i];
////					sumTNS += btr[6][i];
////					btr[7][i] = btr[5][i] * (-1.0 * basisnCoupon[0][i]); // do
////					// not
////					// execute
////					// basis
////					// trade.
////					btr[8][i] = btr[5][i]
////					                   * (libor + (-1.0 * basisnCoupon[0][i])); // do not
////					// execute
////					// basis
////					// trade.
////					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
////							* libor)
////							/ (100 * haircut); // do not execute basis trade.
////					btr[10][i] = btr[5][i] * btr[2][i];
////					sumTGC += btr[10][i];
////					btr[11][0] = sumTGC;
////					btr[12][0] = sumTNS;
////					btr[13][i] = 0.0; // if the total gain from trading in this
////					// tranche is the maximum
////				}// end else
//			}// end for
//
//			break;

		case 2: // in this case investor buys equally across all asset grades but only
			// earns basis income on those
			// tranches where a negative basis and positive net capital
			// saving is made.
			// i.e. not all cash investment earns a basis income.
			for (int i = 0; i < btr[0].length; i++) {
				btr[5][i] = (leverageInjection + notional) * BaselIICapital.equalTrancheStructure[i];// add face value
				// of MBS
				// represented
				// by each
				// tranche
				// to cds-basis report
				if (btr[4][i] > 0.0) {
					btr[6][i] = btr[5][i] * netCapSaving[i];// add per tranche
					// dollar amount of
					// capital savings
					// to cds-basis
					// report
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add per
					// tranche
					// dollar
					// amount
					// of
					// basis
					// income
					// to
					// cds-basis
					// report
					btr[8][i] = btr[5][i]
					                   * (libor + (-1.0 * basisnCoupon[0][i]));// add per
					// tranche
					// dollar
					// amount of
					// trade
					// income
					sum += btr[4][i]; // if there is a negative basis and the
					// net capital saving is positive
					// execute basis-trade;
					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
							* libor)
							/ (100 * haircut); // if the total gain from trading
					// is positive
					// execute basis-trade;
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][0] = sumTGC;
					btr[12][0] = sumTNS;
					btr[13][i] = 1.0; // if the total gain from trading is
					// positive
					// execute basis-trade;
				} else {
					btr[6][i] = btr[5][i] * 0;
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * 0;// add per tranche dollar amount
					// of basis income
					// to cds-basis report
					btr[8][i] = btr[5][i] * 0;// add per tranche dollar amount
					// of trade income
					// to cds-basis report
					btr[9][i] = 0; // if the total gain from trading is positive
					btr[10][i] = btr[5][i] * 0;
					sumTGC += btr[10][i];
					btr[11][0] = sumTGC;
					btr[12][0] = sumTNS;
					btr[13][i] = 0.0;
				}

			}// end for loop
			break;

		case 3:
			// now loop through the tranches to see which of them contains the
			// maximum basis
			// and overwrite the initial trading strategy decisions.
			// THis is slightly different from the case in 1 because here the
			// agent invests in the tranche that has maximum basis and
			// also the one that has maximum saving.
			double ext = 0;
			for (int i = 0; i < btr[4].length; i++) {

				if (i == 0) {

					btr[5][i] = notional * 0.7; // set notional per tranche
					// portfolio weights according
					// to returns relative
					// to the total income obtained from trade and add to
					// cds-basis report
					btr[6][i] = btr[5][i] * trancheSaving[i];// add per tranche
					// dollar amount of
					// capital savings
					// to cds-basis report
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add per
					// tranche
					// dollar
					// amount
					// of
					// basis
					// income
					// to cds-basis report
					btr[8][i] = btr[5][i]
					                   * (libor + (-1.0 * basisnCoupon[0][i]));// add per
					// tranche
					// dollar
					// amount of
					// trade
					// income
					// to cds-basis report
					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
							* libor)
							/ (100 * haircut); // if the total gain from trading
					// is positive
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][i] = sumTGC;
					btr[12][i] = sumTNS;

					btr[13][i] = 1.0; // if the total gain from trading in this
					// tranche is the maximum execute
					// basis-trade in this tranche;

				} else {
					if (getMaximumIndex(numbersBasis) == i) {
						btr[5][i] = (leverageInjection + notional) * 0.3; // set notional per tranche
						// portfolio weights
						// according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						sumTNS += btr[6][i];
						btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// basis
						// income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
								* libor)
								/ (100 * haircut); // if the total gain from
						// trading is positive
						btr[10][i] = btr[5][i] * btr[2][i];
						sumTGC += btr[10][i];
						btr[11][0] = sumTGC;
						btr[12][0] = sumTNS;

						btr[13][i] = 1.0; // if the total gain from trading in
						// this tranche is the maximum
						// execute basis-trade in this tranche;
						if ((-1.0 * basisnCoupon[0][i]) > netCapSaving[i]) {
							btr[9][i] = 0.0;
							btr[10][i] = 0.0;
							btr[11][i] = 0.0;
							btr[12][i] = 0.0;

							btr[13][i] = 0.0; // if the total gain from trading
							// in this tranche is the
							// maximum
							// execute basis-trade in this tranche;
						}

					} else {
						btr[5][i] = 0.0; // set notional per tranche portfolio
						// weights according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						btr[7][i] = 0.0;// add per tranche dollar amount of
						// basis income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = 0.0;
						btr[10][i] = 0.0;
						btr[11][i] = 0.0;
						btr[12][i] = 0.0;

						btr[13][i] = 0.0; // do not execute basis trade.
					}// end of else
				}
			}// end of for-loop

			break;

		case 4:
			// now loop through the tranches to see which of them contains the
			// maximum basis
			// and overwrite the initial trading strategy decisions.
			// THis is slightly different from the case in 1 because here the
			// agent invests in the tranche that has maximum basis and
			// also the one that has maximum saving. NO SAVINGS ON SENIOR
			// TRANCHE
			double ext1 = 0;
			for (int i = 0; i < btr[4].length; i++) {

				if (i == 0) {

					btr[5][i] = notional * 0.7; // set notional per tranche
					// portfolio weights according
					// to returns relative
					// to the total income obtained from trade and add to
					// cds-basis report
					btr[6][i] = btr[5][i] * trancheSaving[i];// add per tranche
					// dollar amount of
					// capital savings
					// to cds-basis report
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * -1.0 * 0;// add per tranche dollar
					// amount of basis
					// income
					// to cds-basis report
					btr[8][i] = btr[5][i]
					                   * (libor + (-1.0 * basisnCoupon[0][i])) * 0;// add
					// per
					// tranche
					// dollar
					// amount
					// of
					// trade
					// income
					// to cds-basis report
					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
							* libor)
							/ (100 * haircut); // if the total gain from trading
					// is positive
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][i] = sumTGC;
					btr[12][i] = sumTNS;

					btr[13][i] = 1.0; // if the total gain from trading in this
					// tranche is the maximum execute
					// basis-trade in this tranche;

				} else {
					if (getMaximumIndex(numbersBasis) == i) {
						btr[5][i] = (leverageInjection + notional) * 0.3; // set notional per tranche
						// portfolio weights
						// according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						sumTNS += btr[6][i];
						btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// basis
						// income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
								* libor)
								/ (100 * haircut); // if the total gain from
						// trading is positive
						btr[10][i] = btr[5][i] * btr[2][i];
						sumTGC += btr[10][i];
						btr[11][0] = sumTGC;
						btr[12][0] = sumTNS;

						btr[13][i] = 1.0; // if the total gain from trading in
						// this tranche is the maximum
						// execute basis-trade in this tranche;
						if (-1.0 * basisnCoupon[0][i] < 0) {
							btr[9][i] = 0.0;
							btr[10][i] = 0.0;
							btr[11][i] = 0.0;
							btr[12][i] = 0.0;

							btr[13][i] = 0.0; // if the total gain from trading
							// in this tranche is the
							// maximum
							// execute basis-trade in this tranche;
						}

					} else {
						btr[5][i] = 0.0; // set notional per tranche portfolio
						// weights according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						btr[7][i] = 0.0;// add per tranche dollar amount of
						// basis income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = 0.0;
						btr[10][i] = 0.0;
						btr[11][i] = 0.0;
						btr[12][i] = 0.0;

						btr[13][i] = 0.0; // do not execute basis trade.
					}// end of else
				}
			}// end of for-loop

			break;

		case 5:
			// now loop through the tranches to see which of them contains the
			// maximum basis
			// and overwrite the initial trading strategy decisions.
			// THis is slightly different from the case in 1 because here the
			// agent invests in the tranche that has maximum basis and
			// also the one that has maximum saving. NO SAVINGS ON SENIOR
			// TRANCHE
			double ext2 = 0;
			for (int i = 0; i < btr[4].length; i++) {

				if (i == 0) {

					btr[5][i] = notional * 0.9; // set notional per tranche
					// portfolio weights according
					// to returns relative
					// to the total income obtained from trade and add to
					// cds-basis report
					btr[6][i] = btr[5][i] * trancheSaving[i];// add per tranche
					// dollar amount of
					// capital savings
					// to cds-basis report
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * -1.0 * 0;// add per tranche dollar
					// amount of basis
					// income
					// to cds-basis report
					btr[8][i] = btr[5][i]
					                   * (libor + (-1.0 * basisnCoupon[0][i])) * 0;// add
					// per
					// tranche
					// dollar
					// amount
					// of
					// trade
					// income
					// to cds-basis report
					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
							* libor)
							/ (100 * haircut); // if the total gain from trading
					// is positive
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][i] = sumTGC;
					btr[12][i] = sumTNS;

					btr[13][i] = 1.0; // if the total gain from trading in this
					// tranche is the maximum execute
					// basis-trade in this tranche;

				} else {
					if (getMaximumIndex(numbersBasis) == i) {
						btr[5][i] = (leverageInjection + notional) * 0.1; // set notional per tranche
						// portfolio weights
						// according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						sumTNS += btr[6][i];
						btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// basis
						// income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
								* libor)
								/ (100 * haircut); // if the total gain from
						// trading is positive
						btr[10][i] = btr[5][i] * btr[2][i];
						sumTGC += btr[10][i];
						btr[11][0] = sumTGC;
						btr[12][0] = sumTNS;

						btr[13][i] = 1.0; // if the total gain from trading in
						// this tranche is the maximum
						// execute basis-trade in this tranche;
						if ((-1.0 * basisnCoupon[0][i]) > netCapSaving[i]) {
							btr[9][i] = 0.0;
							btr[10][i] = 0.0;
							btr[11][i] = 0.0;
							btr[12][i] = 0.0;

							btr[13][i] = 0.0; // if the total gain from trading
							// in this tranche is the
							// maximum
							// execute basis-trade in this tranche;
						}

					} else {
						btr[5][i] = 0.0; // set notional per tranche portfolio
						// weights according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						btr[7][i] = 0.0;// add per tranche dollar amount of
						// basis income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = 0.0;
						btr[10][i] = 0.0;
						btr[11][i] = 0.0;
						btr[12][i] = 0.0;

						btr[13][i] = 0.0; // do not execute basis trade.
					}// end of else
				}
			}// end of for-loop

			// The following bit of code is to chack if there are any further
			// tranches on which a basis trade can be made to
			// support the sinor AAA tranche. THis is required under Basel
			// rules.

			break;
		case 6:
			// now loop through the tranches to see which of them contains the
			// maximum basis
			// and overwrite the initial trading strategy decisions.
			// THis is slightly different from the case in 1 because here the
			// agent invests in the tranche that has maximum basis and
			// also the one that has maximum saving. NO SAVINGS ON SENIOR
			// TRANCHE
			double ext3 = 0;
			for (int i = 0; i < btr[4].length; i++) {

				if (i == 0) {

					btr[5][i] = notional * 0.8; // set notional per tranche
					// portfolio weights according
					// to returns relative
					// to the total income obtained from trade and add to
					// cds-basis report
					btr[6][i] = btr[5][i] * trancheSaving[i];// add per tranche
					// dollar amount of
					// capital savings
					// to cds-basis report
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * -1.0 * 0;// add per tranche dollar
					// amount of basis
					// income
					// to cds-basis report
					btr[8][i] = btr[5][i]
					                   * (libor + (-1.0 * basisnCoupon[0][i])) * 0;// add
					// per
					// tranche
					// dollar
					// amount
					// of
					// trade
					// income
					// to cds-basis report
					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
							* libor)
							/ (100 * haircut); // if the total gain from trading
					// is positive
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][i] = sumTGC;
					btr[12][i] = sumTNS;

					btr[13][i] = 1.0; // if the total gain from trading in this
					// tranche is the maximum execute
					// basis-trade in this tranche;

				} else {
					if (getMaximumIndex(numbersBasis) == i) {
						btr[5][i] = (leverageInjection + notional) * 0.2; // set notional per tranche
						// portfolio weights
						// according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						sumTNS += btr[6][i];
						btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// basis
						// income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
								* libor)
								/ (100 * haircut); // if the total gain from
						// trading is positive
						btr[10][i] = btr[5][i] * btr[2][i];
						sumTGC += btr[10][i];
						btr[11][0] = sumTGC;
						btr[12][0] = sumTNS;

						btr[13][i] = 1.0; // if the total gain from trading in
						// this tranche is the maximum
						// execute basis-trade in this tranche;
						if ((-1.0 * basisnCoupon[0][i]) > netCapSaving[i]) {
							btr[9][i] = 0.0;
							btr[10][i] = 0.0;
							btr[11][i] = 0.0;
							btr[12][i] = 0.0;

							btr[13][i] = 0.0; // if the total gain from trading
							// in this tranche is the
							// maximum
							// execute basis-trade in this tranche;
						}

					} else {
						btr[5][i] = 0.0; // set notional per tranche portfolio
						// weights according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						btr[7][i] = 0.0;// add per tranche dollar amount of
						// basis income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = 0.0;
						btr[10][i] = 0.0;
						btr[11][i] = 0.0;
						btr[12][i] = 0.0;

						btr[13][i] = 0.0; // do not execute basis trade.
					}// end of else
				}
			}// end of for-loop

			// The following bit of code is to chack if there are any further
			// tranches on which a basis trade can be made to
			// support the sinor AAA tranche. THis is required under Basel
			// rules.
			for (int i = 1; i < basisnCoupon[0].length; i++) {
				ext3 += btr[5][i];
			}
			if (ext3 <= 0) {
				btr[5][0] = 0.0; // set notional per tranche portfolio weights
				// according to returns relative
				// to the total income obtained from trade and add to cds-basis
				// report
				btr[6][0] = btr[5][0] * netCapSaving[0];// add per tranche
				// dollar amount of
				// capital savings
				// to cds-basis report
				btr[7][0] = 0.0;// add per tranche dollar amount of basis income
				// to cds-basis report
				btr[8][0] = btr[5][0] * (libor + (-1.0 * basisnCoupon[0][0]));// add
				// per
				// tranche
				// dollar
				// amount
				// of
				// trade
				// income
				// to cds-basis report
				btr[9][0] = 0.0;
				btr[10][0] = 0.0;
				btr[11][0] = 0.0;
				btr[12][0] = 0.0;
				btr[13][0] = 0.0; // do not execute basis trade.

			}

			break;
		case 7:
			// now loop through the tranches to see which of them contains the
			// maximum basis
			// and overwrite the initial trading strategy decisions.
			// THis is slightly different from the case in 1 because here the
			// agent invests in the tranche that has maximum basis and
			// also the one that has maximum saving. NO SAVINGS ON SENIOR
			// TRANCHE
			double ext4 = 0;
			for (int i = 0; i < btr[4].length; i++) {

				if (i == 0) {

					btr[5][i] = notional * 0.75; // set notional per tranche
					// portfolio weights
					// according
					// to returns relative
					// to the total income obtained from trade and add to
					// cds-basis report
					btr[6][i] = btr[5][i] * trancheSaving[i];// add per tranche
					// dollar amount of
					// capital savings
					// to cds-basis report
					sumTNS += btr[6][i];
					btr[7][i] = btr[5][i] * -1.0 * 0;// add per tranche dollar
					// amount of basis
					// income
					// to cds-basis report
					btr[8][i] = btr[5][i]
					                   * (libor + (-1.0 * basisnCoupon[0][i])) * 0;// add
					// per
					// tranche
					// dollar
					// amount
					// of
					// trade
					// income
					// to cds-basis report
					btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
							* libor)
							/ (100 * haircut); // if the total gain from trading
					// is positive
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][i] = sumTGC;
					btr[12][i] = sumTNS;

					btr[13][i] = 1.0; // if the total gain from trading in this
					// tranche is the maximum execute
					// basis-trade in this tranche;

				} else {
					if (getMaximumIndex(numbersBasis) == i) {
						btr[5][i] = (leverageInjection + notional) * 0.25; // set notional per tranche
						// portfolio weights
						// according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						sumTNS += btr[6][i];
						btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// basis
						// income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
								* libor)
								/ (100 * haircut); // if the total gain from
						// trading is positive
						btr[10][i] = btr[5][i] * btr[2][i];
						sumTGC += btr[10][i];
						btr[11][0] = sumTGC;
						btr[12][0] = sumTNS;

						btr[13][i] = 1.0; // if the total gain from trading in
						// this tranche is the maximum
						// execute basis-trade in this tranche;
						// The following bit of code is to chack if there are any further
						// tranches on which a basis trade can be made to
						// support the sinor AAA tranche. THis is required under Basel
						// rules.

						if ((-1.0 * basisnCoupon[0][i]) > netCapSaving[i]) {
							btr[9][i] = 0.0;
							btr[10][i] = 0.0;
							btr[11][i] = 0.0;
							btr[12][i] = 0.0;

							btr[13][i] = 0.0; // if the total gain from trading
							// in this tranche is the
							// maximum
							// execute basis-trade in this tranche;
						}

					} else {
						btr[5][i] = 0.0; // set notional per tranche portfolio
						// weights according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						btr[7][i] = 0.0;// add per tranche dollar amount of
						// basis income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = 0.0;
						btr[10][i] = 0.0;
						btr[11][i] = 0.0;
						btr[12][i] = 0.0;

						btr[13][i] = 0.0; // do not execute basis trade.
					}// end of else
				}
			}// end of for-loop

			break;
		case 8:
			// now loop through the tranches to see which of them contains the
			// maximum basis
			// and overwrite the initial trading strategy decisions.
			// THis is slightly different from the case in 1 because here the
			// agent invests in the tranche that has maximum basis and
			// also the one that has maximum saving. NO SAVINGS ON SENIOR
			// TRANCHE
			double ext5 = 0;
			for (int i = 0; i < btr[4].length; i++) {

				if (i == 0) {

					btr[5][i] = notional * 0.40; // set notional per tranche
					// portfolio weights
					// according
					// to returns relative
					// to the total income obtained from trade and add to
					// cds-basis report
					btr[6][i] = btr[5][i] * trancheSaving[i];// add per tranche
					// dollar amount of
					// capital savings
					// to cds-basis report
					sumTNS += btr[6][i];
					btr[7][i] = 0.0;// add per tranche dollar
					// amount of basis
					// income
					// to cds-basis report
					btr[8][i] = 0.0;// add
					// per
					// tranche
					// dollar
					// amount
					// of
					// trade
					// income
					// to cds-basis report
					btr[9][i] = 0.0; // if the total gain from trading
					// is positive
					btr[10][i] = btr[5][i] * btr[2][i];
					sumTGC += btr[10][i];
					btr[11][i] = sumTGC;
					btr[12][i] = sumTNS;

					btr[13][i] = 1.0; // if the total gain from trading in this
					// tranche is the maximum execute
					// basis-trade in this tranche;

				} else {
					if (getMaximumIndex(numbersBasis) == i) {
						btr[5][i] = (leverageInjection + notional) * 0.60; // set notional per tranche
						// portfolio weights
						// according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						if(-1.0 * basisnCoupon[0][i] < 0){
							//if basis is positive
							btr[6][i] = btr[5][i] * (trancheSaving[i] - 0.015 + cashSpread[i]);
							btr[7][i] = 0.0;// add
							// per
							// tranche
							// dollar
							// amount
							// of
							// basis
							// income
							// to cds-basis report
							btr[8][i] = 0.0;// add
							// per
							// tranche
							// dollar
							// amount
							// of
							// trade
							// income
							// to cds-basis report
							btr[9][i] = 0.0; // if the total gain from
							// trading is positive

						} 
						else{
							btr[6][i] = btr[5][i] * netCapSaving[i];// add per

							btr[7][i] = btr[5][i] * -1.0 * basisnCoupon[0][i];// add
							// per
							// tranche
							// dollar
							// amount
							// of
							// basis
							// income
							// to cds-basis report
							btr[8][i] = btr[5][i]
							                   * (0 + (-1.0 * basisnCoupon[0][i]));// add
							// per
							// tranche
							// dollar
							// amount
							// of
							// trade
							// income
							// to cds-basis report
							btr[9][i] = (btr[8][i] - (btr[5][i] - (btr[5][i] * haircut))
									* 1)
									/ (100 * haircut); // if the total gain from
							// trading is positive

						}
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						sumTNS += btr[6][i];
						btr[10][i] = btr[5][i] * btr[2][i];
						sumTGC += btr[10][i];
						btr[11][0] = sumTGC;
						btr[12][0] = sumTNS;

						btr[13][i] = 1.0; // if the total gain from trading in
						// this tranche is the maximum
						// execute basis-trade in this tranche;
						// The following bit of code is to chack if there are any further
						// tranches on which a basis trade can be made to
						// support the sinor AAA tranche. THis is required under Basel
						// rules.

						//new logic to add
						// if the cds basis is +ve and the bank does not sell CDS
						//check if the basis exceeds netsavings if true set all of 10 to 13 to zero
						//
						//if(-1.0 * basisnCoupon[0][i] < 0){

						//if (-1.0 * basisnCoupon[0][i] < 0) {
						if ((0.0 > btr[6][i])) {
							btr[9][i] = 0.0;
							btr[10][i] = 0.0;
							btr[11][i] = 0.0;
							btr[12][i] = 0.0;

							btr[13][i] = 0.0; // if the total gain from trading
							// in this tranche is the
							// maximum
							// execute basis-trade in this tranche;
							btr[13][0] = 0.0;
						}

					} else {
						btr[5][i] = 0.0; // set notional per tranche portfolio
						// weights according to returns
						// relative
						// to the total income obtained from trade and add to
						// cds-basis report
						btr[6][i] = btr[5][i] * netCapSaving[i];// add per
						// tranche
						// dollar amount
						// of capital
						// savings
						// to cds-basis report
						btr[7][i] = 0.0;// add per tranche dollar amount of
						// basis income
						// to cds-basis report
						btr[8][i] = btr[5][i]
						                   * (libor + (-1.0 * basisnCoupon[0][i]));// add
						// per
						// tranche
						// dollar
						// amount
						// of
						// trade
						// income
						// to cds-basis report
						btr[9][i] = 0.0;
						btr[10][i] = 0.0;
						btr[11][i] = 0.0;
						btr[12][i] = 0.0;

						btr[13][i] = 0.0; // do not execute basis trade.
					}// end of else
				}
			}// end of for-loop

			for (int i = 1; i < basisnCoupon[0].length; i++) {
				ext5 += btr[5][i];
			}

			break;

		default:
			System.out.println("Action out of bounds");

		}

		return btr;
	}

	private static double getMaximum(ArrayList<Double> numbers) {
		// TODO Auto-generated method stub
		// finding it with a loop
		double max = numbers.get(0);
		for (int i = 0; i < numbers.size(); ++i) {
			if (numbers.get(i) > max) {
				max = numbers.get(i);
			}
		}
		// max is now the largest
		// System.out.println(max);
		return max;
	}

	private static double getMaximumIndex(ArrayList<Double> numbers) {

		int maxIndex = 0;
		for (double z : numbers) {
			if (z == getMaximum(numbers)) {
				maxIndex = numbers.indexOf(z);
			}
		}
		// System.out.println(maxIndex);
		return maxIndex;

	}

	private static void setIndex(int i) {
		// TODO Auto-generated method stub
		maxTrancheIndex = i;
	}

	private static double[] computeNetCapSaving(double[] trancheSaving,
			double[][] basisnCoupon) {
		/**
		 * computes the net regulatory capital saving i.e. regulatoy capital
		 * savings net of cost of opening the negative basis trade
		 */
		double[] ncs = new double[basisnCoupon[0].length];

		for (int i = 0; i < ncs.length; i++) {
			ncs[i] = trancheSaving[i] - basisnCoupon[0][i];
		}

		return ncs;
	}

	private static double[][] computeBasisCoupons(double[] cashSpread,
			double[][] derivativeSpread) {
		/**
		 * computed the CDS basis and initial coupon for buying credit
		 * protection given the CDS price and CDS spread and bond spreads
		 */
		// Note that the credit ratings are defined as the columns and the
		// spreads, price and coupons are the rows.
		double[][] bt = new double[2][derivativeSpread[0].length];

		for (int i = 0; i < bt[0].length; i++) {
			bt[0][i] = (derivativeSpread[0][i] - cashSpread[i]) * 0.0001; // CDS-Basis
			// =
			// (CDSSpread
			// -
			// bondSpread)
			// divide by 100 to convert to percentage

			/*
			 * the following is not needed since the CDS/derivatives spread is already pre-calculated
			 * in the Normura Data and according to those calculated for Gorton (2008)
			 * the ABX spread is given by: 
			 * SpreadCDS (t) = (Upfront Premia (t) / Risky Duration(t)) + ABX Coupon 
			 * 
			 * Where Upfront Premia = 100 - PriceABX(t) 
			 * and 
			 * the Risky Duration is based on proprietary models used by the relevant dealer to project prepayments, 
			 * defaults, losses, durations using assumptions of home price appreciation.
			 */

			bt[1][i] = ((100 - derivativeSpread[1][i]) / 100)
			+ (derivativeSpread[2][i] * 0.0001); // initial coupon or
			// cost of opening
			// the basis trade
			// is (100 -
			// CDSPrice(t)) +
			// CDScoupon
			// divide by 100 to
			// convert to
			// percentage.
		}
		return bt;
	}

	private static double[] computeTrancheSavings(double[] exposureRiskWeights,
			double counterPartyRiskWeight) {
		/**
		 * computes the per tranche regulatory capital savings without
		 * accounting for the cost of buying CDS protection.
		 */

		double[] trancheSaving = new double[exposureRiskWeights.length];

		for (int i = 0; i < exposureRiskWeights.length; i++) {
			trancheSaving[i] = 0.08 * (exposureRiskWeights[i] - counterPartyRiskWeight);
		}

		return trancheSaving;
	}

	//
	private static double[] computeTrancheSavingsNoCRM(
			double[] exposureRiskWeights, double[] counterPartyRiskWeight) {
		/**
		 * computes the per tranche regulatory capital savings without
		 * accounting for the cost of buying CDS protection.
		 */

		double[] trancheSaving = new double[exposureRiskWeights.length];

		for (int i = 0; i < exposureRiskWeights.length; i++) {
			trancheSaving[i] = 0.08 * (exposureRiskWeights[i] - counterPartyRiskWeight[i]);
		}

		return trancheSaving;
	}

	private static double[] computeTrancheSavings(double[] exposureRiskWeights,
			double[] counterPartyRiskWeights) {
		/**
		 * computes the per tranche regulatory capital savings without
		 * accounting for the cost of buying CDS protection.
		 */

		double[] trancheSaving = new double[exposureRiskWeights.length];

		// Assume partial funded S-CDO, the senior most tranche is weighted at a
		// different
		// risk weight on balance sheet to the other tranches.
		trancheSaving[0] = 0.08 * (exposureRiskWeights[0] - counterPartyRiskWeights[0]);

		// This assumes only 2 counter party risk weights
		for (int i = 1; i < exposureRiskWeights.length; i++) {
			trancheSaving[i] = 0.08 * (exposureRiskWeights[i] - counterPartyRiskWeights[1]);
		}

		return trancheSaving;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<SINGLE TRANCHE NON-BANK BASIS
	// TRADE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public ArrayList openPosition(double derivativeSpread, double cashSpread,
			double repoHedgeSpread, double CDSPrice, double CDSCoupon) {

		/**
		 * computes the CDS basis and initial coupon for buying credit
		 * protection using single tranche data inputs
		 */

		int negativeBasis, positiveBasis;
		double dailyFundingGain, protectionBuyPremium;

		// is there a negative basis or positive basis and what is the daily
		// funding gain or daily carry?
		if (derivativeSpread < cashSpread) {
			negativeBasis = 1;
			positiveBasis = 0;
			dailyFundingGain = -1 * (derivativeSpread - cashSpread)
			- repoHedgeSpread;
		} else {
			negativeBasis = 0;
			positiveBasis = 1;
			dailyFundingGain = (derivativeSpread - cashSpread)
			- repoHedgeSpread;
		}

		// premium paid by protection buyer
		protectionBuyPremium = ((100 - CDSPrice) + CDSCoupon);

		this.tradeOpenPosition.add(negativeBasis); // [0,1] is there a negative
		// bisis
		this.tradeOpenPosition.add(positiveBasis); // [0,1] is there a positive
		// bisis
		this.tradeOpenPosition.add(dailyFundingGain); // value of
		// negative/positive
		// bisis which
		// represents the daily
		// gain/carry accrued
		// from
		// carrying out the basis trade.
		this.tradeOpenPosition.add(protectionBuyPremium); // this is the initial
		// premium paid by
		// the protection
		// buyer to take on
		// the basis trade
		this.tradeOpenPosition.add(CDSCoupon); // this is the regular coupon due
		// on the CDS note defined at
		// the start of the CDS contract

		return tradeOpenPosition;
	}

	public double closePosition(double derivativeSpread, double cashSpread,
			double postionOpeningBasis, int nBasis, int pBasis) {

		// was there a negative basis or positive basis at the opening of the
		// trade position?
		// premium paid by protection buyer
		if (nBasis == 1 && pBasis == 0) {

			TradeGain = (derivativeSpread - cashSpread)
			- (-1 * postionOpeningBasis);
		} else if (nBasis == 0 && pBasis == 1) {
			TradeGain = -1
			* ((derivativeSpread - cashSpread) - postionOpeningBasis);

		}

		return TradeGain;
	}

}
