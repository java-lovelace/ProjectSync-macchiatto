
export const baseUrl = 'http://localhost:8080';

export const api = axios.create({
    baseURL: baseUrl,
    headers: { 'Content-Type': 'application/json' }
});
