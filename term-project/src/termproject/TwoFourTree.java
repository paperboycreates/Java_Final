/**
 * Title:       Term Project 2-4 Trees
 * File:        TwoFourTree.java
 * Description: Class builds a 2-4 Tree which can store items based on its key
 * Due:         Dec 13, 2018
 * 
 * @author Jake Allinson
 * @author Jacob Sheets
 * @version 1.0
 */

package termproject;

//import com.sun.xml.internal.ws.util.DOMUtil;
//import java.awt.event.ItemEvent;

public class TwoFourTree implements Dictionary {

    private Comparator treeComp;
    private int size = 0;
    private TFNode treeRoot = null;

    public TwoFourTree(Comparator comp) {
        treeComp = comp;
    }

    private TFNode root() {
        return treeRoot;
    }

    private void setRoot(TFNode root) {
        treeRoot = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Searches dictionary to determine if key is present
     * @param key to be searched for
     * @return object corresponding to key; null if not found
     */
    public Object findElement (Object key) {
                
        TFNode result = findNode(treeRoot, key);
        
        int resultPos = FFGTE(result, key);
        
        if (treeComp.isEqual(result.getItem(resultPos).key(), key)) {
            
            return result.getItem(resultPos).key();
            
        // return null if element is not found
        } else {
            
            return null;
        }
    }
    
    // recursively finds Searched for ndoe of key
    public TFNode findNode (TFNode currNode, Object key) {
        
        int ffgtePos = FFGTE(currNode, key);  
          
        if (treeComp.isEqual(currNode.getItem(ffgtePos).key(), key)) {

            return currNode;
            
        } else { 

            return findNode(currNode.getChild(ffgtePos), key); 
        }
    }
    

    /**
     * Inserts provided element into the Dictionary
     * @param key of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {

        // create node that we will insert into
        TFNode insertNode = null;
        
        if (isEmpty()) { 
            // no root node, so make one
            insertNode = new TFNode();
            setRoot(insertNode);
        } else {
            // find node to insert item into
            insertNode = findExternalNode(treeRoot, key);
        }
        
        // now that we have our node, find index and insert our new item
        int index = FFGTE(insertNode, key);
        Item newItem = new Item(key, element);
        insertNode.insertItem(index, newItem);
        
        // if node has more than the max items allowed, run overflow
        if (insertNode.getNumItems() > insertNode.getMaxItems()) {
            OverFlow(insertNode);
        }

        // check the tree
        checkTree();
        printTree(treeRoot, index);

        // increment the size of our tree
        size++;
        
    }

    
    // method corrects an overflowed node
    private void OverFlow (TFNode currNode) {
        
        // keep running until our curr node, which we move up the tree, isn't overflowed
        while (currNode.getNumItems() > currNode.getMaxItems()) {

            // perform a none-shifting remove
            Item removedItem = currNode.getItem(2);
            currNode.deleteItem(2);
            
            TFNode parentNode;
            
            // EDGE CASE: check if there wasn't a parent for current node
            if (currNode.getParent() == null) {
                
                // we need a new root, and connect to child
                parentNode = new TFNode();
                setRoot(parentNode);
                // connect to each other
                currNode.setParent(parentNode);
                parentNode.setChild(0, currNode);
                
            }
                        
            // get parent node
            parentNode = currNode.getParent();
            
            // put removed item in parent
            int index = FFGTE(parentNode, removedItem.key());
            parentNode.insertItem(index, removedItem);
         
            // create new node and connect it to parent's last child
            TFNode newNode = new TFNode();
            newNode.addItem(0, currNode.getItem(3));
            // use child + 1, like we talked about in class
            parentNode.setChild(index + 1, newNode);
            
            // grab 3 child and creat node
            TFNode currNodeChild3 = currNode.getChild(3);
            
            // Clean house
            currNode.setChild(3, null);
            currNode.deleteItem(3);
            
            // Set child to new Node
            newNode.setChild(0, currNodeChild3);
            
            // grab 4 child and create node
            TFNode currNodeChild4 = currNode.getChild(4);
            currNode.setChild(4, null);
            newNode.setChild(1, currNodeChild4);
            
                        
            // EDGE CASE: check if first child of new node (child 3 of current) is null
            if (currNodeChild3 != null) {
                // if we have children, connect them to their newly created parent node (newNode)
                currNodeChild3.setParent(newNode);
                currNodeChild4.setParent(newNode);
            }

            // connect up our 2 nodes to the parent
            newNode.setParent(parentNode);
            currNode.setParent(parentNode);
            
            // finally move current
            currNode = parentNode;
     
        }
    }
    
    
    // method finds which position key to place item into in a node
    private int FFGTE (TFNode currNode, Object key) {
        // iterate through current node's items to find one >= the key
        for (int i = 0; i < currNode.getNumItems(); i++) {
            if (treeComp.isGreaterThanOrEqualTo(currNode.getItem(i).key(), key)) {
                return i;
            }
        } 
        return currNode.getNumItems();
    }
    
    
    // method recursively finds an external node to place new item
    private TFNode findExternalNode(TFNode currNode, Object key) {
        
        if (currNode.isExternal()) {
            
            // our node is a leaf
            return currNode;
            
        } else { 
            
            // find position such that a < key < b
            int pos = FFGTE (currNode, key);
            // lets try again with our new current
            return findExternalNode (currNode.getChild(pos), key);
            
        }
        
    }


    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     * @param key of data to be removed
     * @return object corresponding to key
     * @exception ElementNotFoundException if the key is not in dictionary
     */
    public Object removeElement(Object key) throws ElementNotFoundException {
        return null;
    }

    public static void main(String[] args) {
        
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);
        
        Integer myInt1 = new Integer(47);
        myTree.insertElement(myInt1, myInt1);
        
        Integer myInt2 = new Integer(83);
        myTree.insertElement(myInt2, myInt2);
        
        Integer myInt3 = new Integer(22);
        myTree.insertElement(myInt3, myInt3);

        Integer myInt4 = new Integer(16);
        myTree.insertElement(myInt4, myInt4);

        Integer myInt5 = new Integer(49);
        myTree.insertElement(myInt5, myInt5);

        Integer myInt6 = new Integer(100);
        myTree.insertElement(myInt6, myInt6);

        Integer myInt7 = new Integer(38);
        myTree.insertElement(myInt7, myInt7);

        Integer myInt8 = new Integer(3);
        myTree.insertElement(myInt8, myInt8);

        Integer myInt9 = new Integer(53);
        myTree.insertElement(myInt9, myInt9);

        Integer myInt10 = new Integer(66);
        myTree.insertElement(myInt10, myInt10);

        Integer myInt11 = new Integer(19);
        myTree.insertElement(myInt11, myInt11);

        Integer myInt12 = new Integer(23);
        myTree.insertElement(myInt12, myInt12);

        Integer myInt13 = new Integer(24);
        myTree.insertElement(myInt13, myInt13);

        Integer myInt14 = new Integer(88);
        myTree.insertElement(myInt14, myInt14);

        Integer myInt15 = new Integer(1);
        myTree.insertElement(myInt15, myInt15);

        Integer myInt16 = new Integer(97);
        myTree.insertElement(myInt16, myInt16);

        Integer myInt17 = new Integer(94);
        myTree.insertElement(myInt17, myInt17);

        Integer myInt18 = new Integer(35);
        myTree.insertElement(myInt18, myInt18);

        Integer myInt19 = new Integer(51);
        myTree.insertElement(myInt19, myInt19);

        myTree.printAllElements();
        System.out.println("done");

        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;


        for (int i = 0; i < TEST_SIZE; i++) {
            myTree.insertElement(new Integer(i), new Integer(i));
            //          myTree.printAllElements();
            //         myTree.checkTree();
        }
        System.out.println("removing");
        for (int i = 0; i < TEST_SIZE; i++) {
            int out = (Integer) myTree.removeElement(new Integer(i));
            if (out != i) {
                throw new TwoFourTreeException("main: wrong element removed");
            }
            if (i > TEST_SIZE - 15) {
                myTree.printAllElements();
            }
        }
        System.out.println("done");
    }

    public void printAllElements() {
        int indent = 0;
        if (root() == null) {
            System.out.println("The tree is empty");
        }
        else {
            printTree(root(), indent);
        }
    }

    public void printTree(TFNode start, int indent) {
        if (start == null) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        printTFNode(start);
        indent += 4;
        int numChildren = start.getNumItems() + 1;
        for (int i = 0; i < numChildren; i++) {
            printTree(start.getChild(i), indent);
        }
    }

    public void printTFNode(TFNode node) {
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            System.out.print(((Item) node.getItem(i)).element() + " ");
        }
        System.out.println();
    }

    // checks if tree is properly hooked up, i.e., children point to parents
    public void checkTree() {
        checkTreeFromNode(treeRoot);
    }

    private void checkTreeFromNode(TFNode start) {
        if (start == null) {
            return;
        }

        if (start.getParent() != null) {
            TFNode parent = start.getParent();
            int childIndex = 0;
            for (childIndex = 0; childIndex <= parent.getNumItems(); childIndex++) {
                if (parent.getChild(childIndex) == start) {
                    break;
                }
            }
            // if child wasn't found, print problem
            if (childIndex > parent.getNumItems()) {
                System.out.println("Child to parent confusion");
                printTFNode(start);
            }
        }

        if (start.getChild(0) != null) {
            for (int childIndex = 0; childIndex <= start.getNumItems(); childIndex++) {
                if (start.getChild(childIndex) == null) {
                    System.out.println("Mixed null and non-null children");
                    printTFNode(start);
                }
                else {
                    if (start.getChild(childIndex).getParent() != start) {
                        System.out.println("Parent to child confusion");
                        printTFNode(start);
                    }
                    for (int i = childIndex - 1; i >= 0; i--) {
                        if (start.getChild(i) == start.getChild(childIndex)) {
                            System.out.println("Duplicate children of node");
                            printTFNode(start);
                        }
                    }
                }

            }
        }

        int numChildren = start.getNumItems() + 1;
        for (int childIndex = 0; childIndex < numChildren; childIndex++) {
            checkTreeFromNode(start.getChild(childIndex));
        }

    }
}
