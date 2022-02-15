package ru.geekbrains.springdata.services;

import ru.geekbrains.springdata.dto.FileMetaDTO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.UUID;

public interface IFileStoreService {

    String storeFile(byte[] content, String fileName, Long subFileType, String userName) throws IOException, NoSuchAlgorithmException;

    byte[] getFile(UUID md5) throws IOException;

    Collection<FileMetaDTO> getMetaFiles(Long subtype);

//    void removeFileFromUser(String fileName, Long userId);
}
