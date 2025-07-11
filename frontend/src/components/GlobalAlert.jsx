import { Snackbar, Alert } from '@mui/material';
import { useEffect, useState } from 'react';

function GlobalAlert() {
    const [open, setOpen] = useState(false);
    const [message, setMessage] = useState('');

    useEffect(() => {
        const handleAlert = (e) => {
            setMessage(e.detail);
            setOpen(true);
        };

        window.addEventListener('show-alert', handleAlert);
        return () => {
            window.removeEventListener('show-alert', handleAlert);
        };
    }, []);

    return (
        <Snackbar
            open={open}
            autoHideDuration={4000}
            onClose={() => setOpen(false)}
            anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
        >
            <Alert onClose={() => setOpen(false)} severity="error" sx={{ width: '100%' }}>
                {message}
            </Alert>
        </Snackbar>
    );
}

export default GlobalAlert;
