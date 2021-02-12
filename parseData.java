//Note most of the functions in this should be combined into several smaller functions, with one controller
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
public class parseData {
   public void parseScenes(SceneCard[] possibleScenes) throws Exception {
      DocumentBuilderFactory myDomFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder myBuilder = myDomFactory.newDocumentBuilder();
      Document cardDoc = myBuilder.parse("cards.xml");
      
      NodeList cardList = cardDoc.getElementsByTagName("card");
      //gets card values
      for(int i = 0; i < cardList.getLength(); i++) {
         String cardDescription = "";
         
         ArrayList<Role> roles = new ArrayList<Role>();
         
         Node cardNode = cardList.item(i);
         //removes empty nodes that where appearing for some reason
         removeEmptyNodes(cardNode);
         Element cardElement = (Element) cardNode;
         NodeList roleList = cardNode.getChildNodes();
         //gets role values
         for(int j = 0; j < roleList.getLength(); j++) {
            Node roleNode = roleList.item(j);
            String nodeName = roleNode.getNodeName();
            Element roleElement = (Element) roleNode;
            //scene info
            if(nodeName.equals("scene")) {
               cardDescription = roleNode.getTextContent();
            //if role create role
            } else {
               parseRole(roles, cardNode, roleElement, false);
            }
         }
         //add sceneCard to libary
         int budget = Integer.parseInt(cardElement.getAttribute("budget"));
         String name = cardElement.getAttribute("name");
         Role[] roleArray = Arrays.copyOf(roles.toArray(), roles.toArray().length, Role[].class);
         possibleScenes[i] = new SceneCard(name, cardDescription, budget, roleArray);
      }
   }
   public void parseRole(ArrayList<Role> roles, Node cardNode, Element roleElement, boolean extra) {
      String name = roleElement.getAttribute("name");
      String line = cardNode.getChildNodes().item(1).getTextContent();
      int rank = Integer.parseInt(roleElement.getAttribute("level"));
      Role newRole = new Role(name, line, rank, extra);
      roles.add(newRole);
   }
   
   public void removeEmptyNodes(Node node) {
      NodeList list = node.getChildNodes();
      for(int i = 0; i < list.getLength(); i++) {
            if(list.item(i).getChildNodes().getLength() == 0) {
               node.removeChild(list.item(i));
            }
      }
   }
   public void parseBoard(Room[] rooms, Scenes sceneLibray) throws Exception {
      parseRooms("set", rooms, sceneLibray);
      parseRooms("trailer", rooms, sceneLibray);
      parseRooms("office", rooms, sceneLibray);
   }
   public void parseRooms(String tagName, Room[] rooms, Scenes sceneLibray) throws Exception {
      DocumentBuilderFactory myDomFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder myBuilder = myDomFactory.newDocumentBuilder();
      Document boardDoc = myBuilder.parse("board.xml");
      NodeList roomList = boardDoc.getElementsByTagName(tagName);
      for(int i = 0; i < roomList.getLength(); i++) {
         ArrayList<Role> roles = new ArrayList<Role>();
         ArrayList<String> neighbors = new ArrayList<String>();
         int shotCounters = 0;
         Node roomNode = roomList.item(i);
         removeEmptyNodes(roomNode);
         Element roomElement = (Element) roomNode;
         NodeList roomInfoList = roomNode.getChildNodes();
         for(int j = 0; j < roomInfoList.getLength(); j++) {
            Node roomInfoNode = roomInfoList.item(j);
            removeEmptyNodes(roomInfoNode);
            String nodeName = roomInfoNode.getNodeName();
            Element roomInfoElement = (Element) roomInfoNode;
            NodeList roleList = roomInfoNode.getChildNodes();
            if(nodeName.equals("neighbors")) {
               for(int k = 0; k < roleList.getLength(); k++) {
                  Element roleElement = (Element) roleList.item(k);
                  neighbors.add(roleElement.getAttribute("name"));
               }
            } else if (nodeName.equals("parts")) {
               for(int k = 0; k < roleList.getLength(); k++) {
                  Element roleElement = (Element) roleList.item(k);
                  parseRole(roles, roomInfoNode, roleElement, true);;
               }
            } else if (nodeName.equals("takes")) {
               Element roleElement = (Element) roleList.item(0);
               shotCounters = Integer.parseInt(roleElement.getAttribute("number"));
            }
         }
         String name = "";
         if(tagName.equals("trailer")) {
            name = "Trailer";
            i = 10;
         } else if (tagName.equals("office")) {
            name = "Office";
            i = 11;
            //Still need to create castingOffice object
         } else {
            name = roomElement.getAttribute("name");
         }
         Role[] roleArray = Arrays.copyOf(roles.toArray(), roles.toArray().length, Role[].class);
         rooms[i] = new Room(name, shotCounters, roleArray, sceneLibray.getRandomCard());
      }
   }

}