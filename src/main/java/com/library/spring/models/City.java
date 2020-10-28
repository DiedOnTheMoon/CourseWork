package com.library.spring.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CITY")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String cityName;
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<Publisher> publishers;

    public City() {
    }

    public City(Long id, String cityName, Set<Publisher> publishers) {
        this.id = id;
        this.cityName = cityName;
        this.publishers = publishers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
    }
}
