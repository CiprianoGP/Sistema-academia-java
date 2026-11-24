-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 25/11/2026 às 00:26
-- Versão do servidor: 10.11.19-MariaDB
-- Versão do PHP: 8.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `academia`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno`
--

CREATE TABLE `aluno` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `aluno`
--

INSERT INTO `aluno` (`codigo`, `nome`, `cpf`, `telefone`, `email`) VALUES
(1, 'Maria Silva', '111.111.111-11', '(61) 99999-0001', 'maria.silva@email.com'),
(2, 'Joao Pereira', '222.222.222-22', '(61) 99999-0002', 'joao.pereira@email.com'),
(3, 'Ana Souza', '333.333.333-33', '(61) 99999-0003', 'ana.souza@email.com'),
(4, 'Carlos Lima', '444.444.444-44', '(61) 99999-0004', 'carlos.lima@email.com');

-- --------------------------------------------------------

--
-- Estrutura para tabela `instrutor`
--

CREATE TABLE `instrutor` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `cref` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `instrutor`
--

INSERT INTO `instrutor` (`codigo`, `nome`, `cpf`, `telefone`, `cref`) VALUES
(1, 'Ricardo Alves', '555.555.555-55', '(61) 98888-0001', '012345-G/DF'),
(2, 'Patricia Gomes', '666.666.666-66', '(61) 98888-0002', '023456-G/DF'),
(3, 'Bruno Costa', '777.777.777-77', '(61) 98888-0003', '034567-G/DF');

-- --------------------------------------------------------

--
-- Estrutura para tabela `matricula`
--

CREATE TABLE `matricula` (
  `codigo` int(11) NOT NULL,
  `cod_aluno` int(11) NOT NULL,
  `cod_plano` int(11) NOT NULL,
  `cod_modalidade` int(11) NOT NULL,
  `cod_instrutor` int(11) NOT NULL,
  `data_inicio` date NOT NULL,
  `data_vencimento` date NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `situacao` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `matricula`
--

INSERT INTO `matricula` (`codigo`, `cod_aluno`, `cod_plano`, `cod_modalidade`, `cod_instrutor`, `data_inicio`, `data_vencimento`, `valor`, `situacao`) VALUES
(1, 1, 2, 1, 1, '2026-10-01', '2027-01-01', 269.90, 'Ativa'),
(2, 2, 1, 2, 2, '2026-10-15', '2026-11-15', 99.90, 'Ativa'),
(3, 3, 1, 3, 3, '2026-09-10', '2026-10-10', 99.90, 'Cancelada'),
(4, 2, 2, 3, 3, '2026-11-24', '2027-02-24', 269.90, 'Ativa');

-- --------------------------------------------------------

--
-- Estrutura para tabela `modalidade`
--

CREATE TABLE `modalidade` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(60) NOT NULL,
  `descricao` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `modalidade`
--

INSERT INTO `modalidade` (`codigo`, `nome`, `descricao`) VALUES
(1, 'Musculacao', 'Treino com pesos e maquinas'),
(2, 'Spinning', 'Aula de bicicleta em grupo'),
(3, 'Pilates', 'Fortalecimento e flexibilidade'),
(4, 'Natacao', 'Aulas na piscina semiolimpica');

-- --------------------------------------------------------

--
-- Estrutura para tabela `plano`
--

CREATE TABLE `plano` (
  `codigo` int(11) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `duracao_meses` int(11) NOT NULL,
  `valor` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `plano`
--

INSERT INTO `plano` (`codigo`, `descricao`, `duracao_meses`, `valor`) VALUES
(1, 'Mensal', 1, 99.90),
(2, 'Trimestral', 3, 269.90),
(3, 'Semestral', 6, 499.90),
(4, 'Anual', 12, 899.90);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `aluno`
--
ALTER TABLE `aluno`
  ADD PRIMARY KEY (`codigo`);

--
-- Índices de tabela `instrutor`
--
ALTER TABLE `instrutor`
  ADD PRIMARY KEY (`codigo`);

--
-- Índices de tabela `matricula`
--
ALTER TABLE `matricula`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `fk_matricula_aluno` (`cod_aluno`),
  ADD KEY `fk_matricula_plano` (`cod_plano`),
  ADD KEY `fk_matricula_modalidade` (`cod_modalidade`),
  ADD KEY `fk_matricula_instrutor` (`cod_instrutor`);

--
-- Índices de tabela `modalidade`
--
ALTER TABLE `modalidade`
  ADD PRIMARY KEY (`codigo`);

--
-- Índices de tabela `plano`
--
ALTER TABLE `plano`
  ADD PRIMARY KEY (`codigo`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `aluno`
--
ALTER TABLE `aluno`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `instrutor`
--
ALTER TABLE `instrutor`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `matricula`
--
ALTER TABLE `matricula`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `modalidade`
--
ALTER TABLE `modalidade`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `plano`
--
ALTER TABLE `plano`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `matricula`
--
ALTER TABLE `matricula`
  ADD CONSTRAINT `fk_matricula_aluno` FOREIGN KEY (`cod_aluno`) REFERENCES `aluno` (`codigo`),
  ADD CONSTRAINT `fk_matricula_instrutor` FOREIGN KEY (`cod_instrutor`) REFERENCES `instrutor` (`codigo`),
  ADD CONSTRAINT `fk_matricula_modalidade` FOREIGN KEY (`cod_modalidade`) REFERENCES `modalidade` (`codigo`),
  ADD CONSTRAINT `fk_matricula_plano` FOREIGN KEY (`cod_plano`) REFERENCES `plano` (`codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
