const url = "http://10.87.207.11:8080/tabaum/produtos";
let produtos = document.querySelector(".produtos");
let data = new Date();
let dataF = data.getFullYear() + "-" + (data.getMonth() + 1) + "-" + data.getDate()

getProd()
logado();

function getProd() {
    fetch(url)
    .then(res => {
        return res.json();
    }).then(data => {
        let info = JSON.parse(localStorage.getItem('info'));
        let prods = info.produtos;
        let prodId;
        let prodQntd;
        for(i = 0; i < prods.length; i++) {
            prodId = prods[i].id_prod;    
            prodQntd = prods[i].qntd;     
            
            data.forEach(item => {
                if(item.id_produto == prodId) {
                    let card = document.querySelector(".produto").cloneNode(true);
    
                    card.classList.remove("model");
                    card.classList.add("item");
                    card.querySelector("img").src = item.imagem;
                    card.querySelector(".prod_desc>.top>h3").innerHTML = item.nome
                    card.querySelector(".valor_quantidade>h4").innerHTML = item.id_produto
                    if(item.promocao != 0){
                        card.querySelector(".valor_quantidade>h5").innerHTML = "R$ " + (item.promocao).toFixed(2);
                    }else{
                        card.querySelector(".valor_quantidade>h5").innerHTML = "R$ " + (item.valor).toFixed(2);
                    }
                    card.querySelector(".valor_quantidade>p").innerHTML = "x " + prodQntd;
    
                   produtos.appendChild(card); 
                }
            })
        }

    calcValor()

    }).catch(err => {
        console.error(err);
    })
}

function calcValor() {
    let cartao = document.querySelector(".cartao");
    let total = document.querySelector(".valorTotal")
    let pix = document.querySelector(".pix")
    let itens = document.querySelectorAll(".item");
    let valorFinal = 0;

    limpar()

    for(i = 0; i < itens.length; i++) {
        let valor = Number(itens[i].querySelector(".valor_quantidade>h5").innerHTML.replace("R$ ", ""))
        let qntd = Number(itens[i].querySelector(".valor_quantidade>p").innerHTML.replace("x ", ""))

        valorFinal = valorFinal + (valor * qntd);
    }
    
    total.innerHTML = "R$ " + valorFinal.toFixed(2)
    pix.innerHTML = "Valor à vista com 10% de desconto R$ " + (valorFinal - (valorFinal * 0.1)).toFixed(2) + " ou no cartão de credito em:"

    for(i = 1; i <= 12; i++) {
        let opcao = document.querySelector(".opcao").cloneNode(true);

        opcao.classList.remove("model");
        opcao.classList.add("a");

        let juros = 0;
        let texticulo = "x sem juros de R$ ";
        if(i > 4){
            juros = ((valorFinal / i) * (i * 0.02))
            texticulo = "x de R$ "
        }
        
        opcao.querySelector("p").innerHTML = i + texticulo + ((valorFinal / i) + juros).toFixed(2);

        cartao.appendChild(opcao);
    }

}

function qntd(e) {
    let a = Number(e.parentNode.parentNode.querySelector("p").innerHTML.replace("x ", ""));
    let b = Number(e.parentNode.parentNode.querySelector("h4").innerHTML);
    let obj = JSON.parse(localStorage.getItem('info'));

    if(e.id == "up"){
        a++;
        e.parentNode.parentNode.querySelector("p").innerHTML = "x " + a
    }else{
        if(a <= 1){
            a = 1
            e.parentNode.parentNode.querySelector("p").innerHTML = "x " + a
        }else{
            a--;
            e.parentNode.parentNode.querySelector("p").innerHTML = "x " + a
        }
    }
    
    for(i = 0; i < obj.produtos.length; i++) {
        console.log(obj.produtos[i].id_produto, b)
        if(obj.produtos[i].id_prod == b){
            obj.produtos[i].qntd = a
            localStorage.setItem('info', JSON.stringify(obj));
            console.log("oi")
            
        }
    }
    calcValor()
    
}

function limpar() {
    let aa = document.querySelectorAll(".a");

    aa.forEach(item => {
        item.remove();
    })
}

function apagar(e){
    e.parentNode.parentNode.parentNode.remove()
    let obj = JSON.parse(localStorage.getItem('info'));
    obj.produtos = obj.produtos.splice(1)
    localStorage.setItem('info', JSON.stringify(obj));

    calcValor()
}

function finalizarCompra() {
    let urlPedido = "http://10.87.207.11:8080/tabaum/pedidos";
    let local = JSON.parse(localStorage.getItem('info'));
    let valor = Number(document.querySelector(".valorTotal").innerHTML.replace("R$ ", ""));

    let obj = {
        "id_cliente": local.id_cliente,
        "data": dataF,
        "valor": valor,
        "status": "Aberto",
        "produtos": JSON.stringify(local.produtos)
    }

    fetch(urlPedido, {method: "POST", body: JSON.stringify(obj)})
    .then(res => {
        console.log(res); return res.json()
    }).then(data => {
        console.log(data)
    }).catch(err => {
        console.log(err)
    });

    alert("Compra Finalizada!");

    delete local["produtos"];

    localStorage.setItem('info', JSON.stringify(local))

    window.location.href = "../home/home.html";
}

function logado(){
    let nomeCompleto = JSON.parse(localStorage.getItem("info")).nomeCompleto
    let nome = nomeCompleto.split(" ")[0];
    
    if(localStorage.getItem("info") != undefined) {
        document.querySelector("#logado").innerHTML = nome;
    }
}
function goHome() {
    window.location.href = "../home/home.html";
}