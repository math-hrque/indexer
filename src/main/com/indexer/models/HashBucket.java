package com.indexer.models;

public class HashBucket {
    private HashBucketNode head;

    public void insert(HashEntry data) {
        HashBucketNode newNode = new HashBucketNode();
        newNode.setData(data);

        if (head == null) {
            head = newNode;
            return;
        }

        HashBucketNode current = head;
        while (current != null) {
            if (current.getData().getKey().equals(data.getKey())) {
                current.getData().setValue(current.getData().getValue() + data.getValue());
                return;
            }
            if (current.getNext() == null) break;
            current = current.getNext();
        }
        current.setNext(newNode);
    }

    public HashBucketNode getHead() {
        return head;
    }
}
