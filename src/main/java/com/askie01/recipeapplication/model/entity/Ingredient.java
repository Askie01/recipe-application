package com.askie01.recipeapplication.model.entity;

import com.askie01.recipeapplication.model.common.LocalDateTimeStringAuditable;
import com.askie01.recipeapplication.model.common.LongVersionable;
import com.askie01.recipeapplication.model.common.StringNameable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "ingredient")
public class Ingredient implements
        LongIdEntity,
        StringNameable,
        LocalDateTimeStringAuditable,
        LongVersionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private MeasureUnit measureUnit;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;

    @Version
    private Long version;
}
