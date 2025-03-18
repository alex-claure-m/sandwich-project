
document.addEventListener('DOMContentLoaded', () => {
    // Configuración del contador (2 minutos)
    let timeLeft = 30;

    const timerElement = document.getElementById('timer'); //contador
    const resendButton = document.getElementById('resendButton'); //boton para reenvio
    const verificationCodeInput = document.getElementById('verificationCode'); // boton to post check verificaion code
    const confirmButton = document.getElementById('confirmButton'); // buton para chechear codigo verificacion

    const emailInput = document.getElementById("email-resend");

    const verificationForm = document.getElementById('verificationForm');

    const countdown = setInterval(() => {
        if (timeLeft <= 0) {
            clearInterval(countdown);
            // timerElement.style.display = none;
            timerElement.textContent = "El código ha expirado.";  // Cambiamos el mensaje de temporizador
            emailInput.style.display = 'block'; // el casillero para escribir de nuevo el mail aparece
            resendButton.style.display = 'block';  // el resend button aparece!
            confirmButton.style.display= 'none'; // saco el boton Confirmar
            verificationCodeInput.style.display= 'none';

        } else {
            emailInput.style.display = 'none';
            const minutes = Math.floor(timeLeft / 60);
            const seconds = timeLeft % 60;
            timerElement.textContent = `Reenviar código en ${minutes}:${seconds < 10 ? '0' : ''}${seconds} minutos`;
            timeLeft--;
        }
    }, 1000);

    //este evento en si, es para manejar cuando el cliente envia el codigo check, se lo debe pasar al endopoint
    // validation-check, que esta en register-validationController
    verificationForm.addEventListener("submit", function(event) {
    event.preventDefault(); // Evitar el envío por defecto

    fetch('/register-verification/validation-check', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ verificationCode: verificationCodeInput.value})
    })
    .then(async response => {
        if (!response.ok) {
           // Si la respuesta no es JSON, intentar obtener el texto del error
            const text = await response.text();
            console.error("Respuesta en texto:", text);
            throw new Error(text);
        }
        return response.json();
        })
    // .then(response => response.json())
    .then(data => {
        console.log("a ver que devolviste hdp", data);

        if (data.success) {
            alert(data.message);
            window.location.href = '/create-password?username=' + encodeURIComponent(data.usuario); // Página de redirección
        } else {
            alert(data.message);
            verificationCodeInput.disabled = true;
            confirmButton.disabled = true;
            resendButton.style.display = 'block';
        }
    })
        .catch(error => {
            console.error('Error en la verificación:', error);
            alert("Hubo un problema con la verificación.");
        });
    });

    /* ******************* PARA CUANDO REENVIE EL CODIGO AL MAIL ********************* */
   resendButton.addEventListener('click', function(){
       const email = emailInput.value.trim(); // el trim function es para sacar espacios vacios

       if(email== " "){
           alert("no escribiste mail, por favor intente de nuevo");
           return
       }
       fetch("/register-verification/resend-code",{
           method: 'POST',
           headers: {
               'Content-Type':' application/json'
           },
           body: JSON.stringify({email:email})
       })
           .then(response => {
               if (!response.ok) {
                   throw new Error('Error en la respuesta del servidor');
               }
               return response.json();  // Asegura que intenta parsear JSON
           })
           .then(data =>{
               if(data.success){
                   alert("codigo enviado con exito");
                   // Oculta los elementos nuevamente
                   emailInput.style.display = 'none';
                   resendButton.style.display = 'none';
                   confirmButton.style.display = 'block';

                   // Reinicia el temporizador
                   timeLeft = 60;
                   countdownRestart(); // reiniciar conteo
               } else{
                   alert('error' +data.message);
               }
           })
           .catch(error =>{
               console.error('Error al reenviar código:', error);
               alert('Hubo un problema al reenviar el código.');
           });
   });

    function countdownRestart() {
        timerElement.textContent = `Reenviar código en 0:30 minutos`;
        let newCountdown = setInterval(() => {
            if (timeLeft <= 0) {
                clearInterval(newCountdown);
                timerElement.textContent = "El código ha expirado.";
                emailInput.style.display = 'block';
                resendButton.style.display = 'block';
                confirmButton.style.display = 'none';
                verificationCodeInput.style.display= 'none';


            } else {
                verificationCodeInput.style.display='block'; // para que aparezca el input para copiar codigo
                const minutes = Math.floor(timeLeft / 60);
                const seconds = timeLeft % 60;
                timerElement.textContent = `Reenviar código en ${minutes}:${seconds < 10 ? '0' : ''}${seconds} minutos`;
                timeLeft--;
            }
        }, 1000);
    }

});