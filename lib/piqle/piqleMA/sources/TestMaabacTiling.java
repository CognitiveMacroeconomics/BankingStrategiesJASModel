


import environnement.AbstractContraintesSolitaire; 
import environnement.State; 
import agents.Swarm; 
import algorithmes.*;
import arbitres.Solitaire; 

import maabac.ArmN;
import maabac.Muscle; 
import maabac.ElementaryMuscleTiling;
import maabac.MuscleFilter; 

import java.util.Random; 

public class TestMaabacTiling{

    public static void main(String argv[]){
	Random gene=new Random(); 
	int nbseg=2; 
	double posx=1.8;
	double posy=0.5; 

	/* Target's position and radius */
	ArmN bras=new ArmN(nbseg,posy,posx,0.2); 
	Swarm essaim=new Swarm(bras); 
	SelectionneurTDFA guru[]=new SelectionneurTDFA[2*nbseg]; 
	
	double epsilon=0.5; 
	int contr[]=new int[2*nbseg];
	int maxi[]=new int[2*nbseg];
	for(int i=0;i<2*nbseg;i++) maxi[i]=50; 
	bras.setState(contr,maxi); 
	State depart=bras.defaultInitialState(); 
	for(int i=0;i<2*nbseg;i++){
	    guru[i]=new SelectionneurTDFA(0.9); 
 	    guru[i].setGamma(0.5);  
	    guru[i].setAlpha(0.1);  
	    guru[i].setGeometricAlphaDecay(); 
	    guru[i].setDecay(1.0);
	    guru[i].setBoltzmann();
	    
	    essaim.add(new Muscle(new ElementaryMuscleTiling(),
				  guru[i],
				  new MuscleFilter(i),
				  depart)); 

	}
        

	Solitaire arbitre=new Solitaire(essaim); 
	arbitre.setMaxIter(1000); 
	int repet=1; 
	int maxrepet=0;
	boolean serie=false; 
	//arbitre.setVerbosity();
	//arbitre.setGraphical(); 
	double total=0.0; 
	int birep=0;
	for(int i=1;i<100000;i++){
	    int u=arbitre.episode(depart); 
	    if(u<500) {
		if(serie) repet++; 
		else repet=1; 
		serie=true; 
		maxrepet++; 
		if(repet==100){
		    arbitre.setGraphical();
		}
		
	
	
	    }
	    else serie=false;
	
	     
	  
	    total+=u; 
	    if(i%100==0) 
		System.out.println(i+" "+u+" "+(total/(i+0.0))+" "+repet+" "+maxrepet+" "+(maxrepet+0.0)/(i+0.0)); 
	     epsilon*=0.9995; 
 	     for(int j=0;j<2*nbseg;j++) 
    		guru[j].setEpsilon(epsilon); 
		 
	
	}

    }
}