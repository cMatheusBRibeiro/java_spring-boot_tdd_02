# language: pt

Funcionalidade: Mensagens

  Cenário: Registrar mensagem
    Quando submeter uma nova mensagem
    Então deve retornar a mensagem criada
    E deve retornar status 201

  Cenário: Obter mensagem por ID
    Dado que uma mensagem já foi registrada
    Quando buscar a mensagem por id
    Então a mensagem é retornada com sucesso
    E deve retornar status 200

  Cenário: Remover mensagem
    Dado que uma mensagem já foi registrada
    Quando requisitar a remoção da mensagem
    Então deve retornar status 204