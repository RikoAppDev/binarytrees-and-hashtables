package binarytree;

import java.util.List;

public class AVL {
    private static class Node {
        int data;
        Node left;
        Node right;
        int height;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node root;

    public AVL() {
        this.root = null;
    }

    public void insert(List<Integer> data) {
        for (int d : data) {
            root = insertNode(root, new Node(d));
        }
    }

    private Node insertNode(Node actualNode, Node insertNode) {
        if (actualNode == null)
            return insertNode;

        if (insertNode.data < actualNode.data) {
            actualNode.left = insertNode(actualNode.left, insertNode);
        } else if (insertNode.data > actualNode.data){
            actualNode.right = insertNode(actualNode.right, insertNode);
        } else {
            return actualNode;
        }

        actualNode.height = 1 + Math.max(getAppropriateHeight(actualNode.left), getAppropriateHeight(actualNode.right));

        int balanceFactor = getBalanceFactor(actualNode);

        if (balanceFactor > 1) {
            if (insertNode.data < actualNode.left.data) {
                return rightRotate(actualNode);
            } else if (insertNode.data > actualNode.left.data) {
                actualNode.left = leftRotate(actualNode.left);
                return rightRotate(actualNode);
            }
        }
        if (balanceFactor < -1) {
            if (insertNode.data > actualNode.right.data) {
                return leftRotate(actualNode);
            } else if (insertNode.data < actualNode.right.data) {
                actualNode.right = rightRotate(actualNode.right);
                return leftRotate(actualNode);
            }
        }

        return actualNode;
    }

    private Node rightRotate(Node actualNode) {
        Node help1 = actualNode.left;
        Node help2 = help1.right;
        help1.right = actualNode;
        actualNode.left = help2;
        actualNode.height = Math.max(getAppropriateHeight(actualNode.left), getAppropriateHeight(actualNode.right)) + 1;
        help1.height = Math.max(getAppropriateHeight(help1.left), getAppropriateHeight(help1.right)) + 1;
        return help1;
    }

    private Node leftRotate(Node actualNode) {
        Node help1 = actualNode.right;
        Node help2 = help1.left;
        help1.left = actualNode;
        actualNode.right = help2;
        actualNode.height = Math.max(getAppropriateHeight(actualNode.left), getAppropriateHeight(actualNode.right)) + 1;
        help1.height = Math.max(getAppropriateHeight(help1.left), getAppropriateHeight(help1.right)) + 1;
        return help1;
    }

    public void search(List<Integer> data) {
        for (int d : data) {
            searchNode(d);
        }
    }

    private boolean searchNode(int data) {
        Node actualNode = getRoot();

        if (actualNode == null) {
            return false;
        }

        while (actualNode.data != data) {
            if (data < actualNode.data) {
                actualNode = actualNode.left;
            } else {
                actualNode = actualNode.right;
            }
            if (actualNode == null) {
                return false;
            }
        }
        return true;
    }

    public void delete(List<Integer> data) {
        for (int d : data) {
            root = deleteNode(getRoot(), d);
        }
    }

    private Node deleteNode(Node actualNode, int data) {
        if (actualNode == null)
            return null;

        if (actualNode.data > data)
            actualNode.left = deleteNode(actualNode.left, data);
        else if (actualNode.data < data)
            actualNode.right = deleteNode(actualNode.right, data);
        else {
            if ((actualNode.left == null) || (actualNode.right == null)) {
                Node help;
                if (actualNode.left == null)
                    help = actualNode.right;
                else
                    help = actualNode.left;

                actualNode = help;
            } else {
                Node help = minimum(actualNode.right);
                actualNode.data = help.data;
                actualNode.right = deleteNode(actualNode.right, help.data);
            }
        }

        if (actualNode == null)
            return null;

        actualNode.height = Math.max(getAppropriateHeight(actualNode.left), getAppropriateHeight(actualNode.right)) + 1;

        int balanceFactor = getBalanceFactor(actualNode);

        if (balanceFactor < -1 && getBalanceFactor(actualNode.right) <= 0)
            return leftRotate(actualNode);

        if (balanceFactor > 1 && getBalanceFactor(actualNode.left) >= 0)
            return rightRotate(actualNode);

        if (balanceFactor > 1 && getBalanceFactor(actualNode.left) < 0) {
            actualNode.left = leftRotate(actualNode.left);
            return rightRotate(actualNode);
        }

        if (balanceFactor < -1 && getBalanceFactor(actualNode.right) > 0) {
            actualNode.right = rightRotate(actualNode.right);
            return leftRotate(actualNode);
        }

        return actualNode;
    }

    private int getBalanceFactor(Node node) {
        if (node == null)
            return 0;

        return getAppropriateHeight(node.left) - getAppropriateHeight(node.right);
    }

    private int getAppropriateHeight(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    private Node minimum(Node actualNode) {
        while (actualNode.left != null) {
            actualNode = actualNode.left;
        }
        return actualNode;
    }

    public void insertValue(int value) {
        this.root = insertNode(getRoot(), new Node(value));
    }

    public boolean searchValue(int value) {
        return searchNode(value);
    }

    public boolean deleteValue(int value) {
        root = deleteNode(getRoot(), value);
        return false;
    }

    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.data);
            traverseInOrder(node.right);
        }
    }

    public Node getRoot() {
        return root;
    }
}
