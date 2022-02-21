package ru.geekbrains.springdata.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.springdata.entity.FileMeta;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FileMetaDto {
     private UUID hash;
     private String fileName;

     public FileMetaDto(FileMeta fileMeta) {
          this.hash = fileMeta.getHash();
          this.fileName = fileMeta.getFileName();
     }
}
