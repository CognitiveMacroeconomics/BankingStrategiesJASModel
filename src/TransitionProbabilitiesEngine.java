

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * 
 * @author Oluwasegun Bewaji
 * 
 *         This class is a utility/engine class used to compute the transition
 *         probabilities of a stochastic paths
 * 
 *         it must be constructed with a given stochastic path(s) passed as an
 *         Array Where two paths are used to construct the engine, the path
 *         lengths for both paths must be identical
 * 
 *         Note: The default implementation of this class assumes that there are
 *         only 3 possible states any given asset whose path the engine is used
 *         to back out the transition probabilities.
 * 
 *         The possible states are that the asset value or rate index moves: 1:
 *         up (positive) 2: down (negative) 3: flat (zero)
 * 
 * 
 */
public class TransitionProbabilitiesEngine {

	// 2D arrays to store the transition probabilities of the assets
	// note this class implements the very basic model so -ve,flat,+ve i.e.
	// {-1,0,1} are the only states
	// for ease of understanding first build out the individual adjacent
	// transition probabilities then merge into the transition matrix
	double[] assetA_Negative_to_NegFlatPos;
	double[] assetA_Flat_to_NegFlatPos;
	double[] assetA_Positive_to_NegFlatPos;

	double[][] assetATransitionProbabilityMatrix;

	double[] assetB_Negative_to_NegFlatPos;
	double[] assetB_Flat_to_NegFlatPos;
	double[] assetB_Positive_to_NegFlatPos;

	double[][] assetBTransitionProbabilityMatrix;

	double[] assetAStochasticPath;
	double[] assetBStochasticPath;
	double initialAssetBReturn;
	double[] assetAReturnsPath;
	double[] assetBReturnsPath;

	int pathLength;
	double initialAssetAReturn;

	// the following list of integers are used to count the transitions from
	// -ve to +ve etc for asset A
	double assetANegNextPositive;
	double assetANegNextFlat;
	double assetANegNextNegative;

	double assetAFltNextPositive;
	double assetAFltNextFlat;
	double assetAFltNextNegative;

	double assetAPosNextPositive;
	double assetAPosNextFlat;
	double assetAPosNextNegative;

	// the following list of integers are used to count the transitions from
	// -ve to +ve etc for asset B
	double assetBNegNextPositive;
	double assetBNegNextFlat;
	double assetBNegNextNegative;

	double assetBFltNextPositive;
	double assetBFltNextFlat;
	double assetBFltNextNegative;

	double assetBPosNextPositive;
	double assetBPosNextFlat;
	double assetBPosNextNegative;

	// the following is the list of probabilities of transitioning from
	// -ve to +ve etc for asset A
	double assetAProbabilityNegNextPositive;
	double assetAProbabilityNegNextFlat;
	double assetAProbabilityNegNextNegative;

	double assetAProbabilityFltNextPositive;
	double assetAProbabilityFltNextFlat;
	double assetAProbabilityFltNextNegative;

	double assetAProbabilityPosNextPositive;
	double assetAProbabilityPosNextFlat;
	double assetAProbabilityPosNextNegative;

	// the following is the list of probabilities of transitioning from
	// -ve to +ve etc for asset B
	double assetBProbabilityNegNextPositive;
	double assetBProbabilityNegNextFlat;
	double assetBProbabilityNegNextNegative;

	double assetBProbabilityFltNextPositive;
	double assetBProbabilityFltNextFlat;
	double assetBProbabilityFltNextNegative;

	double assetBProbabilityPosNextPositive;
	double assetBProbabilityPosNextFlat;
	double assetBProbabilityPosNextNegative;

	public List<AdjacencyMatrixContainer> STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX = new ArrayList<AdjacencyMatrixContainer>();

	/**
	 * Two asset Path Constructor used to compute the joined transition
	 * probability matrix for 1 asset
	 * 
	 * @param path
	 */
	public TransitionProbabilitiesEngine(double[] path, double initAReturn) {
		this.assetAStochasticPath = path;
		this.initialAssetAReturn = initAReturn;
		this.pathLength = this.assetAStochasticPath.length;
		computeAssetAReturnsPath();
		computeAssetAStateTransitionCounts();
		computeAssetAStateTransitionProbabilities();
	}

	/**
	 * Two asset Path Constructor used to compute the joined transition
	 * probability matrix for 2 assets
	 * 
	 * @param path1
	 * @param path2
	 */
	public TransitionProbabilitiesEngine(double[] path1, double[] path2,
			double initAReturn, double initBReturn) {
		this.assetAStochasticPath = path1;
		this.assetBStochasticPath = path2;
		this.initialAssetAReturn = initAReturn;
		this.initialAssetBReturn = initBReturn;

		if (this.assetAStochasticPath.length == this.assetBStochasticPath.length) {
			this.pathLength = this.assetAStochasticPath.length;
			this.computeAssetAReturnsPath();
			this.computeAssetBReturnsPath();
			this.computeAssetAStateTransitionCounts();
			this.computeAssetBStateTransitionCounts();
			this.computeAssetAStateTransitionProbabilities();
			this.computeAssetBStateTransitionProbabilities();// computeAssetBStateTransitionProbabilities()
			this.computeTwoindependentAssetJointTransitionProbabilities();

		} else {
			JOptionPane.showMessageDialog(null, "ErrorMsg",
					"Stochastic Path Lengths must be Identical",
					JOptionPane.ERROR_MESSAGE);

		}
	}

	/**
	 * Computes the path of returns of asset A given the stochastic path and an
	 * initial return value defined in the class construction
	 */
	private void computeAssetAReturnsPath() {
		// initialize the returns path
		this.assetAReturnsPath = new double[this.pathLength];
		this.assetAReturnsPath[0] = this.initialAssetAReturn;// set initial
																// return value
		for (int i = 1; i < this.assetAStochasticPath.length; i++) { // for each
																		// index
																		// level
																		// in
																		// the
																		// path
																		// compute
																		// the
																		// return
																		// as
																		// the
																		// natural
																		// log
			// of the current index level divided by the previous index level
			this.assetAReturnsPath[i] = Rounding
					.roundFourDecimals(Math
							.log((this.assetAStochasticPath[i] / this.assetAStochasticPath[i - 1])));
		}

	}

	/**
	 * Computes the path of returns of asset B given the stochastic path and an
	 * initial return value defined in the class construction
	 */
	private void computeAssetBReturnsPath() {
		// initialize the returns path
		this.assetBReturnsPath = new double[this.pathLength];
		this.assetBReturnsPath[0] = this.initialAssetBReturn;// set initial
																// return value
		for (int i = 1; i < this.assetBStochasticPath.length; i++) { // for each
																		// index
																		// level
																		// in
																		// the
																		// path
																		// compute
																		// the
																		// return
																		// as
																		// the
																		// natural
																		// log
			// of the current index level divided by the previous index level
			this.assetBReturnsPath[i] = Rounding
					.roundFourDecimals(Math
							.log((this.assetBStochasticPath[i] / this.assetBStochasticPath[i - 1])));
		}

	}

	private void computeAssetAStateTransitionCounts() {
		//
		int ntop = 0;
		int ntof = 0;
		int nton = 0;

		int ztop = 0;
		int ztof = 0;
		int zton = 0;

		int ptop = 0;
		int ptof = 0;
		int pton = 0;

		// for each return element of the path of stochastic returns
		// determine if the current return is +ve/-ve/0 then
		for (int i = 0; i < this.assetAReturnsPath.length - 1; i++) {
			double rt0 = this.assetAReturnsPath[i];
			double rt1 = this.assetAReturnsPath[i + 1];
			// if the current return is negative
			if (rt0 < 0 && rt1 < 0) {
				nton++;
			} else if (rt0 < 0 && rt1 == 0) {
				ntof++;
			} else if (rt0 < 0 && rt1 > 0) {
				ntop++;
			}

			// if the current return is zero
			if (rt0 == 0 && rt1 < 0) {
				zton++;
			} else if (rt0 == 0 && rt1 == 0) {
				ztof++;
			} else if (rt0 == 0 && rt1 > 0) {
				ztop++;
			}

			// if the current return is positive
			if (rt0 > 0 && rt1 < 0) {
				pton++;
			} else if (rt0 > 0 && rt1 == 0) {
				ptof++;
			} else if (rt0 > 0 && rt1 > 0) {
				ptop++;
			}
		}

		// Set Count Values
		this.assetANegNextPositive = ntop;
		this.assetANegNextFlat = ntof;
		this.assetANegNextNegative = nton;

		this.assetAFltNextPositive = ztop;
		this.assetAFltNextFlat = ztof;
		this.assetAFltNextNegative = zton;

		this.assetAPosNextPositive = ptop;
		this.assetAPosNextFlat = ptof;
		this.assetAPosNextNegative = pton;

		// System.out.println("assetANegNextPositive: " + assetANegNextPositive
		// + " assetANegNextFlat: " + assetANegNextFlat
		// + " assetANegNextNegative: " + assetANegNextNegative);
		// System.out.println();
		//
		// System.out.println("assetAFltNextPositive: " + assetAFltNextPositive
		// + " assetAFltNextFlat: " + assetAFltNextFlat
		// + " assetAFltNextNegative: " + assetAFltNextNegative);
		// System.out.println();
		//
		// System.out.println("assetAPosNextPositive: " + assetAPosNextPositive
		// + " assetAPosNextFlat: " + assetAPosNextFlat
		// + " assetAPosNextNegative: " + assetAPosNextNegative);
		// System.out.println();

	}

	private void computeAssetBStateTransitionCounts() {
		//
		int ntop = 0;
		int ntof = 0;
		int nton = 0;

		int ztop = 0;
		int ztof = 0;
		int zton = 0;

		int ptop = 0;
		int ptof = 0;
		int pton = 0;

		// for each return element of the path of stochastic returns
		// determine if the current return is +ve/-ve/0 then
		for (int i = 0; i < this.assetBReturnsPath.length - 1; i++) {
			double rt0 = this.assetBReturnsPath[i];
			double rt1 = this.assetBReturnsPath[i + 1];
			// if the current return is negative
			if (rt0 < 0 && rt1 < 0) {
				nton++;
			} else if (rt0 < 0 && rt1 == 0) {
				ntof++;
			} else if (rt0 < 0 && rt1 > 0) {
				ntop++;
			}

			// if the current return is zero
			if (rt0 == 0 && rt1 < 0) {
				zton++;
			} else if (rt0 == 0 && rt1 == 0) {
				ztof++;
			} else if (rt0 == 0 && rt1 > 0) {
				ztop++;
			}

			// if the current return is positive
			if (rt0 > 0 && rt1 < 0) {
				pton++;
			} else if (rt0 > 0 && rt1 == 0) {
				ptof++;
			} else if (rt0 > 0 && rt1 > 0) {
				ptop++;
			}
		}

		// Set Count Values
		this.assetBNegNextPositive = ntop;
		this.assetBNegNextFlat = ntof;
		this.assetBNegNextNegative = nton;

		this.assetBFltNextPositive = ztop;
		this.assetBFltNextFlat = ztof;
		this.assetBFltNextNegative = zton;

		this.assetBPosNextPositive = ptop;
		this.assetBPosNextFlat = ptof;
		this.assetBPosNextNegative = pton;

		// System.out.println("assetBNegNextPositive: " + assetBNegNextPositive
		// + " assetBNegNextFlat: " + assetBNegNextFlat
		// + " assetBNegNextNegative: " + assetBNegNextNegative);
		// System.out.println();
		//
		// System.out.println("assetBFltNextPositive: " + assetBFltNextPositive
		// + " assetBFltNextFlat: " + assetBFltNextFlat
		// + " assetBFltNextNegative: " + assetBFltNextNegative);
		// System.out.println();
		//
		// System.out.println("assetBPosNextPositive: " + assetBPosNextPositive
		// + " assetBPosNextFlat: " + assetBPosNextFlat
		// + " assetBPosNextNegative: " + assetBPosNextNegative);

	}

	/**
	 * Computes the probabilities of asset A transitioning from all possible
	 * states to all other possible states for example the probability of moving
	 * from a state of negative returns to all possible states the sum of
	 * probabilities for each individual stating state to the next i.e. -ve ->
	 * {-ve/+ve/flat} must equal to 1
	 * 
	 */
	private void computeAssetAStateTransitionProbabilities() {
		assetAProbabilityNegNextPositive = Rounding
				.roundTwoDecimals(assetANegNextPositive
						/ (assetANegNextPositive + assetANegNextFlat + assetANegNextNegative));

		assetAProbabilityNegNextFlat = Rounding
				.roundTwoDecimals(assetANegNextFlat
						/ (assetANegNextPositive + assetANegNextFlat + assetANegNextNegative));

		assetAProbabilityNegNextNegative = Rounding
				.roundTwoDecimals(assetANegNextNegative
						/ (assetANegNextPositive + assetANegNextFlat + assetANegNextNegative));

		// flat to others
		assetAProbabilityFltNextPositive = Rounding
				.roundTwoDecimals(assetAFltNextPositive
						/ (assetAFltNextPositive + assetAFltNextFlat + assetAFltNextNegative));

		assetAProbabilityFltNextFlat = Rounding
				.roundTwoDecimals(assetAFltNextFlat
						/ (assetAFltNextPositive + assetAFltNextFlat + assetAFltNextNegative));

		assetAProbabilityFltNextNegative = Rounding
				.roundTwoDecimals(assetAFltNextNegative
						/ (assetAFltNextPositive + assetAFltNextFlat + assetAFltNextNegative));

		// positive to others
		assetAProbabilityPosNextPositive = Rounding
				.roundTwoDecimals(assetAPosNextPositive
						/ (assetAPosNextPositive + assetAPosNextFlat + assetAPosNextNegative));

		assetAProbabilityPosNextFlat = Rounding
				.roundTwoDecimals(assetAPosNextFlat
						/ (assetAPosNextPositive + assetAPosNextFlat + assetAPosNextNegative));

		assetAProbabilityPosNextNegative = Rounding
				.roundTwoDecimals(assetAPosNextNegative
						/ (assetAPosNextPositive + assetAPosNextFlat + assetAPosNextNegative));

		// now set the transition adjacency lists
		assetA_Negative_to_NegFlatPos = new double[] {
				assetAProbabilityNegNextNegative, assetAProbabilityNegNextFlat,
				assetAProbabilityNegNextPositive };

		assetA_Flat_to_NegFlatPos = new double[] {
				assetAProbabilityFltNextNegative, assetAProbabilityFltNextFlat,
				assetAProbabilityFltNextPositive };

		assetA_Positive_to_NegFlatPos = new double[] {
				assetAProbabilityPosNextNegative, assetAProbabilityPosNextFlat,
				assetAProbabilityPosNextPositive };

		assetATransitionProbabilityMatrix = new double[][] {
				{ assetAProbabilityNegNextNegative,
						assetAProbabilityNegNextFlat,
						assetAProbabilityNegNextPositive },
				{ assetAProbabilityFltNextNegative,
						assetAProbabilityFltNextFlat,
						assetAProbabilityFltNextPositive },
				{ assetAProbabilityPosNextNegative,
						assetAProbabilityPosNextFlat,
						assetAProbabilityPosNextPositive } };

		// System.out.println("assetAProbabilityNegNextNegative: "
		// + assetAProbabilityNegNextNegative
		// + " assetAProbabilityNegNextFlat: "
		// + assetAProbabilityNegNextFlat
		// + " assetAProbabilityNegNextPositive: "
		// + assetAProbabilityNegNextPositive);
		// System.out.println();
		//
		// System.out.println("assetAProbabilityFltNextNegative: "
		// + assetAProbabilityFltNextNegative
		// + " assetAProbabilityFltNextFlat: "
		// + assetAProbabilityFltNextFlat
		// + " assetAProbabilityFltNextPositive: "
		// + assetAProbabilityFltNextPositive);
		// System.out.println();
		//
		// System.out.println("assetAProbabilityPosNextNegative: "
		// + assetAProbabilityPosNextNegative
		// + " assetAProbabilityPosNextFlat: "
		// + assetAProbabilityPosNextFlat
		// + " assetAProbabilityPosNextPositive: "
		// + assetAProbabilityPosNextPositive);
		// System.out.println();
	}

	/**
	 * Computes the probabilities of asset B transitioning from all possible
	 * states to all other possible states for example the probability of moving
	 * from a state of negative returns to all possible states the sum of
	 * probabilities for each individual stating state to the next i.e. -ve ->
	 * {-ve/+ve/flat} must equal to 1
	 * 
	 */
	private void computeAssetBStateTransitionProbabilities() {
		// negative to others
		assetBProbabilityNegNextPositive = Rounding
				.roundTwoDecimals((assetBNegNextPositive / (assetBNegNextPositive
						+ assetBNegNextFlat + assetBNegNextNegative)));

		assetBProbabilityNegNextFlat = Rounding
				.roundTwoDecimals((assetBNegNextFlat / (assetBNegNextPositive
						+ assetBNegNextFlat + assetBNegNextNegative)));

		assetBProbabilityNegNextNegative = Rounding
				.roundTwoDecimals((assetBNegNextNegative / (assetBNegNextPositive
						+ assetBNegNextFlat + assetBNegNextNegative)));

		// flat to others
		assetBProbabilityFltNextPositive = Rounding
				.roundTwoDecimals((assetBFltNextPositive / (assetBFltNextPositive
						+ assetBFltNextFlat + assetBFltNextNegative)));

		assetBProbabilityFltNextFlat = Rounding
				.roundTwoDecimals((assetBFltNextFlat / (assetBFltNextPositive
						+ assetBFltNextFlat + assetBFltNextNegative)));

		assetBProbabilityFltNextNegative = Rounding
				.roundTwoDecimals((assetBFltNextNegative / (assetBFltNextPositive
						+ assetBFltNextFlat + assetBFltNextNegative)));

		// positive to others
		assetBProbabilityPosNextPositive = Rounding
				.roundTwoDecimals((assetBPosNextPositive / (assetBPosNextPositive
						+ assetBPosNextFlat + assetBPosNextNegative)));

		assetBProbabilityPosNextFlat = Rounding
				.roundTwoDecimals((assetBPosNextFlat / (assetBPosNextPositive
						+ assetBPosNextFlat + assetBPosNextNegative)));

		assetBProbabilityPosNextNegative = Rounding
				.roundTwoDecimals((assetBPosNextNegative / (assetBPosNextPositive
						+ assetBPosNextFlat + assetBPosNextNegative)));

		// now set the transition adjacency lists
		assetB_Negative_to_NegFlatPos = new double[] {
				assetBProbabilityNegNextNegative, assetBProbabilityNegNextFlat,
				assetBProbabilityNegNextPositive };

		assetB_Flat_to_NegFlatPos = new double[] {
				assetBProbabilityFltNextNegative, assetBProbabilityFltNextFlat,
				assetBProbabilityFltNextPositive };

		assetB_Positive_to_NegFlatPos = new double[] {
				assetBProbabilityPosNextNegative, assetBProbabilityPosNextFlat,
				assetBProbabilityPosNextPositive };

		assetBTransitionProbabilityMatrix = new double[][] {
				{ assetBProbabilityNegNextNegative,
						assetBProbabilityNegNextFlat,
						assetBProbabilityNegNextPositive },
				{ assetBProbabilityFltNextNegative,
						assetBProbabilityFltNextFlat,
						assetBProbabilityFltNextPositive },
				{ assetBProbabilityPosNextNegative,
						assetBProbabilityPosNextFlat,
						assetBProbabilityPosNextPositive } };

		// for (int i = 0; i < assetB_Positive_to_NegFlatPos.length; i++) {
		// System.out.println(assetB_Positive_to_NegFlatPos[i]);
		// }

		// System.out.println("assetBProbabilityNegNextNegative: "
		// + assetBProbabilityNegNextNegative
		// + " assetBProbabilityNegNextFlat: "
		// + assetBProbabilityNegNextFlat
		// + " assetBProbabilityNegNextPositive: "
		// + assetBProbabilityNegNextPositive);
		// System.out.println();
		//
		// System.out.println("assetBProbabilityFltNextNegative: "
		// + assetBProbabilityFltNextNegative
		// + " assetBProbabilityFltNextFlat: "
		// + assetBProbabilityFltNextFlat
		// + " assetBProbabilityFltNextPositive: "
		// + assetBProbabilityFltNextPositive);
		// System.out.println();
		//
		// System.out.println("assetBProbabilityPosNextNegative: "
		// + assetBProbabilityPosNextNegative
		// + " assetBProbabilityPosNextFlat: "
		// + assetBProbabilityPosNextFlat
		// + " assetBProbabilityPosNextPositive: "
		// + assetBProbabilityPosNextPositive);
		// System.out.println();
	}

	private void computeTwoindependentAssetJointTransitionProbabilities() {

		// empty the STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX list if not
		// empty
		if (STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX.size() > 0) {
			STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX.clear();
		}

		// first loop through the rows of the first asset's transition
		// probability matrix
		for (int g = 0; g < this.assetATransitionProbabilityMatrix.length; g++) {
			// now loop through the rows of the second asset's transition
			// probability matrix
			for (int h = 0; h < this.assetBTransitionProbabilityMatrix.length; h++) {
				double[] transProbArray = new double[this.assetATransitionProbabilityMatrix[0].length
						* this.assetBTransitionProbabilityMatrix[0].length];
				int incr = 0; // integer to enable scaling through
								// transProbArray array
				// now for each element of the selected row of the first asset's
				// transition probability matrix
				// multiply by each element of the selected row of the second
				// asset's transition probability matrix
				for (int i = 0; i < this.assetATransitionProbabilityMatrix[g].length; i++) {

					// now loop through each element of the selected row of the
					// second assets transition probability matrix
					for (int l = 0; l < this.assetBTransitionProbabilityMatrix[h].length; l++) {
						transProbArray[incr] = Rounding
								.roundTwoDecimals(this.assetATransitionProbabilityMatrix[g][i]
										* this.assetBTransitionProbabilityMatrix[h][l]);
						incr++;// increment incr to apply to next value in loop
					}
				}// end column for loop
					// add the transition list to the joined probability matrix
				STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX
						.add(AdjacencyMatrixContainer
								.createTwoAssetAdjacencyList(g - 1, h - 1,
										transProbArray));
				// double n = 0;
				// for (int i = 0; i < transProbArray.length; i++) {
				// n += transProbArray[i];
				// System.out.println(transProbArray[i]);
				// }
				// System.out.println("cumulative joint probability: " + n);
			}
		}

	}

	public List<AdjacencyMatrixContainer> getJoinedTransitionProbabilityMatrix() {
		return this.STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX;
	}

}
