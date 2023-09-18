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
import java.util.*;

public final class CollectionSerializer {

    public static Map<Integer, Ticket> deserialize(
            final String filepath
    ) {

        try {
            final QName name =
                    new QName("ticket");

            XMLInputFactory factory = XMLInputFactory.newInstance();

            // эти две линии можно было бы перенести в статик, но я пока думаю
            JAXBContext context = JAXBContext.newInstance(Ticket.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            InputStream stream = new FileInputStream(filepath);
            XMLEventReader reader = factory.createXMLEventReader(stream);

            Map<Integer, Ticket> val = new TreeMap<>();

            XMLEvent event;

            while ((event = reader.peek()) != null) {
                if (event.isStartElement() && ((StartElement) event).getName().equals(name)) {
                    Ticket ticket = unmarshaller.unmarshal(reader, Ticket.class)
                            .getValue();

                    System.out.println(ticket);

                    val.put(ticket.getId(), ticket);
                } else reader.next();
            }

            return val;
        } catch (final JAXBException e) {
            e.printStackTrace();
            throw new IllegalStateException("JAXB exception occurred while unmarshalling the XML file!");
        } catch (final XMLStreamException e) {
            throw new IllegalStateException("XML file reading exception");
        } catch (final FileNotFoundException e) {
            throw new NullPointerException(
                    String.format("File not found! (file path = %s)", filepath)
            );
        }
    }

    public static String serialize(
            final Map<Integer, Ticket> collection
    ) {
        Set<Ticket> set = new HashSet<>(collection.values());

        try {
            CollectionWrapper wrapper = new CollectionWrapper(set);

            // эти две линии можно было бы перенести в статик, но я пока думаю
            JAXBContext context = JAXBContext.newInstance(CollectionWrapper.class, Ticket.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            marshaller.marshal(wrapper, writer);

            return writer.toString();
        } catch (final JAXBException e) {
            e.printStackTrace();

            return "";
        }
    }


}