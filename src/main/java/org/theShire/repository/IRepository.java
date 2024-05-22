package org.theShire.repository;

import org.theShire.domain.BaseEntity;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface IRepository<T extends BaseEntity> {

     T save(T entities);
     List<T> findAll(List<T> entities);
     T findByID(UUID id);
     void deleteById(UUID id);
     int count();
     boolean existsById(UUID id);
     void deleteAll();
     void saveAll(List<T> entities);


}
