package com.crudactivity.projectsync.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    private String description;


    //foreign keys ↓
    @NotNull
    @Column(name = "status_id")
    @JoinColumn(name = "status_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @NotNull
    @Column(name = "responsible_user_id")
    @JoinColumn(name = "responsible_user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    //dates
    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @NotNull
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    //only represent how the data is got(Json)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long statusId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long userId;
}
