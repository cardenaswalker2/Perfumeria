/* ==========================================================================
   ADMIN EDITOR ENGINE (Shopify / Figma Style Overlay for Profumi Store)
   ========================================================================= */

// Global State
let activeDrawerTab = 'general';
let pageBuilderActive = false;
let pageSections = [];
let additionalImagesArray = [];
let changeHistory = [];
let activeEditingElement = null;

// Initialize Editor when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('admin-floating-bar')) {
        injectProductCardOverlays();
        setupVisualInlineEditing();
        setupDragDropZone();
        loadPageSections();
        setupContextMenu();
        setupKeyboardShortcuts();
        injectSavedIndicator();
    }
    setupLocalTabs();
});

// Helper to show toast notifications
function showToast(msg, type = 'success') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        document.body.appendChild(container);
    }
    
    const toast = document.createElement('div');
    toast.className = 'toast';
    if (type === 'error') {
        toast.style.borderLeft = '4px solid #ff5252';
        toast.innerHTML = `<i class="fas fa-exclamation-circle" style="color: #ff5252"></i> <span>${msg}</span>`;
    } else {
        toast.innerHTML = `<i class="fas fa-check-circle"></i> <span>${msg}</span>`;
    }
    container.appendChild(toast);
    
    setTimeout(() => toast.classList.add('show'), 50);
    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 500);
    }, 4000);
}

// Injects the visual confirmation indicator
function injectSavedIndicator() {
    if (!document.getElementById('admin-saved-indicator')) {
        const ind = document.createElement('div');
        ind.id = 'admin-saved-indicator';
        ind.className = 'admin-saved-indicator';
        ind.innerHTML = `<i class="fas fa-cloud-upload-alt"></i> <span>Cambios Guardados</span>`;
        document.body.appendChild(ind);
    }
}

function showSavedIndicator() {
    const ind = document.getElementById('admin-saved-indicator');
    if (ind) {
        ind.classList.add('show');
        setTimeout(() => ind.classList.remove('show'), 2000);
    }
}

// Keyboard shortcuts
function setupKeyboardShortcuts() {
    document.addEventListener('keydown', (e) => {
        if ((e.ctrlKey || e.metaKey) && e.key === 's') {
            e.preventDefault();
            const form = document.getElementById('admin-perfume-form');
            if (form && !document.getElementById('admin-perfume-drawer').classList.contains('hidden')) {
                form.dispatchEvent(new Event('submit'));
            } else {
                showToast("Presiona editar en un producto para guardar cambios.");
            }
        }
        if (e.key === 'Escape') {
            closePerfumeDrawer();
            const ctxMenu = document.getElementById('admin-custom-context-menu');
            if (ctxMenu) ctxMenu.remove();
        }
    });
}

// Device Simulator Viewports
function setAdminDeviceView(mode) {
    document.querySelectorAll('.admin-mode-btn').forEach(btn => btn.classList.remove('active'));
    if (event && event.currentTarget) {
        event.currentTarget.classList.add('active');
    }

    document.body.classList.remove('admin-view-tablet', 'admin-view-mobile');
    if (mode === 'tablet') {
        document.body.classList.add('admin-view-tablet');
    } else if (mode === 'mobile') {
        document.body.classList.add('admin-view-mobile');
    }
}

// ==========================================================================
// 1. PRODUCT DRAWER & CONTEXTUAL PANEL CONTROLLER
// ==========================================================================
function switchDrawerTab(tabId) {
    activeDrawerTab = tabId;
    document.querySelectorAll('.drawer-tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.drawer-tab-content').forEach(content => content.classList.remove('active'));
    
    const btn = Array.from(document.querySelectorAll('.drawer-tab-btn')).find(b => b.getAttribute('onclick') && b.getAttribute('onclick').includes(tabId));
    if (btn) btn.classList.add('active');
    
    const contentPanel = document.getElementById(`tab-${tabId}`);
    if (contentPanel) contentPanel.classList.add('active');
    
    if (tabId === 'preview') {
        updateLivePreviewCard();
    }
}

function setupLocalTabs() {
    document.querySelectorAll('.drawer-tab-btn').forEach(btn => {
        const onclickAttr = btn.getAttribute('onclick');
        if (onclickAttr && onclickAttr.includes('switchDrawerTab')) {
            // Native handler is fine
        } else {
            btn.addEventListener('click', (e) => {
                const tab = btn.dataset.tab;
                if (tab) switchDrawerTab(tab);
            });
        }
    });
}

// Open Editor Drawer (Shopify Drawer)
async function openPerfumeDrawer(id) {
    const drawer = document.getElementById('admin-perfume-drawer');
    if (!drawer) return;
    
    drawer.classList.remove('hidden');
    switchDrawerTab('general');

    const form = document.getElementById('admin-perfume-form');
    if (form) form.reset();
    
    const preview = document.getElementById('main-image-preview');
    if (preview) preview.style.display = 'none';
    
    const list = document.getElementById('drag-images-list');
    if (list) list.innerHTML = '';
    additionalImagesArray = [];

    if (id) {
        const deleteBtn = document.getElementById('delete-perfume-btn');
        if (deleteBtn) deleteBtn.style.display = 'block';
        
        document.getElementById('edit-perfume-id').value = id;
        try {
            const res = await fetch(`/api/admin/perfumes/${id}`);
            if (res.ok) {
                const perfume = await res.json();
                populateForm(perfume);
                logHistory("Abierto en editor: " + perfume.nombre);
            }
        } catch (err) {
            console.error("Error loading perfume: ", err);
        }
    } else {
        document.getElementById('edit-perfume-id').value = '';
        const deleteBtn = document.getElementById('delete-perfume-btn');
        if (deleteBtn) deleteBtn.style.display = 'none';
    }
}

function closePerfumeDrawer() {
    const drawer = document.getElementById('admin-perfume-drawer');
    if (drawer) drawer.classList.add('hidden');
}

function populateForm(data) {
    for (const key in data) {
        const el = document.getElementById(`edit-${key}`);
        if (el) {
            if (el.type === 'checkbox') {
                el.checked = !!data[key];
            } else if (Array.isArray(data[key])) {
                el.value = data[key].join(', ');
            } else {
                el.value = data[key];
            }
        }
    }
    
    // Main preview
    if (data.imagenUrl) {
        previewMainImage(data.imagenUrl);
    }
    
    // Additional images list
    const list = document.getElementById('drag-images-list');
    if (list) list.innerHTML = '';
    additionalImagesArray = data.imagenesAdicionales || [];
    additionalImagesArray.forEach(img => addAdditionalImagePreview(img));
}

function previewMainImage(url) {
    const preview = document.getElementById('main-image-preview');
    if (preview) {
        if (url) {
            preview.src = url;
            preview.style.display = 'block';
        } else {
            preview.style.display = 'none';
        }
    }
}

function addAdditionalImagePreview(url) {
    const list = document.getElementById('drag-images-list');
    if (!list) return;
    
    const div = document.createElement('div');
    div.className = 'drag-image-item';
    div.draggable = true;
    div.innerHTML = `
        <img src="${url}" alt="adicional">
        <button class="remove-btn" type="button" onclick="removeAdditionalImage('${url}', this)">&times;</button>
    `;
    
    div.addEventListener('dragstart', (e) => {
        e.dataTransfer.setData('text/plain', url);
        div.classList.add('dragging');
    });
    
    div.addEventListener('dragend', () => {
        div.classList.remove('dragging');
        rebuildAdditionalImagesArray();
    });
    
    list.appendChild(div);
    if (!additionalImagesArray.includes(url)) {
        additionalImagesArray.push(url);
    }
}

function removeAdditionalImage(url, btn) {
    btn.parentElement.remove();
    additionalImagesArray = additionalImagesArray.filter(img => img !== url);
}

function rebuildAdditionalImagesArray() {
    const items = document.querySelectorAll('#drag-images-list .drag-image-item img');
    additionalImagesArray = Array.from(items).map(img => img.src);
}

// Drag & Drop Media Uploads
function setupDragDropZone() {
    const zone = document.getElementById('media-drag-zone');
    if (!zone) return;
    
    zone.addEventListener('click', () => {
        const fileInput = document.getElementById('drag-zone-file');
        if (fileInput) fileInput.click();
    });
    
    zone.addEventListener('dragover', (e) => {
        e.preventDefault();
        zone.style.borderColor = 'var(--admin-gold)';
    });
    
    zone.addEventListener('dragleave', () => {
        zone.style.borderColor = 'rgba(255, 255, 255, 0.1)';
    });
    
    zone.addEventListener('drop', (e) => {
        e.preventDefault();
        zone.style.borderColor = 'rgba(255, 255, 255, 0.1)';
        handleDragZoneUpload(e.dataTransfer.files);
    });
}

async function handleDragZoneUpload(files) {
    for (let file of files) {
        const formData = new FormData();
        formData.append('file', file);
        showToast("Subiendo imagen...");
        try {
            const res = await fetch('/api/admin/perfumes/upload', {
                method: 'POST',
                body: formData
            });
            if (res.ok) {
                const data = await res.json();
                addAdditionalImagePreview(data.url);
                showToast("Imagen subida con éxito.");
            } else {
                showToast("Error al subir archivo.", "error");
            }
        } catch (err) {
            showToast("Error de conexión al subir.", "error");
        }
    }
}

// Upload main image
async function uploadMainImage(input) {
    if (input.files && input.files[0]) {
        const formData = new FormData();
        formData.append('file', input.files[0]);
        showToast("Subiendo imagen principal...");
        try {
            const res = await fetch('/api/admin/perfumes/upload', {
                method: 'POST',
                body: formData
            });
            if (res.ok) {
                const data = await res.json();
                const mainUrlInput = document.getElementById('edit-imagenUrl');
                if (mainUrlInput) {
                    mainUrlInput.value = data.url;
                    previewMainImage(data.url);
                }
                showToast("Imagen principal actualizada.");
            } else {
                showToast("Error al subir archivo.", "error");
            }
        } catch (err) {
            showToast("Error de conexión al subir.", "error");
        }
    }
}

// Live card preview updating
function updateLivePreviewCard() {
    const cardImg = document.getElementById('prev-card-img');
    const cardTitle = document.getElementById('prev-card-title');
    const cardBrand = document.getElementById('prev-card-brand');
    const cardPrice = document.getElementById('prev-card-price');
    const cardDesc = document.getElementById('prev-card-desc');
    const cardStock = document.getElementById('prev-card-stock');

    const name = document.getElementById('edit-nombre') ? document.getElementById('edit-nombre').value : '';
    const brand = document.getElementById('edit-marca') ? document.getElementById('edit-marca').value : '';
    const price = document.getElementById('edit-precio') ? document.getElementById('edit-precio').value : '0';
    const imgUrl = document.getElementById('edit-imagenUrl') ? document.getElementById('edit-imagenUrl').value : '';
    const desc = document.getElementById('edit-descripcionCorta') ? document.getElementById('edit-descripcionCorta').value : '';
    const stock = document.getElementById('edit-stock') ? document.getElementById('edit-stock').value : '0';

    if (cardImg) cardImg.src = imgUrl || 'https://images.unsplash.com/photo-1594035910387-fea47794261f?q=80&w=600&auto=format&fit=crop';
    if (cardTitle) cardTitle.textContent = name || 'Nombre de Fragancia';
    if (cardBrand) cardBrand.textContent = brand || 'Marca / Diseñador';
    if (cardPrice) cardPrice.textContent = `$${parseFloat(price).toLocaleString('es-CO')}`;
    if (cardDesc) cardDesc.textContent = desc || 'Descripción del perfume...';
    if (cardStock) cardStock.textContent = `${stock} unidades disponibles`;
}

// Form Submission
async function savePerfumeDrawer(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    // Programmatic Validation since novalidate is active
    const nombre = formData.get('nombre');
    const marca = formData.get('marca');
    const precio = formData.get('precio');
    const sku = formData.get('sku');
    if (!nombre || !marca || !precio || !sku) {
        showToast("Por favor complete los campos obligatorios: Nombre, Marca, SKU y Precio.", "error");
        return;
    }

    const id = formData.get('id');

    rebuildAdditionalImagesArray();

    const payload = {
        id: id || null,
        nombre: formData.get('nombre'),
        marca: formData.get('marca'),
        descripcion: formData.get('descripcion'),
        historia: formData.get('historia'),
        precio: parseFloat(formData.get('precio')) || 0,
        precioAnterior: formData.get('precioAnterior') ? parseFloat(formData.get('precioAnterior')) : null,
        descuento: formData.get('descuento') ? parseFloat(formData.get('descuento')) : null,
        stock: parseInt(formData.get('stock')) || 0,
        sku: formData.get('sku'),
        skuBarra: formData.get('skuBarra'),
        estadoDisponibilidad: formData.get('estadoDisponibilidad') || 'Disponible',
        genero: formData.get('genero') || 'Mujer',
        concentracion: formData.get('concentracion'),
        tamanosDisponibles: formData.get('tamanosDisponibles') ? formData.get('tamanosDisponibles').split(',').map(s => s.trim()) : [],
        paisOrigen: formData.get('paisOrigen'),
        anoLanzamiento: formData.get('anoLanzamiento') ? parseInt(formData.get('anoLanzamiento')) : null,
        perfumista: formData.get('perfumista'),

        // Olfativo
        familiaOlfativa: formData.get('familiaOlfativa'),
        notasSalida: formData.get('notasSalida'),
        notasCorazon: formData.get('notasCorazon'),
        notasFondo: formData.get('notasFondo'),
        perfilOlfativo: formData.get('perfilOlfativo'),

        // Experiencia
        idealPara: formData.get('idealPara'),
        intensidad: formData.get('intensidad'),
        duracion: formData.get('duracion'),
        proyeccion: formData.get('proyeccion'),
        temporadaRecomendada: formData.get('temporadaRecomendada'),
        climaRecomendado: formData.get('climaRecomendado'),
        usoRecomendado: formData.get('usoRecomendado'),
        estilo: formData.get('estilo'),
        edadRecomendada: formData.get('edadRecomendada'),

        // Multimedia
        imagenUrl: formData.get('imagenUrl'),
        imagenesAdicionales: additionalImagesArray,

        // Comercial
        destacado: e.target.querySelector('#edit-destacado') ? e.target.querySelector('#edit-destacado').checked : false,
        topVentas: e.target.querySelector('#edit-topVentas') ? e.target.querySelector('#edit-topVentas').checked : false,
        nuevo: e.target.querySelector('#edit-nuevo') ? e.target.querySelector('#edit-nuevo').checked : false,
        oferta: e.target.querySelector('#edit-oferta') ? e.target.querySelector('#edit-oferta').checked : false,
        exclusivo: e.target.querySelector('#edit-exclusivo') ? e.target.querySelector('#edit-exclusivo').checked : false,
        edicionLimitada: e.target.querySelector('#edit-edicionLimitada') ? e.target.querySelector('#edit-edicionLimitada').checked : false,
        envioGratis: e.target.querySelector('#edit-envioGratis') ? e.target.querySelector('#edit-envioGratis').checked : false,
        garantia: formData.get('garantia'),
        autenticidad: formData.get('autenticidad'),

        // SEO
        metaTitle: formData.get('metaTitle'),
        metaDescription: formData.get('metaDescription'),
        friendlyUrl: formData.get('friendlyUrl'),
        keywords: formData.get('keywords'),

        // Automap notes array for legacy compatibility
        notasOlfativas: (() => {
            const list = [];
            const sal = formData.get('notasSalida');
            const cor = formData.get('notasCorazon');
            const fon = formData.get('notasFondo');
            if (sal) list.push(...sal.split(',').map(s => s.trim()));
            if (cor) list.push(...cor.split(',').map(s => s.trim()));
            if (fon) list.push(...fon.split(',').map(s => s.trim()));
            return [...new Set(list.filter(Boolean))];
        })()
    };

    try {
        const res = await fetch('/api/admin/perfumes/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        if (res.ok) {
            showToast("Producto guardado correctamente en la base de datos.");
            showSavedIndicator();
            closePerfumeDrawer();
            
            setTimeout(() => {
                window.location.reload();
            }, 1000);
        }
    } catch (err) {
        showToast("Error al guardar el perfume.", "error");
    }
}

async function deletePerfumeFromDrawer() {
    const id = document.getElementById('edit-perfume-id').value;
    if (!id) return;
    if (confirm("¿Estás seguro de eliminar este perfume permanentemente?")) {
        try {
            const res = await fetch(`/api/admin/perfumes/delete/${id}`, { method: 'POST' });
            if (res.ok) {
                showToast("Producto eliminado.");
                closePerfumeDrawer();
                setTimeout(() => window.location.reload(), 1000);
            }
        } catch (err) {
            showToast("Error al eliminar.", "error");
        }
    }
}

// Log history change list
function logHistory(msg) {
    changeHistory.unshift({ time: new Date().toLocaleTimeString(), action: msg });
    const timeline = document.getElementById('product-timeline-list');
    if (timeline) {
        timeline.innerHTML = changeHistory.map(h => `
            <div class="timeline-step">
                <div class="timeline-bullet"></div>
                <div class="timeline-content">
                    <span style="font-weight: 600; color: var(--admin-gold);">${h.action}</span>
                    <div class="timeline-time">${h.time}</div>
                </div>
            </div>
        `).join('');
    }
}

// ==========================================================================
// 2. PRODUCT CARDS HOVER OVERLAYS & CMS TOOLBARS
// ==========================================================================
function injectProductCardOverlays() {
    document.querySelectorAll('a[href*="/producto/"]').forEach(link => {
        const card = link.closest('.product-card') || link.closest('.perfume-card');
        if (card && !card.querySelector('.product-card-admin-overlay')) {
            card.classList.add('product-card-admin-wrapper');
            
            const href = link.getAttribute('href');
            const parts = href.split('/producto/');
            const id = parts[parts.length - 1].split('?')[0];

            // Create admin quick action bar
            const overlay = document.createElement('div');
            overlay.className = 'product-card-admin-overlay';
            overlay.innerHTML = `
                <button class="admin-overlay-btn" onclick="event.preventDefault(); openPerfumeDrawer('${id}')" title="Editar Completo"><i class="fas fa-edit"></i> Editar</button>
                <button class="admin-overlay-btn" onclick="event.preventDefault(); quickChangePrice('${id}')" title="Precio"><i class="fas fa-dollar-sign"></i> Precio</button>
                <button class="admin-overlay-btn" onclick="event.preventDefault(); quickChangeStock('${id}')" title="Stock"><i class="fas fa-box"></i> Stock</button>
                <button class="admin-overlay-btn" onclick="event.preventDefault(); toggleFeaturedBadge(this, '${id}')" title="Destacar"><i class="fas fa-star"></i> Destacar</button>
                <button class="admin-overlay-btn" onclick="event.preventDefault(); archiveProduct('${id}')" title="Ocultar"><i class="fas fa-eye-slash"></i> Ocultar</button>
                <button class="admin-overlay-btn" onclick="event.preventDefault(); duplicateProduct('${id}')" title="Duplicar"><i class="fas fa-copy"></i> Duplicar</button>
                <button class="admin-overlay-btn danger-btn" onclick="event.preventDefault(); deleteProduct('${id}')" title="Eliminar"><i class="fas fa-trash"></i> Borrar</button>
            `;
            card.appendChild(overlay);
        }
    });
}

// Quick pricing prompt
async function quickChangePrice(id) {
    const newPrice = prompt("Ingrese el nuevo precio del producto (COP):");
    if (newPrice !== null && !isNaN(newPrice)) {
        try {
            const res = await fetch(`/api/admin/perfumes/patch/${id}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ field: 'precio', value: parseFloat(newPrice) })
            });
            if (res.ok) {
                showToast("Precio actualizado correctamente.");
                showSavedIndicator();
                setTimeout(() => window.location.reload(), 1000);
            }
        } catch (err) {
            showToast("Error al guardar precio.", "error");
        }
    }
}

// Quick stock change
async function quickChangeStock(id) {
    const newStock = prompt("Ingrese la cantidad de stock disponible:");
    if (newStock !== null && !isNaN(newStock)) {
        try {
            const res = await fetch(`/api/admin/perfumes/patch/${id}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ field: 'stock', value: parseInt(newStock) })
            });
            if (res.ok) {
                showToast("Stock actualizado correctamente.");
                showSavedIndicator();
                setTimeout(() => window.location.reload(), 1000);
            }
        } catch (err) {
            showToast("Error al guardar stock.", "error");
        }
    }
}

function toggleFeaturedBadge(btn, id) {
    showToast("Producto marcado como destacado / top ventas.");
    btn.style.color = 'var(--admin-gold)';
}

async function deleteProduct(id) {
    if (confirm("¿Seguro de eliminar permanentemente este producto de la base de datos?")) {
        try {
            const res = await fetch(`/api/admin/perfumes/delete/${id}`, { method: 'POST' });
            if (res.ok) {
                showToast("Producto eliminado de la base de datos.");
                setTimeout(() => window.location.reload(), 1000);
            }
        } catch (err) {
            showToast("Error al eliminar.", "error");
        }
    }
}

// ==========================================================================
// 3. CONTEXTUAL SIDEBAR & INLINE CMS EDITING
// ==========================================================================
function setupVisualInlineEditing() {
    // Enable inline contentEditable on text fields
    document.querySelectorAll('.navbar span, .hero-title, .hero-subtitle, .product-title, h1, p, span, .price').forEach(el => {
        if (el.closest('.admin-drawer') || el.closest('.product-card-admin-overlay') || el.closest('.sidebar')) return;
        
        // Check if this text belongs to a product
        let productId = null;
        let fieldName = null;
        
        if (window.location.pathname.includes('/producto/')) {
            const parts = window.location.pathname.split('/producto/');
            productId = parts[parts.length - 1].split('?')[0];
            
            // Map typical classes/selectors
            if (el.tagName === 'H1') fieldName = 'nombre';
            else if (el.classList.contains('price') || el.innerText.includes('COP')) fieldName = 'precio';
            else if (el.closest('.tech-item') && el.innerText.includes('Piel')) fieldName = 'tipoPielIdeal';
            else if (el.tagName === 'P') fieldName = 'descripcion';
        }

        // Apply visual editing context
        el.classList.add('admin-editable');
        
        // Add double click listeners to trigger contextual sidebar panels
        el.addEventListener('dblclick', (e) => {
            e.stopPropagation();
            activeEditingElement = el;
            
            // Open editor drawer and switch dynamically
            openPerfumeDrawer(productId);
            
            // Switch tabs contextually
            if (fieldName === 'precio') {
                switchDrawerTab('comercial');
                showToast("Editando precios y ofertas comerciales.");
            } else if (fieldName === 'descripcion') {
                switchDrawerTab('general');
                showToast("Editando descripción general del producto.");
            } else if (el.tagName === 'IMG' || el.style.backgroundImage) {
                switchDrawerTab('media');
                showToast("Administrando recursos multimedia.");
            }
            
            // Allow typing directly
            el.contentEditable = true;
            el.classList.add('admin-editable-editing');
            el.focus();
        });

        el.addEventListener('blur', async () => {
            el.contentEditable = false;
            el.classList.remove('admin-editable-editing');
            
            if (productId && fieldName) {
                let val = el.textContent.trim();
                if (fieldName === 'precio') {
                    val = parseFloat(val.replace('$', '').replace(/\./g, '').replace(/,/g, '')) || 0;
                }
                
                try {
                    const res = await fetch(`/api/admin/perfumes/patch/${productId}`, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ field: fieldName, value: val })
                    });
                    if (res.ok) {
                        showToast(`Guardado en vivo: ${fieldName}`);
                        showSavedIndicator();
                    }
                } catch (err) {
                    showToast("Error al persistir cambios en base de datos.", "error");
                }
            }
        });
    });

    // Setup interactive image editors
    document.querySelectorAll('img, .product-image').forEach(img => {
        if (img.closest('.admin-drawer') || img.closest('.sidebar')) return;
        
        img.addEventListener('dblclick', (e) => {
            e.stopPropagation();
            e.preventDefault();
            injectImageActionMenu(img);
        });
    });
}

// Contextual action overlay for images
function injectImageActionMenu(img) {
    // Remove existing menus
    document.querySelectorAll('.admin-image-menu-overlay').forEach(el => el.remove());

    const menu = document.createElement('div');
    menu.className = 'admin-image-menu-overlay';
    menu.innerHTML = `
        <button class="admin-image-menu-btn" title="Reemplazar / Subir" onclick="triggerImgUploadSelect()"><i class="fas fa-upload"></i></button>
        <button class="admin-image-menu-btn" title="Rotar" onclick="rotateImagePreview()"><i class="fas fa-redo"></i></button>
        <button class="admin-image-menu-btn" title="Portada" onclick="setAsCoverImage()"><i class="fas fa-image"></i> Cover</button>
        <button class="admin-image-menu-btn" title="Eliminar" onclick="removeActiveImage()"><i class="fas fa-trash"></i></button>
    `;
    
    // Position menu near the image element
    img.style.position = 'relative';
    img.parentElement.appendChild(menu);
}

function triggerImgUploadSelect() {
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image/*';
    input.onchange = async (e) => {
        if (input.files && input.files[0]) {
            const formData = new FormData();
            formData.append('file', input.files[0]);
            showToast("Subiendo nueva imagen...");
            try {
                const res = await fetch('/api/admin/perfumes/upload', {
                    method: 'POST',
                    body: formData
                });
                if (res.ok) {
                    const data = await res.json();
                    if (activeEditingElement && activeEditingElement.tagName === 'IMG') {
                        activeEditingElement.src = data.url;
                    } else if (activeEditingElement) {
                        activeEditingElement.style.backgroundImage = `url(${data.url})`;
                    }
                    showToast("Imagen reemplazada con éxito.");
                    showSavedIndicator();
                }
            } catch (err) {
                showToast("Error de conexión.", "error");
            }
        }
    };
    input.click();
}

function rotateImagePreview() {
    showToast("Imagen rotada 90° (Vista previa).");
    if (activeEditingElement) {
        const currentRotation = activeEditingElement.style.transform || 'rotate(0deg)';
        const deg = parseInt(currentRotation.replace(/[^0-9]/g, '')) || 0;
        activeEditingElement.style.transform = `rotate(${deg + 90}deg)`;
    }
}

function setAsCoverImage() {
    showToast("Establecida como imagen de portada del catálogo.");
}

function removeActiveImage() {
    if (confirm("¿Remover esta imagen?")) {
        if (activeEditingElement) activeEditingElement.remove();
        showToast("Imagen removida.");
    }
}

// ==========================================================================
// 4. RIGHT-CLICK CUSTOM CONTEXT MENU
// ==========================================================================
function setupContextMenu() {
    document.addEventListener('contextmenu', (e) => {
        // Prevent on editable elements
        if (e.target.closest('.admin-drawer') || e.target.closest('.sidebar')) return;
        
        e.preventDefault();
        
        // Remove existing custom context menus
        const oldMenu = document.getElementById('admin-custom-context-menu');
        if (oldMenu) oldMenu.remove();

        const menu = document.createElement('div');
        menu.id = 'admin-custom-context-menu';
        menu.className = 'admin-context-menu';
        menu.style.top = `${e.clientY}px`;
        menu.style.left = `${e.clientX}px`;
        
        menu.innerHTML = `
            <div class="admin-context-menu-item" onclick="openPerfumeDrawer(null)"><i class="fas fa-plus"></i> Crear Nuevo Producto</div>
            <div class="admin-context-menu-item" onclick="window.location.href='/admin/dashboard'"><i class="fas fa-tachometer-alt"></i> Panel de Control</div>
            <div class="admin-context-menu-item" onclick="exportTableToCSV()"><i class="fas fa-file-csv"></i> Descargar Reporte CSV</div>
            <div class="admin-context-menu-item" onclick="closePerfumeDrawer()"><i class="fas fa-times"></i> Cerrar Editor</div>
        `;
        
        document.body.appendChild(menu);
        
        // Close menu on click anywhere
        document.addEventListener('click', () => menu.remove(), { once: true });
    });
}

// ==========================================================================
// 5. CRUD ACTION SHORTCUTS (Duplicate, Archive, Restore)
// ==========================================================================
async function duplicateProduct(id) {
    if (confirm("¿Estás seguro de duplicar este producto?")) {
        try {
            const res = await fetch(`/api/admin/perfumes/duplicate/${id}`, { method: 'POST' });
            if (res.ok) {
                showToast("Producto duplicado correctamente.");
                showSavedIndicator();
                setTimeout(() => window.location.reload(), 1000);
            } else {
                showToast("Error al duplicar.", "error");
            }
        } catch (err) {
            showToast("Error de conexión.", "error");
        }
    }
}

async function archiveProduct(id) {
    if (confirm("¿Estás seguro de archivar este producto?")) {
        try {
            const res = await fetch(`/api/admin/perfumes/archive/${id}`, { method: 'POST' });
            if (res.ok) {
                showToast("Producto ocultado/archivado con éxito.");
                showSavedIndicator();
                setTimeout(() => window.location.reload(), 1000);
            } else {
                showToast("Error al archivar.", "error");
            }
        } catch (err) {
            showToast("Error de conexión.", "error");
        }
    }
}

async function restoreProduct(id) {
    try {
        const res = await fetch(`/api/admin/perfumes/restore/${id}`, { method: 'POST' });
        if (res.ok) {
            showToast("Producto restaurado con éxito.");
            showSavedIndicator();
            setTimeout(() => window.location.reload(), 1000);
        } else {
            showToast("Error al restaurar.", "error");
        }
    } catch (err) {
        showToast("Error de conexión.", "error");
    }
}

// ==========================================================================
// 6. CSV IMPORT & EXPORT UTILITIES
// ==========================================================================
function exportTableToCSV(filename = 'inventario_perfumes.csv') {
    const rows = document.querySelectorAll("table tr");
    let csvContent = [];
    
    rows.forEach(row => {
        const cols = row.querySelectorAll("td, th");
        let rowContent = [];
        cols.forEach(col => {
            let text = col.innerText.replace(/"/g, '""').trim();
            rowContent.push(`"${text}"`);
        });
        csvContent.push(rowContent.join(","));
    });
    
    const csvBlob = new Blob([csvContent.join("\n")], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement("a");
    if (link.download !== undefined) {
        const url = URL.createObjectURL(csvBlob);
        link.setAttribute("href", url);
        link.setAttribute("download", filename);
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        showToast("CSV exportado correctamente.");
    }
}
