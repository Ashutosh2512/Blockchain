package utility;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class StringUtil {
	
	public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	public static byte[] applySig(PrivateKey privatekey, String data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature sig=Signature.getInstance("DSA");
		byte[] output=new byte[0];
		sig.initSign(privatekey);
		sig.update(data.getBytes());
		output=sig.sign();
		return output;
	}
	public static boolean verifySig(PublicKey publickey,byte[] signature,String data) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		Signature sign=Signature.getInstance("DSA");
		sign.initVerify(publickey);
		sign.update(data.getBytes());
		return sign.verify(signature);
	}

}
