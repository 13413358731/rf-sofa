package com.realfinance.sofa.cg.sup.model;

import java.io.Serializable;

public interface ReferenceDto<T extends Serializable> extends Serializable {

    T getId();
}
