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

package com.google.appengine.tck.modules;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.appengine.api.modules.ModulesService;
import com.google.appengine.api.modules.ModulesServiceFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.protocol.modules.OperateOnModule;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@RunWith(Arquillian.class)
public class ModulesServiceTest extends ModulesTestBase {
    private ModulesService service;

    @Deployment
    public static EnterpriseArchive getDeployment() {
        WebArchive module1 = getTckSubDeployment(1).addClass(ModulesServiceTest.class);
        WebArchive module2 = getTckSubDeployment(2).addClass(ModulesServiceTest.class);
        return getEarDeployment(module1, module2);
    }

    @Before
    public void setUp() {
        service = ModulesServiceFactory.getModulesService();
    }

    private static Set<String> toSet(String... strings) {
        return new HashSet<>(Arrays.asList(strings));
    }

    @Test
    public void testCurrentModule1() {
        Assert.assertEquals("default", service.getCurrentModule());
    }

    @Test
    @OperateOnModule("m2")
    public void testCurrentModule2() {
        Assert.assertEquals("m2", service.getCurrentModule());
    }

    @Test
    public void testCurrentVersion1() {
        Assert.assertEquals("1", service.getCurrentVersion());
    }

    @Test
    @OperateOnModule("m2")
    public void testCurrentVersion2() {
        Assert.assertEquals("1", service.getCurrentVersion());
    }

    @Test
    public void testCurrentInstanceId1() {
        String module = service.getCurrentModule();
        String version = service.getCurrentVersion();
        String id = service.getCurrentInstanceId();
        Assert.assertNotNull(id);
        String hostname = service.getInstanceHostname(module, version, id);
        Assert.assertNotNull(hostname);
    }

    @Test
    @OperateOnModule("m2")
    public void testCurrentInstanceId2() {
        String version = service.getCurrentVersion();
        String id = service.getCurrentInstanceId();
        Assert.assertNotNull(id);
        String hostname = service.getInstanceHostname("m2", version, id);
        Assert.assertNotNull(hostname);
    }

    @Test
    public void testDefaultVersion1() {
        Assert.assertEquals("1", service.getDefaultVersion("default"));
    }

    @Test
    @OperateOnModule("m2")
    public void testDefaultVersion2() {
        Assert.assertEquals("1", service.getDefaultVersion("m2"));
    }

    @Test
    public void testModules1() {
        Assert.assertEquals(toSet("default", "m2"), service.getModules());
    }

    @Test
    @OperateOnModule("m2")
    public void testModules2() {
        Assert.assertEquals(toSet("default", "m2"), service.getModules());
    }

    @Test
    public void testModuleInstances1() {
        Assert.assertEquals(1L, service.getNumInstances("default", "1"));
    }

    @Test
    @OperateOnModule("m2")
    public void testModuleInstances2() {
        Assert.assertEquals(1L, service.getNumInstances("m2", "1"));
    }

    @Test
    public void testModuleVersions1() {
        Assert.assertEquals(toSet("1"), service.getVersions("m2"));
    }

    @Test
    @OperateOnModule("m2")
    public void testModuleVersions2() {
        Assert.assertEquals(toSet("1"), service.getVersions("default"));
    }

    @Test
    public void testVersionHostname() {
        String hostname = service.getVersionHostname("m2", service.getCurrentVersion());
        Assert.assertNotNull(hostname);
    }
}
