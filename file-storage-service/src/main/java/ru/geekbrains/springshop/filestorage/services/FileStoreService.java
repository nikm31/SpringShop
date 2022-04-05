package ru.geekbrains.springshop.filestorage.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.geekbrains.springshop.api.dto.FileMetaDto;
import ru.geekbrains.springshop.api.dto.UserDto;
import ru.geekbrains.springshop.filestorage.converters.FileConverter;
import ru.geekbrains.springshop.filestorage.entity.FileMeta;
import ru.geekbrains.springshop.filestorage.integrations.AuthServiceIntegration;
import ru.geekbrains.springshop.filestorage.repositories.FileMetaRepository;
import ru.geekbrains.springshop.filestorage.utils.FileSystemProvider;
import ru.geekbrains.springshop.filestorage.utils.HashHelper;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStoreService {

	private final FileSystemProvider systemProvider;
	private final FileMetaRepository fileMetaRepository;
	private final AuthServiceIntegration userService;

	@Transactional
	public void removeFileFromUser(String fileName, String userName) throws IOException {
		UserDto user = userService.findByUsername(userName);
		FileMetaDto fileToRemove = new FileMetaDto();

		fileToRemove.setHash(fileMetaRepository.findByFileNameAndUser(fileName, user).getHash());
		fileToRemove.setFileName(fileName);

		if (fileMetaRepository.findByHashAndUserIsNot(fileToRemove.getHash(), user).size() > 0) {
			fileMetaRepository.deleteByHashAndUser(fileToRemove.getHash(), user);
		} else {
			systemProvider.deleteFile(fileToRemove.getHash() + "." + FilenameUtils.getExtension(fileToRemove.getFileName()));
			fileMetaRepository.deleteByHashAndUser(fileToRemove.getHash(), user);
		}
	}

	@Transactional
	public String storeFile(byte[] content, String fileName, Long subFileType, String userName) throws IOException, NoSuchAlgorithmException {
		FileMeta fileMeta = new FileMeta();
		final UUID md5 = HashHelper.getMd5Hash(content);

		fileMeta.setHash(md5);
		fileMeta.setFileName(fileName);
		fileMeta.setSubType(subFileType);
		UserDto user = userService.findByUsername(userName);
		fileMeta.setUser(user.getUsername());

		if (fileMetaRepository.findByHash(md5).size() == 0) {
			systemProvider.storeFile(content, md5, fileName);
		}

		if (fileMetaRepository.findByHashAndUser(md5, user).size() > 0) {
			return "У вас уже есть этот файл";
		}

		fileMetaRepository.save(fileMeta);
		return md5.toString();
	}

	public byte[] getFile(UUID md5) throws IOException {
		FileMeta filename = fileMetaRepository.findByHash(md5).get(0);
		String ext = FilenameUtils.getExtension(filename.getFileName());
		String fullFileName = md5.toString() + "." + ext;
		return systemProvider.getFile(fullFileName);
	}

	public Collection<FileMetaDto> getMetaFiles(Long subtype) {
		FileConverter fileConverter = new FileConverter();
		return (Collection<FileMetaDto>) fileMetaRepository.findBySubType(subtype).stream().map(p -> fileConverter.fileEntityToDto(p));
	}

}
