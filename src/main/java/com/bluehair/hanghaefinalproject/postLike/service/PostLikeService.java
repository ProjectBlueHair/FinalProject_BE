package com.bluehair.hanghaefinalproject.postLike.service;

import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import com.bluehair.hanghaefinalproject.postLike.dto.ResponsePostLikeDto;
import com.bluehair.hanghaefinalproject.postLike.entity.PostLike;
import com.bluehair.hanghaefinalproject.postLike.entity.PostLikeCompositeKey;
import com.bluehair.hanghaefinalproject.postLike.repository.PostLikeRepository;
import com.bluehair.hanghaefinalproject.sse.entity.NotificationType;
import com.bluehair.hanghaefinalproject.sse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bluehair.hanghaefinalproject.common.exception.Domain.COMMENT;
import static com.bluehair.hanghaefinalproject.common.exception.Domain.LIKE;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    public ResponsePostLikeDto postLike(Long postId, Member member){
        Post postliked = postRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException(LIKE, SERVICE, POST_NOT_FOUND)
                );
        PostLikeCompositeKey postLikeCompositeKey
                = new PostLikeCompositeKey(member.getId(), postliked.getId());
        boolean likecheck;

        if(postLikeRepository.findById(postLikeCompositeKey).isPresent()){
            postLikeRepository.deleteById(postLikeCompositeKey);
            postliked.disLike();
            postRepository.save(postliked);
            likecheck = false;

            return new ResponsePostLikeDto(likecheck, postliked.getLikeCount());
        }

        postLikeRepository.save(new PostLike(postLikeCompositeKey, member,postliked));
        likecheck=true;
        postliked.likeCount();
        postRepository.save(postliked);

        Member postMember = memberRepository.findByNickname(postliked.getNickname())
                .orElseThrow(() -> new NotFoundException(COMMENT, SERVICE, MEMBER_NOT_FOUND));
        String url = "/api/post/"+postId+"/comment";
        String content = postliked.getTitle()+"을(를) "+member.getNickname()+"님이 좋아합니다.";
        notificationService.send(postMember, NotificationType.POST_LIKED, content, url);

        return new ResponsePostLikeDto(likecheck, postliked.getLikeCount());

    }
}
