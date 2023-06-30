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
 *    Gagnant.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */



import environnement.State; 
import environnement.ListeActions; 
import environnement.Action; 
import dataset.Dataset; 
import java.util.Random;



/** A player which plays to win : 
<ul>
<li> If there is an action that leads to victory : Play it !</li>
<li> Otherwise, play at random (among the legal moves) </li>
</ul>

There is no learning for this algorithm, it is just intended to be a more serious opponent than the random player...

    @author Francesco De Comite (decomite at lifl.fr)
    @version $Revision: 1.0 $ 


*/

public class Gagnant implements Selectionneur{

    public Action getChoix(ListeActions l){
	if(l.size()==0) return null;
	State s=l.getState(); 
	for(int i=0;i<l.size();i++){
	    Action a=l.getAction(i); 
	    State courant=s.copy(); 
	    courant=courant.modify(a); 
	    if((courant.getWinner()==1)&&!courant.getTurn()) return a; 
	    if((courant.getWinner()==-1)&&courant.getTurn()) return a; 
	}
	    Random generator=new Random(); 
	    //System.out.println("jeu aléatoire"); 
	    return l.getAction(generator.nextInt(l.size()));  
    }// getChoix

    /** Learning is meaningless for this algorithm */
    public void apprend(State s1,State s2, double reward,Action a){}; 

    /** Nothing to reset at the beginning of an episode */
    public void nouvelEpisode(){};   

    /** No collected data... */
    public Dataset extractDataset(){return null;}

  
    
}
