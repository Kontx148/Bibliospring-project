package edu.codespring.bibliospring.backend.model;

import java.io.Serializable;

public abstract class BaseEntity extends AbstractModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    public BaseEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
