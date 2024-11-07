package com.xpm.messanger.mapper;


import com.xpm.messanger.dto.user.RegisterUserDto;
import com.xpm.messanger.entity.User;
import com.xpm.messanger.exceptions.ServiceException;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User registerToUser(RegisterUserDto userDto) throws ServiceException;

}
