// /frontend/js/views/projects-list.js
import { GET, DEL, formatDateBackend } from '../api.js';

const basePath = '/projects';

const ProjectsList = {
  async render(container) {
    container.innerHTML = `
      <div class="mb-4 flex items-center justify-between">
        <h2 class="text-2xl font-semibold">Projects</h2>
        <div class="flex items-center gap-3">
          <select id="statusFilter" class="border rounded px-2 py-1"></select>
          <input id="searchName" placeholder="Buscar por nombre..." class="border rounded px-2 py-1" />
        </div>
      </div>
      <div class="overflow-x-auto bg-white rounded shadow">
        <table class="min-w-full">
          <thead class="bg-gray-100 text-left">
            <tr>
              <th class="p-3">Name</th>
              <th class="p-3">Status</th>
              <th class="p-3">Responsible</th>
              <th class="p-3">Start</th>
              <th class="p-3">End</th>
              <th class="p-3">Actions</th>
            </tr>
          </thead>
          <tbody id="projectsBody"></tbody>
        </table>
      </div>
    `;

    await this.loadStatuses();
    await this.loadProjects();

    document.getElementById('statusFilter').addEventListener('change', () => this.loadProjects());
    document.getElementById('searchName').addEventListener('input', () => this.loadProjects());
  },

  async loadStatuses() {
    try {
      const statuses = await GET('/status');
      const sel = document.getElementById('statusFilter');
      sel.innerHTML = `<option value="">Todos</option>` +
        statuses.map(s => `<option value="${s.id}">${s.name}</option>`).join('');
    } catch (e) { /* error handler in api */ }
  },

  async loadProjects() {
    try {
      const all = await GET('/projects');
      const sel = document.getElementById('statusFilter').value;
      const q = document.getElementById('searchName').value?.trim().toLowerCase();

      let list = all;
      if (sel) list = list.filter(p => String(p.statusId) === String(sel));
      if (q) list = list.filter(p => p.name?.toLowerCase().includes(q));

      const body = document.getElementById('projectsBody');
      if (!list.length) {
        body.innerHTML = `<tr><td colspan="6" class="p-4 text-center text-gray-500">No hay proyectos</td></tr>`;
        return;
      }

      body.innerHTML = list.map(p => `
        <tr class="border-t hover:bg-gray-50">
          <td class="p-3">${p.name}</td>
          <td class="p-3">${p.statusName || ''}</td>
          <td class="p-3">${p.userName || ''}</td>
          <td class="p-3">${formatDateBackend(p.startDate)}</td>
          <td class="p-3">${formatDateBackend(p.endDate)}</td>
          <td class="p-3 space-x-2">
            <a href="#/projects/${p.id}" class="text-blue-600">View</a>
            <a href="#/projects/${p.id}/edit" class="text-yellow-600">Edit</a>
            <button data-id="${p.id}" class="text-red-600 btn-delete">Delete</button>
          </td>
        </tr>
      `).join('');

      // attach deletes
      body.querySelectorAll('.btn-delete').forEach(btn => {
        btn.addEventListener('click', async (e) => {
          const id = e.target.dataset.id;
          if (!confirm('¿Eliminar proyecto?')) return;
          try {
            await DEL(`${basePath}/${id}`);
            window.showToast('Proyecto eliminado');
            await this.loadProjects();
          } catch (err) { /* handled */ }
        });
      });

    } catch (e) { /* handled in api */ }
  }
};

export default ProjectsList;
