package com.itmolabs.lab5.commons.serializers;

import com.itmolabs.lab5.model.ticket.Ticket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public final class TicketSerializer {

    public static String serialize(final Ticket ticket) {
        try {
            JAXBContext context = JAXBContext.newInstance(Ticket.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            marshaller.marshal(ticket, writer);

            return writer.toString();
        } catch (final JAXBException jaxbException) {
            jaxbException.printStackTrace();

            return "";
        }
    }

    public static Ticket deserialize(final String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(Ticket.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader reader = new StringReader(xml);

            return (Ticket) unmarshaller.unmarshal(reader);
        } catch (final JAXBException jaxbException) {
            System.err.println(jaxbException.getMessage());

            return new Ticket();
        }
    }
}