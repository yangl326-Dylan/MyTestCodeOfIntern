package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution3 {
	/**
	 * Validate if a given string is numeric
	 * @param s
	 * @return
	 * @author huangting
	 * @date 2014-7-14
	 */
	public boolean isNumber(String s) {
        boolean point = false;
        boolean slash = false;
        boolean e = false;
        String ns = s.trim();
        if(ns.length()<=0)
        	return false;
        boolean sign = ns.charAt(0)=='-' || ns.charAt(0)=='+'? true:false;
        if(sign && ns.length()<=1)
        	return false;
        boolean first = ns.charAt(0)>='0' && ns.charAt(0)<='9'? true:sign && ns.charAt(1)>='0' && ns.charAt(1)<='9' ? true: false;
        boolean last = ns.charAt(ns.length()-1)>='0' && ns.charAt(ns.length()-1)<='9'? true:false;
        int i=0;
        if(sign)
        	i=1;
        for(;i<ns.length();i++){
        	char tmp = ns.charAt(i);
        	if(tmp>='0' && tmp<='9')
        	{
        		continue;
        	}
        	if(first && tmp == '/'){
        		if(!point && !e && slash == false) //before / we should not have . or e
        			slash = true;
        		else
        			return false;
        		continue;
        	}
        	if(tmp == '.'){
        		if(!slash&&!e&&point == false) //before e we should not have e or /
        			point = true;
        		else
        			return false;
        		continue;
        	}
        	if(tmp == 'e'){
        		if(first && e == false) // before e we should have digits
        			e = true;
        		else
        			return false;
        		continue;
        	}
        	return false;
        }
        return last==true? true:first && point? true: false ;
    }
	public boolean isNumber2(String s) {
		 String ns = s.trim(); // remove the head and the tail spaces
		 if(ns.length()<=0)
			 return false;
		 boolean sign = ns.charAt(0)=='-' || ns.charAt(0)=='+'? true:false; // if there is a sign
	     if(sign && ns.length()<=1)
	        return false;
		 boolean point = false; // if there is a .
	     boolean slash = false; // if there is a /
	     boolean e = false; // if there is a e
	     
	     int i = 0;
	     if(sign)
	    	 i=1;
	     for(; i<ns.length();i++){
	    	 
	     }
	}
	/**
	 * Given a non-negative number represented as an array of digits, plus one to the number.
	 * @param digits
	 * @return
	 * @author huangting
	 * @date 2014-7-15
	 */
	public int[] plusOne(int[] digits) {
		int carray = 1;
		List<Integer> ret = new ArrayList<Integer>();
		
		for(int i= digits.length-1;i>=0;i--){
			ret.add((digits[i]+carray)%10);
			carray = (digits[i]+carray)/10;	
		}
		if(carray == 1)
			ret.add(1);
		int[] r = new int[ret.size()];
		for(int i=0;i<ret.size();i++)
			r[ret.size()-i-1] = ret.get(i);
		return r;
	}
	/**
	 * Text Justification
	 * @param words
	 * @param L
	 * @return
	 * @author huangting
	 * @date 2014-7-15
	 */
	public List<String> fullJustify(String[] words, int L) {
        int even = 0;
        int odd = 0;
        int len = 0; //keep the sum length of the string
        int sIdx = 0;
        int eIdx = 0;
        List<String> ret = new ArrayList<String>();
        if(words[0].length()==0)
        {
        	StringBuilder sb = new StringBuilder("");
        	int n = 0;
			while(n++<L){
				sb.append(" ");
			}
			ret.add(sb.toString());
			return ret;
        }
        for(int i=0;i<words.length;i++){
        	len += words[i].length();
        	if(len<=L-(eIdx-sIdx)){
        		eIdx++;
        	}else{
        		even = eIdx-1==sIdx ? L-(len-words[eIdx].length()) : (L-(len-words[eIdx].length()))/(eIdx-1-sIdx);
        		odd = eIdx-1==sIdx ? 0: (L-(len-words[eIdx].length()))%(eIdx-1-sIdx);
        		StringBuilder sb = new StringBuilder();
        		//insert spaces
        		for(int j=sIdx;j<eIdx;j++)
        		{
        			sb.append(words[j]);
        			if(j-sIdx+1<=odd){
        				int n = 0;
        				while(n++<even+1){
        					sb.append(" ");
        				}
        			}else if(j!=eIdx-1){
        				int n = 0;
        				while(n++<even){
        					sb.append(" ");
        				}
        			}
        		}
        		//only one word, we append spaces in the tail
        		if(eIdx-1==sIdx )
        		{
        			int n = 0;
    				while(n++<even){
    					sb.append(" ");
    				}
        		}
        		ret.add(sb.toString());
        		
        		//update params
        		sIdx = eIdx;
        		i--;
        		len = 0;
        	}
        }
        //the last line
        if(len!=0){
        	even = L-len-(eIdx-1-sIdx);
    		StringBuilder sb = new StringBuilder();
    		//insert spaces
    		for(int j=sIdx;j<eIdx;j++)
    		{
    			sb.append(words[j]);
    			if(j!=eIdx-1){
    				sb.append(" ");
    			}
    		}
    		//only one word, we append spaces in the tail
        	int n = 0;
			while(n++<even){
				sb.append(" ");
			}
    		ret.add(sb.toString());
        }
        return ret;
    }
	/**
	 * Implement int sqrt(int a).
	 * 采用牛顿法，迭代公式x(n+1) = 0.5*(x(n)+a/x(n));
	 * @param x
	 * @return
	 * @author huangting
	 * @date 2014-7-15
	 */
	public int sqrt(int x) {
		double g = x;
		while(Math.abs(g*g-x)>0.02)
			g = 0.5*(g+x/g);
		return (int) g;
    }
	/**
	 * You are climbing a stair case. It takes n steps to reach to the top.
	 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
	 * Fibonacci
	 * @param n
	 * @return
	 * @author huangting
	 * @date 2014-7-16
	 */
	public int climbStairs(int n) {
        int [] f = new int[n+1];
        f[0] = 1;
        f[1] = 1;
        for(int i=2;i<=n;i++)
        	f[i]=f[i-1]+f[i-2];
        return f[n];
    }
	/**
	 * Given two words word1 and word2, 
	 * find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
	 * @param word1
	 * @param word2 和这个匹配
	 * @return
	 * @author huangting
	 * @date 2014-7-16
	 */
	public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] ed = new int[m+1][n+1];
        ed[0][0] = 0;
        for(int i=1;i<=m;i++)
        	ed[i][0] = i; //delete word1[i];
        for(int j=1;j<=n;j++)
        	ed[0][j] = j; //add word2[j];
        
        for(int i=1;i<=m;i++){
        	for(int j=1;j<=n;j++){
        		if(word1.charAt(i-1) == word2.charAt(j-1))
        			ed[i][j] = ed[i-1][j-1];
        		else{
        			ed[i][j] = ed[i-1][j]+1;//delete word1[i]
        			int min = ed[i-1][j-1] + 1 < ed[i][j-1]+1 ? ed[i-1][j-1] + 1 : ed[i][j-1]+1; //replace word1[i] with word2[j] or add word2[j] into word1
        			ed[i][j] = min<ed[i][j] ? min: ed[i][j];
        		}
        	}
        }
        return ed[m][n];
    }
	/**
	 * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.
	 * using the first row and column to store information
	 * @param matrix
	 * @author huangting
	 * @date 2014-7-16
	 */
	public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean fRow = false;
        boolean fCol = false;
        
        for (int i = 0; i < m; i++)
			if(matrix[i][0] == 0)
			{
				fCol = true;
				break;
			}
		
		for (int j = 0; j < n; j++)
			if(matrix[0][j] == 0)
			{
				fRow = true;
				break;
			}
		
        //using the first row and column
        for(int i=1;i<m;i++){
        	for(int j=1;j<n;j++){
        		if(matrix[i][j] == 0)
        		{
        			matrix[0][j] = 0;
        			matrix[i][0] = 0;
        		}
        	}
        }
        
        for(int i=1;i<m;i++)
        {
        	if(matrix[i][0] == 0){
        		for(int j=1;j<n;j++)
        			matrix[i][j] = 0;
        	}
        }
        
        for(int j=1;j<n;j++){
        	if(matrix[0][j] == 0){
        		for(int i=1;i<m;i++)
        			matrix[i][j] = 0;
        	}
        }
        //it should be processed later

		
		if(fCol){
			for (int i = 0; i < m; i++)
				matrix[i][0] = 0;
		}
		if(fRow){
			for (int j = 0; j < n; j++)
				matrix[0][j] =  0;
		}
       
	}
	/**
	 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
	 * Integers in each row are sorted from left to right.
	 * The first integer of each row is greater than the last integer of the previous row.
	 * @param matrix
	 * @param target
	 * @return
	 * @author huangting
	 * @date 2014-7-16
	 */
	public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0; 
        int right = m-1;
        int mid = left+(right-left)/2;
        int row = mid;
        while(left<=right){
        	if(matrix[mid][0] == target)
        		return true;
        	if(matrix[mid][0]<target){
        		left = mid+1;
        	}else{
        		right = mid-1;
        	}
        	mid = left+(right-left)/2;
        }
        
        row = left<0||left>=m? right:right<0||right>=m?left:target>matrix[left][0]? left:right;
        left = 0;
        right = n-1;
        mid = left + (right-left)/2;
        while(left<=right){
        	if(matrix[row][mid] == target)
        		return true;
        	if(matrix[row][mid]<target){
        		left = mid+1;
        	}else{
        		right = mid-1;
        	}
        	mid = left+(right-left)/2;
        }
        return false;
    }
	/**
	 * Sort Colors
	 * with an one-pass algorithm using only constant space
	 * @param A
	 * @author huangting
	 * @date 2014-7-16
	 */
	public void sortColors(int[] A) {
        
    }
	public static void main(String[] args){
		Solution3 s = new Solution3();
		int[][] a = {{1,   3,  5,  7},{10, 11, 16, 20},{23, 30, 34, 50}};
		String[] ret = {"What","must","be","shall","be."};
		System.out.println(s.searchMatrix(a,50));
	}
}
