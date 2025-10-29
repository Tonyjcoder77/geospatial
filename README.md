# Code Challenge: Geospatial with Clean Architecture Design


Crie e disponibilize no github uma aplicação Spring Boot com maven com os seguintes requisitos:
Crie uma classe pessoa com os seguintes atributos: id, nome, data de nascimento e data de admissão contendo um construtor para preencher os atributos;


Inicialize sua aplicação populando um mapa de pessoas em memória, com 3 pessoas diferentes de modo que a chave deste mapa facilite a busca de uma pessoa pelo atributo id;


Disponibilize um endpoint (método GET - /person) que retorne a lista de pessoas, ordenada por nome em ordem alfabética, com todos os atributos no formato JSON.


Disponibilize um endpoint (método POST - /person) que inclua uma nova pessoa no mapa em memória.
a. Caso o usuário cadastrante não especifique um id, deve ser atribuído automaticamente o maior id do mapa + 1.
b. Caso o usuário cadastrante especifique um id e já exista uma pessoa no mapa com o mesmo id, deve retornar um erro com o HTTP Status correto para casos de conflito;


Disponibilize um endpoint (método DELETE - /person/{id}) que remova uma pessoa no mapa em memória.
a. Se não encontrar uma pessoa com o id informado, deve retornar um erro com o HTTP Status correto para casos de registro não encontrado;


Disponibilize um endpoint (método PUT - /person/{id}) que atualize uma pessoa no mapa em memória.
a. Se não encontrar uma pessoa com o id informado, deve retornar um erro com o HTTP Status correto para casos de registro não encontrado;



Disponibilize um endpoint (método PATCH - /person/{id}) que atualize um atributo de uma pessoa no mapa em memória.
a. Se não encontrar uma pessoa com o id informado, deve retornar um erro com o HTTP Status correto para casos de registro não encontrado;

Disponibilize um endpoint (método GET - /person/{id}) que retorne a pessoa identificada pelo id passado por parâmetro.
a. Se não encontrar uma pessoa com o id informado, deve retornar um erro com o HTTP Status correto para casos de registro não encontrado;

Disponibilize um endpoint (método GET - /person/{id}/age?output={days|months|years}), que receba um parâmetro de formato de saída, que retorne a idade atual de uma pessoa em dias, meses ou anos completos, de acordo com o parâmetro passado,
a. Se não encontrar uma pessoa com o id informado, deve retornar um erro com o HTTP Status correto para casos de registro não encontrado.
b. Caso o parâmetro de formato de saída não seja reconhecido, deve retornar um erro com o HTTP Status correto para casos de requisições mal formatadas;

Disponibilize um endpoint (método GET - /person/{id}/salary?output={min|full}) que retorne o salário da pessoa em R$, quando selecionado full, e em quantidade de salários mínimos, quando selecionado min, com duas casas decimais, arredondando para cima, baseando-se na regra de que a pessoa inicia na empresa com um salário de R$1558,0, e a cada ano na empresa, seu salário é acrescido em 18% do valor mais R$500.
a. Se não encontrar uma pessoa com o id informado, deve retornar um erro com o HTTP Status correto para casos de registro não encontrado,
b. Caso o parâmetro de formato de saída não seja reconhecido, deve retornar um erro com o HTTP Status correto para casos de requisições mal formatadas
c. Considere o valor do salário mínimo como sendo R$1302,00 (fev/2023)

Exemplo:
Id: 1
Nome: José da Silva,
Data de Nascimento: 06/04/2000
Data de Admissão: 10/05/2020

Data atual: 07/02/2023
Salário mínimo atual: R$1302,00

Respostas esperadas:
8 - (/person/{id}/age?output=days) – 8342
8 - (/person/{id}/age?output=months) – 274
8 - (/person/{id}/age?output=years) – 22

9 - (/person/{id}/salary?output=full) – 3259,36
9 - (/person/{id}/salary?output=min) – 2,51
