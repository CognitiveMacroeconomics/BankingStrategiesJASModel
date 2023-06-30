package qlearning; 
/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation; either version 2.1 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    StockageNN.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */
import environnement.State; 
import environnement.Action; 
import environnement.ListeActions; 

import dataset.*; 
import neuralnetwork.*; 

import java.util.Random; 




/** Memorizing Q(s,a) in a neural network. 

Each experience is trasnformed into a <code>Sample</code><br>
At some moments, the <code>Sample</code>s gathered into a <code>Dataset</code> are fed into a neural network, which is asked to learn Q(s,a) from this dataset.<br>
When asked to return a Q(s,a) value, this class uses the neural network to compute it.

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

*/

public class StockageNN implements Rangement{

    /** The neural network plays the role of memory*/
    protected NeuralNetwork memoire; 
    /** The current samples */
    protected Dataset myDataset=new Dataset(); 
    /** The maximum number of Samples contained into the dataset.*/
    protected int maxSize=5000; 
    /** Control the number of newcoming samples.*/ 
    protected int newOnes=0;
    /** Each time incoming samples reach limit, the neural network is asked to learn.*/
    protected int limit=50; 
    protected Random generateur=new Random(); 

    /** technical trick...*/
    protected State etatBidon; 

    /** Maps R to [0,1] */
    protected double inverseLogistic(double x){
	double u=Math.exp(x); 
	return u/(1+u); 
    }

    /** Maps [0,1] to R */
    protected double logistic(double x){
	return Math.log(x/(1-x)); 
    }

    /** To rescale the values between 0 and 1 */
    protected boolean rescale=false; 

    public double getWeight(int i,int j,int k){
	return memoire.getWeight(i,j,k);
    }
    
    public int getSizeOfLayer(int i){
	return memoire.getSizeOfLayer(i);
    }

    public void setEpoch(int i){
	memoire.setEpoch(i);
    }

    /** Enable rescaling */
    public void setRescale(){this.rescale=true;}
    
    /** Disable rescaling */
    public void unsetRescale(){this.rescale=false;}

    
 
    public double get(State s,Action a){
	double inputs[]=new double[s.tailleCodage()+a.tailleCodage()];
	double resu[]=new double[1]; 
	System.arraycopy(s.codage(),0,inputs,0,s.tailleCodage()); 
	System.arraycopy(a.codage(),0,inputs,s.tailleCodage(),a.tailleCodage());
	if(memoire!=null)
	    {
		try{
		resu=memoire.classify(inputs); 
		}
		catch(Exception e){System.err.println("xxx:"+e); System.exit(-1); }
		if(!rescale)
		    return resu[0];
		else
		    return logistic(resu[0]); 
	    }
		
	else return (generateur.nextDouble()-0.5)/10; 
 }



    public void setNN(int descLayers[]){
	memoire=new NeuralNetwork(descLayers); 
	 memoire.setEpoch(100); 
	 memoire.initNetwork(); 
	 //	 etatBidon=s.getContraintes().defaultInitialState(); 
    }
     
    public void put(State s,Action a,State sp,double qsa){
	if (memoire==null) {// Network does not exist yet
	    // Build it
	    int archi[]=new int[3]; 
	    archi[0]=s.tailleCodage()+a.tailleCodage(); 
	    archi[1]=1+archi[0]/5; 
	    archi[2]=1; 
	    memoire=new NeuralNetwork(archi); 
	    memoire.setEpoch(1); 
	    memoire.initNetwork(); 
	    etatBidon=s.getContraintes().defaultInitialState(); 
	}
	double inputs[]=new double[s.tailleCodage()+a.tailleCodage()];
	double outputs[]=new double[1]; 
	if (rescale)
	    outputs[0]=inverseLogistic(qsa); 
	else
	    outputs[0]=qsa; 
	System.arraycopy(s.codage(),0,inputs,0,s.tailleCodage()); 
	System.arraycopy(a.codage(),0,inputs,s.tailleCodage(),a.tailleCodage()); 
	myDataset.add(new Sample(inputs,outputs)); 
	newOnes++; 
	if(newOnes%limit==1) {
	    try{
		memoire.learnFromDatasetNonStochastic(myDataset); 
	    }
	    catch(Exception e){System.err.println("YYY"+e); System.exit(-1);}
	}

	if (myDataset.numInstances()>maxSize){
	    int u=generateur.nextInt(maxSize); 
	    myDataset.remove(u); 
	}
	return;
	}
    


    /** Printing Q(s,a) value.
     More difficult than when Q(s,a) are really stored : we can only sample the values...*/

    public String toString(){
	String s=""; 
	double candidat=0,score=0; 
	State courant=etatBidon; 

	for(int i=0;i<50;i++){
	    s+=courant+"\n"; 
	    ListeActions loa=courant.getListeActions(); 
	    if(loa.size()!=0){
	    Action bestAct=loa.getAction(0); 
	    double inputs[]=new double[courant.tailleCodage()+bestAct.tailleCodage()];
	    double resu[]=new double[1]; 
	    System.arraycopy(courant.codage(),0,inputs,0,courant.tailleCodage()); 
	    System.arraycopy(bestAct.codage(),0,inputs,courant.tailleCodage(),bestAct.tailleCodage());
	    try{
		 score=memoire.classify(inputs)[0]; 
	    }
	    catch(Exception e){System.err.println("uuu"+e); System.exit(-1);}
	    for(int j=0;j<loa.size();j++){
		Action nouvelle=loa.getAction(j);
		 System.arraycopy(nouvelle.codage(),0,inputs,courant.tailleCodage(),nouvelle.tailleCodage());
		 try{
		      candidat=memoire.classify(inputs)[0]; 
		 }
		 catch(Exception e){System.err.println("uuu"+e); System.exit(-1);}
		 
		 if(candidat>score){
		    score=candidat; 
		    bestAct=nouvelle; 
		}
		 s+="\t "+nouvelle+" "+candidat+"\n"; 
	    }
	    s+="\t Best  "+bestAct+" "+score+"\n"; 
	    courant=courant.modify(bestAct); 
	    }
	    else break; 
	}
	return s; 
    }
		
    /** No dataset extraction at this time (september 2005,USTL ) */
    public Dataset extractDataset(){return null;}; 
}
