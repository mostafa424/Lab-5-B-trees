package com.company;

import java.util.Random;

public class TestCode {
    public void test(){

        BTree<Integer,String> testTree=new BTree<>(3);
        for(int i=0; i<100; i++) {
            testTree.insert(new Random().nextInt(),getRandomString());
        }
        System.out.println("keys:"+testTree.getRoot().getKeys());
        System.out.println("values:"+testTree.getRoot().getValues());
    }

    private String getRandomString() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 8/*length of the string*/; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());

            // get character specified by index
            // from the string
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }
}
