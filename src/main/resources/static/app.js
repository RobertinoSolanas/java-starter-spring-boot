/* ════════════════════════════════════════════════════
   Java Starter – Frontend Logic
   Vanilla JS – no dependencies
   ════════════════════════════════════════════════════ */

(function () {
    'use strict';

    /* ── Helpers ──────────────────────────────────── */

    /** Show a toast notification. type = 'success' | 'error' */
    function toast(message, type) {
        var container = document.getElementById('toast-container');
        var el = document.createElement('div');
        el.className = 'toast ' + type;
        el.textContent = message;
        container.appendChild(el);
        setTimeout(function () { el.remove(); }, 3600);
    }

    /** Generic fetch wrapper that returns parsed JSON or throws */
    function api(method, path, body) {
        var opts = {
            method: method,
            headers: { 'Content-Type': 'application/json' }
        };
        if (body !== undefined) {
            opts.body = JSON.stringify(body);
        }
        return fetch(path, opts).then(function (res) {
            if (res.status === 204) return null;          // DELETE returns 204
            return res.json().then(function (data) {
                if (!res.ok) {
                    var msg = data.message || data.error || 'Unbekannter Fehler';
                    throw new Error(msg);
                }
                return data;
            });
        });
    }

    /* ── Tab switching ───────────────────────────── */
    var tabs = document.querySelectorAll('.tab');
    tabs.forEach(function (tab) {
        tab.addEventListener('click', function () {
            tabs.forEach(function (t) {
                t.classList.remove('active');
                t.setAttribute('aria-selected', 'false');
            });
            tab.classList.add('active');
            tab.setAttribute('aria-selected', 'true');

            document.querySelectorAll('.panel').forEach(function (p) {
                p.classList.remove('active');
                p.hidden = true;
            });
            var target = document.getElementById('panel-' + tab.dataset.tab);
            target.classList.add('active');
            target.hidden = false;
        });
    });

    /* ══════════════════════════════════════════════
       BÜCHER
       ══════════════════════════════════════════════ */

    var buecherBody = document.querySelector('#buecher-table tbody');
    var buecherEmpty = document.getElementById('buecher-empty');
    var formBuch = document.getElementById('form-buch');

    /** Render the books table */
    function renderBuecher(books) {
        buecherBody.innerHTML = '';
        if (!books.length) {
            buecherEmpty.hidden = false;
            return;
        }
        buecherEmpty.hidden = true;

        books.forEach(function (b) {
            var tr = document.createElement('tr');
            tr.dataset.id = b.id;
            tr.innerHTML =
                '<td>' + b.id + '</td>' +
                '<td class="cell-titel">' + escHtml(b.titel) + '</td>' +
                '<td class="cell-autor">' + escHtml(b.autor) + '</td>' +
                '<td class="cell-isbn">' + escHtml(b.isbn) + '</td>' +
                '<td class="cell-jahr">' + escHtml(b.jahr) + '</td>' +
                '<td class="col-actions">' +
                '<div class="action-group">' +
                '<button class="btn btn-sm btn-edit" data-action="edit">Bearbeiten</button>' +
                '<button class="btn btn-sm btn-delete" data-action="delete">Löschen</button>' +
                '</div>' +
                '</td>';
            buecherBody.appendChild(tr);
        });
    }

    function escHtml(str) {
        if (str == null) return '';
        return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
    }

    /** Load all books from API */
    function loadBuecher() {
        api('GET', '/api/books')
            .then(renderBuecher)
            .catch(function (err) { toast('Laden fehlgeschlagen: ' + err.message, 'error'); });
    }

    /** Create a book */
    formBuch.addEventListener('submit', function (e) {
        e.preventDefault();
        var payload = {
            titel: document.getElementById('buch-titel').value.trim(),
            autor: document.getElementById('buch-autor').value.trim(),
            isbn: document.getElementById('buch-isbn').value.trim(),
            jahr: parseInt(document.getElementById('buch-jahr').value, 10)
        };
        api('POST', '/api/books', payload)
            .then(function () {
                toast('Buch erfolgreich angelegt!', 'success');
                formBuch.reset();
                loadBuecher();
            })
            .catch(function (err) { toast(err.message, 'error'); });
    });

    /** Delegate click events on the books table */
    buecherBody.addEventListener('click', function (e) {
        var btn = e.target.closest('[data-action]');
        if (!btn) return;
        var tr = btn.closest('tr');
        var id = tr.dataset.id;
        var action = btn.dataset.action;

        if (action === 'delete') {
            if (!confirm('Buch #' + id + ' wirklich löschen?')) return;
            api('DELETE', '/api/books/' + id)
                .then(function () { toast('Buch gelöscht.', 'success'); loadBuecher(); })
                .catch(function (err) { toast(err.message, 'error'); });
        }

        if (action === 'edit') {
            enterEditMode(tr);
        }

        if (action === 'save') {
            saveEdit(tr);
        }

        if (action === 'cancel') {
            loadBuecher();   // simply reload to exit edit mode
        }
    });

    /** Replace text cells with input fields */
    function enterEditMode(tr) {
        var id = tr.dataset.id;
        ['titel', 'autor', 'isbn', 'jahr'].forEach(function (field) {
            var td = tr.querySelector('.cell-' + field);
            var val = td.textContent;
            td.innerHTML = '<input class="inline-edit" data-field="' + field + '" value="' + escHtml(val) + '" />';
        });
        var actionsCell = tr.querySelector('.col-actions');
        actionsCell.innerHTML =
            '<div class="action-group">' +
            '<button class="btn btn-sm btn-save" data-action="save">Speichern</button>' +
            '<button class="btn btn-sm btn-cancel" data-action="cancel">Abbrechen</button>' +
            '</div>';
    }

    /** Save the inline edit via PUT */
    function saveEdit(tr) {
        var id = tr.dataset.id;
        var payload = {
            titel: tr.querySelector('[data-field="titel"]').value.trim(),
            autor: tr.querySelector('[data-field="autor"]').value.trim(),
            isbn: tr.querySelector('[data-field="isbn"]').value.trim(),
            jahr: parseInt(tr.querySelector('[data-field="jahr"]').value, 10)
        };
        api('PUT', '/api/books/' + id, payload)
            .then(function () { toast('Buch aktualisiert.', 'success'); loadBuecher(); })
            .catch(function (err) { toast(err.message, 'error'); });
    }

    /* ══════════════════════════════════════════════
       BESTELLUNGEN
       ══════════════════════════════════════════════ */

    var bestBody = document.querySelector('#bestellungen-table tbody');
    var bestEmpty = document.getElementById('bestellungen-empty');
    var formBest = document.getElementById('form-bestellung');

    function renderBestellungen(orders) {
        bestBody.innerHTML = '';
        if (!orders.length) {
            bestEmpty.hidden = false;
            return;
        }
        bestEmpty.hidden = true;

        orders.forEach(function (o) {
            var tr = document.createElement('tr');
            tr.innerHTML =
                '<td>' + o.id + '</td>' +
                '<td>' + o.produktId + '</td>' +
                '<td>' + o.menge + '</td>' +
                '<td>' + Number(o.preis).toFixed(2) + '</td>' +
                '<td>' + escHtml(o.beschreibung) + '</td>';
            bestBody.appendChild(tr);
        });
    }

    function loadBestellungen() {
        api('GET', '/api/bestellungen')
            .then(renderBestellungen)
            .catch(function (err) { toast('Laden fehlgeschlagen: ' + err.message, 'error'); });
    }

    formBest.addEventListener('submit', function (e) {
        e.preventDefault();
        var payload = {
            produktId: parseInt(document.getElementById('best-produktId').value, 10),
            menge: parseInt(document.getElementById('best-menge').value, 10),
            preis: parseFloat(document.getElementById('best-preis').value),
            beschreibung: document.getElementById('best-beschreibung').value.trim()
        };
        api('POST', '/api/bestellungen', payload)
            .then(function () {
                toast('Bestellung erfolgreich angelegt!', 'success');
                formBest.reset();
                loadBestellungen();
            })
            .catch(function (err) { toast(err.message, 'error'); });
    });

    /* ── Boot ────────────────────────────────────── */
    loadBuecher();
    loadBestellungen();

})();
