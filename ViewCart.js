

document.addEventListener('DOMContentLoaded' , () => {

    const cart = JSON.parse(localStorage.getItem("cart"));
    const table = document.createElement('table');
    
    
    
    const headerRow = document.createElement("tr");


    const productNameHeader = document.createElement("th");
    productNameHeader.textContent="Product Name"




    const productPriceHeader = document.createElement('th');
    productPriceHeader.textContent="Price"





    headerRow.appendChild(productNameHeader);
    headerRow.appendChild(productPriceHeader);

    table.appendChild(headerRow);

    cart.map(item => {
        

        const bodyRow = document.createElement("tr");

        

        const productName = document.createElement("td");
        productName.textContent = item.name






        const productPrice = document.createElement("td");
        productPrice.textContent = item.price



        bodyRow.appendChild(productName);
        bodyRow.appendChild(productPrice);


        table.appendChild(bodyRow);


    });



    document.getElementById('main').appendChild(table);



})