package com.bluehair.hanghaefinalproject.postLike.service;

import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import com.bluehair.hanghaefinalproject.postLike.dto.ResponsePostLikeDto;
import com.bluehair.hanghaefinalproject.postLike.entity.PostLike;
import com.bluehair.hanghaefinalproject.postLike.entity.PostLikeCompositeKey;
import com.bluehair.hanghaefinalproject.postLike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bluehair.hanghaefinalproject.common.exception.Domain.LIKE;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;


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

        return new ResponsePostLikeDto(likecheck, postliked.getLikeCount());

    }
}
