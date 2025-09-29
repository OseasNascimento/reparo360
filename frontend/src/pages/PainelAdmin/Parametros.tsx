// src/pages/PainelAdmin/Parametros.tsx
import React, { useEffect, useState } from 'react';
import api from '@/services/api';

const Parametros: React.FC = () => {
  const [custoKm, setCustoKm] = useState<string>('');
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [msg, setMsg] = useState<string | null>(null);

  useEffect(() => {
    api.get<{ valor: number }>('/config/custo-km')
      .then(r => setCustoKm((r.data.valor ?? 0).toFixed(2)))
      .catch(() => setCustoKm('1.50'))
      .finally(() => setLoading(false));
  }, []);

  const salvar = async () => {
    setSaving(true);
    setMsg(null);
    try {
      const valor = Number(custoKm.replace(',', '.'));
      if (isNaN(valor) || valor < 0) {
        setMsg('Informe um valor válido (ex.: 2.50).');
        setSaving(false);
        return;
      }
      const r = await api.put<{ valor: number }>('/config/custo-km', { valor });
      setCustoKm(r.data.valor.toFixed(2));
      setMsg('Custo por km atualizado com sucesso!');
    } catch (e: any) {
      setMsg(e.response?.data?.message || 'Erro ao salvar.');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="p-6">Carregando…</div>;

  return (
    <div className="p-6 bg-white rounded shadow max-w-lg">
      <h2 className="text-2xl font-bold mb-4">Parâmetros do Sistema</h2>

      <label className="block text-sm font-medium text-gray-700 mb-2">
        Custo por KM (R$)
      </label>
      <input
        type="text"
        className="border rounded px-3 py-2 w-full"
        value={custoKm}
        onChange={e => setCustoKm(e.target.value)}
        placeholder="Ex.: 2.50"
      />

      <button
        onClick={salvar}
        disabled={saving}
        className="mt-4 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        {saving ? 'Salvando…' : 'Salvar'}
      </button>

      {msg && <p className="mt-3 text-sm">{msg}</p>}
    </div>
  );
};

export default Parametros;
