package com.pjmb.picturesorter;

import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PicturesSorterTest {


    private static String folderPath = "src/test/resources/img/";

    @AfterEach
    void cleanup(TestInfo info) {
        if(!info.getTags().contains("cleanup")) return;
        if(Files.exists(Paths.get(folderPath + "portrait/portrait.jpg"))) {
            new File(folderPath + "portrait/portrait.jpg").renameTo(new File(folderPath + "portrait.jpg"));
            new File(folderPath + "portrait/").delete();
        }
       if(Files.exists(Paths.get(folderPath + "landscape/landscape.jpg"))) {
           new File(folderPath + "landscape/landscape.jpg").renameTo(new File(folderPath + "landscape.jpg"));
           new File(folderPath + "landscape/").delete();
       }
    }

    @Test
    public void testFolderParsing() {
        Set<File> pictures = PicturesSorter.listFilesInFolder(folderPath);
        assertTrue(pictures.contains(new File(folderPath + "portrait.jpg")));
        assertTrue(pictures.contains(new File(folderPath + "landscape.jpg")));
        assertTrue(pictures.contains(new File(folderPath + "file.txt")));
    }

    @Test
    public void testPicturesFiltering() {
        Set<File> pictures = PicturesSorter.listPicturesInFolder(folderPath);
        assertTrue(pictures.contains(new File(folderPath + "portrait.jpg")));
        assertTrue(pictures.contains(new File(folderPath + "landscape.jpg")));
        assertFalse(pictures.contains(new File(folderPath + "file.txt")));
    }

    @Test
    @Tag("cleanup")
    public void testSortingPictures() {
        PicturesSorter.sortPicturesInFolder(folderPath);
        assertTrue(Files.exists(Paths.get(folderPath + "portrait/portrait.jpg")));
        assertTrue(Files.exists(Paths.get(folderPath + "landscape/landscape.jpg")));
        assertTrue(Files.exists(Paths.get(folderPath + "file.txt")));
    }

}