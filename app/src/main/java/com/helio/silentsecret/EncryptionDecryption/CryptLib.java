package com.helio.silentsecret.EncryptionDecryption;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*****************************************************************
 * CrossPlatform CryptLib
 *
 * <p>
 * This cross platform CryptLib uses AES 256 for encryption. This library can
 * be used for encryptoion and de-cryption of string on iOS, Android and Windows
 * platform.<br/>
 * Features: <br/>
 * 1. 256 bit AES encryption
 * 2. Random IV generation.
 * 3. Provision for SHA256 hashing of key.
 * </p>
 *
 * @since 1.0
 * @author navneet
 *****************************************************************/

public class CryptLib {

    private static final String IV_KEY = "mindcrewnewapp17";
    private static final String SECRET_KEY = "newapp17mindcrew";

/**
 * Encryption mode enumeration
 */
private enum EncryptMode {
    ENCRYPT, DECRYPT;
}

// cipher to be used for encryption and decryption
 static Cipher _cx = null;

// encryption key and initialization vector
static byte[] _key = null, _iv = null;

public CryptLib() throws NoSuchAlgorithmException, NoSuchPaddingException {
    // initialize the cipher with transformation AES/CBC/PKCS5Padding
    _cx = Cipher.getInstance("AES/CBC/PKCS5Padding");
    _key = new byte[32]; //256 bit key space
    _iv = new byte[16]; //128 bit IV
}

/**
 * Note: This function is no longer used.
 * This function generates md5 hash of the input string
 * @param inputString
 * @return md5 hash of the input string
 */
public static final String md5(final String inputString) {
    final String MD5 = "MD5";
    try {
        // Create MD5 Hash
        MessageDigest digest = MessageDigest
                .getInstance(MD5);
        digest.update(inputString.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        return hexString.toString();

    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }
    return "";
}

/**
 *
 * @param _inputText
 *            Text to be encrypted or decrypted
 * @param _encryptionKey
 *            Encryption key to used for encryption / decryption
 * @param _mode
 *            specify the mode encryption / decryption
 * @param _initVector
 * 	      Initialization vector
 * @return encrypted or decrypted string based on the mode
 * @throws UnsupportedEncodingException
 * @throws InvalidKeyException
 * @throws InvalidAlgorithmParameterException
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 */
     private static String encryptDecrypt(String _inputText, String _encryptionKey,
										  EncryptMode _mode, String _initVector) throws UnsupportedEncodingException,
			 InvalidKeyException, InvalidAlgorithmParameterException,
			 IllegalBlockSizeException, BadPaddingException {
    String _out = "";// output string
    //_encryptionKey = md5(_encryptionKey);
    //System.out.println("key="+_encryptionKey);

    int len = _encryptionKey.getBytes("UTF-8").length; // length of the key	provided

    if (_encryptionKey.getBytes("UTF-8").length > _key.length)
        len = _key.length;

    int ivlen = _initVector.getBytes("UTF-8").length;

    if(_initVector.getBytes("UTF-8").length > _iv.length)
        ivlen = _iv.length;

    System.arraycopy(_encryptionKey.getBytes("UTF-8"), 0, _key, 0, len);
    System.arraycopy(_initVector.getBytes("UTF-8"), 0, _iv, 0, ivlen);
    //KeyGenerator _keyGen = KeyGenerator.getInstance("AES");
    //_keyGen.init(128);

    SecretKeySpec keySpec = new SecretKeySpec(_key, "AES"); // Create a new SecretKeySpec
                                // for the
                                // specified key
                                // data and
                                // algorithm
                                // name.

    IvParameterSpec ivSpec = new IvParameterSpec(_iv); // Create a new
                            // IvParameterSpec
                            // instance with the
                            // bytes from the
                            // specified buffer
                            // iv used as
                            // initialization
                            // vector.

    // encryption
    if (_mode.equals(EncryptMode.ENCRYPT)) {
        // Potentially insecure random numbers on Android 4.3 and older.
        // Read
        // https://android-developers.blogspot.com/2013/08/some-securerandom-thoughts.html
        // for more info.
        _cx.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);// Initialize this cipher instance
        byte[] results = _cx.doFinal(_inputText.getBytes("UTF-8")); // Finish
                                    // multi-part
                                    // transformation
                                    // (encryption)
        _out = Base64.encodeToString(results, Base64.DEFAULT); // ciphertext
        _out = _out.replace("\n", "");
                                    // output
    }

    // decryption
    if (_mode.equals(EncryptMode.DECRYPT)) {
        _cx.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);// Initialize this ipher instance

        byte[] decodedValue = Base64.decode(_inputText.getBytes(),
                Base64.DEFAULT);
        byte[] decryptedVal = _cx.doFinal(decodedValue); // Finish
                                // multi-part
                                // transformation
                                // (decryption)
        _out = new String(decryptedVal);
    }
    System.out.println(_out);
    return _out; // return encrypted/decrypted string
}

/***
 * This function computes the SHA256 hash of input string
 * @param text input text whose SHA256 hash has to be computed
 * @param length length of the text to be returned
 * @return returns SHA256 hash of input text
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 */
public static String SHA256 (String text, int length) throws NoSuchAlgorithmException, UnsupportedEncodingException {

    String resultStr;
    MessageDigest md = MessageDigest.getInstance("SHA-256");

    md.update(text.getBytes("UTF-8"));
    byte[] digest = md.digest();

    StringBuffer result = new StringBuffer();
    for (byte b : digest) {
        result.append(String.format("%02x", b)); //convert to hex
    }
    //return result.toString();

    if(length > result.toString().length())
    {
        resultStr = result.toString();
    }
    else
    {
        resultStr = result.toString().substring(0, length);
    }

    return resultStr;

}


public static String encrypt(String _plainText)
        throws InvalidKeyException, UnsupportedEncodingException,
		InvalidAlgorithmParameterException, IllegalBlockSizeException,
		BadPaddingException {
        String security_key = "";


    try
    {
        if(_cx == null)
        _cx = Cipher.getInstance("AES/CBC/PKCS5Padding");

        if(_key == null)
        _key = new byte[32]; //256 bit key space

        if(_iv == null)
        _iv = new byte[16]; //128 bit IV

        security_key = CryptLib.SHA256(SECRET_KEY, 32);


    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    return encryptDecrypt(_plainText, security_key, EncryptMode.ENCRYPT, IV_KEY);
}


public static String decrypt(String _encryptedText)
        throws InvalidKeyException, UnsupportedEncodingException,
		InvalidAlgorithmParameterException, IllegalBlockSizeException,
		BadPaddingException {
    String security_key = "";
    try
    {
        if(_cx == null)
            _cx = Cipher.getInstance("AES/CBC/PKCS5Padding");

        if(_key == null)
            _key = new byte[32]; //256 bit key space

        if(_iv == null)
            _iv = new byte[16]; //128 bit IV

        security_key = CryptLib.SHA256(SECRET_KEY, 32);


    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

    return encryptDecrypt(_encryptedText, security_key, EncryptMode.DECRYPT, IV_KEY);
}

/**
 * this function generates random string for given length
 * @param length
 * 				Desired length
 * * @return
 */
public static String generateRandomIV(int length)
{
    SecureRandom ranGen = new SecureRandom();
    byte[] aesKey = new byte[16];
    ranGen.nextBytes(aesKey);
    StringBuffer result = new StringBuffer();
    for (byte b : aesKey) {
        result.append(String.format("%02x", b)); //convert to hex
    }
    if(length> result.toString().length())
    {
        return result.toString();
    }
    else
    {
        return result.toString().substring(0, length);
    }
}
}
