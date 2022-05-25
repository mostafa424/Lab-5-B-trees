package com.company;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode<K extends Comparable<K>,V> implements IBTreeNode{
   private List<K> keys;
   private List<V> values;
   private List<IBTreeNode> children;
   private boolean leaf;
   private int min_deg;

    public BTreeNode(int numOfKeys) {
        this.keys=new ArrayList<>();
        this.values=new ArrayList<>();
        this.children=new ArrayList<>();
        this.min_deg = numOfKeys;
        this.leaf=true;
    }

    @Override
    public int getNumOfKeys() {
        return keys.size();
    }

    @Override
    public void setNumOfKeys(int numOfKeys) {}

    @Override
    public boolean isLeaf() {
        return this.leaf;
    }

    @Override
    public void setLeaf(boolean isLeaf) {
        this.leaf=isLeaf;
    }

    @Override
    public List<K> getKeys() {
        return keys;
    }

    @Override
    public void setKeys(List keys) {
        this.keys=keys;
    }

    @Override
    public List<V> getValues() {
        return values;
    }

    @Override
    public void setValues(List values) {
        this.values=values;
    }

    @Override
    public List<IBTreeNode> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List children) {
        this.children=children;
    }

    public int getMin_deg() {
        return min_deg;
    }

    public void setMin_deg(int min_deg) {
        this.min_deg = min_deg;
    }

    public Object search(Comparable key){
        int i=0;
        while(i<keys.size() && key.compareTo(keys.get(i))<0){
           i++;
        }
        if(keys.get(i)==key){
            return values.get(i);
        }
        else if(this.leaf){
            return null;
        }
        else{
           return children.get(i).search(key);
        }
    }
    @Override
    public void splitNode(int child_position, IBTreeNode node){
        IBTreeNode<K,V> new_node=new BTreeNode<>(node.getMin_deg());
        for(int i=0;i<min_deg-1;i++){
            new_node.getKeys().add((K)node.getKeys().remove(i+min_deg));
            new_node.getValues().add((V)node.getValues().remove(i+min_deg));
        }
        if(!node.isLeaf()){
            for(int i=0;i<min_deg;i++){
                new_node.getChildren().add((IBTreeNode<K, V>) node.getChildren().remove(i+min_deg));
            }
        }
        this.children.add(child_position+1,new_node);
        this.keys.add(child_position+1,(K)node.getKeys().get(min_deg-1));
    }
    public void insertNotFull(Comparable key,Object value){
        int last_key=min_deg-1;
        int i=last_key;
        if(isLeaf()){
            while(i>=0 && key.compareTo(this.keys.get(i))<0){
                i--;
            }
            this.keys.add(i+1,(K)key);
        }
        else{
            while(i>=0 && key.compareTo(this.keys.get(i))<0){
                i--;
            }
            if(this.children.get(i+1).getKeys().size()==2*min_deg-1) {
                splitNode(i + 1, this.children.get(i + 1));

                if (key.compareTo(this.keys.get(i)) > 0) {
                    i++;
                }
                this.children.get(i).insertNotFull(key,value);
            }
        }
    }
}