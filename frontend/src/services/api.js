import axios from 'axios';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    withCredentials: true,
});

// 400 에러 인터셉터 추가
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 400) {
            const { message } = error.response.data;
            // 전역 알림 호출 (예시: window 객체에 콜백 등록 or custom 이벤트)
            window.dispatchEvent(new CustomEvent('show-alert', { detail: message }));
        }
        return Promise.reject(error);
    }
);

export default api;
