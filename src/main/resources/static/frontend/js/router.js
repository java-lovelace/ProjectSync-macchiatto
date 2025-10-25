// /frontend/js/router.js
import ProjectsList from './views/projects-list.js';
import ProjectForm from './views/project-form.js';
import ProjectDetail from './views/project-detail.js';
import UsersCrud from './views/users-crud.js';

const routes = [
  { hash: '#/projects', view: ProjectsList },
  { hash: '#/projects/new', view: ProjectForm },
  { hash: '#/projects/:id/edit', view: ProjectForm },
  { hash: '#/projects/:id', view: ProjectDetail },
  { hash: '#/users', view: UsersCrud }
];

function parseHash(hash) {
  // normalize
  if (!hash || hash === '') return '#/projects';
  return hash;
}

function matchRoute(hash) {
  const parts = hash.split('/').filter(Boolean); // e.g. ['projects', '123', 'edit']
  for (const r of routes) {
    const rparts = r.hash.split('/').filter(Boolean);
    if (rparts.length !== parts.length) continue;
    const params = {};
    let ok = true;
    for (let i = 0; i < rparts.length; i++) {
      if (rparts[i].startsWith(':')) {
        params[rparts[i].substring(1)] = parts[i];
      } else if (rparts[i] !== parts[i]) { ok = false; break; }
    }
    if (ok) return { view: r.view, params };
  }
  return null;
}

async function router() {
  const raw = parseHash(location.hash);
  const matched = matchRoute(raw.replace(/^#/, '#/'));
  const app = document.getElementById('app');
  if (!matched) {
    // fallback: projects list
    location.hash = '#/projects';
    return;
  }
  // render view
  app.innerHTML = '<div class="text-center py-12">Cargando...</div>';
  try {
    await matched.view.render(app, matched.params || {});
  } catch (err) {
    app.innerHTML = '<div class="text-red-500">Error cargando la vista</div>';
    console.error(err);
  }
}

window.addEventListener('hashchange', router);
window.addEventListener('load', () => {
  // ensure default route
  if (!location.hash) location.hash = '#/projects';
  router();
});

export default router;
