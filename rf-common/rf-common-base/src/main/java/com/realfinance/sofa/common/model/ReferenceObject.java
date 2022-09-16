package com.realfinance.sofa.common.model;

import java.io.Serializable;
import java.util.Objects;

public class ReferenceObject<ID extends Serializable> implements IdentityObject<ID> {
    protected ID id;

    protected ReferenceObject() {
    }

    public ReferenceObject(ID id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }
}
