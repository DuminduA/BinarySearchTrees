import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac SplayBST.java
 *  Execution:    java SplayBST
 *  Dependencies: none
 *
 *  Splay tree. Supports splay-insert, -search, and -delete.
 *  Splays on every operation, regardless of the presence of the associated
 *  key prior to that operation.
 *
 *  Written by Josh Israel.
 *
 *  Modified by Buddhi Ayesha, Yasas Senarath
 *
 ******************************************************************************/


public class SplayBST<Key extends Comparable<Key>, Value> {

    private Node root;   // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;            // key
        private Value value;        // associated data
        private Node left, right;   // left and right subtrees

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    // return value associated with the given key
    // if no such value, return null
    public Value get(Key key) {
        root = splay(root, key);
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root.value;
        else return null;
    }

    /***************************************************************************
     *  Splay tree insertion.
     ***************************************************************************/
    public void put(Key key, Value value) {
        // splay key to root
        if (root == null) {
            root = new Node(key, value);
            return;
        }

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        // Insert new node at root
        if (cmp < 0) {
            Node n = new Node(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }

        // Insert new node at root
        else if (cmp > 0) {
            Node n = new Node(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }

        // It was a duplicate key. Simply replace the value
        else {
            root.value = value;
        }

    }

    /***************************************************************************
     *  Splay tree deletion.
     ***************************************************************************/
    /* This splays the key, then does a slightly modified Hibbard deletion on
     * the root (if it is the node to be deleted; if it is not, the key was
     * not in the tree). The modification is that rather than swapping the
     * root (call it node A) with its successor, it's successor (call it Node B)
     * is moved to the root position by splaying for the deletion key in A's
     * right subtree. Finally, A's right child is made the new root's right
     * child.
     */
    public void remove(Key key) {
        if (root == null) return; // empty tree

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp == 0) {
            if (root.left == null) {
                root = root.right;
            } else {
                Node x = root.right;
                root = root.left;
                splay(root, key);
                root.right = x;
            }
        }

        // else: it wasn't in the tree to remove
    }


    /***************************************************************************
     * Splay tree function.
     * **********************************************************************/
    // splay key in the tree rooted at Node h. If a node with that key exists,
    //   it is splayed to the root of the tree. If it does not, the last node
    //   along the search path for the key is splayed to the root.
    private Node splay(Node h, Key key) {
        if (h == null) return null;

        int cmp1 = key.compareTo(h.key);

        if (cmp1 < 0) {
            // key not in tree, so we're done
            if (h.left == null) {
                return h;
            }
            int cmp2 = key.compareTo(h.left.key);
            if (cmp2 < 0) {
                h.left.left = splay(h.left.left, key);
                h = rotateRight(h);
            } else if (cmp2 > 0) {
                h.left.right = splay(h.left.right, key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }

            if (h.left == null) return h;
            else return rotateRight(h);
        } else if (cmp1 > 0) {
            // key not in tree, so we're done
            if (h.right == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.right.key);
            if (cmp2 < 0) {
                h.right.left = splay(h.right.left, key);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            } else if (cmp2 > 0) {
                h.right.right = splay(h.right.right, key);
                h = rotateLeft(h);
            }

            if (h.right == null) return h;
            else return rotateLeft(h);
        } else return h;
    }


    /***************************************************************************
     *  Helper functions.
     ***************************************************************************/

    // height of tree (1-node tree has height 0)
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }

    // right rotate
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    // left rotate
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }

    /**
     * Reading files and return list
     *
     * @param fileLocation add file location
     *
     */
    public List<Long> readFiles(String fileLocation) throws FileNotFoundException {
        //        Reading files and get them in to memory ____ insert operataion

        ArrayList<Long> allData = new ArrayList<>();

        Scanner scanner = new Scanner(new File(fileLocation));
        while (scanner.hasNext()){
            String f = scanner.next();
            String[] row = f.split(",");

            for(String a:row){
                allData.add(Long.parseLong(a));
            }
        }
        return  allData;
    }

    // test client
    public static void main(String[] args) throws FileNotFoundException {
        List<String> fileNameList = new ArrayList<>();
        fileNameList.add("set1/data_1.txt");
        fileNameList.add("set1/data_2.txt");
        fileNameList.add("set1/data_3.txt");
        fileNameList.add("set2/data_1.txt");
        fileNameList.add("set2/data_2.txt");
        fileNameList.add("set2/data_3.txt");

//        Iterate filenames for insert the data
        for(String fileName: fileNameList){
            SplayBST<Long, Integer> st = new SplayBST<>();
            List<Long> dataFromFileSelected = new ArrayList<>();
            List<Long> dataFromFileSelectedForSearch = new ArrayList<>();
            List<Long> dataFromFileSelectedForDelete = new ArrayList<>();

            dataFromFileSelected = st.readFiles("./src/data/insert/" + fileName);
            dataFromFileSelectedForSearch = st.readFiles("./src/data/search/" + fileName);
            dataFromFileSelectedForDelete = st.readFiles("./src/data/delete/" + fileName);


//            Insert the values to a empty BST
            Integer valueGenerator = 1;
            long startTime = System.nanoTime() /1000;
            for(Long a: dataFromFileSelected){
                st.put(a, valueGenerator);
                valueGenerator+=1;
            }
            long endTime = System.nanoTime() /1000;

            long elapsedTime = endTime - startTime;

            System.out.println("Splay Tree inset operation with data file " + fileName + " has been Completed.");
            System.out.println("Elapsed Time in microseconds: " + elapsedTime);

//            Searching the BST to find keys
            long startTimeForSearch = System.nanoTime() /1000;
            for(Long a: dataFromFileSelectedForSearch){
                st.get(a);
            }
            long endTimeForSearch = System.nanoTime() / 1000;

            long elapsedTimeForSearch = endTimeForSearch - startTimeForSearch;

            System.out.println("Splay Tree search operation with data file " + fileName + " has been Completed.");
            System.out.println("Elapsed Time in microseconds: " + elapsedTimeForSearch);

//            Deleting keys from BST
            long startTimeForDelete = System.nanoTime() /1000;
            for(Long a: dataFromFileSelectedForDelete){
                st.remove(a);
            }
            long endTimeForDelete = System.nanoTime() /1000;

            long elapsedTimeForDelete = endTimeForDelete - startTimeForDelete;

            System.out.println("Splay Tree delete operation with data file " + fileName + " has been Completed.");
            System.out.println("Elapsed Time in microseconds: " + elapsedTimeForDelete);

        }

    }

}