/**
 *    Copyright 2012-2017 XebiaLabs B.V.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.xebialabs.overcast.gradle.tasks

import com.xebialabs.overcast.cli.OvercastCli
import groovy.json.JsonOutput
import groovy.json.JsonSlurper;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction

class OvercastTeardown extends DefaultTask {

    @Input
    public File config;

    @Input
    public Map<String, String> handles;


    @TaskAction
    void tearDown() {
        def instances = readInstances()
        getProject().logger.lifecycle("Tearing down instances:" + instances)
        OvercastCli cli = new OvercastCli()
        cli.teardown(instances)
    }

    Map<String, Map<String, String>> readInstances() {
        def instanceFile = new File(getProject().getBuildDir(), "overcast/instances.json")
        if(instanceFile.exists()) {
            return new JsonSlurper().parseText(instanceFile.text)
        } else {
            return [:]
        }
    }


}
