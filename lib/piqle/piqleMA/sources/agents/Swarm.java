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
 *    Swarm.java
 *    Copyright (C) 2006 Francesco De Comité
 *
 */

import java.util.ArrayList; 
import environnement.Action;
import environnement.State; 
import environnement.ComposedAction;
import environnement.Contraintes; 
import environnement.ListeActions;
import dataset.Dataset; 

/** A Swarm is composed of several Agents, each one with its own Selectionneur.
 */

public class Swarm extends Agent{

    /** The list of all elementary Agents */
    protected ArrayList ListOfAgents=new ArrayList(); 

    /** Constructor : no algorithm for a Swarm : each agent has its own one*/
    public Swarm(Contraintes s){
	super(s,null); 
    }

    /** Add an agent to a swarm */
    public void add(ElementaryAgent a){
	this.ListOfAgents.add(a); 
    }

    public void setInitialState(State s){
	super.setInitialState(s);
	for(int i=0;i<this.ListOfAgents.size();i++){
	    ElementaryAgent aa=(ElementaryAgent)ListOfAgents.get(i);
	    aa.setInitialState(s); 
	}
    }

    public void nouvelEpisode(){
	for(int i=0;i<this.ListOfAgents.size();i++){
	    ElementaryAgent aa=(ElementaryAgent)ListOfAgents.get(i);
	    aa.getAlgorithm().nouvelEpisode(); 
	}

    }

    /** Asks each agent to choose its own action, collects those actions into a composed one. */
    protected Action choix(){
	ComposedAction ca=new ComposedAction(); 
	for(int i=0;i<this.ListOfAgents.size();i++){
	    ElementaryAgent aa=(ElementaryAgent)ListOfAgents.get(i);
	    ca.add(aa.choix()); 
	}
	return ca; 
    }

    /** <ul>
	<li>Apply the action, get the reward.</li>
	<li>If learning is enabled, learn from states, action, and reward. </li>
	<ul>*/
    protected Action applyAction(Action a){ 
	State old = etatCourant; 
	ComposedAction ca=(ComposedAction)a; 
	etatCourant=etatCourant.modify(a); 
	double r=etatCourant.getReward(old,a);
	reward=r; 
	//if(etatCourant.isFinal()) System.out.println("X"+etatCourant); 
	for(int i=0;i<ListOfAgents.size();i++){
		ElementaryAgent ag=(ElementaryAgent)ListOfAgents.get(i); 
		ag.setEtatCourant(etatCourant);
	}

	if(this.learningEnabled){
	    for(int i=0;i<ListOfAgents.size();i++){
		ElementaryAgent ag=(ElementaryAgent)ListOfAgents.get(i); 
		ag.getAlgorithm().apprend(ag.getFilter().filterState(old,ag.getContraintes()),
					  ag.getFilter().filterState(etatCourant,ag.getContraintes()),
					  r+ag.internalReward(),
					  (Action)ca.get(i));
	    }
	}
	return a; 
    }
	
    /** TODO : find a correct definition of dataset in this case... */
     public Dataset extractDataset(){
	return null; 
    }

}
    
    