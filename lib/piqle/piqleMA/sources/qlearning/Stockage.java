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
 *    Stockage.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */

import environnement.State; 
import environnement.Action; 
import dataset.*; 

import java.util.HashMap; 
import java.util.Random; 

import java.util.Set; 

import java.util.Iterator; 
import java.io.*; 
import java.util.TreeMap; 
import java.util.Collection; 




/** Memorizing  Q(s,a) in HashMap. The key is the pair (state, value). */

public class Stockage extends HashMap implements Rangement{

    private Random generateur=new Random(); 
    /** Number of items stored */
    private int number=0; 
    private int histogramme[]=new int[1000]; 
    private static int numer=0;  
    private int ordre; 

    public Stockage(){
	super(); 
	// numero du stockeur
	this.ordre=numer; 
	numer++;
    }
  
    /** Read Q(s,a) */
    public double get(State s,Action a){
	if((a==null)||(s==null)) return 0; 
	UniteStockee us=new UniteStockee(a,s); 
	Double db=(Double)(this.get(us));
	if (db==null){
	    number++; 
	    double u=10.0; 
	    //double u=generateur.nextDouble();  
	     this.put(new UniteStockee(a,s),new Double(u)); 
	     // if(number%100==0) System.err.println("XXX "+ordre+" "+number); 
	    return u;
	}
	return db.doubleValue(); 
    }

     
    /** Store Q(s,a) : change its value if already there.
     */
    public void put(State s,Action a,State sp,double qsa){
	if(sp!=null){
	    if(this.put(new UniteStockee(a,s),new Double(qsa))==null)
		{ 
		    number++;
		}
	   
	    }
	    else {
	    this.put(new UniteStockee(a,s),new Double(qsa));	    
	    }
    }

   

    /** To monitor the evolution of Q(s,a) values.
     */
    public void makeHistogram(){ 
	Set keys=this.keySet(); 
	Iterator enu=keys.iterator(); 
	double min=10000.0; 
	double max=-10000.0; 
	double sum=0.0;
	int number=0; 
	while(enu.hasNext()){
	    UniteStockee courante=(UniteStockee)enu.next(); 
	   //Double db=(Double)this.get(courante.getState(),courante.getAction());
	    Double db=new Double (this.get(courante.getState(),courante.getAction()));
	    double d=db.doubleValue(); 
	    if(d>max) max=d; 
	    if(d<min) min=d; 
	    //sum+=db; 
	    sum += d;
	    number++; 
	}
	double moyenne=sum/number; 
	double ecartType=0.0; 
       enu=keys.iterator(); 
	while(enu.hasNext()){ 
	    UniteStockee courante=(UniteStockee)enu.next(); 
	    //Double db=(Double)this.get(courante.getState(),courante.getAction());
	    Double db=new Double(this.get(courante.getState(),courante.getAction()));
	    double d=db.doubleValue(); 
	    ecartType+=(d-moyenne)*(d-moyenne); 
	    histogramme[(int)Math.floor(999*(d-min)/(max-min))]++; 
	}
	
    }// makeHistogram

   public void displayHistogram(){
	for(int i=0;i<1000;i++)
	    System.out.println(i+" "+histogramme[i]); 
    }
	  

    /** Print all available information : cast it to your needs and output formats.*/
    public String toString(){
	HashMap prov=new HashMap(); 
	HashMap local=new HashMap(this); 
	Set keys=local.keySet(); 
	String s=keys.size()+" state/action pairs \nListing of ALL  Q(s,a)\n";  
	Iterator enu=keys.iterator(); 
	UniteStockee courante=null; 
	while(enu.hasNext()){
	    courante=(UniteStockee)enu.next(); 
	    s+=courante.getState()+" "+courante.getAction()+" "+this.get(courante.getState(),courante.getAction())+"\n";
	    Action bestAct=(Action)(prov.get(courante.getState())); 
	    if(bestAct==null) prov.put(courante.getState(),courante.getAction()); 
	    else{
		if(this.get(courante.getState(),courante.getAction())>
		   this.get(courante.getState(),bestAct))
		    prov.put(courante.getState(),courante.getAction());
	    }
	
	    
	}
	Set bk=prov.keySet(); 
	Iterator ebis=bk.iterator();
	s+="Best values Q(s,a) for given s and a\n"; 
	while(ebis.hasNext()){
	    State s1=(State)ebis.next(); 
	    Action best=(Action)(prov.get(s1)); 
	    s+=s1+"---->"+best+" : "+this.get(s1,best)+"\n"; 
	}
	 return s;
    } // toString


    /** Extracts dataset for use with local NN */ 
    public Dataset extractDataset(){
	Dataset forNN=new Dataset(); 
	Set keys=this.keySet(); 
	Iterator enu=keys.iterator(); 
	while(enu.hasNext()){
	    UniteStockee courante=(UniteStockee)enu.next(); 
	    State etat=courante.getState(); 
	    Action act=courante.getAction(); 
	    double u[]=new double[etat.tailleCodage()+act.tailleCodage()]; 
	    System.arraycopy(etat.codage(),0,u,0,etat.tailleCodage()); 
	    System.arraycopy(act.codage(),0,u,etat.tailleCodage(),act.tailleCodage()); 
	    double v[]=new double[1]; 
	    v[0]=(1.0+this.get(etat,act))/2.0;
	    forNN.add(new Sample(u,v)); 
	}
	return forNN;    
    }// extractDataset

    
}

