package blockchain;

import java.security.PublicKey;
import java.util.ArrayList;

//WE HAVE TO WRITE A FUNCTION SO THAT WE CAN PROPAGATE OUR TRANSACTION TO DESTINATION NODE AND AFTER A SUCCESSFUL TRANSACTION WE CAN PROPAGATE TO ALL THE OTHER NODES THIS NODE AND DEST. NODE ARE CONNECTED TO.
public class transaction {
	private PublicKey from;
	private PublicKey to;
	private long time;        // time of origination of transaction
	private float amount;
	private String transactionId;
	private ArrayList<TransactionInput> inputs=new ArrayList<TransactionInput>();
	private ArrayList<TransactionOutput> outputs=new ArrayList<TransactionOutput>();
	
	public transaction(PublicKey from,PublicKey to,float amount) {
		setFrom(from);
		setTo(to);
		setAmount(amount);
		setTime(System.currentTimeMillis());
		setTransactionId();
	}
	@Override
	public String toString() {
		return from.hashCode()+to.hashCode()+time+""+amount;
	}
	
	public ArrayList<TransactionInput> getInputs() {
		return inputs;
	}
	public void setInputs(ArrayList<TransactionInput> inputs) {
		this.inputs = inputs;
	}
	public ArrayList<TransactionOutput> getOutputs() {
		return outputs;
	}
	public void setOutputs(ArrayList<TransactionOutput> outputs) {
		this.outputs = outputs;
	}
	
	public void setTransactionId() {
		this.transactionId = utility.StringUtil.applySha256(this.toString());
	}
	public String getTransactionId() {
		return this.transactionId;
	}
	public PublicKey getFrom() {
		return from;
	}
	public void setFrom(PublicKey from) {
		this.from = from;
	}
	public PublicKey getTo() {
		return to;
	}
	public void setTo(PublicKey to) {
		this.to = to;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	
}
