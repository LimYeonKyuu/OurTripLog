import {
    AppBar,
    Box,
    Button,
    Dialog,
    DialogContent,
    DialogTitle,
    Tab,
    Tabs,
    TextField,
    Toolbar,
    Typography,
} from '@mui/material';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../services/api';
import { useAuth } from '../hooks/useAuth.js';

function Header() {
    const [open, setOpen] = useState(false);
    const [tabIndex, setTabIndex] = useState(0);

    const [id, setId] = useState('');
    const [nickname, setNickname] = useState('');
    const [password, setPassword] = useState('');
    const [passwordCheck, setPasswordCheck] = useState('');
    const [error, setError] = useState('');

    const { login, logout, isLoggedIn } = useAuth();
    const navigate = useNavigate();

    const handleOpen = () => {
        setOpen(true);
        setTabIndex(0);
        resetForm();
    };

    const handleClose = () => {
        setOpen(false);
        resetForm();
    };

    const resetForm = () => {
        setId('');
        setNickname('');
        setPassword('');
        setPasswordCheck('');
        setError('');
    };

    const handleSignup = async (e) => {
        e.preventDefault();

        if (password !== passwordCheck) {
            setError('비밀번호가 일치하지 않습니다.');
            return;
        }

        try {
            await api.post('/api/ourtriplog/auth/signup', {
                id,
                nickname,
                password,
            });

            setTabIndex(0);
            resetForm();
        } catch (err) {
            setError('회원가입 실패');
        }
    };

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const res = await api.post('/api/ourtriplog/auth/signin', {
                id,
                password,
            });

            const { accessToken, refreshToken } = res.data;
            login({ accessToken, refreshToken });
            handleClose();
        } catch (err) {
            setError('로그인 실패');
        }
    };

    return (
        <>
            <AppBar
                position="static"
                color="transparent"
                elevation={0}
                sx={{
                    borderBottom: '1px solid #e0e0e0',
                    backgroundColor: 'white',
                }}
            >
                <Toolbar>
                    {/* 좌측 로고 */}
                    <Box sx={{ flexGrow: 1 }}>
                        <Link to="/" style={{ textDecoration: 'none' }}>
                            <Box sx={{ display: 'flex', alignItems: 'center' }}>
                                <Typography
                                    variant="h6"
                                    component="div"
                                    sx={{ color: 'primary.main', fontWeight: 'bold' }}
                                >
                                    OurTripLog
                                </Typography>
                            </Box>
                        </Link>
                    </Box>

                    {/* 우측 버튼 */}
                    {isLoggedIn ? (
                        <>
                            <Button
                                color="primary"
                                sx={{ textTransform: 'none', mr: 1 }}
                                onClick={() => navigate('/mypage')}
                            >
                                마이페이지
                            </Button>
                            <Button
                                variant="outlined"
                                color="primary"
                                onClick={logout}
                                sx={{ borderRadius: 2, textTransform: 'none' }}
                            >
                                로그아웃
                            </Button>
                        </>
                    ) : (
                        <Button
                            variant="outlined"
                            color="primary"
                            onClick={handleOpen}
                            sx={{ borderRadius: 2, textTransform: 'none' }}
                        >
                            로그인
                        </Button>
                    )}
                </Toolbar>
            </AppBar>

            {/* 로그인/회원가입 팝업 */}
            <Dialog open={open} onClose={handleClose} maxWidth="xs" fullWidth>
                <DialogTitle sx={{ textAlign: 'center', fontWeight: 'bold' }}>
                    OurTripLog
                </DialogTitle>

                <Tabs
                    value={tabIndex}
                    onChange={(e, newValue) => setTabIndex(newValue)}
                    variant="fullWidth"
                    indicatorColor="primary"
                    textColor="primary"
                >
                    <Tab label="로그인" />
                    <Tab label="회원가입" />
                </Tabs>

                <DialogContent>
                    <Box
                        component="form"
                        onSubmit={tabIndex === 0 ? handleLogin : handleSignup}
                        sx={{ mt: 2 }}
                    >
                        <TextField
                            fullWidth
                            margin="normal"
                            label="아이디"
                            value={id}
                            onChange={(e) => setId(e.target.value)}
                            inputProps={{ maxLength: 20 }}
                        />

                        {tabIndex === 1 && (
                            <TextField
                                fullWidth
                                margin="normal"
                                label="닉네임"
                                value={nickname}
                                onChange={(e) => setNickname(e.target.value)}
                                inputProps={{ maxLength: 20 }}
                            />
                        )}

                        <TextField
                            fullWidth
                            margin="normal"
                            label="비밀번호"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            inputProps={{ maxLength: 20 }}
                        />

                        {tabIndex === 1 && (
                            <TextField
                                fullWidth
                                margin="normal"
                                label="비밀번호 확인"
                                type="password"
                                value={passwordCheck}
                                onChange={(e) => setPasswordCheck(e.target.value)}
                                inputProps={{ maxLength: 20 }}
                            />
                        )}

                        {error && (
                            <Typography color="error" sx={{ mt: 1 }}>
                                {error}
                            </Typography>
                        )}

                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            sx={{ mt: 3 }}
                        >
                            {tabIndex === 0 ? '로그인' : '회원가입'}
                        </Button>
                    </Box>
                </DialogContent>
            </Dialog>
        </>
    );
}

export default Header;
