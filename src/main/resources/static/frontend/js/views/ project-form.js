export async function renderNew() {
    return `
    <div>
      <h1 class="text-xl font-semibold">New Project (placeholder)</h1>
      <p class="text-gray-600">Form stub — fill later.</p>
    </div>
  `;
}
export async function renderEdit(id) {
    return `
    <div>
      <h1 class="text-xl font-semibold">Edit Project #${id} (placeholder)</h1>
      <p class="text-gray-600">Form stub — fill later.</p>
    </div>
  `;
}
export default { renderNew, renderEdit };
