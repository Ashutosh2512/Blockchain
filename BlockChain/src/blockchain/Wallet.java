package blockchain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Wallet {
	private PublicKey publickey;   // public key of the wallet
	private PrivateKey privatekey;		//private key of the wallet
	
	/*This HashMap holds all the transaction Outputs that haven't yet been used as an input 
	 * in any other transaction
	 */
	HashMap<String,TransactionOutput> UTXOs=new HashMap<String,TransactionOutput>(); 
	//constructor
	public Wallet() {
		try {
			generateKeyPair();
		} catch(Exception e){
			System.out.println("Unable to genrate wallet.");
			e.printStackTrace();
		}
	}
	
	public void generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg=KeyPairGenerator.getInstance("DSA");
		SecureRandom sr=new SecureRandom();
		kpg.initialize(1024,sr);
		KeyPair kp=kpg.generateKeyPair();
		setPublickey(kp.getPublic());
		setPrivatekey(kp.getPrivate());
	}
	
	public transaction sendFunds(PublicKey reciepient,float value) {
		if(getBalance()<value) {
			System.out.println("The wallet doesn't have enough money.");
		}
		ArrayList<TransactionInput> inputs=new ArrayList<TransactionInput>();
		ArrayList<TransactionOutput> list=selectUTXOs(value);
		transaction newTransaction=new transaction(publickey,reciepient,value);
		int l=list.size();
		for(int i=0;i<l;i++) {
			TransactionOutput UTXO=list.get(i);
			inputs.add(new TransactionInput(UTXO.getId()));
			UTXOs.remove(UTXO.getId());
		}
		newTransaction.setInputs(inputs);
		return newTransaction;
	}
	
	private ArrayList<TransactionOutput> selectUTXOs(float target) {
		//this list will contain all the UTXOs with value smaller than the target
		ArrayList<TransactionOutput> listsmaller=new ArrayList<TransactionOutput>();
		//this list will contain all the UTXOs with value greater than the target
		ArrayList<TransactionOutput> listgreater=new ArrayList<TransactionOutput>();
		float total=0.0f;
		for(Map.Entry<String, TransactionOutput> e:UTXOs.entrySet()) {
			if(e.getValue().getValue()==target) {
				listsmaller.removeAll(listsmaller);
				listsmaller.add(e.getValue());
				return listsmaller;
			}
			else if(e.getValue().getValue()<target) {
				listsmaller.add(e.getValue());
				total+=e.getValue().getValue();
			}
			else {
				listgreater.add(e.getValue());
			}
			if(target==total) {
				return listsmaller;
			}
		}
		//if atleast one UTXO is greater than the given target
		if(listgreater.size()!=0) {
			if(total<target) {
				Collections.sort(listgreater,new sortByvalue());
			}
			ArrayList<TransactionOutput> list=new ArrayList<TransactionOutput>();
			list.add((listgreater.get(0)));
			return list;
		}
		//if none of the above methods work then we will randomly select the UTXOs for the next transaction
		int sum=0,random;
		Random generator=new Random();
		Object[] values=UTXOs.values().toArray();
		ArrayList<TransactionOutput> l=new ArrayList<TransactionOutput>();
		while(sum<target) {
			TransactionOutput t=(TransactionOutput)values[generator.nextInt(values.length)];
			if(!l.contains(t)) {
				l.add(t);
				sum+=t.getValue();
			}
		}
		return l;
	}
	
	public float getBalance() {
		float totalAmount=0.0f;
		for(Map.Entry<String,TransactionOutput> e:BlockChain.getUTXO().entrySet()) {
			if(e.getValue().getReciepient()==publickey) {
				UTXOs.put(e.getKey(), e.getValue());
			}
		}
		for(Map.Entry<String,TransactionOutput> e:UTXOs.entrySet()) {
			//this calls the getValue method of the TransactionOutput class
			totalAmount+=e.getValue().getValue();
		}
		return totalAmount;
	}

	public PublicKey getPublickey() {
		return publickey;
	}

	public void setPublickey(PublicKey publickey) {
		this.publickey = publickey;
	}

	public PrivateKey getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(PrivateKey privatekey) {
		this.privatekey = privatekey;
	}

	public HashMap<String, TransactionOutput> getUTXOs() {
		return UTXOs;
	}

	public void setUTXOs(HashMap<String, TransactionOutput> uTXOs) {
		UTXOs = uTXOs;
	}
}
