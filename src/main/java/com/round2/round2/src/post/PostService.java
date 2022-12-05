package com.round2.round2.src.post;

import com.round2.round2.src.domain.Member;
import com.round2.round2.src.domain.Post;
import com.round2.round2.src.domain.PostCategory;
import com.round2.round2.src.post.model.CreatePostRequest;
import com.round2.round2.src.post.model.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest request) {

        Member member = postRepository.findMemberById(1L);
//        Member member = Member.createMember("이름", "email", "pwd"); //jwt 에서 가져오기
        PostCategory category = postRepository.findCategoryById(request.getCategoryId());
        Post post = Post.createPost(member, category, request);
        try {
            Long id = postRepository.save(post);
            PostResponse postResponse = new PostResponse(id, "게시물 작성 완료");
            return postResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        return postResponse;
    }
}
