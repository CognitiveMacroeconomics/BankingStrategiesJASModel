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
 *    SelectionneurQL.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */

import qlearning.Stockage;   


/** The basic Q-Learning algorithm. 

	<a href="http://www.cs.ualberta.ca/~sutton/book/ebook/node65.html">Sutton & Barto p 149 Q-Learning</a>

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


*/

public class SelectionneurQL extends  SelectionneurMemoire{

    public SelectionneurQL(){
	memoire=new Stockage(); 
    }

    /** When <code>states</code> and <code>actions</code> are memorized, one can enumerate them and build histograms showing the distribution of Q(s,a) values


*/
    
    public void showHistogram(){
	((Stockage)memoire).makeHistogram(); 
	((Stockage)memoire).displayHistogram(); 
    }

	
}    
