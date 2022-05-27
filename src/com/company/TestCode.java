package com.company;

public class TestCode {
    public void test(){
        BTree<Integer,String> testTree=new BTree<>(3);
        testTree.insert(5,"cat");
        testTree.insert(6,"cat");
        testTree.insert(4,"cat");
        testTree.insert(7,"cat");
        testTree.insert(8,"cat");
        testTree.insert(2,"cat");
        testTree.insert(1,"cat");
        System.out.println("keys:"+testTree.getRoot().getKeys());
        System.out.println("values:"+testTree.getRoot().getValues());
    }
}
