package library.transformer;

import java.util.Set;

public interface Transformer<E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    Set<D> toDto(Set<E> entities);
}
