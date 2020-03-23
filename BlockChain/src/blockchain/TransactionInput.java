package blockchain;

public class TransactionInput {
	private String transactionOutputId;
	private TransactionOutput UTXO;
	
	public TransactionInput(String transactionId) {
		this.transactionOutputId=transactionId;
	}

	public String getTransactionOutputId() {
		return transactionOutputId;
	}

	public void setTransactionOutputId(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}

	public TransactionOutput getUTXO() {
		return UTXO;
	}

	public void setUTXO(TransactionOutput uTXO) {
		UTXO = uTXO;
	}
	
}
