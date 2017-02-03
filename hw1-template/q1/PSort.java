package q1;
//UT-EID=rj8656 , lx939

import java.util.*;
import java.util.concurrent.*;

public class PSort implements Runnable{
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	int A[], begin, end;
	public PSort(int A[], int begin, int end){
		this.A = A;
		this.begin = begin;
		this.end = end;
	}
	  
	public static void parallelSort(int[] A, int begin, int end){
		PSort sort = new PSort(A, begin, end);
	    Future<?> future = threadPool.submit(sort);		
	    try{future.get();}
	    catch (Exception e) {System.err.println(e);}
	}
		
	public void run() {
	    if(end - begin < 4){		//base case
	    	int last = end - 1;
	    	int val = 0;
	    	for(int i = begin; i <= last; i++){
	    		if ((i + 1) == A.length) 
	    			return;
	    		val = A[i + 1];
	    		int j = i;
	    		while(j>= begin-1 && A[j] > val){
	    			A[j+1] = A[j];
	    			j = j - 1;
	    		}
	    		A[j+1]= val;		
	    	}
	    	return;
	    }
		try{					//array needs to be split and recursed on 
			int index = splitArray(A, begin, end);
		    Future<?> half1 = null;
		    Future<?> half2 = null;
		    if(index < end){
		    	PSort right = new PSort(A, index, end);
		    	half1 = threadPool.submit(right);
		    }
		    if(begin < index -1){
		    	PSort left = new PSort(A, begin, index);
		    	half2 = threadPool.submit(left);
		    }
	    	try{
	    		half2.get();
	    		half1.get();
	    	}
	    	catch (Exception e) { System.err.println (e);}
	        return;
	    } catch (Exception e) {
	    	return;
	    }
	  }
 
  public int splitArray(int[] A, int begin, int end){
	  int mid = (begin+end)/2;
	  int piv_point = A[mid]; 
	  int lower = begin;
	  int higher = end - 1;
	  while(lower <= higher){
		  while(A[lower] < piv_point){
			  lower++;
		  }
		  while(A[higher] > piv_point){		//moves lower and higher to piv_point
			  higher--;
		  }
		  if(lower <= higher){		
			  int temp = A[lower];
			  A[lower] = A[higher];
			  A[higher] = temp;
			  lower++;
			  higher--;
		  }
	  }
	  return lower;
  }

}
