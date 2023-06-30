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
 *    SelectionneurNN.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */

import qlearning.StockageNN;  


/** A Neural Network is used to 'memorize' the Q(s,a) values.

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


 */

public class SelectionneurNN extends  SelectionneurMemoire{

    public SelectionneurNN(){
	memoire=new StockageNN(); 
	((StockageNN)memoire).setRescale(); 
    }

  
    public SelectionneurNN(int descLayers[]){
	memoire=new StockageNN(); 
	((StockageNN)memoire).setNN(descLayers);  
    }
	 
    public String toString(){
	return memoire.toString();
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
