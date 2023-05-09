package binarytree;

import java.util.List;

public class Splay {
    private static class Node {
        int data;
        Node left, right, parent;

        public Node(int data) {
            this.data = data;
            this.left = this.right = this.parent = null;
        }
    }

    private Node root;

    public Splay() {
        this.root = null;
    }

    public void insert(List<Integer> data) {
        for (int d : data) {
            insertNode(d);
        }
    }

    private void insertNode(int data) {
        Node insertNode = new Node(data);
        Node prev = null;
        Node actualNode = root;

        while (actualNode != null) {
            prev = actualNode;

            if (insertNode.data < actualNode.data) {
                actualNode = actualNode.left;
            } else {
                actualNode = actualNode.right;
            }
        }

        insertNode.parent = prev;

        if (prev == null) {
            setRoot(insertNode);
        } else if (insertNode.data < prev.data) {
            prev.left = insertNode;
        } else {
            prev.right = insertNode;
        }

        splay(insertNode);
    }

    private void splay(Node node) {
        while (node.parent != null) {
            if (node.parent.parent == null) {
                if (node == node.parent.left) {
                    // right rotation
                    doZigRotation(node.parent);
                } else {
                    // left rotation
                    doZagRotation(node.parent);
                }
            } else if (node == node.parent.left && node.parent == node.parent.parent.left) {
                // right-right rotation
                doZigRotation(node.parent.parent);
                doZigRotation(node.parent);
            } else if (node == node.parent.right && node.parent == node.parent.parent.right) {
                // left-left rotation
                doZagRotation(node.parent.parent);
                doZagRotation(node.parent);
            } else if (node == node.parent.right && node.parent == node.parent.parent.left) {
                // left-right rotation
                doZagRotation(node.parent);
                doZigRotation(node.parent);
            } else {
                // right-left rotation
                doZigRotation(node.parent);
                doZagRotation(node.parent);
            }
        }
    }

    // Right rotation
    private void doZigRotation(Node actualNode) {
        Node help = actualNode.left;
        actualNode.left = help.right;
        if (help.right != null) {
            help.right.parent = actualNode;
        }

        if (actualNode.parent == null) {
            setRoot(help);
        } else if (actualNode == actualNode.parent.right) {
            actualNode.parent.right = help;
            help.parent = actualNode.parent;
        } else {
            actualNode.parent.left = help;
            help.parent = actualNode.parent;
        }

        help.right = actualNode;
        actualNode.parent = help;
    }

    // Left rotation
    private void doZagRotation(Node actualNode) {
        Node help = actualNode.right;
        actualNode.right = help.left;
        if (help.left != null) {
            help.left.parent = actualNode;
        }

        if (actualNode.parent == null) {
            setRoot(help);
        } else if (actualNode == actualNode.parent.left) {
            actualNode.parent.left = help;
            help.parent = actualNode.parent;
        } else {
            actualNode.parent.right = help;
            help.parent = actualNode.parent;
        }

        help.left = actualNode;
        actualNode.parent = help;
    }

    public void search(List<Integer> data) {
        for (int d : data) {
            searchNode(d);
        }
    }

    private boolean searchNode(int data) {
        Node actualNode = root;

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

        splay(actualNode);
        return true;
    }

    public void delete(List<Integer> data) {
        for (int d : data) {
            deleteNode(d);
        }
    }

    private boolean deleteNode(int data) {
        Node actualNode = root;

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

        Node deleteNode = actualNode;
        Node leftSubtree = null;
        Node rightSubtree = null;

        splay(deleteNode);

        if (deleteNode.right != null) {
            rightSubtree = deleteNode.right;
            rightSubtree.parent = null;
        }
        if (deleteNode.left != null) {
            leftSubtree = deleteNode.left;
            leftSubtree.parent = null;
        }

        setRoot(join(leftSubtree, rightSubtree));

        return true;
    }

    private Node join(Node leftSubtree, Node rightSubtree) {
        if (leftSubtree == null) {
            return rightSubtree;
        }

        if (rightSubtree == null) {
            return leftSubtree;
        }

        Node maxFromLeftSubtree = maximum(leftSubtree);
        splay(maxFromLeftSubtree);
        maxFromLeftSubtree.right = rightSubtree;
        rightSubtree.parent = maxFromLeftSubtree;

        return maxFromLeftSubtree;
    }

    public Node maximum(Node actualNode) {
        while (actualNode.right != null) {
            actualNode = actualNode.right;
        }
        return actualNode;
    }

    public void insertValue(int value) {
        insertNode(value);
    }

    public boolean searchValue(int value) {
        return searchNode(value);
    }

    public boolean deleteValue(int value) {
        return deleteNode(value);
    }

    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.data);
            traverseInOrder(node.right);
        }
    }

    public void setRoot(Node node) {
        if (node != null) node.parent = null;
        this.root = node;
    }
}
