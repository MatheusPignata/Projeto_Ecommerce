var pw = "";
 document.querySelector(".cadastroForm").addEventListener("submit", function(event) {
     event.preventDefault();
     novoCliente();
 });

function pwReview(e){
    let pwe = e.parentNode.querySelector("input");
    const type = pwe.getAttribute('type') === 'password' ? 'text' : 'password';
    pwe.setAttribute('type', type);
    e.classList.toggle('bi-eye');
}

function maskCPF(i){
    var v = i.value;
    
    if(isNaN(v[v.length-1])){ // impede entrar outro caractere que não seja número
       i.value = v.substring(0, v.length-1);
       return;
    }
    
    i.setAttribute("maxlength", "14");
    if (v.length == 3 || v.length == 7) i.value += ".";
    if (v.length == 11) i.value += "-";
 
 }

function maskCEP(i){
    var v = i.value;

    if(isNaN(v[v.length-1])){
        i.value = v.substring(0, v.length-1);
        return;
     }

     i.setAttribute("maxlength", "9");
     if (v.length == 5) i.value += "-";
}

function setPw(e) {
    pw = md5(e.value);
}

function login() {
    let email = document.querySelector("#log_email").value;
    let url = "http://10.87.207.11:8080/tabaum/login?usuario=" +email + "&senha=" + pw;
     
    fetch(url)
        .then(res => {
            return res.json();
        }).then(data => { 
            if (data.id_cliente != 0) {
                localStorage.setItem("info",JSON.stringify(data));
 
                window.location.href = "../home/home.html";
            }else {
                alert('Email ou senha incorreto')
            }
        }).catch(err => {
            console.log(err);
        });
}

function novoCliente() {
    //e.preventDefault();
    let nome = document.querySelector("#cad_nome");
    let cpf = document.querySelector("#cad_cpf");
    let email = document.querySelector("#cad_email");
    let senha1 = document.querySelector("#cad_senha");
    let senha2 = document.querySelector("#cad_senha2");
    let senhaF = "";
    let url = "http://10.87.207.11:8080/tabaum/clientes";

    if(senha1.value == senha2.value){
        senhaF = md5(senha1.value)

        let obj  = {
            "cpf": cpf.value,
            "nome_completo": nome.value,
            "email": email.value,
            "senha": senhaF
        }

        fetch(url, { method: "POST", body: JSON.stringify(obj)})
        .then(res => {
            console.log(res); return res.json()
        }).then(data => {
            let obj2 = { 
                "id_cliente": data.id_cliente,
                "nomeCompleto": obj.nome_completo
            }

            console.log("oi")

            localStorage.setItem("info",JSON.stringify(obj2));
 
            window.location.href = "http://127.0.0.1:5500/home/home.html";

        }).catch(err => {
            console.log(err);
        });

        nome.value = "";
        cpf.value = "";
        email.value = "";
        senha1.value = "";
        senha2.value = "";

    }else{
        alert("As senhas não são iguais");
    }

}

function goHome() {
        window.location.href = "../home/home.html";
}