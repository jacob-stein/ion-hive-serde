/*
 * Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazon.ionhiveserde.objectinspectors.map;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.apache.hadoop.io.IntWritable;

public class IonFieldNameToIntObjectInspector
        extends AbstractFieldNameObjectInspector<Integer>
        implements IntObjectInspector {

    public static final long MIN_VALUE = Integer.MIN_VALUE;
    public static final long MAX_VALUE = Integer.MAX_VALUE;

    public IonFieldNameToIntObjectInspector(final boolean failOnOverflow) {
        super(TypeInfoFactory.intTypeInfo, failOnOverflow);
    }

    @Override
    public int get(final Object o) {
        return (int) getPrimitiveJavaObject(o);
    }

    @Override
    public Object getPrimitiveJavaObject(final Object o) {
        return getPrimitiveJavaObjectFromFieldName(o.toString());
    }

    @Override
    public Object getPrimitiveWritableObject(final Object o) {
        return new IntWritable(getPrimitiveJavaObjectFromFieldName(o.toString()));
    }

    @Override
    protected Integer getValidatedPrimitiveJavaObject(final String fieldName) {
        return Long.valueOf(fieldName).intValue();
    }

    @Override
    protected void validateSize(final String fieldName) {
        try {
            long value = Long.parseLong(fieldName);

            if (!validRange(value)) {
                throw new IllegalArgumentException(
                        "insufficient precision for " + value + " as " + this.typeInfo.getTypeName());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "invalid format for " + fieldName + " as " + this.typeInfo.getTypeName());
        }
    }

    private boolean validRange(final long value) {
        // runs after checking that fits in a Java int
        return MIN_VALUE <= value && value <= MAX_VALUE;
    }
}