package trees;


public class BinarySearchTree<E extends Comparable<E>> {
    final private BSTNode<E> root;
    private int size;

    public BinarySearchTree() {
        root = new BSTNode<>(null, null);
        size = 0;
    }

    /**
     * Method to determine if the BST contains an element.
     * @param toFind  element to find in the tree
     * @return returns true if the BST contains the element, false otherwise.
     */
    public boolean contains(E toFind) {
        if(toFind == null) return false;
        BSTNode<E> curr = root;
        while (curr != null && curr.getData() != null) {
            int comp = toFind.compareTo(curr.getData());
            if (comp < 0) curr = curr.getLeftChild();
            else if (comp > 0) curr = curr.getRightChild();
            else return true;
        }
        return false;
    }

    /**
     * Method to insert a node (if not already present) into a BST
     * @param toAdd BSTNode to add to the BST tree.
     * @return true if successfully added (i.e. was not already present)
     */
    public boolean insert(E toAdd) {
        if(toAdd == null) return false;
        BSTNode<E> curr = root;
        if (curr.getData() == null) {
            curr.setData(toAdd);
            size++;
            return true;
        }

        int comp = toAdd.compareTo(curr.getData());
        while ((comp > 0 && curr.getRightChild() != null) || (comp < 0 && curr.getLeftChild() != null)) {
            if (comp < 0) curr = curr.getLeftChild();
            else curr = curr.getRightChild();

            comp = toAdd.compareTo(curr.getData());
        }
        BSTNode<E> nodeAdd = new BSTNode<>(toAdd, curr);
        if (comp < 0) curr.setLeftChild(nodeAdd);
        else if (comp > 0) curr.setRightChild(nodeAdd);
        else return false;  // Already in tree
        size++;
        return true;
    }

    /**
     * Helper method to determine if a node is a leaf node (i.e. no children).
     * @param node to examine if it is a leaf node
     * @return true if this node is a leaf node.
     */
    private boolean isLeaf(BSTNode<E> node) {
        if (node.getRightChild() == null && node.getLeftChild() == null)
            return true;
        return false;
    }

    /**
     * Helper method to determine if this node has 1 child under it.
     * @param node  node to examine the children of.
     * @return True if this node has one child under it.
     */
    private boolean hasOneChild(BSTNode<E> node) {
        int children = 0;
        if (node.getRightChild() == null)
            children++;
        if(node.getLeftChild() == null)
            children++;
        if(children == 1)
            return true;
        return false;
    }

    /**
     * Helper method for the remove element instance where the node to remove has 2 children.  In this case, traverse
     * the right child's path using the left path to find the smallest child under the right node of the node to remove.
     * @param curr
     * @return
     */
    private E findSmallest(BSTNode<E> curr) {
        while(curr.getLeftChild() != null) {
            curr = curr.getLeftChild();
        }
        return curr.getData();
    }

    /**
     * Helper method to determine the approach to remove a BST node.  The behavior varies if the node is a leaf,
     * has one children, or 2 children.
     * @param curr the node to remove
     * @param parent the parent of the node to remove
     */
    private void removeNode(BSTNode<E> curr, BSTNode<E> parent) {
        if(isLeaf(curr)) {  // Remove Leaf node is easy, just null out parent's reference
            BSTNode<E> left = parent.getLeftChild();
            if(left != null && left.equals(curr)) {
                parent.setLeftChild(null);
            } else {
                parent.setRightChild(null);
            }
        } else if( hasOneChild(curr)) {  // Repoint parent at the single child of current node
            BSTNode<E> child;
            if(curr.getLeftChild() != null)
                child = curr.getLeftChild();
            else child = curr.getRightChild();
            if(parent.getLeftChild().equals(curr)) {
                parent.setLeftChild(child);
            } else {
                parent.setRightChild(child);
            }
        } else {    // Most complicated case, the node to remove has 2 children
            E smallestValue = findSmallest(curr.getRightChild());
            curr.setData(smallestValue);
            remove(smallestValue);
        }
    }

    /**
     *
     * @param value Removes the specified element (value) from this set if it is present.
     * @return true if the element is found and removed.
     */
    public boolean remove(E value) {
        if(value == null) return false;

        BSTNode<E> curr = root;
        BSTNode<E> parent = curr;
        while (curr != null && curr.getData() != null) {
            int comp = value.compareTo(curr.getData());
            if (comp < 0) {
                parent = curr;
                curr = curr.getLeftChild();
            }
            else if (comp > 0) {
                parent = curr;
                curr = curr.getRightChild();
            }
            else {  // Found node
                removeNode(curr, parent);
                size--;
                return true;
            }
        }
        return false;

    }

    /**
     *
     * @return Returns the number of elements in this set (its cardinality).
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return true if this set contains no elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private StringBuilder toString(BSTNode<E> curr, StringBuilder sb) {
        if (curr == null) return sb;

        sb.append(curr.getData());

        // if leaf node, then return
        if (curr.getLeftChild() == null && curr.getRightChild() == null) return sb;

        // for left subtree
        sb.append('(');
        toString(curr.getLeftChild(), sb);
        sb.append(')');
        // only if right child is present to avoid extra parenthesis
        if (curr.getRightChild() != null) {
            sb.append('(');
            toString(curr.getRightChild(), sb);
            sb.append(')');
        }
        return sb;
    }

    /**
     * override the toString method of class.
     *
     * @return String representation of this node
     */
    @Override
    public String toString() {

        // bases case
        if (root == null || root.getData() == null) return "()";

        StringBuilder sb = new StringBuilder();
        return toString(root, sb).toString();
    }
}

class BSTNode<E extends Comparable<E>> {
    E data;
    BSTNode<E> parent;
    BSTNode<E> leftChild;
    BSTNode<E> rightChild;

    public BSTNode(E data, BSTNode<E> parent) {
        this.data = data;
        this.parent = parent;
    }


    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public BSTNode<E> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BSTNode<E> leftChild) {
        this.leftChild = leftChild;
    }

    public BSTNode<E> getRightChild() {
        return rightChild;
    }

    public void setRightChild(BSTNode<E> rightChild) {
        this.rightChild = rightChild;
    }


}
