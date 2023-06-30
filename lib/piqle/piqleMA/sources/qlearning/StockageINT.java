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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    StockageINT.java
 *    Copyright (C) 2005,USTL  Francesco De Comité
 *
 */

// TODO : extractarffdataset + javadoc

import environnement.State; 
import environnement.Action; 
import dataset.*; 

import java.util.HashMap; 
import java.util.Random; 

import java.util.Set; 

import java.util.Iterator; 
import java.util.HashSet; 
import java.io.*; 
import java.util.TreeMap; 
import java.util.Collection; 



/** Pour ranger les Q(s,a) entiers */

public class StockageINT extends HashMap{

    private Random generateur=new Random(); 
    private int histogramme[]=new int[1000]; 
    private int maxValue=0; 

    private int number=0; 
  
   

    /** Aller rechercher la valeur d'un couple etat-action */
    public int get(State s,Action a){
	UniteStockee us=new UniteStockee(a,s); 
	Integer db=(Integer)(this.get(us));
	if (db==null) {
	    int u=generateur.nextInt(10);  
	    this.put(new UniteStockee(a,s),new Integer(u)); 
	    if(u>maxValue) maxValue=u; 
	    number++; 
	    return u;
	}
	return db.intValue(); 
    }

    
    /** Ranger une valeur associée à un couple Etat-Action 
     Par la même occasion on mémorise l'état où on est arrivé en partant de s et en appliquant a (c'est s')*/
    public void put(State s,Action a,State sp,int qsa){
	if(sp!=null){
	    if(this.put(new UniteStockee(a,s),new Integer(qsa))==null){
		number++;
	    }
	}
	else this.put(new UniteStockee(a,s),new Integer(qsa));
    }

 
	  

    /** Debug : Affichage de la valeur V(s) d'un état*/
    public String toString(){
	HashMap prov=new HashMap(); 
	String s=number+" couples Etat/action\nAffichage de tous les Q(s,a)\n"; 
	Set keys=this.keySet(); 
	Iterator enu=keys.iterator(); 
	while(enu.hasNext()){
	    UniteStockee courante=(UniteStockee)enu.next(); 
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
	s+="Affichage des meilleurs actions selon l'état\n"; 
	while(ebis.hasNext()){
	    State s1=(State)ebis.next(); 
	    Action best=(Action)(prov.get(s1)); 
	    s+=s1+"---->"+best+" : "+this.get(s1,best)+"\n"; 
	}
	 return s;
    } // toString

    /** qsa=qsa/factor */
    public void divide(int factor){
	Set keys=this.keySet(); 
	Iterator enu=keys.iterator(); 
	while(enu.hasNext()){
	    UniteStockee courante=(UniteStockee)enu.next(); 
	    int value=((Integer)this.get(courante)).intValue();	  
	   
	    this.put(courante,new Integer(value/factor)); 
	}
    }

    private int getMaxValue(){
	Set keys=this.keySet(); 
	Iterator enu=keys.iterator(); 
	int value=0; 
	while(enu.hasNext()){
	    UniteStockee courante=(UniteStockee)enu.next(); 
	    value=((Integer)this.get(courante)).intValue();
	    if(value>maxValue) maxValue=value; 
	}
	return maxValue; 
    }

    public void normalize(){
	getMaxValue(); 
	Set keys=this.keySet(); 
	Iterator enu=keys.iterator(); 

	while(enu.hasNext()){
	    UniteStockee courante=(UniteStockee)enu.next(); 
	    int value=((Integer)this.get(courante)).intValue();	  
	    double rapport=value/(maxValue+0.0)*1000; 
	    super.put((Object)courante,new Integer((int)rapport)); 
	}
	maxValue=1000; 
    }

    public void makeHistogram(){
	histogramme=new int[1000]; 
	Set keys=this.keySet(); 
	Iterator enu=keys.iterator(); 
	maxValue=0; 
	int count=0; 
	while(enu.hasNext()){ 
	    int value=((Integer)this.get(enu.next())).intValue(); 
	    if(value>maxValue) maxValue=value; 
	    System.out.println(count+" xx "+value); 
	    count++; 
	}
	enu=keys.iterator(); 
	while(enu.hasNext()){
	    int value=((Integer)this.get(enu.next())).intValue(); 
	    histogramme[(999*value)/maxValue]++; 
	}
    }

    public void displayHistogram(){
	for(int i=0;i<1000;i++)
	    System.out.println(i+" "+histogramme[i]); 
    }
	
	
   

}

