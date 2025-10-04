package com.geekersjoel237.weline.shared.infrastructure.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@MappedSuperclass
@Getter
@Setter
@SoftDelete
@SQLRestriction("deleted_at IS NULL")
public abstract class BaseEntity {

    /**
     * L'identifiant unique de l'entité.
     * Le type est String, comme convenu.
     */
    @Id
    @Column(updatable = false, nullable = false)
    private String id;

    /**
     * La date et l'heure de création de l'enregistrement.
     * Automatiquement rempli par Hibernate lors de la première sauvegarde.
     */
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    /**
     * La date et l'heure de la dernière mise à jour de l'enregistrement.
     * Automatiquement mis à jour par Hibernate à chaque modification.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
