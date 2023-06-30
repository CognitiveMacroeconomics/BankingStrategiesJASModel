package agents; 

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
 *    Agent2Joueurs.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */



import environnement.State; 
import environnement.Action; 
import environnement.ListeActions; 
import environnement.Contraintes; 




import algorithmes.Selectionneur; 

/** Adversarial agent must wait the answer of its opponent before learning 
    (afterstates, <a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node68.html">Sutton & Barto page 156</a>)
@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
 
 */

public class Agent2Joueurs extends Agent{

    /** Remember the last action. */
    protected Action lastAction; 
    /** Remember the last state. */
    protected State old; 
   

      /** Place this agent somewhere in the environment, set its choice algorithm. */
    public Agent2Joueurs(Contraintes  s, Selectionneur al){ 
	super(s,al); 
    }

    protected Action applyAction(Action a){
	old=etatCourant.copy(); 
	this.etatCourant=etatCourant.modify(a); 
	this.lastAction=a.copy(); 
	return a; 
    } 

  

    /** The referee (<i>arbitre</i>) tells the agent the new state of the environment after its opponent played.
     @param s The state of the environment after the opponent played.
*/
    public void getInformed(State s){
	if(old!=null){
	    double r=s.getReward(old,lastAction); 
	    reward=r; 
	    if(this.learningEnabled){
		algorithme.apprend(old,s,r,lastAction); 
	    }
	this.old=etatCourant; 
	
	}this.etatCourant=s;
    }

   
    /** Same as above, technical difference when the play comes to an end : we need to change the <i>turn indicator</i> back.*/
    public void getInformedFinal(State s){ 
	State s1=s.copy();
	s1.toggleTurn(); 
	getInformed(s1); 
    }
}
