/* Random Agent that works in all domains
* Copyright (C) 2007, Brian Tanner brian@tannerpages.com (http://brian.tannerpages.com/)
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA. */
package RandomAgent;

import java.util.Random;

import rlVizLib.utilities.TaskSpecObject;

import rlglue.agent.Agent;
import rlglue.types.Action;
import rlglue.types.Observation;

public class RandomAgent implements Agent {
	private Action action;
	private int numInts =1;
	private int numDoubles =0;
	private Random random = new Random();

        TaskSpecObject TSO=null;
	

        
        public RandomAgent(){
        }

	public void agent_cleanup() {
	}

	public void agent_end(double arg0) {

	}

	public void agent_freeze() {

	}

	public void agent_init(String taskSpec) {
            TSO = new TaskSpecObject(taskSpec);

            action = new Action(TSO.num_discrete_action_dims,TSO.num_continuous_action_dims);	
	}

	public String agent_message(String arg0) {
            return null;
	}

	public Action agent_start(Observation o) {
            randomify(action);
            return action;
	}

	public Action agent_step(double arg0, Observation o) {
            randomify(action);
            return action;
	}

	private void randomify(Action action){
		int i_index = 0;
		int d_index = 0;
		for (int i=0; i<TSO.action_dim;i++) {
			if (TSO.action_types[i] == 'i') {
				action.intArray[i_index++]=random.nextInt(((int)TSO.action_maxs[i]+1)-(int)TSO.action_mins[i]) + ((int)TSO.action_mins[i]);
			}
			else {
				action.doubleArray[d_index++]=random.nextDouble()*(TSO.action_maxs[i] - TSO.action_mins[i]) + TSO.action_mins[i];
			}
		}
	}
	


}
