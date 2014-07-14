package util;

public class MySort {
	//[start,end)
	public static void quickSortDesc(int[] A, int start, int end){
		//only one number
		if( start+1>=end )
			return;
		
		//±ê±ø
		int pv = A[start];
		//partition
		int i= start+1;
		int j= end-1;
		
		while(i<j){
			while(i<=j && A[i]>=pv) // boundary 
				i++;
			while(j>=i && A[j]<pv) // boundary
				j--;
			//swap
			if(i<j){
				int tmp = A[i];
				A[i] = A[j];
				A[j] = tmp;
			}else
				break;
		}
		
		//put pv into the right position
		if(pv < A[j]) //the judgment is very necessary 
		{
			A[start] = A[j];
			A[j] = pv;
		}
		
		//sub-problem
		quickSortDesc(A,start,j); //pv is already in its sorted position
		quickSortDesc(A,i,end);
	}
	/**
	 * test
	 * @param args
	 * @author huangting
	 * @date 2014-6-25
	 */
	public static void main(String[] args){
		int[] A = {1};
		quickSortDesc(A,0,A.length);
		MyPrint.printArray(A);
	}
}
