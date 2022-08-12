/*
 * Copyright (C) 2017 The Android Open Source Project
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

package app.saltedfish.nfcreader.support;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;

import java.io.File;

/**
 * Implementation of the Typeface compat methods.
 */

public class TypefaceCompatImpl {
    private static final String TAG = "TypefaceCompat";

    /**
     * Used by Resources to load a font resource of type font file.
     */

    public static Typeface createFromResourcesFontFile(
            Context context, Resources resources, int id) {
        final File tmpFile = TypefaceCompatUtil.getTempFile(context);
        if (tmpFile == null) {
            return null;
        }
        try {
            if (!TypefaceCompatUtil.copyToFile(tmpFile, resources, id)) {
                return null;
            }
            return Typeface.createFromFile(tmpFile.getPath());
        } catch (RuntimeException e) {
            // This was thrown from Typeface.createFromFile when a Typeface could not be loaded.
            // such as due to an invalid ttf or unreadable file. We don't want to throw that
            // exception anymore.
            return null;
        } finally {
            tmpFile.delete();
        }
    }
}