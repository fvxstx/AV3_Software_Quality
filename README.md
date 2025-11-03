# 🧊 Projeto Geladeira Inteligente

## 📘 Descrição do Projeto
O projeto **Geladeira Inteligente** foi desenvolvido como parte da disciplina **Qualidade de Software**.  
Seu objetivo é simular o funcionamento de uma geladeira controlada por usuários com diferentes níveis de acesso, permitindo o gerenciamento e monitoramento dos itens armazenados.

O sistema conta com três principais entidades:
- **Usuário**
- **Geladeira**
- **Geladeira Itens**

### ⚙️ Funcionalidades Principais
- **Cadastro de Usuários:** Podem ser classificados como **pais**, **filhos** ou **outros**.
- **Controle de Acesso:**
    - **Pais (Administradores):**
        - Podem **adicionar itens** à geladeira.
        - Podem **definir restrições** de acesso para determinados itens (itens proibidos para certos usuários).
    - **Filhos e Outros Usuários:**
        - Podem **retirar itens** da geladeira, desde que não sejam itens restritos.
- **Registro de Ações:**
    - Toda retirada de item é **registrada no sistema**, permitindo o acompanhamento de quais usuários retiraram cada item e em qual momento.

### 🧩 Entidades do Sistema
1. **Usuário**
    - Atributos principais: `id`, `nome`, `tipo` (pai, filho, outro)
2. **Geladeira**
    - Atributos principais: `id`, `nome`, `localização`
3. **Geladeira Itens**
    - Atributos principais: `id`, `nome`, `quantidade`, `restritoPara`, `dataAdicao`, `dataRemocao`

### 🧠 Objetivo Pedagógico
O projeto visa aplicar os conceitos de **qualidade de software**, **boas práticas de desenvolvimento**, e **controle de requisitos funcionais e não funcionais**.  
Durante o desenvolvimento, foram considerados aspectos como **clareza dos requisitos**, **testabilidade**, **manutenibilidade** e **segurança no controle de acessos**.

---

## 👨‍💻 Integrantes do Grupo
- Diogo Da Silva Souza
- Caue de Souza Luz
- Danilo Queiroz Nogueira
- Fausto Bento Torres
- Vinicius Lacerda Santos

---

## 🏫 Disciplina
**Qualidade de Software**  
Curso de **Ciência da Computação**

---

## 🧱 Tecnologias Utilizadas

- Linguagem: Java
- Banco de Dados: MySQL
- Framework: Spring Boot 
- Ferramentas de controle de versão: Git e GitHub

---

## ✅ Boas Práticas Aplicadas
- Separação de responsabilidades nas entidades
- Registro de ações e rastreabilidade
- Controle de permissões baseado em papéis
- Adoção de padrões de qualidade e clareza no código
- Versionamento e documentação contínua

---

## 📄 Licença
Este projeto foi desenvolvido para fins **acadêmicos** e não possui fins comerciais.
