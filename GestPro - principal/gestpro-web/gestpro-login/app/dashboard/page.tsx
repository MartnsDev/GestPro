"use client";

import { useState, useEffect } from "react";
import {
  Home,
  Package,
  Warehouse,
  CreditCard,
  Users,
  BarChart3,
  Settings,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { getUsuario, logout, type Usuario } from "@/lib/api";
import { removeToken } from "@/lib/auth";
import styles from "@/app/styles/dashboard.module.css";

export default function DashboardPage() {
  const [usuario, setUsuario] = useState<Usuario | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadUsuario() {
      try {
        // Tenta buscar dados do backend via cookie
        const data = await getUsuario();
        if (data) {
          setUsuario(data);
        } else {
          // Se não tiver cookie válido, redireciona para login
          window.location.href = "/";
          return;
        }
      } catch (error) {
        console.error("Erro ao carregar usuário:", error);
        window.location.href = "/";
      } finally {
        setLoading(false);
      }
    }

    loadUsuario();
  }, []);

  const handleLogout = async () => {
    try {
      await logout(); // backend remove cookie
    } catch (error) {
      console.error("Erro ao fazer logout:", error);
    } finally {
      removeToken(); // remove token local, caso exista
      window.location.href = "/";
    }
  };

  if (loading) {
    return (
      <div className={styles.loadingContainer}>
        <div className={styles.loadingText}>Carregando...</div>
      </div>
    );
  }

  if (!usuario) return null;

  const iniciais = usuario.nome
    .split(" ")
    .map((n) => n[0])
    .join("")
    .toUpperCase()
    .slice(0, 2);

  return (
    <div className={styles.dashboardContainer}>
      {/* Header */}
      <header className={styles.dashboardHeader}>
        <div className={styles.headerBrand}>
          <div className={styles.headerLogo}>
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none">
              <path
                d="M7 18c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2-.9-2-2-2zM1 2v2h2l3.6 7.59-1.35 2.45c-.16.28-.25.61-.25.96 0 1.1.9 2 2 2h12v-2H7.42c-.14 0-.25-.11-.25-.25l.03-.12.9-1.63h7.45c.75 0 1.41-.41 1.75-1.03l3.58-6.49c.08-.14.12-.31.12-.48 0-.55-.45-1-1-1H5.21l-.94-2H1zm16 16c-1.1 0-1.99.9-1.99 2s.89 2 1.99 2 2-.9 2-2-.9-2-2-2z"
                fill="currentColor"
              />
            </svg>
          </div>
          <span className={styles.headerTitle}>GestPro</span>
        </div>

        <div className={styles.headerUser}>
          <span className={styles.headerUserName}>
            Olá, {usuario.nome.split(" ")[0]}!
          </span>
          {usuario.foto ? (
            <img
              src={usuario.foto}
              alt={usuario.nome}
              className={styles.headerUserAvatar}
            />
          ) : (
            <div className={styles.headerUserInitials}>{iniciais}</div>
          )}
          <Button
            onClick={handleLogout}
            variant="ghost"
            className="text-white hover:text-gray-300 hover:bg-[#1a3a52]"
          >
            Sair
          </Button>
        </div>
      </header>

      {/* Sidebar e Main Content */}
      <div className={styles.dashboardLayout}>
        {/* Sidebar */}
        <aside className={styles.sidebar}>
          <nav className={styles.sidebarNav}>
            <button
              className={`${styles.sidebarNavItem} ${styles.sidebarNavItemActive}`}
            >
              <Home className="w-5 h-5" />
              Dashboard
            </button>
            <button className={styles.sidebarNavItem}>
              <Package className="w-5 h-5" />
              Produtos
            </button>
            <button className={styles.sidebarNavItem}>
              <Warehouse className="w-5 h-5" />
              Estoque
            </button>
            <button className={styles.sidebarNavItem}>
              <CreditCard className="w-5 h-5" />
              Vendas
            </button>
            <button className={styles.sidebarNavItem}>
              <Users className="w-5 h-5" />
              Clientes
            </button>
            <button className={styles.sidebarNavItem}>
              <BarChart3 className="w-5 h-5" />
              Relatórios
            </button>
            <button className={styles.sidebarNavItem}>
              <Settings className="w-5 h-5" />
              Configurações
            </button>
          </nav>
        </aside>

        {/* Main Content */}
        <main className={styles.mainContent}>
          <div className={styles.mainContentInner}>
            {/* Welcome Section */}
            <div className={styles.welcomeSection}>
              <h1 className={styles.welcomeTitle}>
                Olá, {usuario.nome.split(" ")[0]}!
              </h1>
              <p className={styles.welcomeSubtitle}>
                Bem-vindo ao GestPro — Status: {usuario.statusAcesso}
              </p>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}
