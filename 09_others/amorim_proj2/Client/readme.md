# Parametros necessarios para executar o Cliente

O *Cliente* necessita dos seguintes parametros:
1. PubSub IP - o IP da maquina que da *host* ao *rabbit mq*;
2. PubSub Port - a porta da maquina que da *host* ao *rabbit mq*;
3. Exchange Name - o nome do *exchange* para o qual o cliente pretende mandar as mensagens.

# Exchange e queue pelo qual o cliente vai receber as mensagens

Como e apenas para demonstracao do trabalho pratico optamos por criar o *exchange* e a *queue* do cliente
dentro da propria aplicacao para esta ser mais simples de usar.