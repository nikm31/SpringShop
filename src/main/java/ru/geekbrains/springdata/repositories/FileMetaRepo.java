package ru.geekbrains.springdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.springdata.dto.FileMetaDTO;
import ru.geekbrains.springdata.entity.files.FileMeta;
import ru.geekbrains.springdata.entity.users.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface FileMetaRepo extends JpaRepository<FileMeta, Long> {

    List<FileMeta> findByHashAndUser(UUID hashName, User user);
    List<FileMeta> findByHashAndUserIsNot(UUID hashName, User user);
    List<FileMeta> findByHash(UUID hashName);

    Collection<FileMeta> findBySubType(Long subType);

    FileMeta findByFileNameAndUser(String fileName, User user);

    @Transactional
    void deleteByHashAndUser(UUID hash, User user);

}

