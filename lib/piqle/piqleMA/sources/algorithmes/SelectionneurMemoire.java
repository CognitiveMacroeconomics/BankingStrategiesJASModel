package algorithmes; 
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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    SelectionneurMemoire.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */


import environnement.State; 
import environnement.ListeActions; 
import environnement.Action;
import dataset.Dataset; 

import qlearning.Rangement; 


import java.util.Random; 

/** The base of all Q-Learning-like algorithms : 

<ul>
<li> Provides a structure to memorize or compute the Q(s,a) </li>
<li> Contains all the parameters used in the Q-Learning update rules </li>
<li> Contains all the parameters used to control convergence</li>
</ul>



 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

*/

abstract public class SelectionneurMemoire implements Selectionneur{  
    /** We always need a part of randomness */
    protected static Random generateur=new Random(); 
    
    /** Memorizing or computing Q(s,a) */
    protected Rangement  memoire;

    /** Ugly : to test FOIL TODO : erase*/
    public Rangement getMemoire(){return this.memoire; }

    /** Learning rate */
    protected double alpha=0.9;
    /** discount rate */
    protected double gamma=0.9; 
    /** balance between exploration and exploitation */
    protected double epsilon=0.5;
    /** Factor by which we multiply alpha at each learning step (geometric decay)<br>
	<i> Note : geometric decay does no insure convergence.</i>
    */
    protected double decayAlpha=0.999999999; 
    /** Number of learning steps */
    protected double count=1.0; 
    /** Power of decay when alpha=1/(count^alphaDecayPower)
	@see <a href="http://www.cs.tau.ac.il/~evend/papers/ql-jmlr.ps">Learning rates for Q-Learning</a>
    */
    protected double alphaDecayPower=0.8; 

    public void setAlpha(double a){this.alpha=a; }
    public void setGamma(double g){this.gamma=g;}
    public void setEpsilon(double e){this.epsilon=e;}
    public void setDecay(double d){this.decayAlpha=d;}
    public void setAlphaDecayPower(double a){this.alphaDecayPower=a;}

    public double getAlpha(){return alpha;}
    public double getGamma(){return gamma;}
    public double getEpsilon(){return epsilon;}
    public double getDecay(){return this.decayAlpha;}
    public double getAlphaDecayPower(){return this.alphaDecayPower;}

    /** Alpha decay methods
	<ul>
	<li> Geometric : use decayAlpha </li>
	<li> Exponential : use alphaDecayPower (default)</li>
	<ul>
    */
    protected boolean geometricDecay=false; 
    
    
    public void setGeometricAlphaDecay(){geometricDecay=true; }
    public void setExponentialAlphaDecay(){geometricDecay=false;}

    /** How convergence is controlled ? 
	<ul>
	<li> true : alpha decays geometrically </li>
	<li> false : alpha decays exponentially</li>
	</ul>
    */
    public boolean getGeometricDecay(){
	return geometricDecay;}

    /** How to implement randomness ? 
	<ul> 
	<li>epsilon-greedy</li>
	<li>Roulette wheel selection</li>
	<li>Boltzmann </li>
	<ul>
	Roulette wheel or Boltzmann selection makes epsilon useless.
    */
    protected boolean rouletteWheel=false; 
    protected boolean epsilonGreedy=true; 
    protected boolean boltzmann=false; 
    protected double tau=0.5; 

    public void setTau(double t){this.tau=t;}
    public double getTau(){return this.tau;}

    public void setRouletteWheel(){
	rouletteWheel=true; 
	epsilonGreedy=false; 
	boltzmann=false;
    }

    /** Set the epsilon-greedy policy */
    public void setEpsilonGreedy(){
	epsilonGreedy=true; 
	rouletteWheel=false;
	boltzmann=false; 
    }

    /** Set Boltzmann selection */
    public void setBoltzmann(){
	epsilonGreedy=false; 
	rouletteWheel=false;
	boltzmann=true; 
    }

   
    public boolean getRouletteWheel(){
	return rouletteWheel;
    }
    public boolean getEpsilonGreedy(){
	return epsilonGreedy; 
    }
    public boolean getBoltzmann(){
	return boltzmann; 
    }


    /** Finding Q(s,a) */
    public double getValue(State s,Action a){
	return memoire.get(s,a); 
    }

   

    /** Nothing to reset at this level. */
    public void nouvelEpisode(){}; 
    
    /** Learning from experience.
	@param s1 the start state.
	@param s2 the arrival state.
	@param reward immediate reward.
	@param a the chosen action.

	<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node65.html">Sutton & Barto p 149 Q-Learning</a>
    */
    public void apprend(State s1,State s2, double reward,Action a){
	if(geometricDecay)
	    alpha*=decayAlpha;
	else{
	    alpha=1/Math.pow(count+1.0,this.alphaDecayPower);
	}

	count++; 
	double qsa=memoire.get(s1,a);
	ListeActions la=s2.getListeActions();
	if(la.size()!=0){
	double maxqsap=memoire.get(s2,la.getAction(0)); 
	for(int i=1;i<la.size();i++){
	    Action aprime=la.getAction(i); 
	    double qsap=memoire.get(s2,aprime);
	    if(qsap>maxqsap) maxqsap=qsap; 
	}
	qsa+=alpha*(reward+gamma*maxqsap-qsa); 
	memoire.put(s1,a,s2,qsa);
	}
	else {
	    memoire.put(s1,a,s2,qsa+alpha*(reward-qsa)); 
	}
    }

    /** Choose one of the legal moves */
    public Action getChoix(ListeActions l){
	if(rouletteWheel) return getChoix2(l); 
	if(epsilonGreedy) return getChoix1(l); 
	if(boltzmann) return getChoix3(l); 
	return null;
    }

    /** Roulette Wheel selection of the next action : the probability for an action to be chosen is relative to its Q(s,a) value.

    TODO DEBUG : not valid if Q(s,a) can be negative !!!*/

    protected Action getChoix2(ListeActions l){
	if(l.size()==0) return null; 
	State s=l.getState();
	double sum=0; 
	for(int i=0;i<l.size();i++) sum+=memoire.get(s,l.getAction(i))+1; 
	double choix=generateur.nextDouble()*sum; 
	int indice=0;
	double partialSum=memoire.get(s,l.getAction(indice))+1; 
	while(choix>partialSum){
		  indice++; 
		  partialSum+=1+memoire.get(s,l.getAction(indice)); 
	}
	return l.getAction(indice);
    }

    /** Boltzmann selection */
     protected Action getChoix3(ListeActions l){
	if(l.size()==0) return null; 
	State s=l.getState();
	double sum=0; 
	double tab[]=new double[l.size()]; 
	for(int i=0;i<l.size();i++) {
	    sum+=Math.exp(memoire.get(s,l.getAction(i))/this.tau); 
	    tab[i]=sum; 
	}    
	double choix=generateur.nextDouble()*sum; 
	for(int i=0;i<l.size();i++){
	    if(choix<=tab[i]) return l.getAction(i);
	}
	System.err.println("Wrong"); 
	System.exit(-1); 
	return null;
    }



    /** Epsilon-greedy choice of action.
     */
    protected Action getChoix1(ListeActions l){
	if(l.size()==0) return null;
	State s=l.getState(); 	
	Action meilleure=l.getAction(0); 
	double maxqsap=memoire.get(s,meilleure); 
	for(int i=1;i<l.size();i++){
	    Action a=l.getAction(i); 
	    double qsap=memoire.get(s,a);
	    if(qsap>maxqsap) {
		maxqsap=qsap;  
		meilleure=a;
	    }
	}
	// Beginning the method with this test should speed up the program
	if(generateur.nextDouble()>this.epsilon) return meilleure; 
	else 
	    return l.getAction(generateur.nextInt(l.size())); 
    }

    /** Auxiliary/debug method : find the best action from a state.*/
    public Action bestAction(State s){
	ListeActions l=s.getListeActions(); 
	Action meilleure=l.getAction(0); 
	double maxqsap=memoire.get(s,meilleure); 
	for(int i=1;i<l.size();i++){
	    Action a=l.getAction(i); 
	    double qsap=memoire.get(s,a);
	    if(qsap>maxqsap) {
		maxqsap=qsap; 
		meilleure=a;
	    }
	}
	return meilleure; 
    }
   

    public String toString(){
	return memoire.toString();
    }

    
    public Dataset extractDataset(){
	return memoire.extractDataset(); 
    }

 

    
}
