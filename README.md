# Desafio Programador Fullstack UNOESC
Esse é o nosso desafio para a vaga de programador fullstack na [UNOESC](https://www.unoesc.edu.br/). Serão testadas as habilidades e qualidade de código ao transformar requisitos limitados em uma aplicação web.

### DESAFIO
Desenvolver uma aplicação web responsável por gerenciar processos de uma instituição de ensino superior. Soluções parciais serão aceitas, mas o que for submetido deve estar funcionando.

A aplicação deverá possuir 03 (três) perfis diferentes:

Visão do administrador:
- Incluir, excluir, atualizar e visualizar usuários, cursos e disciplinas;

Visão do aluno:
- Realizar seu cadastro no sistema;
- Visualizar os cursos e efetuar sua inscrição em um deles.

Visão do professor:
- Visualizar as disciplinas ministradas pelo professor.
- Visualizar os alunos vinculados às disciplinas ministradas pelo professor.
- Imprimir um relatório com as disciplinas ministradas pelo professor no formato CSV, XLS ou PDF;

Atributos mínimos do usuário: 
- código, nome, cpf e perfil.

Atributos mínimos do curso: 
- código, nome e vagas.

Atributos mínimos da disciplina: 
- código, nome e curso.

Atributos mínimos da inscrição: 
- código, usuário e curso.

### ESCOPO DO DESAFIO
- Documentar todas suposições realizadas sobre o desafio no arquivo README.md.
  - Exemplo de suposição: Cada disciplina pode ser ministrada por apenas um professor.
- Tecnologias a serem utilizadas:
  - Java 8;
  - Spring Boot;
  - Maven;
  - Thymeleaf;
  - MySQL;
  - Git;

### AVALIAÇÃO
- O código será avaliado de acordo com os critérios: 
  - Build e execução da aplicação (resultado funcional);
  - Padrões de projeto MVC (Model View Controller); 
  - Clean code (pattern, manutenabilidade e clareza); 
  - Histórico do Git; 
  - Boas práticas de UI (Interface com o Usuário);
  - Estrutura do banco de dados;
  - Organização do relatório.
- Não esqueça de documentar o processo necessário para rodar a aplicação.


## DOCUMENTAÇÃO DA IMPLEMENTAÇÃO

# Como subir o projeto
  - Antes de qualquer coisa é necessario ter instalado o Java SE 1.8.
  - É recomendado o uso da ferramenta Spring Tool Suite 4.11.1 pois esta já possui todas as ferramentas necessárias para rodar o projeto, incluindo o Boot Dashboard.
  - Utilize o menu Boot Dashboard, selecione o projeto e Start.
  - Utilize o usuário "admin" e senha "admin" para acessar como Administrador. Outros cadastros podem ser realizados através deste perfil ou novos perfis cadastrados.

# Suposições
  - Os usuários só podem ser manipulados por usuários com perfil ADMIN
  - Toda e qualquer entidade só pode ser excluída se não houverem relacionamentos com outras entidades
  - Como cursos sofrem alterações em suas matrizes, ao excluir uma disciplina, esta é automaticamente desvinculada do curso. O efeito cascata também ocorre ao excluir um professor.

# Sugestão de melhorias
  - Não permitir o uso de disciplinas já vinculadas a um curso
  - Listar inscrições para os administradores
  - Solicitar confirmação ao excluir registros
  - Permissões dinâmicas por perfil
  - Sistema de logs/auditoria
  - Melhorias no mapeamento de rotas
  - Criar um Dashboard de cursos para inscrição
  - Criação de outros relatórios
  - Adicionar proteção CSRF em formulários
  
# Referências
DOCUMENTAÇÕES:

[Spring Official Documents](https://spring.io/guides)

[https://docs.spring.io/spring-security/site/docs/4.2.19.RELEASE/guides/html5/form-javaconfig.html#overriding-the-default-configure-httpsecurity-method](https://docs.spring.io/spring-security/site/docs/4.2.19.RELEASE/guides/html5/form-javaconfig.html#overriding-the-default-configure-httpsecurity-method)

CURSOS:

[Spring Boot & MVC com Thymeleaf - Udemy](https://www.udemy.com/course/spring-boot-mvc-com-thymeleaf/)

[Spring Boot & MVC com Spring Security - Udemy](https://www.udemy.com/course/spring-boot-mvc-com-spring-security/)

[Relatórios com JasperReports, Java e Spring Boot - Udemy](https://www.udemy.com/course/relatorios-com-jasperreports-java-e-spring-boot/)

[Spring Framework Expert - AlgaWorks - Projeto Brewer](https://cafe.algaworks.com/fn020-spring-framework-expert/)

BLOGS:

[https://domineospring.wordpress.com/](https://domineospring.wordpress.com/)