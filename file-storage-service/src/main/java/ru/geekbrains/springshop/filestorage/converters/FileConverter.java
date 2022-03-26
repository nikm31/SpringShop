package ru.geekbrains.springshop.filestorage.converters;

import ru.geekbrains.springshop.api.dto.FileMetaDto;
import ru.geekbrains.springshop.filestorage.entity.FileMeta;

public class FileConverter {
	public FileMetaDto fileEntityToDto(FileMeta fileMeta) {
		FileMetaDto fileMetaDto = new FileMetaDto();
		fileMetaDto.setFileName(fileMeta.getFileName());
		fileMetaDto.setHash(fileMeta.getHash());
		return fileMetaDto;
	}
}
