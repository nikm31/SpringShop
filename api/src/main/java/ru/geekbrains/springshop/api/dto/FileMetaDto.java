package ru.geekbrains.springshop.api.dto;


import java.util.UUID;


public class FileMetaDto {
     private UUID hash;
     private String fileName;

//     public FileMetaDto(FileMeta fileMeta) {
//          this.hash = fileMeta.getHash();
//          this.fileName = fileMeta.getFileName();
//     }

     public UUID getHash() {
          return hash;
     }

     public void setHash(UUID hash) {
          this.hash = hash;
     }

     public String getFileName() {
          return fileName;
     }

     public void setFileName(String fileName) {
          this.fileName = fileName;
     }
}
