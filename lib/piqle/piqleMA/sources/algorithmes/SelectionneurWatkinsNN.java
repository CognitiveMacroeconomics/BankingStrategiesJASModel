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
 *    SelectionneurWatkinsNNSinglejava
 *    Copyright (C) 2004 Francesco De Comité
 *
 */

import qlearning.StockageNN; 


/** Watkin's algorithm to manage and compute Q(s,a), but Q(s,a) are learned thanks to a Neural Network. */


public class SelectionneurWatkinsNN extends SelectionneurWatkins{

  

  
    public SelectionneurWatkinsNN(double l){
	super(l); 
	memoire=new StockageNN(); 
    }

    /** Auxiliary/Debug : makes it possible to inspect the underlying Neural Network.*/
      public double getWeight(int i, int j,int k){
	 return ((StockageNN)memoire).getWeight(i,j,k); 
     }
     

    /** Auxiliary/Debug : makes it possible to inspect the underlying Neural Network.*/
     public int getSizeOfLayer(int i){
	 return ((StockageNN)memoire).getSizeOfLayer(i);
     }


}
