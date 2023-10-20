
const fetchOrders = (username) => {

    return fetch("http://localhost:8080/product/get/" + username)
        .then(res => res.json())
        .then(data => {
            return data
        });
}

document.addEventListener('DOMContentLoaded', async () => {

    const username = JSON.parse(localStorage.getItem("username"))


    const orders = await fetchOrders(username);
    console.log(orders)


    const table = document.createElement('table');



    const headerRow = document.createElement("tr");


    const productNameHeader = document.createElement("th");
    productNameHeader.textContent = "Product Name"
    productNameHeader.style.cssText="border:1px solid black"




    const usernameHeader = document.createElement('th');
    usernameHeader.textContent = "Customer"
    usernameHeader.style.cssText="border:1px solid black"



    const countryHeader = document.createElement('th');
    countryHeader.textContent = "Country"
    countryHeader.style.cssText="border:1px solid black"




    const stateHeader = document.createElement('th');
    stateHeader.textContent = "State"
    stateHeader.style.cssText="border:1px solid black"

    const paymentTypeHeader = document.createElement('th');
    paymentTypeHeader.textContent = "Payment Type"
    paymentTypeHeader.style.cssText="border:1px solid black"



    headerRow.appendChild(productNameHeader);
    headerRow.appendChild(usernameHeader);
    headerRow.appendChild(countryHeader);
    headerRow.appendChild(stateHeader);
    headerRow.appendChild(paymentTypeHeader);



    table.appendChild(headerRow);
    table.style.cssText="border:1px solid black"




    orders.map(order => {


        const bodyRow = document.createElement("tr");



        const productName = document.createElement("td");
        productName.textContent = order.productName
        productName.style.cssText="border:1px solid black"





        const username = document.createElement("td");
        username.textContent = order.username
        username.style.cssText="border:1px solid black"





        const country = document.createElement("td");
        country.textContent = order.country
        country.style.cssText="border:1px solid black"






        const state = document.createElement("td");
        state.textContent = order.state
        state.style.cssText="border:1px solid black"


        const paymentType = document.createElement("td");
        paymentType.textContent = order.payment_type
        paymentType.style.cssText="border:1px solid black"



        bodyRow.appendChild(productName);
        bodyRow.appendChild(username);
        bodyRow.appendChild(country);
        bodyRow.appendChild(state);
        bodyRow.appendChild(paymentType);



        
        table.appendChild(bodyRow);

    });

    document.getElementById('main').appendChild(table);
})