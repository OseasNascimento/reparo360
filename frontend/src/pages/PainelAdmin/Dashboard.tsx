import React, { useEffect, useMemo, useState } from 'react';
import {
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  CartesianGrid,
  ReferenceLine,
} from 'recharts';
import api from '@/services/api';
import { CardMetric } from '@/components/CardMetric';
import { ChartWrapper } from '@/components/ChartWrapper';
import { Skeleton } from '@/components/Skeleton';
import { Wallet, Receipt, PiggyBank, Briefcase } from 'lucide-react';

type RecordMap = Record<string, number>;

interface ResumoDashboardDTO {
  receita: number;
  despesa: number;
  lucro: number;
  totalServicos: number;
}

const fmtMoney = (v: number) => v.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
const MONTH_LABELS = ['jan', 'fev', 'mar', 'abr', 'mai', 'jun', 'jul', 'ago', 'set', 'out', 'nov', 'dez'];
const BAR_COLOR = '#2563EB'; // azul-600

const Dashboard: React.FC = () => {
  const [resumo, setResumo] = useState<ResumoDashboardDTO | null>(null);
  const [lucroPorMesMap, setLucroPorMesMap] = useState<RecordMap>({});
  const [vendasPorTecnico, setVendasPorTecnico] = useState<RecordMap>({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // anos disponíveis (chaves YYYY-MM)
  const anosDisponiveis = useMemo(() => {
    const anos = new Set<string>();
    Object.keys(lucroPorMesMap).forEach((k) => {
      const [y] = k.split('-');
      if (y) anos.add(y);
    });
    return Array.from(anos).sort();
  }, [lucroPorMesMap]);

  const anoAtual = new Date().getFullYear().toString();
  const [anoSelecionado, setAnoSelecionado] = useState<string>(anoAtual);

  useEffect(() => {
    Promise.all([
      api.get<ResumoDashboardDTO>('/dashboard/resumo'),
      api.get<RecordMap>('/dashboard/financeiro/lucro-por-mes'),
      api.get<RecordMap>('/dashboard/tecnicos/vendas'),
    ])
      .then(([rResumo, rLucro, rVendas]) => {
        setResumo(rResumo.data);
        setLucroPorMesMap(rLucro.data);
        setVendasPorTecnico(rVendas.data);

        const anos = Object.keys(rLucro.data)
          .map((k) => k.split('-')[0])
          .filter(Boolean);
        if (anos.length && !anos.includes(anoAtual)) {
          setAnoSelecionado(anos.sort().slice(-1)[0]!);
        }
      })
      .catch((err) => {
        console.error(err);
        setError('Erro ao carregar dados do dashboard.');
      })
      .finally(() => setLoading(false));
  }, []);

  // série de lucro por mês p/ o ano selecionado
  const lucroSeries = useMemo(() => {
    return MONTH_LABELS.map((label, idx) => {
      const month = String(idx + 1).padStart(2, '0');
      const key = `${anoSelecionado}-${month}`;
      const val = Number(lucroPorMesMap[key] ?? 0);
      return { mes: label, valor: val };
    });
  }, [lucroPorMesMap, anoSelecionado]);

  // total vendas por técnico (usado na barra horizontal)
  const vendasTecnicosData = useMemo(
    () =>
      Object.entries(vendasPorTecnico)
        .map(([nome, total]) => ({ nome, total: Number(total) }))
        .sort((a, b) => b.total - a.total),
    [vendasPorTecnico]
  );

  if (loading) {
    return (
      <div className="p-6 space-y-8 bg-gradient-to-b from-slate-50 to-slate-100 min-h-screen">
        <div className="flex items-center justify-between">
          <h1 className="text-3xl font-bold">Visão Geral</h1>
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {Array.from({ length: 4 }).map((_, i) => (
            <Skeleton key={i} height="4rem" />
          ))}
        </div>
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <ChartWrapper title="Lucro por Mês">
            <Skeleton height="300px" />
          </ChartWrapper>
          <ChartWrapper title="Vendas por Técnico">
            <Skeleton height="300px" />
          </ChartWrapper>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="p-6">
        <p className="text-center text-red-600">{error}</p>
      </div>
    );
  }

  if (!resumo) return null;

  const { receita, despesa, lucro, totalServicos } = resumo;

  // valores fictícios de delta só para ilustrar; ajuste com API se quiser
  const deltaReceita = { value: 12.5, suffix: '%', label: 'vs mês anterior' };
  const deltaDespesa = { value: -5.2, suffix: '%', label: 'vs mês anterior', positiveIsGood: false };
  const deltaLucro = { value: 8.1, suffix: '%', label: 'vs mês anterior' };

  return (
    <div className="p-6 bg-gradient-to-b from-slate-50 to-slate-100 min-h-screen">
      {/* Cabeçalho + filtro ano */}
      <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-6">
        <h1 className="text-3xl font-bold tracking-tight text-slate-900">Visão Geral</h1>

        <div className="flex items-center gap-3">
          <label htmlFor="ano" className="text-sm text-slate-600">
            Ano
          </label>
          <select
            id="ano"
            value={anoSelecionado}
            onChange={(e) => setAnoSelecionado(e.target.value)}
            className="border border-slate-200 rounded-lg px-3 py-2 bg-white text-slate-800 shadow-sm"
          >
            {(anosDisponiveis.length ? anosDisponiveis : [anoAtual]).map((a) => (
              <option key={a} value={a}>
                {a}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* KPIs */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <CardMetric title="Receita" value={fmtMoney(receita)} icon={<Wallet size={18} />} delta={deltaReceita} />
        <CardMetric title="Despesa" value={fmtMoney(despesa)} icon={<Receipt size={18} />} delta={deltaDespesa} />
        <CardMetric title="Lucro" value={fmtMoney(lucro)} icon={<PiggyBank size={18} />} delta={deltaLucro} />
        <CardMetric title="Serviços" value={totalServicos} icon={<Briefcase size={18} />} />
      </div>

      {/* Gráficos */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <ChartWrapper title="Lucro por Mês">
          <div className="text-xs text-slate-500 mb-2">Ano {anoSelecionado}</div>

          {lucroSeries.every((p) => p.valor === 0) ? (
            <div className="h-72 grid place-items-center text-slate-500">
              <div className="text-center">
                <p>Sem dados para este ano.</p>
                <p className="text-xs text-slate-400">Assine/registre OS para ver o gráfico.</p>
              </div>
            </div>
          ) : (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={lucroSeries}>
                <CartesianGrid strokeDasharray="3 3" vertical={false} />
                <XAxis dataKey="mes" />
                <YAxis tickFormatter={(v) => v.toLocaleString('pt-BR')} />
                <Tooltip formatter={(v: number) => fmtMoney(v)} />
                <Legend />
                <ReferenceLine y={0} stroke="#9CA3AF" />
                <Bar dataKey="valor" name="Lucro (R$)" fill={BAR_COLOR} radius={[6, 6, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          )}
        </ChartWrapper>

        <ChartWrapper title="Vendas por Técnico">
          {vendasTecnicosData.length === 0 ? (
            <div className="h-72 grid place-items-center text-slate-500">
              <p>Sem vendas registradas.</p>
            </div>
          ) : (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart
                data={vendasTecnicosData}
                layout="vertical"
                margin={{ left: 28, right: 12, top: 8, bottom: 8 }}
              >
                <CartesianGrid strokeDasharray="3 3" horizontal={false} />
                <XAxis type="number" tickFormatter={(v) => v.toLocaleString('pt-BR')} />
                <YAxis type="category" dataKey="nome" width={150} />
                <Tooltip formatter={(v: number) => fmtMoney(v)} />
                <Legend />
                <Bar dataKey="total" name="Vendas (R$)" fill={BAR_COLOR} radius={[6, 6, 6, 6]} />
              </BarChart>
            </ResponsiveContainer>
          )}
        </ChartWrapper>
      </div>
    </div>
  );
};

export default Dashboard;
