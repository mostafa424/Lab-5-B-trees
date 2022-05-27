package com.company;

public class Main {

    public static void main(String[] args) {
	  //  System.out.println(new ChooseFile().chooseFile());
      //  System.out.println(new ChooseDirectory().chooseFile());
       // new MainController().run();
        System.out.println("Draft".compareTo("ann"));
    }
}
/*
// write your code here
      try{
          String curDirectory = System.getProperty("user.dir");
          File file = new File(curDirectory+"\\input.txt");
          DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
          Document document = documentBuilder.parse(file);
          document.getDocumentElement().normalize();
          System.out.println("Root: " + document.getDocumentElement().getTagName());
          NodeList nodeList = document.getElementsByTagName("student");
          for (int i=0; i<nodeList.getLength(); i++) {
              Node node = nodeList.item(i);
              System.out.println("\nCurrent node: "+ node.getNodeName());
              if (node.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) node;
                  System.out.println("Student roll no : "
                          + eElement.getAttribute("rollno"));
                  System.out.println("First Name : "
                          + eElement
                          .getElementsByTagName("firstname")
                          .item(0)
                          .getTextContent());
                  System.out.println("Last Name : "
                          + eElement
                          .getElementsByTagName("lastname")
                          .item(0)
                          .getTextContent());
                  System.out.println("Nick Name : "
                          + eElement
                          .getElementsByTagName("nickname")
                          .item(0)
                          .getTextContent());
                  System.out.println("Marks : "
                          + eElement
                          .getElementsByTagName("marks")
                          .item(0)
                          .getTextContent());
              }
          }
       }catch(Exception e) {
          e.printStackTrace();
            System.out.println("An error occurred");
       }
 */
