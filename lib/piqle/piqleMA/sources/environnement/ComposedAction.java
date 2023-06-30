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
 *    ComposedAction.java
 *    Copyright (C) 2006 Francesco De Comité
 *
 */

import java.util.ArrayList; 
import java.util.Iterator; 

/** A container of elementary µAgent actions */

public class ComposedAction extends ArrayList implements Action{


	/** Clone an Action. */
    public Action copy(){
	ComposedAction neuf=new ComposedAction(); 
	Iterator en=this.iterator(); 
	while(en.hasNext())
	    neuf.add(en.next()); 
	return neuf; 
    }



	// For use with Neural Networks
	
	/** Size of an Action's coding (for NN). */
    public int tailleCodage(){return 0;}

	/** Action's coding (for NN). */
    public double[] codage(){return null;}


	/** No action */
    public boolean isNullAction(){return false;}

	/** Q-Learning memorizing techniques use hashcoding : it is necessary to redefine it for each problem/game */
    public int hashCode(){return super.hashCode();}


	/** Q-Learning memorizing techniques use equality: it is necessary to redefine it for each problem/game */
    public boolean equals(Object o){return super.equals(o); }


}