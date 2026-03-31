
// Helper functions for showing/hiding forms
function showAddListingForm() { document.getElementById('add-listing-form').style.display = 'flex'; }
function hideAddListingForm() { document.getElementById('add-listing-form').style.display = 'none'; }
function showAddOrderForm() { document.getElementById('add-order-form').style.display = 'flex'; }
function hideAddOrderForm() { document.getElementById('add-order-form').style.display = 'none'; }
hideAddListingForm();
hideAddOrderForm();
// Fetch and render listings
function fetchListings() {
    fetch('/api/listings/get-listing')
        .then(res => res.json())
        .then(data => {
            const tbody = document.querySelector('#listings-table tbody');
            tbody.innerHTML = '';
            data.forEach(listing => {
                tbody.innerHTML += `<tr>
                    <td>${listing.listingID}</td>
                    <td>${listing.vehicleType}</td>
                    <td>${listing.isInsured ? 'Yes' : 'No'}</td>
                    <td>${listing.condition}</td>
                    <td>${listing.year}</td>
                    <td>${listing.brand}</td>
                    <td><img src="${listing.imageLink}" alt="Car" style="width:60px;height:40px;"></td>
                    <td><button onclick="removeListing(${listing.listingID})">Remove</button></td>
                </tr>`;
            });
        });
}

function addListing() {
    const listing = {
        vehicleType: document.getElementById('vehicleType').value,
        isInsured: document.getElementById('isInsured').checked,
        condition: document.getElementById('condition').value,
        year: parseInt(document.getElementById('year').value),
        brand: document.getElementById('brand').value,
        imageLink: document.getElementById('imageLink').value
    };
    fetch('/api/listings/add-listing', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(listing)
    }).then(() => {
        hideAddListingForm();
        fetchListings();
    });
}

function removeListing(id) {
    fetch(`/api/listings/remove-listing/${id}`, { method: 'DELETE' })
        .then(() => fetchListings());
}

// Fetch and render orders
function fetchOrders() {
    fetch('/api/orders/get-order')
        .then(res => res.json())
        .then(data => {
            const tbody = document.querySelector('#orders-table tbody');
            tbody.innerHTML = '';
            data.forEach(order => {
                tbody.innerHTML += `<tr>
                    <td>${order.orderID}</td>
                    <td>${order.phoneNo}</td>
                    <td>${order.address}</td>
                    <td>${order.listing ? order.listing.listingID : ''}</td>
                    <td><button onclick="removeOrder(${order.orderID})">Remove</button></td>
                </tr>`;
            });
        });
}

function addOrder() {
    const order = {
        phoneNo: document.getElementById('phoneNo').value,
        address: document.getElementById('address').value,
        listing: { listingID: parseInt(document.getElementById('orderListingID').value) }
    };
    fetch('/api/orders/add-order', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(order)
    }).then(() => {
        hideAddOrderForm();
        fetchOrders();
    });
}

function removeOrder(id) {
    fetch(`/api/orders/remove-order/${id}`, { method: 'DELETE' })
        .then(() => fetchOrders());
}

// Initial load
window.onload = function() {
    fetchListings();
    fetchOrders();
};
