package com.round2.round2.src.post;

import com.round2.round2.config.exception.CustomException;
import com.round2.round2.src.domain.Member;
import com.round2.round2.src.domain.Post;
import com.round2.round2.src.domain.PostCategory;
import com.round2.round2.src.domain.Status;
import com.round2.round2.src.post.model.CreatePostRequest;
import com.round2.round2.src.post.model.CreatePostResponse;
import com.round2.round2.src.post.model.PostResponse;
import com.round2.round2.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.round2.round2.config.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JwtService jwtService;


    /**
     * 3.1 인기게시물 API
     */
    public List<Post> findBestPost() throws CustomException {
        return postRepository.findBestPost(); //throw
    }

    /**
     * 3.2 게시물 리스트 API
     */
    public List<Post> findPostList(int category) {
        return postRepository.findPostList(category);
    }


    /**
     * 3.3 게시물 작성 API
     */
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) throws CustomException {
        Member member = postRepository.findMemberById(1L);
//        Member member = Member.createMember("이름", "email", "pwd"); //jwt 에서 가져오기
        PostCategory category = postRepository.findCategoryById(request.getCategoryId()); //throw INVALID_POST_CATEGORY
        Post post = Post.createPost(member, category, request);
        try {
            Long id = postRepository.save(post);
            CreatePostResponse postResponse = new CreatePostResponse(id);
            return postResponse;
        } catch (Exception e) {
            throw new CustomException(INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 3.4 게시물 상세 API
     */
    public Post getPost(Long postId) {
        Post post = postRepository.findPostById(postId); //throw POST_NOT_EXIST
        if (post.getStatus().equals(Status.INACTIVE)) {
            throw new CustomException(DELETED_POST);
        }
        return post;
    }

    public PostResponse getPostResponse(Post post) {
        Long memberIdByJwt = jwtService.getUserIdx();
        boolean isMyPost = false;
        boolean isLiked = false;
        try {
            if (post.getMember().getId() == memberIdByJwt)
                isMyPost = true;
            if (postRepository.checkIsLiked(post.getId(), memberIdByJwt) == true)
                isLiked = true;
        } catch (Exception e) {
            throw new CustomException(DATABASE_ERROR);
        }
        post.updateView(); //조회수 +1
        PostResponse PostResponse = new PostResponse(post, isMyPost, isLiked);
        return PostResponse;
    }


}
