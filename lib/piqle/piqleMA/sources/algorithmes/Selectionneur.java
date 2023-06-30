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
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    Selectionneur.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */
import environnement.State; 
import environnement.ListeActions; 
import environnement.Action; 

import dataset.Dataset; 


import java.io.Serializable; 

/** All we ask an algorithm to be able to do is : 

<ul>
<li> Extract an action chosen among the list of possible moves</li>
<li> Learn from its experience </li>
</ul>



 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 



*/

public interface  Selectionneur extends Serializable{
    /** Choose an action in the list of legal moves */
    public Action getChoix(ListeActions l); 

    /** Learn 
	@param s1 The state the agent is in before the action is performed.
	@param s2 The state the agent goes to when the action is performed. 
	@param reward The reward obtained for this move.
	@param a The action the agent took.
    */
    public void apprend(State s1,State s2, double reward,Action a);  

    /** Format the experience in a shape usable by Neural Networks*/
    public Dataset extractDataset(); 

   


     /** There might be some things to do at the beginning of each episode... */
    public void nouvelEpisode(); 


}
