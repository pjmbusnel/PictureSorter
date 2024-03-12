package com.pjmb.picturesorter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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
        createFolderIfNotExist(targetFolder);
        String targetFile = targetFolder + file.getName();
        boolean isMoved = file.renameTo(new File(targetFile));
        if (!isMoved) {
            System.out.println("Could not move file to " + targetFile);
        }
    }

    private static boolean isExist(String file) {
        return Files.exists(Paths.get(file));
    }

    private static void createFolderIfNotExist(String folder) {
        if (!isExist(folder))
            new File(folder).mkdirs();
    }

    public static void renamePicturesInFolder(String folder, String prefix, int indexStart, boolean randomizeOrder) {
        List<File> pictures = new ArrayList<>();
        pictures.addAll(listPicturesInFolder(folder));
        if (randomizeOrder==true)
            Collections.shuffle(pictures);
        else
            Collections.sort(pictures);
        AtomicInteger i = new AtomicInteger(indexStart);
        pictures.forEach(p -> {
            renameFile(p, prefix + i + getExtension(p.getName()));
            i.getAndIncrement();
        });
    }

    private static String getExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }

    private static void renameFile(File fileSource, String newName) {
        String targetFile = fileSource.getPath().substring(0,fileSource.getPath().length() - fileSource.getName().length()) + newName;
        boolean isRenamed = false;
        // TODO need a temporary rename step, to avoid collision/loss
        boolean isExist = isExist(targetFile);
        if (!isExist) {
            isRenamed = fileSource.renameTo(new File(targetFile));
        }
        if (!isRenamed || isExist) {
            System.out.println("Could not rename file to " + targetFile);
        }
    }
}
