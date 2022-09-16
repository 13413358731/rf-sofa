package com.realfinance.sofa.common.model;

import java.io.Serializable;

/**
 * ID
 * @param <ID>
 */
public interface IdentityObject<ID extends Serializable> {
    ID getId();

    void setId(ID id);
}
