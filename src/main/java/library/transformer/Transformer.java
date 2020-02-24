package library.transformer;

import java.util.Set;
import java.util.stream.Collectors;

public interface Transformer<E, D> {
    E toEntity(D dto);

    D toDto(E entity);


    default Set<E> toEntity(Set<D> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    default Set<D> toDto(Set<E> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toSet());
    }

}