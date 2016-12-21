package net.tjololo.openshift.havoc.connector.kubernetes.contracts.v1;

/**
 * Created by veg on 20.12.2016.
 */
public class Metadata {
    private String name;
    private String selfLink;

    public Metadata() {
    }

    public Metadata(String name, String selfLink) {
        this.name = name;
        this.selfLink = selfLink;
    }

    public String getName() {
        return name;
    }

    public String getSelfLink() {
        return selfLink;
    }
}
