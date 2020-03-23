package blockchain;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockChain {
	private static int difficulty;
	private static block latestBlock;
	private static HashMap<String,TransactionOutput> UTXO;
	
	public static int getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(int difficulty) {
		BlockChain.difficulty = difficulty;
	}

	public static block getLatestBlock() {
		return latestBlock;
	}

	public static void setLatestBlock(block latestBlock) {
		BlockChain.latestBlock = latestBlock;
	}

	public static HashMap<String, TransactionOutput> getUTXO() {
		return UTXO;
	}

	public static void setUTXO(HashMap<String, TransactionOutput> uTXO) {
		UTXO = uTXO;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
