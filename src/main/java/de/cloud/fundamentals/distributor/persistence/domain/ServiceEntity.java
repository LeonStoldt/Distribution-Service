package de.cloud.fundamentals.distributor.persistence.domain;

import javax.persistence.*;

@Entity
@Table(name="services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String command;
    private String url;

    public ServiceEntity(String command, String url) {
        this.command = command;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
