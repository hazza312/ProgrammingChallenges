/*
Detect a cycle in a linked list. Note that the head pointer may be 'null' if the list is empty.

A Node is defined as: 
    class Node {
        int data;
        Node next;
    }
*/

boolean hasCycle(Node head) {
    HashMap<Node, Integer> nodes = new HashMap<Node, Integer>();
    
    while (head != null) {
        if (nodes.get(head)!= null) {
            return true;
        }        
        nodes.put(head, 1);
        head = head.next;        
    }
    
    return false;
}