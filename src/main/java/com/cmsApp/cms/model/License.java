package com.cmsApp.cms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class License implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment the id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Nullable
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "licensesOfContent")
    private List<Content> contentsOfLicense = new ArrayList<>();


    @Column(nullable = false)
    private Long startTime;

    @Column(nullable = false)
    private Long endTime; //= Calendar.getInstance().getTime();

    @Column(nullable = false, updatable = false)
    private String licenseCode;

    //Update only name, startTime and endTime
    public void updateLicense(License license) {
        this.name = license.name;
        this.startTime = license.startTime;
        this.endTime = license.endTime;
    }

    @Override
    public String toString() {
        return "License{" +
                "id= " + id +
                ", name= " + name +
                ", startTime= " + startTime +
                ", endTime= " + endTime +
                "}";
    }
}