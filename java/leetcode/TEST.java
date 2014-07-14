package leetcode;

import java.util.Arrays;
import java.util.Collections;

import util.MySort;

/**
 * youdao
 * 
 * @author huangting
 * @date 2014-6-25
 */
class Node implements Comparable<Node>{
	int value;
	int layer;
	public Node(int val, int lay){
		value = val;
		layer = lay;
	}
	public int compareTo(Node arg0) {
	    return  this.value > arg0.value? -1: this.value < arg0.value? 1 : 0;
    }
}
public class TEST {
	/**
	 * 打印螺旋矩阵
	 * @param n
	 * @author huangting
	 * @date 2014-6-25
	 */
	public static void printMatrix(int n){
		int [][] mtx = new int[n][n];
		int idx = 1;
		int i = 0 , j = 0;
		int lag = 1;
		while(lag <= n){
			if(lag == 1)
			{
				mtx[0][0] = idx;
				idx++;
			}else if(lag % 2 == 0){
				//->
				j ++;
				mtx[i][j] = idx;
				idx ++;
				// |
				int tmp = 1;
				while(tmp++<lag){
					i++;
					mtx[i][j] = idx;
					idx++;
				}
				//<-
				tmp = 1;
				while(tmp++<lag){
					j--;
					mtx[i][j] = idx;
					idx++;
				}
			}else{
				//|
				i++;
				mtx[i][j] = idx++;
				//->
				int tmp = 1;
				while(tmp++<lag){
					j++;
					mtx[i][j] = idx++;
				}
				// | ^
				tmp = 1;
				while(tmp++<lag){
					i--;
					mtx[i][j] = idx++;
				}
			}
			lag++;
		}
		for(i=0;i<n;i++){
			for(j=0;j<n;j++){
				System.out.print(mtx[i][j] + " ");
			}
			System.out.println();
		}
	}
	/**
	 * 计算哈弗曼树的高度
	 * @param A
	 * @return
	 * @author huangting
	 * @date 2014-6-25
	 */
	public static int hufmanHeight(int[] A){
		//先按降序排序
		MySort.quickSortDesc(A, 0, A.length);
		Node[] an = new Node[A.length];
		for(int i=0;i<A.length;i++){
			an[i] = new Node(A[i], 1);
		}
		int point = an.length - 1;//地标指针
		int ret = 0; // return value
		if(an.length == 1)
			return 1;
		while(point>0){
			int min1 = an[point].value;
			int min2 = an[point-1].value;
			Node sum = new Node(min1 + min2,Math.max(an[point].layer, an[point-1].layer)+1);
			//update ret
			if(Math.max(an[point].layer, an[point-1].layer)+1 > ret)
				ret = Math.max(an[point].layer, an[point-1].layer)+1;
			int idx = point - 2;
			//采用插入排序的方法，将sum插入到原排好序的array中
			while(idx>=0){
				if(an[idx].value<sum.value){
					an[idx+1] = an[idx];
					idx--;
				}else
					break;
			}
			an[idx+1] = sum;
			point--;
		}
		return ret;
	}
	/**
	 * 采用同余定理
	 * (a+b)%c = (a%c+b%c)%c
	 * (a*b)%c = (a%c*b%c)%c
	 * @param str
	 * @return
	 * @author huangting
	 * @date 2014-6-25
	 */
	public static int bigNumMod(String str){
		if(str.length() <= 0)
			return 0;
		int len = str.length();
		int i = 0;
		int ret = 0;
		while(i<len){
			int num = str.charAt(i)-'0';
			ret = ret * 10%359;
			ret = ret%359 + num%359;
			ret = ret%359;
			i++;
		}
		return ret;
	}
	/**
	 * 用dp
	 * int[][][] p 
	 * 乘积最大
	 * @param num
	 * @param k
	 * @return
	 * @author huangting
	 * @date 2014-6-25
	 */
	public static int maxMulti(String num, int k){
		int len = num.length();
		//无法被分割成比字符串还长的段
		if(k>=len)
			return -1;
		int[][][] p = new int[len][len][k+1];
		//初始化
		for(int i=0;i<len;i++){
			int tmp = num.charAt(i) - '0';
			p[i][i][0] = tmp;
		}
		//bottom to up
		for(int sz=2;sz<=len;sz++){
			for(int i = 0;i<=len-sz;i++){
				//被分割的段数
				for(int j=0;j<=k;j++){
					if(j == 0)
					{
						p[i][i+sz-1][0] =  p[i][i+sz-2][0]*10+p[i+sz-1][i+sz-1][0];
						continue;
					}
					if(j == k && sz !=len)
						continue;
					//to get p[i][i+sz-1][j]
					//初始化
					p[i][i+sz-1][j] = 0;
					//切割位置从i,i+sz-2
					for(int cut = i; cut<i+sz-1; cut++){
						int ls = i;
						int le = cut;
						int rs = cut+1;
						int re = i+sz-1;
						//左边被分割的K可以是0,1,2,...,j-1, 求各种情况下的max
						for(int left = 0; left<j; left++){
							int right = j-left-1; //右边被分割的k
							if(left>=le-ls+1 || right>=re-rs+1)
								continue;
							int temp = p[ls][le][left] * p[rs][re][right];
							if(temp > p[i][i+sz-1][j])
								p[i][i+sz-1][j] = temp;
						}
					}
				}
			}
		}
		//get p[0][len-1][k]
		return p[0][len-1][k];
	}
	public static void main(String[] args){
		//TEST.printMatrix(5);
		//int[] A= {2,2,1,8,7,6};
		//System.out.println(TEST.hufmanHeight(A));
		//System.out.println(TEST.bigNumMod("3590000000000000112"                                                               ));
		System.out.println(TEST.maxMulti("1231", 2));
	}
}
