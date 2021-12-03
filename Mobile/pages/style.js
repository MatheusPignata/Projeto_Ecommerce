import { StyleSheet } from'react-native';



export default StyleSheet.create (
    {
    header:{
        alignItems: 'center',
        justifyContent: 'center',
        height: 100,
        width: '100%',
        backgroundColor: 'black'
      },
      headerImage: {
        width: '50%',
        height: '100%',
        marginTop: 5,
      },
      container: {
        backgroundColor: '#252525',
        flexDirection: 'column',
    },
    title: {
        width: '100%',
        height: 40,
        justifyContent: 'center',
        alignItems: 'center',
    },
    texto: {
        color: 'white',
        fontSize: 20,
        fontFamily: 'Open Sans',
    },
    titulo: {
        color: 'white',
        fontSize: 30,
        fontFamily: 'Open Sans',
        alignItems: 'center',
        justifyContent: 'center',
    },
    body: {
        marginTop: -40,
        alignItems: 'center',
        justifyContent: 'center',
        padding: 70,
        flexWrap: 'wrap',
        
    },
    produto: {
        width: 270,
        height: 230,
        marginTop: 30,
        alignItems: 'center',
        justifyContent: 'center',
        paddingRight: 200,
    },
    valor: {
        color: 'gray',
        fontFamily: 'Open Sans',
        fontSize: 15,
        textDecorationLine: 'line-through',
        marginTop: 15,
    },
    promocao: {
        color: 'white',
        fontFamily: 'Open Sans',
    },
    descricao :{
        fontSize: 13,
        color: 'white',
        fontFamily: 'Open Sans',
    },
    categoria: {
        fontSize: 15,
        color: 'white',
        fontFamily: 'Open Sans',
        marginBottom: 10,
    },
    footer: {
        backgroundColor: 'black',
        width: '100%',
        padding: 10,
    },
    grande: {
        fontSize: 25,
        fontFamily: 'Open Sans',
        color: 'white',
    },
    pequeno: {
        fontSize: 15,
        fontFamily: 'Open Sans',
        color: 'white',
    },
    copyright: {
        color:  '#ffffff',
        opacity: 0.5,
        fontFamily: 'Open Sans',
    }
    }
)