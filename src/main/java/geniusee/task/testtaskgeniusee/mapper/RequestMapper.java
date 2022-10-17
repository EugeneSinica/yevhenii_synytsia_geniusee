package geniusee.task.testtaskgeniusee.mapper;

public interface RequestMapper<T, K> {
  T toModel(K dto);
}
