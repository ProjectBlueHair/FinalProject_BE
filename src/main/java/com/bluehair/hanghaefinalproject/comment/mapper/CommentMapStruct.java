package com.bluehair.hanghaefinalproject.comment.mapper;

import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentDto;
import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentListDto;
import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.ReplyDto;
import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapStruct {
    CommentMapStruct COMMENT_MAPPER = Mappers.getMapper(CommentMapStruct.class);
    default Comment commentDtoToComment(CommentDto commentDto, Member member, Post post, Long parentId) {
        return Comment.builder()
                .profileImg(member.getProfileImg())
                .nickname(member.getNickname())
                .parentsId(parentId)
                .contents(commentDto.getContents())
                .post(post)
                .likeCount(0L)
                .build();
    }
    ReplyDto commentToReplyDto(Comment comment, Boolean isLiked);
    default CommentListDto synthesisCommentAndReplyListToCommentListDto(Comment comment, Boolean isLiked, List<ReplyDto> replyDtoList) {
        return CommentListDto.builder()
                .comment(comment)
                .replyList(replyDtoList)
                .isLiked(isLiked)
                .build();
    }
}
