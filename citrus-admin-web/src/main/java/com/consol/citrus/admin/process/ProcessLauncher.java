/*
 * Copyright 2006-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.admin.process;

import com.consol.citrus.admin.process.listener.ProcessListener;
import com.consol.citrus.admin.service.command.TerminalCommand;

/**
 * Launcher for starting a new process. The process is started in a separate thread to the callers. A {@link ProcessListener} can be used for
 * receiving events (such as start, stop, etc) as well as output from the process.
 *
 * @author Martin.Maher@consol.de
 */
public interface ProcessLauncher {

    /**
     * Returns the ID of the process launched
     * @return
     */
    String getProcessId();

    /**
     * Launches a new process. The caller is blocked or waits until the process terminates normally or has exceeded the
     * maxExecutionTime.
     *
     * @param command the terminal command to be executed
     * @param timeout the length of time in seconds to allow the process to complete normally before it is terminated. Setting the value to '0' will cause the process to execute indefinitely.
     * @param processListeners
     */
    void launchAndWait(TerminalCommand command, int timeout, ProcessListener... processListeners);

    /**
     * Launches a new process. The process is launched in the background and control returns immediately to the caller.
     * Through the {@link ProcessListener}) callback mechanism the caller can be informed about the state of the running
     * process.
     *
     * @param command the terminal command to be launched
     * @param timeout the length of time in seconds to allow the process to complete normally before it is terminated. Setting the value to '0' will cause the process to execute indefinitely.
     * @param processListeners
     */
    void launchAndContinue(TerminalCommand command, int timeout, ProcessListener... processListeners);

    /**
     * Stops the running process.
     * No exception is thrown when stop is called on a already stopped process or on a process that has not been started.
     */
    void stop();

    /**
     * Attach command to a running process.
     * @param command
     * @param processListeners
     */
    void attach(TerminalCommand command, ProcessListener ... processListeners);
}
