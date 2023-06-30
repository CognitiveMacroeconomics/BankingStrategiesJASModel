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
 *    State.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */



import java.io.Serializable; 



    /** Methods for a Generic State. */
    public interface State extends Cloneable,Serializable{
	
	
	/** The list of all legal moves starting from the currrent state. */
	public ListeActions getListeActions(); 
	

	public void setContraintes(Contraintes c); 
	
	/** Change a state according to the action. */
	public State modify(Action a); 

	
	/** Read the universe the state is in. */
	public Contraintes getContraintes(); 

	/** Reward of (old,this,a) */
	public double getReward(State old,Action a); 

	/** Is this state final ?  */
	public boolean isFinal(); 

	/** Clone. */
	public State copy(); 



	/** Who is the winner ? (in a final state) */
	public int getWinner(); 

       
	/** For two-players game : which one can play ? */
	public  boolean getTurn(); 
	/** Let the other player play.*/
	public  void toggleTurn(); 
	/** Choose the next player.*/
	public void setTurn(boolean b);

	/* Functions for use of Neural Networks. */
	
	/** Size of State's coding (for NN). */ 
	public int tailleCodage(); 

	/** State's coding (for NN). */
	public double[] codage(); 

	/** Q-Learning memorizing techniques use hashcoding : it is necessary to redefine it for each problem/game */
	public int hashCode(); 


	/** Q-Learning memorizing techniques use equality: it is necessary to redefine it for each problem/game */
	public boolean equals(Object o); 




}

    
