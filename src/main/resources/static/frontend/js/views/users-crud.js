// /frontend/js/views/users-crud.js
import { GET, POST, PUT, DEL } from '../api.js';

const UsersCrud = {
  async render(container) {
    container.innerHTML = `
      <div class="flex justify-between mb-4">
        <h2 class="text-2xl font-semibold">People</h2>
        <button id="btnAddUser" class="bg-blue-600 text-white px-3 py-1 rounded">+ New</button>
      </div>

      <div class="bg-white rounded shadow p-4">
        <table class="w-full">
          <thead class="bg-gray-100">
            <tr>
              <th class="p-2">Full Name</th>
              <th class="p-2">Email</th>
              <th class="p-2">Role</th>
              <th class="p-2">Active</th>
              <th class="p-2">Actions</th>
            </tr>
          </thead>
          <tbody id="usersBody"></tbody>
        </table>
      </div>

      <div id="userFormContainer" class="mt-4"></div>
    `;

    document.getElementById('btnAddUser').addEventListener('click', () => this.showForm());
    await this.loadUsers();
  },

  async loadUsers() {
    try {
      const users = await GET('/users');
      const body = document.getElementById('usersBody');
      if (!users.length) {
        body.innerHTML = `<tr><td colspan="5" class="p-4 text-center text-gray-500">No hay usuarios</td></tr>`;
        return;
      }
      body.innerHTML = users.map(u => `
        <tr class="border-t">
          <td class="p-2">${u.fullName}</td>
          <td class="p-2">${u.email}</td>
          <td class="p-2">${u.role}</td>
          <td class="p-2">${u.active ? 'Yes' : 'No'}</td>
          <td class="p-2 space-x-2">
            <button data-id="${u.id}" class="btn-edit text-yellow-600">Edit</button>
            <button data-id="${u.id}" class="btn-del text-red-600">Delete</button>
          </td>
        </tr>
      `).join('');

      body.querySelectorAll('.btn-edit').forEach(b => b.addEventListener('click', (e) => {
        const id = e.target.dataset.id; this.showForm(id);
      }));
      body.querySelectorAll('.btn-del').forEach(b => b.addEventListener('click', async (e) => {
        const id = e.target.dataset.id;
        if (!confirm('Eliminar usuario?')) return;
        try { await DEL(`/users/${id}`); window.showToast('Usuario eliminado'); await this.loadUsers(); } catch (err) {}
      }));

    } catch (e) {}
  },

  async showForm(id) {
    const c = document.getElementById('userFormContainer');
    c.innerHTML = `
      <div class="bg-white p-4 rounded shadow">
        <h3 class="font-semibold mb-2">${id ? 'Editar' : 'Nuevo'} usuario</h3>
        <form id="userForm" class="space-y-2">
          <div><input id="fullName" placeholder="Full name" class="w-full border rounded px-2 py-1" required></div>
          <div><input id="email" placeholder="Email" class="w-full border rounded px-2 py-1" required></div>
          <div>
            <select id="role" class="w-full border rounded px-2 py-1">
              <option value="PM">PM</option>
              <option value="MEMBER">MEMBER</option>
            </select>
          </div>
          <div class="flex items-center gap-2">
            <input type="checkbox" id="active" />
            <label for="active">Active</label>
          </div>
          <div class="flex gap-2">
            <button class="bg-green-600 text-white px-3 py-1 rounded">Save</button>
            <button id="cancelUser" class="px-3 py-1 rounded border">Cancel</button>
          </div>
        </form>
      </div>
    `;

    if (id) {
      try {
        const u = await GET(`/users/${id}`);
        document.getElementById('fullName').value = u.fullName || '';
        document.getElementById('email').value = u.email || '';
        document.getElementById('role').value = u.role || 'MEMBER';
        document.getElementById('active').checked = !!u.active;
      } catch (err) {}
    }

    document.getElementById('cancelUser').addEventListener('click', (e) => {
      e.preventDefault(); document.getElementById('userFormContainer').innerHTML = '';
    });

    document.getElementById('userForm').addEventListener('submit', async (e) => {
      e.preventDefault();
      const payload = {
        fullName: document.getElementById('fullName').value.trim(),
        email: document.getElementById('email').value.trim(),
        role: document.getElementById('role').value,
        active: document.getElementById('active').checked
      };
      try {
        if (id) { await PUT(`/users/${id}`, payload); window.showToast('Usuario actualizado'); }
        else { await POST('/users', payload); window.showToast('Usuario creado'); }
        document.getElementById('userFormContainer').innerHTML = '';
        await this.loadUsers();
      } catch (err) {}
    });
  }
};

export default UsersCrud;
