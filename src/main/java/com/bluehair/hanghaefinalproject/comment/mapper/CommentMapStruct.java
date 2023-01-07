package com.bluehair.hanghaefinalproject.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapStruct {
    CommentMapStruct COMMENT_MAPPER = Mappers.getMapper(CommentMapStruct.class);

}
