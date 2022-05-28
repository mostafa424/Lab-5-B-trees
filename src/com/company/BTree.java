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
            int index = root.getKeys().indexOf(key);
            if(index != -1) {
                root.getValues().set(index, value);
            } else if(root.getKeys().size() == max_degree){
                IBTreeNode<K,V> tmp = new BTreeNode<>();
                tmp.setLeaf(false);
                tmp.getChildren().add(root);
                splitNode(0, tmp, root);
                int i = 0;
                if (tmp.getKeys().get(0).compareTo(key)<0)
                {
                    i++;
                }
                insertNotFull(tmp.getChildren().get(i),key,value);
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
        if (root == null) return false;
        return deleteValue(null, root, key);
    }

    private void splitNode(int childPosition, IBTreeNode<K, V> parentNode, IBTreeNode<K, V> childNode){
        IBTreeNode<K,V> newNode = new BTreeNode<>();
            newNode.setLeaf(childNode.isLeaf());
        for(int i = 0; i < this.min_degree - 1; i++){
            newNode.getKeys().add(childNode.getKeys().remove(min_degree));
            newNode.getValues().add(childNode.getValues().remove(min_degree));
        }
        if(!childNode.isLeaf()){
            for(int i = 0; i < min_degree; i++){
                newNode.getChildren().add(childNode.getChildren().remove(min_degree));
            }
        }
        parentNode.getChildren().add(childPosition+1, newNode);
        if(parentNode.getKeys().isEmpty()){
            parentNode.getKeys().add(childNode.getKeys().remove(min_degree-1));
            parentNode.getValues().add(childNode.getValues().remove(min_degree-1));
        }
        else{
            parentNode.getKeys().add(childPosition, childNode.getKeys().remove(min_degree-1));
            parentNode.getValues().add(childPosition,childNode.getValues().remove(min_degree-1));
        }
    }

    private void insertNotFull(IBTreeNode<K, V> node, K key, V value){
        int i = node.getNumOfKeys() - 1;
        int index = node.getKeys().indexOf(key);
        if(index != -1) {
            node.getValues().set(index, value);
        } else if(node.isLeaf()){
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
                splitNode(i+1 , node, node.getChildren().get(i+1));
                if (key.compareTo(node.getKeys().get(i+1)) > 0) {
                    i++;
                }
            }
            insertNotFull(node.getChildren().get(i+1), key, value);
        }
    }

    private V searchNode(IBTreeNode<K, V> node, K key){
        int i = 0;
        while(i < node.getKeys().size() && node.getKeys().get(i).compareTo(key)< 0){
            i++;
        }
        if(i!=node.getKeys().size() && node.getKeys().size()!=0 && node.getKeys().get(i).equals(key)){
            return node.getValues().get(i);
        } else if (node.isLeaf()) {
            return null;
        } else {
            return searchNode(node.getChildren().get(i), key);
        }
    }

    private boolean deleteValue(IBTreeNode<K, V> nodeParent, IBTreeNode<K, V> node, K key) {
        int i = 0;
        while(i < node.getKeys().size() && node.getKeys().get(i).compareTo(key) < 0){
            i++;
        }
        if(i != node.getKeys().size() && node.getKeys().size() != 0 && node.getKeys().get(i).equals(key)){
            if (node.isLeaf()) {
                node.getKeys().remove(i);
                node.getValues().remove(i);
                if (node.getNumOfKeys() < min_degree - 1 && nodeParent != null) {
                    deleteFixup(nodeParent, nodeParent.getChildren().indexOf(node));
                    if(root.getKeys().isEmpty()) root = node;
                }
            } else {
                node.getKeys().set(i, node.getChildren().get(i + 1).getKeys().get(0));
                node.getValues().set(i, node.getChildren().get(i + 1).getValues().get(0));
                deleteValue(node, node.getChildren().get(i + 1), node.getKeys().get(i));
            }
            return true;
        } else if (node.isLeaf()) {
            return false;
        } else {
            boolean res = deleteValue(node, node.getChildren().get(i), key);
            if(node.getNumOfKeys() < min_degree - 1 && nodeParent != null) {
                deleteFixup(nodeParent, nodeParent.getChildren().indexOf(node));
            }
            return res;
        }
    }

    private void deleteFixup(IBTreeNode<K, V> parentNode, int childNum) {
        if (childNum > 0 && parentNode.getChildren().get(childNum - 1).getNumOfKeys() > min_degree - 1) {
            parentNode.getChildren().get(childNum).getKeys().add(0, parentNode.getKeys().get(childNum - 1));
            parentNode.getChildren().get(childNum).getValues().add(0, parentNode.getValues().get(childNum - 1));
            if(!parentNode.getChildren().get(childNum).isLeaf()) {
                parentNode.getChildren().get(childNum).getChildren().add(0, parentNode.getChildren().get(childNum - 1).getChildren().remove(parentNode.getChildren().get(childNum - 1).getChildren().size() - 1));
            }
            parentNode.getKeys().set(childNum - 1, parentNode.getChildren().get(childNum - 1).getKeys().remove(parentNode.getChildren().get(childNum - 1).getKeys().size() - 1));
            parentNode.getValues().set(childNum - 1, parentNode.getChildren().get(childNum - 1).getValues().remove(parentNode.getChildren().get(childNum - 1).getValues().size() - 1));
        } else if (childNum < parentNode.getChildren().size() - 1 && parentNode.getChildren().get(childNum + 1).getNumOfKeys() > min_degree - 1) {
            parentNode.getChildren().get(childNum).getKeys().add(parentNode.getKeys().get(childNum));
            parentNode.getChildren().get(childNum).getValues().add(parentNode.getValues().get(childNum));
            if(!parentNode.getChildren().get(childNum).isLeaf()) {
                parentNode.getChildren().get(childNum).getChildren().add(parentNode.getChildren().get(childNum + 1).getChildren().remove(parentNode.getChildren().get(childNum + 1).getChildren().size() - 1));
            }
            parentNode.getKeys().set(childNum, parentNode.getChildren().get(childNum + 1).getKeys().remove(0));
            parentNode.getValues().set(childNum, parentNode.getChildren().get(childNum + 1).getValues().remove(0));
        } else {
            if (childNum == 0) {
                parentNode.getChildren().get(0).getKeys().add(parentNode.getKeys().remove(0));
                parentNode.getChildren().get(0).getValues().add(parentNode.getValues().remove(0));
                parentNode.getChildren().get(0).getKeys().addAll(parentNode.getChildren().get(1).getKeys());
                parentNode.getChildren().get(0).getValues().addAll(parentNode.getChildren().get(1).getValues());
                if(!parentNode.getChildren().get(0).isLeaf()) {
                    parentNode.getChildren().get(0).getChildren().addAll(parentNode.getChildren().get(1).getChildren());
                }
                parentNode.getChildren().remove(1);
            } else {
                parentNode.getChildren().get(childNum - 1).getKeys().add(parentNode.getKeys().remove(childNum - 1));
                parentNode.getChildren().get(childNum - 1).getValues().add(parentNode.getValues().remove(childNum - 1));
                parentNode.getChildren().get(childNum - 1).getKeys().addAll(parentNode.getChildren().get(childNum).getKeys());
                parentNode.getChildren().get(childNum - 1).getValues().addAll(parentNode.getChildren().get(childNum).getValues());
                if(!parentNode.getChildren().get(childNum - 1).isLeaf()) {
                    parentNode.getChildren().get(childNum - 1).getChildren().addAll(parentNode.getChildren().get(childNum).getChildren());
                }
                parentNode.getChildren().remove(childNum);
            }
        }
    }

}
