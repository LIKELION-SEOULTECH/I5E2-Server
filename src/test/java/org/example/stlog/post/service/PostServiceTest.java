package org.example.stlog.post.service;

import org.example.stlog.post.entity.Post;
import org.example.stlog.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("게시글 생성")
    void createPost_success(){
        //given
        String username = "seokju";
        String password = "1111";
        String encodedPw = "encoded1";
        String content = "내용내용";

        when(passwordEncoder.encode(password)).thenReturn(encodedPw);

        Post post = Post.builder()
                .username(username)
                .password(encodedPw)
                .content(content)
                .build();

        when(postRepository.save(any(Post.class))).thenReturn(post);

        // when
        Post savedPost = postService.createPost(username, password, content);

        // then
        assertThat(savedPost.getUsername()).isEqualTo(username);
        assertThat(savedPost.getPassword()).isEqualTo(encodedPw);
        verify(postRepository, times(1)).save(any(Post.class));

    }

    @Test
    @DisplayName("게시글 id로 가져왔을때 일치")
    void getPostById(){
        //given
        Post post = Post.builder()
                .username("test")
                .password("pw")
                .content("test content")
                .build();
        post.setEmotion("happy");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // when
        Optional<Post> result = postService.getPostById(1L);

        // then
        assertThat(result).isPresent();
    }


}
