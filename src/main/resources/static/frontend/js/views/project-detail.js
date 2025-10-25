// /frontend/js/views/project-detail.js
import { GET, DEL, formatDateBackend } from '../api.js';

const basePath = '/projects';

const ProjectDetail = {
  async render(container, params = {}) {
    const id = params.id;
    container.innerHTML = `<div class="text-center py-12">Cargando detalles...</div>`;
    try {
      const p = await GET(`/projects/${id}`);
      container.innerHTML = `
        <div class="bg-white p-6 rounded shadow">
          <div class="flex justify-between items-start">
            <div>
              <h2 class="text-2xl font-semibold">${p.name}</h2>
              <p class="text-sm text-gray-500">${p.statusName || ''} · ${p.userName || ''}</p>
            </div>
            <div class="space-x-2">
              <a href="#/projects/${p.id}/edit" class="text-yellow-600">Edit</a>
              <button id="btnDel" class="text-red-600">Delete</button>
            </div>
          </div>

          <div class="mt-4 space-y-3">
            <div><strong>Description:</strong><p>${p.description || '-'}</p></div>
            <div><strong>Start:</strong> ${formatDateBackend(p.startDate)}</div>
            <div><strong>End:</strong> ${formatDateBackend(p.endDate)}</div>
          </div>

          <div class="mt-6">
            <a href="#/projects" class="text-sm text-gray-600">← Volver a la lista</a>
          </div>
        </div>
      `;

      document.getElementById('btnDel').addEventListener('click', async () => {
        if (!confirm('Eliminar proyecto?')) return;
        try {
          await DEL(`${basePath}/${id}`);
          window.showToast('Proyecto eliminado');
          location.hash = '#/projects';
        } catch (e) { /* handled */ }
      });

    } catch (e) { /* handled */ }
  }
};

export default ProjectDetail;
