/*
 * Copyright (C) 2011 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bel.android.dspmanager.activity;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.SyncFailedException;

public class Utils {
    protected static final String TAG = DSPManager.class.getSimpleName();
    private static final String TAG_READ = TAG + "_Read";
    private static final String TAG_WRITE = TAG + "_Write";

    /**
     * Write a string value to the specified file.
     *
     * @param filename The filename
     * @param value The value
     */
    public static void writeValue(String filename, String value) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(filename), false);
            fos.write(value.getBytes());
            fos.flush();
        } catch (FileNotFoundException ex) {
            Log.w(TAG, "File " + filename + " not found: " + ex);
        } catch (SyncFailedException ex) {
            Log.w(TAG, "File " + filename + " sync failed: " + ex);
        } catch (IOException ex) {
            Log.w(TAG, "IOException trying to sync " + filename + ": " + ex);
        } catch (RuntimeException ex) {
            Log.w(TAG, "RuntimeException while syncing file: ", ex);
        } finally {
            if (fos != null) {
                try {
                    Log.w(TAG_WRITE, "File " + filename + ": " + value);
                    fos.close();
                } catch (IOException ex) {
                    Log.w(TAG, "IOException while closing synced file: ", ex);
                } catch (RuntimeException ex) {
                    Log.w(TAG, "RuntimeException while closing file: ", ex);
                }
            }
        }

    }

    /**
     * Write a string value to the specified file.
     *
     * @param filename The filename
     * @param value The value
     */
    public static void writeValue(String filename, Boolean value) {
        FileOutputStream fos = null;
        String sEnvia;
        try {
            fos = new FileOutputStream(new File(filename), false);
            if (value)
                sEnvia = "1";
            else
                sEnvia = "0";
            fos.write(sEnvia.getBytes());
            fos.flush();
            // fos.getFD().sync();
        } catch (FileNotFoundException ex) {
            Log.w(TAG, "File " + filename + " not found: " + ex);
        } catch (SyncFailedException ex) {
            Log.w(TAG, "File " + filename + " sync failed: " + ex);
        } catch (IOException ex) {
            Log.w(TAG, "IOException trying to sync " + filename + ": " + ex);
        } catch (RuntimeException ex) {
            Log.w(TAG, "RuntimeException while syncing file: ", ex);
        } finally {
            if (fos != null) {
                try {
                    Log.w(TAG_WRITE, "File " + filename + ": " + value);
                    fos.close();
                } catch (IOException ex) {
                    Log.w(TAG, "IOException while closing synced file: ", ex);
                } catch (RuntimeException ex) {
                    Log.w(TAG, "RuntimeException while closing file: ", ex);
                }
            }
        }
    }

    /**
     * Write the "color value" to the specified file. The value is scaled from
     * an integer to an unsigned integer by multiplying by 2.
     *
     * @param filename The filename
     * @param value The value of max value Integer.MAX
     */
    public static void writeColor(String filename, int value) {
        writeValue(filename, String.valueOf((long) value * 2));
    }

    /**
     * Check if the specified file exists.
     *
     * @param filename The filename
     * @return Whether the file exists or not
     */
    public static boolean fileExists(String filename) {
        return new File(filename).exists();
    }

    // Read value from sysfs interface
    public static String readOneLine(String sFile) {
        BufferedReader brBuffer;
        String sLine = null;

        try {
            brBuffer = new BufferedReader(new FileReader(sFile), 512);
            try {
                sLine = brBuffer.readLine();
            } finally {
                Log.w(TAG_READ, "File " + sFile + ": " + sLine);
                brBuffer.close();
            }
        } catch (Exception e) {
            Log.e(TAG_READ, "Exception when reading /sys/ file", e);
        }
        return sLine;
    }
}
