import java.util.ArrayList;
import java.util.Collections;

public class BinaryTree {
    private static class Node {
        private Integer value;
        private Node left;
        private Node right;
        private Node parent;

        public Node(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private Node root;
    private int deep = 0;

    public void innodeWalk(Node node) {
        if (node == null) {
            return;
        }
        deep++;
        innodeWalk(node.left);
        System.out.println(deep + ":" + node.value);
        innodeWalk(node.right);
        deep--;
    }

    public void drawWalk(Node node) {
        if (node.left != null) {
            DrawBinaryTree.getInstance().addEdge(node.value, node.left.value);
            drawWalk(node.left);
        } else {
//            DrawBinaryTree.getInstance().addNull(node.value);
        }
        if (node.right != null) {
            DrawBinaryTree.getInstance().addEdge(node.value, node.right.value);
            drawWalk(node.right);
        } else {
//            DrawBinaryTree.getInstance().addNull(node.value);
        }
    }

    public void draw() {
        DrawBinaryTree.release();
        drawWalk(root);
        DrawBinaryTree.release();
    }

    public Node search(Integer integer) {
        Node n = root;
        while (n != null && n.value.intValue() != integer) {
            if (integer < n.value) {
                n = n.left;
            } else {
                n = n.right;
            }
        }
        return n;
    }

    public void insert(Integer integer) {
        Node node = new Node(integer);
        Node x = root;
        Node y = null;
        while (x != null) {
            y = x;
            if (node.value < x.value) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.value < y.value) {
            y.left = node;
        } else {
            y.right = node;
        }
    }

    public void delete(Integer integer) {
        Node node = search(integer);
        if (node.right == null) {
            transplant(node, node.left);
        } else if (node.left == null) {
            transplant(node, node.right);
        } else {
            Node succeed;
            if ((succeed = min(node.right)) != node.right) {
                transplant(succeed, succeed.right);
                succeed.right = node.right;
                node.right.parent = succeed;
            }
            transplant(node, node.right);
            node.left.parent = succeed;
            succeed.left = node.left;
        }
    }

    public Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void transplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        Collections.shuffle(integers);
        BinaryTree binaryTree = new BinaryTree();
        System.out.println(integers);
        integers.forEach(binaryTree::insert);

        binaryTree.innodeWalk(binaryTree.root);
        binaryTree.draw();
//        System.out.println(binaryTree.min(binaryTree.search(89)));
    }
}
