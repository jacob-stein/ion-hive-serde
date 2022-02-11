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

import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.HiveDecimalObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;

public class IonFieldNameToDecimalObjectInspector
        extends AbstractOverflowableFieldNameObjectInspector<String, HiveDecimal>
        implements HiveDecimalObjectInspector {

    public IonFieldNameToDecimalObjectInspector(final boolean failOnOverflow) {
        super(TypeInfoFactory.decimalTypeInfo, failOnOverflow);
    }

    @Override
    public HiveDecimal getPrimitiveJavaObject(final Object o) {
        return getPrimitiveJavaObjectFromIonValue(o.toString());
    }

    @Override
    public HiveDecimalWritable getPrimitiveWritableObject(final Object o) {
        return new HiveDecimalWritable(getPrimitiveJavaObjectFromIonValue(o.toString()));
    }

    @Override
    protected HiveDecimal getValidatedPrimitiveJavaObject(final String fieldName) {
        return HiveDecimal.create(fieldName);
    }

    @Override
    protected void validateSize(final String fieldName) {
        // no-op
    }
}