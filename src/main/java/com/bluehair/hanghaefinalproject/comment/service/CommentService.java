package com.bluehair.hanghaefinalproject.comment.service;


import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentDto;
import com.bluehair.hanghaefinalproject.comment.dto.serviceDto.CommentListDto;
import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.comment.entity.CommentLike;
import com.bluehair.hanghaefinalproject.comment.entity.CommentLikeCompositeKey;
import com.bluehair.hanghaefinalproject.comment.repository.CommentLikeRepository;
import com.bluehair.hanghaefinalproject.comment.repository.CommentRepository;
import com.bluehair.hanghaefinalproject.common.exception.Domain;
import com.bluehair.hanghaefinalproject.common.exception.Layer;
import com.bluehair.hanghaefinalproject.common.exception.NotAuthorizedMemberException;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import com.bluehair.hanghaefinalproject.sse.entity.NotificationType;
import com.bluehair.hanghaefinalproject.sse.service.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void createComment(Long postId,Long parentId, CommentDto commentDto, String nickname) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT, Layer.SERVICE,POST_NOT_FOUND)
        );
        if (parentId != null){
            commentRepository.findById(parentId).orElseThrow(
                    () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,COMMENT_NOT_FOUND)
            );
        }

        Optional<Member> member = memberRepository.findByNickname(nickname);
        Long likeCount = 0L;
        Comment comment = new Comment(parentId, nickname, member.get().getProfileImg(),commentDto.getContents(),likeCount, post);

        commentRepository.save(comment);
        //post 작성자에게 댓글 알림 - 댓글 조회로 이동
        Member postMember = memberRepository.findByNickname(post.getNickname())
                .orElseThrow(() -> new NotFoundException(COMMENT, SERVICE, MEMBER_NOT_FOUND));
        String url = "/api/post/"+postId+"/comment";
        String content = post.getTitle()+"에 "+nickname+"님이 댓글을 남겼습니다.";
        notificationService.send(postMember, NotificationType.COMMENT, content, url);

    }

    public void updateComment(Long commentId, CommentDto commentDto, String nickname) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,COMMENT_NOT_FOUND)
        );
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_FOUND)
        );

        if (!comment.getNickname().equals(member.getNickname())){
             throw new NotAuthorizedMemberException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_AUTHORIZED);
        }

        comment.update(commentDto);

        commentRepository.save(comment);

    }

    public void deleteComment(Long commentId, String nickname) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,COMMENT_NOT_FOUND)
        );
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_FOUND)
        );
        if (!comment.getNickname().equals(member.getNickname())){
            throw new NotAuthorizedMemberException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_AUTHORIZED);
        }

        commentRepository.deleteById(commentId);
    }

    public List<CommentListDto> getComment(Long postId) {

        List<CommentListDto> commentList = commentRepository.findByPostId(postId);

        return commentList;
    }

    public boolean likeComment(Long commentId, String nickname) {

        boolean liked = false;

        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,MEMBER_NOT_FOUND)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(Domain.COMMENT,Layer.SERVICE,COMMENT_NOT_FOUND)
        );

        CommentLikeCompositeKey commentLikeCompositeKey = new CommentLikeCompositeKey(commentId, member.getId());
        Optional<CommentLike> like = commentLikeRepository.findByCommentIdAndMemberId(commentId,member.getId());
        if (like.isPresent()){
            CommentLike commentLike = like.get();
            comment.unlike();
            commentLikeRepository.delete(commentLike);
            return liked = false;
        }else{
            CommentLike commentLike = new CommentLike(commentLikeCompositeKey, member, comment);
            comment.like();
            commentLikeRepository.save(commentLike);
            return liked = true;
        }

    }
}
