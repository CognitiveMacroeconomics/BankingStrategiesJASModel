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
import dataset.Sample; 
import neuralnetwork.NeuralNetwork; 


/** Samples are not memorized : each time a new experience is available, the neural network is asked to train. The sample is then forgotten.

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

 */



public class StockageNNSingle extends StockageNN{

  
    public void put(State s,Action a,State sp,double qsa){
    	if (memoire==null) {//No neural Network yet
	    // Build it
	    int archi[]=new int[3]; 
	    archi[0]=s.tailleCodage()+a.tailleCodage(); 
	    archi[1]=100 ; //1+archi[0]/5; 
	    archi[2]=1; 
	    memoire=new NeuralNetwork(archi); 
	    memoire.setEpoch(10); 
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
	try{
	    memoire.learnFromOneExample(new Sample(inputs,outputs)); 
	}
	catch(Exception e){System.err.println("StockageNNSingle"+e); System.exit(-1);}
	
	return; 
    }
}
