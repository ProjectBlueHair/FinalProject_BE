package com.bluehair.hanghaefinalproject.comment.service;


import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentDto;
import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.comment.exception.CommentNotAuthorizedMemberException;
import com.bluehair.hanghaefinalproject.comment.exception.NotFoundCommentRequestException;
import com.bluehair.hanghaefinalproject.comment.repository.CommentRepository;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.exception.NotFoundPostRequestException;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import com.bluehair.hanghaefinalproject.security.exception.InvalidMemberException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.POST_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.COMMENT_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.NOT_AUTHORIZED;
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void createComment(Long postId,Long parentId, CommentDto commentDto, String nickname) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostRequestException(POST_NOT_FOUND)
        );
        if (parentId != null){
            commentRepository.findById(parentId).orElseThrow(
                    () -> new NotFoundCommentRequestException(COMMENT_NOT_FOUND)
            );
        }

        Optional<Member> member = memberRepository.findByNickname(nickname);

        Comment comment = new Comment(parentId, nickname, member.get().getProfileImg(),commentDto.getContents(), post);

        commentRepository.save(comment);

    }

    public void updateComment(Long commentId, CommentDto commentDto, String nickname) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentRequestException(COMMENT_NOT_FOUND)
        );
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new InvalidMemberException(MEMBER_NOT_FOUND)
        );

        if (!comment.getNickname().equals(member.getNickname())){
             throw new CommentNotAuthorizedMemberException(NOT_AUTHORIZED);
        }

        comment.update(commentDto);

        commentRepository.save(comment);

    }
}
