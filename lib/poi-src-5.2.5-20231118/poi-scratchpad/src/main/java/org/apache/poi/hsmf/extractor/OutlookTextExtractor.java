/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */
package org.apache.poi.hsmf.extractor;

import static org.apache.poi.util.StringUtil.startsWithIgnoreCase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.poi.extractor.POIOLE2TextExtractor;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.apache.poi.hsmf.datatypes.StringChunk;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.LocaleUtil;

/**
 * A text extractor for HSMF (Outlook) .msg files.
 * Outputs in a format somewhat like a plain text email.
 *
 * @since 4.1.2
 */
public class OutlookTextExtractor implements POIOLE2TextExtractor {
    private final MAPIMessage msg;
    private boolean doCloseFilesystem = true;

    public OutlookTextExtractor(MAPIMessage msg) {
        this.msg = msg;
    }

    public OutlookTextExtractor(DirectoryNode poifsDir) throws IOException {
        this(new MAPIMessage(poifsDir));
    }

    public OutlookTextExtractor(POIFSFileSystem fs) throws IOException {
        this(new MAPIMessage(fs));
    }

    public OutlookTextExtractor(InputStream inp) throws IOException {
        this(new MAPIMessage(inp));
    }

    public static void main(String[] args) throws Exception {
        if(args.length == 0) {
            System.err.println("Usage: OutlookTextExtractor <file> [<file> ...]");
            System.exit(1);
        }
        for (String filename : args) {
            try (POIFSFileSystem poifs = new POIFSFileSystem(new File(filename));
                 OutlookTextExtractor extractor = new OutlookTextExtractor(poifs)) {
                System.out.println(extractor.getText());
            }
        }
    }

    /**
     * Returns the underlying MAPI message
     */
    public MAPIMessage getMAPIMessage() {
        return msg;
    }

    /**
     * Outputs something a little like a RFC822 email
     */
    public String getText() {
        StringBuilder s = new StringBuilder();

        // See if we can get a suitable encoding for any
        //  non unicode text in the file
        msg.guess7BitEncoding();

        // Off we go
        Iterator<String> emails;
        try {
            emails = Arrays.asList(msg.getRecipientEmailAddressList()).iterator();
        } catch (ChunkNotFoundException e) {
            emails = Collections.emptyIterator();
        }

        try {
            s.append("From: ").append(msg.getDisplayFrom()).append("\n");
        } catch (ChunkNotFoundException ignored) {
        }

        // For To, CC and BCC, try to match the names
        //  up with their email addresses. Relies on the
        //  Recipient Chunks being in the same order as
        //  people in To + CC + BCC.
        try {
            handleEmails(s, "To", msg.getDisplayTo(), emails);
        } catch (ChunkNotFoundException ignored) {
        }
        try {
            handleEmails(s, "CC", msg.getDisplayCC(), emails);
        } catch (ChunkNotFoundException ignored) {
        }
        try {
            handleEmails(s, "BCC", msg.getDisplayBCC(), emails);
        } catch (ChunkNotFoundException ignored) {
        }

        // Date - try two ways to find it
        try {
            // First try via the proper chunk
            SimpleDateFormat f = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ROOT);
            f.setTimeZone(LocaleUtil.getUserTimeZone());
            s.append("Date: ").append(f.format(msg.getMessageDate() == null ? new Date(0) : msg.getMessageDate().getTime())).append("\n");
        } catch (ChunkNotFoundException e) {
            try {
                // Failing that try via the raw headers
                String[] headers = msg.getHeaders();
                for (String header : headers) {
                    if (startsWithIgnoreCase(header, "date:")) {
                        s.append("Date:").append(header, header.indexOf(':') + 1, header.length()).append("\n");
                        break;
                    }
                }
            } catch (ChunkNotFoundException he) {
                // We can't find the date, sorry...
            }
        }

        try {
            s.append("Subject: ").append(msg.getSubject()).append("\n");
        } catch (ChunkNotFoundException ignored) {
        }

        // Display attachment names
        // To get the attachments, use ExtractorFactory
        for (AttachmentChunks att : msg.getAttachmentFiles()) {
            StringChunk name = att.getAttachLongFileName();
            if (name == null) name = att.getAttachFileName();
            String attName = name == null ? null : name.getValue();

            if (att.getAttachMimeTag() != null &&
                    att.getAttachMimeTag().getValue() != null) {
                attName = att.getAttachMimeTag().getValue() + " = " + attName;
            }
            s.append("Attachment: ").append(attName).append("\n");
        }

        try {
            s.append("\n").append(msg.getTextBody()).append("\n");
        } catch (ChunkNotFoundException ignored) {
        }

        return s.toString();
    }

    /**
     * Takes a Display focused string, eg "Nick; Jim" and an iterator
     * of emails, and does its best to return something like
     * {@code "Nick <nick@example.com>; Jim <jim@example.com>"}
     */
    protected void handleEmails(StringBuilder s, String type, String displayText, Iterator<String> emails) {
        if (displayText == null || displayText.length() == 0) {
            return;
        }

        String[] names = displayText.split(";\\s*");
        boolean first = true;

        s.append(type).append(": ");
        for (String name : names) {
            if (first) {
                first = false;
            } else {
                s.append("; ");
            }

            s.append(name);
            if (emails.hasNext()) {
                String email = emails.next();
                // Append the email address in <>, assuming
                //  the name wasn't already the email address
                if (!email.equals(name)) {
                    s.append(" <").append(email).append(">");
                }
            }
        }
        s.append("\n");
    }

    @Override
    public MAPIMessage getDocument() {
        return msg;
    }

    @Override
    public void setCloseFilesystem(boolean doCloseFilesystem) {
        this.doCloseFilesystem = doCloseFilesystem;
    }

    @Override
    public boolean isCloseFilesystem() {
        return doCloseFilesystem;
    }

    @Override
    public MAPIMessage getFilesystem() {
        return msg;
    }
}
