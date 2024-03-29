package ru.geekbrains.springdata.utils;


import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
public class FileSystemProvider implements IFileSystemProvider {

	private String storeFolder;
	private Path storePath;
	private String imgFolder;
	private Path imagesPath;

	@PostConstruct
	public void init() {
		storeFolder = "FILE_STORE";
		imgFolder = "img";
		String currentPath = Paths.get("").toAbsolutePath().toString();
		storePath = Paths.get(currentPath, storeFolder);
		imagesPath = Paths.get(currentPath, imgFolder);
	}

	@Override
	public File getProductImage(String imagePath) {
		Path fullImgPath = Paths.get(imagesPath.toString(), imagePath);
		return new File(fullImgPath.toString());
	}

	@Override
	public byte[] getFile(String fileHash) throws IOException {

		try (Stream<Path> walk = Files.walk(storePath)) {
			String fileName = walk.map(Path::toString)
					.filter(f -> f.contains(fileHash))
					.findFirst()
					.orElseThrow(() -> new IOException("file not found"));

			File file = new File(fileName);
			return Files.readAllBytes(file.toPath());
		}
	}

	@Override
	public String storeFile(byte[] content, UUID md5, String fileName) throws IOException {
		String fileNameExtension = FilenameUtils.getExtension(fileName);
		fileName = String.format("%s.%s", md5, fileNameExtension); //  сохраняем файл - меняем название файла на его хеш и длбавляем его расширение

		Path fullFileNamePath = Paths.get(storePath.toString(), fileName);
		String fullFileName = fullFileNamePath.toString();

		if (!Files.exists(fullFileNamePath)) {
			FileUtils.writeByteArrayToFile(new File(fullFileName), content);
		}
		return fullFileName;
	}


	@Override
	public void deleteFile(String fileName) throws IOException {
		Path path = Paths.get(storePath + File.separator + fileName);
		if (Files.exists(path)) {
			Files.delete(path);
		}
	}
}