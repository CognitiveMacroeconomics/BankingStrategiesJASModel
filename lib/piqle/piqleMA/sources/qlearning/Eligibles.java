package qlearning; 
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
 *    Eligibles.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */
import java.util.HashMap; 
import java.util.Set; 
import java.util.Iterator; 

import environnement.State; 
import environnement.Action; 



/** Memorizing eligibility traces.

@see algorithmes.AbstractSelectionneurQLambda

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


*/

public class Eligibles extends HashMap{

    /** Incremente (state,action) eligibility value. */
    public void incremente(State s,Action a){
	Double dv=(Double)(super.get(new UniteStockee(a,s))); 
	double value; 
	if (dv==null) value=0; 
	else
	    value=dv.doubleValue(); 
	super.put(new UniteStockee(a,s),new Double(1+value)); 
    }

    /** Read eligibility value.*/
    public double get(State s, Action a){
	Double dv=(Double)(super.get(new UniteStockee(a,s))); 
	double value; 
	if (dv==null) value=0; 
	else
	    value=dv.doubleValue(); 
	return value; 
    }


    /** Store eligibility value. */ 
    public void set(State s,Action a, double value){
	super.put(new UniteStockee(a,s),new Double(value)); 
    }

    /** Store eligibility value.*/
    public void put(UniteStockee us,double value){
	super.put(us,new Double(value)); 
    }

    /** Read eligibility value.*/
    public double get(UniteStockee us){
	Double d=(Double)(super.get(us)); 
	return d.doubleValue(); 
    }

    /** Iterator on (state,action) pairs. */
    public Iterator getIterator(){
	Set keys=this.keySet(); 
	return keys.iterator(); 
    }

}
