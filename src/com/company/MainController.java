package com.company;

import com.company.BTree;
import com.company.IBTree;
import com.company.SearchEngine.ISearchEngine;
import com.company.SearchEngine.ISearchResult;
import com.company.SearchEngine.SearchEngine;
import com.company.SearchEngine.WikiDoc;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println("Choose operation");
        System.out.println("1-Search in one file.");
        System.out.println("2-Search in one files.");
        System.out.println("3-Delete file from engine.");
        Scanner scanner = new Scanner(System.in);
        IChooser iChooser;
        IBTree<Integer, WikiDoc> testTree = new BTree<>(20);
        ISearchEngine iSearchEngine = new SearchEngine(testTree, new ArrayList<Integer>());
        String path;
        int input= Integer.parseInt(scanner.nextLine());
        boolean searching = true;
        boolean running = true;
        while(running) {
            switch (input) {
                case 1:
                    iChooser = new ChooseFile();
                    path = iChooser.getDirectory();
                    iSearchEngine.indexWebPage(path);
                    break;
                case 2:
                    iChooser = new ChooseDirectory();
                    path = iChooser.getDirectory();
                    iSearchEngine.indexDirectory(path);
                    break;
                case 3:
                    iChooser = new ChooseFile();
                    path = iChooser.getDirectory();
                    iSearchEngine.deleteWebPage(path);
                    searching = false;
                    break;
            }
            System.out.println();
            while (searching) {
                System.out.println("Enter Words or -1 to finish");
                String word = scanner.nextLine();
                if (word.equals("-1")) {
                    running = false;
                    break;
                } else {
                    List<ISearchResult> list = iSearchEngine.searchByMultipleWordWithRanking(word);
                    for (ISearchResult iSearchResult : list) {
                        System.out.println("ID: " + iSearchResult.getId() + "  Rank: " + iSearchResult.getRank());
                    }
                }
            }
        }
    }
}
