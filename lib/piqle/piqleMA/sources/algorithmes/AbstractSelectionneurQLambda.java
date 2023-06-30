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
 *    AbstractSelectionneurQLambda.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */




import environnement.State; 
import environnement.ListeActions; 
import environnement.Action;

import qlearning.Eligibles; 

/** 
    Common settings and behaviors of Q(lambda) algorithms<p>

<a href="http://www.cs.ualberta.ca/~sutton/book/node78.html"> Sutton and Barto chap 7.6 page 182</a><br>

    Concrete implementations are following Watkin's or Peng's definitions.<p>

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
    
*/



abstract public class AbstractSelectionneurQLambda extends SelectionneurQL{
 /** The new parameter for that family of algorithms */
    protected double lambda;

    /** Saving and retrieving eligibility traces */
    protected Eligibles eligibles; 

    /** Lambda must be set at the beginning */
    public AbstractSelectionneurQLambda(double l){
	super(); 
	this.lambda=l;
	this.eligibles=new Eligibles(); 
    }

     /** Using or not replacement of traces <p>

     <a href="http://www.cs.ualberta.ca/~sutton/book/node80.html"><i>Sutton & Barto chap 7.8 p186</i></a>*/
     protected boolean replace=false; 
     
     /** Setting the replace boolean */
     public void setReplace(){replace=true;}
     
    /** Unsetting the replace boolean */
     public void unsetReplace(){this.replace=false;}
     
     
    /** Access lambda */
     public double getLambda(){return this.lambda;}
    /** Access lambda */
     public void setlambda(double l){this.lambda=l;}

    
    /** Reset eligibility traces */
    public void reset(){this.eligibles=new Eligibles(); }
    
 
    abstract public void apprend(State s1,State s2, double reward,Action a);

	
 
    public void nouvelEpisode(){
	this.reset();
    }

    abstract public Action getChoix(ListeActions l); 
}
