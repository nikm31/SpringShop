package ru.geekbrains.springshop.filestorage.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public interface FileSystemProvider {

    File getProductImage (String imagePath);

    byte[] getFile(String fileHash) throws IOException;

    String storeFile(byte[] content, UUID md5, String fileName)throws IOException;

    void deleteFile(String fileHash)throws IOException;
}
