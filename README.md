# Algoritmos de Busca IA

Este projeto tem como objetivo servir como metodo avalitivo para a disciplina de Inteligência Artificial do Curso de Ciencia da Computação da UNIOESTE.

### Tecnologias

- Gradle
- LWJGL
- ImGui
- Graphviz

### Pré-requisitos

Primeiramente instale a ferramente Graphiz pelo [site oficial](https://graphviz.org/download/) e adicione os binários a variavel de ambiente.

Certifique-se que a ultima versão do Java esteja instalada e as variáveis de ambiente estejam configuradas corretamente para que a ferramenta de build Gradle execute com sucesso.

### Build

**Observação: Este projeto foi testado apenas em ambientes Windows!**

Para realizar o build do projeto abra um terminal e execute o seguinte comando na pasta raiz do projeto:

```bash
.\gradlew.bat run
```

É possível também executar o projeto por meio do Intellij, para isto basta abrir o projeto e configurar a IDE para executar a classe Main.

### Uso

Para utilizar o projeto primeiramente é necessário carregar um arquivo contendo a descrição de um grafo, para isto, clique no menu superior ```File -> Open```, escolha o arquivo desejado e clique no botão ```OK```.

Uma imagem do grafo deve aparecer na janela principal, caso isto não aconteça verifique as mensagens na janela inferior de Logs para identificar erros no arquivo de descrição.

Após realizar o carregamento do grafo é possível realizar a busca por meio dos diversos algoritmos listados no menu superior ```Actions```. Ao clicar em uma das opções dois botões aparecerão na parte superior da janela principal juntamente com a imagem do grafo.

Clicando no botão ```Next``` uma iteração do algoritmo será executada indicando seu progresso na imagem do grafo, caso queira executar o algoritmo automaticamente clique no botão ```Finish```. 

Ao finalizar uma busca, as informações serão exibidas como mensagems na janela inferior de logs e o caminho será exibido na imagem.

### Autores
- Lyncon Baez
- Lucas Tomio

### Licença
[MIT](https://choosealicense.com/licenses/mit/)