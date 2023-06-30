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
 *    Contraintes.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */





import java.io.Serializable; 

    /**The game's rules : 
       <ul>
       <li> Gives the list of possible actions from a given state.</li>
       <li> Computes the next state, given a start state and an action.</li>
       <li> Knows when a state is final.</li>
       <li> Knows whether a state is a winning one.</li>
       </ul>
       
    

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

    */

    public interface Contraintes extends Serializable{

	/**  Gives the list of possible actions from a given state. */
	public ListeActions getListeActions(State s); 

	/**  Computes the next state, given a start state and an action. */
	public State etatSuivant(State s,Action a); 


	public State defaultInitialState(); 

	
	public double getReward(State s1,State s2,Action a); 


	public boolean isFinal(State s); 
	

	/** Who won ?  
	    <ul>
	    <li> Two-players game :  </li>
	    <ul>
	    <li> Player One : -1</li>
	    <li> Tie : 0 </li>
	    <li> Player Two : 1 </li>
	    </li>
	    </ul>
	    <li> Single-player game : </li>
	    <ul>
	    <li> Win : -1 </li>
	    <li> Null: 0    </li>
	    <li> Loose: 1 </li>
	    </ul>
	    </ul> */
	public int whoWins(State s);
	    
    }
