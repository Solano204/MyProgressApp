let button = document.getElementById('googleLoginButton');
    
button.addEventListener('click', registerUserGoogles => {
    registerUserGoogle();
});


    let registerUserGoogle = async () => {

        let userData = {
            user: "Migos2323",
            email: "Miugosds@lsmes.com",
            typeAuthentication: "Google",
            registerInformation: {
                name: "Esmeralda",
                age: 20,
                height: 1.25,
                country: "USA",
                gender: "Masculino",
                levelActivity: "Sedentarios",
                valueActivity: 2.0,
                goal: "Mantener Peso",
                startingWeight: 75.0,
                currentWeight: 75.0,
                endWeight: 80.0
            }
        };

        const peticion = await fetch("http://localhost:8080/register/Google/User)",
        {
            method: 'POST', // or 'PUT' or 'DELETE' 
            headers:  {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });
    } ;