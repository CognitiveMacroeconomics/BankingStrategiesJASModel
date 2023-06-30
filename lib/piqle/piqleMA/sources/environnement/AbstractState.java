package environnement; 

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
 *    AbstractState.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */




/** Wrapping Interface State and define some common behaviors.

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

*/

abstract public class AbstractState implements State{

    /** The game's rules */
    protected Contraintes myContraintes; 
    /** Tells which player can play.*/
    protected boolean turn; 

    
    public AbstractState(Contraintes ct){
	this.myContraintes=ct; 
    }

    public boolean isFinal(){
	return this.myContraintes.isFinal(this); 
    }

    public void setContraintes(Contraintes ct){this.myContraintes=ct;}
    
    
    public ListeActions getListeActions(){
	return myContraintes.getListeActions(this); 
    }
	
       
    public State modify(Action a){
	return this.myContraintes.etatSuivant(this,a); 
    }

    public Contraintes getContraintes(){return myContraintes;}

    public double getReward(State old,Action a){
	return this.myContraintes.getReward(old,this,a); 
}

    abstract public State copy(); 
 
  

    public int getWinner(){
	return myContraintes.whoWins(this); 
    }


   

    public  boolean getTurn(){return this.turn;} 

     public void toggleTurn(){this.turn=!this.turn; }

    public void setTurn(boolean b){ this.turn=b;}

    
    abstract public int tailleCodage(); 


    abstract public double[] codage(); 

   
  

}




    
