package utility;
import java.util.ArrayList;

import blockchain.block;
import blockchain.transaction;
// this class still has to be completed
public class merkle_tree {
	node root;
	
	public static class node{
		node left;
	    node right;
		public String hash;
	}
	public static node create_tree(block b) {
		ArrayList<node> currentlist=new ArrayList<node>();
		ArrayList<node> nextlist=new ArrayList<node>();
		int l=b.getTransactions().size();
		for(int i=0;i<l;i++) {
			node n=new node();
			n.hash=b.getTransactions().get(i).getTransactionId();
			currentlist.add(n);
		}
		
		while(currentlist.size()!=1) {
			int length=currentlist.size();
			
			for(int i=0;i<length;i=i+2) {
				node n=new node();
				n.left=currentlist.get(i);
				n.right=currentlist.get(i+1);
				n.hash=StringUtil.applySha256(n.left.hash+n.right.hash);
				nextlist.add(n);
				
			}
			if(length%2==1) {
				node n=new node();
				n.left=currentlist.get(length-1);
				n.right=currentlist.get(length-1);
				nextlist.add(n);
			}
			currentlist=nextlist;
			nextlist=new ArrayList<node>();
		}
		return currentlist.get(0);
	}
	
	public static boolean verifyTransaction(block b, String transactionId) {
		node n=b.getMerkle_root();
		ArrayList<node> list=new ArrayList<node>();
		//function to find and add nodes to list
		createPath(list,n,transactionId);
		int i=0;
		int l=list.size();
		String r="";
		while(i<l) {
			r=StringUtil.applySha256(list.get(i).hash+list.get(i+1).hash);
		}
		if(r==b.getMerkle_root().hash) {
			return true;
		}
		return false;
	}
	public static node createPath(ArrayList<node> list,node n,String id) {
		if(n==null) {
			return null;
		}
		else if(n.hash==id) {
			list.add(n);
			return n;
		}
		node p=null;
		node k=createPath(list,n.left,id);
		if(k!=null) {
			list.add(n.right);
			p=n;
		}
		node r=createPath(list,n.right,id);
		if(r!=null) {
			list.add(n.left);
			p=n;
		}
		return p;
	}
}
