package algorithmes; 

import environnement.State; 
import environnement.ListeActions; 
import environnement.Action; 



import dataset.Dataset; 

import qlearning.StockageINT; 

import java.util.Random; 

/** Essai de généralisation de l'implémentation de l'algorithme <it> Compact Q-Learning</it> : 
    toutes les quantités manipulées sont des octets non signés. 

    <a href="http://asl.epfl.ch/aslInternalWeb/ASL/publications/uploadedFiles/compactQlearning_for%20print%20version.pdf "> Voir le papier</a>
*/

public class SelectionneurQLINT implements Selectionneur{

     /** Un générateur aléatoire */
    protected static Random generateur=new Random(); 

    protected int f1=1,f2=2,f3=4; 
    protected int p1=1; 
    protected int m1=3; 
    
    /** La structure qui mémorise les Q(s,a) */
    protected StockageINT  memoire=new StockageINT(); 

    /** The threshold under which Q(s,a) is actually updated */
    protected int theta=200; 

    public void setThreshold(int v){
	this.theta=v; 
    }

    public int getThreshold(){
	return this.theta; 
    }
    public void randomize(){
	f1=1+generateur.nextInt(10); 
	f2=1+generateur.nextInt(10); 
	f3=1+generateur.nextInt(10);
	p1=1+generateur.nextInt(10); 
	m1=2+generateur.nextInt(10); 
	theta=50+generateur.nextInt(190); 
    }

    /** Only the roulette wheel selection is available */
    public Action getChoix(ListeActions l){
	if(l.size()==0) return null; 
	State s=l.getState();
	int sum=0; 
	for(int i=0;i<l.size();i++) {
	    sum+=memoire.get(s,l.getAction(i))+1; 
	}
	int choix=generateur.nextInt(sum); 
	int indice=0;
	int partialSum=memoire.get(s,l.getAction(indice))+1; 
	while(choix>partialSum){
	    indice++; 
	    partialSum+=1+memoire.get(s,l.getAction(indice)); 
	}
	return l.getAction(indice);



    }


    public void apprend(State s1,State s2, double reward,Action a){
	int qsa=memoire.get(s1,a); 
	ListeActions la=s2.getListeActions();
	if(la.size()!=0){
	    int maxqsap=memoire.get(s2,la.getAction(0)); 
	    for(int i=1;i<la.size();i++){
		Action aprime=la.getAction(i); 
		int qsap=memoire.get(s2,aprime);
		if(qsap>maxqsap) maxqsap=qsap; 
	    }
	    int f=0; 
	    if(maxqsap>20) f=f1; 
	    if(maxqsap>40) f=f2; 
	    if(maxqsap>80) f=f3;
	    
	    int oldqsa=qsa; 
	    qsa+=m1*(int)reward+f;
	    if(qsa>theta) qsa=qsa-p1; 
	}
	else qsa=m1*(int)reward-qsa; 
	if(qsa<0) qsa=0; 
	//	if(qsa>65000) qsa=65000; 
	 
	memoire.put(s1,a,s2,qsa); 

    }//apprend


    /** Auxiliary/debug method : find the best action from a state.*/
    public Action bestAction(State s){
	ListeActions l=s.getListeActions(); 
	Action meilleure=l.getAction(0); 
	double maxqsap=memoire.get(s,meilleure); 
	for(int i=1;i<l.size();i++){
	    Action a=l.getAction(i); 
	    double qsap=memoire.get(s,a);
	    if(qsap>maxqsap) {
		maxqsap=qsap; 
		meilleure=a;
	    }
	}
	return meilleure; 
    }
    
    /** No extensions towards NN at this time ... */
    public Dataset extractDataset(){return null;}

    public void nouvelEpisode(){}; 

      public String toString(){
	  String s=""; 
	  s+="f1 : "+f1+" f2 : "+f2+" f3 : "+f3+" p1 : "+p1+" Seuil : "+theta; 
	  s+="\n"+memoire.toString(); 
	return s;
    }

    public void showHistogram(){
	memoire.makeHistogram(); 
	memoire.displayHistogram(); 
    }

 




}