# Parametros necessarios para executar o Courier Server

O *Courier Server* necessita dos seguintes parametros:
0. Server Port - porta onde o *Courier App* se pode conectar;
1. Courier Name - o nome do *Courier Server* no *Spread*;
2. PubSub IP - o IP da maquina que da *host* ao *rabbit mq*;
3. PubSub Port - a porta da maquina que da *host* ao *rabbit mq*;
4. Spread IP - o ip da maquina que da *host* ao *spread node* onde se vai conectar;
5. (optional) Spread Port - a a porta da maquina que da *host* ao *spread node* onde se vai conectar.

# Detalhes importantes da Eleicao

A eleicao tem a *flag canCandidate* para impedir que o cliente corra o risco de ganhar 2 encomendas por se 
candidatar a segunda sem saber que ganhou a primeira.   
A eleicao tem a *flag isBusy* para impedir que o cliente participa numa eleicao quando este esta ocupado.   
A *flag isBusy* esta preparada para a sua utilizacao em multiThread, uma vez que esta tambem pode ser afetada
pelas acoes do *Courier App*, uma vez que este pode alterar a sua disponibilidade(acabando a encomenda ou indicando
que esta ocupado).

# Detalhes sobre os pedidos

Os pedidos tem 2 estruturas de dados associadas: o *HashMap* de ID para pedido e a *LinkedList* de pedidos pendentes.    
O *HashMap* foi criado para que nao seja necessario o uso do pedido completo durante as eleicoes, deste modo 
pode-se usar diretamente o ID e mesmo assim conseguir saber se o pedido ja esta ou nao a ser processado, 
uma vez que o *HashMap* tem um tempo de procura de `O(1)` e as listas de `O(N)`, consultado o *HashMap* antes da 
*LinkedList* para que assim o processamento seja mais rapido.    
Ambas as estruturas de dados sao preparadas para utilizacao *multiThread*.