1º Correr Ring Manager na VM : "sudo java -jar RingManager-1.0-jar-with-dependencies.jar"

2º Correr todos os servidores nas VMs, cada servidor irá comunicar com o Ring Manager e obter os ips dos servidores vizinhos,
desta maneira o Ring é construído : "sudo java -jar ChatServiceImpl-1.0-jar-with-dependencies.jar "IP da VM" "IP da VM do Ring Manager""

3º Depois de todos os servidores estarem conectados, os clientes podem comunicar com o Ring Manager e obter o IP do respetivo servidor.