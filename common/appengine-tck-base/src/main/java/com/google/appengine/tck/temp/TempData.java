/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.appengine.tck.temp;

import java.io.Serializable;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public interface TempData extends Serializable {
    Map<String, Object> toProperties(DatastoreService ds);
    void fromProperties(Map<String, Object> propeties);

    long getTimestamp();
    void setTimestamp(long timestamp);

    void preGet(DatastoreService ds) throws Exception;
    void postGet(DatastoreService ds) throws Exception;

    void prePut(DatastoreService ds) throws Exception;
    void postPut(DatastoreService ds) throws Exception;

    void preDelete(DatastoreService ds) throws Exception;
    void postDelete(DatastoreService ds) throws Exception;
}
