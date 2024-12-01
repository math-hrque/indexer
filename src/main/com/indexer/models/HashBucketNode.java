package com.indexer.models;

public class HashBucketNode {
    private HashEntry data;
    private HashBucketNode next;

    public HashEntry getData() {
        return data;
    }

    public void setData(HashEntry data) {
        this.data = data;
    }

    public HashBucketNode getNext() {
        return next;
    }

    public void setNext(HashBucketNode next) {
        this.next = next;
    }
}
