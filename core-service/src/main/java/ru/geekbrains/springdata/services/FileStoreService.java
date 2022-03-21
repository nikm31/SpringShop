package ru.geekbrains.springdata.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.geekbrains.springdata.dto.FileMetaDto;
import ru.geekbrains.springdata.entity.FileMeta;
import ru.geekbrains.springdata.integrations.AuthServiceIntegration;
import ru.geekbrains.springdata.repositories.FileMetaRepo;
import ru.geekbrains.springdata.utils.HashHelper;
import ru.geekbrains.springdata.utils.IFileSystemProvider;
import ru.geekbrains.springshop.api.dto.UserDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStoreService {

	private final IFileSystemProvider systemProvider;
	private final FileMetaRepo fileMetaProvider;
	private final AuthServiceIntegration userService;

	public byte[] getProductImage(String imagePath) throws IOException {
		File img = systemProvider.getProductImage(imagePath);
		return Files.readAllBytes(img.toPath());
	}

	public void removeFileFromUser(String fileName, String userName) throws IOException {
		UserDto user = userService.findByUsername(userName);
		FileMetaDto fileToRemove = new FileMetaDto();

		fileToRemove.setHash(fileMetaProvider.findByFileNameAndUser(fileName, user).getHash());
		fileToRemove.setFileName(fileName);

		if (fileMetaProvider.findByHashAndUserIsNot(fileToRemove.getHash(), user).size() > 0) {
			fileMetaProvider.deleteByHashAndUser(fileToRemove.getHash(), user);
		} else {
			systemProvider.deleteFile(fileToRemove.getHash() + "." + FilenameUtils.getExtension(fileToRemove.getFileName()));
			fileMetaProvider.deleteByHashAndUser(fileToRemove.getHash(), user);
		}
	}


	public String storeFile(byte[] content, String fileName, Long subFileType, String userName) throws IOException, NoSuchAlgorithmException {
		FileMeta fileMeta = new FileMeta();
		final UUID md5 = HashHelper.getMd5Hash(content);

		fileMeta.setHash(md5);
		fileMeta.setFileName(fileName);
		fileMeta.setSubType(subFileType);
		UserDto user = userService.findByUsername(userName);
		fileMeta.setUser(user.getUsername());

		if (fileMetaProvider.findByHash(md5).size() == 0) {
			systemProvider.storeFile(content, md5, fileName);
		}

		if (fileMetaProvider.findByHashAndUser(md5, user).size() > 0) {
			return "У вас уже есть этот файл";
		}

		fileMetaProvider.save(fileMeta);
		return md5.toString();
	}


	public byte[] getFile(UUID md5) throws IOException {
		FileMeta filename = fileMetaProvider.findByHash(md5).get(0);
		String ext = FilenameUtils.getExtension(filename.getFileName());
		String fullFileName = md5.toString() + "." + ext;
		return systemProvider.getFile(fullFileName);
	}


	public Collection<FileMetaDto> getMetaFiles(Long subtype) {
		return (Collection<FileMetaDto>) fileMetaProvider.findBySubType(subtype).stream().map(FileMetaDto::new);
	}

}
