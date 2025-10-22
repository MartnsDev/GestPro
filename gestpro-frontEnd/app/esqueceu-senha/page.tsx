"use client";

import React, { useState } from "react";
import Image from "next/image";
import { Mail, Lock, Check, ArrowLeft, KeyRound } from "lucide-react";

export default function EsqueceuSenhaPage() {
  const [step, setStep] = useState<1 | 2>(1);
  const [email, setEmail] = useState("");
  const [codigo, setCodigo] = useState("");
  const [novaSenha, setNovaSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");

  const BACKEND_URL = "http://localhost:8080/api/auth";

  const handleEnviarCodigo = async (e?: React.FormEvent) => {
    e?.preventDefault();
    try {
      const response = await fetch(`${BACKEND_URL}/esqueceu-senha`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email }),
      });

      if (response.ok) {
        alert("Código enviado para seu email!");
        setStep(2);
      } else if (response.status === 404) {
        alert("Email não encontrado. Verifique e tente novamente.");
      } else {
        alert("Erro ao enviar código. Tente novamente mais tarde.");
      }
    } catch (error) {
      console.error("Erro ao enviar código:", error);
      alert("Erro ao conectar com o servidor.");
    }
  };

  const handleRedefinirSenha = async (e: React.FormEvent) => {
    e.preventDefault();
    if (novaSenha !== confirmarSenha) {
      alert("As senhas não coincidem!");
      return;
    }

    try {
      const response = await fetch(`${BACKEND_URL}/redefinir-senha`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, codigo, novaSenha }),
      });

      if (response.ok) {
        alert("Senha redefinida com sucesso!");
        window.location.href = "/";
      } else if (response.status === 400) {
        alert("Código inválido ou expirado. Tente novamente.");
      } else {
        alert("Erro ao redefinir senha. Tente novamente mais tarde.");
      }
    } catch (error) {
      console.error("Erro ao redefinir senha:", error);
      alert("Erro ao conectar com o servidor.");
    }
  };

  return (
    <div className="min-h-screen w-full relative overflow-hidden bg-gradient-to-r from-[#0f2847] via-[#1a4d5c] to-[#16a085] flex flex-col">
      {/* Cabeçalho */}
      <div className="w-full text-center pt-8 md:pt-12 z-20">
        <h1 className="text-white text-xl md:text-2xl lg:text-3xl font-bold tracking-wider px-4">
          {step === 1 ? "RECUPERAR SENHA" : "REDEFINIR SENHA"}
        </h1>
      </div>

      {/* Container Principal - Formulário Centralizado */}
      <div className="flex-1 flex items-center justify-center px-4 md:px-8">
        <div className="bg-white rounded-2xl md:rounded-3xl shadow-2xl p-6 md:p-8 lg:p-10 w-full max-w-[440px]">
          {/* Voltar */}
          <a
            href="/"
            className="inline-flex items-center gap-2 text-gray-600 hover:text-gray-800 mb-4 text-sm"
          >
            <ArrowLeft className="w-4 h-4" /> Voltar para login
          </a>

          {/* Logo */}
          <div className="flex items-center justify-center mb-6 md:mb-8">
            <Image
              src="/logo-gestpro.png"
              alt="GestPro"
              width={180}
              height={54}
              className="object-contain w-40 md:w-48 lg:w-52 h-auto"
            />
          </div>

          {/* Passo 1 */}
          {step === 1 && (
            <form onSubmit={handleEnviarCodigo} className="space-y-4">
              <p className="text-sm text-gray-600 text-center mb-4">
                Digite seu email para receber o código de recuperação
              </p>

              <div className="relative">
                <Mail className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
                <input
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full pl-12 pr-4 py-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#10b981] focus:border-transparent text-gray-700 placeholder:text-gray-400 text-sm md:text-base"
                  required
                />
              </div>

              <button
                type="submit"
                className="w-full bg-[#10b981] hover:bg-[#059669] text-white font-semibold py-3 rounded-xl transition-colors flex items-center justify-center gap-2 shadow-lg shadow-[#10b981]/30 text-sm md:text-base"
              >
                Enviar Código <Check className="w-5 h-5" strokeWidth={3} />
              </button>
            </form>
          )}

          {/* Passo 2 */}
          {step === 2 && (
            <form onSubmit={handleRedefinirSenha} className="space-y-4">
              <p className="text-sm text-gray-600 text-center mb-4">
                Digite o código enviado para <strong>{email}</strong> e sua nova
                senha
              </p>

              <div className="relative">
                <KeyRound className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
                <input
                  type="text"
                  placeholder="Código de verificação"
                  value={codigo}
                  onChange={(e) => setCodigo(e.target.value)}
                  className="w-full pl-12 pr-4 py-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#10b981] focus:border-transparent text-gray-700 placeholder:text-gray-400 text-sm md:text-base"
                  required
                />
              </div>

              <div className="relative">
                <Lock className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
                <input
                  type="password"
                  placeholder="Nova senha"
                  value={novaSenha}
                  onChange={(e) => setNovaSenha(e.target.value)}
                  className="w-full pl-12 pr-4 py-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#10b981] focus:border-transparent text-gray-700 placeholder:text-gray-400 text-sm md:text-base"
                  required
                />
              </div>

              <div className="relative">
                <Lock className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
                <input
                  type="password"
                  placeholder="Confirmar nova senha"
                  value={confirmarSenha}
                  onChange={(e) => setConfirmarSenha(e.target.value)}
                  className="w-full pl-12 pr-4 py-3 border border-gray-200 rounded-xl focus:outline-none focus:ring-2 focus:ring-[#10b981] focus:border-transparent text-gray-700 placeholder:text-gray-400 text-sm md:text-base"
                  required
                />
              </div>

              <button
                type="submit"
                className="w-full bg-[#10b981] hover:bg-[#059669] text-white font-semibold py-3 rounded-xl transition-colors flex items-center justify-center gap-2 shadow-lg shadow-[#10b981]/30 text-sm md:text-base"
              >
                Redefinir Senha <Check className="w-5 h-5" strokeWidth={3} />
              </button>

              <button
                type="button"
                onClick={() => handleEnviarCodigo()}
                className="w-full text-[#10b981] hover:text-[#059669] font-medium text-sm"
              >
                Não recebeu o código? Reenviar
              </button>
            </form>
          )}
        </div>
      </div>

      {/* Rodapé */}
      <div className="w-full text-center pb-8 md:pb-12 z-20">
        <p className="text-white text-base md:text-lg lg:text-xl font-medium px-4">
          Sua loja organizada, suas vendas garantidas
        </p>
      </div>
    </div>
  );
}
