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
 *    SelectionneurWatkins.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */





import environnement.Action; 
import environnement.State; 
import environnement.ListeActions; 

import java.util.Iterator;
import qlearning.Eligibles; 
import qlearning.UniteStockee;

import qlearning.Stockage; 


/** 

<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node78.html">Q(lambda), version Watkins </a>


 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/


public class SelectionneurWatkins extends AbstractSelectionneurQLambda{

    /** Memorize the next action to perform. */
    protected Action aprime=null;

    /** The number of learning steps achieved. */
    protected double count=1.0; 
  

    public SelectionneurWatkins(double lambda){
	super(lambda); 
	memoire=new Stockage(); 
    }

    /** Learning from experience.
	@param s1 the start state.
	@param s2 the arrival state.
	@param reward immediate reward.
	@param a the chosen action.
<br>
       <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node78.html">Algorithm as described is Sutton & Barto, page 184, figure 7.14</a>
<br>
	
    */

  public void apprend(State s1,State s2, double reward,Action a){
      Action aetoile;
      double delta;  
     
      if(geometricDecay)
	  alpha*=decayAlpha;
      else
	  alpha=1/Math.pow(count+1.0,this.alphaDecayPower);
      
      count++;
      if(lambda!=0){ 
	  if(!replace)
	      eligibles.incremente(s1,a); 
	  else
	      eligibles.set(s1,a,1); 
      }
      if(s2.isFinal()) {
	  delta=reward-memoire.get(s1,a); 
	  aprime=null; 
	  aetoile=null; 
      }
      else {
      aprime=this.getChoixInterne(s2.getListeActions()); 
      aetoile=this.bestAction(s2); 
      delta=reward+gamma*memoire.get(s2,aetoile)-memoire.get(s1,a); 
      }
      // update all state-action value estimates and eligibility traces
      
     
      // Eligibility
      // A first loop to update  Q(s,a)
      	Iterator parcours=eligibles.getIterator(); 
	while(parcours.hasNext()){
	 UniteStockee courante=(UniteStockee)parcours.next();
	 double valeur=eligibles.get(courante); 
	 double old=memoire.get(courante.getState(),courante.getAction()); 
	 memoire.put(courante.getState(),courante.getAction(),s2,old+alpha*delta*valeur); 
	}
	if((aetoile!=null)&&(aetoile.equals(aprime))){
	    parcours=eligibles.getIterator(); 
	    while(parcours.hasNext()){
		UniteStockee courante=(UniteStockee)parcours.next(); 
		double valeur=eligibles.get(courante);
		eligibles.put(courante,valeur*gamma*lambda); 
	    }
	}
	// reset eligibility traces
	else this.reset();  
 }// apprend


   
 private Action getChoixInterne(ListeActions l){
     if(epsilonGreedy) return getChoix1(l); 
     if(boltzmann) return getChoix3(l); 
     if(rouletteWheel) return getChoix2(l); 
     return null;   
 }
    

    public Action getChoix(ListeActions l){
	if(aprime!=null) return aprime; 
	if(epsilonGreedy) return getChoix1(l); 
	if(boltzmann) return getChoix3(l); 
	if(rouletteWheel) return getChoix2(l); 
	return null;
    } 

    
   
    public void reset(){
	super.reset(); 
	aprime=null;
    }

    public void nouvelEpisode(){
	aprime=null;
    }
	
}
