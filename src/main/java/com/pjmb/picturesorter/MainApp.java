package com.pjmb.picturesorter;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("Picture Sorter");
        String folder = "/Users/...";
        PicturesSorter.sortPicturesInFolder(folder);
        System.out.println("Picture Sorting Done !!");
    }
}
