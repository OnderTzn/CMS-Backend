package com.cmsApp.cms.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
@JsonSerialize
public class Content implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment the id
    @Column(updatable = false) //nullable = false,
    private Long id;

    @Column//nullable = false,
    private String name;

    @Enumerated(EnumType.STRING)
    @Column //nullable = false,
    private Status status = Status.InProgress;

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "license_id")
    )
    private List<License> licensesOfContent = new ArrayList<>();

    @Column
    private String posterUrl;

    @Column
    private String videoUrl;

    @Column
    private String contentCode;

    //Add a license to a content
    public void addLicenseToContent(License license) {
        licensesOfContent.add(license);
    }

    //Delete a license from a content
    public void deleteLicenseFromContent(License license) {
        licensesOfContent.remove(license);
    }

    //Update only name and videoUrl
    public void updateContent(Content content) {
        this.name = content.name;
        this.videoUrl = content.videoUrl;
    }

    public String toString() {
        return "Content{" +
                "id= " + id +
                ", name= " + name +
                ", status= " + status +
                ", posterUrl=" + posterUrl +
                ", videoUrl=" + videoUrl +
                '}';
    }
}