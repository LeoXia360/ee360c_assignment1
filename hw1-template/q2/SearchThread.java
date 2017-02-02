//UT-EID=LX939
//RJ8656


import java.util.*;
import java.util.concurrent.*;


public class SearchThread implements Callable<Integer>{
	private int x;
	private int[] sub;
	
	public SearchThread(int x, int[] sub){
		 this.x = x;
		 this.sub = sub;
	}
	
	public Integer call(){
		for(int i = 0; i < sub.length; i++){
			if(sub[i] == x){
				return i;
			}
		}
		//didn't find in this array
		return -1;
	}
  
}
