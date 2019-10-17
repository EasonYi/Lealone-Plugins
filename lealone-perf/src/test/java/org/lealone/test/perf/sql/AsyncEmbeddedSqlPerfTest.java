/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lealone.test.perf.sql;

import java.util.HashMap;

import org.lealone.server.Scheduler;
import org.lealone.storage.PageOperation;
import org.lealone.storage.PageOperationHandlerFactory;

// -Xms512M -Xmx512M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
public class AsyncEmbeddedSqlPerfTest extends EmbeddedSqlPerfTestBase {

    public static void main(String[] args) throws Exception {
        new AsyncEmbeddedSqlPerfTest().run();
    }

    private Scheduler[] handlers;

    @Override
    protected void testWrite(int loop) {
        // multiThreadsRandomWriteAsync(loop);
        multiThreadsSerialWriteAsync(loop);
    }

    @Override
    protected void testRead(int loop) {
        multiThreadsRandomRead(loop);
        multiThreadsSerialRead(loop);
    }

    @Override
    protected void testConflict(int loop) {
        testConflict(loop, true);
    }

    @Override
    protected void beforeRun() {
        createPageOperationHandlers();
        singleThreadSerialWrite();
        super.beforeRun();
    }

    private void createPageOperationHandlers() {
        handlers = new Scheduler[threadCount];
        HashMap<String, String> config = new HashMap<>();
        for (int i = 0; i < threadCount; i++) {
            handlers[i] = new Scheduler(i, config);
            handlers[i].start();
        }
        PageOperationHandlerFactory.create(null, handlers);
    }

    @Override
    protected Thread getThread(PageOperation pageOperation, int index, int start) {
        Scheduler h = handlers[index];
        h.handlePageOperation(pageOperation);
        return h;
    }

    @Override
    protected Scheduler getScheduler(int index) {
        return handlers[index];
    }
}
