import React, {useState, useEffect} from 'react';
import { Image, View, Text, Button} from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import style from '../pages/style'

import * as Linking from 'expo-linking';

function shuffle(sourceArray) {
    for (var i = 0; i < sourceArray.length - 1; i++) {
        var j = i + Math.floor(Math.random() * (sourceArray.length - i));

        var temp = sourceArray[j];
        sourceArray[j] = sourceArray[i];
        sourceArray[i] = temp;
    }
    return sourceArray;
}

export default function pagina (){
    const [produtos, setProdutos] = useState([]);

    useEffect( () => {
        fetch('http://10.87.207.11:8080/tabaum/produtos')
        .then((resp) =>  resp.json())
        .then((data) => {
            data = shuffle(data);
            setProdutos(data);
         })
         .catch(() => {
            
         });
    }, []);

    return (    
        <View style={style.container}>
            <View style={style.header}>
                <Image style={style.headerImage} source={require("../assets/tabaum.png")} />
            </View>
            <LinearGradient 
                colors={['#66FFB2', '#00A2FF']}
                style={style.title}
                start={{x: 0, y: 0}}
                end={{x: 1, y: 1}}
                >
                    <Text style={style.titulo}>Promoções</Text>
            </LinearGradient >
               <View style={style.body}>
            {
                produtos.map((item,index) => {
                    if(item.promocao !== 0) {
                        return (
                            
                            <View>
                                <Image style = {style.produto} source = {{uri: item.imagem}} />
                                <Text style={style.valor}>R${item.valor}</Text>
                                <Text style={style.texto}>R${item.promocao}</Text>
                                <Text style={style.texto}>{item.nome}</Text>
                                <Text style={style.categoria}>{item.marca}</Text>
                                <Text style={style.categoria}>{item.categoria}</Text>
                                <Button color="#00A2FF" title="Comprar" key={index}

                                onPress={ () => {
                                    Linking.openURL("http://10.87.207.8:5500/prod_ind/index.html?id="+item.id_produto)
                                }}
                                />

                                
                            </View>
                            
                            
                        )
                        
                    }
                    
                }
                )
                
            }
            
            </View>
    
    <View style={style.footer}>
                <Text style={style.grande}>Atendimento</Text>
                <Text style={style.pequeno}>Horário de atendimento: 08:00 às 20:00 - Segunda a Sábado, 
                horário de Brasília (exceto domingo e feriados)</Text>
                <Text style={style.pequeno}>Endereço: Rua General Osório, 539 - 10° andar - Centro Pirassununga/SP - CEP: 13630-970</Text>
                <Text style={style.grande}>Central SAC</Text>
                <Text style={style.pequeno}>(19)707070, se não der, 70 de novo</Text>
                <Text style={style.grande}>E-mail</Text>
                <Text style={style.pequeno}>faleconosco@tabaum.com.br</Text>
                <Text style={style.copyright}>Copyright © 2021 All Rights Reserved by TaBaum.</Text>
            </View>
        </View>
    );
}