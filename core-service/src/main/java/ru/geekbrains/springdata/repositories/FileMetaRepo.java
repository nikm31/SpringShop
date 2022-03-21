package ru.geekbrains.springdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springdata.entity.FileMeta;
import ru.geekbrains.springshop.api.dto.UserDto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface FileMetaRepo extends JpaRepository<FileMeta, Long> {

    List<FileMeta> findByHashAndUser(UUID hashName, UserDto user);
    List<FileMeta> findByHashAndUserIsNot(UUID hashName, UserDto user);
    List<FileMeta> findByHash(UUID hashName);

    Collection<FileMeta> findBySubType(Long subType);

    FileMeta findByFileNameAndUser(String fileName, UserDto user);

    @Transactional
    void deleteByHashAndUser(UUID hash, UserDto user);

}

