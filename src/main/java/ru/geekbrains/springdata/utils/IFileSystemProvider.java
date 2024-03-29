package ru.geekbrains.springdata.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public interface IFileSystemProvider {

    File getProductImage (String imagePath);

    byte[] getFile(String fileHash) throws IOException;

    String storeFile(byte[] content, UUID md5, String fileName)throws IOException;

    void deleteFile(String fileHash)throws IOException;
}
