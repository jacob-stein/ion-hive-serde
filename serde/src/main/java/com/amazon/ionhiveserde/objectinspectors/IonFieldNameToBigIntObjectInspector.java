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

package com.amazon.ionhiveserde.objectinspectors;

import org.apache.hadoop.hive.serde2.objectinspector.primitive.IntObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

public class IonFieldNameToBigIntObjectInspector
        extends AbstractOverflowableFieldNameObjectInspector<String, Long>
        implements IntObjectInspector {

    public IonFieldNameToBigIntObjectInspector(final boolean failOnOverflow) {
        super(TypeInfoFactory.longTypeInfo, failOnOverflow);
    }

    @Override
    protected Long getValidatedPrimitiveJavaObject(final String fieldName) {
        return Long.parseLong(fieldName);
    }

    @Override
    protected void validateSize(final String fieldName) {
        try {
            Long longValue = Long.parseLong(fieldName);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "invalid format for " + fieldName + " as " + this.typeInfo.getTypeName());
        }
    }

    @Override
    public int get(final Object o) {
        return (int) getPrimitiveJavaObject(o);
    }

    @Override
    public Object getPrimitiveJavaObject(final Object o) {
        // TODO: Verify if field name can be null
        return Long.parseLong(o.toString());
    }

    @Override
    public Object getPrimitiveWritableObject(final Object o) {
        // TODO: Verify if field name can be null
        return new LongWritable(Long.parseLong(o.toString()));
    }
}