//UT-EID=


import java.util.*;
import java.util.concurrent.*;


public class PSearch{
  public static int parallelSearch(int k, int[] A, int numThreads){

	  ExecutorService es = Executors.newFixedThreadPool(numThreads);
	  //stores the futures
	  ArrayList<Future<Integer>> f = new ArrayList<Future<Integer>>();
	  //stores the beginning index of each subarray
	  ArrayList<Integer> b = new ArrayList<Integer>();
	  
	  int beginning = 0;
	  int end = numThreads;
	  for(int i = 0; i < numThreads; i++){
		  //separates into array into subarrays and creates future for each one
		  b.add(beginning);
		  //out of bounds exception
		  Future<Integer> bigRings = es.submit(new SearchThread(k, Arrays.copyOfRange(A, beginning, end)));
		  f.add(bigRings);
		  beginning = end + 1;
		  //not accounting for the case when array size is not perfectly divisible by numThreads
		  end = end + numThreads + 1;
	  }
	  int count = 0;
	  for(Future<Integer> lowLife : f){
		  try {
			if(lowLife.get() != -1){
				  //get the index here
				  System.out.println("The value is at : " + b.indexOf(count) + lowLife.get());
				  return b.indexOf(count) + lowLife.get();
			  }
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  count++;
	  }

    return -1; // if not found
    
  }
}
