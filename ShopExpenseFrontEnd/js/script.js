let currentPage = 1;
const pageSize = 10;

async function fetchExpenses(page, size) {
    try {
        const response = await fetch(`http://localhost:8080/kieutrangshop/expenses?page=${page}&size=${size}`);
        if (!response.ok) throw new Error('Failed to fetch expenses');
        const expenses = await response.json();
        return expenses;
    } catch (error) {
        console.error('Error fetching expenses:', error);
        return [];
    }
}

function renderExpenses(expenses) {
    const expenseBody = document.getElementById('expenseBody');
    expenseBody.innerHTML = '';
    expenses.forEach(expense => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td class="border p-2">${expense.id}</td>
            <td class="border p-2">${expense.amount.toFixed(2)}</td>
            <td class="border p-2">${expense.description}</td>
            <td class="border p-2">${expense.expenseDate}</td>
            <td class="border p-2">${expense.paymentMethod}</td>
            <td class="border p-2">${expense.location}</td>
            <td class="border p-2">${expense.note || ''}</td>
            <td class="border p-2">${expense.categoryId}</td>
        `;
        expenseBody.appendChild(row);
    });
}

function setupPagination() {
    const prevPageBtn = document.getElementById('prevPage');
    const nextPageBtn = document.getElementById('nextPage');
    const pageInfo = document.getElementById('pageInfo');

    prevPageBtn.disabled = currentPage === 1;
    pageInfo.textContent = `Page ${currentPage}`;

    prevPageBtn.onclick = async () => {
        if (currentPage > 1) {
            currentPage--;
            const expenses = await fetchExpenses(currentPage, pageSize);
            renderExpenses(expenses);
            setupPagination();
        }
    };

    nextPageBtn.onclick = async () => {
        currentPage++;
        const expenses = await fetchExpenses(currentPage, pageSize);
        if (expenses.length === 0) {
            currentPage--;
        } else {
            renderExpenses(expenses);
        }
        setupPagination();
    };
}

async function init() {
    const expenses = await fetchExpenses(currentPage, pageSize);
    renderExpenses(expenses);
    setupPagination();
}

document.addEventListener('DOMContentLoaded', init);