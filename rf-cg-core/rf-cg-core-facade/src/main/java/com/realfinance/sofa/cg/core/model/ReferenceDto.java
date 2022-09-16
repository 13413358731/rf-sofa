package com.realfinance.sofa.cg.core.model;

import java.io.Serializable;

public interface ReferenceDto<T extends Serializable> extends Serializable {

    T getId();
}
