package com.company.SearchEngine;

import com.company.BTree;
import com.company.IBTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine implements ISearchEngine {
    private final IBTree<Integer, WikiDoc> searchTree;
    private final List<Integer> insertedID;


    public SearchEngine(IBTree<Integer, WikiDoc> searchTree, List<Integer> insertedID) {
        this.searchTree = searchTree;
        this.insertedID = insertedID;
    }

    @Override
    public void indexWebPage(String filePath) {
        List<Element> docElements = parseElements(filePath);
        if (docElements == null) return;
        for (Element eElement : docElements) {
            IBTree<String, Integer> wordTree = new BTree<>(20);
            String text = eElement.getTextContent();
            String[] textArray = text.split(" ");
            for (String word : textArray) {
                word = word.replace("\n", "");
                Integer res = wordTree.search(word);
                if (res == null) res = 0;
                wordTree.insert(word, res);
            }
            WikiDoc doc = new WikiDoc(Integer.parseInt(eElement.getAttribute("id")), eElement.getAttribute("url"), eElement.getAttribute("title"), wordTree);
            this.searchTree.insert(doc.getId(), doc);
            this.insertedID.add(doc.getId());
        }
    }

    @Override
    public void indexDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if(directory.isDirectory()) {
            File[] files = directory.listFiles();
            if(files != null) {
                for(File file: files) {
                    indexDirectory(file.getAbsolutePath());
                }
            }
        } else if(directory.isFile()) {
            indexWebPage(directoryPath);
        }
    }

    @Override
    public void deleteWebPage(String filePath) {
        List<Element> docElements = parseElements(filePath);
        if(docElements == null) return;
        for (Element docElement : docElements) {
            this.searchTree.delete(Integer.parseInt(docElement.getAttribute("id")));
            this.insertedID.remove(Integer.parseInt(docElement.getAttribute("id")));
        }
    }

    @Override
    public List<ISearchResult> searchByWordWithRanking(String word) {
        List<ISearchResult> res = new ArrayList<>();
        for (Integer integer : this.insertedID) {
            ISearchResult searchResult = new SearchResult();
            WikiDoc doc = this.searchTree.search(integer);
            if (doc == null) continue;
            Integer rank = doc.getWordRep().search(word);
            if (rank == null) continue;
            searchResult.setId(Integer.toString(doc.getId()));
            searchResult.setRank(rank);
            res.add(searchResult);
        }
        sortSearchResults(res);
        return res;
    }

    private void sortSearchResults(List<ISearchResult> res) {
        res.sort((o1, o2) -> Integer.compare(o2.getRank(), o1.getRank()));
    }

    @Override
    public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
        List<ISearchResult> res = new ArrayList<>();
        String[] words = sentence.split(" ");
        for (Integer integer : this.insertedID) {
            ISearchResult searchResult = new SearchResult();
            WikiDoc doc = this.searchTree.search(integer);
            if (doc == null) continue;
            boolean allWordsFound = true;
            int combinedRank = Integer.MAX_VALUE;
            for (String word : words) {
                Integer rank = doc.getWordRep().search(word);
                if (rank == null) {
                    allWordsFound = false;
                    break;
                }
                combinedRank = Integer.min(combinedRank, rank);
            }
            if (allWordsFound && combinedRank != Integer.MAX_VALUE) {
                searchResult.setId(Integer.toString(doc.getId()));
                searchResult.setRank(combinedRank);
                res.add(searchResult);
            }
        }
        sortSearchResults(res);
        return res;
    }

    private List<Element> parseElements(String filePath) {
        List<Element> res = new ArrayList<>();
        File file = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            System.out.println("Something went wrong when constructing document builder.");
            return null;
        }
        Document document;
        try {
            document = documentBuilder.parse(file);
        } catch (IOException ex) {
            System.out.println("Something went wrong when reading file.");
            return null;
        } catch (SAXException ex) {
            System.out.println("File being read is most likely not an XML file.");
            return null;
        }
        document.getDocumentElement().normalize();
        System.out.println("Root: " + document.getDocumentElement().getTagName());
        NodeList nodeList = document.getElementsByTagName("doc");
        for (int i=0; i<nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("\nCurrent node: "+ node.getNodeName());
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                res.add((Element) node);
            }
        }
        return res;
    }
}
