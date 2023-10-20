const orderNow = () => {
    
    const cart = JSON.parse(localStorage.getItem('cart'));
    const requestBody = []
    
    const username = document.getElementById('name').value;
    const country = document.getElementById('password').value;
    const state = document.getElementById('state').value;
    const paymentType = document.getElementById('paymentType').value;



    localStorage.setItem('username',JSON.stringify(username));

    cart.map(item => {

        const productDetails = {
            username: username,
            productName: item.name,
            country:country,
            state: state,
            payment_type: paymentType
        }

        requestBody.push(productDetails);

    })
    
    fetch('http://localhost:8080/product/save', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(requestBody)
})
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.error(error));


setTimeout(() => {
    localStorage.setItem('cart',JSON.stringify([]));
    window.location.href="/orders.html"
},1000)

    

}