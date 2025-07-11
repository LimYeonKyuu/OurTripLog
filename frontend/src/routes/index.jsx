import { createBrowserRouter } from 'react-router-dom';
import HomePage from '../pages/HomePage';
import Header from '../components/Header';
import GlobalAlert from '../components/GlobalAlert';

// 공통 레이아웃
const RootLayout = ({ children }) => (
    <>
        <GlobalAlert />
        <Header />
        {children}
    </>
);

export const router = createBrowserRouter([
    {
        path: '/',
        element: (
            <RootLayout>
                <HomePage />
            </RootLayout>
        ),
    },
    // 다른 페이지도 추가하면 RootLayout 안에 넣으면 됨
]);
