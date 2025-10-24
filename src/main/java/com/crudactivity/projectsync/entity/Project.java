package com.crudactivity.projectsync.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name cannot be empty")
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;


    //foreign keys ↓
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @JoinColumn(name = "responsible_user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    //dates
    @CreationTimestamp
    @Column(name = "created_at",  updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;

    @NotNull(message = "Star date cannot be empty")
    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    //only represent how the data is got(Json)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long statusId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long userId;
}
