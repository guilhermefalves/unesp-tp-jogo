Projeto apresentado a matéria de Técnicas de Programação - UNESP - Bauru
Professor:
    Renê Pegoraro
Autores:
    Guilherme Felipe Alves

Introdução
    Por ser um jogo multiplayer, o projeto tem sua arquitetura baseada em um client-server. Sendo o server responsável por alimentar o cliente, dado a ele informações como o tabuleiro, ou a posição do outro jogador. Já o cliente, é responsável por gerar a interface gráfica para o usuário (jogador), além de enviar informações importantes ao servidor para manter os jogadores atualizados da situação do jogo.

Orientções:
    Antes de tudo, é necessário ter um ambiente preparado com o jdk na versão 11. Para testar o ambiente, execute: javac --version e java --version (linux)
    Para iniciar o jogo, primeiro é necessário compilar e iniciar o server, para isso:
        - Dentro deste diretorio execute: cd server && javac Server.java && java Server (linux)
          Deve aparecer a seguinte mensagem: "Server started"
        - Com o servidor iniciado, é hora de iniciar o cliente, para isso em outra aba dentro deste diretório execute: cd client/ && javac MazeBomb.java && java MazeBomb (linux)
          Deve aparecer uma janela com um tabuleiro e os jogadores já conectados