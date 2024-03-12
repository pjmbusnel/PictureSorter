package com.pjmb.picturesorter;

public class MainApp {

    public static void main(String[] args) {
        //String folder = "/Users/...";
        //PicturesSorter.sortPicturesInFolder(folder);
        //System.out.println("Sorting pictures done !");

        String folder = "/Users/...";
        PicturesSorter.renamePicturesInFolder(folder,"img_", 1, true);
        System.out.println("Renaming pictures done !");
    }
}
