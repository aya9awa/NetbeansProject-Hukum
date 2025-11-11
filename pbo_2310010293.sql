-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 08, 2025 at 06:26 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbo_2310010293`
--

-- --------------------------------------------------------

--
-- Table structure for table `advokat`
--

CREATE TABLE `advokat` (
  `id_advokat` varchar(20) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `pass` varchar(50) NOT NULL,
  `no_tlp` varchar(20) DEFAULT NULL,
  `desk` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `advokat`
--

INSERT INTO `advokat` (`id_advokat`, `nama`, `email`, `pass`, `no_tlp`, `desk`) VALUES
('0123', 'Alya', 'alya123@gmail.com', 'alya', '0912831', 'Pengacara'),
('0124', 'Maulida', 'maulida23@gmail.com', 'maulida', '0912831', 'Penasehat');

-- --------------------------------------------------------

--
-- Table structure for table `hkonsul`
--

CREATE TABLE `hkonsul` (
  `idhkons` varchar(20) NOT NULL,
  `jawab` varchar(255) DEFAULT NULL,
  `tgl` varchar(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hkonsul`
--

INSERT INTO `hkonsul` (`idhkons`, `jawab`, `tgl`, `status`) VALUES
('0981', 'Kurang duit', '8 Juli', 'Selesai'),
('0982', 'Karena anda kurang kasih sayang', '8 Desember', 'Selesai');

-- --------------------------------------------------------

--
-- Table structure for table `klien`
--

CREATE TABLE `klien` (
  `idklien` varchar(100) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `no_tlp` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `klien`
--

INSERT INTO `klien` (`idklien`, `nama`, `email`, `no_tlp`) VALUES
('0991', 'Alya', 'alya123@gmail.com', '098771238123'),
('0992', 'Hikmah', 'hikmah@gmail.com', '21312321'),
('12312', '123', '123', '123');

-- --------------------------------------------------------

--
-- Table structure for table `konsultasi`
--

CREATE TABLE `konsultasi` (
  `id_kons` varchar(20) NOT NULL,
  `tanggal` varchar(20) DEFAULT NULL,
  `pertanyaan` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `konsultasi`
--

INSERT INTO `konsultasi` (`id_kons`, `tanggal`, `pertanyaan`) VALUES
('00111', '8 November', 'Kenapa saya banyak tugas?'),
('00222', '8 Juli', 'Kenapa saya ganteng?'),
('123', '1231', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `advokat`
--
ALTER TABLE `advokat`
  ADD PRIMARY KEY (`id_advokat`);

--
-- Indexes for table `hkonsul`
--
ALTER TABLE `hkonsul`
  ADD PRIMARY KEY (`idhkons`);

--
-- Indexes for table `klien`
--
ALTER TABLE `klien`
  ADD PRIMARY KEY (`idklien`);

--
-- Indexes for table `konsultasi`
--
ALTER TABLE `konsultasi`
  ADD PRIMARY KEY (`id_kons`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
