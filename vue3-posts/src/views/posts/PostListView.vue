<template>
  <div>
    <h2>게시글 목록</h2>
    <hr class="my-4" />
    <div class="row g-3">
      <div v-for="post in posts" :key="post.id" class="col-4">
        <PostItem
          :title="post.title"
          :contents="post.contents"
          :createdAt="post.createdAt"
          @click="goPage(post.id)"
        ></PostItem>
      </div>
    </div>
  </div>
</template>

<script setup>
import PostItem from '@/components/posts/PostItem.vue';
import { getPosts } from '@/api/posts';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const posts = ref([]);
const router = useRouter();

const fetchPosts = () => {
  posts.value = getPosts();
};
const goPage = id => {
  //router.push(`/posts/${id}`);
  router.push({
    name: 'PostDetail',
    params: {
      id,
    },
    query: {
      query: 'query',
    },
    hash: '#hash',
  });
};

fetchPosts();
</script>

<style lang="scss" scoped></style>
