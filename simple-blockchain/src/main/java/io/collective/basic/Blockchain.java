package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Implement a BlockChain
 */
public class Blockchain {
    private final ArrayList<Block> blocks;


    /**
     * Constructor to initialize the list of blocks
     */
    public Blockchain() {
        blocks = new ArrayList<>();
    }

    /**
     * Determine if the blockchain is empty
     * @return true if empty else false
     */
    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    /**
     * Add a block to the chain of blocks
     * @param block block to add to the chain
     */
    public void add(Block block) {
        blocks.add(block);
    }

    /**
     * Number of blocks in the blockchain
     * @return the size of the blockchain (in blocks)
     */
    public int size() {
        return blocks.size();
    }

    /**
     * Determine if the BlockChain is valid by examining each block in the chain
     * @return true if all blocks are valid else false.
     * @throws NoSuchAlgorithmException  when a particular cryptographic algorithm is requested but is not available in
     *      the environment.
     */
    public boolean isValid() throws NoSuchAlgorithmException {

        // check an empty chain
        if (blocks.isEmpty()) return true;

        // check a chain of one
        String previousHash = blocks.get(0).getPreviousHash();
        if (!previousHash.equals(Block.STARTING_PREVIOUS_HASH))
            return false;

        // check a chain of many

        for (Block block : blocks) {
            if (!isMined(block) || !block.getHash().equals(block.calculatedHash())
                    || !block.getPreviousHash().equals(previousHash))
                return false;
            previousHash = block.getHash();
        }

        return true;
    }

    /// Supporting functions

    /**
     * Mine the passed in block
     * @param block block to mine
     * @return return a new block that is mined.
     * @throws NoSuchAlgorithmException  when a particular cryptographic algorithm is requested but is not available in
     *      the environment.
     */
    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    /**
     * Determine if the block was mined
     * @param minedBlock the block to check if it's been mined.
     * @return true if it was mined false otherwise
     */
    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}