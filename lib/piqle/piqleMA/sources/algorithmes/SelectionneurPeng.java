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
 *    SelectionneurPeng.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */




import environnement.State; 
import environnement.ListeActions; 
import environnement.Action;
import dataset.Dataset; 

import qlearning.Rangement;  
import qlearning.Eligibles; 
import qlearning.UniteStockee;

import java.util.Random; 
import java.util.Iterator; 

/** Peng's implementation of Q(lambda)
<br>
<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node78.html"> Sutton and Barto chap 7.6 page 182</a><br>

Reference : <a href="ftp://ftp.ccs.neu.edu/pub/people/rjw/qlambda-ml-96.ps">Peng et Williams 1996</a>

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 



*/

 public class SelectionneurPeng extends AbstractSelectionneurQLambda{

     public SelectionneurPeng(double l){
	 super(l);
     }
   
    
     /** Learning from experience.
	@param s1 the start state.
	@param s2 the arrival state.
	@param reward immediate reward.
	@param a the chosen action.
<br>
	<a href="ftp://ftp.ccs.neu.edu/pub/people/rjw/qlambda-ml-96.ps">Peng et Williams 1996</a><br>

	The implemented algorithm is described in the above mentionned paper, at page 5

    */
    public void apprend(State s1,State s2, double reward,Action a){
	if(geometricDecay)
	    alpha*=decayAlpha;
	else
	    alpha=1/Math.pow(count+0.0,this.alphaDecayPower);
	count++; 
	double maxqsap=0; // Vhat(x_{t+1})
	double maxq=0;    // Vhat(x_t) 

	ListeActions la=s2.getListeActions();
	if(la.size()!=0){
	maxqsap=memoire.get(s2,la.getAction(0)); 
	for(int i=1;i<la.size();i++){
	    Action aprime=la.getAction(i); 
	    double qsap=memoire.get(s2,aprime);
	    if(qsap>maxqsap) maxqsap=qsap; 
	}// Peng et William : Vhat(x_{t+1})

	}
	//Peng et William : Compute Vhat(x_t)
	ListeActions lb=s1.getListeActions(); 
	if(lb.size()!=0){
	    maxq=memoire.get(s1,lb.getAction(0)); 
	    for(int i=1;i<lb.size();i++){
		Action aprime=lb.getAction(i); 
		double qsap=memoire.get(s1,aprime); 
		if(qsap>maxq) maxq=qsap; 
	    }
	}// V_hat(x_t) is comuted
	// Eligibilities
	double qsa=memoire.get(s1,a);
	double et=reward+gamma*maxqsap-maxq;
	double etprime=reward+gamma*maxqsap-qsa; 
	Iterator parcours=eligibles.getIterator(); 
	while(parcours.hasNext()){
	    UniteStockee courante=(UniteStockee)parcours.next(); 
	    double valeur=eligibles.get(courante); 
	    double old=memoire.get(courante.getState(),courante.getAction()); 
	    valeur*=lambda*gamma; 
	    memoire.put(courante.getState(),courante.getAction(),null,old+alpha*valeur*et); 
	    eligibles.put(courante,valeur); 
	}
	
	memoire.put(s1,a,s2,qsa+alpha*etprime); 
	if(!replace)
	    eligibles.incremente(s1,a); 
	else eligibles.set(s1,a,1); 

	
    }// apprend

   public void setRouletteWheel(){
       System.err.println("Warning : Roulette wheel selection not implemented in Peng(lambda)");  
       epsilonGreedy=true; 
       rouletteWheel=false;
       boltzmann=false; 
       
    }

    public void setBoltzmann(){
	System.err.println("Warning : Boltzmann selection not implemented in Peng(lambda)");  
	epsilonGreedy=true; 
	rouletteWheel=false;
	boltzmann=false; 
    }


     public Action getChoix(ListeActions l){
	 Action pro=null;
	if(epsilonGreedy) pro=getChoix1(l); 
	if(boltzmann) pro=getChoix3(l); 
	if(rouletteWheel) pro=getChoix2(l); 
	if(!pro.equals(meilleure(l))) this.reset(); 
	return pro; 
    }
    
    
    public Action meilleure(ListeActions l){
	if(l.size()==0) return null; 
	State s=l.getState(); 	
	Action m=l.getAction(0); 
	double maxqsap=memoire.get(s,m); 
	for(int i=1;i<l.size();i++){
	    Action a=l.getAction(i); 
	    double qsap=memoire.get(s,a);
	    if(qsap>maxqsap) {
		maxqsap=qsap; 
		m=a;
	    }
	}
	return m;
	
    }

   
     
}
    
