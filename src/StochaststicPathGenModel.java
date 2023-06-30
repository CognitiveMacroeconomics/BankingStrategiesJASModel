

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class StochaststicPathGenModel {
	
	ArrayList<AssetPath> singleIterationAssetPaths = new ArrayList<AssetPath> ();
	ModelParameters parameters;
	AssetPath singleIterationAveragePath;
	double initialAssetValue;
	
	
	/**
	 * Model constructor is such that a set of parameters must be provided for the model to generate an output
	 * @param params
	 */
	public StochaststicPathGenModel(ModelParameters params){
		this.parameters = params;
		setInitialAssetValue(this.parameters.getInitialAssetvalue());
		initialize();
//		System.out.println(assetPaths.size());
	}



	public void setInitialAssetValue(double initialAssetvalue2) {
		// TODO Auto-generated method stub
		this.initialAssetValue = initialAssetvalue2;
	}

	
	public double getInitialAssetvalue() {
		// TODO Auto-generated method stub
		return this.initialAssetValue;
	}



	private void initialize() {
		// TODO Auto-generated method stub
		this.singleIterationAveragePath = new AssetPath(this.parameters.getAssetName()+" Average Path", 
				this.getInitialAssetvalue(), this.parameters.getPathLength());
		for(int i = 0; i<this.parameters.getNumberOfPaths(); i++){
			//AssetPath(String AssetName, double initVal, int iterations)
			AssetPath ap = new AssetPath(this.parameters.getAssetName(), 
					this.getInitialAssetvalue(), this.parameters.getPathLength());
			singleIterationAssetPaths.add(ap);
		}
		//this.computeSingleIterationAveragePathRandomSelection();
	}
	
	


	public void generatePaths() {
		// TODO Auto-generated method stub
		switch(this.parameters.getStochasticProcessTypeStringIndex()){
		
		case 0: //standard Brownian motion paths
			//MonteCPrices.simValueP(double mean,double sd,double time,double initialvalue)
			for(int i = 0; i< singleIterationAssetPaths.size(); i++){
				MonteCPrices m = new MonteCPrices(this.parameters.getPathLength());
				singleIterationAssetPaths.get(i).setPath(m.simValueP(this.parameters.getDriftMean(), this.parameters.getStandardDeviation(), 
						this.parameters.getTimeShit_dt(), this.getInitialAssetvalue()));
//				System.out.println(assetPaths.get(i).toString() + ": " 
//						+ assetPaths.get(i).getPath()[assetPaths.get(i).getPath().length-1]);
			}
			break;
		case 1:  //continuous time Brownian motion paths Merton (1973)
			//MonteCPrices.simValueln(double mean,double sd,double time,double initialvalue)
			for(int i = 0; i< singleIterationAssetPaths.size(); i++){
				MonteCPrices m = new MonteCPrices(this.parameters.getPathLength());
				singleIterationAssetPaths.get(i).setPath(m.simValueln(this.parameters.getDriftMean(), this.parameters.getStandardDeviation(), 
						this.parameters.getTimeShit_dt(), this.getInitialAssetvalue()));
//				System.out.println(assetPaths.get(i).toString() + ": " 
//						+ assetPaths.get(i).getPath()[assetPaths.get(i).getPath().length-1]);
			}
			break;
		case 2:  //simple untidy discretised implementation of The Cox-Ingersoll-Ross
			//MonteCPrices.simValueCIR(double alpha, double theta, double sigma, double time, double initialValue)
			for(int i = 0; i< singleIterationAssetPaths.size(); i++){
				MonteCPrices m = new MonteCPrices(this.parameters.getPathLength());
				singleIterationAssetPaths.get(i).setPath(m.simValueCIR(this.parameters.getCir_Alpha(), this.parameters.getCir_Theta(), 
						this.parameters.getStandardDeviation(), this.parameters.getTimeShit_dt(),
						this.getInitialAssetvalue()));
//				System.out.println(assetPaths.get(i).toString() + ": " 
//						+ assetPaths.get(i).getPath()[assetPaths.get(i).getPath().length-1]);
			}
			break;
		case 3:  //simple untidy discretised implementation of The Cox-Ingersoll-Ross++ (CIR++) model with stochastic jumps
			//MonteCPrices.simValueCIRpp(double alpha, double theta, double sigma, double time, double initialValue,  
			//double jumpIntensity, double meanJumpSize, double widthOfJumpSizeDistribution)
			for(int i = 0; i< singleIterationAssetPaths.size(); i++){
				MonteCPrices m = new MonteCPrices(this.parameters.getPathLength());
				singleIterationAssetPaths.get(i).setPath(m.simValueCIRpp(this.parameters.getCir_Alpha(), 
						this.parameters.getCir_Theta(), 
						this.parameters.getStandardDeviation(), this.parameters.getTimeShit_dt(),
						this.getInitialAssetvalue(), this.parameters.getJumpIntensity(), 
						this.parameters.getJump_Mean_Size(), this.parameters.getJump_Size_Distribution_Width()));
//				System.out.println(assetPaths.get(i).toString() + ": " 
//						+ assetPaths.get(i).getPath()[assetPaths.get(i).getPath().length-1]);
			}
			break;
			
		case 4:  //simple untidy forged implementation of a geometric Brownian motion with stochastic jumps
			//MonteCPrices.simValueJumpDiffusionBM(double mean, double sigma, double time, double initialValue,  
			//double jumpIntensity, double meanJumpSize, double widthOfJumpSizeDistribution)
			for(int i = 0; i< singleIterationAssetPaths.size(); i++){
				MonteCPrices m = new MonteCPrices(this.parameters.getPathLength());
				singleIterationAssetPaths.get(i).setPath(m.simValueJumpDiffusionBM(this.parameters.getDriftMean(), 
						this.parameters.getStandardDeviation(), this.parameters.getTimeShit_dt(),
						this.getInitialAssetvalue(), this.parameters.getJumpIntensity(), 
						this.parameters.getJump_Mean_Size(), this.parameters.getJump_Size_Distribution_Width()));
//				System.out.println(assetPaths.get(i).toString() + ": " 
//						+ assetPaths.get(i).getPath()[assetPaths.get(i).getPath().length-1]);
			}
			break;
			
		case 5:  //simple untidy forged implementation of a Heston stochastic volatility model
			//MonteCPrices.simValueHestonStochastricVolatilityDiffusion(double mean, 
			//double sigma, double time, double initialValue,  double sigmaAlpha, double sigmaTheta, double sigmaSigma)
			for(int i = 0; i< singleIterationAssetPaths.size(); i++){
				MonteCPrices m = new MonteCPrices(this.parameters.getPathLength());
				singleIterationAssetPaths.get(i).setPath(m.simValueHestonStochastricVolatilityDiffusion(
						this.parameters.getDriftMean(), 
						this.parameters.getStandardDeviation(), this.parameters.getTimeShit_dt(),
						this.getInitialAssetvalue(), this.parameters.getHestonMeanReversionRate(), 
						this.parameters.getHestonLongTermVariance(), this.parameters.getHestonVarianceVolatility()));
//				System.out.println(assetPaths.get(i).toString() + ": " 
//						+ assetPaths.get(i).getPath()[assetPaths.get(i).getPath().length-1]);
			}
			break;
			default:
				System.out.println("Invalid Selection");
		}
	}
	
	
	public ArrayList<AssetPath> getSingleIterationAssetPaths(){
		
		return this.singleIterationAssetPaths;
	}
	
	public AssetPath getSingleIterationAveragePath(){
		
		return this.singleIterationAveragePath;
	}

	
	public void computeSingleIterationAveragePath(){
		int plth = this.parameters.getPathLength();
		for(int i = 0; i < this.parameters.getPathLength(); i++){
			double sum = 0;
			double avrg = 0;
////			if(this.parameters.getStochasticProcessTypeStringIndex() < 2){
////				sum = 0;
////			}else{
////				sum = 1;
////			}
			for(int j = 0; j < this.singleIterationAssetPaths.size(); j++){
//				//if(this.parameters.getStochasticProcessTypeStringIndex() < 2){
					sum += this.singleIterationAssetPaths.get(j).getPath()[i];
//				//}
//				//else{
////					sum *= this.singleIterationAssetPaths.get(j).getPath()[i];
////				}
			}
			
////			if(this.parameters.getStochasticProcessTypeStringIndex() < 2){
				avrg = sum/plth;
				this.singleIterationAveragePath.getPath()[i] = avrg;
////			}
////			else{
////				avrg =Math.pow(sum, 1/plth);
////				this.singleIterationAveragePath.getPath()[i] = avrg;
////			}
			
		}
	}
		
		
		public void computeSingleIterationAveragePathRandomSelection(){
			int plth = this.singleIterationAssetPaths.size();
			int randomPathIndex = 0 + (int)(Math.random()*plth); 
			this.singleIterationAveragePath.setPath(this.singleIterationAssetPaths.get(randomPathIndex).getPath());
		}
		
		public void setTransitionProbability(){
			//TransitionProbabilitiesEngine(double[] path1, double[] path2, double initAReturn, double initBReturn)
			int plth = this.parameters.getNumberOfPaths();
			int randomPathIndex1 = 0 + (int)(Math.random()*plth); 
			int randomPathIndex2 = 0 + (int)(Math.random()*plth);
			AssetPath path1 = new AssetPath(this.parameters.getAssetName()+" Average Path", 
					this.parameters.getInitialAssetvalue(), this.parameters.getPathLength());
			AssetPath path2 = new AssetPath(this.parameters.getAssetName()+" Average Path", 
					this.parameters.getInitialAssetvalue(), this.parameters.getPathLength());
			
			path1.setPath(this.singleIterationAssetPaths.get(randomPathIndex1).getPath());
			path2.setPath(this.singleIterationAssetPaths.get(randomPathIndex2).getPath());
			TransitionProbabilitiesEngine tEng = new TransitionProbabilitiesEngine(path1.getPath(), path2.getPath(), 0, 0);
			List<AdjacencyMatrixContainer> STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX = tEng.getJoinedTransitionProbabilityMatrix();
			
//			System.out.println(STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX.size());
//			System.out.println(STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX.toString());
			
		}

		
		

			
	
	
	
	

}
