package environment; 


import environment.IAction; 
import environment.ActionList; 


import rlglue.types.Action;

/** An action is a list of integer values (exit if any dimension is double) */

public class ActionRL implements environment.IAction{

    // Static fields : needed because we do  know in advance neither the number of dimensions, nor the
    // range of each dimension.
    // Practically, in all rlglue examples, actions are unidimensionnal integers

    private static int size; 
    private static  boolean types[];

    private static ActionList allActions;
    private static int nbActions=0; 
    private static boolean firstTime=true; 

    public static int getNumberOfActions(){return nbActions;}

    private static double lowerBoundsDouble[],upperBoundsDouble[];
    private static int lowerBoundsInt[],upperBoundsInt[]; 

    private static int numInt,numDouble; 

    private static void helpForBuildingActions(int index,int[] previousValues){
	if(index==size){
	    ActionRL u=new ActionRL(previousValues); 
	    ActionRL.allActions.add(u); 
	    System.out.println("Adding action "+nbActions+" "+u); 
	    nbActions++; 
	}
	else{
	    int[] newValues=new int[previousValues.length+1]; 
	    System.arraycopy(previousValues,0,newValues,0,previousValues.length); 
	    int lowerBound=ActionRL.lowerBoundsInt[index]; 
	    int upperBound=ActionRL.upperBoundsInt[index]; 
	    for(int i=lowerBound;i<=upperBound;i++){
		newValues[index]=i; 
		helpForBuildingActions(index+1,newValues); 
	    }
	}

    }//help

    public static ActionList getActionList(){return ActionRL.allActions;}


    public static void init(int actDim,
			    boolean[] actTypes,
			    double[] minDouble,
			    double[] maxDouble,
			    int[] minInt,
			    int[] maxInt,
			    int nI,
			    int nD
			    )		    
   {
	if(firstTime) firstTime=false; else return;  
	ActionRL.size=actDim;

	ActionRL.types=new boolean[ActionRL.size];
	System.arraycopy(actTypes,0,ActionRL.types,0,ActionRL.size);

	ActionRL.numInt=nI; 
	//ActionRL.numDouble=nD;

	ActionRL.lowerBoundsDouble=new double[ActionRL.size]; 
	ActionRL.upperBoundsDouble=new double[ActionRL.size]; 
	ActionRL.lowerBoundsInt=new int[ActionRL.size]; 
	ActionRL.upperBoundsInt=new int[ActionRL.size]; 

	for(int i=0;i<ActionRL.size;i++){
	    if(ActionRL.types[i]){ // Integer
		ActionRL.lowerBoundsInt[i]=minInt[i]; 
		ActionRL.upperBoundsInt[i]=maxInt[i];
	    }
	    else{ // double
		ActionRL.lowerBoundsDouble[i]=minDouble[i]; 
		ActionRL.upperBoundsDouble[i]=maxDouble[i];
	    }
	}// for



	ActionRL.allActions=new ActionList(null); 
	for(int i=0;i<size;i++)
	    if(!ActionRL.types[i]){
		// TODO : handling continuous actions (discretization ??)
		System.out.println("Sorry, actions even partially defined by double are not handled"); 
		System.exit(-1); 
	    }
	int lowerBound=ActionRL.lowerBoundsInt[0]; 
	int upperBound=ActionRL.upperBoundsInt[0]; 
	int[] previousValues=new int[1]; 
	for(int i=lowerBound;i<=upperBound;i++){
	    previousValues[0]=i; 
	    helpForBuildingActions(1,previousValues); 
	    }

    }//init

    // Non static part of the class
    
    private int[] val; 
    private int number; 

    private ActionRL(int[] values){
	this.val=new int[ActionRL.size]; 
	System.arraycopy(values,0,this.val,0,ActionRL.size); 
	this.number=nbActions; 
    }

    public String toString(){
	String s=""; 
	for(int i=0;i<ActionRL.size-1;i++)
	    s+=this.val[i]+","; 
	s+=this.val[ActionRL.size-1]; 
	return s; 
    }

    public IAction copy(){
	int[] nv=new int[ActionRL.size]; 
	System.arraycopy(this.val,0,nv,0,ActionRL.size); 
	return new ActionRL(nv); 
    }

    public int hashCode(){
	int sum=0; 
	for(int i=0;i<ActionRL.size;i++)
	    sum+=this.val[i]%23; 
	return sum; 
    }//hashCode

    public boolean equals(Object o){
	if(!(o instanceof ActionRL)) return false; 
	ActionRL a =(ActionRL)o; 
	for(int i=0;i<ActionRL.size;i++)
	    if(this.val[i]!=a.val[i]) return false; 
	return true; 
    }//equals


    // Double : not implemented yet
    // Int : one among k 
    public int nnCodingSize(){
	int size=0; 
	for(int i=0;i<ActionRL.size;i++)
	    size+=ActionRL.upperBoundsInt[i]-ActionRL.lowerBoundsInt[i]+1; 
	return size; 

    }// nnCodingSize
    // TODO
    public double[] nnCoding(){
	double[] code=new double[this.nnCodingSize()]; 
	int decal=0; 
	for(int i=0;i<ActionRL.size;i++){
	    code[decal+this.val[i]-ActionRL.lowerBoundsInt[i]]=1;
	    decal=decal+ActionRL.upperBoundsInt[i]-ActionRL.lowerBoundsInt[i]+1;  
	}//for
	return code; 
    }//nnCoding

    public rlglue.types.Action actionRLToAction(){
	// TODO ICI !!!
	rlglue.types.Action cp=new rlglue.types.Action(ActionRL.numInt,ActionRL.numDouble); 
	for(int i=0;i<ActionRL.numInt;i++) cp.intArray[i]=this.val[i]; 
	return cp; 
    }// actionRLTo Action

    public int getNumber(){return this.number;}

}// ActionRL


