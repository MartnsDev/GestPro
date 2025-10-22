// app/payment/page.tsx
"use client";

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

interface UserData {
  nome: string;
  email: string;
  tipoPlano: string;
  foto?: string;
}

export default function PaymentPage() {
  const router = useRouter();
  const [user, setUser] = useState<UserData | null>(null);

  // Simula fetch do usuário logado
  useEffect(() => {
    const userData = localStorage.getItem("usuario"); // ou fetch da API
    if (userData) {
      setUser(JSON.parse(userData));
    } else {
      router.push("/login"); // se não tiver logado, vai pro login
    }
  }, [router]);

  const handlePayment = () => {
    alert("Pagamento realizado com sucesso!"); // Aqui você integra com sua API de pagamento
    // Após pagamento, você pode atualizar o plano do usuário e redirecionar pro dashboard
    router.push("/dashboard");
  };

  if (!user) return null;

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="bg-white rounded-2xl shadow-xl p-8 max-w-md w-full text-center">
        <img
          src={user.foto || "/default-avatar.png"}
          alt="Foto do usuário"
          className="w-24 h-24 mx-auto rounded-full mb-4"
        />
        <h1 className="text-2xl font-bold mb-2">Olá, {user.nome}!</h1>
        <p className="text-gray-600 mb-6">
          Seu plano <span className="font-semibold">{user.tipoPlano}</span>{" "}
          expirou.
        </p>
        <p className="text-gray-700 mb-6">
          Para continuar usando o GestPro, faça o pagamento do plano.
        </p>
        <button
          onClick={handlePayment}
          className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition"
        >
          Pagar Agora
        </button>
      </div>
    </div>
  );
}
// app/payment/page.tsx
("use client");

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

interface UserData {
  nome: string;
  email: string;
  tipoPlano: string;
  foto?: string;
}

export default function PaymentPage() {
  const router = useRouter();
  const [user, setUser] = useState<UserData | null>(null);

  // Simula fetch do usuário logado
  useEffect(() => {
    const userData = localStorage.getItem("usuario"); // ou fetch da API
    if (userData) {
      setUser(JSON.parse(userData));
    } else {
      router.push("/login"); // se não tiver logado, vai pro login
    }
  }, [router]);

  const handlePayment = () => {
    alert("Pagamento realizado com sucesso!"); // Aqui você integra com sua API de pagamento
    // Após pagamento, você pode atualizar o plano do usuário e redirecionar pro dashboard
    router.push("/dashboard");
  };

  if (!user) return null;

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="bg-white rounded-2xl shadow-xl p-8 max-w-md w-full text-center">
        <img
          src={user.foto || "/default-avatar.png"}
          alt="Foto do usuário"
          className="w-24 h-24 mx-auto rounded-full mb-4"
        />
        <h1 className="text-2xl font-bold mb-2">Olá, {user.nome}!</h1>
        <p className="text-gray-600 mb-6">
          Seu plano <span className="font-semibold">{user.tipoPlano}</span>{" "}
          expirou.
        </p>
        <p className="text-gray-700 mb-6">
          Para continuar usando o GestPro, faça o pagamento do plano.
        </p>
        <button
          onClick={handlePayment}
          className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition"
        >
          Pagar Agora
        </button>
      </div>
    </div>
  );
}
