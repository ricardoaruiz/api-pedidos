const BASE_URL = 'http://localhost:8080';

function login() {
    const email = document.querySelector("#email").value;
    const password = document.querySelector("#password").value;

    fetch(`${BASE_URL}/login`, {
        method: 'POST',
        body: `{
                "email": "${email}",
                "senha": "${password}"
            }
        `
    })
    .then(response => {
        const token = document.querySelector('#token');
        token.value = response.headers.get("Authorization");
        setLoggedScreenState();
    })
}

function setLoggedScreenState() {
    const token = document.querySelector('#token').value;
    const btnLoggedClient = document.querySelector('#btnLoggedClient');

    if (token !== undefined && token !== '') {
        btnLoggedClient.removeAttribute('disabled');
        return;
    } 
    btnLoggedClient.setAttribute('disabled');
}

function loadLoggedClient() {
    const token = document.querySelector('#token').value;
    fetch(`${BASE_URL}/clientes/logged`, {
        method: 'GET',
        headers: {
            'Authorization': token
        }
    })
    .then(response => response.json())
    .then(client => {
        const clientData = document.querySelector('.client-data')
        clientData.innerHTML = JSON.stringify(client);
    })
    .catch(error => console.log(error));
}