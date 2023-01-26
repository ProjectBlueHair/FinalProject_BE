package com.bluehair.hanghaefinalproject.comment.service;


import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentDto;
import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentListDto;
import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.ReplyDto;
import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.comment.repository.CommentRepository;
import com.bluehair.hanghaefinalproject.common.exception.Domain;
import com.bluehair.hanghaefinalproject.common.exception.Layer;
import com.bluehair.hanghaefinalproject.common.exception.NotAuthorizedMemberException;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.like.entity.CommentLike;
import com.bluehair.hanghaefinalproject.like.repository.CommentLikeRepository;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import com.bluehair.hanghaefinalproject.sse.entity.NotificationType;
import com.bluehair.hanghaefinalproject.sse.entity.RedirectionType;
import com.bluehair.hanghaefinalproject.sse.service.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bluehair.hanghaefinalproject.comment.mapper.CommentMapStruct.COMMENT_MAPPER;
import static com.bluehair.hanghaefinalproject.common.exception.Domain.COMMENT;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.POST_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.COMMENT_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.MEMBER_NOT_AUTHORIZED;
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void createComment(Long postId,Long parentId, CommentDto commentDto, String nickname) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT, Layer.SERVICE,POST_NOT_FOUND, "Post ID : " + postId)
        );
        if (parentId != null){
            commentRepository.findById(parentId).orElseThrow(
                    () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,COMMENT_NOT_FOUND, "Parent Comment ID : " + parentId)
            );
        }
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(COMMENT, SERVICE, MEMBER_NOT_FOUND, "Nickname : " + nickname));

        Comment comment = COMMENT_MAPPER.commentDtoToComment(commentDto, member, post, parentId);
        commentRepository.save(comment);

        //post 작성자에게 댓글 알림 - 댓글 조회로 이동
        Member postMember = memberRepository.findByNickname(post.getNickname())
                .orElseThrow(() -> new NotFoundException(COMMENT, SERVICE, MEMBER_NOT_FOUND, "Nickname : " + nickname));

        if(!postMember.getNickname().equals(member.getNickname())) {
            String content = post.getTitle() + "에 " + nickname + "님이 댓글을 남겼습니다.";
            notificationService.send(postMember, member, NotificationType.COMMENT, content, RedirectionType.detail, postId, null);
        }
    }
    @Transactional
    public void updateComment(Long commentId, CommentDto commentDto, String nickname) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,COMMENT_NOT_FOUND, "Comment ID : " + commentId)
        );
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_FOUND, "Nickname : " + nickname)
        );

        if (!comment.getNickname().equals(member.getNickname())){
             throw new NotAuthorizedMemberException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_AUTHORIZED, member.getNickname());
        }

        comment.update(commentDto);
        commentRepository.save(comment);
    }
    @Transactional
    public void deleteComment(Long commentId, String nickname) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,COMMENT_NOT_FOUND, "Comment ID : " + commentId)
        );
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_FOUND, "Nickname : " + nickname)
        );

        if (!comment.getNickname().equals(member.getNickname())){
            throw new NotAuthorizedMemberException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_AUTHORIZED, member.getNickname());
        }

        deleteCommentLike(commentId);
        commentRepository.deleteById(commentId);
    }

    public List<CommentListDto> getComment(Long postId, Member member) {
        List<Comment> notParentsCommentList = commentRepository.findByPostIdAndParentsId(postId, null);

        List<CommentListDto> commentList = new ArrayList<>();
        for (Comment comment : notParentsCommentList){
            boolean commentIsLiked = false;
            if(member != null){
                Optional<CommentLike> commentLiked = commentLikeRepository.findByCommentIdAndMemberId(comment.getId(), member.getId());
                if(commentLiked.isPresent()){
                    commentIsLiked = true;
                }
            }
            Comment comments = commentRepository.findById(comment.getId()).orElseThrow(
                    () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,COMMENT_NOT_FOUND, "Comment ID : " + comment.getId())
            );

            List<Comment> ParentsCommentList = commentRepository.findByParentsId(comments.getId());

            List<ReplyDto> replyList = new ArrayList<>();
            for (Comment c : ParentsCommentList){
                boolean replyIsLiked = false;
                if(member != null) {
                    Optional<CommentLike> replyLiked = commentLikeRepository.findByCommentIdAndMemberId(c.getId(), member.getId());
                    if (replyLiked.isPresent()) {
                        replyIsLiked = true;
                    }
                }
                ReplyDto replyDto = COMMENT_MAPPER.commentToReplyDto(c, replyIsLiked);
                replyList.add(replyDto);
            }
            CommentListDto commentListDto = COMMENT_MAPPER.synthesisCommentAndReplyListToCommentListDto(comment, commentIsLiked, replyList);
            commentList.add(commentListDto);
        }
        return commentList;
    }
    @Transactional
    public void deleteCommentLike(Long commentId){
        commentLikeRepository.deleteByCommentId(commentId);
    }
}
