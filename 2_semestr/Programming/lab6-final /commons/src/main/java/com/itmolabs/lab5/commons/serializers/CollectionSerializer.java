package com.itmolabs.lab5.commons.serializers;

import com.itmolabs.lab5.model.ticket.Ticket;
import com.itmolabs.lab5.commons.wrapper.CollectionWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class CollectionSerializer {

    public static Map<Integer, Ticket> deserialize(String filepath) {
        Map<Integer, Ticket> collection = new TreeMap<>();

        try {
            QName qName = new QName("ticket");

            InputStream inputStream = new FileInputStream(filepath);
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            JAXBContext context = JAXBContext.newInstance(Ticket.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            XMLEvent event;

            while ((event = xmlEventReader.peek()) != null) {
                if (event.isStartElement() && ((StartElement) event).getName().equals(qName)) {
                    Ticket unmarshalledTicket = unmarshaller.unmarshal(xmlEventReader, Ticket.class).getValue();

                    collection.put(unmarshalledTicket.getId(), unmarshalledTicket);
                } else xmlEventReader.next();
            }

            return collection;
        } catch (final JAXBException e) {
            System.err.println("JAXB exception occurred while unmarshalling the XML file!");
        } catch (final FileNotFoundException e) {
            System.err.printf("File not found! (file path = %s)", filepath);

            e.printStackTrace();
        } catch (final XMLStreamException e) {
            System.err.println("XML file reading exception");
        }

        return collection;
    }

    public static String serialize(Map<Integer, Ticket> collection) {
        Set<Ticket> set = new HashSet<>();

        try {
            collection.forEach((key, value) -> set.add(value));

            CollectionWrapper ticketsWrapper = new CollectionWrapper(set);

            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionWrapper.class, Ticket.class);

            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter stringWriter = new StringWriter();

            marshaller.marshal(ticketsWrapper, stringWriter);
            return stringWriter.toString();
        } catch (final JAXBException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }


}