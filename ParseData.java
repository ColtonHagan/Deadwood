/*
Name : Colton Hagan and Steven Le
Class : CS 345
Date : 2/23/21
Program Description : Parses data from xml file into a workable form
*/
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class ParseData {

    //Creates given array of all Scenes with SceneCards from xml files
    public void parseScenes(SceneCard[] possibleScenes) throws Exception {
        NodeList cardList = getOuterNodes("cards.xml", "card");
        for (int i = 0; i < cardList.getLength(); i++) {
            String cardDescription = "";
            int sceneNumber = 0;
            ArrayList<Role> roles = new ArrayList<Role>();
            Node cardNode = cardList.item(i);
            NodeList roleList = getInnerNodes(cardNode);
            Element cardElement = (Element) cardNode;
            
            //Finds roles and description/sceneNumbers on a SceneCard
            for (int j = 0; j < roleList.getLength(); j++) {
                Node roleNode = roleList.item(j);
                String nodeName = roleNode.getNodeName();
                Element roleElement = (Element) roleNode;
                if (nodeName.equals("scene")) {
                    cardDescription = roleNode.getTextContent().trim();
                    sceneNumber = Integer.parseInt(roleElement.getAttribute("number"));
                } else {
                    parseRole(roles, roleNode, roleElement, false);
                }
            }
            int budget = Integer.parseInt(cardElement.getAttribute("budget"));
            String name = cardElement.getAttribute("name");
            String image = cardElement.getAttribute("img");
            Role[] roleArray = Arrays.copyOf(roles.toArray(), roles.toArray().length, Role[].class);
            possibleScenes[i] = new SceneCard(name, cardDescription, budget, sceneNumber, roleArray, image);
        }
    }

    //Creates role from given node information
    private void parseRole(ArrayList<Role> roles, Node cardNode, Element roleElement, boolean extra) {
        int[] cords = new int[2];
        String name = roleElement.getAttribute("name");
        NodeList roleInfo = getInnerNodes(cardNode);
        Element areaElement = (Element) roleInfo.item(0);
        cords[0] = Integer.parseInt(areaElement.getAttribute("x"));
        cords[1] = Integer.parseInt(areaElement.getAttribute("y"));
        String line = roleInfo.item(1).getTextContent().trim();
        int rank = Integer.parseInt(roleElement.getAttribute("level"));
        Role newRole = new Role(name, line, rank, extra, cords);
        roles.add(newRole);
    }
    
    //Removes empty children of given node
    private void removeEmptyNodes(Node node) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getChildNodes().getLength() == 0) {
                node.removeChild(list.item(i));
            }
        }
    }
   
    //Parses initial nodes/tags of a Document 
    private NodeList getOuterNodes(String fileName, String tagName) throws Exception {
        DocumentBuilderFactory myDomFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder myBuilder = myDomFactory.newDocumentBuilder();
        Document myDoc = myBuilder.parse(fileName);
        return myDoc.getElementsByTagName(tagName);
    }

    //Removes empty nodes and returns children of a node
    private NodeList getInnerNodes(Node baseNode) {
        removeEmptyNodes(baseNode);
        return baseNode.getChildNodes();
    }

    //Parses possible upgrade from xml file
    public int[][] parseOffice() throws Exception {
        NodeList upgradeList = getOuterNodes("board.xml", "upgrade");
        int[][] possibleUpgrades = new int[5][3];
        for (int i = 0; i < upgradeList.getLength(); i++) {
            Element upgradeElement = (Element) upgradeList.item(i);
            int upgradeCost = Integer.parseInt(upgradeElement.getAttribute("amt"));
            if (upgradeElement.getAttribute("currency").equals("dollar")) {
                possibleUpgrades[i][0] = i + 2;
                possibleUpgrades[i][1] = upgradeCost;
            } else {
                possibleUpgrades[i - 5][2] = upgradeCost;
            }
        }
        return possibleUpgrades;
    }
    
    //Creates array of rooms from XML files
    private void parseRooms(NodeList itemList, Room[] rooms) {
        ArrayList<Role> roles = new ArrayList<Role>();
        ArrayList<String> neighbors = new ArrayList<String>();
        int[][] possibleUpgrades = new int[5][3];
        int shotCounters = 0;
        String name = "";
        int[] cords = new int[2];

        for (int i = 0; i < itemList.getLength(); i++) {
            Node roomNode = itemList.item(i);
            NodeList roomInfoList = getInnerNodes(roomNode);
            Element roomElement = (Element) roomNode;
            for (int j = 0; j < roomInfoList.getLength(); j++) {
                Node roomInfoNode = roomInfoList.item(j);
                NodeList roleList = getInnerNodes(roomInfoNode);
                String nodeName = roomInfoNode.getNodeName();
                Element roomInfoElement = (Element) roomInfoNode;
                
                //Finds adjecent room, roles, shot countesr
                if (nodeName.equals("neighbors")) {
                    for (int k = 0; k < roleList.getLength(); k++) {
                        Element roleElement = (Element) roleList.item(k);
                        neighbors.add(roleElement.getAttribute("name"));
                    }
                } else if (nodeName.equals("parts")) {
                    for (int k = 0; k < roleList.getLength(); k++) {
                        Element roleElement = (Element) roleList.item(k);
                        parseRole(roles, roleList.item(k), roleElement, true);
                    }
                } else if (nodeName.equals("takes")) {
                    Element roleElement = (Element) roleList.item(0);
                    shotCounters = Integer.parseInt(roleElement.getAttribute("number"));
                } else if (nodeName.equals("area")) {
                    cords[0] = Integer.parseInt(roomInfoElement.getAttribute("x"));
                    cords[1] = Integer.parseInt(roomInfoElement.getAttribute("y"));
                }
            }
            
            //Creates a blank trailer/office room with no shotcounters/sceneCard from xml
            if (roomNode.getNodeName().equals("trailer")) {
                name = "trailer";
                i = rooms.length - 2;
            } else if (roomNode.getNodeName().equals("office")) {
                name = "office";
                i = rooms.length - 1;
            } else {
                name = roomElement.getAttribute("name");
            }
            Role[] roleArray = Arrays.copyOf(roles.toArray(), roles.toArray().length, Role[].class);
            String[] neighborsArray = Arrays.copyOf(neighbors.toArray(), neighbors.toArray().length, String[].class);
            rooms[i] = new Room(name, shotCounters, roleArray, neighborsArray, cords);
            roles.clear();
            neighbors.clear();
        }
    }
    
    //Creates "regular" set roomes along with office annd trailer
    public void parseBoard(Room[] rooms) throws Exception {
        parseRooms(getOuterNodes("board.xml", "set"), rooms);
        parseRooms(getOuterNodes("board.xml", "office"), rooms);
        parseRooms(getOuterNodes("board.xml", "trailer"), rooms);

    }
}