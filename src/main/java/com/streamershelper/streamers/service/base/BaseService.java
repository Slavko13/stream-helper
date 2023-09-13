package com.streamershelper.streamers.service.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {

    public <T> T map(Object source, Class<T> targetClass) {
        T target;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Ошибка при создании экземпляра целевого класса", e);
        }

        for (Field sourceField : source.getClass().getDeclaredFields()) {
            sourceField.setAccessible(true);
            try {
                Field targetField = targetClass.getDeclaredField(sourceField.getName());
                targetField.setAccessible(true);

                Object value = sourceField.get(source);
                targetField.set(target, value);
            } catch (NoSuchFieldException e) {
                // Если поле отсутствует в целевом классе, пропустим его
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Ошибка при копировании свойств", e);
            }
        }

        return target;
    }

    public <T> List<T> mapList(List<?> sources, Class<T> targetClass) {
        List<T> targets = new ArrayList<>();
        for (Object source : sources) {
            targets.add(map(source, targetClass));
        }
        return targets;
    }
}

