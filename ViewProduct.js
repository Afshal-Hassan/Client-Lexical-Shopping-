import products from "./ProductApi.js"
import cart from "./CartState.js"


document.addEventListener('DOMContentLoaded',() => {


    if (!localStorage.getItem('cart')){
        localStorage.setItem('cart',JSON.stringify(cart));
    }

    else{
        const cartState = JSON.parse(localStorage.getItem('cart'));



        products.map((product) => {
            const productDiv = document.createElement("div");
            productDiv.style.cssText= "   display: flex; flex-direction: column; align-items: center; justify-content: center;"
    
    
    
    
            const productImage = document.createElement("img");
            productImage.src=product.image;
            productImage.style.cssText= " margin-bottom:10px; "
    
    
    
    
            
            const productName = document.createElement("span")
            productName.innerHTML=product.name;
            productName.style.cssText= " margin-bottom:13px; font-weight:600 "
    
    
    
    
            const productPrice = document.createElement("span")
            productPrice.innerHTML="Price: " + product.price 
            productPrice.style.cssText= " margin-bottom:15px; font-weight:600 "
    
    
    
    
            const buy = document.createElement("button");
            buy.textContent="Buy"
            buy.style.cssText = "margin-bottom:20px"
            
            
    
            buy.onclick = (event) => {
                cartState.push(product);
                localStorage.setItem("cart",JSON.stringify(cartState));
                alert("Item added to cart")
            }
    
    
    
            productDiv.appendChild(productImage)
            productDiv.appendChild(productName);
            productDiv.appendChild(productPrice)
            productDiv.appendChild(buy)
    
            document.getElementById('main').appendChild(productDiv);
        });
    }


   

    const cartPage = document.createElement("a");
    cartPage.setAttribute('href',"carts.html");
    cartPage.innerHTML="See Cart"
    document.getElementById('main').appendChild(cartPage);


    


})