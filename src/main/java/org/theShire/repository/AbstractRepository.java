package org.theShire.repository;

import org.theShire.domain.BaseEntity;
import org.theShire.repository.IRepository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractRepository<T extends BaseEntity> implements IRepository<T> {

    protected HashMap<UUID, T> entryMap = new HashMap<>();

    @Override
    public T save(T entity) {
        entryMap.put(entity.getEntityId(), entity);
        return entity;
    }

    @Override
    public List<T> findAll(List<T> entities) {
        return entryMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public T findByID(UUID id) {
        return entryMap.get(id);
    }

    @Override
    public void deleteById(UUID id) {
        entryMap.remove(id);
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
}
