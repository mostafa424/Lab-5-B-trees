package com.company;

public class BTree <K extends Comparable<K>, V> implements IBTree<K, V>{
   private IBTreeNode<K,V> root;
   private final int min_degree;

    public BTree (int min_degree) {
        this.root = new BTreeNode<>();
        this.min_degree = min_degree;
    }

    @Override
    public int getMinimumDegree() {
        return this.min_degree;
    }

    @Override
    public IBTreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(K key, V value) {
        if (root == null) {
            root = new BTreeNode<>();
            root.getKeys().add(key);
            root.getValues().add(value);
        } else {
            int max_degree = 2 * min_degree - 1;
            if(root.getKeys().size() == max_degree){
                IBTreeNode<K,V> tmp = new BTreeNode<>();
                tmp.setLeaf(false);
                tmp.getChildren().add(root);
                splitNode(0, tmp, root);
                root = tmp;
            } else {
                insertNotFull(root, key, value);
            }
        }
    }

    @Override
    public V search(K key) {
        if(root == null){return null;}
        return searchNode(root, key);
    }

    @Override
    public boolean delete(K key) {
        return false;
    }

    private void splitNode(int childPosition, IBTreeNode<K, V> parentNode, IBTreeNode<K, V> childNode){
        IBTreeNode<K,V> newNode = new BTreeNode<>();
        for(int i = 0; i < this.min_degree - 1; i++){
            newNode.getKeys().add(childNode.getKeys().remove(i + min_degree));
            newNode.getValues().add(childNode.getValues().remove(i + min_degree));
        }
        if(!childNode.isLeaf()){
            for(int i = 0; i < min_degree; i++){
                newNode.getChildren().add(childNode.getChildren().remove(i + min_degree));
            }
        }
        parentNode.getChildren().add(childPosition+1, newNode);
        parentNode.getKeys().add(childPosition+1, childNode.getKeys().get(min_degree-1));
    }

    private void insertNotFull(IBTreeNode<K, V> node, K key, V value){
        int i = min_degree - 1;
        if(node.isLeaf()){
            while(i >= 0 && key.compareTo(node.getKeys().get(i)) < 0){
                i--;
            }
            node.getKeys().add(i + 1, key);
            node.getValues().add(i + 1, value);
        } else {
            while(i >= 0 && key.compareTo(node.getKeys().get(i)) < 0){
                i--;
            }
            if(node.getChildren().get(i+1).getKeys().size() == 2 * min_degree - 1) {
                splitNode(i + 1, node, node.getChildren().get(i + 1));
                if (key.compareTo(node.getKeys().get(i)) > 0) {
                    i++;
                }
                insertNotFull(node.getChildren().get(i), key, value);
            }
        }
    }

    private V searchNode(IBTreeNode<K, V> node, K key){
        int i = 0;
        while(i < node.getKeys().size() && key.compareTo(node.getKeys().get(i)) < 0){
            i++;
        }
        if(node.getKeys().get(i)==key){
            return node.getValues().get(i);
        } else if (node.isLeaf()) {
            return null;
        } else {
            return searchNode(node.getChildren().get(i), key);
        }
    }

}
