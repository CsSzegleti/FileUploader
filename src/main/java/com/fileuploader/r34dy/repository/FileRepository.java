package com.fileuploader.r34dy.repository;

import com.fileuploader.r34dy.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

}
