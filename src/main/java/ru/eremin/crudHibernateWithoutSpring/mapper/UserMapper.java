package ru.eremin.crudHibernateWithoutSpring.mapper;

import org.mapstruct.factory.Mappers;
import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.model.dto.UserDTO;


@org.mapstruct.Mapper
public interface UserMapper extends AbstractMapper<User, UserDTO> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Override
    User toEntity(UserDTO userDTO);

    @Override
    UserDTO toDto(User user);
}
