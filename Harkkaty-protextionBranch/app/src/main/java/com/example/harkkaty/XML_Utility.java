package com.example.harkkaty;

import android.provider.DocumentsContract;
import android.util.EventLog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XML_Utility {

    private File file;
    private static XML_Utility xml = new XML_Utility();
    private DateC datec;

    //proxy strings
    private String Time, RAccount, Amount, Message, Entity;

    //string used for creating new xml instance
    String timeString, receiverString, amountString, messageString, entityString, rootString, event, filepath;

    //xml-accociated
    private Document dom;
    private Element ele, rootEle, childEle, accountEle;
    private  DocumentBuilder db;

    private XML_Utility(){
        Time="";
        Amount="";
        ele = null;
        rootEle = null;
        timeString = "Time of event";
        receiverString = "Receiver";
        amountString = "Money";
        messageString = "Message";
        entityString = "The entity";
        rootString = "Events";
        event="Event";
        childEle=null;
        filepath ="events.xml";
        accountEle=null;
    }

    public static XML_Utility getInstance(){return xml;}

    //used to add an event to xml
    public void addEvent(Date time, String receiver, double amount, String message, String entity, String accountID) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            Time = datec.getSimpleDate(time);
            Amount = Double.toString(amount);
            Document document=null;
            db = dbf.newDocumentBuilder();
            file = new File(filepath);

            if(file.createNewFile()){
                document = db.parse(file);
            }else{
                createEventXml(document, accountID);//, Time, receiver, Amount, message, entity);
                document = db.parse(file);
            }

            //checks if account already has an element in teh xml file
            if(document.getElementsByTagName(accountID)==null){
                accountEle = document.createElement(accountID);
            }else{
                accountEle.getElementsByTagName(accountID);
            }
            //gets the already created elements from the document
            rootEle=document.getDocumentElement();
            ele = document.createElement(event);

            //makes a
            childEle = document.createElement(timeString);
            childEle.appendChild(document.createTextNode(Time));
            ele.appendChild(childEle);
            childEle = document.createElement(receiverString);
            childEle.appendChild(document.createTextNode(receiver));
            ele.appendChild(childEle);
            childEle = document.createElement(amountString);
            childEle.appendChild(document.createTextNode(Amount));
            ele.appendChild(childEle);
            childEle = document.createElement(messageString);
            childEle.appendChild(document.createTextNode(message));
            ele.appendChild(childEle);
            childEle = document.createElement(entityString);
            childEle.appendChild(document.createTextNode(entity));

            accountEle.appendChild(ele);
            rootEle.appendChild(accountEle);
            document.appendChild(rootEle);

            saveToDocument(document);

        }catch (ParserConfigurationException e) {

        }catch (Exception e){

        }
    }

    private void saveToDocument(Document document){
       try {

        TransformerFactory transfact = TransformerFactory.newInstance();
        Transformer transformer = transfact.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult result = new StreamResult(filepath);
        transformer.transform(domSource ,result);

       }catch (TransformerException e) {

       }
    }

    //creates new xml.file
    private void createEventXml(Document document, String accountID){//, String time, String receiver, String amount, String message, String entity ){
        document = db.newDocument();
        rootEle = document.createElement(rootString);
        document.appendChild(rootEle);
        TransformerFactory transfact = TransformerFactory.newInstance();
        try {
            Transformer transformer = transfact.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(filepath));
    }

/*
    //gets evetns based on accountID and returns them as a Accountevent list
    public ArrayList<AccountEvents> getEvents(String ID){
        ArrayList<AccountEvents> accEvents = new ArrayList<>();
        AccountEvents accountEvent;
        accEvents=null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc =  builder.parse(filepath);
            doc.getDocumentElement().normalize();
            String proxy;
            NodeList Nlist=doc.getDocumentElement().getElementsByTagName(ID);//
            Node nod = Nlist.item(0);
            Nlist= nod.getChildNodes();//
            //Date date, String Raccount, double Amount, String message, String entity
            for (int i=0; i<Nlist.getLength(); i++){
                Node node=Nlist.item(i);
                accountEvent=new AccountEvents();
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    Time=element.getElementsByTagName(timeString).item(0).getTextContent();
                    RAccount=element.getElementsByTagName(receiverString).item(0).getTextContent();
                    Amount=element.getElementsByTagName(amountString).item(0).getTextContent();
                    Message=element.getElementsByTagName(messageString).item(0).getTextContent();
                    Entity=element.getElementsByTagName(entityString).item(0).getTextContent();
                    accountEvent.AccountEvents(datec.makeIntoDate(Time), RAccount, Double.parseDouble(Amount), Message, Entity);
                    accEvents.add(accountEvent);
                }
            }
            return accEvents;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  accEvents;
    }*/


}
