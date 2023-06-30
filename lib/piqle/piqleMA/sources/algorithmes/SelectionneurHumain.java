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
 *    SelectionneurHumain.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */





import environnement.State; 
import environnement.ListeActions; 
import environnement.Action; 

import dataset.Dataset; 

import clavier.Clavier; 



/**
   A Generic (i.e. available for any problem)  Human Player.<br>
   This HCI is minimal :  you are asked to choose in a list of all possible moves.

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

 */


public class SelectionneurHumain implements Selectionneur{

    /** The learning phase is left to the human... */
	public void apprend(State s1,State s2, double reward,Action a){}

    /** Lists the legal moves, ask human to choose */
	public Action getChoix(ListeActions l){
	    if(l.getAction(0).isNullAction()){
		System.out.println("You must Pass"); 
		return l.getAction(0);
	    }
	State s=l.getState(); 
	while(true){
	    System.out.println("Choose in this list : "); 
	    for(int i=0;i<l.size();i++)
		System.out.println(i+" "+l.getAction(i));
	    int j=Clavier.readInt(); 
	    if((j>=0)&&(j<l.size())) return l.getAction(j); 
	}
	}// getChoix

     /** No data collected... */
    public Dataset extractDataset(){return null;}

   
    
    /** Nothing to reset */
    public void nouvelEpisode(){}; 

    }
