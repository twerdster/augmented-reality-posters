# Augmented Reality Posters

An Android application that uses a poster as an augmented-reality marker to display associated virtual content.

Students: Michael Weiss, Myr Hagay.

Supervision: Aaron Wetzler.

## Status

**Partial original source recovered. Build and run untested.** Ten unchanged text files from the November 2011 final handoff are preserved under `source/`: six student Java additions, two small picture-geometry headers, the Android manifest and platform properties. This is not a complete application.

## Running

The original project used Android/Eclipse, Java plus native C++/JNI, QCAR and OpenGL. `default.properties` targets Android API 11; the manifest sets minimum API 8. The launcher is `com.qualcomm.QCARSamples.ImageTargets.MainScreen`. `ContentGetter` uses the historical Apache HttpClient API to fetch a poster index/images. An exact QCAR/NDK/Eclipse version or verified build command was not recovered.

Included student classes are `AboutDialog`, `ContentGetter`, `MainScreen`, `PosterData`, `PosterOptionsActivity` and `PostersListActivity`. The original package name is preserved for provenance; it does not imply these additions are Qualcomm-authored or that Qualcomm code is included.

The final archive also contained Qualcomm sample files explicitly marked Confidential and Proprietary. Those Java/native sample files, native makefiles, SDK resources/libraries and APKs are excluded. UI assets/resources, poster data, Eclipse workspace/user state and unreviewed reports/presentations are also omitted. The snapshot references omitted sample classes and resources and cannot build standalone. Do not restore the excluded sample/SDK code without separately establishing redistribution rights.

No recovered code or binaries were executed. Included files retain their original bytes; no API credentials or personal identifiers were found in the selected text files during the bounded review.

## Links

- [Project entry on Aaron Wetzler's website](https://twerdster.github.io/#augmented-reality-posters)

## Attribution and Updates

The historical report uses the name variants Miki Weiss and Hagay Myr; website credits above are retained. No blanket license has been added. Student source and each omitted third-party dependency retain their own ownership and terms. Original authors can open an issue to contribute updates or request corrections or removal of their material.

