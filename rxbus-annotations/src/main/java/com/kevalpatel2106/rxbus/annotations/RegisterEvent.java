/*
 *  Copyright 2017 Keval Patel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance wit
 *  the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 *   the specific language governing permissions and limitations under the License.
 */

package com.kevalpatel2106.rxbus.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Keval on 10-Jul-17.
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface RegisterEvent {
    String[] EVENT_TAG() default "";

    Class[] EVENT_CLASS() default NONE.class;

    /**
     * Default value for {@link #EVENT_CLASS()}.
     */
    enum NONE {
    }
}