package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
 class ListNode {
     int val;
     ListNode next;
     ListNode(int x) {
         val = x;
         next = null;
     }
 }
public class Solutions {
	/**
	 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai).
	 *  n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and 
	 * (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.
	 * this solution will get TLE
	 * after handling the special cases, it's finally accepted
	 * @param height
	 * @return
	 * @author Administrator
	 * @date 2014-5-27
	 */
	public int maxArea(int[] height){
		//can't form a container
		if(height == null || height.length<=1)
			return 0;
		int len = height.length;
		int[] left = new int[len];//left[i] is to record the largest distance from the left side to the ith line, and a[left[i]]>=a[i]
		int[] right = new int[len];//similar to left, but from the right side
		//specially handler for ascending or descending array
		boolean flag = false;
		int area = height[len-1]*(len-1);
		//the descending one
		for(int i=len-2;i>=0;i--){
			if(height[i]<height[i+1]){
				flag = true;
				break;
			}else{
				if(height[i]*i>area)
					area = height[i]*i;
			}
		}
		if(flag == false)
			return area;
		//ascending one
		flag = false;
		area = height[0]*(len-1);
		for(int i =1;i<len;i++){
			if(height[i]<height[i-1])
			{
				flag = true;
				break;
			}else{
				if(height[i]*(len-1-i)>area)
					area = height[i] * (len-1-i);
			}
		}
		if(flag == false)
			return area;
		//caculate the left array
		left[0] = 0;
	
		for(int i=1;i<len;i++){
			int index = i-1;
			left[i] = 0;
			while(index>=0){
				if(height[index] >= height[i]){//it can make sure that height[left[index]]>=a[i]
					left[i] = (i-index);
					if(left[index]==0)
						index --;
					else
						index -= left[index];
				}else{//to check the next left one
					index -- ;
				}
			}
		}
		//caculate the right array
		right[len-1] = 0;
		
		for(int i = len-2;i>=0;i--){
			int index = i+1;
			right[i] = 0;
			while(index<len){
				if(height[index]>height[i]){
					right[i] = index-i ;
					if(right[index]==0)
						index ++;
					else
						index += right[index];
				}else{
					index++;
				}
			}
		}
		
		//caculate the largest area
		int max = 0;
		for(int i=0;i<len;i++){
			int temp = (left[i]+right[i])*height[i];
			if(temp>max)
				max = temp;
		}
		return max;
	}
	//greedy algorithm solution
	public int maxArea2(int[] height){
		int left = 0, right = height.length-1;
		int max = 0;
		while(left<right){
			max = Math.max(max, (right-left)*(Math.min(height[left], height[right]))); 
			if(height[left]<height[right])
			{
				left++;
			}else
				right--;
		}
		return max;
	}
	//Given an integer, convert it to a roman numeral.
	//Input is guaranteed to be within the range from 1 to 3999.
	public String intToRoman(int num) {
        return null;
    }
	
	public String longestCommonPrefix(String[] strs){
		if(strs == null || strs.length ==0)
			return "";
		if(strs.length == 1)
			return strs[0];
		for(int i=0;i<strs[0].length();i++){
			String temp = strs[0].substring(i, i+1);
			boolean flag = true;
			for(int j=1;j<strs.length;j++){
				if(strs[j].length()<i+1)
				{
					flag = false;
					break;
				}
				String target = strs[j].substring(i,i+1);
				if(!target.equals(temp)){
					flag = false;
					break;
				}
			}
			if(flag == false){
				return strs[0].substring(0, i);
			}
		}
		return strs[0];
	}
	public List<List<Integer>> threeSum(int[] num) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
		if(num.length<=2)
        	return ret;
		//first sort the num array
		Arrays.sort(num);
		
		for(int i=0;i<num.length;i++){
			if(i>=1 && num[i]==num[i-1])//which has computed in the last round
				continue;
			int sum = num[i] * (-1);
			int start = i+1;
			int end = num.length-1;
			
			while(start<end){
				if(start>i+1 && num[start] == num[start-1])
				{
					start++;
					continue;
				}
				if(num[start]+num[end] == sum){
					ArrayList<Integer> list = new ArrayList<Integer>();
					list.add(num[i]);
					list.add(num[start]);
					list.add(num[end]);
					ret.add(list);
					start++; // continue to get the next pair
					continue;
				}
				if(num[start]+num[end] < sum)
					start++;
				else
					end--;
			}
		}
		
		return ret;
    }
	public List<List<Integer>> fourSum(int[] num, int target) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		if(num.length>=4)
		{
			Arrays.sort(num);
			if(num[0]+num[1]+num[2]+num[3]>target || 
					num[num.length-1]+num[num.length-2]+num[num.length-3]+num[num.length-4]<target)
				return ret;
			rec4sum(num,4,0,target,null,ret);
		}
		return ret;
    }
	/**
	 * 
	 * @param num the array
	 * @param index 4->3->2->1
	 * @param start the possible start index
	 * @param target the current target
	 * @param list the current possible result list
	 * @param ret the total result
	 * @author Administrator
	 * @date 2014-6-3
	 */
	private void rec4sum(int[] num, int index, int start,int target, List<Integer> list, List<List<Integer>> ret){
		//base case
		if(index == 1){
			for(int i=start;i<num.length;i++){
				if(i!=start && num[i] == num[i-1])
					continue;
				
				if(num[i] == target){
					List<Integer> li = new ArrayList<Integer>();
					li.addAll(list);
					li.add(num[i]);
					//first check whether list has been in the ret
					boolean flag = false;
					for(List<Integer> lis: ret){
						boolean subflag = false;//by default, li and list are the same
						//the following needs to consider carefully
						for(Integer it:lis){
							if(!li.contains(it))
							{
								subflag = true;
								break;
							}
						}
						for(Integer it:li){
							if(!lis.contains(it))
							{
								subflag = true;
								break;
							}
						}
						//if li and list are the same
						if(subflag == false){
							flag = true;
							break;
						}
					}
					//list has been in the result
					if(flag == true){
						continue;
					}else{
						ret.add(li);
					}
				}
			}
			return;
		}
		for(int i = start;i<=num.length-index; i++){
			List<Integer> li = new ArrayList<Integer>();
			if(list != null && list.size()>0)
				li.addAll(list);
			//this is a effective pruning method
			if(num[i]*index > target)
				break;
			li.add(num[i]);
			rec4sum(num,index-1,i+1,target-num[i],li,ret);
		}
	}
	/**
	 * return all possible letter combinations
	 * @param digits
	 * @return
	 * @author Administrator
	 * @date 2014-6-3
	 */
	public List<String> letterCombinations(String digits) {
        HashMap<Integer, String> map = new HashMap<Integer,String>();
        map.put(2, "abc");
        map.put(3, "def");
        map.put(4, "ghi");
        map.put(5, "jkl");
        map.put(6, "mno");
        map.put(7, "pqrs");
        map.put(8, "tuv");
        map.put(9, "wxyz");
        List<String> ret = new ArrayList<String>();
        
        if(digits.length()>0){
        	sub4letters(digits, 0, null, ret, map);
        }else{
        	ret.add("");
        }
        return ret;
    }
	/**
	 * using recursion method
	 * @param digits 
	 * @param index 
	 * @param cur 
	 * @param ret
	 * @param map
	 * @author Administrator
	 * @date 2014-6-3
	 */
	private void sub4letters(String digits, int index, String cur, List<String> ret, HashMap<Integer, String> map){
		if(index >= digits.length())
			return;
		int tar = digits.charAt(index)-'0';
		String tars = map.get(tar);
		
		for(int i=0;i<tars.length();i++){
			char t = tars.charAt(i);
			StringBuilder sb = new StringBuilder();
			if(cur!=null && cur.length()>0)
				sb.append(cur);
			sb.append(t);
			if(index == digits.length()-1)
				ret.add(sb.toString());
			sub4letters(digits, index+1,sb.toString(), ret, map);
		}
	}
	/**
	 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
	 * merge 2 sorted list. If we merge every adjacent 2 lists per iteration, we needs log2(k) iterations,
	 *  where merge 2 sorted list costs O(n) per iteration. Thus the run time is O(log(k)n).
	 * @param lists
	 * @return
	 * @author Administrator
	 * @date 2014-6-9
	 */
	public ListNode mergeKLists(List<ListNode> lists) {
        if(lists.isEmpty() || lists.size()<=0)
        	return null;
        if(lists.size() == 1)
        	return lists.get(0);
        
        int iter = (int)(Math.log(lists.size())/Math.log(2));
        iter = iter < Math.log(lists.size())/Math.log(2) ? iter+1:iter;
        
        for(int i=0;i<iter;i++){
        	for(int j = 0; j<lists.size(); j += Math.pow(2, i+1)){
        		 int offset = j+(int)Math.pow(2,i);
        		 ListNode temp = merger2Lists(lists.get(j), offset>=lists.size()? null:lists.get(offset));
        		 lists.set(j, temp);
        	}
        }
        return lists.get(0);
    }
	
	public ListNode merger2Lists(ListNode a, ListNode b){
		if(b == null )
			return a;
		if(a == null )
			return b;
		ListNode head = null;
		ListNode point = null;
		ListNode t1 = a;
		ListNode t2 = b;
		while(t1!=null && t2!=null){
			if(head == null)
			{
				head = t1.val<=t2.val? t1: t2;
				//point = head;
			}
			while(t1!=null && t2!=null && t1.val <= t2.val){
				if(point != null)
				{
					point.next = t1;
					point = point.next;
				}else 
					point = t1;
				
				t1 = t1.next;
			}
			while(t2!=null && t1!=null && t2.val < t1.val){
				if(point != null)
				{
					point.next = t2;
					point = point.next;
				}else
					point = t2;
				
				t2 = t2.next;
			}
		}
		
		if(t1!=null){
			point.next = t1;
		}else if(t2!=null){
			point.next = t2;
		}
		return head;
	}
	/**
	 * Given a linked list, swap every two adjacent nodes and return its head.
	 * @param head
	 * @return
	 * @author Administrator
	 * @date 2014-6-9
	 */
	 public ListNode swapPairs(ListNode head) {
	       if(head == null || head.next == null)
	    	   return head;
	       ListNode t1 = head;
	       ListNode t2 = null;
	       ListNode newHead = t1.next;
	       while(t1!=null && t1.next!=null){
	    	   t2 = t1.next;
	    	   t1.next = t1.next.next;
	    	   t2.next = t1;
	    	   t1 = t1.next;
	       }
	       return newHead;
	 }
	 /**
	  * Solution : reverse a list with K nodes(a group), I achieve it by log2(K) (ceil). 
	  * in every iteration, I reverse two subGroup, after all iterations, the K nodes are totally reversed.
	  * Example :
	  * 1->2->3->4
	  * iter1 : 2->1->4->3
	  * iter2 : 4->3->2->1
	  * @param head
	  * @param k
	  * @return
	  * @author Administrator
	  * @date 2014-6-11
	  */
	  public ListNode reverseKGroup(ListNode head, int k) {
          if(head == null || head.next == null || k == 1)
		        	return head;
		        ListNode newHead = null;
		        ListNode ts = head; // the beginning node of each group 
		        ListNode te = ts; // the end node of each group
		        int iter = (int)(Math.log(k)/Math.log(2)); 
	        	iter = iter < Math.log(k)/Math.log(2)? iter + 1 : iter;
	        	
	        	ListNode eLast = null; //the last node of the last group
		        while(te!=null && te.next!=null){
		        	int i=1;
		        	//determine the start and end of the some group
		        	while(i++<k){
		        		te = te.next;
		        		if(te == null)
		        			return newHead==null? head : newHead;
		        	}
		        	
		        	//te is the first node of the next group
		        	te = te.next;
		        	
		        	for(int j=0;j<iter;j++){
		        		int size = (int)Math.pow(2, j+1);
		        		ListNode last = null;//the last node
		        		for(int m=0;m<(k%size==0?k/size:k/size+1);m++){
		        			ListNode s1 = null; //the first node of the first subgroup
		        			ListNode e1 = null; //the end node of the first subgroup
		        			ListNode s2 = null; //the first node of the second subgroup
		        			ListNode e2 = null; //the end node of the second subgroup
		        			if(last == null)
		        				s1 = ts;
		        			else
		        				s1 = last.next;
		        			e1 = s1;
		        			int n=1;
		        			while(e1.next!=te && n++<size/2)
		        				e1 = e1.next;
		        			if(e1.next == te)
		        				break;
		        			s2 = e1.next;
		        			e2 = s2;
		        			n=1;
		        			while(e2.next!= te && n++<size/2)
		        				e2 = e2.next;
		        			
		        			if(last!=null)
		        				last.next = s2;
		        			else
		        				ts = s2;
		        			last = e1;
		        			//swap the two subgroups
		        			ListNode temp = e2.next;
		        			e2.next = s1;
		        			e1.next = temp;
		        			
		        		}
		        		if(j==iter-1 && newHead == null)
		        			newHead = ts;
		        		else  if(j==iter-1 && eLast!=null)
		        			eLast.next = ts;
		        		if(j==iter-1)
		        		    eLast = last;
		        	}
		        	if(eLast != null)
		        		eLast.next = te;
		        	ts = te;
		        }
		        return newHead;
    }
	public int removeDuplicates(int[] A) {
		if(A == null || A.length<=1)
			return A!=null?A.length:0;
		int i=0;
		for(int j=1;j<A.length;j++){
			if(A[j] == A[i])
				continue;
			else{
				i++;
				if(i!=j)
					A[i] = A[j];
			}
		}
		return i+1;
	}
	public int removeElement(int[] A, int elem) {
        if(A == null || A.length == 0)
        	return 0;
        int i = A.length;//from the end to the beginning
        int j = 0;
        for(j=0;j<i;j++){
        	if(A[j] == elem){
        		i--;
        		//find the one that is not the instance of elem
        		while(i>=0 && A[i]==elem)
        			i--;
        		if(j<i)
        		{
        			A[j] = A[i];
        			A[i] = elem;
        		}
        	}
        }
        return j<i+1? j:i+1;
    }
	
	public String strStr(String haystack, String needle) {
        
        if(haystack == null || haystack.length()<needle.length() || needle == null )
        	return null;
        if(needle.length() == 0 )
        	return haystack;
        if(haystack.length() == needle.length())
        {	for(int i=needle.length()-1;i>=0;i--)
        		if(haystack.charAt(i) != needle.charAt(i))
        			return null;
        	return haystack;
        }
        char[] ch = haystack.toCharArray();
        char[] cn = needle.toCharArray();
        boolean[][] memo = new boolean[cn.length][ch.length];
        
        for(int i=0;i<ch.length;i++)
        {
        	memo[0][i]= (cn[0] == ch[i] ? true : false);
        	if(cn.length == 1 && memo[0][i])
        		return haystack.substring(i-cn.length+1);
        }
        for(int i=1;i<cn.length;i++)
        	memo[i][0] = false;
        
        for(int i=1;i<cn.length;i++){
        	for(int j=1;j<ch.length;j++){
        		memo[i][j] = cn[i] == ch[j] && memo[i-1][j-1] ? true : false;
        		if(i==cn.length-1 && memo[i][j] == true)
        			return haystack.substring(j-cn.length+1);
        	}
        }
       	
        return null;
    }
	/**
	 * directly by plus, will get TLE
	 * @param dividend
	 * @param divisor
	 * @return
	 * @author Administrator
	 * @date 2014-6-11
	 */
	public int divide(int dividend, int divisor) {
		long[] rets = new long[32]; // keep all the temps
		int ret = 0;
		long temp = divisor>0 ? divisor : Math.abs(divisor);
		long absDividend = Math.abs((long)dividend);
		long absDivisor = Math.abs((long)divisor);
		boolean flag = true;
		if( dividend<0 && divisor>0  || divisor<0 &&dividend>0)
			flag = false;
		//special case 1
		if(divisor == 0)
			return -1;
		//special case 2
		if(temp == 1)
			return flag == false ? 0-Math.abs(dividend):Math.abs(dividend);
		//special case 3
		if(dividend == 0)
			return 0;
		//special case 4
		if(absDividend == absDivisor)
			return flag == true ? 1 : -1;
		int i = 0;
		ret = 0;
		rets[0] = 0;
		while(temp > 0 && temp<=absDividend){
			ret = (ret == 0 ? 1 : ret+ret);
			rets[++i] = temp;
			temp += temp;
		}
		temp = rets[i]; //recover it
		for(int j=i-1;j>=1;j--)
		{
			if(temp+rets[j]<=absDividend)
			{
				ret+= (int)Math.pow(2, j-1);
				temp += rets[j];
			}
		}
		return flag==false ? 0-ret:ret;
    }
	/**
	 * Substring with Concatenation of All Words 
	 * @param S
	 * @param L
	 * @return
	 */
	 public List<Integer> findSubstring(String S, String[] L) {
         List<Integer> ret = new ArrayList<Integer>();
	     if(L == null || L[0].length()<=0)
	    	 return ret;
	     int w = L[0].length();
	     int k = L.length;
	     int m = S.length();
	     //store the counts of each word in L
	     HashMap<String, Integer> cn = new HashMap<String, Integer>(k);
	     //store the number of each word met in S
	     HashMap<String, Integer> cnt = new HashMap<String, Integer>(k);
	     int count = 0;
	     int begin = 0;
	     //caculate the counts of each word in L
	     for(int i=0;i<k;i++){
	    	 if(cn.containsKey(L[i]))
	    		 cn.put(L[i], cn.get(L[i])+1);
	    	 else
	    		 cn.put(L[i], 1);
	     }
	     
	     for(int i=0;i<w;i++){
	    	 begin = i;
	    	 count = 0;
	    	 cnt.clear();
	    	 for(int j=i;j<m;j=j+w){
	    		 String tar = S.substring(j,j+w<m? j+w:m);
	    		 //if reach to a word that's not in the L, reset the begin , count and cnt;
	    		 if(!cn.containsKey(tar)){
	    			 begin = j+w;
	    			 count = 0;
	    			 cnt.clear();
	    			 continue;
	    		 }
	    		 if(!cnt.containsKey(tar) || cnt.get(tar) == 0){
	    			 cnt.put(tar, 1);
	    			 count++;
	    		 }else if(cnt.get(tar)<cn.get(tar)){
	    			 cnt.put(tar, cnt.get(tar)+1);
	    			 count++;
	    		 }else{
	    			 if(count < k){//reset all the variables
	    				 while(true){
	    					 String rm = S.substring(begin,begin+w);
	    					 begin = begin + w;
	    					 if(rm.equals(tar))
	    						 break;
	    					 else
	    						 cnt.put(rm, cnt.get(rm)-1);
	    				 }
	    			 }else{
	    				 ret.add(begin);
	    				 count --;
	    				 String rm = S.substring(begin,begin+w);
	    				 cnt.put(rm, cnt.get(rm)-1);
	    				 begin=begin+w;
	    				 continue;
	    			 }
	    		 }
	    		 if(count == k){
	    			 ret.add(begin);
    				 count --;
    				 String rm = S.substring(begin,begin+w);
    				 cnt.put(rm, cnt.get(rm)-1);
    				 begin=begin+w;
	    		 }
	    	 }
	     }
	     return ret;
	}
	 /**
	  * Implement next permutation,
	  *  which rearranges numbers into the lexicographically next greater permutation of numbers.
	  * @param num
	  * @author Administrator
	  * @date 2014-6-13
	  */
	 public void nextPermutation(int[] num) {
	        if(num == null || num.length <=1)
	        	return ;
	        int i = num.length-2;
	        int last = num.length -1;
	        while(i>=0){
	        	if(num[i]>=num[last]){//keep looking, and put the current num to the end of the array
	        		int tmp = num[i];
	        		int ind = i+1;
	        		while(ind<=last)
	        		{
	        			num[ind-1] = num[ind];
	        			ind++;
	        		}
	        		num[last] = tmp;
	        	}else{
	        		int ind = i+1;
	        		while(num[i]>=num[ind]) // the equal is very important!!
	        			ind++;
	        		int tmp = num[i];
	        		num[i] = num[ind];
	        		num[ind] = tmp;
	        		break;
	        	}
	        	i--;
	        }
	  }
	public static void main(String[] argv){
		Solutions s = new Solutions();
		String[] L = {"a","b","a"};
		//System.out.println(s.findSubstring("abababab", L));
		//System.out.println(s.divide(-2147483648, -2147483648));
		int [] A ={1,5,1};
		s.nextPermutation(A);
		System.out.println(A);
		//System.out.printlnA);
		//String ss="qqq";
		/*List<ListNode> list = new ArrayList<ListNode>();
		ListNode a = new ListNode(1);
		a.next = new ListNode(2);
		a.next.next = new ListNode(3);
		a.next.next.next = new ListNode(4);*/
		
		//s.mergeKLists(list);
		//s.swapPairs(a);
		//System.out.println(s.letterCombinations("23"));
	}
}
