/* -------------------------------------------------
   Front‑end logic – pure vanilla JavaScript (ES6+)
   ------------------------------------------------- */

document.addEventListener('DOMContentLoaded', () => {
    // ---- Orders -------------------------------------------------
    const orderForm = document.getElementById('order-form');
    const orderTableBody = document.querySelector('#order-table tbody');

    orderForm.addEventListener('submit', async e => {
        e.preventDefault();
        const data = {
            produktId: Number(orderForm.produktId.value),
            menge: Number(orderForm.menge.value),
            preis: Number(orderForm.preis.value),
            beschreibung: orderForm.beschreibung.value.trim()
        };

        try {
            const resp = await fetch('/api/order', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });
            if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
            const created = await resp.json();
            addOrderRow(created);
            orderForm.reset();
        } catch (err) {
            alert('Failed to create order: ' + err);
        }
    });

    async function loadOrders() {
        try {
            const resp = await fetch('/api/order');
            if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
            const orders = await resp.json();
            orderTableBody.innerHTML = '';
            orders.forEach(addOrderRow);
        } catch (err) {
            console.error('Could not load orders', err);
        }
    }

    function addOrderRow(order) {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${order.id ?? ''}</td>
            <td>${order.produktId}</td>
            <td>${order.menge}</td>
            <td>${order.preis}</td>
            <td>${order.beschreibung ?? ''}</td>
        `;
        orderTableBody.appendChild(tr);
    }

    // ---- Books --------------------------------------------------
    const availableBody = document.querySelector('#available-books tbody');
    const borrowedBody  = document.querySelector('#borrowed-books tbody');

    async function loadBooks() {
        // Available books
        try {
            const resp = await fetch('/api/book/available');
            if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
            const books = await resp.json();
            availableBody.innerHTML = '';
            books.forEach(b => addBookRow(b, false));
        } catch (err) {
            console.error('Could not load available books', err);
        }

        // Borrowed books
        try {
            const resp = await fetch('/api/book/borrowed');
            if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
            const books = await resp.json();
            borrowedBody.innerHTML = '';
            books.forEach(b => addBookRow(b, true));
        } catch (err) {
            console.error('Could not load borrowed books', err);
        }
    }

    function addBookRow(book, isBorrowed) {
        const tr = document.createElement('tr');
        const actionTd = document.createElement('td');

        // Delete button
        const delBtn = document.createElement('button');
        delBtn.textContent = 'Delete';
        delBtn.className = 'action-btn delete-btn';
        delBtn.onclick = () => deleteBook(book.id);
        actionTd.appendChild(delBtn);

        // Borrow / Return button
        const toggleBtn = document.createElement('button');
        toggleBtn.textContent = isBorrowed ? 'Return' : 'Borrow';
        toggleBtn.className = `action-btn ${isBorrowed ? 'return-btn' : 'borrow-btn'}`;
        toggleBtn.onclick = () => toggleBorrow(book.id, isBorrowed);
        actionTd.appendChild(toggleBtn);

        tr.innerHTML = `
            <td>${book.id}</td>
            <td>${book.titel}</td>
            <td>${book.autor}</td>
            <td>${book.isbn}</td>
            ${isBorrowed ? `<td>${book.ausgeliehenVon ?? ''}</td>` : ''}
        `;
        tr.appendChild(actionTd);

        (isBorrowed ? borrowedBody : availableBody).appendChild(tr);
    }

    async function deleteBook(id) {
        if (!confirm('Delete book #' + id + '?')) return;
        try {
            const resp = await fetch(`/api/book/${id}`, { method: 'DELETE' });
            if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
            await loadBooks();
        } catch (err) {
            alert('Failed to delete book: ' + err);
        }
    }

    async function toggleBorrow(id, currentlyBorrowed) {
        const endpoint = currentlyBorrowed ? `/api/book/${id}/return` : `/api/book/${id}/borrow`;
        try {
            const resp = await fetch(endpoint, { method: 'POST' });
            if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
            await loadBooks();
        } catch (err) {
            alert('Failed to change borrow state: ' + err);
        }
    }

    // ---- Initial load -------------------------------------------
    loadOrders();
    loadBooks();
});
