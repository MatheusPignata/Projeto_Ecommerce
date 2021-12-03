var nota = Number((Math.random() * 6).toFixed(1));
const url = "http://10.87.207.11:8080/tabaum/produtos";
var id_prod = 0;
let user = document.querySelector("#logado").innerHTML;
let lista = document.querySelector(".lista");

if(nota > 5) nota = 5;

getProd();
Nota();
logado();
logoff();
mobile();

window.addEventListener('resize', function(event) {
    Nota();
    mobile();
}, true);

function mobile() {
    let width = window.innerWidth;
    let div_btn_abrir = document.querySelector(".btn_burguer");
    let div_btn_fechar = document.querySelector(".btn_x");
    
    div_btn_abrir.innerHTML = "";
    div_btn_fechar.innerHTML = "";

    if(width <= 1024) {
        let btn_abrir = document.createElement("button");
        btn_abrir.className = "btn_abrir";
        btn_abrir.addEventListener("click", () => {
            abrir();
        });
        btn_abrir.innerHTML = "<img src='https://icon-library.com/images/hamburger-icon-png/hamburger-icon-png-11.jpg'>";

        div_btn_abrir.appendChild(btn_abrir);

        let btn_fechar = document.createElement("button");
        btn_fechar.className = "btn_fechar";
        btn_fechar.addEventListener("click", () => {
            fechar();
        });
        btn_fechar.innerHTML = "<img src='https://images2.imgbox.com/ea/9b/DBhBWboq_o.png'>";

        div_btn_fechar.appendChild(btn_fechar)
    }
}

function getProd() {
    let params = (new URL(document.location)).searchParams;
    let id = params.get('id');
    id_prod = id;
    let title = document.querySelector(".title")
    let img = document.querySelector(".img")
    let valor = document.querySelector(".valor")
    let desconto = document.querySelector(".valor_desconto")
    let info = document.querySelector(".info")
    let description = document.querySelector(".description")
    let produtos = document.querySelector(".produtos");

    fetch(url)
    .then(res => {
        return res.json();
    }).then(data => {
        title.innerHTML = data[id - 1].nome;
        img.src = data[id - 1].imagem;
        description.innerHTML = data[id - 1].descricao;

        if(data[id - 1].promocao != 0){   
            valor.innerHTML = "R$ " + (data[id - 1].valor).toFixed(2)
            desconto.innerHTML = "R$ " + (data[id - 1].promocao).toFixed(2);
            info.innerHTML = "Em até 12x de R$ " + ((data[id - 1].promocao)/12).toFixed(2) + " sem juros no cartão"
        }else{
            valor.innerHTML = "";
            desconto.innerHTML = "R$ " + (data[id - 1].valor).toFixed(2)
            info.innerHTML = "Em até 12x de R$ " + ((data[id - 1].valor)/12).toFixed(2) + " sem juros no cartão"
        }

        data.forEach(prod => {
            if(data[id - 1].categoria == prod.categoria && data[id -1].id_produto != prod.id_produto){
                let item = document.querySelector(".card").cloneNode(true);

                
                item.href+="?id=" + prod.id_produto;
                item.classList.remove("model");
                item.querySelector("img").src = prod.imagem;
                item.querySelector(".prod_desc >.teste").innerHTML = prod.nome;
                if(prod.promocao == 0){
                    item.querySelector(".prod_desc >h5").innerHTML = "";
                    item.querySelector(".prod_desc >h2").innerHTML = "R$ " + (prod.valor).toFixed(2);
                }else{
                    item.querySelector(".prod_desc >h5").innerHTML = "R$ " + (prod.valor).toFixed(2);
                    item.querySelector(".prod_desc >h2").innerHTML = "R$ " + (prod.promocao).toFixed(2);
                }
        
                produtos.appendChild(item);
           }
        })

    }).catch(err => {
        console.log(err);
    })
}

function Nota() {
    let star = document.querySelector(".nota");
    let img = document.querySelector(".nota>img");
    let bkNota = document.querySelector(".bk_nota");
    let head = document.querySelector("head")
    let style = document.createElement("style");
    let pixTotal = (10*window.innerWidth)/100;
    let pixStar = pixTotal / 5;

    style.innerHTML = `.batata{
        width: ${(pixStar * nota)}px;
    }`

    head.appendChild(style);
    star.style.height = img.offsetHeight+"px";
    bkNota.classList.add("batata");
}

function bereguejhonson(e){
    let degrade = document.querySelector(".degrade")

    if(e.offsetWidth + e.scrollLeft >= e.scrollWidth){
        degrade.style.opacity = "0";
        degrade.style.visibility = "hidden";
    }else{
        degrade.style.opacity = "1";
        degrade.style.visibility = "visible";
    }
}

function compra() {
    let obj = JSON.parse(localStorage.getItem('info'));
    
    if(obj == null){
        window.location.href = "../login_cadastro/index.html"
    }

    if(obj.produtos == undefined){
        let prods = [{id_prod:id_prod,qntd:1}]

        obj["produtos"] = prods;
            
    }else if( obj.produtos.length == 0){
        obj.produtos.push({id_prod:id_prod,qntd:1})

    }else{
        for(let i = 0; i < obj.produtos.length; i++){
            if(obj.produtos[i].id_prod == id_prod){
                obj.produtos[i].qntd ++;
                break;
            }
            else if(i == obj.produtos.length -1){              
                obj.produtos.push({id_prod:id_prod,qntd:1})
                break
            }
        }
    }
    localStorage.setItem('info', JSON.stringify(obj));

    
    window.location.href = "../carrinho/index.html";
}

function logado(){
    let nomeCompleto = JSON.parse(localStorage.getItem("info")).nomeCompleto
    let nome = nomeCompleto.split(" ")[0];
    
    if(localStorage.getItem("info") != undefined) {
        document.querySelector("#logado").innerHTML = nome;
    }
}

function logoff(){
    document.querySelector(".icon").addEventListener("mouseover", () => {        
        if(user.innerHTML != "") document.querySelector("#sair").style="display:flex"
        document.querySelector("#statuspedido").style="display:flex"
     })
     document.querySelector(".icon").addEventListener("mouseout", () => {
        document.querySelector("#sair").style="display:none"
        document.querySelector("#statuspedido").style="display:none"
     })
    
}

function sair() {
    localStorage.removeItem("info");
    goHome();
}

function cadastro() {
    if(user == ""){
        window.location.href = "../login_cadastro/index.html";
    }
}

function goHome() {
    window.location.href = "../home/home.html";
}

function abrir() {
    document.querySelector(".submenu").style.display = "block";
}
  
function fechar() {
    document.querySelector(".submenu").style.display = "none";
}

function statusPedido() {
    let url = "http://10.87.207.11:8080/tabaum/pedidos"
    let obj = JSON.parse(localStorage.getItem("info"))

    document.querySelector("body").style.overflow = "hidden";
    document.querySelector(".modal").classList.remove("model");

    fetch(url)
    .then(res => {
        return res.json();
    }).then(data => {
        data.forEach(pedido => {
            if(pedido.cliente == obj.id_cliente){
                let cardPedido = document.querySelector(".pedido").cloneNode(true);

                cardPedido.classList.remove("model");
                cardPedido.querySelector(".numeroPedido").innerHTML = "Pedido: " + pedido.id_pedido;
                cardPedido.querySelector(".data").innerHTML = pedido.data;

                pedido.itens.forEach(item => {
                    let itemPedido = cardPedido.querySelector(".itemPedido").cloneNode(true);
                    itemPedido.querySelector(".nome").innerHTML = item.produto.nome;
                    itemPedido.querySelector(".qntd").innerHTML = "x" + item.quantidade;
                    if(item.produto.promocao == 0){
                        itemPedido.querySelector(".valor1").innerHTML = "R$ " + (item.produto.valor).toFixed(2);
                    }else{
                        itemPedido.querySelector(".valor1").innerHTML = "R$ " + (item.produto.promocao).toFixed(2);
                    }
                    cardPedido.querySelector(".todosOsProdutos").appendChild(itemPedido);
                })

                cardPedido.querySelector(".total").innerHTML = "R$ "  + (pedido.valor).toFixed(2);

                lista.appendChild(cardPedido);
            }
        })
    }).catch(err => {
        console.log(err)
    })

}

function closeModal() {
    document.querySelector("body").style.overflow = "visible";
    document.querySelector(".modal").classList.add("model");
}

function imprimir() {
    var conteudo = document.querySelector('.lista').innerHTML;
    
    let tela_impressao = window.open();
    tela_impressao.document.write('<html><head><title>Print it!</title><style>@import url(https://fonts.googleapis.com/css?family=Open+Sans);*{margin:0;padding:0;box-sizing:border-box}body{display:flex;flex-direction:column;align-items:center;background-color:#252525;padding-right:2vw}.submenu{display:block;position:fixed;width:10vw;left:0;height:100%;background:#1e1e1e;transition:all .5s ease;z-index:1}.submenu header{display:flex;align-items:center;justify-content:center;padding-bottom:2vw;cursor:pointer}.submenu ul{margin:0;padding:0;list-style:none}.submenu li{position:relative;cursor:pointer}.submenu a{display:block;box-sizing:border-box;border-bottom:1px solid #000;border-top:1px solid rgba(255,255,255,.1);border-left:5px solid transparent;height:65px;width:100%;color:#eee;text-decoration:none;padding:10px 15px;background:#1e1e1e;font-family:"Open Sans",sans-serif;transition:all .5s ease;font-size:90%}.submenu li:hover>a{border-left:5px solid #00fc92;color:#00a3fd}.submenu ul ul{position:absolute;left:0;top:0;width:100%;visibility:hidden;opacity:0;transition:transform .2s;transform:translateX(50px)}.submenu li:hover>ul{left:100%;visibility:visible;opacity:1;transform:translateX(0)}.header{display:flex;width:85vw;height:6vh;align-items:center;margin:20px 0;color:#fff;align-self:flex-end;justify-content:space-between}.logo{margin-top:3vh;width:80%}.icon img{height:30px;margin:0 20px;filter:invert(100%)}.icon{display:flex;flex-direction:column;align-items:center;justify-content:center;position:relative;transition:all .5s ease;z-index:1;cursor:pointer}#sair{position:absolute;top:172%;width:100%;height:4vh;align-items:center;justify-content:center;transition:all .5s ease;cursor:pointer;display:none;background-color:rgba(56,56,56,.5);border:1px solid rgba(255,255,255,.5)}#statuspedido{position:absolute;text-align:center;top:100%;width:100%;height:4vh;align-items:center;justify-content:center;transition:all .5s ease;cursor:pointer;display:none;background-color:rgba(56,56,56,.5);border:1px solid rgba(255,255,255,.5)}.modal{width:100vw;height:100vh;background:rgba(0,0,0,.3);position:fixed;top:0;left:0;display:flex;align-items:center;justify-content:center;z-index:3}.square{width:50%;height:80%;background-color:#fff;border-radius:20px;display:flex;position:relative;align-items:flex-end;justify-content:center}.square>span{position:absolute;right:0;top:0;margin:2%;cursor:pointer}.square>span>img{width:30px}.lista{width:95%;height:90%;overflow-y:scroll;margin-bottom:15px}.lista::-webkit-scrollbar{width:10px}.lista::-webkit-scrollbar-track{background:#555;border:3px solid #fff;border-radius:6px}.lista::-webkit-scrollbar-thumb{background:radial-gradient(circle,#00fc92 0,#00a3fd 100%);border-radius:6px}.pedido{width: 100%;display:flex;flex-direction:column;border:solid 1px #000;margin:10px;padding:5px;color:#000}.cima{display:flex;justify-content:space-between}.numeroPedido{font-size:16px;font-weight:700}.itemPedido{display:flex;font-size:14px;justify-content:space-between;border-bottom:solid 1px rgba(0,0,0,.5);padding:10px 0}.itemPedido div{display:flex}.nome{white-space:nowrap;overflow:hidden;text-overflow:ellipsis}.qntd{margin-right:10px}.baixo{display:flex;justify-content:space-between;margin-top:10px;font-weight:700}.search-box{width:fit-content;height:fit-content;position:relative}.input-search{height:50px;width:50px;border-style:none;padding:10px;font-size:18px;letter-spacing:2px;outline:0;border-radius:25px;transition:all .5s ease-in-out;background:linear-gradient(90deg,#00fc92 0,#00a3fd 100%);padding-right:40px;color:#fff}.input-search::placeholder{color:rgba(255,255,255,.8);font-size:18px;letter-spacing:2px;font-weight:100}.btn-search img{width:60%;filter:invert(100%)}.btn-search{width:50px;height:50px;border-style:none;font-size:20px;font-weight:700;outline:0;cursor:pointer;border-radius:50%;position:absolute;right:0;color:#fff;background-color:transparent;pointer-events:painted}.btn-search:focus~.input-search{width:300px;border-radius:0;background-color:transparent;border-bottom:1px solid rgba(255,255,255,.5);transition:all .5s cubic-bezier(0,.11,.35,2)}.input-search:focus{width:300px;border-radius:0;background-color:transparent;border-bottom:1px solid rgba(255,255,255,.5);transition:all .5s cubic-bezier(0,.11,.35,2)}.destaque{background-color:#ccc;width:85vw;height:200px;overflow:hidden;align-self:flex-end}.img_des{width:100%}.meio{display:flex;width:85vw;margin:20px 0;justify-content:space-between;position:relative;align-self:flex-end;border-bottom:1px solid #000;z-index:0}.degrade{position:absolute;width:100%;height:15%;z-index:1;background-image:linear-gradient(0deg,#252525 0,rgba(255,255,255,0) 100%);bottom:0;transition:all .3s ease}.produtos{display:flex;height:85vh;width:100%;flex-wrap:wrap;overflow-y:scroll;align-self:center;border:1px solid rgba(255,255,255,.1)}.produtos::-webkit-scrollbar{width:0}.produtos::-webkit-scrollbar-track{box-shadow:inset 0 0 5px transparent;border-radius:10px}.produtos::-webkit-scrollbar-thumb{background:inherit;border-radius:10px}.card{width:12.5%;height:32vh;display:flex;flex-direction:column;align-items:center;cursor:pointer;margin:2% 2% 0;color:#fff;text-decoration:none}.model{display:none;visibility:hidden}.card:hover{box-shadow:-10px 0 13px -7px #00fc92,10px 0 13px -7px #00a3fd,5px 5px 3px -6px transparent}.card img{width:100%;margin-bottom:5%}.prod_desc{width:100%;display:flex;flex-direction:column;margin-left:5%}.teste{white-space:nowrap;overflow:hidden;text-overflow:ellipsis;width:95%}.card h5{text-decoration:line-through}.card h2{color:#00a3fd;margin:10% 5% 0 0;align-self:flex-end}.footer{display:flex;color:#fff;width:85vw;align-self:flex-end;justify-content:center}.tes{display:flex;flex-flow:column wrap;margin:3vh 5vw}.footer p{font-weight:700;font-size:18px;margin-bottom:1vh}.footer h5{font-size:12px;margin-bottom:1vh}.footer a{color:#fff;text-decoration:none}.footer a:hover{border-left:3px solid #00fc92;color:#00a3fd}.copyright{color:rgba(255,255,255,.5)}@media (max-width:1024px){body{padding:0}.btn_fechar{filter:invert(100%);background:0 0;border:none;margin-top:20px;margin-left:20px}.btn_fechar img{width:20px;height:20px}.btn_abrir{background:0 0;border:none;margin-left:10px}.btn_abrir img{width:50px;height:50px}.submenu{width:42vw;display:none}.header{width:80%;align-self:center}.btn-search:focus~.input-search{width:150px}.input-search:focus{width:150px}#sair{top:160%}.square{width:80%}.destaque{width:100%;height:100px;align-self:center}.meio{height:60vh;width:100vw;align-self:center}.produtos{height:60vh;width:100vw}.card{width:29%;height:25vh}.prod_desc p{font-size:12px}.prod_desc h5{font-size:12px}.prod_desc h2{font-size:20px}.footer{width:100%;font-size:10px;align-self:center;justify-content:space-between}.footer h5{font-size:10px}.tes{margin:3vh 1vw}}</style></head><body>');
    tela_impressao.document.write(conteudo);
    tela_impressao.document.write('</body></html>');
    
    tela_impressao.window.print();
    tela_impressao.window.close()
}