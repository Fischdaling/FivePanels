package org.theShire.repository;

import org.theShire.domain.BaseEntity;
import org.theShire.repository.IRepository;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractRepository<T extends BaseEntity> implements IRepository<T> {

    protected HashMap<UUID, T> entryMap = new HashMap<>();

    @Override
    public T save(T entity) {
        entryMap.put(entity.getEntityId(), entity);
        return entity;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entryMap.values());
    }

    @Override
    public T findByID(UUID id) {
        return entryMap.get(id);
    }

    @Override
    public void deleteById(UUID id) {
        entryMap.remove(id);
    }
    public HashMap<UUID, T> getEntryMap() {
        return entryMap;
    }

    @Override
    public int count() {
        return entryMap.size();
    }

    @Override
    public boolean existsById(UUID id) {
        return entryMap.containsKey(id);
    }

    @Override
    public void deleteAll() {
        entryMap.clear();
    }

    @Override
    public void saveAll(List<T> entities) {
        for (T entity : entities) {
            entryMap.put(entity.getEntityId(), entity);
        }
    }

    public abstract void saveEntryMap(String filepath);
    public abstract void loadEntryMap(String filepath);
}
