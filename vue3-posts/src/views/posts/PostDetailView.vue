<template>
  <div>
    <h2>{{ form.title }}</h2>
    <p>{{ form.contents }}</p>
    <p class="text-muted">{{ form.createdAt }}</p>
    <hr class="my-4" />
    <div class="row g-2">
      <div class="col-auto">
        <button class="btn btn-outline-dark">이전글</button>
      </div>
      <div class="col-auto">
        <button class="btn btn-outline-dark">다음글</button>
      </div>
      <div class="col-auto me-auto"></div>
      <div class="col-auto">
        <button class="btn btn-outline-dark" @click="goListPage">목록</button>
      </div>
      <div class="col-auto">
        <button class="btn btn-outline-primary" @click="goEditPage">
          수정
        </button>
      </div>
      <div class="col-auto">
        <button class="btn btn-outline-danger">삭제</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { getPostById } from '@/api/posts';
import { ref } from 'vue';

const props = defineProps({
  id: String,
});
const router = useRouter();
const form = ref({});
/**
 * ref: 객체 할당 가능, 일관성 유지 가능 but form.value.title, form.value.contents 이런식으로 접근해야함
 * reactive: 객체 할당 불가능 하지만 from.title, form.contents 식으로 접근 가능하다
 */

const fetchPost = () => {
  const data = getPostById(props.id);
  form.value = { ...data }; //객체 복사
};

fetchPost();

const goEditPage = () => {
  router.push({
    name: 'PostEdit',
    params: { id: props.id },
  });
};

const goListPage = () => {
  router.push({
    name: 'PostList',
  });
};
</script>

<style lang="scss" scoped></style>
