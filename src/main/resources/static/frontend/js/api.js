// /frontend/js/api.js
const baseUrl = 'http://localhost:8080/api';

export const api = axios.create({
  baseURL: baseUrl,
  headers: { 'Content-Type': 'application/json' },
  timeout: 10000
});

async function handleError(err) {
  console.error(err);
  const msg = err?.response?.data?.message || err.message || 'Error desconocido';
  window.showToast(msg);
  throw err;
}

export const GET = async (path, params = {}) => {
  try {
    const r = await api.get(path, { params });
    return r.data;
  } catch (e) { return handleError(e); }
};

export const POST = async (path, body) => {
  try {
    const r = await api.post(path, body);
    return r.data;
  } catch (e) { return handleError(e); }
};

export const PUT = async (path, body) => {
  try {
    const r = await api.put(path, body);
    return r.data;
  } catch (e) { return handleError(e); }
};

export const DEL = async (path) => {
  try {
    const r = await api.delete(path);
    return r.data;
  } catch (e) { return handleError(e); }
};

// Util: formatea fecha yyyy-MM-dd HH:mm:ss -> legible
export function formatDateBackend(dateStr) {
  if (!dateStr) return '';
  // Some backends return "yyyy-MM-dd HH:mm:ss" which browser Date can parse if swap T
  const s = dateStr.replace(' ', 'T');
  const d = new Date(s);
  if (isNaN(d)) return dateStr;
  return d.toLocaleDateString('es-CO', { year: 'numeric', month: 'short', day: 'numeric' });
}
