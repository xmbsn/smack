package org.jivesoftware.jingleaudio.jspeex;

import org.jivesoftware.smackx.jingle.media.JingleMediaManager;
import org.jivesoftware.smackx.jingle.media.JingleMediaSession;
import org.jivesoftware.smackx.jingle.media.PayloadType;
import org.jivesoftware.smackx.jingle.nat.TransportCandidate;

import java.io.File;
import java.io.IOException;

/**
 * $RCSfile$
 * $Revision: $
 * $Date: 25/12/2006
 *
 * Copyright 2003-2006 Jive Software.
 *
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class SpeexMediaManager extends JingleMediaManager {

    public SpeexMediaManager() {
        setupPayloads();
        setupJMF();
    }

    public JingleMediaSession createMediaSession(PayloadType payloadType, final TransportCandidate remote, final TransportCandidate local) {
        return new AudioMediaSession(payloadType, remote, local);
    }

    /**
     * Setup API supported Payloads
     */
    private void setupPayloads() {
        this.addPayloadType(new PayloadType.Audio(15, "speex"));
    }

    /**
     * Runs JMFInit the first time the application is started so that capture
     * devices are properly detected and initialized by JMF.
     */
    public static void setupJMF() {
        try {

            // .jmf is the place where we store the jmf.properties file used
            // by JMF. if the directory does not exist or it does not contain
            // a jmf.properties file. or if the jmf.properties file has 0 length
            // then this is the first time we're running and should continue to
            // with JMFInit
            String homeDir = System.getProperty("user.home");
            File jmfDir = new File(homeDir, ".jmf");
            String classpath = System.getProperty("java.class.path");
            classpath += System.getProperty("path.separator")
                    + jmfDir.getAbsolutePath();
            System.setProperty("java.class.path", classpath);

            if (!jmfDir.exists())
                jmfDir.mkdir();

            File jmfProperties = new File(jmfDir, "jmf.properties");

            if (!jmfProperties.exists()) {
                try {
                    jmfProperties.createNewFile();
                }
                catch (IOException ex) {
                    System.out.println("Failed to create jmf.properties");
                    ex.printStackTrace();
                }
            }

            // if we're running on linux checkout that libjmutil.so is where it
            // should be and put it there.
            runLinuxPreInstall();

            if (jmfProperties.length() == 0) {
                //JMFInit init = new JMFInit(null);
                //init.setVisible(false);
            }

        }
        finally {

        }

    }

    private static void runLinuxPreInstall() {
        // @TODO Implement Linux Pre-Install
    }
}
