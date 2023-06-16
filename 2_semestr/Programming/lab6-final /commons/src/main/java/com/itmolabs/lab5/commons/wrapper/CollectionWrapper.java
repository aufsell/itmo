package com.itmolabs.lab5.commons.wrapper;

import com.itmolabs.lab5.model.ticket.Ticket;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
public class CollectionWrapper {

    protected Set<Ticket> ticketMap;

    public CollectionWrapper(Set<Ticket> collection) {
        this.ticketMap = collection;
    }

    @XmlElement(name = "ticket")
    public Set<Ticket> getTicketMap() {
        return ticketMap;
    }
}
