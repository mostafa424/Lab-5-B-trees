package com.company;

import com.company.BTree;
import com.company.IBTree;

import java.util.Scanner;

public class MainController {
    public void run(){
        System.out.println("Welcome to B-tree Program");
        System.out.println("For B-tree Testing press 1");
        System.out.println("For search engine testing press 2");
        Scanner scanner = new Scanner(System.in);
        int input= scanner.nextInt();
        switch(input){
            case 1:
                testBtree(); break;
            case 2:
                testSearchEngine();break;
            default:
                System.out.println("Error choosing the program exiting...");

        }
        scanner.close();
    }
    private void testBtree(){

        Scanner scanner=new Scanner(System.in);
        int input=-1;
        System.out.println("Welcome to B-tree testing Program");
        System.out.println("Enter minimum degree: ");
        IBTree<Integer,String> testTree=new BTree<>(scanner.nextInt());
        System.out.println("For insertions press 1");
        System.out.println("For searching press 2");
        System.out.println("For deletion press 3");
        System.out.println("To Exit press 4");
        while(input!=0){
                input= scanner.nextInt();
                switch (input){
                    case 1:
                        System.out.println("Enter key:");
                        int key= scanner.nextInt();
                        System.out.println("Enter val:");
                        String val= scanner.next();
                        testTree.insert(key,val);
                        System.out.println("Operation Success");break;
                    case 2:System.out.println("Enter key:");
                        int key2= scanner.nextInt();
                        System.out.println("val is : "+testTree.search(key2));
                        break;
                    case 3:
                        System.out.println("Enter key:");
                        int key3= scanner.nextInt();
                        testTree.delete(key3);
                        System.out.println("Operation Success");
                        break;
                }
        }


    }
    private void testSearchEngine(){

    }
}
