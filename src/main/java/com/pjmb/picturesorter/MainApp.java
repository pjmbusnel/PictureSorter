package com.pjmb.picturesorter;

public class MainApp {

    public static void main(String[] args) {
        String folder = "/Users/...";
        PicturesSorter.sortPicturesInFolder(folder);
        System.out.println("Picture Sorting Done !");
    }
}
