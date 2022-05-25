package com.company;

public class BTree <K extends Comparable<K>, V> implements IBTree{
   private IBTreeNode<K,V> root;
   private int min_degree;

    public BTree(int min_degree) {
        this.root = new BTreeNode<>(min_degree);
        this.min_degree = min_degree;
    }

    @Override
    public int getMinimumDegree() {
        return this.min_degree;
    }

    @Override
    public IBTreeNode getRoot() {
        return root;
    }

    @Override
    public void insert(Comparable key, Object value) {
        if(root==null){
            root=new BTreeNode<>(min_degree);
            root.getKeys().add((K) key);
            root.getValues().add((V)value);
        }
        else{
            int max_degree=2*min_degree-1;
            if(root.getKeys().size()==max_degree){
                IBTreeNode<K,V>tmp=new BTreeNode<>(min_degree);
                tmp.setLeaf(false);
                tmp.getChildren().add(root);
                tmp.splitNode(0,root);
                root=tmp;
            }
            else{root.insertNotFull(key,value);}
        }
    }

    @Override
    public Object search(Comparable key) {
        if(root == null){return null;}
        return root.search(key);
    }

    @Override
    public boolean delete(Comparable key) {
        return false;
    }

}
