package com.wells.madhu.batchmodpocwork.helper;

import org.bson.types.ObjectId;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;

public class ObjectIdToIntegerConverter implements Converter<ObjectId, Integer> {

    @Override
    public Integer convert(ObjectId objectId) {
       // ObjectId objectId1 = new ObjectId();
        int intValue = objectId.hashCode();
        return objectId != null ? intValue : null;
    }
}
