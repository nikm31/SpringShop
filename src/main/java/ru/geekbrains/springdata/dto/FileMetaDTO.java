package ru.geekbrains.springdata.dto;

import lombok.Data;
import ru.geekbrains.springdata.entity.files.FileMeta;

import java.util.UUID;

@Data
public class FileMetaDTO {
     private UUID hash;
     private String fileName;

     public FileMetaDTO() {
     }

     public FileMetaDTO(FileMeta fileMeta) {
          this.hash = fileMeta.getHash();
          this.fileName = fileMeta.getFileName();
     }
}
