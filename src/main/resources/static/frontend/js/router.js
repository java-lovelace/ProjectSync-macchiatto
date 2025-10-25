// Router hash muy simple
const routes = {
    '#/projects': async () => (await import('./views/projects-list.js')).render(),
    '#/projects/new': async () => (await import('./views/project-form.js')).renderNew(),
    '#/projects/:id': async (id) => (await import('./views/project-detail.js')).render(id),
    '#/projects/:id/edit': async (id) => (await import('./views/project-form.js')).renderEdit(id),
    '#/users': async () => (await import('./views/users-crud.js')).render()
};

// Match tipo “/projects/123/edit”
function matchRoute(hash) {
    if (routes[hash]) return { fn: routes[hash] };

    // dinámicas
    const parts = hash.split('/');
    if (parts.length === 3 && parts[1] === '#') return null;

    // /#/projects/123
    if (/^#\/projects\/\d+$/.test(hash)) {
        const id = hash.split('/')[2];
        return { fn: routes['#/projects/:id'], param: id };
    }
    // /#/projects/123/edit
    if (/^#\/projects\/\d+\/edit$/.test(hash)) {
        const id = hash.split('/')[2];
        return { fn: routes['#/projects/:id/edit'], param: id };
    }
    return null;
}

async function render() {
    const app = document.getElementById('app');
    const hash = location.hash || '#/projects';
    const match = matchRoute(hash);
    try {
        if (match) {
            const html = await match.fn(match.param);
            app.innerHTML = html;
        } else {
            const html = await routes['#/projects']();
            app.innerHTML = html;
        }
    } catch (e) {
        console.error(e);
        app.innerHTML = `<div class="text-red-600">Error rendering route</div>`;
    }
}

window.addEventListener('hashchange', render);
window.addEventListener('DOMContentLoaded', render);
