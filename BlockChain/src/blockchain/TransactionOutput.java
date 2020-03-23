package blockchain;

import java.security.PublicKey;
import java.util.Comparator;

import utility.StringUtil;

public class TransactionOutput{
	
	private String id;
	private PublicKey reciepient;
	private float value;
	private String parentTransactionId;
	
	public TransactionOutput(String Id,PublicKey Reciepient,float Value) {
		setId(Id);
		setReciepient(Reciepient);
		setValue(Value);
		setId(StringUtil.getStringFromKey(reciepient)+Float.toString(Value)+Id);
	}
	// getters nad setters for private methods
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PublicKey getReciepient() {
		return reciepient;
	}
	public void setReciepient(PublicKey recipient) {
		this.reciepient = recipient;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getParentTransactionId() {
		return parentTransactionId;
	}
	public void setParentTransactionId(String parentTransactionId) {
		this.parentTransactionId = parentTransactionId;
	}	
}
class sortByvalue implements Comparator<TransactionOutput>{
	@Override
	public int compare(TransactionOutput t1, TransactionOutput t2) {
		if(t1.getValue()==t2.getValue()) {
			return 0;
		}
		else if(t1.getValue()<t2.getValue()) {
			return -1;
		}
		return 1;
	}
}