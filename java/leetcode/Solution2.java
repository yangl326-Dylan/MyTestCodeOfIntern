package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
class Interval {
     int start;
     int end;
     Interval() { start = 0; end = 0; }
     Interval(int s, int e) { start = s; end = e; }
 }
class IntervalComparator implements Comparator<Interval>{

	public int compare(Interval arg0, Interval arg1) {
	    // TODO Auto-generated method stub
	    return arg0.start<arg1.start? -1: arg0.start == arg1.start? 0: 1;
    }
	
}

public class Solution2 {
	/**
	 * Write a program to solve a Sudoku puzzle by filling the empty cells.
	 * Solution：backtracking
	 * @param board
	 * @author huangting
	 * @date 2014-6-16
	 */
	public void solveSudoku(char[][] board) {
        List<HashSet<Integer>> rows = new ArrayList<HashSet<Integer>>();//store values appeared in the rows
        List<HashSet<Integer>> cols = new ArrayList<HashSet<Integer>>(); //store values appeared in the col
        List<HashSet<Integer>> subs = new ArrayList<HashSet<Integer>>(); //store values appeared in that particular 3x3 group
        //first initialize
        for(int i=0;i<9;i++)
        {
        	rows.add(new HashSet<Integer>());
        	cols.add(new HashSet<Integer>());
        	subs.add(new HashSet<Integer>());
        }
        //statistic all the nonempty cells
        for(int i=0;i<9;i++){
        	for(int j=0;j<9;j++){
        		int v = board[i][j]-'0';
        		rows.get(i).add(v);
				cols.get(j).add(v);
				subs.get(3*(i/3)+j/3).add(v);
        	}
        }
     
        for(int i=0;i<9;i++){
        	for(int j=0;j<9;j++){
        		System.out.print(board[i][j] );
        		System.out.print(" ");
        	}
        	System.out.println();
        } 
        System.out.println();
        solve(board,0,0,rows,cols,subs);
        for(int i=0;i<9;i++){
        	for(int j=0;j<9;j++){
        		System.out.print(board[i][j] + " ");
        	}
        	System.out.println();
        } 
        
    }
	private boolean solve(char[][] board, int i, int j,List<HashSet<Integer>> rows,List<HashSet<Integer>> cols,List<HashSet<Integer>> subs){
		if(i > 8 || j >8)
			return true;
		if(board[i][j] == '.'){
			for(int v=1;v<=9;v++){
				if(!rows.get(i).contains(v) && !cols.get(j).contains(v) && !subs.get(3*(i/3)+j/3).contains(v)){
					board[i][j] = (char) (v+'0');
					rows.get(i).add(v);
					cols.get(j).add(v);
					subs.get(3*(i/3)+j/3).add(v);
					int jI = j == 8? 0:j+1;
					int iI = j == 8 ? i+1:i;
					boolean flag = solve(board,iI,jI, rows,cols,subs);
					if(flag)
						return true;
					else{
						rows.get(i).remove(v);
						cols.get(j).remove(v);
						subs.get(3*(i/3)+j/3).remove(v);
						board[i][j] = '.';
					}
				}
			}
		}else
		{
			int jI = j == 8? 0:j+1;
			int iI = j == 8 ? i+1:i;
			return solve(board,iI,jI, rows,cols,subs);
		}
		
		return false;
	}
	/**
	 * The count-and-say sequence is the sequence of integers beginning as follows:
	 * @param n
	 * @return
	 * @author huangting
	 * @date 2014-6-16
	 */
	public String countAndSay(int n) {
        String[] rets = new String[n];
        rets[0] = "1";
        int lv = -1;
        int lcnt = 0;
        for(int i=1;i<n;i++){
        	char[] tmp = rets[i-1].toCharArray();
        	lv = -1;
        	lcnt = 0;
        	StringBuilder ret = new StringBuilder();
        	//counting
        	for(int j=0;j<tmp.length;j++){
        		if(tmp[j]-'0' == lv){
        			lcnt ++;
        		}else{
        			//saying
        			if(lv != -1){
        				ret.append((char)(lcnt+'0')); //how many
        				ret.append((char)(lv+'0')); //what
        			}
        			lv = tmp[j]-'0';
        			lcnt = 1;
        		}
        		
        	}
        	//the last one need to append
        	ret.append((char)(lcnt+'0')); //how many
			ret.append((char)(lv+'0')); //what
        	rets[i] = ret.toString();
        	System.out.println(ret.toString());
        }
        return rets[n-1];
    }
	/**
	 * solution : by recursion algorithm
	 * @param candidates
	 * @param target
	 * @return
	 * @author huangting
	 * @date 2014-6-17
	 */
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        Arrays.sort(candidates);
        return subCom(candidates,len,0,target);
    }
	private List<List<Integer>> subCom(int[] cand,int len, int start, int target){
		List<List<Integer>> rets = new ArrayList<List<Integer>>();
		if(target<=0)
			return rets;
		//the base case, can't split anymore
		if(start == len-1){
			if(target%cand[len-1] == 0){
				List<Integer> ret = new ArrayList<Integer>();
				for(int i=0;i<target/cand[len-1];i++)
					ret.add(cand[len-1]);
				rets.add(ret);
				return rets;
			}else{
				return rets;
			}
		}
		//split into a sub-problem
		for(int i=0;i<=target/cand[start];i++){
			//the current number
			List<Integer> ret = new ArrayList<Integer>();
			for(int j=0;j<i;j++)
				ret.add(cand[start]);
			
			List<List<Integer>> sRet = subCom(cand,len,start+1,target-i*cand[start]);
			//combination
			for(int j=0;j<sRet.size();j++){
				List<Integer> tmp= new ArrayList<Integer>();
				tmp.addAll(ret);
				tmp.addAll(sRet.get(j));
				rets.add(tmp);
			}
			//in this case, there is no components come from the left sub-problem
			if(target - i*cand[start] == 0){
				rets.add(ret);
			}
		}
		return rets;
	}
	/**
	 * solution : by recursion algorithm
	 * reduce the problem to the common combination sum problem
	 * 
	 * @param candidates
	 * @param target
	 * @return
	 * @author huangting
	 * @date 2014-6-17
	 */
	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        int len = candidates.length;
        Arrays.sort(candidates);
        //the reduce technique is here, shown by the hashmap kv and the new unique array cand
        int[] cand = new int[len]; 
        //caculating the duplicats
        HashMap<Integer, Integer> kv = new HashMap<Integer,Integer>();
        int j=0;
        for(int i=0;i<candidates.length;i++){
        	if(kv.containsKey(candidates[i]))
        		kv.put(candidates[i], kv.get(candidates[i])+1);
        	else{
        		kv.put(candidates[i], 1);
        		cand[j++] = candidates[i]; 
        	}
        }
        return subCom2(cand,j,0,target, kv);
    }
	private List<List<Integer>> subCom2(int[] cand,int len, int start, int target, HashMap<Integer,Integer> kv){
		List<List<Integer>> rets = new ArrayList<List<Integer>>();
		if(target<=0)
			return rets;
		//the base case, can't split anymore
		if(start == len-1){
			if(target%cand[len-1] == 0 && target/cand[len-1] <= kv.get(cand[len-1])){
				List<Integer> ret = new ArrayList<Integer>();
				for(int j=0;j<target/cand[len-1];j++)
					ret.add(cand[len-1]);
				rets.add(ret);
				return rets;
			}else{
				return rets;
			}
		}
		// split into a sub-problem
		// split into a sub-problem
		for (int i = 0; i <= kv.get(cand[start]); i++) {
			// the current number
			List<Integer> ret = new ArrayList<Integer>();
			for (int j = 0; j < i; j++)
				ret.add(cand[start]);

			List<List<Integer>> sRet = subCom2(cand, len, start + 1, target - i * cand[start],kv);
			// combination
			for (int j = 0; j < sRet.size(); j++) {
				List<Integer> tmp = new ArrayList<Integer>();
				tmp.addAll(ret);
				tmp.addAll(sRet.get(j));
				rets.add(tmp);
			}
			// in this case, there is no components come from the left
			// sub-problem
			if (target - i * cand[start] == 0) {
				rets.add(ret);
			}
		}
		return rets;
	}
	/**
	 * Given an unsorted integer array, find the first missing positive integer.
	 * using the space of the array itself
	 * @param A
	 * @return
	 * @author huangting
	 * @date 2014-6-18
	 */
	public int firstMissingPositive(int[] A) {
		int n = A.length;
		//by swapping get an array with value of n in index of location n-1 
		for(int i=0;i<n;i++){
			if(A[i] != i+1){ //A[i] is not in the right location
				if(A[i]<=0 || A[i]>n) // ignore it
					continue;
				else{ //swap the A[i] to the proper location indexed with A[i]-1
					int tmp = A[A[i]-1];
					if(tmp == A[i])
						continue;
					A[A[i]-1] = A[i];
					A[i] = tmp;
					i--;
				}
			}
		}
		int ret = 0;
		//find the first one not in [1-n];
		for(int i=0;i<n;i++){
			if(A[i] != i+1)
			{
				ret = i+1;
				break;
			}
		}
		return ret;
    }
	public int trap(int[] A) {
		//the base case, can't trap rain
        if(A == null || A.length<=2)
        	return 0;
        int len = A.length;
        int[] lCnt = new int[len]; // store the index of the highest bar in the left side for each, if it exists (>= the current bar)
        int[] rCnt = new int[len]; // store the index of the highest bar in the right side for each, if it exists (>= the current bar)
        //caculate lCnt
        lCnt[0] = 0; //if it not exists marked with -1
        for(int i=1;i<len;i++){
        	int index = i-1;
        	lCnt[i] = i;//initialize
        	while(index >= 0 ){
        		if(A[index] > A[i] && A[index] > A[lCnt[i]]){//>=; highest; left
        			lCnt[i] = index;
        		}
        		index--;
        	}
        }
        //caculate rCnt
        rCnt[len-1] = -1;
        for(int i=len-2;i>=0;i--){
        	int index = i+1;
        	rCnt[i] = i;//initialize
        	while(index < len ){
        		if(A[index] > A[i] && A[index] > A[rCnt[i]]){ // >=; highest; right
        			rCnt[i] = index;
        		}
    			index ++ ;
        	}
        }
        int ret = 0;
        //caculate the area, for each low-lying area
        for(int i=1;i<len-1;i++){
        	//only concern the low-lying area, and compute all the bars in this area
        	if(lCnt[i]!= i && rCnt[i]!=i){
        		int minmax = Math.min(A[lCnt[i]], A[rCnt[i]]);
        		int tmp = 0;
        		tmp = (minmax-A[i]);
        		//add to the result
        		ret += tmp;
        	}
        }
        return ret;
    }
	public int trap2(int[] A) {
		//the base case, can't trap rain
        if(A == null || A.length<=2)
        	return 0;
        int len = A.length;
        int[] lCnt = new int[len]; // store the index of the highest bar in the left side for each, if it exists (>= the current bar)
        int[] rCnt = new int[len]; // store the index of the highest bar in the right side for each, if it exists (>= the current bar)
        //caculate lCnt
        lCnt[0] = 0; //if it not exists marked with -1
        for(int i=1;i<len;i++){
        	int index = i-1;
        	lCnt[i] = i;//initialize
        	while(index >= 0 ){
        		if(A[index] >= A[i]){
        			if(lCnt[index] != index) //exist
        			{
        				if(A[lCnt[index]] > A[lCnt[i]])//bigger
        				{
        					lCnt[i] = lCnt[index];
        					break;
        				}else{
        					index--;
        				}
        			}else{
        				if(A[index]>A[lCnt[i]])
        					lCnt[i] = index;
        				index--;
        			}
        		}else{
        			if(lCnt[index] != index){ //exist
        				if(A[lCnt[index]] > A[i] && A[lCnt[index]]>A[lCnt[i]])
        				{
        					lCnt[i] = lCnt[index];
        				}
        			}
        			break;
        		}
        	}
        }
        //caculate rCnt
        rCnt[len-1] = len-1;
        for(int i=len-2;i>=0;i--){
        	int index = i+1;
        	rCnt[i] = i;//initialize
        	while(index < len ){
        	if(A[index] >= A[i]){
    			if(rCnt[index] != index) //exist
    			{
    				if(A[rCnt[index]] > A[rCnt[i]])//bigger
    				{
    					rCnt[i] = rCnt[index];
    					break;
    				}else{
    					index++;
    				}
    			}else{
    				if(A[index]>A[rCnt[i]])
    					rCnt[i] = index;
    				index++;
    			}
    		}else{
    			if(rCnt[index] != index){ //exist
    				if(A[rCnt[index]] > A[i] && A[rCnt[index]]>A[rCnt[i]])
    				{
    					rCnt[i] = rCnt[index];
    				}
    			}
    			break;
    		}
        	}
        }
        int ret = 0;
        //caculate the area, for each low-lying area
        for(int i=1;i<len-1;i++){
        	//only concern the low-lying area, and compute all the bars in this area
        	if(lCnt[i]!= i && rCnt[i]!=i){
        		int minmax = Math.min(A[lCnt[i]], A[rCnt[i]]);
        		int tmp = 0;
        		tmp = (minmax-A[i]);
        		//add to the result
        		ret += tmp;
        	}
        }
        return ret;
    }
	public boolean isMatch(String s, String p) {
        if(s == null && p == null || 
        		s!=null && s.equals(p))
        	return true;
        if(s == null && p!=null ||
        		s!=null && s==null)
        	return false;
      
        int len1 = s.length();
        int len2 = p.length();
        int i=0,j=0;
        for(i=0,j=0;i<len1;i++){
        	if(j>=len2) //p has reached its end before s
        		return false;
        	if(s.charAt(i) == p.charAt(j)){ //corresponding location are the same
        		j++;
        		continue;
        	}
        	if(p.charAt(j) == '?'){//match any single char
        		j++;
        		continue;
        	}
        	if(p.charAt(j) == '*'){
        		if(len2-j-1>len1-i-1){
        			if(i<len1-1 && (s.charAt(i+1) == p.charAt(j+1) || p.charAt(i+1) == '*' || p.charAt(i+1)=='?'))
        			{
        				j++;continue;
        			}
        			i--;
        		}else if(len2-j-1<len1-i-1){
        			i = i+ ((len1-i-1)-(len2-j-1));	
        		}
        		j++;
        		continue;
        	}
        	return false;
        }
        if(j == len2-1 && p.charAt(j) == '*')
        	return true;
        	
        if(j<len2)
        	return false;
        return true;
    }
	public boolean isMatch2(String s, String p) {
        if(s == null && p == null || 
        		s!=null && s.equals(p))
        	return true;
        if(s == null && p!=null ||
        		s!=null && s==null)
        	return false;
        
        int len1 = s.length();
        int len2 = p.length();
        boolean flag = p.contains("*");
        if(!flag && len1!=len2) //there is no "*", and have different length
        	return false;
        if(!flag && len1 == len2){ //we have to traverse s and p to identify if they are matched
        	for(int i=0;i<len1;i++){
        		if(s.charAt(i) == p.charAt(i) ||
        				p.charAt(i) == '?')
        			continue;
        		else
        			return false; // not matched
        	}
        	return true;
        }
        //there is "*"
        if(flag){
        	 
        	 int l1 = s.length();
			 int l2 = p.length();
			 char[] sc = s.toCharArray();
			 char[] pc = p.toCharArray();
			 
			 if(l1 == 0){
				 for(int i=0;i<l2;i++){
					 if(pc[i] !='*')
						 return false;
				 }
				 return true;
			 }
			 boolean[][] dp = new boolean[2][l1];
			
			 for(int i=0;i<l2;i++){
				 for(int j=0;j<l1;j++){
					 if(i == 0 ){ // the first row
						 if(j == 0){
							 if(pc[i] == sc[j] || pc[i] == '*' || pc[i] == '?')
								 dp[i%2][j] = true;
							 else {
								dp[i%2][j] = false;
							}
						 }else{
							 if(pc[i] == '*')
								 dp[i%2][j] = true;
							 else {
							//	 System.out.println(i%2 + " ");
								dp[i%2][j] = false;
							}
						 }
					}else { //the other rows
						if(j == 0) {
							if(dp[i%2==0? 1: 0][j] == true && pc[i] == '*')
								dp[i%2][j] = true;
							else {
								dp[i%2][j] = false;
							}
						}else{
							//case 1 : p[0...i-1] is matched s[0...j] && p[i] = '?'
							if(dp[i%2 == 0 ? 1:0][j] == true && pc[i] == '*')
								dp[i%2][j] = true;
							else if(dp[i%2 == 0 ? 1:0][j-1] == true && (
									pc[i] == sc[j] || pc[i] == '*' || pc[i] == '?')) //case 2 : p[0..i-1] is matched s[0...j-1] && p[i] is matched s[j]
							{
								dp[i%2][j] = true;
							}else {
								dp[i%2][j] = false;
							}
						}
					}
				 }
			 }
			 return dp[(l2-1)%2][l1-1];
        }
        return false;
	}
	/**
	 * using recursive method
	 * unsolved
	 * @param s
	 * @param p
	 * @return
	 * @author huangting
	 * @date 2014-6-30
	 */
	public boolean isMatch3(String s, String p) {
		 if(s == null && p == null || 
	        		s!=null && s.equals(p))
	        	return true;
	     if(s == null && p!=null ||
	        		s!=null && p==null)
	        	return false;
	     if(s.equals("") && (p.equals("") || p.equals("*")))
	    	 return true;
	     if(s.charAt(0) == p.charAt(0))
	    	 return isMatch(s.substring(1),p.substring(1));
	    
	     return false;
	}
	/**
	 * Jump Game II
	 * @param A
	 * @return
	 * @author huangting
	 * @date 2014-6-30
	 */
	public int jump(int[] A) {
        int [][] p = new int[A.length][A.length];
        
        for(int sz = 1; sz<A.length;sz++){
        	for(int i=0;i<A.length-sz;i++){
        		if(A[i] == 0){//if we can't not jump at this location
        			p[i][i+sz] = Integer.MAX_VALUE;
        			continue;
        		}
        		if(A[i]>=sz){ //if we can jump by 1 jump
        			p[i][i+sz] = 1;
        		}else{
        			int min = Integer.MAX_VALUE;
        			for(int j = A[i];j>=1;j--){
        				if(p[i][i+j] + p[i+j][i+sz] < min)
        					min = p[i][i+j] + p[i+j][i+sz];
        			}
        			p[i][i+sz] = min;
        		}
        		
        	}
        }
        return p[0][A.length-1];
    }
	public int jump2(int[] A) {
		int[] p = new int[A.length];
		for(int i=A.length-2;i>=0;i--){
			int len = A.length-i-1;
			if(A[i] >= len)
			{
				p[i] = 1;
			}else if(A[i] == 0){
				p[i] = Integer.MAX_VALUE;
			}else{
				int min = Integer.MAX_VALUE;
    			for(int j = A[i];j>=1;j--){
    				if(1 + p[i+j] < min)
    				{
    					min = 1 + p[i+j];
    				}
    			}
    			p[i] = min;
			}
		}
		return p[0];
	}
	/*
	 * We use "last" to keep track of the maximum distance that has been reached
	 * by using the minimum steps "ret", whereas "curr" is the maximum distance
	 * that can be reached by using "ret+1" steps. Thus,
	 * curr = max(curr,i+A[i]) where 0 <= i <= last.
	 */
	 int jump3(int A[]) {
	        int ret = 0; 
	        int last = 0;
	        int curr = 0;
	        int n = A.length;
	        for (int i = 0; i < n; ++i) {
	            if (i > last) {
	                last = curr;
	                ++ret;
	            }
	            curr = Math.max(curr, i+A[i]);
	        }
	        return ret;
	    }
	 /**
	  * Given a collection of numbers, return all possible permutations.
	  * @param num
	  * @return
	  * @author huangting
	  * @date 2014-7-1
	  */
	 private List<List<Integer>> permute(int[] num) {
		//because we have to delete from the array, so we first transfer
		 //to the linkedlist
		 LinkedList<Integer> n = new LinkedList<Integer>() ; 
		 for(int i=0;i<num.length;i++){
			 n.add(num[i]);
		 }
		 return permuteDetail(n);
	 }
	 /**
	  * by recursive algorithm, I do the permutation
	  * @param num
	  * @return
	  * @author huangting
	  * @date 2014-7-1
	  */
	 private List<List<Integer>> permuteDetail(LinkedList<Integer> num) {
		 List<List<Integer>> ret = new ArrayList<List<Integer>>();
		 if(num == null || num.size()<=0)
			 return ret;
		 if(num.size() == 1){
			 ArrayList<Integer> curr = new ArrayList<Integer>();
			 curr.add(num.get(0));
			 ret.add(curr);
			 return ret;
		 }
		 int last = Integer.MAX_VALUE;
		 for(int i=0;i<num.size();i++){
			 //since we have sort the array, if the next one is the same with the last
			 //one, it will get the same permutation, so we don't need to permute it
			 if(num.get(i) == last)
			 {
				 continue;
			 }
			 //track the last one num
			 last = num.get(i);
			 int cur = num.get(i);
			 LinkedList<Integer> newNum = new LinkedList<Integer>();
			 newNum.addAll(num);
			 
			 last = newNum.get(i);
			 newNum.remove(i);
			 List<List<Integer>> sub = permuteDetail(newNum);
			 //merge
			 for(int j=0;j<sub.size();j++){
				sub.get(j).add(cur);
				ret.add(sub.get(j));
			 }
		 }
		return ret;
	 }
	 public List<List<Integer>> permuteUnique(int[] num) {
		 Arrays.sort(num);//first sort,so we can avoid the duplication by not generating it
		 return permute(num);
	  }
	 /**
	  * Rotate the image by 90 degrees (clockwise).
	  * in place
	  * @param matrix
	  * @author huangting
	  * @date 2014-7-1
	  */
	 public void rotate(int[][] matrix) {
		 if(matrix == null)
			 return ;
	     int n = matrix.length;
	     if(n == 1)
	    	 return;
	     for(int i=0;i<(n-1)/2;i++){
	    	 for(int j=i;j<n-i-1;j++){
	    		 int t1 = matrix[i][j];
	    		 int t2 = matrix[j][n-i-1];
	    		 int t3 = matrix[n-i-1][n-j-1];
	    		 int t4 = matrix[n-j-1][i];
	    		 matrix[j][n-i-1] = t1;
	    		 matrix[n-i-1][n-j-1] = t2;
	    		 matrix[n-j-1][i] = t3;
	    		 matrix[i][j] = t4;
	    	 }
	     }
	  }
	  public List<String> anagrams(String[] strs) {
		  List<String> ret = new ArrayList<String>();
		  if(strs == null || strs.length <= 1)
			  return ret;
		  HashMap<String, List<String>> cur = new HashMap<String,List<String>>();
		  for(int i=0;i<strs.length;i++){
			  String tmp = sort(strs[i]);
			  if(cur.containsKey(tmp))
				  cur.get(tmp).add(strs[i]);
			  else{
				  List<String> t = new ArrayList<String>();
				  t.add(strs[i]);
				  cur.put(tmp, t);
			  }
		  }
		  for(String key: cur.keySet()){
			  if(cur.get(key).size()>1){
				  ret.addAll(cur.get(key));
			  }
		  }
		  return ret;
	  }
	  
	  private String sort(String str){
		  char[] c = str.toCharArray();
		  Arrays.sort(c);
		  return String.valueOf(c);
	  }
	  /**
	   * judge whether the two strings are anagrams
	   * @param str1
	   * @param str2
	   * @return
	   * @author huangting
	   * @date 2014-7-1
	   */
	  private boolean isAna(String str1, String str2){
		  if(str1.length()!=str2.length())
			  return false;
		  //by hashMap
		  HashMap<Character,Integer> t1 = new HashMap<Character,Integer>();
		  HashMap<Character,Integer> t2 = new HashMap<Character,Integer>();
		  for(int i=0;i<str1.length();i++){
			  char c1 = str1.charAt(i);
			  char c2 = str2.charAt(i);
			  if(t1.containsKey(c1))
				  t1.put(c1, t1.get(c1)+1);
			  else
				  t1.put(c1, 1);
			  if(t2.containsKey(c2))
				  t2.put(c2, t2.get(c2)+1);
			  else
				  t2.put(c2, 1);
		  }
		  for(Entry<Character,Integer> e : t1.entrySet()){
			  if(e.getValue()!=t2.get(e.getKey()))
				  return false;
		  }
		  
		  return true;
		 /* //by sorting
		  char[] c1 = str1.toCharArray();
		  char[] c2 = str2.toCharArray();
		  
		  Arrays.sort(c1);
		  Arrays.sort(c2);
		  for(int i=0;i<c1.length;i++){
			  if(c1[i]!=c2[i])
				  return false;
		  }
		  return true;*/
		 /* int[] t1 = new int[26];
		  int[] t2 = new int[26];
		  for(int i=0;i<str1.length();i++){
			  t1[str1.charAt(i)-'a']++;
			  t2[str2.charAt(i)-'a']++;
		  }
		  //check if the two hash tables are the same
		  for(int i=0;i<26;i++){
			  if(t1[i]!=t2[i])
				  return false;
		  }
		  return true;*/
	  }
	 /**
	  * by recursive algorithm : recompute the same thing many times
	  * not good
	  * @param x
	  * @param n
	  * @return
	  * @author huangting
	  * @date 2014-7-2
	  */
	 public double pow(double x, int n) {
	     if(n == 0)
	    	 return 1;
	     if(n == 1)
	    	 return x;
	     if(n%2 == 0)
	    	 return pow(x,n/2)*pow(x,n/2);
	     else
	    	 return x*pow(x,(n-1)/2)*pow(x,(n-1)/2);
	 }
	 public double pow2(double x, int n){
		 if(n == 0)
	    	 return 1;
	     if(n == 1)
	    	 return x;
	     double[] f = new double[32]; //f[i] means x^(2^i)
	     f[0] = x;
	     for(int i=1;i<f.length;i++){
	    	 f[i] = f[i-1]*f[i-1];
	     }
	     boolean sign = n>0? true:false;
	     double ret = 1;
	     int tmp = Math.abs(n);
	     int i=0; 
	     while(tmp>0){
	    	int dig = tmp%2;
	    	tmp = tmp/2;
		  	if(dig == 1)
		  	{
		  		ret*=f[i];
		  	}
		  	i++;
	     }
	     if(!sign)
	    	 return 1.0/ret;
	     return ret;
	 }
	 /**
	  * all possible solutions : backtrack algorithm
	  * 
	  * @param n
	  * @return
	  * @author huangting
	  * @date 2014-7-2
	  */
	 public List<String[]> solveNQueens(int n) {
	    List<String[]> ret = new ArrayList<String[]>();
	    //two base cases
	    if(n<=0)
	    	return ret;
	    if(n == 1){
	    	String[] s = {"Q"};
	    	ret.add(s);
	    	return ret;
	    }
	    //board
	    char[][] boards = new char[n][n];
	    for(int i=0;i<n;i++){
	    	for(int j=0;j<n;j++){
	    		boards[i][j] = '.';
	    	}
	    }
	    //only consider one half, another half can be solved by symmetrical
	    for(int i=0;i<n;i++){
	    	boards[0][i] = 'Q';
	    	backtrack(boards,1,n,ret);
	    	boards[0][i] = '.';
	    }

	    return ret;
	 }
	 private void backtrack(char[][] b, int idx, int n, List<String[]> ret){
		 if(idx >= n){
			 String[] r = new String[n];
			 for(int i=0;i<n;i++){
				 r[i] = String.valueOf(b[i]);
			 }
			 ret.add(r);
			 return ;
		 }
		 //[idx][i]
		 for(int i=0;i<n;i++){
			 //every location has to be checked if it's legal, that is, if the current location is conflict with the chosen locations
			 boolean flag = true;
			 for(int j=0;j<idx;j++){
				 for(int k=0;k<n;k++){
					 if(b[j][k] == 'Q'){
						 // --  | \
						  // System.out.println("idx :" + idx+" j :" +j +" i :" + i+" k: "+k);
						 if(k==i || Math.abs((idx-j))==Math.abs((i-k))){ // only one conflict is enough
							 flag = false;
							 break; 
						 }
					 }
				 }
				 if(!flag)
					 break;
			}
			 if(flag) // no conflicts
			 {
				 b[idx][i] = 'Q';
				 backtrack(b,idx+1,n,ret);
				 b[idx][i] = '.';
			 }
		 }
	 }
	 int sum = 0;
	 public int totalNQueens(int n) {
		 int uplimit = (1<<n)-1; //形成一个n个1

		 bitBacktrack(0,0,0,uplimit);
		 return sum;
	 }
	 /**
	  * using bit to backtrack
	  * @param row means the columns already chosen before
	  * @param ld means the \ already chosen before
	  * @param rd means the / already chosen before
	  * @return
	  * @author huangting
	  * @date 2014-7-2
	  */
	 private void bitBacktrack(int row, int ld, int rd, int uplimit){
		 //int ans = 0;
		 if(row < uplimit){
			 //first get all illegal positions by row|ld|rd, then get the legal positions by & with ~(row|ld|rd)
			 int pos = uplimit & ~(row|ld|rd);
			 while(pos>0){
				 int p = pos&(~pos+1); // the number of the most right location of 1
				 pos = pos - p;
				 bitBacktrack(row|p,(ld|p)>>1,(rd|p)<<1,uplimit);
			 }
		 }else
			 sum++;
	 }
	 /**
	  * DP solution
	  * @param A
	  * @return
	  * @author huangting
	  * @date 2014-7-2
	  */
	 public int maxSubArray(int[] A) {
	        int[] d = new int[A.length];
	        d[0] = A[0];
	        int ret = d[0];
	        for(int i=0;i<A.length;i++){
	        	d[i] = A[i];
	        	if(d[i-1]>0)
	        		d[i] += d[i-1];
	        	if(d[i]>ret)
	        		ret = d[i];
	        }
	        return ret;
	  } 
	 /**
	  * divide and conquer
	  * @param A
	  * @return
	  * @author huangting
	  * @date 2014-7-2
	  */
	 public int maxSubArray2(int[] A) {
		return divideAndConq(A,0,A.length-1);
	 }
	 private int divideAndConq(int[] A, int start, int end){
		 if(start>end)
			 return Integer.MIN_VALUE;
		 if(start == end)
			 return A[start];
		 int le = start+(end-start)/2;
		 int rs = start+(end-start)/2+1;
		 int left = divideAndConq(A,start,le);
		 int right = divideAndConq(A,rs,end);
		 int lhalf = A[le];
		 int rhalf = A[rs];
		 int ltmp = A[le--];
		 for(int i=le;i>=start;i--){
			 ltmp +=A[i];
			 if(ltmp>lhalf)
				 lhalf = ltmp;
		 }

		 int rtmp = A[rs++];
		 for(int i=rs;i<=end;i++){
			 rtmp +=A[i];
			 if(rtmp>rhalf)
				 rhalf = rtmp;
		 }
		 return Math.max((lhalf+rhalf), Math.max(left, right));
	 }
	/**
	 * recursive algorithm will cost lot on the reforming matrix, so give up
	 * @param matrix
	 * @return
	 * @author huangting
	 * @date 2014-7-2
	 */
	public List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> ret = new ArrayList<Integer>();
		if(matrix == null || matrix.length<=0)
			return ret;
	     int m = matrix.length;
	     int n = matrix[0].length;
	     
	     int mc = m;//rows
	     int nc = n; //column
	     
	     int cnt = 0;
	     int total = m*n;//the total elements
	     while(cnt<total){
	    	 int comN = n-nc;
	    	 int comM = m-mc;
	    	 //->
	    	 if(cnt<total){
	    		 for(int i=0;i<nc;i++){
	    			 int x = comM/2;
	    			 int y = comN/2 + i;
	    			 ret.add(matrix[x][y]);
	    			 cnt++;//count
	    		 }
	    		 mc--;//rows has consumed by 1
	    	 }
	    	 comN = n-nc;
	    	 comM = m-mc;
	    	 //|
	    	 if(cnt<total){
	    		for(int i=0;i<mc;i++){
	    			int x = (comM+1)/2+i;
	    			int y = n-(comN/2)-1;
	    			ret.add(matrix[x][y]);
	    			cnt++;
	    		}
	    		nc--;//column has consumed by 1
	    	 }
	    	 comN = n-nc;
	    	 comM = m-mc;
	    	 //<-
	    	 if(cnt<total){
	    		 for(int i=nc;i>0;i--){
	    			 int x = m-(comM/2)-1;
	    			 int y = comN/2+i-1;
	    			 ret.add(matrix[x][y]);
	    			 cnt++;
	    		 }
	    		 mc--;
	    	 }
	    	 comN = n-nc;
	    	 comM = m-mc;
	    	 //|^
	    	 if(cnt<total){
	    		 for(int i=mc;i>0;i--){
	    			 int x = (comM+1)/2+i-1;
	    			 int y = comN/2;
	    			 ret.add(matrix[x][y]);
	    			 cnt++;
	    		 }
	    		 nc--;
	    	 }
	     }
	     return ret;
	}
	/**
	 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
	 * DP
	 * @param A
	 * @return
	 * @author huangting
	 * @date 2014-7-4
	 */
	public boolean canJump(int[] A) {
        if(A==null || A.length<=0)
        	return true;
        //boolean[] p = new boolean[A.length];
        //p[A.length-1] = true;
        int last = A.length-1;
        for(int i=A.length-2;i>=0;i--){
        	if(A[i]>=(last-i))
        		last = i;
        }
        return last == 0;
    }
	public List<Interval> merge(List<Interval> intervals) {
		if(intervals == null || intervals.size() <= 1)
        	return intervals;
		Collections.sort(intervals,new IntervalComparator());
        List<Interval> ret = new ArrayList<Interval>();
        
        for(int i=0;i<intervals.size();i++){
        	if(i == 0 )
        	{
        		if(intervals.get(i).end>=intervals.get(i+1).start){ // has overlaping
        			Interval itv = new Interval();
        			itv.start = intervals.get(i).start;
        			if(intervals.get(i).end>=intervals.get(i+1).end){//include i+1
        				itv.end = intervals.get(i).end;
        			}else
        				itv.end = intervals.get(i+1).end;
        			ret.add(itv);
        		}else{
        			ret.add(intervals.get(i));
        			ret.add(intervals.get(i+1));
        			i++;
        		}
        	}else{
        		if(ret.get(ret.size()-1).end>=intervals.get(i).start){ // has overlaping
        			if(ret.get(ret.size()-1).end<intervals.get(i).end)//include i+1
        				ret.get(ret.size()-1).end = intervals.get(i).end;
        		}else{ //no overlapping
        			ret.add(intervals.get(i));
        		}
        	}
        }
        return ret;
    }
	/**
	 * first insert then merge
	 * @param intervals
	 * @param newInterval
	 * @return
	 * @author huangting
	 * @date 2014-7-4
	 */
	public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> ret = new ArrayList<Interval>();
       	boolean f1 = false; // mark if the start has found its proper location
    	boolean f2 = false; // mark if the start has found its proper location
    	boolean f = false; // if itv has overlap with number i interval
        Interval itv = new Interval();
        if(intervals == null || intervals.size()<=0)
        {
        	ret.add(newInterval);
        	return ret;
        }
    	for(int i=0;i<intervals.size();i++){
    		
       		if(!f1){// has not found the proper location for start
       			if(newInterval.start<=intervals.get(i).start)
       			{
       				itv.start = newInterval.start;
       				f1 = true;
       			}
       			else if(newInterval.start<=intervals.get(i).end)
       			{
       				itv.start = intervals.get(i).start;
       				f1 = true;
       			}
       		}
       		if(f1&&!f2){
       			if(newInterval.end<intervals.get(i).start){
       				itv.end = newInterval.end;
       				f2 = true;
       			}else if(newInterval.end<=intervals.get(i).end){
       				itv.end = intervals.get(i).end;
       				f = true;
       				f2 = true;
       			} if(i==intervals.size()-1 && newInterval.end>intervals.get(i).end){
       				itv.end = newInterval.end;
       				f = true;
       				f2 = true;
       			}
       		}
       		if(f1 && f2){
       			ret.add(itv);
       			if(f){
       				if(i+1<intervals.size())
       					ret.addAll(intervals.subList(i+1, intervals.size()-1));
       			}else{
       				ret.addAll(intervals.subList(i, intervals.size()));
       			}
       			break;
       		}
       		if(!f1){
       			ret.add(intervals.get(i));
       		}
       	}
    	//still not insert 
    	if(!f1){
    		ret.add(newInterval);
    	}
    	return ret;
    }
	
	 public int lengthOfLastWord(String s) {
		 if(s == null || s.length()<=0)
			 return 0;
		 int cnt = 0; // the length of the current word
		 int last = 0;//the length of the last word
		 
		 for(int i=0;i<s.length();i++){
			 if(s.charAt(i) == ' '){
				 if(cnt != 0)
					 last = cnt;
				 cnt = 0;
			 }else{
				 cnt++;
			 }
		 }
		 if(cnt!=0)
			 last = cnt;
		 return last;
	 }
	 public int[][] generateMatrix(int n) {
		 int[][] ret = new int[n][n];
		 if(n == 0)
			 return ret;
		 subGen(ret,n,1,n);
		 return ret;
	 }
	 /**
	  * 
	  * @param ret
	  * @param wid = n
	  * @param start inilized 1
	  * @author huangting
	  * @date 2014-7-4
	  */
	 private void subGen(int [][] ret, int wid, int start, int n){
		 if(wid<=0)
			 return;
		 if(wid == 1){
			 ret[(n-wid)/2][(n-wid)/2] = n*n;
			 return;
		 }
		 int x = (n-wid)/2;
		 int y = (n-wid)/2;
		 //row 1
		 for(int i=0;i<wid-1;i++){
			 ret[x][y+i] = start++;
		 }
		 //col 1
		 for(int i=0;i<wid-1;i++){
			 ret[x+i][y+wid-1] = start++;
		 }
		 //row 2
		 for(int i=0;i<wid-1;i++){
			 ret[x+wid-1][y+wid-1-i] = start++;
		 }
		 //col 2
		 for(int i=0;i<wid-1;i++){
			 ret[x+wid-1-i][y] = start++;
		 }
		 subGen(ret,wid-2,start,n);
	 }
	 /**
	  * by divide and conque method
	  * @param n
	  * @param k
	  * @return
	  * @author huangting
	  * @date 2014-7-7
	  */
	 public String getPermutation(int n, int k) {
		 int [] f = new int[n+1];
		 f[1] = 1;
		 //compute all the n!
		 for(int i=2;i<=n;i++)
			 f[i]=(i)*f[i-1];
		 
		 //get the list
		 List<Integer> num = new ArrayList<Integer>();
		 for(int i=1;i<=n;i++)
			 num.add(i);
		 return subGet(num, k, f);
	 }
	 private String subGet(List<Integer> num, int k, int[] f){
		 if(num.size() == 1)
			 return String.valueOf(num.get(0));
		 int n = num.size(); // the number of the left numbers
		 int kth = (k-1)/f[n-1]; // make sure the first number of kth permutation is what
		 int first = num.remove(kth);
		 return String.valueOf(first) + subGet(num,k-kth*f[n-1], f);
	 }
	 /**
	  * Given a list, rotate the list to the right by k places, where k is non-negative.
	  * solution : first link the tail to the head, then move (len-k) step from head, the
	  * new head is the pointer stopped
	  * @param head
	  * @param n
	  * @return
	  * @author huangting
	  * @date 2014-7-7
	  */
	 public ListNode rotateRight(ListNode head, int n) {
	    if(head == null || head.next == null || n == 0)
	    	return head;
	    int len = 1;
	    ListNode tmp = head;
	    while(tmp.next!= null)
	    {
	    	tmp = tmp.next;
	    	len++;
	    }
	    tmp.next = head;
	    n = n % len;
	    int k = len - n;
	    int i = 1;
	    while(i++<=k)
	    	tmp = tmp.next;
	    head = tmp.next;
	    tmp.next = null;
	    return head;
	 }
	 /**
	  * A robot is located at the top-left corner of a m x n grid
	  * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid 
	  * How many possible unique paths are there?
	  * Solution : DP
	  *    assume dp[i][j] is the unique paths to reach grid[i][j]
	  *     dp[i][j] = dp[i-1][j] + dp[i][j-1];
	  * @param m
	  * @param n
	  * @return
	  * @author huangting
	  * @date 2014-7-7
	  */
	 public int uniquePaths(int m, int n) {
	     int dp[][] = new int[m][n];
	     for(int i=0;i<n;i++)
	    	 dp[0][i] = 1;
	     for(int j=0;j<m;j++)
	    	 dp[j][0] = 1;
	     for(int i=1;i<m;i++){
	    	 for(int j=1;j<n;j++){
	    		 dp[i][j] = dp[i-1][j] + dp[i][j-1];
	    	 }
	     }
	     return dp[m-1][n-1];
	 }
	 
	 public int uniquePathsWithObstacles(int[][] obstacleGrid) {
		 if(obstacleGrid == null || obstacleGrid.length == 0)
			 return 0;
		 int m = obstacleGrid.length;
		 int n = obstacleGrid[0].length;
		 
		 int dp[][] = new int[m][n];
		 dp[0][0] = obstacleGrid[0][0] == 0 ? 1:0;
	     for(int i=1;i<n;i++)
	    	 dp[0][i] = obstacleGrid[0][i] == 0? dp[0][i-1] : 0;
	     for(int j=1;j<m;j++)
	    	 dp[j][0] = obstacleGrid[j][0] == 0? dp[j-1][0] : 0;
	     for(int i=1;i<m;i++){
	    	 for(int j=1;j<n;j++){
	    		 if(obstacleGrid[i][j] == 1)
	    			 dp[i][j] = 0;
	    		 else
	    			 dp[i][j] = dp[i-1][j] + dp[i][j-1];
	    	 }
	     }
	     return dp[m-1][n-1];
	 }
	 /**
	  * DP solution
	  * @param grid
	  * @return
	  * @author huangting
	  * @date 2014-7-8
	  */
	 public int minPathSum(int[][] grid) {
	        if(grid == null || grid.length == 0 )
	        	return 0;
	        int m = grid.length;
	        int n = grid[0].length;
	        int [][] dp = new int[m][n];
	        dp[0][0] = grid[0][0];
	        
	        for(int i = 1;i<n;i++)
	        	dp[0][i] = dp[0][i-1]+grid[0][i];
	        for(int j=1;j<m;j++)
	        	dp[j][0] = dp[j-1][0]+grid[j][0];
	        
	        for(int i=1;i<m;i++){
	        	for(int j=1;j<n;j++){
	        		dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1])+grid[i][j];
	        	}
	        }
	        return dp[m-1][n-1];
	 } 
	 /**
	  * Merge two sorted linked lists and return it as a new list. 
	  * The new list should be made by splicing together the nodes of the first two lists.
	  * @param l1
	  * @param l2
	  * @return
	  * @author huangting
	  * @date 2014-7-8
	  */
	 public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		 // if there is one list is empty
		 if(l1 == null )
			 return l2;
		 if(l2 == null )
			 return l1;
		 ListNode ret = null;
		 ListNode point = null;
		 ListNode s1 = l1;//the start node of l1
		 ListNode e1 = l1;//the end node of l1
		 ListNode s2 = l2;
		 ListNode e2 = l2;
		 ListNode tmp1 = null;
		 while(e1!=null && e2!=null){
			 while(e1!=null && e2!=null && e1.val<=e2.val)
			 {
				 tmp1 = e1;
				 e1 = e1.next;
			 }
			 if(s1!=e1){ //move forward already
				 if(ret == null){
					 ret = s1;
					 point = ret;
				 }else
					 point.next = s1;
				 point = tmp1;
				 s1 = e1; //new begin
			 }
			 while(e1!=null && e2!=null &&e2.val<=e1.val){
				 tmp1 = e2;
				 e2 = e2.next;
			 }
			 if(s2!=e2){
				 if(ret == null){
					 ret = s2;
					 point = ret;
				 }else
					 point.next = s2;
				 point = tmp1;
				 s2 = e2;
			 }
		 }
		 if(s1 == null && s2 == null)
			 return ret;
		 else if(s1 == null){
			 point.next = s2;
			 return ret;
		 }else{
			 point.next = s1;
			 return ret;
		 }
	 }
	 /**
	  * Given two binary strings, return their sum (also a binary string).
	  * @param a
	  * @param b
	  * @return
	  * @author huangting
	  * @date 2014-7-9
	  */
	 public String addBinary(String a, String b) {
		 //the input is not legal
	     if(a == null || b==null || a.length()==0 || b.length()==0)
	    	 return null;
	     StringBuilder sb = new StringBuilder();
	     
	 }
	public static void main(String[] argv){
		Solution2 s = new Solution2();
		String A = " ";
		ListNode r1 = new ListNode(-10);
		r1.next = new ListNode(-10);
		r1.next.next = new ListNode(-9);
		r1.next.next.next = new ListNode(-4);
		r1.next.next.next.next = new ListNode(1);
		ListNode r2 = new ListNode(-7);
		
		System.out.println(s.mergeTwoLists(r1, r2));
		/*char[][] board = new char[9][9];
		board[0] = "..9748...".toCharArray();
		board[1] = "7........".toCharArray();
		board[2] = ".2.1.9...".toCharArray();
		board[3] = "..7...24.".toCharArray();
		board[4] = ".64.1.59.".toCharArray();
		board[5] = ".98...3..".toCharArray();
		board[6] = "...8.3.2.".toCharArray();
		board[7] = "........6".toCharArray();
		board[8] = "...2759..".toCharArray();*/
		//s.solveSudoku(board);
		//s.countAndSay(5);
	}
}
