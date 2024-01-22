# Android SMS Fowarder

## Classes do aplicativo e suas funções

**TLSSocketFactory:**</br>
Esta classe estende SSLSocketFactory e é usada para criar sockets seguros (SSL/TLS).
Quando ignoreSsl é true, ela aceita todos os certificados SSL, essencialmente desabilitando a validação SSL.
Se ignoreSsl for false, ela mantém a validação SSL padrão.
Essa classe é usada no arquivo WebHookWorkRequest para configurar a fábrica de soquetes SSL usada nas solicitações da web.

**BootCompletedReceiver:**</br>
Esta classe é um BroadcastReceiver que é acionado quando o dispositivo é inicializado.
Ele inicia o serviço SmsReceiverService para lidar com as mensagens SMS em segundo plano.

**ForwardingConfig:**</br>
Representa uma configuração de encaminhamento de SMS.
Salva e recupera configurações de encaminhamento de SMS usando SharedPreferences.
Define valores padrão para URL, modelo JSON e cabeçalhos.
Possui métodos para salvar, recuperar e remover configurações.

**ListAdapter:**</br>
Uma implementação personalizada de ArrayAdapter usada para exibir a lista de configurações de encaminhamento.
Personaliza a exibição de itens na lista.

**MainActivity:**</br>
Atividade principal do aplicativo.
Solicita permissão para receber SMS e exibe a lista de configurações de encaminhamento.
Inicia o serviço SmsReceiverService se ainda não estiver em execução.
Permite adicionar novas configurações por meio de um diálogo.

**SmsReceiver:**</br>
BroadcastReceiver que responde a mensagens SMS recebidas.
Extrai o conteúdo da mensagem, identifica a configuração de encaminhamento correspondente e chama o método callWebHook para encaminhamento.

**SmsReceiverService:**</br>
Um serviço que registra o SmsReceiver para lidar com as mensagens SMS recebidas.
Inicia em primeiro plano com uma notificação se o dispositivo estiver executando o Android Oreo (versão 8.0) ou superior.

**WebHookWorkRequest:**</br>
Um worker para realizar solicitações da web (WebHook) em segundo plano.
Usa HttpURLConnection para enviar dados para uma URL especificada com cabeçalhos opcionais.
Implementa lógica de tentativas de repetição em caso de falha na solicitação.
