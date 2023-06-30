package environment;

import rlglue.types.Observation;

public class StateRL implements IState{

    // Static fields : needed because we do  know in advance neither the number of dimensions, nor the
    // range of each dimension.
    
    protected static TileAbleEnvironmentRL theWorld; 

    public static void setWorld(TileAbleEnvironmentRL a){theWorld=a;}; 
    public static TileAbleEnvironmentRL getWorld(){return theWorld; }

    

    
    protected static int size;
    protected static int numInt,numDouble;
    protected static boolean stateTypes[];
    protected static double minDouble[],maxDouble[]; 
    protected static int minInt[],maxInt[];
    protected static double equalCoef=0.05; 

    protected static ActionList staticActionList; 

    public static StateRL FINAL; 
    
    protected static boolean firstTime=true; 

    public static String printInfo(){
	String s="State coding information :\n"; 
	s+="Equality condition : "+equalCoef+"\n"; 
	s+="Dimension : "+size+"(Integers : "+numInt+" Doubles : "+numDouble+")\n"; 
	return s; 
    }


    public static void init(int size,
			    int nI,
			    int nD,
			    boolean[] types,
			    double[] minDb,
			    double[] maxDb,
			    int[] minInt,
			    int[] maxInt,
			    ActionList allActions
			    )
    {
	if(firstTime) firstTime=false; else return; 
	StateRL.size=size; 
	StateRL.numInt=nI; 
	StateRL.numDouble=nD; 

	StateRL.staticActionList=allActions; 

	StateRL.stateTypes=new boolean[StateRL.size]; 
	System.arraycopy(types,0,StateRL.stateTypes,0,StateRL.size); 

	StateRL.minDouble=new double[StateRL.size]; 
	System.arraycopy(minDb,0,StateRL.minDouble,0,StateRL.size); 

	StateRL.maxDouble=new double[StateRL.size]; 
	System.arraycopy(maxDb,0,StateRL.maxDouble,0,StateRL.size); 


	StateRL.minInt=new int[StateRL.size]; 
	System.arraycopy(minInt,0,StateRL.minInt,0,StateRL.size); 

	StateRL.maxInt=new int[StateRL.size]; 
	System.arraycopy(maxInt,0,StateRL.maxInt,0,StateRL.size); 
	FINAL=new StateRL(); 
 	FINAL.minDouble[0]=1214.2707; 
    }//init


    public static void setEqualCoef(double u){ equalCoef=u;}
    public static double gatEqualCoef(){return equalCoef;}


    // Non static fields and methods
    
    protected double stateValuesDouble[]; 
    protected int stateValuesInt[]; 
    /** Could be static ? */
    protected TileAbleEnvironmentRL universe=null; 

    public StateRL(Observation ob){
	this(); 
	int indexDouble=0; 
	int indexInt=0; 
	for(int i=0;i<StateRL.size;i++){
	    if(StateRL.stateTypes[i])// type=integer
		this.stateValuesInt[indexInt]=ob.intArray[indexInt++];
	    else // type=double
		{
		    this.stateValuesDouble[indexDouble]=ob.doubleArray[indexDouble++]; 
	}
	}// for
    }

    protected StateRL(){
	this.stateValuesDouble=new double[StateRL.numDouble]; 
	this.stateValuesInt=new int[StateRL.numInt]; 
    }


    /** No use */
    public ActionList getActionList(){
	StateRL.staticActionList.setState(this); 
	return StateRL.staticActionList; 
}

    /** No use -- except for tiling */
    public void setEnvironment(IEnvironment c){ }

    /** No use */
    public IState modify(IAction a){return null;}

    /** No use except for tiling */
    public IEnvironment getEnvironment(){return this.theWorld;}

    /** No use */
    public double getReward(IState old, IAction a){return 0.0; }

    /** Is this state final ?  TODO */
    public boolean isFinal(){return (this.minDouble[0]==1214.2707); }

	
    public IState copy(){
	StateRL cp=new StateRL();
	int indexInt=0,indexDouble=0;
	for(int i=0;i<StateRL.size;i++){
	    if(StateRL.stateTypes[i])// type=integer
		cp.stateValuesInt[indexInt]=this.stateValuesInt[indexInt++];
	    else // type=double
		cp.stateValuesDouble[indexDouble]=this.stateValuesDouble[indexDouble++]; 
	}// for
	return cp; 
    }// copy

	/** Size of State's coding (for NN).*/
    public int nnCodingSize(){
	int size=0; 
	for(int i=0;i<StateRL.size;i++){
	    if(StateRL.stateTypes[i])
		size=size+StateRL.maxInt[i]-StateRL.minInt[i]+1; 
	    else
		size++; 
	}//for
	return size; 
    }// nnCodingSize

	/** State's coding (for NN). */
    public double[] nnCoding(){
	double code[]  = new double[this.nnCodingSize()]; 
	int decal=0; 
	int indexInt=0; 
	int indexDouble=0; 
	for(int i=0;i<StateRL.size;i++){
	    if(StateRL.stateTypes[i]){
		code[decal+this.stateValuesInt[indexInt]-StateRL.minInt[i]]=1;
		decal+=StateRL.maxInt[i]-StateRL.minInt[i]+1; 
		indexInt++; 
	    }
	    else{
		code[decal]=(this.stateValuesDouble[indexDouble]-StateRL.minDouble[i]);
		code[decal]/=StateRL.maxDouble[i]-StateRL.minDouble[i]; 
		decal++; 
		indexDouble++; 	     
	    }

	}// for
	return code; 
    }//nnCoding

	/** Q-Learning memorizing techniques use hashcoding : it is necessary to redefine it for each problem/game */
    public int hashCode(){
	int sum=0; 
	int indexInt=0,indexDouble=0; 
	for(int i=0;i<StateRL.size;i++){
	    if(StateRL.stateTypes[i]) sum=sum+this.stateValuesInt[indexInt++]; 
	    else sum=sum+(int)(10*this.stateValuesDouble[indexDouble++]); 
	    sum=sum%23; 
	}
	return sum; 
    }// hashCode

	/** Q-Learning memorizing techniques use equality: it is necessary to redefine it for each problem/game */
    public boolean equals(Object o){
	if(!(o instanceof StateRL)) return false; 
	StateRL so=(StateRL)o; 
	int indexInt=0,indexDouble=0; 
	for(int i=0;i<StateRL.size;i++){
	    if(StateRL.stateTypes[i]){
		if(this.stateValuesInt[indexInt]!=so.stateValuesInt[indexInt++]) return false; 
	    }
	    else{
		if(Math.abs(this.stateValuesDouble[indexDouble]-so.stateValuesDouble[indexDouble++])>
		   Math.abs(StateRL.maxDouble[i]-StateRL.minDouble[i])*equalCoef) return false; 
	    }
	   
	}
	return true; 
    }//equals

}