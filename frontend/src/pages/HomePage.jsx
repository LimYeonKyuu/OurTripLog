import { Box, Button, Container, Typography } from '@mui/material';
import TravelExploreIcon from '@mui/icons-material/TravelExplore';

function HomePage() {
    return (
        <>
            <Box
                sx={{
                    minHeight: 'calc(100vh - 64px)', // 헤더 높이 제외
                    background: 'linear-gradient(135deg, #FBF5DE 0%, #ffffff 100%)',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    px: 2,
                }}
            >
                <Container
                    maxWidth="sm"
                    sx={{
                        backgroundColor: 'white',
                        boxShadow: 4,
                        borderRadius: 4,
                        p: 5,
                        textAlign: 'center',
                    }}
                >
                    <Box sx={{ display: 'flex', justifyContent: 'center', mb: 2 }}>
                        <TravelExploreIcon sx={{ fontSize: 48, color: 'primary.main' }} />
                    </Box>

                    <Typography
                        variant="h3"
                        fontWeight="bold"
                        gutterBottom
                        sx={{ color: 'primary.main' }}
                    >
                        OurTripLog
                    </Typography>

                    <Typography
                        variant="body1"
                        color="text.secondary"
                        sx={{ mb: 4 }}
                    >
                        친구들과 함께 여행을 계획하고, 일정을 공유해보세요.
                    </Typography>

                    <Button
                        variant="contained"
                        size="large"
                        sx={{
                            backgroundColor: 'primary.main',
                            color: 'white',
                            borderRadius: 3,
                            px: 4,
                            py: 1.5,
                            '&:hover': {
                                backgroundColor: '#305c8c',
                            },
                        }}
                        onClick={() => {
                            console.log('여행 시작하기 클릭!');
                        }}
                    >
                        여행 시작하기
                    </Button>
                </Container>
            </Box>
        </>
    );
}

export default HomePage;
