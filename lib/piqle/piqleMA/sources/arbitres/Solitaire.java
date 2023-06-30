package arbitres; 
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
 *    Solitaire.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */
import agents.Agent; 
import environnement.State; 
import environnement.Action; 
import environnement.AbstractContraintesSolitaire; 

/** Control of a single player game (or problem)


@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


 */

public class Solitaire{

   

    /** The player */
    private Agent joueur;

    /** The universe to whom we have to send graphical informations */
    private AbstractContraintesSolitaire universe; 

    /** Saying whether a graphical interface is needed or not */
    private boolean graphical=false; 

    public void setGraphical(){
	if(!this.graphical){
	    this.graphical=true;
	    this.universe.setGraphics(); // open graphical display
	}

    }
    public void clearGraphical(){
	if(this.graphical){
	    this.graphical=false;
	    this.universe.closeGraphics(); 
	}
    }

    /** Reward from the current episode */
    private double totalReward; 

   

    /** Maximal length of an episode */
    private int maxIter=1000; 

    /** Read the maximal length of an episode */
    public int getMaxIter(){return this.maxIter;}

    /** Change the maximal length of an episode. */
    public void setMaxIter(int t){
	if(t>0) maxIter=t;}

     /** Verbosity */
    private boolean verbosity=false; 

    /** Verbose*/
    public void setVerbosity(){verbosity=true; }
    /** Mute*/
    public void unsetVerbosity(){verbosity=false;}

    /** */
    public Solitaire(Agent joueur){
	this.joueur=joueur; 
	this.universe=(AbstractContraintesSolitaire)this.joueur.getContraintes(); 
    }

    /** Read the reward earned during the last episode */
    public double getRewardForEpisode(){
	return totalReward;
    }

    /** Beginning from an initial state, play the game until : 
	<ul>
	<li> A final state</li>
	<li> The maximal length for an episode</li>
	</ul>

     	@return Length of episode
    */
    public int episode(State initial){
	State courant=(State) initial.copy();  
	this.joueur.setInitialState(initial); 
	this.joueur.nouvelEpisode(); 
	totalReward=0.0; 
	// Initializing the graphical display if needed
	if(this.graphical) this.universe.clearGraphics(); 
	if (verbosity) System.out.println("************\n"+"Starting State "+this.joueur.getEtatCourant()); 

	if(this.graphical) this.universe.sendState(initial); 
	for(int i=0; i<this.maxIter;i++){
	    State old=this.joueur.getEtatCourant(); 
	   
	    if(verbosity) System.out.println("************\nCurrent State  : "+this.joueur.getEtatCourant()); 
	    Action c=this.joueur.agir();
	    totalReward+=this.joueur.getLastReward(); 
	    if (verbosity) System.out.println("Action : "+c+" "+totalReward+" "+i); 
	    if(verbosity) System.out.println("State Reached  : "+this.joueur.getEtatCourant()+"\n************"); 
	    if(this.graphical) this.universe.sendState(this.joueur.getEtatCourant()); 
	    if (this.joueur.getEtatCourant().isFinal()) {
		//	System.out.println("# "+this.joueur.getEtatCourant()); 
		if(verbosity)
		    System.err.println("end !"); 
		return i+1; 
	    } 	   
	}
	//if(this.graphical) this.universe.closeGraphics(); 
	return this.maxIter; 
    }
}
	
