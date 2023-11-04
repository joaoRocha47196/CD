# Parametros necessarios para executar o Cliente

O Courier Server necessita dos seguintes parametros:
1. PubSub IP - o IP da maquina que da *host* ao *rabbit mq*;
2. PubSub Port - a porta da maquina que da *host* ao rabbit mq;
3. Queue Name - o nome da *queue* de onde o worker pretende ler as mensagens;
4. Worker Name - o nome do worker no *spread*;
5. Spread IP - o IP do *spread node* a onde este se pretende ligar;
6. (optional) SpreadPort - o porto do *spread node* a onde este se pretende ligar.