package com.itmolabs.serializers;

import com.itmolabs.model.Ticket;
import com.itmolabs.wrappers.CollectionWrapper;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CollectionSerializer {

    public static Map<Integer, Ticket> deserialize(String filepath) {
        Map<Integer, Ticket> collection = new TreeMap<>();
        try {
            final QName qName = new QName("ticket");
            InputStream inputStream = new FileInputStream(filepath);
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            JAXBContext context = JAXBContext.newInstance(Ticket.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XMLEvent e;
            while ((e = xmlEventReader.peek()) != null) {
                if (e.isStartElement() && ((StartElement) e).getName().equals(qName)) {
                    Ticket unmarshalledTicket = unmarshaller.unmarshal(xmlEventReader, Ticket.class).getValue();
                    collection.put(unmarshalledTicket.getId(), unmarshalledTicket);
                } else {
                    xmlEventReader.next();
                }
            }
            return collection;
        } catch (JAXBException jaxbException) {
            System.err.println("XML syntax error");
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(filepath);
            fileNotFoundException.printStackTrace();
            System.err.println("File not found");
        } catch (XMLStreamException xmlStreamException) {
            System.err.println("XML file reading exception");
        }
        return collection;
    }

    public static String serialize(Map<Integer, Ticket> collection) {
        try {
            Set<Ticket> set = new HashSet<>();
            for (Map.Entry<Integer, Ticket> entry : collection.entrySet()) {
                set.add(entry.getValue());
            }
            CollectionWrapper ticketsWrapper = new CollectionWrapper(set);
            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionWrapper.class, Ticket.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(ticketsWrapper, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }


}