package com.itmolabs.wrappers;

import com.itmolabs.model.Ticket;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@XmlRootElement
public class CollectionWrapper {

    private Set<Ticket> ticketMap = new HashSet<>();

    public CollectionWrapper() {}

    public CollectionWrapper(Set<Ticket> collection) {
        this.ticketMap = collection;
    }

    @XmlElement(name = "ticket")
    public Set<Ticket> getTicketMap() {
        return ticketMap;
    }

    public void setTicketMap(Set<Ticket> set) {
        this.ticketMap = set;
    }

}
