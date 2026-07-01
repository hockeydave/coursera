package io.collective.basic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A Block entity within a blockchain
 */
public class Block {
    private final String previousHash;
    private final long timestamp;
    private final int nonce;
    private final String hash;
    public static final String STARTING_PREVIOUS_HASH = "0";

    public Block(String previousHash, long timestamp, int nonce) throws NoSuchAlgorithmException {
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.hash = calculatedHash();
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public String getHash() {
        return hash;
    }

    public String calculatedHash() throws NoSuchAlgorithmException {
        return calculateHash(previousHash+timestamp+nonce);
    }

    /// Supporting functions

    /**
     * Calculate the SHA-256 hash of input string
     * @param string to SHA-256 hash
     * @return SHA-256 hashed value of string
     * @throws NoSuchAlgorithmException when a particular cryptographic algorithm is requested but is not available in
     * the environment.
     */
    static String calculateHash(String string) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(string.getBytes());
        return String.format("%064x", new BigInteger(1, digest.digest()));
    }
}