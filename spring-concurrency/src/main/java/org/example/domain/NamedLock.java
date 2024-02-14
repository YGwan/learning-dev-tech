package org.example.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NamedLock {
    @Id
    private Long id;

    public NamedLock() {
    }
}
