// /frontend/js/views/project-form.js
import { GET, POST, PUT } from '../api.js';

const basePath = '/projects';

const ProjectForm = {
  async render(container, params = {}) {
    const id = params.id;
    container.innerHTML = `
      <div class="bg-white p-6 rounded shadow">
        <h2 class="text-xl font-semibold mb-4">${id ? 'Editar proyecto' : 'Nuevo proyecto'}</h2>
        <form id="projectForm" class="space-y-3">
          <div>
            <label class="block text-sm">Name *</label>
            <input required id="name" class="w-full border rounded px-2 py-2" />
          </div>
          <div>
            <label class="block text-sm">Description</label>
            <textarea id="description" class="w-full border rounded px-2 py-2"></textarea>
          </div>
          <div class="grid grid-cols-2 gap-3">
            <div>
              <label class="block text-sm">Status</label>
              <select id="statusId" class="w-full border rounded px-2 py-2"></select>
            </div>
            <div>
              <label class="block text-sm">Responsible (User)</label>
              <select id="userId" class="w-full border rounded px-2 py-2"></select>
            </div>
          </div>
          <div class="grid grid-cols-2 gap-3">
            <div>
              <label class="block text-sm">Start Date</label>
              <input id="startDate" type="datetime-local" class="w-full border rounded px-2 py-2" />
            </div>
            <div>
              <label class="block text-sm">End Date</label>
              <input id="endDate" type="datetime-local" class="w-full border rounded px-2 py-2" />
            </div>
          </div>

          <div class="flex gap-2 mt-4">
            <button type="submit" class="bg-green-600 text-white px-4 py-2 rounded">Guardar</button>
            <a href="#/projects" class="px-4 py-2 rounded border">Cancelar</a>
          </div>
        </form>
      </div>
    `;

    await this.loadSelects();

    if (id) {
      await this.loadProject(id);
    }

    document.getElementById('projectForm').addEventListener('submit', async (e) => {
      e.preventDefault();
      await this.save(id);
    });
  },

  async loadSelects() {
    try {
      const statuses = await GET('/status');
      document.getElementById('statusId').innerHTML = `<option value="">-- Seleccionar --</option>` +
        statuses.map(s => `<option value="${s.id}">${s.name}</option>`).join('');

      const users = await GET('/users');
      document.getElementById('userId').innerHTML = `<option value="">-- Seleccionar --</option>` +
        users.map(u => `<option value="${u.id}">${u.fullName}</option>`).join('');
    } catch (e) { /* handled */ }
  },

  async loadProject(id) {
    try {
      const p = await GET(`/projects/${id}`);
      document.getElementById('name').value = p.name || '';
      document.getElementById('description').value = p.description || '';
      document.getElementById('statusId').value = p.statusId || '';
      document.getElementById('userId').value = p.userId || '';
      // Convert backend "yyyy-MM-dd HH:mm:ss" -> input datetime-local value "yyyy-MM-ddTHH:mm"
      if (p.startDate) document.getElementById('startDate').value = p.startDate.replace(' ', 'T').slice(0,16);
      if (p.endDate) document.getElementById('endDate').value = p.endDate.replace(' ', 'T').slice(0,16);
    } catch (e) { /* handled */ }
  },

  async save(id) {
    try {
      const obj = {
        name: document.getElementById('name').value.trim(),
        description: document.getElementById('description').value.trim(),
        statusId: document.getElementById('statusId').value || null,
        userId: document.getElementById('userId').value || null,
        startDate: this.toBackendDate(document.getElementById('startDate').value),
        endDate: this.toBackendDate(document.getElementById('endDate').value)
      };

      if (!obj.name) { window.showToast('El nombre es requerido'); return; }

      if (id) {
        await PUT(`${basePath}/${id}`, obj);
        window.showToast('Proyecto actualizado');
      } else {
        await POST(basePath, obj);
        window.showToast('Proyecto creado');
      }
      location.hash = '#/projects';
    } catch (err) { /* handled */ }
  },

  // Convert "yyyy-MM-ddTHH:mm" -> "yyyy-MM-dd HH:mm:ss" (backend format)
  toBackendDate(val) {
    if (!val) return null;
    const date = val.replace('T', ' ');
    if (date.length === 16) return date + ':00';
    return date;
  }
};

export default ProjectForm;
