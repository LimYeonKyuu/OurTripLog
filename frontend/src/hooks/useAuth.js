import { create } from 'zustand';

export const useAuth = create((set) => ({
    accessToken: localStorage.getItem('accessToken') || null,
    refreshToken: localStorage.getItem('refreshToken') || null,

    isLoggedIn: !!localStorage.getItem('accessToken'),

    login: ({ accessToken, refreshToken }) => {
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
        set({
            accessToken,
            refreshToken,
            isLoggedIn: true,
        });
    },

    logout: () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        set({
            accessToken: null,
            refreshToken: null,
            isLoggedIn: false,
        });
    },
}));
