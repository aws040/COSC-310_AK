package structure;

/**
 * Base code was taken from one of my previous projects. It was then modified to fit this project.
 * Binary tree that can hold T data
 * @param <T> data type to be used in the entire tree
 * @author Alejandro Borroni
 * @version 2026.10.17.11
 */
public class BinaryTree<T>{

    /**
     * Node for the binary tree, stores {@code <T>} data and a left child and right child
     * @param <T> data type to be used in the node
     */
    public static class BinTreeNode<T> {
        private T data;
        private BinTreeNode<T> left;
        private BinTreeNode<T> right;
        public BinTreeNode(T data) {
            this.data = data;
            left = right = null;
        }
        public BinTreeNode(T data, BinTreeNode<T> left, BinTreeNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
        public T data() {
            return data;
        }
        public void setData(T data) {
            this.data = data;
        }
        public BinTreeNode<T> left() {
            return left;
        }
        public void setLeft(BinTreeNode<T> left) {
            this.left = left;
        }
        public BinTreeNode<T> right() {
            return right;
        }
        public void setRight(BinTreeNode<T> right) {
            this.right = right;
        }
    }
    private final BinTreeNode<T> root;
    private int size;

    /**
     * Make a BinaryTree with T data (Useless)
     * @param data taken as T data type and make it the root
     */
    public BinaryTree(T data) {
        BinTreeNode<T> node = new BinTreeNode<>(data);
        size = 1;
        root = node;
    }

    /**
     * Create a BinaryTree with a max size given. Only accepts an integer and makes a
     * binary tree filled with only integers
     * @param maxSize is the maximum size of the tree to be made
     */
    @SuppressWarnings("unchecked")
    public BinaryTree(int maxSize) {
        root = (BinTreeNode<T>) buildIntTree(1, maxSize);
        size = maxSize;
    }

    public String toString() {
        String data = "";
        return printInorder(root, data);
    }

    private BinTreeNode<Integer> buildIntTree(int n, int max) {
        if (n > max) {
            return null;
        }else {
            return new BinTreeNode<>(n, buildIntTree(2 * n, max), buildIntTree(2 * n + 1, max));
        }
    }

    /**
     * Print the tree Inorder
     */
    public void printInorder() {
        String data = "";
        data = printInorder(root, data);
        System.out.println(data);
    }

    private String printInorder(BinTreeNode<T> root, String data) {
        if (root.left() != null) {
            data = printInorder(root.left(), data);
        }
        data = data + root.data() + " ";
        if (root.right() != null) {
            data = printInorder(root.right(), data);
        }
        return data;
    }

    /**
     * Print the tree in Preorder
     */
    public void printPreorder() {
        String data = "";
        data = printPreorder(root, data);
        System.out.println(data);
    }

    private String printPreorder(BinTreeNode<T> root, String data) {
        data = data + root.data() + " ";
        if (root.left() != null) {
            data = printPreorder(root.left(), data);
        }
        if (root.right() != null) {
            data = printPreorder(root.right(), data);
        }
        return data;
    }

    /**
     * Print the tree in Postorder
     */
    public void printPostorder() {
        String data = "";
        data = printPostorder(root, data);
        System.out.println(data);
    }

    private String printPostorder(BinTreeNode<T> root, String data) {
        if (root.left() != null) {
            data = printPostorder(root.left(), data);
        }
        if (root.right() != null) {
            data = printPostorder(root.right(), data);
        }
        return data + root.data() + " ";
    }

    /**
     * Print the tree sideways
     */
    public void printTree() {
        printTree(root, 1);
    }

    private void printTree(BinTreeNode<T> root, int space) {
        if (root == null) {
            return;
        }

        int spacing = 5;
        space += spacing;

        printTree(root.right(), space);
        for (int i = spacing; i < space; i++) {
            System.out.print(" ");
        }
        System.out.println(root.data());

        printTree(root.left(), space);
    }

    /**
     * returns the greatest level
     * @return greatest level
     */
    public int levels() {
        int total = size;
        int level = 0;
        while (total > 1) {
            total = total / 2;
            level++;
        }
        level++;
        return level;
    }

    /**
     * returns the count of elements in the tree
     * @return the count of elements
     */
    public int count() {
        return size;
    }

    /**
     * returns the amount of left nodes in the tree
     * @return amount of left nodes
     */
    public int countLeft() {
        return countLeft(root, 0);
    }

    private int countLeft(BinTreeNode<T> root, int count) {
        if (root == null) {
            return 0;
        }
        if (root.left() != null) {
            count++;
            count = countLeft(root.left(), count);
        }
        if (root.right() != null) {
            count = countLeft(root.right(), count);
        }
        return count;
    }

    /**
     * checks to see if the tree is full
     * @return if tree is full
     */
    public boolean isFull() {
        return isFull(root);
    }

    private boolean isFull(BinTreeNode<T> root) {
        if (root == null) {
            return false;
        }
        boolean isfull = true;
        if (root.left() == null && root.right() == null) {
            return true;
        }else if (root.left() == null || root.right() == null) {
            return false;
        } else {
            isfull = isFull(root.left());
            isfull = isfull && isFull(root.right());
        }
        return isfull;
    }

    /**
     * uses the toString method and comparables to compare both trees
     * @param tree taken as a binaryTree
     * @return true if both trees are the same
     */
    public boolean equals(BinaryTree<T> tree) {
        return this.toString().equals(tree.toString());
    }

    /**
     * Count the current leafs in the tree
     */
    public int countLeafs() {
        return countLeafs(root);
    }

    private int countLeafs(BinTreeNode<T> root) {
        if (root == null) {
            return 0;
        }
        int node = 0;
        if (root.left() == null && root.right() == null) {
            return 1;
        }
        if (root.left() != null) {
            node += countLeafs(root.left());
        }
        if (root.right() != null) {
            node += countLeafs(root.right());
        }
        return node;
    }

    /**
     * Class that returns the current root of the tree
     * @return current root of tree
     */
    public BinTreeNode<T> getRoot(){
        return root;
    }

    /**
     * Takes a current node and adds a child to the left of the node
     * @param parent taken as a BinTreeNode
     * @param data taken as the data to be added to the left of the node
     */
    public void addLeftChild(BinTreeNode<T> parent, T data) {
        BinTreeNode<T> node = new BinTreeNode<>(data);
        if (parent != null && parent.left() == null) {
            parent.setLeft(node);
            size++;
        } else if (parent != null) {
            BinTreeNode<T> temp = parent.left();
            parent.setLeft(node);
            node.setLeft(temp);
            size++;
        }
    }

    /**
     * Takes a current node and adds a child to the right of the node
     * @param parent taken as a BinTreeNode
     * @param data taken as the data to be added to the right of the node
     */
    public void addRightChild(BinTreeNode<T> parent, T data) {
        BinTreeNode<T> node = new BinTreeNode<>(data);
        if (parent != null && parent.right() == null) {
            parent.setRight(node);
            size++;
        } else if (parent != null) {
            BinTreeNode<T> temp = parent.right();
            parent.setRight(node);
            node.setLeft(temp);
            size++;
        }
    }
}

