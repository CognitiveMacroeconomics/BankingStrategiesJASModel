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
 *    SelectionneurAleatoire.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */

import java.util.Random; 

import environnement.ListeActions; 
import environnement.State; 
import environnement.Action; 
import dataset.Dataset; 




/** A minimal opponent to compare with all the other algorithms

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


 */

public class SelectionneurAleatoire implements Selectionneur{

    private Random generateur=new Random(); 

     /** Chooses an action from the list of legal moves.*/
    public Action getChoix(ListeActions l){
	int choix=generateur.nextInt(l.size()); 
	return l.getAction(choix); 
    }
	
    /** Resettings at the begin of an episode. */
    public void nouvelEpisode(){}; 

    /** No learning when you play at random...
    */
    public void apprend(State s1,State s2, double reward,Action a){} 

    /** No data to extract...*/
    public Dataset extractDataset(){return null;}

  

}
