package ru.eremin.crudHibernateWithoutSpring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.model.dto.UserDTO;


@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO mapToDTO(User user);

    User mapToUser(UserDTO userDTO);
}
