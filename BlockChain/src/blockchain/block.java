package blockchain;
import utility.merkle_tree;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import utility.StringUtil;
//think of a place to read transaction
public class block {
	private String hash;
	private String previoushash;
	private merkle_tree.node merkle_root;
	private List<transaction> transactions;
	private long nonce;
	private long timeStamp;
	
	public merkle_tree.node getMerkle_root() {
		return merkle_root;
	}
	public void setMerkle_root() {
		this.merkle_root = merkle_tree.create_tree(this);
	}
	public block(String previoushash,List<transaction> transactions) {
		this.transactions=transactions;
		this.previoushash=previoushash;
		setMerkle_root();
		this.timeStamp=System.currentTimeMillis();
		this.hash=calculateHash();
	}
	//function to calculate hash using a particular nonce value
	public String calculateHash() {
		String hash=StringUtil.applySha256(previoushash+merkle_root.hash+nonce+timeStamp);
		return hash;
	}
	public String mineBlock(int difficulty) {
		String target=(new char[difficulty]).toString().replace('\0', '0');
		String hashvalue=calculateHash();
		while(target!=hashvalue.substring(0, difficulty+1)) {
			nonce++;
			hashvalue=calculateHash();
		}
		System.out.println("block mined!!!!");
		return hashvalue;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getPrevioushash() {
		return previoushash;
	}
	public void setPrevioushash(String previoushash) {
		this.previoushash = previoushash;
	}
	public List<transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<transaction> transactions) {
		this.transactions = transactions;
	}
	public long getNonce() {
		return nonce;
	}
	public void setNonce(long nonce) {
		this.nonce = nonce;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
