package com.realfinance.sofa.flow.model;

import java.io.Serializable;

public interface ReferenceDto<T extends Serializable> extends Serializable {

    T getId();
}
