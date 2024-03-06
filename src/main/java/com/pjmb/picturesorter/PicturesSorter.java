package com.pjmb.picturesorter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PicturesSorter {

    public static final String PORTRAIT_FOLDER = "portrait";
    public static final String LANDSCAPE_FOLDER = "landscape";

    public static Set<File> listFilesInFolder(String folderPath) {
        Set<File> files = Collections.emptySet();
        try (Stream<Path> stream = Files.list(Paths.get(folderPath))) {
            files = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toFile)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println("Exception listing folder " + folderPath);
            System.out.println(e.getMessage());
        }
        return files;
    }

    public static Set<File> listPicturesInFolder(String folder) {
        return listFilesInFolder(folder).stream()
                .filter(f -> isImage(f))
                .collect(Collectors.toSet());
    }

    private static BufferedImage getBufferedImage(File f) {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Exception opening file " + f.getPath());
            System.out.println(e.getMessage());
        }
        return bimg;
    }

    private static boolean isImage(File f) {
        BufferedImage bimg = getBufferedImage(f);
        return bimg != null ? true : false;
    }

    public static void sortPicturesInFolder(String folder) {
        Set<File> files = listPicturesInFolder(folder);
        files.forEach(img -> {
            BufferedImage bimp = getBufferedImage(img);
            if (bimp.getHeight() >= bimp.getWidth())
                movePictureToFolder(img, PORTRAIT_FOLDER);
            else
                movePictureToFolder(img, LANDSCAPE_FOLDER);
        });
    }

    private static void movePictureToFolder(File file, String folder) {
        String targetFolder = file.getPath().substring(0,file.getPath().lastIndexOf(file.getName())) + folder + File.separator;
        createIfNotExist(targetFolder);
        String targetFile = targetFolder + file.getName();
        boolean isMoved = file.renameTo(new File(targetFile));
        if (!isMoved) {
            System.out.println("Could not move file to " + targetFile);
        }
    }

    private static void createIfNotExist(String folder) {
        if (!Files.exists(Paths.get(folder)))
            new File(folder).mkdirs();
    }
}
