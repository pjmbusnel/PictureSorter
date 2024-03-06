package com.pjmb.picturesorter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PicturesSorterTest {


    private String folderPath = "src/test/resources/img/";

    @BeforeAll
    static void setup() {
    }

    @AfterAll
    static void cleanup() {
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
    public void testSortingPictures() {
        PicturesSorter.sortPicturesInFolder(folderPath);

    }

}