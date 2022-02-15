package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.geekbrains.springdata.dto.FileMetaDTO;
import ru.geekbrains.springdata.entity.files.FileMeta;
import ru.geekbrains.springdata.entity.users.User;
import ru.geekbrains.springdata.exceptions.ResourceNotFoundException;
import ru.geekbrains.springdata.repositories.FileMetaRepo;
import ru.geekbrains.springdata.utils.FileSystemProvider;
import ru.geekbrains.springdata.utils.HashHelper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStoreService implements IFileStoreService {

    private final FileSystemProvider systemProvider;
    private final FileMetaRepo fileMetaProvider;
    private final UserService userService;

    public void removeFileFromUser(String fileName, String userName) throws IOException {
        User user = userService.findByUsername(userName).orElseThrow(()-> new ResourceNotFoundException("Юзер не найден: " + userName));
        FileMetaDTO fileToRemove = new FileMetaDTO();
        fileToRemove.setHash(fileMetaProvider.findByFileNameAndUser(fileName, user).getHash());
        fileToRemove.setFileName(fileName);
        if (fileMetaProvider.findByHashAndUserIsNot(fileToRemove.getHash(), user).size() > 0) {
            fileMetaProvider.deleteByHashAndUser(fileToRemove.getHash(), user);
        } else {
            systemProvider.deleteFile(fileToRemove.getHash() + "."
                    + FilenameUtils.getExtension(fileToRemove.getFileName()));
            fileMetaProvider.deleteByHashAndUser(fileToRemove.getHash(), user);
        }
    }

    @Override
    public String storeFile(byte[] content, String fileName, Long subFileType, String userName) throws IOException, NoSuchAlgorithmException {
        FileMeta fileMeta = new FileMeta();
        final UUID md5 = HashHelper.getMd5Hash(content);

        fileMeta.setHash(md5);
        fileMeta.setFileName(fileName);
        fileMeta.setSubType(subFileType);
        User user = userService.findByUsername(userName).orElseThrow(()-> new ResourceNotFoundException("Юзер не найден: " + userName));
        fileMeta.setUser(user);

        if (fileMetaProvider.findByHash(md5).size() == 0) {
            systemProvider.storeFile(content, md5, fileName);
        }

        if (fileMetaProvider.findByHashAndUser(md5, user).size() > 0) {
            return "У вас уже есть этот файл";
        }

        fileMetaProvider.save(fileMeta);
        return md5.toString();
    }

    @Override
    public byte[] getFile(UUID md5) throws IOException {
        FileMeta filename = fileMetaProvider.findByHash(md5).get(0);
        String ext = FilenameUtils.getExtension(filename.getFileName());
        String fullFileName = md5.toString() + "." + ext;
        return systemProvider.getFile(fullFileName);
    }

    @Override
    public Collection<FileMetaDTO> getMetaFiles(Long subtype) {
        return (Collection<FileMetaDTO>) fileMetaProvider.findBySubType(subtype).stream().map(FileMetaDTO::new);
    }

}
