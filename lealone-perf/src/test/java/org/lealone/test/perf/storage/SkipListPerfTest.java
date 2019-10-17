/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lealone.test.perf.storage;

import org.lealone.db.value.ValueInt;
import org.lealone.db.value.ValueString;
import org.lealone.storage.memory.MemoryStorage;
import org.lealone.test.perf.storage.StorageMapPerfTest;

public class SkipListPerfTest extends StorageMapPerfTest {

    public static void main(String[] args) throws Exception {
        new SkipListPerfTest().run();
    }

    @Override
    protected void openMap() {
        if (map == null || map.isClosed()) {
            MemoryStorage ms = new MemoryStorage();
            map = ms.openMap(SkipListPerfTest.class.getSimpleName(), ValueInt.type, ValueString.type, null);
        }
    }
}
